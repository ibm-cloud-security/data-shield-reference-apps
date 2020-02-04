#!/bin/bash

function install_sgx_driver {
    wget https://download.01.org/intel-sgx/sgx-linux/2.8/distro/rhel7.4-server/sgx_linux_x64_driver_2.6.0_51c4821.bin
    bash sgx_linux_x64_driver_2.6.0_51c4821.bin

    ls /dev/isgx >/dev/null 2>1  && echo "SGX driver installed" || echo "SGX driver installation failed"
}

install_sgx_driver
