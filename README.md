recurly-java-library
====================

Java library for Recurly, originally developed for [Killbilling](http://killbilling.org), an open-source subscription management and billing system.

Getting started
---------------

1. Make sure you can build the project by running the smoke tests: `mvn clean test`
2. Go to [recurly.com](http://recurly.com/) and create an account. This account will be used as a sandbox environment for testing.
3. In your Recurly account, click on **API Credentials** (bottom of the left menu), click the **Enable API Access** button and write down your API Key.
4. Verify the setup by running the recurly-java-library integration tests (make sure to update your API Key): `mvn clean test -Pintegration -Dkillbill.payment.recurly.apiKey=1234567689abcdef`
5. Go to your Recurly account, you should see some data (e.g. account created).
6. Congrats! You're all set!

Build
-----

To build the project, use maven:

    mvn clean install
