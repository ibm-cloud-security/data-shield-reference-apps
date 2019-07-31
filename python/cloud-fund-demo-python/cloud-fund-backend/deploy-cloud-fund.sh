kubectl delete pod cloud-fund-backend
sleep 5
kubectl create -f ../deployment/cloud-fund/cloud-fund-backend.yml
sleep 10
kubectl get pods