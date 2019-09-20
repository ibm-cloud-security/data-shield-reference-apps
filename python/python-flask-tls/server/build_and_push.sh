#!/usr/bin/env bash

ibmcloud cr image-rm ${CONTAINER_REGISTRY_PATH}/python-flask-server
docker build -t ${CONTAINER_REGISTRY_PATH}/python-flask-server .
docker push ${CONTAINER_REGISTRY_PATH}/python-flask-server
