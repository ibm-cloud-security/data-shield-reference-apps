#!/bin/bash

function install_sgx_driver  {
    os=$(awk -F= '/^PRETTY_NAME/{print $2}' /etc/os-release)
    if [[ $os =~ "Ubuntu 18" ]]; then
        sudo apt-get update
        sudo apt-get install -y build-essential
        sudo apt-get install -y linux-headers-$(uname -r)
        sudo apt-get install kmod

        wget https://download.01.org/intel-sgx/sgx-linux/2.13.3/distro/ubuntu18.04-server/sgx_linux_x64_driver_2.11.0_2d2b795.bin
    elif [[ $os =~ "Ubuntu 16" ]]; then
        sudo apt-get update
        sudo apt-get install -y build-essential
        sudo apt-get install -y linux-headers-$(uname -r)

        wget https://download.01.org/intel-sgx/sgx-linux/2.13.3/distro/ubuntu16.04-server/sgx_linux_x64_driver_2.11.0_2d2b795.bin
    elif [[ $os =~ "Red Hat" ]]; then
        sudo yum install -y kernel-devel
        sudo yum install -y gcc
        wget https://download.01.org/intel-sgx/sgx-linux/2.13.3/distro/rhel7.6-server/sgx_linux_x64_driver_2.11.0_2d2b795.bin
    else
        echo "$os is not supported."
        exit 1
    fi

    bash sgx_linux_x64_driver_2.11.0_2d2b795.bin
}

function install_psw {
    psw_installed=false
    os=$(awk -F= '/^PRETTY_NAME/{print $2}' /etc/os-release)
    if [[ $os =~ "Ubuntu" ]]; then
        # Install tools
        sudo apt-get install -y libssl-dev libcurl4-openssl-dev libprotobuf-dev

        # Install PSW 
        if [[ $os =~ "Ubuntu 18" ]]; then
            echo 'deb [arch=amd64] https://download.01.org/intel-sgx/sgx_repo/ubuntu bionic main' | sudo tee /etc/apt/sources.list.d/intel-sgx.list
            wget -qO - https://download.01.org/intel-sgx/sgx_repo/ubuntu/intel-sgx-deb.key | sudo apt-key add -
        elif [[ $os =~ "Ubuntu 16" ]]; then
            echo 'deb [arch=amd64] https://download.01.org/intel-sgx/sgx_repo/ubuntu xenial main' | sudo tee /etc/apt/sources.list.d/intelsgx.list
            wget -qO - https://download.01.org/intel-sgx/sgx_repo/ubuntu/intel-sgx-deb.key | sudo apt-key add -
        fi

        sudo apt-get update -y
        sudo apt-get install -y libsgx-launch libsgx-urts
        sudo apt-get install -y libsgx-epid libsgx-urts
        sudo apt-get install -y libsgx-quote-ex libsgx-urts

        psw_installed=true

    elif [[ $os =~ "Red Hat" ]]; then
        yum install -y openssl-devel libcurl-devel protobufdevel yum-utils
        cd /
        wget https://download.01.org/intel-sgx/sgx-linux/2.13.3/distro/rhel7.6-server/sgx_rpm_local_repo.tgz
        tar -xvf sgx_rpm_local_repo.tgz
        yum-config-manager --add-repo file:///sgx_rpm_local_repo
        yum --nogpgcheck install -y libsgx-launch libsgx-urts
        yum --nogpgcheck install -y libsgx-epid libsgx-urts
        yum --nogpgcheck install -y libsgx-quote-ex libsgx-urts

        psw_installed=true
    fi

}

function check_install {
    ls /dev/isgx >/dev/null 2>1  && echo "SGX driver installed" || echo "SGX driver NOT installed"
    if [[ "$psw_installed" = true ]]; then
        echo "PSW installed"
    fi
}

install_sgx_driver
install_psw
check_install

