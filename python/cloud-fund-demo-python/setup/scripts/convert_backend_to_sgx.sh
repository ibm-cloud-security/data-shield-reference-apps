source ./values.sh
source ./cloud_login.sh
source ./export_cluster_config.sh
kubectl delete pod cloud-fund-backend
token=`ibmcloud iam oauth-tokens | awk -F"Bearer " '{print $NF}'`
curl -H 'Content-Type: application/json' -d '{"inputImageName": '\"${CONTAINER_REGISTRY_PATH}/cloud-fund-backend\"', "outputImageName": '\"${CONTAINER_REGISTRY_PATH}/cloud-fund-backend-sgx\"', "rwDirs": ["/"] }'  -H "Authorization: Basic $token"  https://enclave-manager.${CLUSTER_INGRESS_SECRET}.${CLUSTER_REGION}.containers.appdomain.cloud/api/v1/tools/converter/convert-app
sleep 30
kubectl create -f ./../deployments/cloud-fund-backend-sgx.yml