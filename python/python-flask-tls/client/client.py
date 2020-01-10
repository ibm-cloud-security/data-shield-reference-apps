import requests 
import logging
from flask import Flask
from flask import request
app = Flask(__name__)

logger = logging.getLogger('python_tls_app')
logger.setLevel(logging.INFO)
fh = logging.FileHandler("python_tls.log")
fh.setLevel(logging.DEBUG)
ch = logging.StreamHandler()
ch.setLevel(logging.DEBUG)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
fh.setFormatter(formatter)
ch.setFormatter(formatter)
logger.addHandler(fh)
logger.addHandler(ch)

@app.route("/request", methods=['GET', 'POST'])
def request_token():
    URL = "https://python-flask-server-service-sgx:5002/token"

    if request.method == 'GET':
        data = {'name':'test_name'}
    else:
        data = {'name': request.get_json()['name']}

    output = ""
    try:
        r = requests.post(url = URL, json = data, verify='/etc/ssl/certs/ca-certificates.crt', cert=("flask-client.crt", "flask-client.key"))
        output = r.content
    except Exception as e:
        logger.error(e)
    return output

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)