# Steps to run Java Hello World

## Run hello world locally

1. `cd java-hello-world`
2. `docker build -t <java-app-name> .`
3. `docker run <java-app-name>`

## Deploy java-hello-world in cluster

_Make sure you are connected to a cluster, and **kubectl** commands work._
_Also, make sure you login to the container registry **ibmcloud cr login**_

1. `cd java-hello-world`
2. Execute `build_and_push.sh`, which builds this container, and pushes it to a registry, and deploys the application to a container. Container registry can be modified, by changing the registry in `build_and_push.sh` and also changing the image to pull from inside `/deployment/deployment.yml`
3. Check for the `java-hello-world` pod, which should be in `running` state after a minute or so. You can run `kubectl logs java-hello-world` to see the _Hello World_ message.

## Convert java-hello-world application and deploy

1. Convert java-hello-world image, by using the the Enclave Manager UI or curl command. Make sure Memory size is set to **2GB** and Thread count to is set to **128**.
2. After conversion, execute `/deployment/sgx-deployment.yml`. Make sure the yml file is pointing to the correct converted image.
3. Check for the `java-hello-world-sgx` pod, which should be in `running` state after a minute or so. You can run `kubectl logs java-hello-world-sgx` to see the _Hello World_ message.
