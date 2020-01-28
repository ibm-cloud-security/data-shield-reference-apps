#!/bin/bash

# Install tools
sudo apt-get install -y libssl-dev libcurl4-openssl-dev libprotobuf-dev

# Install PSW Ubuntu 18
echo 'deb [arch=amd64] https://download.01.org/intel-sgx/sgx_repo/ubuntu xenial main' | sudo tee /etc/apt/sources.list.d/intelsgx.list
wget -qO - https://download.01.org/intel-sgx/sgx_repo/ubuntu/intel-sgx-deb.key | sudo apt-key add -
sudo apt-get update -y
sudo apt-get install -y libsgx-launch libsgx-urts
sudo apt-get install -y libsgx-epid libsgx-urts
sudo apt-get install -y libsgx-quote-ex libsgx-urts
