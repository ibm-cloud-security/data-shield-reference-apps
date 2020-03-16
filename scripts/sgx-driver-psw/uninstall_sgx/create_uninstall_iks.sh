#!/bin/bash

bash cleanup_iks.sh

docker build -t icr.io/ibm/ibm-sgx-uninstaller  . 
docker push icr.io/ibm/ibm-sgx-uninstaller
kubectl create -f deployment_uninstall_sgx_iks.yaml 