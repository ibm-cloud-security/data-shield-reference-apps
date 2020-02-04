#!/bin/bash

kubectl delete daemonset sgx-driver-installer
ibmcloud cr image-rm us.icr.io/datashield-core/sgx-driver-installer
docker build -t us.icr.io/datashield-core/sgx-driver-installer . 
docker push us.icr.io/datashield-core/sgx-driver-installer
kubectl create -f deployment_install_sgx_driver.yaml 