#!/bin/bash

kubectl delete daemonset sgx-installer
ibmcloud cr image-rm us.icr.io/datashield-core/sgx-installer
docker build -t us.icr.io/datashield-core/sgx-installer . 
docker push us.icr.io/datashield-core/sgx-installer
kubectl create -f deployment_install_sgx.yaml 