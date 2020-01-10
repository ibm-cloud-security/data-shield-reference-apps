source values.sh

export DBCONN=$(echo -n $RAW_DB_CONN| base64)
export PASSWORD=$(echo -n $RAW_DB_PASSWORD | base64)
export USERNAME=$(echo -n $RAW_DB_USERNAME | base64)
export DB_NAME=$(echo -n $RAW_DB_NAME | base64)
export BFF_URL=$(echo -n $RAW_BFF_URL | base64)
export BACKEND_URL=$(echo -n $RAW_BACKEND_URL | base64)
export FRONTEND_URL=$(echo -n $RAW_FRONTEND_URL | base64)
export API_KEY=$(echo -n $ACCOUNT_API_KEY | base64)
export APP_ID_CONFIG=$(echo -n $RAW_APP_ID_CONFIG | base64)
export certpem=$(echo -n $ENCODED_CERT)
export APP_ID_SIGN_UP_URL=$(echo -n $RAW_APP_ID_SIGN_UP_URL | base64)

if [[ ${ACCOUNT_RESOURCE_GROUP} = '' ]]
then export ACCOUNT_RESOURCE_GROUP='';
else export ACCOUNT_RESOURCE_GROUP="-g ${ACCOUNT_RESOURCE_GROUP}"
fi

# Key Protect instance GUID
export GUID=$(ibmcloud resource service-instance "${KEY_PROTECT_INSTANCE}" ${ACCOUNT_RESOURCE_GROUP} --output JSON | jq -r .[0].guid)
#Get iam token
IAM_TOKEN=$(ibmcloud iam oauth-tokens | awk {'print $3" "$4'})
#Get crk_id
export CRK=$(curl -X GET ${KEY_PROTECT_MANAGEMENT_URL} --header "Authorization: $IAM_TOKEN" --header "Bluemix-Instance: ${GUID}" --header "Content-Type: application/vnd.ibm.kms.key+json"   | jq -r '.resources[0] | select(.name=="'"${KEY_NAME}"'") | .id')
export CRK_ID=$(echo -n $CRK | base64)
export KP_INSTANCE_ID=$(echo -n $GUID | base64)
export KMS_URL_SECRET=$(echo -n $KEY_PROTECT_MANAGEMENT_URL | base64)
cat ./../deployments/cloud-fund-environment-secrets_temp.yml |  yq '.data.dbconn=env.DBCONN | .data.password=env.PASSWORD
| .data.db_name=env.DB_NAME | .data.username=env.USERNAME | .data.bff_url=env.BFF_URL | .data.backend_url=env.BACKEND_URL
| .data.frontend_url=env.FRONTEND_URL | .data.kp_instance_id=env.KP_INSTANCE_ID | .data.crk_id=env.CRK_ID | .data.API_KEY=env.API_KEY
| .data.app_id_credentials=env.APP_ID_CONFIG | .data.app_id_sign_in=env.APP_ID_SIGN_UP_URL | .data.kms_url_secret=env.KMS_URL_SECRET | .data.cert_pem=env.certpem' -y > ./../deployments/cloud-fund-environment-secrets.yml

cat ./../deployments/cloud-fund-ingress_temp.yml |  yq '.spec.rules[0].host=env.CLUSTER_INGRESS | .spec.tls[0].hosts[0]=env.CLUSTER_INGRESS  | .spec.tls[0].secretName=env.CLUSTER_INGRESS_SECRET' -y > ./../deployments/cloud-fund-ingress.yml
