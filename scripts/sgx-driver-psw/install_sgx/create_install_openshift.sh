#!/bin/bash

bash cleanup_openshift.sh

docker build -t us.icr.io/datashield-core/ibm-sgx-installer . 
docker push us.icr.io/datashield-core/ibm-sgx-installer 
# docker build -t icr.io/ibm/ibm-sgx-installer  . 
# docker push icr.io/ibm/ibm-sgx-installer 
oc create -f scc.yaml
oc create serviceaccount sgx-admin
oc secrets link sgx-admin all-icr-io --for=pull
oc adm policy add-scc-to-user sgx-admin -z sgx-admin
oc create -f deployment_install_sgx_openshift.yaml 