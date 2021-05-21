#!/bin/bash
wget https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/uninstall_sgx/uninstall_sgx.sh --no-check-certificate
chroot /host bash < uninstall_sgx.sh
while true; do
  sleep 1000
done