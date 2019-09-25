# Remote Attestation Reference Apps
## Getting root of trust certificate from Enclave Manager
```
kubectl port-forward svc/$(helm list | grep 'data-shield' | awk {'print $1'})-enclaveos-manager 9090 &
curl -ks https://localhost:9090/v1/zones | jq -r .certificate > enclave-manager-ca.crt
kill %kubectl
```
Make sure that enclave-manager-ca.crt is present in both the client and server directories before building each application.

## Building applications
Run build_and_push.sh in each of the server and client directories to create and push the unconverted versions of the applications. After conversion, run sgx-deploy.sh to deploy the applications on your cluster.