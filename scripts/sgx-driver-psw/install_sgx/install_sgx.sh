#!/bin/bash

function install_sgx_driver  {
    os=$(awk -F= '/^PRETTY_NAME/{print $2}' /etc/os-release)
    if [[ $os =~ "Ubuntu 18" ]]; then
        sudo apt-get update
        sudo apt-get install -y build-essential
        sudo apt-get install -y linux-headers-$(uname -r)

        wget https://download.01.org/intel-sgx/sgx-linux/2.8/distro/ubuntu18.04-server/sgx_linux_x64_driver_2.6.0_51c4821.bin
    elif [[ $os =~ "Ubuntu 16" ]]; then
        sudo apt-get update
        sudo apt-get install -y build-essential
        sudo apt-get install -y linux-headers-$(uname -r)

        wget https://download.01.org/intel-sgx/sgx-linux/2.8/distro/ubuntu16.04-server/sgx_linux_x64_driver_2.6.0_51c4821.bin
    elif [[ $os =~ "Red Hat" ]]; then
        sudo yum install -y kernel-devel
        wget https://download.01.org/intel-sgx/sgx-linux/2.8/distro/rhel7.4-server/sgx_linux_x64_driver_2.6.0_51c4821.bin
    else
        echo "$os is not supported."
        exit 1
    fi

    bash sgx_linux_x64_driver_2.6.0_51c4821.bin
}

function install_psw {
    os=$(awk -F= '/^PRETTY_NAME/{print $2}' /etc/os-release)
    echo $os
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

        echo "PSW installed"

    elif [[ $os =~ "Red Hat" ]]; then
        yum install -y openssl-devel libcurl-devel protobufdevel yum-utils
        wget https://download.01.org/intel-sgx/latest/linux-latest/distro/rhel7.4-server/sgx_rpm_local_repo.tgz
        tar -xvf sgx_rpm_local_repo.tgz
        yum-config-manager --add-repo file:///sgx_rpm_local_repo
        yum --nogpgcheck install libsgx-launch libsgx-urts
        yum --nogpgcheck install libsgx-epid libsgx-urts
        yum --nogpgcheck install libsgx-quote-ex libsgx-urts

        echo "PSW installed"
    fi

}

install_sgx_driver
install_psw
