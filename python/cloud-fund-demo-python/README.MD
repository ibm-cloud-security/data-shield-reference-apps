# Cloud Fund Python Demo

# Setting up Cloud Fund Python demo

## Project Structure

* Backend: This project runs Python backend, which connects to a MongoDB instance.
    * image: `<registry_path>/cloud-fund-backend`
    * deployment: `cloud-fund-backend`
    * service: `cloud-fund-backend-service`
    
* Backend-for-frontend: This projects runs on nodejs. It serves as the backend for the frontend UI.
    * image: `<registry_path>/cloud-fund-bff`
    * deployment: `cloud-fund-bff`
    * service: `cloud-fund-bff-service`
 
* Frontend: The frontend runs on react - redux.
    * image: `<registry_path>/cloud-fund-frontend`
    * deployment: `cloud-fund-frontend`
    * service: `cloud-fund-frontend-service`

## Steps to install

_Make sure you have yq installed._

1. Populate values in `./setup/scripts/values.sh`. The set of values belong to the following categories:
    * SGX Cluster where we want to deploy this demo
    * Account and registry where we want to push/pull images to/from
    * Key protect instance information
    * Mongodb information
    * App ID configuration (a predefined instance is already populated)
    * App URLs (it is recommended to use defaults)
    
2. CD to `./setup/scripts` and execute`setup.sh`
3. Check for the following pods, services and ingress to be up and running:
    * cloud-fund-backend
    * cloud-fund-frontend
    * cloud-fund-bff
    * cloud-fund-backend-service
    * cloud-fund-bff-service
    * cloud-fund-frontend-service
    * cloud-fund-ingress
    
4. Once all pods are up and running, the demo app can be accessed through the ingress host. For example `<cluster_name>.<region>.containers.appdomain.cloud`.

_Make sure your App ID instance has the proper ingress URL defined as redirect URL, for example `https://<cluster_name>.<region>.containers.appdomain.cloud/*`. 
This can be found at the App ID instance configuration, under `Manage Identity Providers` -> `Authentication Settings` -> `Add web redirect URLs`._

## What does the `setup.sh` script run? (All scripts found under `./setup/scripts`)

1. Exports cluster configuration: `export_cluster_config.sh`
2. Logs in to ibmcloud and container registry `cloud_login.sh`
3. Builds and pushes to the registry, backend, backend-for-frontend and frontend images `build_and_push.sh`
4. Creates secrets, using the values from `values.sh`, `create_secrets.sh`
5. Deploys pods, services and ingress `deploy.sh`

## Useful scripts (Found under `./setup/scripts`)
    
* `cloud_login.sh` Logs to ibmcloud and container registry
* `create_secrets.sh` Creates secrets, based on defined values in `values.sh`
* `deploy.sh` Redeploys all pods, services and ingress for this demo
* `export_cluster_config.sh` Exports cluster configuration (basically connects to IKS cluster)
* `setup.sh` Does the main setup
* `use_sgx_backend.sh` Changes `cloud-fund-backend` to use SGX protected image 
* `use_unconverted_backend.sh` Changes `cloud-fund-backend` to use unconverted image
* `build_and_push.sh` Builds and pushes each one of the components to the registry specified in `values.sh`