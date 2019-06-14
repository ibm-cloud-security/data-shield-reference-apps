# SGX cluster where we want to deploy
export CLUSTER_API_KEY='gz4HWHvRyHEvyT8zyx8NvCzM8K55il_k10Z1dGXGSGms'
export CLUSTER_RESOURCE_GROUP=''
export CLUSTER_REGION='us-south'
export CLUSTER_NAME='data-shield-ga-test'
export CLUSTER_INGRESS="${CLUSTER_NAME}.${CLUSTER_REGION}.containers.appdomain.cloud"

# Encryption secret key (used for storing credit card info)
export ENCRYPTION_KEY='defaultKey'
export SALT='defaultValue'

# Account and registry where we want to push images
export ACCOUNT_API_KEY='gz4HWHvRyHEvyT8zyx8NvCzM8K55il_k10Z1dGXGSGms'
export ACCOUNT_RESOURCE_GROUP=''
export ACCOUNT_REGION='us-south'
#Account needs to have pull/push access to this container registry
export CONTAINER_REGISTRY_PATH='us.icr.io/datashield-core'


# App ID configuration
export APP_ID_CLIENT_ID="db977581-bd68-42eb-8de1-85036dba4401"
export APP_ID_CLIENT_SECRET="YWRlNmJmNzYtOGMwOC00MDFhLTkyZDktMWQ0ZmQwNTFlOTA2"
export APP_ID_TENANT_ID="cf2e1045-f130-4a92-80eb-0e15c509e5e6"
export APP_ID_REGION="us-south"
# These do not need to be touched, since they take the App Id configuration from above
export RAW_APP_ID_CONFIG='{"clientId": "'${APP_ID_CLIENT_ID}'", "oauthServerUrl": "https://'${APP_ID_REGION}'.appid.cloud.ibm.com/oauth/v4/'${APP_ID_TENANT_ID}'", "profilesUrl": "https://'${APP_ID_REGION}'.appid.cloud.ibm.com", "secret": "'${APP_ID_CLIENT_SECRET}'", "tenantId": "'${APP_ID_TENANT_ID}'"}'
export RAW_APP_ID_SIGN_UP_URL="https://${APP_ID_REGION}.appid.cloud.ibm.com/oauth/v4/${APP_ID_TENANT_ID}/authorization?client_id=${APP_ID_CLIENT_ID}&response_type=sign_up&redirect_uri=https://${CLUSTER_INGRESS}/ibm/bluemix/appid/callback&scope=appid_default"


# App URLs. If possible, do not touch them. If these need to be changed, then /deployments/cloud-fund-ingress.yml needs to be changed.
export RAW_BACKEND_URL='http://cloud-fund-backend-service:8080'
export RAW_BFF_URL="https://${CLUSTER_INGRESS}"
export RAW_FRONTEND_URL="https://${CLUSTER_INGRESS}"