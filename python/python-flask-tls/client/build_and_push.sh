#!/usr/bin/env bash

ibmcloud cr image-rm $registry/python-flask-client
docker build -t $registry/python-flask-client .
docker push $registry/python-flask-client