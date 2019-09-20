#!/usr/bin/env bash

ibmcloud cr image-rm ${CONTAINER_REGISTRY_PATH}/python-flask-client
docker build -t ${CONTAINER_REGISTRY_PATH}/python-flask-client .
docker push ${CONTAINER_REGISTRY_PATH}/python-flask-client