kubectl delete secret cloud-fund-environment-secrets
kubectl delete ingress cloud-fund-ingress
kubectl delete service cloud-fund-backend-service
kubectl delete service cloud-fund-bff-service
kubectl delete service cloud-fund-frontend-service
kubectl delete pod cloud-fund-backend
kubectl delete pod cloud-fund-bff
kubectl delete pod cloud-fund-frontend
kubectl delete NetworkPolicy cloud-fund-network-policy

sleep 60

kubectl create -f ./../deployments/cloud-fund-environment-secrets.yml
kubectl create -f ./../deployments/cloud-fund-backend.yml
kubectl create -f ./../deployments/cloud-fund-frontend.yml
kubectl create -f ./../deployments/cloud-fund-bff.yml
kubectl create -f ./../deployments/cloud-fund-backend-service.yml
kubectl create -f ./../deployments/cloud-fund-frontend-service.yml
kubectl create -f ./../deployments/cloud-fund-bff-service.yml
kubectl create -f ./../deployments/cloud-fund-ingress.yml
kubectl create -f ./../deployments/cloud-fund-network-policy.yml