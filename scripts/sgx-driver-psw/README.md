# Intel SGX Driver and PSW Installer
These scripts install the Intel SGX driver and the Intel SGX Platform Software on SGX-enabled systems. 

The following operating systems are supported:
* Ubuntu 16.04.3
* Ubuntu 18.04
* Red Hat Enterprise Linux Server release 7.4

## Install on bare metal server
```
curl -fssl https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/install_sgx/install_sgx.sh | bash
```

## Uninstall on bare metal server 
```
curl -fssl https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/uninstall_sgx/uninstall_sgx.sh| bash
```

## Install on IKS clusters
Create a daemonset that installs the SGX drivers and the PSW
```
kubectl create -f https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/install_sgx/deployment_install_sgx_iks.yaml
```
You can remove the installer after it has finished running
```
kubectl delete daemonset sgx-installer
```

## Install on OpenShift clusters
Create a privileged security context constraint and a service account
```
curl -fssl https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/config_openshift/create_openshift_config.sh | bash
```

Create a daemonset that installs the SGX drivers and the PSW
```
curl -fssl https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/config_openshift/create_openshift_config.sh | bash
```
You can remove the installer after it has finished running
```
oc delete daemonset sgx-installer
```

You can also delete the security context constraint and service account created
```
oc delete scc sgx-admin
oc delete serviceaccount sgx-admin
```

## Uninstall on IKS clusters
Create a daemonset that uninstalls the SGX drivers and the PSW
```
kubectl create -f https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/uninstall_sgx/deployment_uninstall_sgx_iks.yaml
```

You can remove the uninstaller after it has finished running
```
kubectl delete daemonset sgx-uninstaller
```

## Uninstall on OpenShift clusters
Create a privileged security context constraint and a service account
```
curl -fssl https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/config_openshift/create_openshift_config.sh | bash
```
Create a daemonset that uninstalls the SGX drivers and the PSW
```
oc create -f https://raw.githubusercontent.com/ibm-cloud-security/data-shield-reference-apps/master/scripts/sgx-driver-psw/uninstall_sgx/deployment_uninstall_sgx_openshift.yaml
```

You can remove the uninstaller after it has finished running
```
oc delete daemonset sgx-uninstaller
```

You can also delete the security context constraint and service account created
```
oc delete scc sgx-admin
oc delete serviceaccount sgx-admin
```


