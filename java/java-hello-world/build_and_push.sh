ibmcloud cr image-rm registry.ng.bluemix.net/datashield-core/java-hello-world
docker build -t registry.ng.bluemix.net/datashield-core/java-hello-world .
docker push registry.ng.bluemix.net/datashield-core/java-hello-world
kubectl delete pod java-hello-world
kubectl create -f ./deployment/deployment.yml
