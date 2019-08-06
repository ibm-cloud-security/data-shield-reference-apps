import logging
from bson.json_util import dumps
from flask import Flask
from flask import request, jsonify
import json
import ast
import imp
import os
import time
from pymongo import MongoClient
from urllib.parse import urlparse
from urllib.parse import parse_qs
from keyprotect import KeyProtect
from bson.objectid import ObjectId
import requests

app = Flask(__name__)

#Setting logger
logger = logging.getLogger('cloud_fund_app')
#logger.setLevel(logging.DEBUG)
logger.setLevel(logging.INFO)
fh = logging.FileHandler("cloud_fund.log")
fh.setLevel(logging.DEBUG)
ch = logging.StreamHandler()
ch.setLevel(logging.DEBUG)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
fh.setFormatter(formatter)
ch.setFormatter(formatter)
logger.addHandler(fh)
logger.addHandler(ch)

logger.info("")
logger.info("************* Starting execution ****************")
logger.info("Reading environment variables.")
#DATABASE = MongoClient()[os.environ.get('DB_NAME')] # DB_NAME
DATABASE = MongoClient()[str(open('/etc/secret-volume/db_name', 'r').read())] # DB_NAME
logger.debug(DATABASE)
#username = os.environ.get('SECRET_USERNAME')
username = str(open('/etc/secret-volume/username', 'r').read())
logger.debug("User: "+username)
#password = os.environ.get('SECRET_PASSWORD')
password = str(open('/etc/secret-volume/password', 'r').read())
#dbconnection = os.environ.get('SECRET_DBCONN')
dbconnection = str(open('/etc/secret-volume/dbconn', 'r').read())
logger.debug("DB Connection: "+dbconnection)
#apikey = os.environ.get('API_KEY')
apikey = str(open('/etc/secret-volume/API_KEY', 'r').read())
#kp_instance_id = os.environ.get('KP_INSTANCE_ID')
kp_instance_id = str(open('/etc/secret-volume/kp_instance_id', 'r').read())
logger.debug("Key Protect Instance: "+kp_instance_id)
#crk_id = os.environ.get('CRK_ID')
crk_id = str(open('/etc/secret-volume/crk_id', 'r').read())
logger.debug("Crypto Key: "+crk_id)
IAM_URL = "https://iam.bluemix.net/oidc/token"
logger.debug("IAM URL: "+IAM_URL)
uri = 'mongodb://' + username + ':' + password + '@' + dbconnection +'&ssl=true&ssl_ca_certs=/etc/secret-volume/cert_pem'
#uri = 'mongodb://' + username + ':' + password + '@' + dbconnection
logger.debug("URI: "+uri)
client = MongoClient(uri)
logger.debug(client)

# Select the database
#db = client[os.environ.get('DB_NAME')]
db = client[str(open('/etc/secret-volume/db_name', 'r').read())]
# Select the collection
collection = db.transactions
cards = db.cards

@app.route("/")
def get_initial_response():
    """Welcome message for the API."""
    # Message to the trandaction
    logger.info("get_initial_response():GET")
    message = {
        'apiVersion': 'v1.0',
        'status': '200',
        'message': 'Welcome to the Disaster Funding API'
    }

    # Making the message looks good
    logger.debug(message)
    resp = jsonify(message)
    # Returning the object
    return resp

@app.route("/api/v1/transactions", methods=['GET'])
def fetch_transactions():
    """
       Function to fetch the transactions.
       """

    logger.info("fetch_transactions():GET")
    logger.debug(request.query_string)

    try:
        # Call the function to get the query params
        # Check if query_string is not empty
        if request.query_string:
            query_params = parse_query_params(request.query_string)

            # Try to convert the value to int
            #query = {k: int(v) if isinstance(v, str) and v.isdigit() else v for k, v in query_params.items()}
            #query = dict((k, int(v)) for k, v in query_params.items())
            query = dict((str(k,'utf-8'), str(v,'utf-8')) for k, v in query_params.items())

            # Fetch all the record(s)
            records_fetched = collection.find(query)


            # Only return the date and contribution fields
            sanitized_records = []
            for record in records_fetched:
                sanitized_record = {
                    "date": record['date'],
                    "contribution": record['contribution']
                }
                sanitized_records.append(sanitized_record)


            # Check if the records are found
            if records_fetched.count() > 0:
                # Prepare the response
                return dumps(sanitized_records)
            else:
                # No records are found
                logger.info("No records were found.")
                return "", 404

        else:
            logger.info("Query string is empty")
            # Return all the records as query string parameters are not available
            records_fetched = collection.find()

            if records_fetched.count()>0:
                # Prepare response if the transactions are found
                return dumps(records_fetched)
            else:
                # Return empty array if no transactions are found
                return jsonify([])
    except:
        # Error while trying to fetch the resource
        # Add message for debugging purpose
        logger.error("Error while getting transactions")
        return "", 500

@app.route("/api/v1/transactions", methods=['POST'])
def create_transaction():
    """
       Function to create new transaction.
       """
    try:
        # Create new transaction (TODO: validate user input)
        logger.info("create_transaction(): POST")
        startTime = time.time()
        logger.info("Start time:"+str(startTime))
        try:
            body = ast.literal_eval(json.dumps(request.get_json()))
        except Exception as e:
            # Bad request as request body is not available
            # Add message for debugging purpose
            logger.error(e)
            return "", 400

        record_created = collection.insert_one(body)

        logger.debug("Record created");

        # Prepare the response
        if isinstance(record_created, list):
            # Return list of Id of the newly created item
            return jsonify([str(v) for v in record_created]), 201
        else:
            endTime = time.time()
            logger.info("End time:"+str(endTime))
            # Return Id of the newly created item
            return jsonify(str(record_created)), 201
    except Exception as e:
        # Error while trying to create the resource
        logger.error("Error while posting transaction.")
        logger.error(e);
        return "", 500

