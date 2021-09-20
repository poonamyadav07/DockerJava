source /opt/env.txt
echo "MSI_SECRET=${MSI_SECRET}"
echo "MSI_ENDPOINT=${MSI_ENDPOINT}"
echo "KEYVAULT_NAME=${KEYVAULT_NAME}"

access_token=$(curl -v  -H X-IDENTITY-HEADER:${MSI_SECRET} "${MSI_ENDPOINT}?resource=https://vault.azure.net&api-version=2019-08-01" | jq --raw-output '.access_token')

echo "access_token=${access_token}"

dbname=$(curl -ks --retry 5 https://${KEYVAULT_NAME}.vault.azure.net/secrets/${dbnameM10}?api-version=2016-10-01 -H "Authorization: Bearer $access_token" | jq --raw-output -r '.value')

echo "dburl=${dbname}"

echo "-------------------Running App service-------------"

java -jar app.jar
