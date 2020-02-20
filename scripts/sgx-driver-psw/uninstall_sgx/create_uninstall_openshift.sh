#!/bin/bash

bash cleanup_openshift.sh

docker build -t us.icr.io/datashield-core/sgx-uninstaller . 
docker push us.icr.io/datashield-core/sgx-uninstaller
oc create -f scc.yaml
oc create serviceaccount sgx-admin
oc secrets add serviceaccount/sgx-admin secrets/default-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-us-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-uk-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-de-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-au-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-jp-icr-io --for=pull
oc adm policy add-scc-to-user sgx-admin -z sgx-admin
oc create -f deployment_uninstall_sgx_openshift.yaml 