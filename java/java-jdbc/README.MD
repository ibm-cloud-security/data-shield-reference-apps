# Steps to run java-jdbc

## Run java-jdbc locally

1. `cd java-jdbc`
2. `docker build -t <java-jdbc-app-name> .`
3. Run a docker mysql container locally `docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=password -d mysql:latest`
4. Run java-jdbc container `docker run --env DB_USER='root' --env DB_PASSWORD='password' --env DB_URL='jdbc:mysql://some-mysql:3306?allowPublicKeyRetrieval=false' --env DB_DRIVER='com.mysql.jdbc.Driver' --env TRANSACTIONS='100' --link some-mysql:mysql <java-jdbc-app-name>`

## Deploy java-jdbc in cluster

_Make sure you are connected to a cluster, and **kubectl** commands work._
_Also, make sure you login to the container registry **ibmcloud cr login**_

1. `cd java-jdbc`
2. Execute `build_and_push.sh`, which builds this container, pushes it to a registry, and deploys the application to a container. Container registry can be modified by changing the registry in `build_and_push.sh`, and also changing the image to pull from inside `/deployment/deployment.yml`.
3. Check for the `java-jdbc` pod, which should be in `running` state after a minute or so. You can run `kubectl logs java-jdbc` to see the test output message.

## Convert java-jdbc application and deploy

1. Convert java-jdbc image, by using the the Enclave Manager UI or curl command. Make sure Memory size is set to **2GB** and Thread count to is set to **128**.
2. After conversion, execute `/deployment/sgx-deployment.yml`. Make sure the yml file is pointing to the correct converted image.
3. Check for the `java-jdbc-sgx` pod, which should be in `running` state after a minute or so. You can run `kubectl logs java-jdbc-sgx` to see the test output message.
