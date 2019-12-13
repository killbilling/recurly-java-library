/*
 * Copyright 2010-2014 Ning, Inc.
 * Copyright 2014-2018 The Billing Project, LLC
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ning.billing.recurly.model.Account;
import com.ning.billing.recurly.model.AccountAcquisition;
import com.ning.billing.recurly.model.AccountBalance;
import com.ning.billing.recurly.model.Accounts;
import com.ning.billing.recurly.model.AcquisitionChannel;
import com.ning.billing.recurly.model.AddOn;
import com.ning.billing.recurly.model.AddOns;
import com.ning.billing.recurly.model.Address;
import com.ning.billing.recurly.model.Adjustment;
import com.ning.billing.recurly.model.AdjustmentRefund;
import com.ning.billing.recurly.model.Adjustments;
import com.ning.billing.recurly.model.BillingInfo;
import com.ning.billing.recurly.model.Coupon;
import com.ning.billing.recurly.model.Coupon.RedemptionResource;
import com.ning.billing.recurly.model.Coupons;
import com.ning.billing.recurly.model.CustomField;
import com.ning.billing.recurly.model.CustomFields;
import com.ning.billing.recurly.model.GiftCard;
import com.ning.billing.recurly.model.Invoice;
import com.ning.billing.recurly.model.InvoiceCollection;
import com.ning.billing.recurly.model.InvoiceRefund;
import com.ning.billing.recurly.model.Invoices;
import com.ning.billing.recurly.model.Item;
import com.ning.billing.recurly.model.Plan;
import com.ning.billing.recurly.model.PlanCode;
import com.ning.billing.recurly.model.PlanCodes;
import com.ning.billing.recurly.model.Purchase;
import com.ning.billing.recurly.model.RecurlyAPIError;
import com.ning.billing.recurly.model.Redemption;
import com.ning.billing.recurly.model.Redemptions;
import com.ning.billing.recurly.model.RefundMethod;
import com.ning.billing.recurly.model.RefundOption;
import com.ning.billing.recurly.model.ShippingAddress;
import com.ning.billing.recurly.model.ShippingAddresses;
import com.ning.billing.recurly.model.Subscription;
import com.ning.billing.recurly.model.SubscriptionAddOns;
import com.ning.billing.recurly.model.SubscriptionNotes;
import com.ning.billing.recurly.model.SubscriptionUpdate;
import com.ning.billing.recurly.model.Subscriptions;
import com.ning.billing.recurly.model.Transaction;
import com.ning.billing.recurly.model.Transactions;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    @Test(groups = "integration")
    public void testUnauthorizedException() throws Exception {
        final String subdomain = System.getProperty(KILLBILL_PAYMENT_RECURLY_SUBDOMAIN);
        RecurlyClient unauthorizedRecurlyClient = new RecurlyClient("invalid-api-key", subdomain);
        unauthorizedRecurlyClient.open();

        try {
            unauthorizedRecurlyClient.getAccounts();
            Assert.fail("getAccounts call should not succeed with invalid credentials.");
        } catch (RecurlyAPIException expected) {
            Assert.assertEquals(expected.getRecurlyError().getSymbol(), "unauthorized");
        }
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
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testReopenAccount() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();

        try {
            // Create account
            final Account newAccount = recurlyClient.createAccount(accountData);
            Assert.assertNull(newAccount.getClosedAt());

            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
            final Account closedAccount = recurlyClient.getAccount(accountData.getAccountCode());
            Assert.assertNotNull(closedAccount.getClosedAt());

            // Reopen the account
            final Account reopenedAccount = recurlyClient.reopenAccount(accountData.getAccountCode());

            // Confirm that the reopened account is the same as the original
            // (besides `updated_at`, which may differ)
            newAccount.setUpdatedAt(reopenedAccount.getUpdatedAt());
            Assert.assertEquals(reopenedAccount, newAccount);
        } finally {
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testInvalidTokenError() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        RecurlyAPIException error = null;

        try {
            // Create account with invalid billing token
            final BillingInfo billingInfo = new BillingInfo();
            billingInfo.setTokenId("invalid token");
            accountData.setBillingInfo(billingInfo);

            final Subscription subscription = new Subscription();
            subscription.setPlanCode("anything");
            final Subscriptions subscriptions = new Subscriptions();
            subscriptions.setRecurlyObject(subscription);

            final Purchase purchase = new Purchase();
            purchase.setCurrency(CURRENCY);
            purchase.setAccount(accountData);
            purchase.setSubscriptions(subscriptions);
            recurlyClient.previewPurchase(purchase);
        } catch (RecurlyAPIException expected) {
            error = expected;
        }

        // Despite being a 422 error, this case returns a single Error
        // object rather than Errors. Check that we're deserializing it
        // properly.
        Assert.assertEquals(error.getRecurlyError().getHttpStatusCode(), 422);
        Assert.assertEquals(error.getRecurlyError().getSymbol(), "token_invalid");
    }

    @Test(groups = "integration")
    public void testGetBillingInfo() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        billingInfoData.setAccount(null); // need to null out test account
        accountData.setBillingInfo(billingInfoData);

        try {
            // Create account and fetch billing info
            final Account account = recurlyClient.createAccount(accountData);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);
            Assert.assertEquals(retrievedBillingInfo.getType(), "credit_card");
        } finally {
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
            Thread.sleep(1000);  // TODO - can remove after Jan 18th 2017
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
            final String invoiceID = subInvoice.getId();
            final Invoice gotInvoice = recurlyClient.getInvoice(invoiceID);

            Assert.assertEquals(subInvoice.hashCode(), gotInvoice.hashCode());
            
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
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testGetSiteSubscriptions() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();

        try {
            final Account account = recurlyClient.createAccount(accountData);
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            final Plan plan = recurlyClient.createPlan(planData);

            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            subscriptionData.setRemainingBillingCycles(1);

            // makes sure we have at least one subscription
            recurlyClient.createSubscription(subscriptionData);

            // make sure we return more than one subscription
            Assert.assertTrue(recurlyClient.getSubscriptions().size() > 0);
        } finally {
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testGetCoupons() throws Exception {
        final Coupons retrievedCoupons = recurlyClient.getCoupons();
        Assert.assertTrue(retrievedCoupons.size() >= 0);
    }

    @Test(groups="integration")
    public void testGetAndDeleteAdjustment() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);

            // Create a plan
            final Plan plan = recurlyClient.createPlan(planData);

            // Subscribe the user to the plan
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            subscriptionData.setRemainingBillingCycles(null);

            //Add some adjustments to the account's open invoice
            final Adjustment adjustmentData = new Adjustment();
            adjustmentData.setCurrency("USD");
            adjustmentData.setUnitAmountInCents("100");
            adjustmentData.setDescription("A description of an account adjustment");

            Adjustment adjustment = recurlyClient.createAccountAdjustment(account.getAccountCode(), adjustmentData);
            final String uuid = adjustment.getUuid();

            adjustment = recurlyClient.getAdjustment(uuid);

            Assert.assertEquals(adjustment.getUuid(), uuid);

            recurlyClient.deleteAdjustment(uuid);

            // Check that we deleted it
            try {
                recurlyClient.getAdjustment(uuid);
                Assert.fail("Failed to delete the Adjustment");
            } catch (final RecurlyAPIException e) {
                Assert.assertEquals(e.getRecurlyError().getHttpStatusCode(), 404);
            }
        } finally {
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
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

        for (int i = 0; i < minNumberOfAccounts; i++) {
            // If the environment is used, we will have more than the ones we created
            Assert.assertEquals(accounts.size(), 1);
            accountCodes.add(accounts.get(0).getAccountCode());
            if (i < minNumberOfAccounts - 1) {
                accounts = accounts.getNext();
            }
        }
        Assert.assertEquals(accountCodes.size(), minNumberOfAccounts);

        System.setProperty(RECURLY_PAGE_SIZE, "50");
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
            Assert.fail("Should have thrown transaction exception");
        } catch (TransactionErrorException e) {
            Assert.assertEquals(e.getErrors().getTransactionError().getErrorCode(), "fraud_ip_address");
            Assert.assertEquals(e.getErrors().getTransactionError().getMerchantMessage(), "The payment gateway declined the transaction because it originated from an IP address known for fraudulent transactions.");
            Assert.assertEquals(e.getErrors().getTransactionError().getCustomerMessage(), "The transaction was declined. Please contact support.");
        }
    }

    @Test(groups = "integration")
    public void testCreateUpdateAccount() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        CustomFields customFields = new CustomFields();
        // NOTE: acct_field and acct_field2 must be created on the integration server first
        customFields.add(TestUtils.createRandomCustomField("acct_field"));
        customFields.add(TestUtils.createRandomCustomField("acct_field2"));
        accountData.setCustomFields(customFields);
        final AccountAcquisition acquisitionData = TestUtils.createRandomAccountAcquisition();
        accountData.setAccountAcquisition(acquisitionData);

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
            Assert.assertEquals(accountData.getAddress().getAddress1(), account.getAddress().getAddress1());
            Assert.assertEquals(accountData.getAddress().getAddress2(), account.getAddress().getAddress2());
            Assert.assertEquals(accountData.getAddress().getCity(), account.getAddress().getCity());
            Assert.assertEquals(accountData.getAddress().getState(), account.getAddress().getState());
            Assert.assertEquals(accountData.getAddress().getZip(), account.getAddress().getZip());
            Assert.assertEquals(accountData.getAddress().getCountry(), account.getAddress().getCountry());
            Assert.assertEquals(accountData.getAddress().getPhone(), account.getAddress().getPhone());
            Assert.assertEquals(accountData.getCustomFields(), account.getCustomFields());

            // fetch and check the acquisition data
            final AccountAcquisition acquisition = recurlyClient.getAccountAcquisition(account.getAccountCode());
            Assert.assertEquals(acquisition.getCurrency(), acquisitionData.getCurrency());
            Assert.assertEquals(acquisition.getChannel(), acquisitionData.getChannel());
            Assert.assertEquals(acquisition.getCampaign(), acquisitionData.getCampaign());
            Assert.assertEquals(acquisition.getSubchannel(), acquisitionData.getSubchannel());
            Assert.assertEquals(acquisition.getCostInCents(), acquisitionData.getCostInCents());

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

            // Test Update Account
            Account updateAccount = new Account();
            updateAccount.setAccountCode(account.getAccountCode());
            CustomFields fields = account.getCustomFields();
            fields.get(0).setValue("");
            fields.get(1).setValue("update this value");
            updateAccount.setCustomFields(fields);
            recurlyClient.updateAccount(updateAccount.getAccountCode(), updateAccount);
            Account getAccount = recurlyClient.getAccount(updateAccount.getAccountCode());
            Assert.assertEquals(getAccount.getCustomFields().size(), 1);
            Assert.assertEquals(getAccount.getCustomFields().get(0).getValue(), "update this value");

        } catch(Exception e) {
          System.out.print(e.getMessage());
        } finally {

            // Clean up
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testGetAccountBalance() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();

        try {
            final Account account = recurlyClient.createAccount(accountData);

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);

            final Adjustment adjustment = new Adjustment();
            adjustment.setUnitAmountInCents(150);
            adjustment.setCurrency(CURRENCY);

            recurlyClient.createAccountAdjustment(account.getAccountCode(), adjustment);
            final AccountBalance balance = recurlyClient.getAccountBalance(account.getAccountCode());

            Assert.assertEquals(balance.getBalanceInCents().getUnitAmountUSD(), new Integer(150));
            Assert.assertEquals(balance.getPastDue(), Boolean.FALSE);
        } finally {
            // Clean up
            recurlyClient.clearBillingInfo(accountData.getAccountCode());
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testCreateItem() throws Exception {
        final Item itemData = TestUtils.createRandomItem();
        
        try {
            // Create an item
            final Item item = recurlyClient.createItem(itemData);
            Assert.assertNotNull(item);
            Assert.assertTrue(recurlyClient.getItems().size() > 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Delete the item
            recurlyClient.deleteItem(itemData.getItemCode());
        }
    }

    @Test(groups = "integration")
    public void testUpdateItem() throws Exception {
        final Item itemData = TestUtils.createRandomItem();

        try {
            // Create an item
            final Item item = recurlyClient.createItem(itemData);
            final Item itemChanges = new Item(); // Start with a fresh item object for changes
            Assert.assertNotNull(item);

            // Set the itemcode to identify which item to change
            itemChanges.setItemCode(itemData.getItemCode());

            // Change some attributes
            itemChanges.setName("A new name");
            itemChanges.setDescription("A new description");
            // **custom fields must be configured through ui**
            // final CustomFields customFields = new CustomFields();
            // final CustomField field = new CustomField();
            // field.setName("size");
            // field.setValue("small");
            // customFields.add(field);
            // itemChanges.setCustomFields(customFields);

            // Send off the changes and get the updated object
            final Item updatedItem = recurlyClient.updateItem(itemChanges.getItemCode(), itemChanges);

            Assert.assertNotNull(updatedItem);
            Assert.assertEquals(updatedItem.getName(), "A new name");
            Assert.assertEquals(updatedItem.getDescription(), "A new description");
            // Assert.assertEquals(updatedItem.getCustomFields(), itemChanges.getCustomFields());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Delete the item
            recurlyClient.deleteItem(itemData.getItemCode());
        }
    }

    @Test(groups = "integration")
    public void testReactivateItem() throws Exception {
        final Item itemData = TestUtils.createRandomItem();

        try {
            // Create an item
            final Item item = recurlyClient.createItem(itemData);
            Assert.assertNotNull(item);
            Assert.assertTrue(recurlyClient.getItems().size() > 0);
            // Delete the item
            recurlyClient.deleteItem(item.getItemCode());
            final Item deletedItem = recurlyClient.getItem(item.getItemCode());
            Assert.assertEquals(deletedItem.getState(), "inactive");
            // Reactivate the item
            recurlyClient.reactivateItem(item.getItemCode());
            final Item reactivatedItem = recurlyClient.getItem(item.getItemCode());
            Assert.assertEquals(reactivatedItem.getState(), "active");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Delete the item
            recurlyClient.deleteItem(itemData.getItemCode());
        }
    }

    @Test(groups = "integration")
    public void testCreatePlan() throws Exception {
        final Plan planData = TestUtils.createRandomPlan();
        try {
            // Create a plan
            final Plan plan = recurlyClient.createPlan(planData);

            // test creation of plan
            Assert.assertNotNull(plan);
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
    public void testUpdatePlan() throws Exception {
        final Plan planData = TestUtils.createRandomPlan();

        try {
            // Create a plan
            final DateTime creationDateTime = new DateTime(DateTimeZone.UTC);
            final Plan plan = recurlyClient.createPlan(planData);
            final Plan planChanges = new Plan(); // Start with a fresh plan object for changes

            Assert.assertNotNull(plan);

            // Set the plancode to identify which plan to change
            planChanges.setPlanCode(planData.getPlanCode());

            // Change some attributes
            planChanges.setName("A new name");
            planChanges.setDescription("A new description");

            // Send off the changes and get the updated object
            final Plan updatedPlan = recurlyClient.updatePlan(planChanges);

            Assert.assertNotNull(updatedPlan);
            Assert.assertEquals(updatedPlan.getName(), "A new name");
            Assert.assertEquals(updatedPlan.getDescription(), "A new description");
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
        final Coupon couponDataForPlan = TestUtils.createRandomCoupon();

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

            // Create a coupon for the plan
            couponDataForPlan.setAppliesToAllPlans(false);
            final PlanCodes planCodes = new PlanCodes();
            planCodes.add(new PlanCode(plan.getPlanCode()));
            couponDataForPlan.setPlanCodes(planCodes);
            Coupon couponForPlan = recurlyClient.createCoupon(couponDataForPlan);

            // Set up a subscription
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            subscriptionData.setRemainingBillingCycles(2);
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
            //Assert.assertEquals(subscriptionPreview.getRemainingBillingCycles(), subscriptionData.getRemainingBillingCycles());

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
            //Assert.assertEquals(subscription.getRemainingBillingCycles(), subscriptionData.getRemainingBillingCycles());

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
            final CustomFields customFields = new CustomFields();
            customFields.add(TestUtils.createRandomCustomField("food"));
            subscriptionNotesData.setCustomFields(customFields);

            recurlyClient.updateSubscriptionNotes(subscription.getUuid(), subscriptionNotesData);
            final Subscription subscriptionWithNotes = recurlyClient.getSubscription(subscription.getUuid());

            // Verify that the notes were updated
            Assert.assertNotNull(subscriptionWithNotes);
            Assert.assertEquals(subscriptionWithNotes.getTermsAndConditions(), subscriptionNotesData.getTermsAndConditions());
            Assert.assertEquals(subscriptionWithNotes.getCustomerNotes(), subscriptionNotesData.getCustomerNotes());
            Assert.assertEquals(subscriptionWithNotes.getCustomFields().get(0).getValue(), subscriptionNotesData.getCustomFields().get(0).getValue());

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
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
            // Delete the Plan
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void getInvoiceSubscriptions() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);
            account.setAccountCode(accountData.getAccountCode());

            // Create BillingInfo
            billingInfoData.setAccount(account);
            final BillingInfo billingInfo = recurlyClient.createOrUpdateBillingInfo(billingInfoData);
            Assert.assertNotNull(billingInfo);
            final BillingInfo retrievedBillingInfo = recurlyClient.getBillingInfo(account.getAccountCode());
            Assert.assertNotNull(retrievedBillingInfo);

            // Create a plan
            final Plan plan = recurlyClient.createPlan(planData);
            
            // Set up a subscription to invoice
            final Subscription invoiceSubscription = new Subscription();
            invoiceSubscription.setPlanCode(plan.getPlanCode());

            final Subscriptions subscriptions = new Subscriptions();
            subscriptions.add(invoiceSubscription);

            // Set account and subscriptions to purchase
            final Purchase purchaseData = new Purchase();
            purchaseData.setAccount(accountData);
            purchaseData.setSubscriptions(subscriptions);
            purchaseData.setCollectionMethod("automatic");
            purchaseData.setCurrency("USD");

            // // Create invoice collection
            final InvoiceCollection collection = recurlyClient.purchase(purchaseData);
            final Invoice invoiceData = collection.getChargeInvoice();
            // Do a lookup for subs for given invoice
            final Subscriptions isubs = recurlyClient.getInvoiceSubscriptions(invoiceData.getId());
            Assert.assertNotNull(isubs);
        } finally {
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
            // Delete the Plan
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testCreateSubscriptionWithCustomFields() throws Exception {
        final List<AddOn> addons = new ArrayList<AddOn>();
        final Plan planData = TestUtils.createRandomPlan();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final CustomField accountField = TestUtils.createRandomCustomField("acct_field");
        final CustomFields accountFields = new CustomFields();
        accountFields.add(accountField);
        final CustomField subField = TestUtils.createRandomCustomField("sub_field");
        final CustomFields subFields = new CustomFields();
        subFields.add(subField);
        final Account accountData = TestUtils.createRandomAccount();
        accountData.setBillingInfo(billingInfoData);
        billingInfoData.setAccount(null); // prevents double-POSTing account data inside billing_info
        accountData.setCustomFields(accountFields);
        try {
            // Create the plan
            final Plan plan = recurlyClient.createPlan(planData);
            Subscription subscriptionData = TestUtils.createRandomSubscription(CURRENCY, plan, accountData, addons);
            subscriptionData.setCustomFields(subFields);
            // Creates the account w/ billing_info, subscription and custom fields on account & subscription at once
            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);
            final Account account = recurlyClient.getAccount(accountData.getAccountCode());
            Assert.assertEquals(subscription.getCustomFields(), subFields);
            Assert.assertEquals(account.getCustomFields(), accountFields);
        } finally {
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

            recurlyClient.createAccountAdjustment(accountData.getAccountCode(), a);

            // Post an invoice/invoice the adjustment
            final Invoice invoiceData = new Invoice();
            invoiceData.setLineItems(null);
            final InvoiceCollection collection = recurlyClient.postAccountInvoice(accountData.getAccountCode(), invoiceData);
            Assert.assertNotNull(collection.getChargeInvoice());

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
            final Invoice invoiceFail = recurlyClient.postAccountInvoice(accountData.getAccountCode(), failInvoiceData).getChargeInvoice();
            Assert.assertNotNull(invoiceFail);

            // Check to see if the adjustment was invoiced properly
            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), null, Adjustments.AdjustmentState.PENDING);
            Assert.assertEquals(retrievedAdjustments.size(), 0, "Retrieved Adjustments marked as pending although none should be.");

            retrievedAdjustments = recurlyClient.getAccountAdjustments(accountData.getAccountCode(), null, Adjustments.AdjustmentState.INVOICED);
            Assert.assertEquals(retrievedAdjustments.size(), 2, "Not all Adjustments marked as invoiced although all should be.");

        } finally {
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testCreateInvoiceAndRetrieveInvoicePdf() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();

        PDDocument pdDocument = null;
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
            final Invoice invoice = recurlyClient.postAccountInvoice(accountData.getAccountCode(), invoiceData).getChargeInvoice();
            Assert.assertNotNull(invoice);

            InputStream pdfInputStream = recurlyClient.getInvoicePdf(invoice.getId());
            Assert.assertNotNull(pdfInputStream);

            pdDocument = PDDocument.load(pdfInputStream);
            String pdfString = new PDFTextStripper().getText(pdDocument);

            Assert.assertNotNull(pdfString);
            Assert.assertTrue(pdfString.contains("Invoice # " + invoice.getId()));
            Assert.assertTrue(pdfString.contains("Subtotal $" + 1.5));
            // Attempt to close the invoice
            final Invoice closedInvoice = recurlyClient.markInvoiceSuccessful(invoice.getId());
            Assert.assertEquals(closedInvoice.getState(), "paid", "Invoice not closed successfully");

        } finally {
            if (pdDocument != null) {
                pdDocument.close();
            }
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
            final Invoice invoice = recurlyClient.postAccountInvoice(accountData.getAccountCode(), invoiceData).getChargeInvoice();
            Assert.assertNotNull(invoice);

            // Attempt to close the invoice
            final Invoice closedInvoice = recurlyClient.markInvoiceSuccessful(invoice.getId());
            Assert.assertEquals(closedInvoice.getState(), "paid", "Invoice not closed successfully");

            // Create an Adjustment
            final Adjustment b = new Adjustment();
            b.setUnitAmountInCents(250);
            b.setCurrency(CURRENCY);

            final Adjustment createdB = recurlyClient.createAccountAdjustment(accountData.getAccountCode(), b);

            // Post an invoice/invoice the adjustment
            final Invoice failInvoiceData = new Invoice();
            failInvoiceData.setCollectionMethod("manual");
            failInvoiceData.setLineItems(null);
            final Invoice invoiceFail = recurlyClient.postAccountInvoice(accountData.getAccountCode(), failInvoiceData).getChargeInvoice();
            Assert.assertNotNull(invoiceFail);

            // Attempt to fail the invoice
            final Invoice failedInvoice = recurlyClient.markInvoiceFailed(invoiceFail.getId()).getChargeInvoice();
            Assert.assertEquals(failedInvoice.getState(), "failed", "Invoice not failed successfully");

            // Create an Adjustment
            final Adjustment c = new Adjustment();
            c.setUnitAmountInCents(450);
            c.setCurrency(CURRENCY);

            final Adjustment created = recurlyClient.createAccountAdjustment(accountData.getAccountCode(), c);

            // Post an invoice/invoice the adjustment
            final Invoice externalInvoiceData = new Invoice();
            externalInvoiceData.setCollectionMethod("manual");
            externalInvoiceData.setLineItems(null);
            final Invoice invoiceExternal = recurlyClient.postAccountInvoice(accountData.getAccountCode(), externalInvoiceData).getChargeInvoice();
            Assert.assertNotNull(invoiceExternal);

            //create an external payment to pay off the invoice
            final Transaction externalPaymentData = new Transaction();
            externalPaymentData.setPaymentMethod("other");
            final DateTime collectionDateTime = new DateTime(DateTimeZone.UTC);
            externalPaymentData.setCollectedAt(collectionDateTime);
            externalPaymentData.setAmountInCents(450);

            final Transaction externalPayment = recurlyClient.enterOfflinePayment(invoiceExternal.getId(), externalPaymentData);
            Assert.assertNotNull(externalPayment);
            Assert.assertEquals(externalPayment.getInvoice().getState(), "paid", "Invoice not closed successfully");

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
            final Subscription sub = recurlyClient.createSubscription(subscriptionData);

            // Create transaction from subscription
            final Transaction subTransaction = sub.getInvoice().getTransactions().get(0);
            // Fetch subscriptions from created transaction
            final Subscription fetchedSub = recurlyClient.getTransactionSubscriptions(subTransaction.getUuid()).get(0);
            // Test that the original subscription equals the fetched subscription
            Assert.assertEquals(sub.getUuid(), fetchedSub.getUuid());

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

            // Test Invoices retrieval
            final Invoices invoices = recurlyClient.getAccountInvoices(account.getAccountCode());
            // 2 Invoices are present (the first one is for the transaction, the second for the subscription)
            Assert.assertEquals(invoices.size(), 2, "Number of Invoices incorrect");
            Assert.assertEquals(invoices.get(0).getTotalInCents(), t.getAmountInCents(), "Amount in cents is not the same");
            final Integer total = subscriptionData.getUnitAmountInCents() + planData.getSetupFeeInCents().getUnitAmountUSD();
            Assert.assertEquals(invoices.get(1).getTotalInCents(), total, "Amount in cents is not the same");
        } finally {
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
            Thread.sleep(1000);  // TODO - can remove after Jan 18th 2017
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

            // Update addOn
            AddOn addOnData = new AddOn();
            addOnData.setName("New Name");

            final AddOn updatedAddOn = recurlyClient.updateAddOn(plan.getPlanCode(), addOnRecurly.getAddOnCode(), addOnData);
            Assert.assertEquals(updatedAddOn.getName(), "New Name");
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

            // Also test getting all coupons
            Coupons coupons = recurlyClient.getCoupons();
            Assert.assertNotNull(coupons);

        } finally {
            recurlyClient.deleteCoupon(couponData.getCouponCode());
        }
    }

    @Test(groups = "integration")
    public void testBulkCoupons() throws Exception {
        final Coupon couponData = TestUtils.createRandomCoupon();
        couponData.setType(Coupon.Type.bulk);
        couponData.setUniqueCodeTemplate(String.format("'%s'99999", couponData.getCouponCode()));

        Coupon coupon = recurlyClient.createCoupon(couponData);

        Coupon genCouponData = new Coupon();

        genCouponData.setNumberOfUniqueCodes(3);
        Coupons coupons = recurlyClient.generateUniqueCodes(coupon.getCouponCode(), genCouponData);
        Coupon c = coupons.get(0);
        System.out.println("Coupon c " + c.toString());
        Assert.assertEquals(coupons.size(), 3);
    }

    @Test(groups = "integration")
    public void testUpdateSubscriptions() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();
        final Plan plan2Data = TestUtils.createRandomPlan(CURRENCY);
        final ShippingAddress shad = TestUtils.createRandomShippingAddress();
        final ShippingAddresses shads = new ShippingAddresses();
        shads.add(shad);
        accountData.setShippingAddresses(shads);

        try {
            // Create a user
            final Account account = recurlyClient.createAccount(accountData);
            // fetch the shipping address object that was created with the account
            final ShippingAddress originalShippingAddress = recurlyClient.getAccountShippingAddresses(account.getAccountCode()).get(0);

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
            final Account accountCodeData = new Account();
            accountCodeData.setAccountCode(account.getAccountCode());
            subscriptionData.setAccount(accountCodeData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            // set the shipping address to account's first shipping address
            subscriptionData.setShippingAddressId(originalShippingAddress.getId());
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
            Assert.assertNotNull(subscriptionUpdatedPreview.getInvoiceCollection().getChargeInvoice());
            Assert.assertEquals(subscription.getUuid(), subscriptionUpdatedPreview.getUuid());
            Assert.assertNotEquals(subscription.getPlan(), subscriptionUpdatedPreview.getPlan());
            Assert.assertEquals(plan2.getPlanCode(), subscriptionUpdatedPreview.getPlan().getPlanCode());

            // Update with a new shipping address
            subscriptionUpdateData.setShippingAddress(TestUtils.createRandomShippingAddress());

            // Update the subscription
            final Subscription subscriptionUpdated = recurlyClient.updateSubscription(subscription.getUuid(), subscriptionUpdateData);

            Assert.assertNotNull(subscriptionUpdated);
            Assert.assertEquals(subscription.getUuid(), subscriptionUpdated.getUuid());
            Assert.assertNotEquals(subscription.getPlan(), subscriptionUpdated.getPlan());
            Assert.assertEquals(plan2.getPlanCode(), subscriptionUpdated.getPlan().getPlanCode());
        } finally {
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
        final Coupon secondCouponData = TestUtils.createRandomCoupon();
        final Coupon subscriptionLevelCouponData = TestUtils.createRandomCoupon();
        subscriptionLevelCouponData.setRedemptionResource(RedemptionResource.subscription);

        try {
            final Account account = recurlyClient.createAccount(accountData);
            final Plan plan = recurlyClient.createPlan(planData);
            final Coupon coupon = recurlyClient.createCoupon(couponData);
            final Coupon secondCoupon = recurlyClient.createCoupon(secondCouponData);
            final Coupon subscriptionLevelCoupon = recurlyClient.createCoupon(subscriptionLevelCouponData);

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

            // Redeem another coupon
            final Redemption secondRedemptionData = new Redemption();
            secondRedemptionData.setAccountCode(account.getAccountCode());
            secondRedemptionData.setCurrency(CURRENCY);
            Redemption secondRedemption = recurlyClient.redeemCoupon(secondCoupon.getCouponCode(), secondRedemptionData);
            Assert.assertNotNull(secondRedemption);
            Assert.assertEquals(secondRedemption.getCoupon().getCouponCode(), secondCoupon.getCouponCode());
            Assert.assertEquals(secondRedemption.getAccount().getAccountCode(), account.getAccountCode());
            Assert.assertFalse(secondRedemption.getSingleUse());
            Assert.assertEquals(secondRedemption.getTotalDiscountedInCents(), (Integer) 0);
            Assert.assertEquals(secondRedemption.getState(), "active");
            Assert.assertEquals(secondRedemption.getCurrency(), CURRENCY);

            Redemptions redemptions = recurlyClient.getCouponRedemptionsByAccount(account.getAccountCode());
            Assert.assertEquals(redemptions.size(), 2);

            // Remove both coupon redemptions
            recurlyClient.deleteCouponRedemption(account.getAccountCode(), redemption.getUuid());
            recurlyClient.deleteCouponRedemption(account.getAccountCode(), secondRedemption.getUuid());
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

            // Redeem a subscription level coupon
            final Redemption subscriptionLevelRedemptionData = new Redemption();
            subscriptionLevelRedemptionData.setAccountCode(account.getAccountCode());
            subscriptionLevelRedemptionData.setCurrency(CURRENCY);
            subscriptionLevelRedemptionData.setSubscriptionUuid(subscription.getUuid());
            Redemption subscriptionLevelRedemption = recurlyClient.redeemCoupon(subscriptionLevelCoupon.getCouponCode(), subscriptionLevelRedemptionData);
            Assert.assertNotNull(subscriptionLevelRedemption.getUuid());

            // List the redemptions on the subscription
            Redemptions subRedemptions = recurlyClient.getCouponRedemptionsBySubscription(subscription.getUuid(), new QueryParams());
            Assert.assertEquals(subRedemptions.size(), 1);

        } finally {
            recurlyClient.closeAccount(accountData.getAccountCode());
            recurlyClient.deletePlan(planData.getPlanCode());
            recurlyClient.deleteCoupon(couponData.getCouponCode());
            recurlyClient.deleteCoupon(secondCouponData.getCouponCode());
            recurlyClient.deleteCoupon(subscriptionLevelCouponData.getCouponCode());
        }
    }

    @Test(groups = "integration")
    public void testCreateTrialExtensionCoupon() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);
        final Coupon couponData = TestUtils.createRandomCoupon();

        couponData.setName("apitrialext");
        couponData.setFreeTrialAmount(3);
        couponData.setFreeTrialUnit("month");
        couponData.setDiscountType("free_trial");

        final LocalDateTime now = LocalDateTime.now();

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
            // set our coupon code
            subscriptionData.setCouponCode(coupon.getCouponCode());

            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);

            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);

            Assert.assertNotNull(subscription);

            // this code should be fine as long as we don't try to add more than 12 months
            int expectedMonth = now.getMonthOfYear() + 3;
            if (expectedMonth > 12) expectedMonth = expectedMonth - 12;
            Assert.assertEquals(subscription.getTrialEndsAt().getMonthOfYear(), expectedMonth);
        } finally {
            recurlyClient.closeAccount(accountData.getAccountCode());
            recurlyClient.deletePlan(planData.getPlanCode());
            recurlyClient.deleteCoupon(couponData.getCouponCode());
        }
    }

    @Test(groups = "integration")
    public void testRedeemGiftCardOnSubscription() throws Exception {
        final GiftCard giftCardData = TestUtils.createRandomGiftCard();
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);

        try {
            // Purchase a gift card
            final GiftCard giftCard = recurlyClient.purchaseGiftCard(giftCardData);

            Assert.assertEquals(giftCard.getProductCode(), giftCardData.getProductCode());
            Assert.assertNull(giftCard.getRedeemedAt());

            // Let's redeem on a subscription
            final Plan plan = recurlyClient.createPlan(planData);
            final Account account = giftCard.getGifterAccount();
            final GiftCard redemptionData = new GiftCard();
            final Subscription subscriptionData = new Subscription();

            // set our gift card redemption data
            redemptionData.setRedemptionCode(giftCard.getRedemptionCode());
            subscriptionData.setGiftCard(redemptionData);

            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(account);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);

            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);
            Assert.assertNotNull(subscription);

            final GiftCard redeemedCard = recurlyClient.getGiftCard(giftCard.getId());

            Assert.assertNotNull(redeemedCard.getRedeemedAt());
        } finally {
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testRedeemGiftCardOnAccount() throws Exception {
        final GiftCard giftCardData = TestUtils.createRandomGiftCard();

        // Purchase a gift card
        final GiftCard giftCard = recurlyClient.purchaseGiftCard(giftCardData);

        Assert.assertEquals(giftCard.getProductCode(), giftCardData.getProductCode());
        Assert.assertNull(giftCard.getRedeemedAt());

        // Let's redeem on an account
        final Account gifterAccount = giftCard.getGifterAccount();
        Assert.assertNotNull(gifterAccount);

        final String redemptionCode = giftCard.getRedemptionCode();
        final String gifterAccountCode = gifterAccount.getAccountCode();
        final GiftCard redeemedCard = recurlyClient.redeemGiftCard(redemptionCode, gifterAccountCode);

        Assert.assertNotNull(redeemedCard.getRedeemedAt());
    }

    @Test(groups = "integration")
    public void testPreviewGiftCardPurchase() throws Exception {
        final GiftCard giftCardData = TestUtils.createRandomGiftCard();

        // Preview a gift card purchase
        final GiftCard giftCard = recurlyClient.previewGiftCard(giftCardData);

        // Should be the purchased gift card
        Assert.assertEquals(giftCard.getProductCode(), giftCardData.getProductCode());

        // But should not be created
        Assert.assertNull(giftCard.getId());
        Assert.assertNull(giftCard.getCreatedAt());
    }

    @Test(groups = "integration")
    public void testShippingAddressesOnAccount() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final ShippingAddress shippingAddress1 = TestUtils.createRandomShippingAddress();
        final ShippingAddress shippingAddress2 = TestUtils.createRandomShippingAddress();
        final ShippingAddresses shippingAddressesData = new ShippingAddresses();

        try {
            shippingAddressesData.add(shippingAddress1);
            shippingAddressesData.add(shippingAddress2);

            accountData.setShippingAddresses(shippingAddressesData);

            final Account account = recurlyClient.createAccount(accountData);

            ShippingAddresses shippingAddresses = recurlyClient.getAccountShippingAddresses(account.getAccountCode());

            Assert.assertEquals(shippingAddresses.size(), 2);
        } finally {
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testShippingAddressesOnSubscription() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);
        final ShippingAddress shippingAddressData = TestUtils.createRandomShippingAddress();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();

        billingInfoData.setAccount(null); // null out random account fixture
        accountData.setBillingInfo(billingInfoData); // add the billing info to account data

        try {
            final Account account = recurlyClient.createAccount(accountData);
            final Plan plan = recurlyClient.createPlan(planData);

            // Subscribe the user to the plan
            final Subscription subscriptionData = new Subscription();

            // set our shipping address
            subscriptionData.setShippingAddress(shippingAddressData);

            // set our sub data
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);

            // attach the account
            final Account existingAccount = new Account();
            existingAccount.setAccountCode(account.getAccountCode());
            subscriptionData.setAccount(existingAccount);

            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);

            Assert.assertNotNull(subscription.getHref());
        } finally {
            recurlyClient.closeAccount(accountData.getAccountCode());
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testInvoices() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();

        billingInfoData.setAccount(null); // null out random account fixture
        accountData.setBillingInfo(billingInfoData); // add the billing info to account data

        try {
            final Account account = recurlyClient.createAccount(accountData);
            final Plan plan = recurlyClient.createPlan(planData);

            // Add some adjustments to the account
            final Adjustment adjustmentData1 = new Adjustment();
            adjustmentData1.setCurrency("USD");
            adjustmentData1.setUnitAmountInCents(100);
            adjustmentData1.setDescription("A description of an account adjustment1");

            recurlyClient.createAccountAdjustment(account.getAccountCode(), adjustmentData1);

            final Adjustment adjustmentData2 = new Adjustment();
            adjustmentData2.setCurrency("USD");
            adjustmentData2.setUnitAmountInCents(100);
            adjustmentData2.setDescription("A description of an account adjustment2");

            recurlyClient.createAccountAdjustment(account.getAccountCode(), adjustmentData2);

            final Invoice invoiceData = new Invoice();
            invoiceData.setCollectionMethod("automatic");

            final Invoice invoice = recurlyClient.postAccountInvoice(account.getAccountCode(), invoiceData).getChargeInvoice();

            Assert.assertEquals(invoice.getTotalInCents(), new Integer(200));

            // wait for the invoice to be marked paid
            // has to happen asynchronously on the server
            Thread.sleep(5000);

            // Test updating an invoice while we're at it
            Address address = TestUtils.createRandomAddress();

            Invoice updatedInvoice = new Invoice();
            updatedInvoice.setAddress(address);
            updatedInvoice.setPoNumber("9876");
            updatedInvoice.setTermsAndConditions("T&C");
            updatedInvoice.setCustomerNotes("Some notes");

            recurlyClient.updateInvoice(invoice.getId(), updatedInvoice);

            // Now refund the invoice
            final InvoiceRefund refundOptions = new InvoiceRefund();
            refundOptions.setRefundMethod(RefundMethod.transaction_first);
            refundOptions.setAmountInCents(100);
            refundOptions.setCreditCustomerNotes("Credit Customer Notes");
            refundOptions.setExternalRefund(true);
            refundOptions.setPaymentMethod("credit_card");
            final Invoice refundInvoice = recurlyClient.refundInvoice(invoice.getId(), refundOptions);

            Assert.assertEquals(refundInvoice.getTotalInCents(), new Integer(-100));
            Assert.assertEquals(refundInvoice.getSubtotalInCents(), new Integer(-100));
            Assert.assertEquals(refundInvoice.getTransactions().get(0).getAction(), "refund");

            // The refundInvoice should have an original_invoices of the original invoice
            final Invoices originalInvoices = recurlyClient.getOriginalInvoices(refundInvoice.getId());
            Assert.assertEquals(originalInvoices.get(0).getId(), invoice.getId());
        } finally {
            recurlyClient.closeAccount(accountData.getAccountCode());
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testLineItemInvoiceRefund() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();

        billingInfoData.setAccount(null); // null out random account fixture
        accountData.setBillingInfo(billingInfoData); // add the billing info to account data

        try {
            final Account account = recurlyClient.createAccount(accountData);
            final Plan plan = recurlyClient.createPlan(planData);

            // Add some adjustments to the account
            final Adjustment adjustmentData1 = new Adjustment();
            adjustmentData1.setCurrency("USD");
            adjustmentData1.setUnitAmountInCents(100);
            adjustmentData1.setDescription("A description of an account adjustment1");

            recurlyClient.createAccountAdjustment(account.getAccountCode(), adjustmentData1);

            final Adjustment adjustmentData2 = new Adjustment();
            adjustmentData2.setCurrency("USD");
            adjustmentData2.setUnitAmountInCents(100);
            adjustmentData2.setDescription("A description of an account adjustment2");

            recurlyClient.createAccountAdjustment(account.getAccountCode(), adjustmentData2);

            final Invoice invoiceData = new Invoice();
            invoiceData.setCollectionMethod("automatic");

            Invoice invoice = recurlyClient.postAccountInvoice(account.getAccountCode(), invoiceData).getChargeInvoice();

            Assert.assertEquals(invoice.getTotalInCents(), new Integer(200));

            // wait for the invoice to be marked paid
            // has to happen asynchronously on the server
            Thread.sleep(5000);

            // refetch the invoice
            invoice = recurlyClient.getInvoice(invoice.getId());

            final ArrayList<AdjustmentRefund> lineItems = new ArrayList<AdjustmentRefund>();

            // let's just refund the first adjustment
            // we can use "toAdjustmentRefund" on the adjustment to turn it
            // into an AdjustmentRefund
            final AdjustmentRefund adjustmentRefund = invoice.getLineItems().get(0).toAdjustmentRefund();

            // we could change the quantity here or the prorating settings if we want, defaults to full quantity
            // adjustmentRefund.setQuantity(1);
            lineItems.add(adjustmentRefund);

            final InvoiceRefund refundOptions = new InvoiceRefund();
            refundOptions.setRefundMethod(RefundMethod.transaction_first);
            refundOptions.setLineItems(lineItems);
            final Invoice refundInvoice = recurlyClient.refundInvoice(invoice.getId(), refundOptions);

            Assert.assertEquals(refundInvoice.getTotalInCents(), new Integer(-100));
            Assert.assertEquals(refundInvoice.getSubtotalInCents(), new Integer(-100));
            Assert.assertEquals(refundInvoice.getTransactions().get(0).getAction(), "refund");

            Assert.assertEquals(refundInvoice.getLineItems().size(), 1);
            final Adjustment lineItem = refundInvoice.getLineItems().get(0);
            Assert.assertEquals(lineItem.getQuantity(), new Integer(1));
        } finally {
            recurlyClient.closeAccount(accountData.getAccountCode());
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testCounts() throws Exception {
        final QueryParams qp = new QueryParams();
        qp.setBeginTime(new DateTime("2017-01-01T00:00:00Z"));

        Integer accountCount = recurlyClient.getAccountsCount(qp);
        Assert.assertNotNull(accountCount);
        Integer couponsCount = recurlyClient.getCouponsCount(qp);
        Assert.assertNotNull(couponsCount);
        Integer transactionsCount = recurlyClient.getTransactionsCount(qp);
        Assert.assertNotNull(transactionsCount);
        Integer plansCount = recurlyClient.getPlansCount(qp);
        Assert.assertNotNull(plansCount);
        Integer giftCardsCount = recurlyClient.getGiftCardsCount(qp);
        Assert.assertNotNull(giftCardsCount);
    }

    @Test(groups = "integration")
    public void testPurchase() throws Exception {
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Adjustments adjustmentsData = new Adjustments();
        final Adjustment adjustmentData = TestUtils.createRandomAdjustment();
        final GiftCard giftCardData = TestUtils.createRandomGiftCard();
        final ShippingAddress shippingAddress = TestUtils.createRandomShippingAddress();
        final ShippingAddresses addresses = new ShippingAddresses();

        giftCardData.setProductCode("test_gift_card");
        giftCardData.setCurrency("USD");

        adjustmentData.setCurrency(null); // this one accepted by site
        adjustmentsData.add(adjustmentData);

        billingInfoData.setAccount(null); // null out random account fixture
        accountData.setBillingInfo(billingInfoData); // add the billing info to account data
 
        try {
            final Plan plan = recurlyClient.createPlan(planData);
            final GiftCard purchasedGiftCard = recurlyClient.purchaseGiftCard(giftCardData);
            final GiftCard redemptionData = new GiftCard();
            redemptionData.setRedemptionCode(purchasedGiftCard.getRedemptionCode());
            final ArrayList<String> couponCodes = new ArrayList<String>(Arrays.asList("ascu2wprjk", "vttx1luuwz"));
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            final Subscriptions subscriptions = new Subscriptions();
            subscriptions.add(subscriptionData);

            final Purchase purchaseData = new Purchase();
            purchaseData.setAccount(accountData);
            purchaseData.setAdjustments(adjustmentsData);
            purchaseData.setCollectionMethod("automatic");
            purchaseData.setAdjustments(adjustmentsData);
            purchaseData.setCurrency("USD");
            purchaseData.setShippingAddress(shippingAddress);
            purchaseData.setSubscriptions(subscriptions);
            purchaseData.setGiftCard(redemptionData);
            purchaseData.setCouponCodes(couponCodes);
            purchaseData.setCustomerNotes("Customer Notes");
            purchaseData.setTermsAndConditions("Terms and Conditions");
            purchaseData.setVatReverseChargeNotes("VAT Reverse Charge Notes");

            final Invoice invoice = recurlyClient.purchase(purchaseData).getChargeInvoice();
            Assert.assertNotNull(invoice.getInvoiceNumber());

            // Ensure Adjustment's subscription id is valid
            String subscriptionId = invoice.getLineItems().get(0).getSubscriptionId();
            Subscription sub = recurlyClient.getSubscription(subscriptionId);
            Assert.assertEquals(sub.getUuid(), subscriptionId);
        } finally {
            recurlyClient.closeAccount(accountData.getAccountCode());
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testAuthorizePurchase() throws Exception {
        final Plan planData = TestUtils.createRandomPlan(CURRENCY);
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Adjustments adjustmentsData = new Adjustments();
        final Adjustment adjustmentData = TestUtils.createRandomAdjustment();
        final GiftCard giftCardData = TestUtils.createRandomGiftCard();
        final ShippingAddress shippingAddress = TestUtils.createRandomShippingAddress();
        final ShippingAddresses addresses = new ShippingAddresses();

        giftCardData.setProductCode("test_gift_card");
        giftCardData.setCurrency("USD");

        adjustmentData.setCurrency(null); // this one accepted by site
        adjustmentsData.add(adjustmentData);

        billingInfoData.setAccount(null); // null out random account fixture
        accountData.setBillingInfo(billingInfoData); // add the billing info to account data

        // these 2 things are required by this endpoint
        billingInfoData.setExternalHppType("adyen"); // set the external hpp collection to adyen
        accountData.setEmail("benjamin.dumonde@example.com");

        try {
            final Plan plan = recurlyClient.createPlan(planData);
            final GiftCard purchasedGiftCard = recurlyClient.purchaseGiftCard(giftCardData);
            final GiftCard redemptionData = new GiftCard();
            redemptionData.setRedemptionCode(purchasedGiftCard.getRedemptionCode());
            final ArrayList<String> couponCodes = new ArrayList<String>(Arrays.asList("ascu2wprjk", "vttx1luuwz"));
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            final Subscriptions subscriptions = new Subscriptions();
            subscriptions.add(subscriptionData);

            final Purchase purchaseData = new Purchase();
            purchaseData.setAccount(accountData);
            purchaseData.setAdjustments(adjustmentsData);
            purchaseData.setCollectionMethod("automatic");
            purchaseData.setAdjustments(adjustmentsData);
            purchaseData.setCurrency("USD");
            purchaseData.setShippingAddress(shippingAddress);
            purchaseData.setSubscriptions(subscriptions);
            purchaseData.setGiftCard(redemptionData);
            purchaseData.setCouponCodes(couponCodes);
            purchaseData.setCustomerNotes("Customer Notes");
            purchaseData.setTermsAndConditions("Terms and Conditions");
            purchaseData.setVatReverseChargeNotes("VAT Reverse Charge Notes");

            final Invoice invoice = recurlyClient.authorizePurchase(purchaseData).getChargeInvoice();
            Assert.assertNotNull(invoice.getUuid());
        } finally {
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

        @Test(groups = "integration")
    public void testAccountAcquisition() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();

        try {
            final Account account = recurlyClient.createAccount(accountData);
            AccountAcquisition acquisitionData = TestUtils.createRandomAccountAcquisition();

            // test create
            AccountAcquisition acquisition = recurlyClient.createAccountAcquisition(account.getAccountCode(), acquisitionData);
            Assert.assertEquals(acquisition.getSubchannel(), acquisitionData.getSubchannel());
            Assert.assertEquals(acquisition.getCampaign(), acquisitionData.getCampaign());
            Assert.assertEquals(acquisition.getChannel(), acquisitionData.getChannel());
            Assert.assertEquals(acquisition.getCurrency(), acquisitionData.getCurrency());
            Assert.assertEquals(acquisition.getCostInCents(), acquisitionData.getCostInCents());

            // test lookup
            acquisition = recurlyClient.getAccountAcquisition(account.getAccountCode());
            Assert.assertEquals(acquisition.getSubchannel(), acquisitionData.getSubchannel());
            Assert.assertEquals(acquisition.getCampaign(), acquisitionData.getCampaign());
            Assert.assertEquals(acquisition.getChannel(), acquisitionData.getChannel());
            Assert.assertEquals(acquisition.getCurrency(), acquisitionData.getCurrency());
            Assert.assertEquals(acquisition.getCostInCents(), acquisitionData.getCostInCents());

            // test update
            acquisitionData.setSubchannel("updated");
            acquisitionData.setCampaign("updated");
            acquisitionData.setCostInCents(0);
            acquisitionData.setCurrency("EUR");
            acquisitionData.setChannel(AcquisitionChannel.OTHER);

            acquisition = recurlyClient.updateAccountAcquisition(account.getAccountCode(), acquisitionData);
            Assert.assertEquals(acquisition.getSubchannel(), "updated");
            Assert.assertEquals(acquisition.getCampaign(), "updated");
            Assert.assertEquals(acquisition.getChannel(), AcquisitionChannel.OTHER);
            Assert.assertEquals(acquisition.getCurrency(), "EUR");
            Assert.assertEquals(acquisition.getCostInCents(), new Integer(0));

            // test clear
            recurlyClient.deleteAccountAcquisition(account.getAccountCode());

            try {
                recurlyClient.getAccountAcquisition(account.getAccountCode());
                Assert.fail("getAccountAcquisition should have failed to find acquisition details for this account");
            } catch (final RecurlyAPIException e) {
                Assert.assertNotNull(e);
                // good
            }
        } finally {
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }

    @Test(groups = "integration")
    public void testPauseSubscription() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final BillingInfo billingInfoData = TestUtils.createRandomBillingInfo();
        final Plan planData = TestUtils.createRandomPlan();
        billingInfoData.setAccount(null);
        accountData.setBillingInfo(billingInfoData);
        try {
            // Create a plan
            final Plan plan = recurlyClient.createPlan(planData);

            // Set up a subscription
            final Subscription subscriptionData = new Subscription();
            subscriptionData.setPlanCode(plan.getPlanCode());
            subscriptionData.setAccount(accountData);
            subscriptionData.setCurrency(CURRENCY);
            subscriptionData.setUnitAmountInCents(1242);
            subscriptionData.setRemainingBillingCycles(2);

            // Create the user to the plan
            final Subscription subscription = recurlyClient.createSubscription(subscriptionData);
            // Test subscription creation
            Assert.assertNotNull(subscription);

            // schedule a pause for 1 cycle
            final Subscription scheduledPauseSub = recurlyClient.pauseSubscription(subscription.getUuid(), 1);
            Assert.assertEquals(scheduledPauseSub.getRemainingPauseCycles(), new Integer(1));
            Assert.assertEquals(scheduledPauseSub.getPausedAt().getClass(), DateTime.class);

            final Subscription canceledPauseSub = recurlyClient.pauseSubscription(subscription.getUuid(), 0);
            Assert.assertEquals(canceledPauseSub.getPausedAt(), null);
            Assert.assertEquals(canceledPauseSub.getRemainingPauseCycles(), null);
        } finally {
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
            // Delete the Plan
            recurlyClient.deletePlan(planData.getPlanCode());
        }
    }

    @Test(groups = "integration")
    public void testInvoicesCount() throws Exception {
        int invoiceCount = recurlyClient.getInvoicesCount(new QueryParams());
        Assert.assertTrue(invoiceCount > 0);
    }

    @Test(groups = "integration")
    public void testShippingAddresses() throws Exception {
        final Account accountData = TestUtils.createRandomAccount();
        final ShippingAddress shippingAddressData = TestUtils.createRandomShippingAddress();

        try {
            final Account account = recurlyClient.createAccount(accountData);

            final ShippingAddress shippingAddress = recurlyClient.createShippingAddress(account.getAccountCode(), shippingAddressData);
            final long shadId = shippingAddress.getId();

            Assert.assertNotNull(shadId);

            final ShippingAddress updatedRequest = new ShippingAddress();
            updatedRequest.setCity("Houston");

            final ShippingAddress updatedShippingAddress = recurlyClient.updateShippingAddress(account.getAccountCode(),
                    shadId, updatedRequest);

            Assert.assertEquals(updatedShippingAddress.getCity(), "Houston");

            // now delete the address
            recurlyClient.deleteShippingAddress(account.getAccountCode(), shadId);

            // check that it was deleted
            try {
                recurlyClient.getShippingAddress(account.getAccountCode(), shadId);
                Assert.fail("Should not have been able to fetch shipping address we just deleted");
            } catch (RecurlyAPIException ex) {
                final RecurlyAPIError err = ex.getRecurlyError();
                Assert.assertEquals(err.getSymbol(), "not_found");
            } catch (Exception ex) {
                Assert.fail("Fetching deleted shipping address should have failed with 404 and instead failed because: " + ex.getMessage());
            }

        } finally {
            // Close the account
            recurlyClient.closeAccount(accountData.getAccountCode());
        }
    }
}
