#!/usr/bin/env bash

ibmcloud cr image-rm <your registry path>/python-flask-server
docker build -t <your registry path>/python-flask-server .
docker push <your registry path>/python-flask-server
