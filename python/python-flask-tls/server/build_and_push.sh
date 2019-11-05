#!/usr/bin/env bash

ibmcloud cr image-rm $registry/python-flask-server
docker build -t $registry/python-flask-server .
docker push $registry/python-flask-server
