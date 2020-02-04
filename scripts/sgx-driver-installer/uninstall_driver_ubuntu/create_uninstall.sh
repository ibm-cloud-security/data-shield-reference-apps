#!/bin/bash

kubectl delete daemonset sgx-driver-uninstaller
ibmcloud cr image-rm us.icr.io/datashield-core/sgx-driver-uninstaller
docker build -t us.icr.io/datashield-core/sgx-driver-uninstaller . 
docker push us.icr.io/datashield-core/sgx-driver-uninstaller
kubectl create -f deployment_uninstall_sgx_driver.yaml 