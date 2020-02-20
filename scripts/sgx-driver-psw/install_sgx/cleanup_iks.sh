#!/bin/bash

kubectl delete daemonset sgx-uninstaller
ibmcloud cr image-rm us.icr.io/datashield-core/sgx-uninstaller