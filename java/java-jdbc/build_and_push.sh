ibmcloud cr image-rm registry.ng.bluemix.net/datashield-core/java-jdbc
docker build -t registry.ng.bluemix.net/datashield-core/java-jdbc .
docker push registry.ng.bluemix.net/datashield-core/java-jdbc
kubectl delete pod java-jdbc
kubectl create -f ./deployment/deployment.yml
