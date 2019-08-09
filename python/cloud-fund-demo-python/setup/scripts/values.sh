
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
export CLUSTER_INGRESS="cloud-fund.${CLUSTER_NAME}.${CLUSTER_REGION}.containers.appdomain.cloud"

# Key Protect values
export KEY_PROTECT_INSTANCE=""
export KEY_PROTECT_MANAGEMENT_URL=""
export KEY_NAME=""

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
