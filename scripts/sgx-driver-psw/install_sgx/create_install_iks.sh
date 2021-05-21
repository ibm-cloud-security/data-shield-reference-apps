#!/bin/bash

bash cleanup_iks.sh

docker build -t us.icr.io/datashield-core/ibm-sgx-installer . 
docker push us.icr.io/datashield-core/ibm-sgx-installer 
# docker build -t icr.io/ibm/ibm-sgx-installer . 
# docker push icr.io/ibm/ibm-sgx-installer 
kubectl create -f deployment_install_sgx_iks.yaml 