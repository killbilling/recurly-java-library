recurly-java-library
====================

Java library for Recurly, originally developed for [Kill Bill](http://killbill.io), an open-source subscription management and billing system.

Getting started
---------------

The library is distributed via [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.ning.billing%22%20AND%20a%3A%22recurly-java-library%22):

```
<dependency>
    <groupId>com.ning.billing</groupId>
    <artifactId>recurly-java-library</artifactId>
    <version>0.34.0</version>
</dependency>
```

The easiest way to get started is by looking at the code examples in [TestRecurlyClient.java](https://github.com/killbilling/recurly-java-library/blob/master/src/test/java/com/ning/billing/recurly/TestRecurlyClient.java).

Development
-----------

1. Make sure you can build the project by running the smoke tests: `mvn clean test`
2. Go to [recurly.com](http://recurly.com/) and create an account. This account will be used as a sandbox environment for testing.
3. In your Recurly account, click on **API Credentials** (bottom of the left menu), click the **Enable API Access** button and write down your API Key.
4. Verify the setup by running the recurly-java-library integration tests (make sure to update your API Key and Subdomain): `mvn clean test -Pintegration -Dkillbill.payment.recurly.apiKey=1234567689abcdef -Dkillbill.payment.recurly.subDomain=mycompany`
5. Go to your Recurly account, you should see some data (e.g. account created).
6. Congrats! You're all set!

Java properties
---------------

* Set `-Drecurly.debug=true` to output debug information in the info log file
* Set `-Drecurly.page.size=20` to configure the page size for Recurly API calls
* To run the tests, one can use `-Dkillbill.payment.recurly.currency=EUR` to override the default USD currency used
* You may optionally pass the TLS protocol used with the setting `-Dkillbill.payment.recurly.tlsProtocol`. Keep in mind that Recurly only supports `TLSv1.2` and above. This setting defaults to `TLSv1.2`.

Push notifications
------------------

See https://gist.github.com/dbathily/4433939 for an example on how to set it up.

Build
-----

To build the project, use maven:

    mvn clean install
