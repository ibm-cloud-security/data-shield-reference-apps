#!/bin/bash

bash cleanup_iks.sh

docker build -t us.icr.io/datashield-core/sgx-uninstaller . 
docker push us.icr.io/datashield-core/sgx-uninstaller
kubectl create -f deployment_uninstall_sgx_iks.yaml 