#source ../setup/scripts/values.sh

ibmcloud cr image-rm ${CONTAINER_REGISTRY_PATH}/cloud-fund-bff
docker build -t ${CONTAINER_REGISTRY_PATH}/cloud-fund-bff . --no-cache
docker push ${CONTAINER_REGISTRY_PATH}/cloud-fund-bff