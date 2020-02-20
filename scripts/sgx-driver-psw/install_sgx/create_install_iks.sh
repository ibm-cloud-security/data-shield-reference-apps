#!/bin/bash

bash cleanup_iks.sh

docker build -t us.icr.io/datashield-core/sgx-installer . 
docker push us.icr.io/datashield-core/sgx-installer
kubectl create -f deployment_install_sgx_iks.yaml 