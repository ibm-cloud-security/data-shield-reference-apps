# SGX Installer


## Introduction


## Chart details


## Prerequisites


## Resources required

* An SGX-enabled Kubernetes cluster. Depending on the type of cluster that you choose, the type of machine flavor differs. Be sure that you have the correct corresponding flavor.

  * Kubernetes Service available machine types: `mb2c.4x32` and `ms2c.4x32.1.9tb.ssd`, `mb3c.4x32`, `ms3c.4x32.1.9tb.ssd`. 


## Installing the chart

```
helm install sgx-installer .
```


### Verifying the chart
To verify that the drivers and PSW have been installed successfully, do `kubectl logs <name of installer pod>` and verify that installation was successful. 

### Uninstalling the chart

To remove the installer daemonset, run 
```
helm uninstall sgx-installer
```

To remove the SGX drivers and PSW from your machine, install the SGX uninstaller Helm chart.


## Documentation

