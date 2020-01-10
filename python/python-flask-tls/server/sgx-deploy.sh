#!/usr/bin/env bash

kubectl delete pod python-flask-server-sgx
kubectl create -f sgx-deployment.yml

kubectl delete service python-flask-server-service-sgx
until kubectl create -f sgx-service.yml; do
    sleep 20
done