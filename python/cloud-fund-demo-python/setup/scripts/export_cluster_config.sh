#source values.sh

if [[ ${CLUSTER_RESOURCE_GROUP} = '' ]]
then export CLUSTER_RESOURCE_GROUP='';
else export CLUSTER_RESOURCE_GROUP="-g ${CLUSTER_RESOURCE_GROUP}"
fi

echo "no" | ibmcloud login -a cloud.ibm.com --apikey ${CLUSTER_API_KEY} ${CLUSTER_RESOURCE_GROUP} -r ${CLUSTER_REGION}

#Export cluster configuration
`ibmcloud cs cluster-config ${CLUSTER_NAME} | awk '/export/{print}'`

kubectl get pods