ibmcloud cr image-rm registry.ng.bluemix.net/datashield-core/java-io
docker build -t registry.ng.bluemix.net/datashield-core/java-io .
docker push registry.ng.bluemix.net/datashield-core/java-io
kubectl delete pod java-io
kubectl create -f ./deployment/deployment.yml
