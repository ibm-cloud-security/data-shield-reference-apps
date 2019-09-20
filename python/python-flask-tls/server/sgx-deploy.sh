#!/usr/bin/env bash

kubectl delete pod python-flask-server-sgx
kubectl create -f sgx-deployment.yml

kubectl delete service python-flask-server-service-sgx
kubectl delete ing python-flask-server-ingress
sleep 120
kubectl create -f sgx-service.yml
kubectl create -f ingress.yml