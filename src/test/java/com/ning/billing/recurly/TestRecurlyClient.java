/*
 * Copyright 2010-2012 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.recurly;

import com.ning.billing.recurly.model.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ning.billing.recurly.model.Plan.RecurlyUnitCurrency;

import static com.ning.billing.recurly.TestUtils.randomString;

public class TestRecurlyClient {

    private static final Logger log = LoggerFactory.getLogger(TestRecurlyClient.class);

    private RecurlyClient recurlyClient;

    @BeforeMethod(groups = "integration")
    public void setUp() throws Exception {
        final String apiKey = System.getProperty("killbill.payment.recurly.apiKey");
        if (apiKey == null) {
            Assert.fail("You need to set your Recurly api key to run integration tests: -Dkillbill.payment.recurly.apiKey=...");
        }

        recurlyClient = new RecurlyClient(apiKey);
        recurlyClient.open();
    }

    @AfterMethod(groups = "integration")
    public void tearDown() throws Exception {
        recurlyClient.close();
    }

    @Test(groups = "integration")
    public void testGetPageSize() throws Exception {
        System.setProperty("recurly.page.size", "");
        Assert.assertEquals(new Integer("20"), RecurlyClient.getPageSize());

        System.setProperty("recurly.page.size", "350");
        Assert.assertEquals(new Integer("350"), RecurlyClient.getPageSize());
    }

    @Test(groups = "integration")
    public void testGetPageSizeGetParam() throws Exception {
        System.setProperty("recurly.page.size", "");
        Assert.assertEquals("per_page=20", RecurlyClient.getPageSizeGetParam());

        System.setProperty("recurly.page.size", "350");
        Assert.assertEquals("per_page=350", RecurlyClient.getPageSizeGetParam());
    }

    @Test(groups = "integration")
    public void testCreateAccount() throws Exception {
        final Account accountData = new Account();
        accountData.setAccountCode(randomString());
        accountData.setEmail(randomString() + "@laposte.net");
        accountData.setFirstName(randomString());
        accountData.setLastName(randomString());
        accountData.setUsername(randomString());
        accountData.setAcceptLanguage("en_US");
        accountData.setCompanyName(randomString());

        final DateTime creationDateTime = new DateTime(DateTimeZone.UTC);
        final Account account = recurlyClient.createAccount(accountData);
        Assert.assertNotNull(account);
        Assert.assertEquals(accountData.getAccountCode(), account.getAccountCode());
        Assert.assertEquals(accountData.getEmail(), account.getEmail());
        Assert.assertEquals(accountData.getFirstName(), account.getFirstName());
        Assert.assertEquals(accountData.getLastName(), account.getLastName());
        Assert.assertEquals(accountData.getUsername(), account.getUsername());
        Assert.assertEquals(accountData.getAcceptLanguage(), account.getAcceptLanguage());
        Assert.assertEquals(accountData.getCompanyName(), account.getCompanyName());
        // Verify we can serialize date times
        Assert.assertEquals(Minutes.minutesBetween(account.getCreatedAt(), creationDateTime).getMinutes(), 0);
        log.info("Created account: {}", account.getAccountCode());

        final Accounts retrievedAccounts = recurlyClient.getAccounts();
        Assert.assertTrue(retrievedAccounts.size() > 0);

        final Account retrievedAccount = recurlyClient.getAccount(account.getAccountCode());
        Assert.assertEquals(retrievedAccount, account);

        final BillingInfo billingInfoData = new BillingInfo();
        billingInfoData.setFirstName(randomString());
        billingInfoData.setLastName(randomString());
        billingInfoData.setNumber("4111-1111-1111-1111");
        billingInfoData.setVerificationValue(123);
        billingInfoData.setMonth(11);
        billingInfoData.setYear(2015);
        billingInfoData.setAccount(account);

        final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
        Assert.assertNotNull(billingInfo);
        Assert.assertEquals(billingInfoData.getFirstName(), billingInfo.getFirstName());
        Assert.assertEquals(billingInfoData.getLastName(), billingInfo.getLastName());
        Assert.assertEquals(billingInfoData.getMonth(), billingInfo.getMonth());
        Assert.assertEquals(billingInfoData.getYear(), billingInfo.getYear());
        Assert.assertEquals(billingInfo.getCardType(), "Visa");
        log.info("Added billing info: {}", billingInfo.getCardType());

        final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
        Assert.assertEquals(retrievedBillingInfo, billingInfo);
    }

    @Test(groups = "integration")
    public void testCreatePlan() throws Exception {
        // Create a plan
        final Plan plan = new Plan();
        plan.setPlanCode(randomString());
        plan.setName(randomString());
        final RecurlyUnitCurrency unitCurrency = new RecurlyUnitCurrency();
        unitCurrency.setUnitAmountEUR(12);
        plan.setSetupFeeInCents(unitCurrency);
        plan.setUnitAmountInCents(unitCurrency);

        final DateTime creationDateTime = new DateTime(DateTimeZone.UTC);
        recurlyClient.createPlan(plan);

        Assert.assertTrue(recurlyClient.getPlans().size() > 0);

        final Plan retrievedPlan = recurlyClient.getPlan(plan.getPlanCode());
        Assert.assertEquals(retrievedPlan, plan);

        // Verify we can serialize date times
        Assert.assertEquals(Minutes.minutesBetween(retrievedPlan.getCreatedAt(), creationDateTime).getMinutes(), 0);
    }

    @Test(groups = "integration")
    public void testCreateSubscriptions() throws Exception {
        // Create a user
        final Account accountData = new Account();
        accountData.setAccountCode(randomString());
        accountData.setEmail(randomString() + "@laposte.net");
        accountData.setFirstName(randomString());
        accountData.setLastName(randomString());
        accountData.setUsername(randomString());
        accountData.setAcceptLanguage("en_US");

        final Account account = recurlyClient.createAccount(accountData);

        final BillingInfo billingInfoData = new BillingInfo();
        billingInfoData.setFirstName(randomString());
        billingInfoData.setLastName(randomString());
        billingInfoData.setNumber("4111-1111-1111-1111");
        billingInfoData.setVerificationValue(123);
        billingInfoData.setMonth(11);
        billingInfoData.setYear(2015);
        billingInfoData.setAccount(account);

        final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
        final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());

        accountData.setBillingInfo(billingInfo);

        // // Create a plan
        final Plan plan = new Plan();
        plan.setPlanCode(randomString());
        plan.setName(randomString());
        final RecurlyUnitCurrency unitCurrency = new RecurlyUnitCurrency();
        unitCurrency.setUnitAmountEUR(12);
        plan.setSetupFeeInCents(unitCurrency);
        plan.setUnitAmountInCents(unitCurrency);
        recurlyClient.createPlan(plan);


        /**
         * NOTE: can't test this as I can't create transactions due to an issue with the XML.
         * The subscription object needs a State field for containing info about the state of subscriptions
         * being returned from the reucrly api. But, when we want to create a new subscription that State
         * field gets added to the XML sent to the api - and State is an illegal field in tsi instance. The API
         * rejects the call. So we either have to have 2 objects (inbound subscription and outbound) __or__ we
         * some-how filter the fields on their way to serialisation through the JacksonXML XMLMapper.
         *
         * This is currently not yet implemented - and I have no idea how to implement it (yet) :)
         */
        // // Subscribe the user to the plan
        //Subscription subscriptionData = new Subscription();
        //subscriptionData.setPlanCode(plan.getPlanCode());
        //subscriptionData.setAccount(account);
        //subscriptionData.setCurrency("EUR");
        //subscriptionData.setState(null);
        //final Subscription subscription = recurlyClient.createSubscription(subscriptionData);

        // // Query the subscriptions for the user and check that
        // // returned values make sense

        //// Get subscriptions for me...
        //final String name = "1234";
        //Subscriptions subs = recurlyClient.getAccountSubscriptions(name);
        //
        //System.out.println("Subs for me.....");
        //for (Subscription s : subs ) {
        //    System.out.println(s.toString());
        //}
    }

    @Test(groups = "integration")
    public void testQueryTransactions() throws Exception {
        // Not sure... this unit test depends on being able to create transactions, so until
        // I can figure out how to do that I can't write this.
        // Temp fix - search for my transactions.
        String accountCode = "1234";
        final Transactions trans = recurlyClient.getAccountTransactions(accountCode);
        for (Transaction t : trans) {
            System.out.println("Transaction:: " + trans.toString());
        }



    }

}
