source ./values.sh
source ./export_cluster_config.sh
kubectl delete pod cloud-fund-backend
kubectl create -f ./../deployments/cloud-fund-backend-sgx.yml