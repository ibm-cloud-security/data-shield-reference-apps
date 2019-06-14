# Steps to run java-io

## Run java-io locally

1. `cd java-io`
2. `docker build -t <java-io-app-name> .`
3. Set `KB_SIZE` value to "1" for example. This means the file to be used will be approximately of 1KB.
4. Run java container `docker run <java-io-app-name>`

## Deploy java-io in cluster

_Make sure you are connected to a cluster, and **kubectl** commands work._
_Also, make sure you login to the container registry **ibmcloud cr login**_

1. `cd java-io`
2. Execute `build_and_push.sh`, which builds this container, pushes it to a registry, and deploys the application to a container. Container registry can be modified by changing the registry in `build_and_push.sh`, and also changing the image to pull from inside `/deployment/deployment.yml`.
3. Check for the `java-io` pod, which should be in `running` state after a minute or so. You can run `kubectl logs java-io` to see the test output message.

## Convert java-io application and deploy

1. Convert java-io image, by using the the Enclave Manager UI or curl command. Make sure Memory size is set to **2GB** and Thread count to is set to **128**.
2. After conversion, execute `/deployment/sgx-deployment.yml`. Make sure the yml file is pointing to the correct converted image.
3. Check for the `java-io-sgx` pod, which should be in `running` state after a minute or so. You can run `kubectl logs java-io-sgx` to see the test output message.
