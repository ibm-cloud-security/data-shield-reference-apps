#!/bin/bash

kubectl delete daemonset psw-uninstaller
ibmcloud cr image-rm us.icr.io/datashield-core/psw-uninstaller
docker build -t us.icr.io/datashield-core/psw-uninstaller . 
docker push us.icr.io/datashield-core/psw-uninstaller
kubectl create -f deployment_uninstall_psw.yaml 