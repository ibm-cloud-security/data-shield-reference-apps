#!/bin/bash

bash cleanup_openshift.sh

docker build -t us.icr.io/datashield-core/ibm-sgx-uninstaller . 
docker push us.icr.io/datashield-core/ibm-sgx-uninstaller
oc create -f scc.yaml
oc create serviceaccount sgx-admin
oc secrets link sgx-admin all-icr-io --for=pull
oc adm policy add-scc-to-user sgx-admin -z sgx-admin
oc create -f deployment_uninstall_sgx_openshift.yaml 