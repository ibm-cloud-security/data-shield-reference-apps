#!/bin/bash

oc create -f https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/config_openshift/scc.yaml
oc create serviceaccount sgx-admin
oc secrets link sgx-admin all-icr-io --for=pull
oc adm policy add-scc-to-user sgx-admin -z sgx-admin
