if [ -z "$API_KEY" ]; then
  mvn clean test
else
  mvn clean test -Pintegration -Dkillbill.payment.recurly.apiKey=$API_KEY -Dkillbill.payment.recurly.subDomain=$SUBDOMAIN
fi
