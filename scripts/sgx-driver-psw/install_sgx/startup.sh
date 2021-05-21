#!/bin/bash
wget https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/install_sgx/install_sgx.sh --no-check-certificate
chroot /host bash < install_sgx.sh
while true; do
  sleep 1000
done