#!/bin/bash

kubectl delete daemonset psw-installer
ibmcloud cr image-rm us.icr.io/datashield-core/psw-installer
docker build -t us.icr.io/datashield-core/psw-installer . 
docker push us.icr.io/datashield-core/psw-installer
kubectl create -f deployment_install_psw.yaml 