@app.route("/api/v1/store_card", methods=['POST'])
def store_card():
    """
       Function to create new transaction.
       """
    try:
        # Create new transaction
        logger.info("store_card():POST")
        startTime = time.time()
        logger.info("Start time:"+str(startTime))

        try:
            card_info = request.get_json()
            email = card_info['email']
            campaign = card_info['campaign']
            contribution = card_info['contribution']
            date = card_info['date']
            type = card_info['type']
            logger.debug(email)

            # Get iam token
            iam_token = getAccessToken()
            logger.debug("IAM Token")
            logger.debug(iam_token)

            # Create Key Project Instance
            kp_instance = KeyProtect(iam_token, kp_instance_id)
            logger.debug("KP Instance")
            logger.debug(kp_instance)

            # Generate DEK using crk_id
            dek_object = kp_instance.generateDEK(crk_id)
            logger.debug("DEK object")
            logger.debug(dek_object)

            # Get wrapped DEK from object
            wdek = dek_object['ciphertext']
            logger.debug("WDEK")
            logger.debug(wdek)

            # Encrypt value with wrapped dek and the crk_id - encrypt accepts string objects only
            encrypted_card_info = kp_instance.encrypt(json.dumps(card_info), wdek, crk_id)
            logger.debug("Encrypting information")
            logger.debug(encrypted_card_info)

            logger.info("Creating record to store.")
            record_created = cards.insert_one(
            {
            "wdek": wdek,
            "email": email,
            "campaign": campaign,
            "contribution": contribution,
            "date": date,
            "encrypted_card_info":encrypted_card_info
            })

            endTime = time.time()
            logger.info("End time:"+str(endTime))

            # Prepare the response
            if isinstance(record_created, list):
                # Return list of Id of the newly created item
                return jsonify([str(v) for v in record_created]), 201
            else:
                # Return Id of the newly created item
                return jsonify(str(record_created)), 201


        except Exception as e:
            # Bad request as request body is not available
            # Add message for debugging purpose
            logger.error(e)
            return "", 400

        record_created = cards.insert(body)
        return "Created", 201
    except:
        logger.error("Error while posting card info.")
        # Error while trying to create the resource

@app.route("/api/v1/transactions/<transaction_id>", methods=['DELETE'])
def remove_transaction(transaction_id):
    """
       Function to remove the transaction.
       """
    try:
        logger.info("remove_transaction(transaction_id): DELETE")
        logger.info(transaction_id)
        # Delete the transaction
        delete_transaction = collection.delete_one({"_id": ObjectId(transaction_id)})

        if delete_transaction.deleted_count > 0 :
            # Prepare the response
            return "", 204
        else:
            # Resource Not found
            return "", 404
    except:
        # Error while trying to delete the resource
        # Add message for debugging purpose
        logger.error("Error while deleting transaction.")
        return "", 500

@app.route("/api/v1/get_cards", methods=['GET'])
def fetch_cards():
    """
       Function to fetch the transactions.
       """

    try:
        logger.info("fetch_cards(): GET")
        # Call the function to get the query params
        # Check if query_string is not empty
        if request.query_string:

            query_params = parse_query_params(request.query_string)

            # Try to convert the value to int
            #query = {k: int(v) if isinstance(v, str) and v.isdigit() else v for k, v in query_params.items()}
            #query = dict((k, int(v)) for k, v in query_params.items())
            query = dict((str(k,'utf-8'), str(v,'utf-8')) for k, v in query_params.items())


            # Fetch all the record(s)
            records_fetched = cards.find(query)

            # Only return the date and contribution fields
            sanitized_records = []
            for record in records_fetched:
                sanitized_record = {

                    "date": record['date'],
                    "contribution": record['contribution']
                }
                sanitized_records.append(sanitized_record)

            # Check if the records are found
            if records_fetched.count() > 0:
                # Prepare the response
                return dumps(sanitized_records)
            else:
                # No records are found
                logger.info("No records were found.")
                return "", 404
        else:
            logger.info("No query string found")
            # Return all the records as query string parameters are not available
            records_fetched = cards.find()

            if records_fetched.count()>0:
                # Prepare response if the transactions are found
                return dumps(records_fetched)
            else:
                # Return empty array if no transactions are found
                logger.info("No records were found.")
                return jsonify([]), 404
    except Exception as e:
        # Error while trying to fetch the resource
        # Add message for debugging purpose
        logger.error("Error while getting card records.")
        logger.error(e)
        return "", 500

def parse_query_params(query_string):
    """
        Function to parse the query parameter string.
        """
    # Parse the query param string
    logger.info("parse_query_params(query_string)")
    parsed = urlparse(query_string)
    logger.info(parsed)
    query_params = dict(parse_qs(parsed.path))
    logger.debug(query_params)
    # Get the value from the list
    query_params = {k: v[0] for k, v in query_params.items()}
    return query_params

def getAccessToken():
    logger.info("getAccessToken()")
    data = [
        ('grant_type', 'urn:ibm:params:oauth:grant-type:apikey'),
        ('apikey', apikey),
    ]
    headers = {'content-type': 'application/x-www-form-urlencoded', 'Accept': 'application/json'}
    r = requests.post(IAM_URL, data=data, headers=headers)
    data = r.json()
    logger.info("Returning access token.")
    return data['access_token']

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=int("8500"))
    #app.run(host="0.0.0.0", port=int("8500"), debug=True)

