#!/bin/bash

oc delete daemonset sgx-installer
oc delete scc sgx-admin
oc delete serviceaccount sgx-admin
ibmcloud cr image-rm us.icr.io/datashield-core/sgx-installer