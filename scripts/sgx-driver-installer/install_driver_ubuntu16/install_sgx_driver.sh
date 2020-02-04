#!/bin/bash

function check_driver {
    if [[ ! -e /sys/module/isgx/version ]] ; then
        install_driver=true
    else 
        install_driver=false
        echo "SGX driver already installed."
        if [[ ! -e /dev/isgx ]] ; then
            echo "SGX driver is installed but no SGX device - SGX may not be enabled"
            exit 1
        fi
    fi
}

function install_sgx_driver {
    if [[ $install_driver == true ]] ; then
        sudo apt-get update
        sudo apt-get install -y build-essential
        sudo apt-get install -y linux-headers-$(uname -r)

        wget https://download.01.org/intel-sgx/sgx-linux/2.8/distro/ubuntu16.04-server/sgx_linux_x64_driver_2.6.0_51c4821.bin
        chmod +x sgx_linux_x64_driver_2.6.0_51c4821.bin
        ./sgx_linux_x64_driver_2.6.0_51c4821.bin

        ls /dev/isgx >/dev/null 2>1  && echo "SGX driver installed" || echo "SGX driver installation failed"
    fi
}

#check_driver
install_driver=true
install_sgx_driver
