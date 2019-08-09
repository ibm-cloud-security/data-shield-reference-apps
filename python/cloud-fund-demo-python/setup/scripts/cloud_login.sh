#source values.sh

echo "no" | ibmcloud login -a cloud.ibm.com --apikey ${ACCOUNT_API_KEY} -r ${ACCOUNT_REGION}
ibmcloud cr login