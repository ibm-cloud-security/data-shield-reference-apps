#!/usr/bin/env bash

kubectl delete pod python-flask-client-sgx
kubectl create -f sgx-deployment.yml

kubectl delete service python-flask-client-service-sgx
kubectl delete ing python-flask-client-ingress
until kubectl create -f sgx-service.yml; do
    sleep 20
done
kubectl create -f ingress.yml

