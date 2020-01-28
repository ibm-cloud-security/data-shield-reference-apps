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
        sudo apt-get install -y git
        sudo apt-get install -y build-essential
        sudo apt-get install -y linux-headers-$(uname -r)

        cd $(mktemp -d)
        rm -rf linux-sgx-driver
        git clone https://github.com/intel/linux-sgx-driver 
        cd linux-sgx-driver/
        make 

        sudo mkdir -p "/lib/modules/"`uname -r`"/kernel/drivers/intel/sgx"    
        sudo cp -f isgx.ko "/lib/modules/"`uname -r`"/kernel/drivers/intel/sgx"    
        sudo sh -c "cat /etc/modules | grep -Fxq isgx || echo isgx >> /etc/modules"    
        sudo /sbin/depmod
        sudo /sbin/modprobe isgx

        cd ..

        ls /dev/isgx >/dev/null 2>1  && echo "SGX driver installed" || echo "SGX driver installation failed"
    fi
}

check_driver
install_sgx_driver
