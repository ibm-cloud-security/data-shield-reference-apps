#!/bin/bash

kubectl delete daemonset sgx-uninstaller
ibmcloud cr image-rm us.icr.io/datashield-core/sgx-uninstaller
docker build -t us.icr.io/datashield-core/sgx-uninstaller . 
docker push us.icr.io/datashield-core/sgx-uninstaller
kubectl create -f deployment_uninstall_sgx.yaml 