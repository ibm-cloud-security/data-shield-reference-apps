import requests 
import logging
from flask import Flask
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

@app.route("/")
def hello():
    return "Hello World"

@app.route("/request")
def request():
    URL = "https://python-flask-server-service-sgx:5002/token"

    data = {'name':'test_name'}

    output = ""
    try:
        r = requests.post(url = URL, json = data, verify='/usr/local/share/ca-certificates/enclave-manager-ca.crt', cert=("flask-client.crt", "flask-client.key"))
        output = r.content
    except Exception as e:
        logger.error(e)
    return output

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)