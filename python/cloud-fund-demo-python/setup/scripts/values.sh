# IBM Cloud account and registry where we want to push images to
export ACCOUNT_API_KEY=''
export ACCOUNT_REGION=''
#Account needs to have pull/push access to this container registry. Deployment files need to be updated with this path.
export CONTAINER_REGISTRY_PATH=''

# SGX cluster where we want to deploy
export CLUSTER_API_KEY="${ACCOUNT_API_KEY}"
export CLUSTER_RESOURCE_GROUP=''
export CLUSTER_REGION=''
export CLUSTER_NAME=''
export CLUSTER_INGRESS_SECRET=''
export CLUSTER_INGRESS="cloud-fund.${CLUSTER_INGRESS_SECRET}.${CLUSTER_REGION}.containers.appdomain.cloud"

# Key Protect values
export KEY_PROTECT_INSTANCE=""
export KEY_PROTECT_MANAGEMENT_URL=""
export KEY_NAME="cloud-fund-key"

#DBaaS values
export RAW_DB_CONN=''
export RAW_DB_PASSWORD=''
export RAW_DB_USERNAME=''
export RAW_DB_NAME=''
export ENCODED_CERT=''

# App ID configuration
export APP_ID_CLIENT_ID=""
export APP_ID_CLIENT_SECRET=""
export APP_ID_TENANT_ID=""
export APP_ID_REGION=""
# These do not need to be touched, since they take the App Id configuration from above
export RAW_APP_ID_CONFIG='{"clientId": "'${APP_ID_CLIENT_ID}'", "oauthServerUrl": "https://'${APP_ID_REGION}'.appid.cloud.ibm.com/oauth/v4/'${APP_ID_TENANT_ID}'", "profilesUrl": "https://'${APP_ID_REGION}'.appid.cloud.ibm.com", "secret": "'${APP_ID_CLIENT_SECRET}'", "tenantId": "'${APP_ID_TENANT_ID}'"}'
export RAW_APP_ID_SIGN_UP_URL="https://${APP_ID_REGION}.appid.cloud.ibm.com/oauth/v4/${APP_ID_TENANT_ID}/authorization?client_id=${APP_ID_CLIENT_ID}&response_type=sign_up&redirect_uri=https://${CLUSTER_INGRESS}/ibm/bluemix/appid/callback&scope=appid_default"

# App URLs. If possible, do not touch them. If these need to be changed, then /deployments/cloud-fund-ingress.yml needs to be changed.
export RAW_BACKEND_URL='http://cloud-fund-backend-service:8500'
export RAW_BFF_URL="https://${CLUSTER_INGRESS}"
export RAW_FRONTEND_URL="https://${CLUSTER_INGRESS}"




### Cert Manager implementation ###

## In addition, Cert manager can be used with this application. For that, we would need to do the following steps:

# 1. Create Certificate Manager instance
# ibmcloud resource service-instance-create "${resource_group}_certs" cloudcerts free $region -g $resource_group

# 2. Getting instance id, which is basically the entire crn
# export cert_manager_instance_id=$(ibmcloud resource service-instance '${resource_group}_certs' --output json | jq -r .[0].crn)

# 3. URL Parsing crn value
# export cert_manager_instance_id2=$(python3 -c "import urllib.parse, sys; print(urllib.parse.quote_plus(sys.argv[1]))"  "$cert_manager_instance_id")

# 4. Call to create payload.json
# python3 "create_payload_from_pem_and_key.py"

# 5. Uploading certificate for frontend
#export crn=$(curl -X POST -H "Content-Type: application/json" -H "authorization: $iam_token" -d @cloudfund.json https://us-south.certificate-manager.cloud.ibm.com/api/v3/${cert_manager_instance_id2}/certificates/import | jq -r ._id)

# 6. Uploading certificate for bff
#export crn2=$(curl -X POST -H "Content-Type: application/json" -H "authorization: $iam_token" -d @bff.cloudfund.json https://us-south.certificate-manager.cloud.ibm.com/api/v3/${cert_manager_instance_id2}/certificates/import | jq -r ._id)


## Creating alb secret
# https://cloud.ibm.com/docs/containers?topic=containers-ingress

# 1. Login to ibmcloud

# 2. Export cluster configuration

# 3. Create secret for frontend certificate
# ibmcloud ks alb-cert-deploy --secret-name ${alb_secret_name} --cluster ${cluster} --cert-crn ${crn}

#4. Creating secret for bff frontend certificate
# ibmcloud ks alb-cert-deploy --secret-name ${alb_secret_name_bff} --cluster ${cluster} --cert-crn ${crn2}


