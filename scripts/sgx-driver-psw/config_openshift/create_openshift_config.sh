#!/bin/bash

oc create -f scc.yaml
oc create serviceaccount sgx-admin
oc secrets add serviceaccount/sgx-admin secrets/default-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-us-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-uk-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-de-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-au-icr-io --for=pull
oc secrets add serviceaccount/sgx-admin secrets/default-jp-icr-io --for=pull
oc adm policy add-scc-to-user sgx-admin -z sgx-admin