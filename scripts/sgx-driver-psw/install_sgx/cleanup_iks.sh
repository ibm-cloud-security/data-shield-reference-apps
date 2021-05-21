#!/bin/bash

kubectl delete daemonset sgx-installer
ibmcloud cr image-rm us.icr.io/datashield-core/ibm-sgx-installer