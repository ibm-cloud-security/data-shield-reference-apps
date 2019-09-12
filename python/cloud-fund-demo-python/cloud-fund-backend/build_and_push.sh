#source ../setup/scripts/values.sh

ibmcloud cr image-rm ${CONTAINER_REGISTRY_PATH}/cloud-fund-backend
docker build -t ${CONTAINER_REGISTRY_PATH}/cloud-fund-backend . --no-cache
docker push ${CONTAINER_REGISTRY_PATH}/cloud-fund-backend