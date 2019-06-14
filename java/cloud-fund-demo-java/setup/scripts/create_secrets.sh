#source values.sh

export BACKEND_URL=$(echo -n $RAW_BACKEND_URL | base64)
export BFF_URL=$(echo -n $RAW_BFF_URL | base64)
export FRONTEND_URL=$(echo -n $RAW_FRONTEND_URL | base64)
export APP_ID_CONFIG=$(echo -n $RAW_APP_ID_CONFIG | base64)
export APP_ID_SIGN_UP_URL=$(echo -n $RAW_APP_ID_SIGN_UP_URL | base64)
export ENC_KEY=$(echo -n $ENCRYPTION_KEY | base64)
export SALT_VALUE=$(echo -n $SALT | base64)

cat ./../deployments/cloud-fund-environment-secrets_temp.yml |  yq '.data.backend_url=env.BACKEND_URL | .data.bff_url=env.BFF_URL
| .data.frontend_url=env.FRONTEND_URL | .data.app_id_credentials=env.APP_ID_CONFIG | .data.app_id_sign_in=env.APP_ID_SIGN_UP_URL
| .data.encryption_key=env.ENC_KEY | .data.salt_value=env.SALT_VALUE' -y > ./../deployments/cloud-fund-environment-secrets.yml

cat ./../deployments/cloud-fund-ingress_temp.yml |  yq '.spec.rules[0].host=env.CLUSTER_INGRESS | .spec.tls[0].hosts[0]=env.CLUSTER_INGRESS  | .spec.tls[0].secretName=env.CLUSTER_NAME' -y > ./../deployments/cloud-fund-ingress.yml