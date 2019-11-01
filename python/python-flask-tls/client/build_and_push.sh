#!/usr/bin/env bash

ibmcloud cr image-rm <your registry path>/python-flask-client
docker build -t <your registry path>/python-flask-client .
docker push <your registry path>/python-flask-client