#!/usr/bin/env bash

kubectl delete pod python-flask-server-sgx
kubectl create -f sgx-deployment.yml

kubectl delete service python-flask-server-service-sgx
kubectl delete ing python-flask-server-ingress
until kubectl create -f sgx-service.yml; do
    sleep 20
done
kubectl create -f ingress.yml