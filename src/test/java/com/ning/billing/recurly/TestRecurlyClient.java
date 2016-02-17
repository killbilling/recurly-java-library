/*
 * Copyright 2010-2014 Ning, Inc.
 * Copyright 2014-2015 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ning.billing.recurly.model.Account;
import com.ning.billing.recurly.model.Accounts;
import com.ning.billing.recurly.model.AddOn;
import com.ning.billing.recurly.model.AddOns;
import com.ning.billing.recurly.model.Adjustment;
import com.ning.billing.recurly.model.Adjustments;
import com.ning.billing.recurly.model.BillingInfo;
import com.ning.billing.recurly.model.Coupon;
import com.ning.billing.recurly.model.Coupons;
import com.ning.billing.recurly.model.Invoice;
import com.ning.billing.recurly.model.Invoices;
import com.ning.billing.recurly.model.Plan;
import com.ning.billing.recurly.model.Redemption;
import com.ning.billing.recurly.model.RefundOption;
import com.ning.billing.recurly.model.Subscription;
import com.ning.billing.recurly.model.SubscriptionAddOns;
import com.ning.billing.recurly.model.SubscriptionUpdate;
import com.ning.billing.recurly.model.SubscriptionNotes;
import com.ning.billing.recurly.model.Subscriptions;
import com.ning.billing.recurly.model.Transaction;
import com.ning.billing.recurly.model.Transactions;

public class TestRecurlyClient {

    public static final String RECURLY_PAGE_SIZE = "recurly.page.size";
    public static final String KILLBILL_PAYMENT_RECURLY_API_KEY = "killbill.payment.recurly.apiKey";
    public static final String KILLBILL_PAYMENT_RECURLY_SUBDOMAIN = "killbill.payment.recurly.subDomain";
    public static final String KILLBILL_PAYMENT_RECURLY_DEFAULT_CURRENCY_KEY = "killbill.payment.recurly.currency";

    // Default to USD for all tests, which is expected to be supported by Recurly by default
    // Multi Currency Support is an enterprise add-on
    private static final String CURRENCY = System.getProperty(KILLBILL_PAYMENT_RECURLY_DEFAULT_CURRENCY_KEY, "USD");

    private RecurlyClient recurlyClient;

    @BeforeMethod(groups = {"integration", "enterprise"})
    public void setUp() throws Exception {
        final String apiKey = System.getProperty(KILLBILL_PAYMENT_RECURLY_API_KEY);
        String subDomainTemp = System.getProperty(KILLBILL_PAYMENT_RECURLY_SUBDOMAIN);
        if (apiKey == null) {
            Assert.fail("You need to set your Recurly api key to run integration tests:" +
                        " -Dkillbill.payment.recurly.apiKey=...");
        }
        
        if (subDomainTemp == null) {
          subDomainTemp = "api";
        }
        
        final String subDomain = subDomainTemp;

        recurlyClient = new RecurlyClient(apiKey, subDomain);
        recurlyClient.open();
    }

    @AfterMethod(groups = {"integration", "enterprise"})
    public void tearDown() throws Exception {
        recurlyClient.close();
    }

    @Test(groups = "integration", description = "See https://github.com/killbilling/recurly-java-library/issues/21")
    public void testGetEmptySubscriptions() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);

            final Subscriptions subs = recurlyClient.getAccountSubscriptions(accountData.getAccountCode(), "active");
            Assert.assertEquals(subs.size(), 0);
        } finally {
            // Clear up the BillingInfo
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration", description = "See https://github.com/killbilling/recurly-java-library/issues/23")
    public void testRemoveSubscriptionAddons() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);

            // Create a plan with addons
            final Plan plan = recurlyClient.createPlan(planData);
            final List<AddOn> addons = new ArrayList<AddOn>();
            final int nbAddOns = 5;
            for (int i = 0; i < nbAddOns; i++) {
                final AddOn addOn = TestUtils.createRandomAddOn(CURRENCY);
                final AddOn addOnRecurly = recurlyClient.createPlanAddOn(plan.getPlanCode(), addOn);
                addons.add(addOnRecurly);
            }

            // Create a subscription with addons
            final Subscription subscriptionDataWithAddons = TestUtils.createRandomSubscription(CURRENCY, plan, accountData, addons);
            final Subscription subscriptionWithAddons = recurlyClient.createSubscription(subscriptionDataWithAddons);
            Assert.assertEquals(subscriptionWithAddons.getAddOns().size(), nbAddOns);
            for (int i = 0; i < nbAddOns; i++) {
                Assert.assertEquals(subscriptionWithAddons.getAddOns().get(i).getAddOnCode(), addons.get(i).getAddOnCode());
            }

            // Fetch the corresponding invoice
            final Invoice subInvoice = subscriptionWithAddons.getInvoice();
            Assert.assertNotNull(subInvoice);
            
            // Refetch the invoice using the getInvoice method
            final int invoiceID = subInvoice.getInvoiceNumber();
            final Invoice gotInvoice = recurlyClient.getInvoice(invoiceID);
            
            Assert.assertEquals(subInvoice,gotInvoice);
            
            // Remove all addons
            final SubscriptionUpdate subscriptionUpdate = new SubscriptionUpdate();
            subscriptionUpdate.setAddOns(new SubscriptionAddOns());
            final Subscription subscriptionWithAddons1 = recurlyClient.updateSubscription(subscriptionWithAddons.getUuid(), subscriptionUpdate);
            Assert.assertTrue(subscriptionWithAddons1.getAddOns().isEmpty());

            // Add them again
            final SubscriptionUpdate subscriptionUpdate1 = new SubscriptionUpdate();
            final SubscriptionAddOns newAddons = new SubscriptionAddOns();
            newAddons.addAll(subscriptionDataWithAddons.getAddOns());
            subscriptionUpdate1.setAddOns(newAddons);
            final Subscription subscriptionWithAddons2 = recurlyClient.updateSubscription(subscriptionWithAddons.getUuid(), subscriptionUpdate1);
            Assert.assertEquals(subscriptionWithAddons2.getAddOns().size(), nbAddOns);
            for (int i = 0; i < nbAddOns; i++) {
                Assert.assertEquals(subscriptionWithAddons2.getAddOns().get(i).getAddOnCode(), addons.get(i).getAddOnCode());
            }
        } finally {
            // Clear up the BillingInfo
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testGetPageSize() throws Exception {
        System.setProperty(RECURLY_PAGE_SIZE, "");
        Assert.assertEquals(new Integer("20"), RecurlyClient.getPageSize());
        System.setProperty(RECURLY_PAGE_SIZE, "350");
        Assert.assertEquals(new Integer("350"), RecurlyClient.getPageSize());
    }

    @Test(groups = "integration")
    public void testGetPageSizeGetParam() throws Exception {
        System.setProperty(RECURLY_PAGE_SIZE, "");
        Assert.assertEquals("per_page=20", RecurlyClient.getPageSizeGetParam());
        System.setProperty(RECURLY_PAGE_SIZE, "350");
        Assert.assertEquals("per_page=350", RecurlyClient.getPageSizeGetParam());
    }

    @Test(groups = "integration")
    public void testGetCoupons() throws Exception {
        final Coupons retrievedCoupons = recurlyClient.getCoupons();
        Assert.assertTrue(retrievedCoupons.size() >= 0);
    }

    @Test(groups = "integration")
    public void testGetAdjustments() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);

            // Create a plan
            final Plan plan = recurlyClient.createPlan(planData);

            // Subscribe the user to the plan
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);

            //Add some adjustments to the account's open invoice
            final Adjustment adjustment = new Adjustment();
            adjustment.setCurrency("USD");
            adjustment.setUnitAmountInCents("100");
            adjustment.setDescription("A description of an account adjustment");

            //Use an "accounting code" for one of the adjustments
            String adjustmentAccountCode = "example account code";
            final Adjustment adjustmentWithCode = new Adjustment();
            adjustmentWithCode.setAccountingCode(adjustmentAccountCode);
            adjustmentWithCode.setCurrency("USD");
            adjustmentWithCode.setUnitAmountInCents("200");
            adjustmentWithCode.setDescription("A description of an account adjustment with a code");

            //Create 2 new Adjustments
            recurlyClient.createAccountAdjustment(accountData.getAccountCode(), adjustment);
            recurlyClient.createAccountAdjustment(accountData.getAccountCode(), adjustmentWithCode);

            // Test adjustment retrieval methods
            Adjustments retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), null, null);
            Assert.assertEquals(retrievedAdjustments.size(), 2, "Did not retrieve correct count of Adjustments of any type and state");

            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), Adjustments.AdjustmentType.CHARGE, null);
            Assert.assertEquals(retrievedAdjustments.size(), 2, "Did not retrieve correct count of Adjustments of type Charge");

            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), Adjustments.AdjustmentType.CHARGE, Adjustments.AdjustmentState.INVOICED);
            Assert.assertEquals(retrievedAdjustments.size(), 0, "Retrieved Adjustments of type Charge marked as invoiced although none should be.");

            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), null, Adjustments.AdjustmentState.INVOICED);
            Assert.assertEquals(retrievedAdjustments.size(), 0, "Retrieved Adjustments marked as invoiced although none should be.");

            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), Adjustments.AdjustmentType.CHARGE, Adjustments.AdjustmentState.PENDING);
            Assert.assertEquals(2, retrievedAdjustments.size(), "Did not retrieve correct count of Adjustments of type Charge in Pending state");
            int adjAccountCodeCounter = 0;
            for (Adjustment adj : retrievedAdjustments) {
                if (adjustmentAccountCode.equals(adj.getAccountingCode())) {
                    adjAccountCodeCounter++;
                }
            }
            Assert.assertEquals(adjAccountCodeCounter, 1, "An unexpected number of Adjustments were assigned the accountCode [" + adjustmentAccountCode + "]");

        } finally {
            // Clear up the BillingInfo
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
            // Delete the Plan
            recurlyClient.deletePlan(planData.getPlanCode());
        }

    }

    @Test(groups = "integration")
    public void testPagination() throws Exception {
        System.setProperty(RECURLY_PAGE_SIZE, "1");

        final int minNumberOfAccounts = 5;
        for (int i = 0; i < minNumberOfAccounts; i++) {
            final Account accountData = TestUtils.createRandomAccount();
            recurlyClient.createAccount(accountData);
        }

        final Set<String> accountCodes = new HashSet<String>();
        Accounts accounts = recurlyClient.getAccounts();
        Assert.assertNull(accounts.getPrevUrl());
        for (int i = 0; i < minNumberOfAccounts; i++) {
            // If the environment is used, we will have more than the ones we created
            Assert.assertTrue(accounts.getNbRecords() >= minNumberOfAccounts);
            Assert.assertEquals(accounts.size(), 1);
            accountCodes.add(accounts.get(0).getAccountCode());
            if (i < minNumberOfAccounts - 1) {
                accounts = accounts.getNext();
            }
        }
        Assert.assertEquals(accountCodes.size(), minNumberOfAccounts);

        for (int i = minNumberOfAccounts - 1; i >= 0; i--) {
            Assert.assertTrue(accounts.getNbRecords() >= minNumberOfAccounts);
            Assert.assertEquals(accounts.size(), 1);
            accounts = accounts.getPrev();
        }
    }

    @Test(groups = "integration")
    public void testCreateAccountWithBadBillingInfo() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        // See http://docs.recurly.com/payment-gateways/test
        billingInfoData.setNumber("4000-0000-0000-0093");

        try {
            final Account account = recurlyClient.createAccount(accountData);
            billingInfoData.setAccount(account);

            recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.fail();
        } catch (TransactionErrorException e) {
            Assert.assertEquals(e.getErrors().getTransactionError().getErrorCode(), "fraud_ip_address");
            Assert.assertEquals(e.getErrors().getTransactionError().getMerchantMessage(), "The payment gateway declined the transaction because it originated from an IP address known for fraudulent transactions.");
            Assert.assertEquals(e.getErrors().getTransactionError().getCustomerMessage(), "The transaction was declined. Please contact support.");
        }
    }

    @Test(groups = "integration")
    public void testCreateAccount() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();

        try {
            final DateTime creationDateTime = new DateTime(DateTimeZone.UTC);
            final Account account = recurlyClient.createAccount(accountData);

            // Test account creation
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
            Assert.assertEquals(accountData.getAddress().getAddress1(), account.getAddress().getAddress1());
            Assert.assertEquals(accountData.getAddress().getAddress2(), account.getAddress().getAddress2());
            Assert.assertEquals(accountData.getAddress().getCity(), account.getAddress().getCity());
            Assert.assertEquals(accountData.getAddress().getState(), account.getAddress().getState());
            Assert.assertEquals(accountData.getAddress().getZip(), account.getAddress().getZip());
            Assert.assertEquals(accountData.getAddress().getCountry(), account.getAddress().getCountry());
            Assert.assertEquals(accountData.getAddress().getPhone(), account.getAddress().getPhone());

            // Test getting all
            final Accounts retrievedAccounts = recurlyClient.getAccounts();
            Assert.assertTrue(retrievedAccounts.size() > 0);

            // Test an account lookup
            final Account retrievedAccount = recurlyClient.getAccount(account.getAccountCode());
            Assert.assertEquals(retrievedAccount, account);

            // Create a BillingInfo
            billingInfoData.setAccount(account);

            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);

            // Test BillingInfo creation
            Assert.assertNotNull(billingInfo);
            Assert.assertEquals(billingInfoData.getFirstName(), billingInfo.getFirstName());
            Assert.assertEquals(billingInfoData.getLastName(), billingInfo.getLastName());
            Assert.assertEquals(billingInfoData.getMonth(), billingInfo.getMonth());
            Assert.assertEquals(billingInfoData.getYear(), billingInfo.getYear());
            Assert.assertEquals(billingInfo.getCardType(), "Visa");

            // Test BillingInfo lookup
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertEquals(retrievedBillingInfo, billingInfo);

        } finally {
            // Clean up
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testCreatePlan() throws Exception {
        final Plan planData = TestUtils.createRandomPlan();
        try {
            // Create a plan
            final DateTime creationDateTime = new DateTime(DateTimeZone.UTC);
            final Plan plan = recurlyClient.createPlan(planData);
            final Plan retPlan = recurlyClient.getPlan(plan.getPlanCode());

            // test creation of plan
            Assert.assertNotNull(plan);
            Assert.assertEquals(retPlan, plan);
            // Verify we can serialize date times
            Assert.assertEquals(Minutes.minutesBetween(plan.getCreatedAt(), creationDateTime).getMinutes(), 0);
            // Check that getting all the plans makes sense
            Assert.assertTrue(recurlyClient.getPlans().size() > 0);

        } finally {
            // Delete the plan
            recurlyClient.deletePlan(planData.getPlanCode());
            // Check that we deleted it
            try {
                final Plan retrievedPlan2 = recurlyClient.getPlan(planData.getPlanCode());
                Assert.fail("Failed to delete the Plan");
            } catch (final RecurlyAPIException e) {
                // good
            }
        }
    }

    @Test(groups = "integration")
    public void testCreateSubscriptions() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();
        final Coupon couponData = TestUtils.createRandomCoupon();

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);

            // Create a plan
            final Plan plan = recurlyClient.createPlan(planData);

            // Create a coupon
            Coupon coupon = recurlyClient.createCoupon(couponData);

            // Set up a subscription
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            // Apply a coupon at the time of subscription creation
            subscriptionData.setCouponCode(couponData.getCouponCode());
            // Create some notes on the subscription
            subscriptionData.setCustomerNotes("Customer Notes");
            subscriptionData.setTermsAndConditions("Terms and Conditions");
            final DateTime creationDateTime = new DateTime(DateTimeZone.UTC);

            // Preview the user subscribing to the plan
            final Subscription subscriptionPreview = recurlyClient.previewSubscription(subscriptionData);

            // Test the subscription preview
            Assert.assertNotNull(subscriptionPreview);
            Assert.assertEquals(subscriptionPreview.getCurrency(), subscriptionData.getCurrency());
            if (null == subscriptionData.getQuantity()) {
                Assert.assertEquals(subscriptionPreview.getQuantity(), new Integer(1));
            } else {
                Assert.assertEquals(subscriptionPreview.getQuantity(), subscriptionData.getQuantity());
            }

            // Subscribe the user to the plan
            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);

            // Test subscription creation
            Assert.assertNotNull(subscription);
            // Test invoice fetching via href
            Assert.assertNotNull(subscription.getInvoice());
            Assert.assertEquals(subscription.getCurrency(), subscriptionData.getCurrency());
            if (null == subscriptionData.getQuantity()) {
                Assert.assertEquals(subscription.getQuantity(), new Integer(1));
            } else {
                Assert.assertEquals(subscription.getQuantity(), subscriptionData.getQuantity());
            }
            // Verify we can serialize date times
            Assert.assertEquals(Minutes.minutesBetween(subscription.getActivatedAt(), creationDateTime).getMinutes(),
                                0);

            // Test lookup for subscription
            final Subscription sub1 = recurlyClient.getSubscription(subscription.getUuid());
            Assert.assertNotNull(sub1);
            Assert.assertEquals(sub1, subscription);
            // Do a lookup for subs for given account
            final Subscriptions subs = recurlyClient.getAccountSubscriptions(accountData.getAccountCode());
            // Check that the newly created sub is in the list
            Subscription found = null;
            for (final Subscription s : subs) {
                if (s.getUuid().equals(subscription.getUuid())) {
                    found = s;
                    break;
                }
            }
            if (found == null) {
                Assert.fail("Could not locate the subscription in the subscriptions associated with the account");
            }

            // Verify the subscription information, including nested parameters
            // See https://github.com/killbilling/recurly-java-library/issues/4
            Assert.assertEquals(found.getAccount().getAccountCode(), accountData.getAccountCode());
            Assert.assertEquals(found.getAccount().getFirstName(), accountData.getFirstName());

            // Verify the coupon redemption
            final Redemption redemption = recurlyClient.getCouponRedemptionByAccount(account.getAccountCode());
            Assert.assertNotNull(redemption);
            Assert.assertEquals(redemption.getCoupon().getCouponCode(), couponData.getCouponCode());
            
            // Update the subscription's notes
            final SubscriptionNotes subscriptionNotesData = new SubscriptionNotes();
            subscriptionNotesData.setTermsAndConditions("New Terms and Conditions");
            subscriptionNotesData.setCustomerNotes("New Customer Notes");
                      
            recurlyClient.updateSubscriptionNotes(subscription.getUuid(), subscriptionNotesData);
            final Subscription subscriptionWithNotes = recurlyClient.getSubscription(subscription.getUuid());
              
            // Verify that the notes were updated
            Assert.assertNotNull(subscriptionWithNotes);
            Assert.assertEquals(subscriptionWithNotes.getTermsAndConditions(), subscriptionNotesData.getTermsAndConditions());
            Assert.assertEquals(subscriptionWithNotes.getCustomerNotes(), subscriptionNotesData.getCustomerNotes());

            // Cancel a Subscription
            recurlyClient.cancelSubscription(subscription);
            final Subscription cancelledSubscription = recurlyClient.getSubscription(subscription.getUuid());
            Assert.assertEquals(cancelledSubscription.getState(), "canceled");

            recurlyClient.reactivateSubscription(subscription);
            final Subscription reactivatedSubscription = recurlyClient.getSubscription(subscription.getUuid());
            Assert.assertEquals(reactivatedSubscription.getState(), "active");

            // Terminate a Subscription
            recurlyClient.terminateSubscription(subscription, RefundOption.full);
            final Subscription expiredSubscription = recurlyClient.getSubscription(subscription.getUuid());
            Assert.assertEquals(expiredSubscription.getState(), "expired");
        } finally {
            // Clear up the BillingInfo
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
            // Delete the Plan
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }
    
    @Test(groups = "integration")
    public void testCreateBulkSubscriptions() throws Exception {
      final Account accountData = TestUtils.createRandomAccount();
      final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
      try {        
          final Account account = recurlyClient.createAccount(accountData);
          // Create BillingInfo
          billingInfoData.setAccount(account);
          final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
          // Test bulk subscription creation
          for(int i = 0; i < 3; i++) {
            final Plan planData = TestUtils.createRandomPlan();
            final Plan plan = recurlyClient.createPlan(planData);
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            subscriptionData.setBulk(true);
            
            //create the subscription
            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);
            
            // Delete the Plan
            recurlyClient.deletePlan(planData.getPlanCode());
            
          }
          // Check that the newly created subs are in the list
          final Subscriptions bulkSubs = recurlyClient.getAccountSubscriptions(accountData.getAccountCode());

          int count = 0;
          
          for (final Subscription s : bulkSubs) {
            count++;
          }
          
          if (count != 3) {
              Assert.fail("Could not create subscriptions in bulk");
          }
      } finally {
          // Clear up the BillingInfo
          recurlyClient.clearBillingInfo(accountData.getAccountCode());
          // Close the account
          recurlyClient.closeAccount(accountData.getAccountCode());
      }
    }

    @Test(groups = "integration")
    public void testCreateAndCloseInvoices() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);

            // Create an Adjustment
            final Adjustment a = new Adjustment();
            a.setUnitAmountInCents(150);
            a.setCurrency(CURRENCY);

            final Adjustment createdA = recurlyClient.createAccountAdjustment(accountData.getAccountCode(), a);

            // Post an invoice/invoice the adjustment
            final Invoice invoiceData = new Invoice();
            invoiceData.setLineItems(null);
            final Invoice invoice = recurlyClient.postAccountInvoice(accountData.getAccountCode(), invoiceData);
            Assert.assertNotNull(invoice);

            // Check to see if the adjustment was invoiced properly
            Adjustments retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), null, Adjustments.AdjustmentState.PENDING);
            Assert.assertEquals(retrievedAdjustments.size(), 0, "Retrieved Adjustments marked as pending although none should be.");

            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), null, Adjustments.AdjustmentState.INVOICED);
            Assert.assertEquals(retrievedAdjustments.size(), 1, "Not all Adjustments marked as invoiced although all should be.");

            // Create an Adjustment
            final Adjustment b = new Adjustment();
            b.setUnitAmountInCents(250);
            b.setCurrency(CURRENCY);

            final Adjustment createdB = recurlyClient.createAccountAdjustment(accountData.getAccountCode(), b);

            // Post an invoice/invoice the adjustment
            final Invoice failInvoiceData = new Invoice();
            failInvoiceData.setLineItems(null);
            final Invoice invoiceFail = recurlyClient.postAccountInvoice(accountData.getAccountCode(), failInvoiceData);
            Assert.assertNotNull(invoiceFail);

            // Check to see if the adjustment was invoiced properly
            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), null, Adjustments.AdjustmentState.PENDING);
            Assert.assertEquals(retrievedAdjustments.size(), 0, "Retrieved Adjustments marked as pending although none should be.");

            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), null, Adjustments.AdjustmentState.INVOICED);
            Assert.assertEquals(retrievedAdjustments.size(), 2, "Not all Adjustments marked as invoiced although all should be.");

        } finally {
            // Clear up the BillingInfo
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "enterprise")
    public void testCreateAndCloseManualInvoices() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create an Adjustment
            final Adjustment a = new Adjustment();
            a.setUnitAmountInCents(150);
            a.setCurrency(CURRENCY);

            final Adjustment createdA = recurlyClient.createAccountAdjustment(accountData.getAccountCode(), a);

            // Post an invoice/invoice the adjustment
            final Invoice invoiceData = new Invoice();
            invoiceData.setCollectionMethod("manual");
            invoiceData.setLineItems(null);
            final Invoice invoice = recurlyClient.postAccountInvoice(accountData.getAccountCode(), invoiceData);
            Assert.assertNotNull(invoice);

            // Attempt to close the invoice
            final Invoice closedInvoice = recurlyClient.markInvoiceSuccessful(invoice.getInvoiceNumber());
            Assert.assertEquals(closedInvoice.getState(), "collected", "Invoice not closed successfully");

            // Create an Adjustment
            final Adjustment b = new Adjustment();
            b.setUnitAmountInCents(250);
            b.setCurrency(CURRENCY);

            final Adjustment createdB = recurlyClient.createAccountAdjustment(accountData.getAccountCode(), b);

            // Post an invoice/invoice the adjustment
            final Invoice failInvoiceData = new Invoice();
            failInvoiceData.setCollectionMethod("manual");
            failInvoiceData.setLineItems(null);
            final Invoice invoiceFail = recurlyClient.postAccountInvoice(accountData.getAccountCode(), failInvoiceData);
            Assert.assertNotNull(invoiceFail);

            // Attempt to fail the invoice
            final Invoice failedInvoice = recurlyClient.markInvoiceFailed(invoiceFail.getInvoiceNumber());
            Assert.assertEquals(failedInvoice.getState(), "failed", "Invoice not failed successfully");

            // Create an Adjustment
            final Adjustment c = new Adjustment();
            c.setUnitAmountInCents(450);
            c.setCurrency(CURRENCY);

            final Adjustment createdC = recurlyClient.createAccountAdjustment(accountData.getAccountCode(), c);

            // Post an invoice/invoice the adjustment
            final Invoice externalInvoiceData = new Invoice();
            externalInvoiceData.setCollectionMethod("manual");
            externalInvoiceData.setLineItems(null);
            final Invoice invoiceExternal = recurlyClient.postAccountInvoice(accountData.getAccountCode(), externalInvoiceData);
            Assert.assertNotNull(invoiceExternal);

            //create an external payment to pay off the invoice
            final Transaction externalPaymentData = new Transaction();
            externalPaymentData.setPaymentMethod("other");
            final DateTime collectionDateTime = new DateTime(DateTimeZone.UTC);
            externalPaymentData.setCollectedAt(collectionDateTime);
            externalPaymentData.setAmountInCents(450);

            final Transaction externalPayment = recurlyClient.enterOfflinePayment(invoiceExternal.getInvoiceNumber(), externalPaymentData);
            Assert.assertNotNull(externalPayment);
            Assert.assertEquals(externalPayment.getInvoice().getState(), "collected", "Invoice not closed successfully");

        } finally {
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testCreateAndQueryTransactions() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);

            // Create a plan
            final Plan plan = recurlyClient.createPlan(planData);

            // Subscribe the user to the plan
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setUnitAmountInCents(150);
            subscriptionData.setCurrency(CURRENCY);
            recurlyClient.createSubscription(subscriptionData);

            // Create a transaction
            final Transaction t = new Transaction();
            accountData.setBillingInfo(billingInfoData);
            t.setAccount(accountData);
            t.setAmountInCents(15);
            t.setCurrency(CURRENCY);
            final Transaction createdT = recurlyClient.createTransaction(t);

            // Test that the transaction created correctly
            Assert.assertNotNull(createdT);
            // Can't test for account equality yet as the account is only a ref and doesn't get mapped.
            Assert.assertEquals(createdT.getAmountInCents(), t.getAmountInCents());
            Assert.assertEquals(createdT.getCurrency(), t.getCurrency());

            // Test lookup on the transaction via the users account
            final Transactions trans = recurlyClient.getAccountTransactions(account.getAccountCode());
            // 3 transactions: voided verification, $1.5 for the plan and the $0.15 transaction above
            Assert.assertEquals(trans.size(), 3);
            Transaction found = null;
            for (final Transaction _t : trans) {
                if (_t.getUuid().equals(createdT.getUuid())) {
                    found = _t;
                    break;
                }
            }
            if (found == null) {
                Assert.fail("Failed to locate the newly created transaction");
            }

            // Verify the transaction information, including nested parameters
            // See https://github.com/killbilling/recurly-java-library/issues/4
            Assert.assertEquals(found.getAccount().getAccountCode(), accountData.getAccountCode());
            Assert.assertEquals(found.getAccount().getFirstName(), accountData.getFirstName());
            Assert.assertEquals(found.getInvoice().getAccount().getAccountCode(), accountData.getAccountCode());
            Assert.assertEquals(found.getInvoice().getAccount().getFirstName(), accountData.getFirstName());
            Assert.assertEquals(found.getInvoice().getTotalInCents(), (Integer) 15);
            Assert.assertEquals(found.getInvoice().getCurrency(), CURRENCY);

            // Verify we can retrieve it
            Assert.assertEquals(recurlyClient.getTransaction(found.getUuid()).getUuid(), found.getUuid());

            // Verify we can refund it
            Assert.assertTrue(recurlyClient.getTransaction(found.getUuid()).getRefundable());
            recurlyClient.refundTransaction(found.getUuid(), null);
            Assert.assertFalse(recurlyClient.getTransaction(found.getUuid()).getRefundable());

            // Test Invoices retrieval
            final Invoices invoices = recurlyClient.getAccountInvoices(account.getAccountCode());
            // 2 Invoices are present (the first one is for the transaction, the second for the subscription)
            Assert.assertEquals(invoices.size(), 2, "Number of Invoices incorrect");
            Assert.assertEquals(invoices.get(0).getTotalInCents(), t.getAmountInCents(), "Amount in cents is not the same");
            Assert.assertEquals(invoices.get(1).getTotalInCents(), subscriptionData.getUnitAmountInCents(), "Amount in cents is not the same");
        } finally {
            // Clear up the BillingInfo
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
            // Delete the Plan
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testAddons() throws Exception {
        // Create a Plan
        final Plan planData = TestUtils.createRandomPlan();
        final AddOn addOn = TestUtils.createRandomAddOn();

        try {
            // Create an AddOn
            final Plan plan = recurlyClient.createPlan(planData);
            AddOn addOnRecurly = recurlyClient.createPlanAddOn(plan.getPlanCode(), addOn);

            // Test the creation
            Assert.assertNotNull(addOnRecurly);
            Assert.assertEquals(addOnRecurly.getAddOnCode(), addOn.getAddOnCode());
            Assert.assertEquals(addOnRecurly.getName(), addOn.getName());
            Assert.assertEquals(addOnRecurly.getUnitAmountInCents(), addOn.getUnitAmountInCents());
            Assert.assertEquals(addOnRecurly.getDefaultQuantity(), addOn.getDefaultQuantity());

            // Query for an AddOn
            addOnRecurly = recurlyClient.getAddOn(plan.getPlanCode(), addOn.getAddOnCode());

            // Check the 2 are the same
            Assert.assertEquals(addOnRecurly.getAddOnCode(), addOn.getAddOnCode());
            Assert.assertEquals(addOnRecurly.getName(), addOn.getName());
            Assert.assertEquals(addOnRecurly.getDefaultQuantity(), addOn.getDefaultQuantity());
            Assert.assertEquals(addOnRecurly.getDisplayQuantityOnHostedPage(), addOn.getDisplayQuantityOnHostedPage());
            Assert.assertEquals(addOnRecurly.getUnitAmountInCents(), addOn.getUnitAmountInCents());

            // Query for AddOns and Check again
            AddOns addOns = recurlyClient.getAddOns(plan.getPlanCode());
            Assert.assertEquals(addOns.size(), 1);
            Assert.assertEquals(addOns.get(0).getAddOnCode(), addOn.getAddOnCode());
            Assert.assertEquals(addOns.get(0).getName(), addOn.getName());
            Assert.assertEquals(addOns.get(0).getDefaultQuantity(), addOn.getDefaultQuantity());
            Assert.assertEquals(addOns.get(0).getDisplayQuantityOnHostedPage(), addOn.getDisplayQuantityOnHostedPage());
            Assert.assertEquals(addOns.get(0).getUnitAmountInCents(), addOn.getUnitAmountInCents());
        } finally {
            // Delete an AddOn
            recurlyClient.deleteAddOn(planData.getPlanCode(), addOn.getAddOnCode());
            // Delete the plan
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testCreateCoupon() throws Exception {
        final Coupon couponData = TestUtils.createRandomCoupon();

        try {
            // Create the coupon
            Coupon coupon = recurlyClient.createCoupon(couponData);
            Assert.assertNotNull(coupon);
            Assert.assertEquals(coupon.getName(), couponData.getName());
            Assert.assertEquals(coupon.getCouponCode(), couponData.getCouponCode());
            Assert.assertEquals(coupon.getDiscountType(), couponData.getDiscountType());
            Assert.assertEquals(coupon.getDiscountPercent(), couponData.getDiscountPercent());

            // Get the coupon
            coupon = recurlyClient.getCoupon(couponData.getCouponCode());
            Assert.assertNotNull(coupon);
            Assert.assertEquals(coupon.getName(), couponData.getName());
            Assert.assertEquals(coupon.getCouponCode(), couponData.getCouponCode());
            Assert.assertEquals(coupon.getDiscountType(), couponData.getDiscountType());
            Assert.assertEquals(coupon.getDiscountPercent(), couponData.getDiscountPercent());

        } finally {
            recurlyClient.deleteCoupon(couponData.getCouponCode());
        }
    }

    @Test(groups = "integration")
    public void testUpdateSubscriptions() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();
        final Plan plan2Data = TestUtils.createRandomPlan(CURRENCY);

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);

            // Create a plan
            final Plan plan = recurlyClient.createPlan(planData);
            final Plan plan2 = recurlyClient.createPlan(plan2Data);

            // Subscribe the user to the plan
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            final DateTime creationDateTime = new DateTime(DateTimeZone.UTC);
            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);

            Assert.assertNotNull(subscription);

            // Set subscription update info
            final SubscriptionUpdate subscriptionUpdateData = new SubscriptionUpdate();
            subscriptionUpdateData.setTimeframe(SubscriptionUpdate.Timeframe.now);
            subscriptionUpdateData.setPlanCode(plan2.getPlanCode());

            // Preview the subscription update
            final Subscription subscriptionUpdatedPreview = recurlyClient.updateSubscriptionPreview(subscription.getUuid(), subscriptionUpdateData);

            Assert.assertNotNull(subscriptionUpdatedPreview);
            // Test inline invoice fetch
            Assert.assertNotNull(subscriptionUpdatedPreview.getInvoice());
            Assert.assertEquals(subscription.getUuid(), subscriptionUpdatedPreview.getUuid());
            Assert.assertNotEquals(subscription.getPlan(), subscriptionUpdatedPreview.getPlan());
            Assert.assertEquals(plan2.getPlanCode(), subscriptionUpdatedPreview.getPlan().getPlanCode());

            // Update the subscription
            final Subscription subscriptionUpdated = recurlyClient.updateSubscription(subscription.getUuid(), subscriptionUpdateData);

            Assert.assertNotNull(subscriptionUpdated);
            Assert.assertEquals(subscription.getUuid(), subscriptionUpdated.getUuid());
            Assert.assertNotEquals(subscription.getPlan(), subscriptionUpdated.getPlan());
            Assert.assertEquals(plan2.getPlanCode(), subscriptionUpdated.getPlan().getPlanCode());
        } finally {
            // Clear up the BillingInfo
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
            // Delete the Plans
            recurlyClient.deletePlan(planData.getPlanCode());
            recurlyClient.deletePlan(plan2Data.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testRedeemCoupon() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);
        final Coupon couponData = TestUtils.createRandomCoupon();

        try {
            final Account account = recurlyClient.createAccount(accountData);
            final Plan plan = recurlyClient.createPlan(planData);
            final Coupon coupon = recurlyClient.createCoupon(couponData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);

            // Subscribe the user to the plan
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);
            Assert.assertNotNull(subscription);

            // No coupon at this point
            try {
                recurlyClient.getCouponRedemptionByAccount(account.getAccountCode());
                Assert.fail("Coupon should not be found.");
            } catch (RecurlyAPIException expected) {
                Assert.assertTrue(true);
            }

            // Redeem a coupon
            final Redemption redemptionData = new Redemption();
            redemptionData.setAccountCode(account.getAccountCode());
            redemptionData.setCurrency(CURRENCY);
            Redemption redemption = recurlyClient.redeemCoupon(coupon.getCouponCode(), redemptionData);
            Assert.assertNotNull(redemption);
            Assert.assertEquals(redemption.getCoupon().getCouponCode(), coupon.getCouponCode());
            Assert.assertEquals(redemption.getAccount().getAccountCode(), account.getAccountCode());
            Assert.assertFalse(redemption.getSingleUse());
            Assert.assertEquals(redemption.getTotalDiscountedInCents(), (Integer) 0);
            Assert.assertEquals(redemption.getState(), "active");
            Assert.assertEquals(redemption.getCurrency(), CURRENCY);

            // Get the coupon redemption
            redemption = recurlyClient.getCouponRedemptionByAccount(account.getAccountCode());
            Assert.assertNotNull(redemption);
            Assert.assertEquals(redemption.getCoupon().getCouponCode(), coupon.getCouponCode());
            Assert.assertEquals(redemption.getAccount().getAccountCode(), account.getAccountCode());

            // Remove a coupon
            recurlyClient.deleteCouponRedemption(account.getAccountCode());
            try {
                recurlyClient.getCouponRedemptionByAccount(account.getAccountCode());
                Assert.fail("Coupon should be removed.");
            } catch (RecurlyAPIException expected) {
                Assert.assertTrue(true);
            }

            // Redeem a coupon once again
            final Redemption redemptionData2 = new Redemption();
            redemptionData2.setAccountCode(account.getAccountCode());
            redemptionData2.setCurrency(CURRENCY);
            redemption = recurlyClient.redeemCoupon(coupon.getCouponCode(), redemptionData2);
            Assert.assertNotNull(redemption);
            redemption = recurlyClient.getCouponRedemptionByAccount(account.getAccountCode());
            Assert.assertEquals(redemption.getCoupon().getCouponCode(), coupon.getCouponCode());
            Assert.assertEquals(redemption.getAccount().getAccountCode(), account.getAccountCode());
            Assert.assertFalse(redemption.getSingleUse());
            Assert.assertEquals(redemption.getTotalDiscountedInCents(), (Integer) 0);
            Assert.assertEquals(redemption.getState(), "active");
            Assert.assertEquals(redemption.getCurrency(), CURRENCY);

        } finally {
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            recurlyClient.deleteCouponRedemption(accountData.getAccountCode());
            recurlyClient.closeAccount(accountData.getAccountCode());
            recurlyClient.deletePlan(planData.getPlanCode());
            recurlyClient.deleteCoupon(couponData.getCouponCode());
        }
    }

}
