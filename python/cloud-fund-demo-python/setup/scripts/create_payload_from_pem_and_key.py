import json
# json.loads(string)
payload = {
  "name": "cloudfund.app",
  "description": "description",
  "data": {}
}

with open("cloudfund.app.pem", 'r') as f:
  content = f.read()
payload['data']['content'] = content

with open("cloudfund.app.key", 'r') as f:
  privateKey = f.read()
payload['data']['priv_key'] = privateKey

print (json.dumps(payload), file=open ("cloudfund.json","w"))

payload2 = {
  "name": "bff.cloudfund.app",
  "description": "description",
  "data": {}
}

with open("bff.cloudfund.app.pem", 'r') as f:
  content = f.read()
payload2['data']['content'] = content

with open("bff.cloudfund.app.key", 'r') as f:
  privateKey = f.read()
payload2['data']['priv_key'] = privateKey

print (json.dumps(payload2), file=open ("bff.cloudfund.json","w"))

