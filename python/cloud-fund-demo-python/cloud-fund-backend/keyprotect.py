from flask import Flask
from resources import Resource_group
from resources import Resource
from resources import Account
 
import requests
import json
from Crypto.Cipher import AES
import base64, os
import collections
app = Flask(__name__)

# api-endpoint
IAM_URL = "https://iam.bluemix.net/oidc/token"

class KeyProtect:
        def __init__(self, token, instance_id, url = "https://keyprotect.us-south.bluemix.net/api/v2/keys"):
            self.token = token
            #instance id
            self.instance_id = instance_id
            self.url = url

        def listKeys(self):
            bearer_token = 'Bearer '+ self.token
            headers = {'content-type': 'application/x-www-form-urlencoded', 'Accept': 'application/vnd.ibm.collection+json', 'authorization': bearer_token, 'bluemix-instance': self.instance_id}

            # sending get request and saving the response as response object
            r = requests.get(url = self.url, headers=headers)

            # extracting data in json format
            data = r.json()

            return data

        def generateDEK(self, CRKid, aad="[null]"):
            bearer_token = 'Bearer '+ self.token

            URL = self.url + '/' + CRKid
            PARAMS = {'action':'wrap'}

            #payload = "{\"aad\":[null]}"
            payload = "{\"aad\":" + aad +"}"

            headers = {'content-type': 'application/vnd.ibm.kms.key_action+json', 'Accept': 'application/vnd.ibm.collection+json', 'authorization': bearer_token, 'bluemix-instance': self.instance_id}
 
            # sending get request and saving the response as response object
            r = requests.post(url = URL, data=payload, params=PARAMS, headers=headers)
 
            # extracting data in json format
            data = r.json()

            return data

        def wrap(self, plaintext, CRKid, aad="[null]"):

            bearer_token = 'Bearer '+ self.token
            URL = self.url + '/' + CRKid
            PARAMS = {'action':'wrap'}

            payload = "{\"aad\":" + aad +", \"plaintext\":\""+plaintext+"\"}"
            #payload = "{\"aad\":[null],\"plaintext\":\"password\"}"

            headers = {'content-type': 'application/vnd.ibm.kms.key_action+json', 'Accept': 'application/vnd.ibm.collection+json', 'authorization': bearer_token, 'bluemix-instance': self.instance_id}
 
            # sending get request and saving the response as response object
            r = requests.post(url = URL, data=payload, params=PARAMS, headers=headers)
 
            # extracting data in json format
            data = r.json()

            cipher_text = data['ciphertext']
            return cipher_text

        def unwrap(self, ciphertext, CRKid, aad="[null]"):

            bearer_token = 'Bearer '+ self.token
            URL = self.url + '/' + CRKid
            PARAMS = {'action':'unwrap'}

            payload = "{\"aad\":" + aad +", \"ciphertext\":\""+ciphertext+"\"}"

            headers = {'content-type': 'application/vnd.ibm.kms.key_action+json', 'Accept': 'application/vnd.ibm.collection+json', 'authorization': bearer_token, 'bluemix-instance': self.instance_id}
 
            # sending get request and saving the response as response object
            r = requests.post(url = URL, data=payload, params=PARAMS, headers=headers)
 
            # extracting data in json format
            data = r.json()

            plaintext = data['plaintext']
            return plaintext

        def encrypt(self, data, wDEK, CRKid, padding_character="{"):
            encoded_dek = self.unwrap(wDEK, CRKid);
            app.logger.info(encoded_dek)

            dek = base64.b64decode(encoded_dek)
            #print(dek)

            cipher = AES.new(dek)
            #print(cipher)

	    # pad the message
	    # because AES encryption requires the length of the msg to be a multiple of 16
            app.logger.info(data)
            padded_data = data + (padding_character * ((16-len(data)) % 16))

	    # use the cipher to encrypt the padded message
            encrypted_msg = cipher.encrypt(padded_data)

	    # encode the encrypted msg for storing safely in the database
            encoded_encrypted_msg = base64.b64encode(encrypted_msg)
            app.logger.info(type(encoded_encrypted_msg))

	    # return encoded encrypted message
            return encoded_encrypted_msg

        def decrypt(self, encoded_encrypted_data, wDEK, CRKid, padding_character=b'{'):
            encoded_dek = self.unwrap(wDEK, CRKid);
            dek = base64.b64decode(encoded_dek)
            cipher = AES.new(dek)

            encrypted_data = base64.b64decode(encoded_encrypted_data)

	    # use the cipher to decrypt the encrypted message
            decrypted_msg = cipher.decrypt(encrypted_data)

	    # unpad the encrypted message
            unpadded_decrypted_msg = decrypted_msg.rstrip(padding_character)

	    # return a decrypted original private message
            return unpadded_decrypted_msg

#end keyprotect class

def getAccessToken():
    data = [
        ('grant_type', 'urn:ibm:params:oauth:grant-type:apikey'),
        ('apikey', "ccPOrzHZaHk3kCudSUhyjq1R38U3xESqVNVuTnnyVOWw"),
    ]
    headers = {'content-type': 'application/x-www-form-urlencoded', 'Accept': 'application/json'}
    r = requests.post(IAM_URL, data=data, headers=headers)
    data = r.json()
    #self.access_token = data['access_token']
    return data['access_token']
