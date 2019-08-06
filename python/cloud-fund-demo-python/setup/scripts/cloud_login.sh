#source values.sh

if [[ ${ACCOUNT_RESOURCE_GROUP} = '' ]]
then export ACCOUNT_RESOURCE_GROUP='';
else export ACCOUNT_RESOURCE_GROUP="-g ${ACCOUNT_RESOURCE_GROUP}"
fi

echo "no" | ibmcloud login -a cloud.ibm.com --apikey ${ACCOUNT_API_KEY} ${ACCOUNT_RESOURCE_GROUP} -r ${ACCOUNT_REGION}
ibmcloud cr login