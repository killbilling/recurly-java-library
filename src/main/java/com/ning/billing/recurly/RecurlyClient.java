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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.xml.bind.DatatypeConverter;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.billing.recurly.model.Account;
import com.ning.billing.recurly.model.Accounts;
import com.ning.billing.recurly.model.AddOn;
import com.ning.billing.recurly.model.AddOns;
import com.ning.billing.recurly.model.Adjustment;
import com.ning.billing.recurly.model.Adjustments;
import com.ning.billing.recurly.model.BillingInfo;
import com.ning.billing.recurly.model.Coupon;
import com.ning.billing.recurly.model.Coupons;
import com.ning.billing.recurly.model.Errors;
import com.ning.billing.recurly.model.Invoice;
import com.ning.billing.recurly.model.Invoices;
import com.ning.billing.recurly.model.Plan;
import com.ning.billing.recurly.model.Plans;
import com.ning.billing.recurly.model.RecurlyAPIError;
import com.ning.billing.recurly.model.RecurlyObject;
import com.ning.billing.recurly.model.RecurlyObjects;
import com.ning.billing.recurly.model.Redemption;
import com.ning.billing.recurly.model.RefundOption;
import com.ning.billing.recurly.model.Subscription;
import com.ning.billing.recurly.model.SubscriptionUpdate;
import com.ning.billing.recurly.model.SubscriptionNotes;
import com.ning.billing.recurly.model.Subscriptions;
import com.ning.billing.recurly.model.Transaction;
import com.ning.billing.recurly.model.Transactions;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.StandardSystemProperty;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.google.common.net.HttpHeaders;

public class RecurlyClient {

    private static final Logger log = LoggerFactory.getLogger(RecurlyClient.class);

    public static final String RECURLY_DEBUG_KEY = "recurly.debug";
    public static final String RECURLY_PAGE_SIZE_KEY = "recurly.page.size";

    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private static final String PER_PAGE = "per_page=";

    private static final String X_RECORDS_HEADER_NAME = "X-Records";
    private static final String LINK_HEADER_NAME = "Link";

    private static final String GIT_PROPERTIES_FILE = "com/ning/billing/recurly/git.properties";
    @VisibleForTesting
    static final String GIT_COMMIT_ID_DESCRIBE_SHORT = "git.commit.id.describe-short";
    private static final Pattern TAG_FROM_GIT_DESCRIBE_PATTERN = Pattern.compile("recurly-java-library-([0-9]*\\.[0-9]*\\.[0-9]*)(-[0-9]*)?");

    public static final String FETCH_RESOURCE = "/recurly_js/result";

    /**
     * Checks a system property to see if debugging output is
     * required. Used internally by the client to decide whether to
     * generate debug output
     */
    private static boolean debug() {
        return Boolean.getBoolean(RECURLY_DEBUG_KEY);
    }

    /**
     * Returns the page Size to use when querying. The page size
     * is set as System.property: recurly.page.size
     */
    public static Integer getPageSize() {
        Integer pageSize;
        try {
            pageSize = new Integer(System.getProperty(RECURLY_PAGE_SIZE_KEY));
        } catch (NumberFormatException nfex) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public static String getPageSizeGetParam() {
        return PER_PAGE + getPageSize().toString();
    }

    // TODO: should we make it static?
    private final XmlMapper xmlMapper;
    private final String userAgent;

    private final String key;
    private final String baseUrl;
    private AsyncHttpClient client;

    public RecurlyClient(final String apiKey) {
        this(apiKey, "api");
    }

    public RecurlyClient(final String apiKey, final String subDomain) {
        this(apiKey, subDomain + ".recurly.com", 443, "v2");
    }

    public RecurlyClient(final String apiKey, final String host, final int port, final String version) {
        this.key = DatatypeConverter.printBase64Binary(apiKey.getBytes());
        this.baseUrl = String.format("https://%s:%d/%s", host, port, version);
        this.xmlMapper = RecurlyObject.newXmlMapper();
        this.userAgent = buildUserAgent();
    }

    /**
     * Open the underlying http client
     */
    public synchronized void open() {
        client = createHttpClient();
    }

    /**
     * Close the underlying http client
     */
    public synchronized void close() {
        if (client != null) {
            client.close();
        }
    }

    /**
     * Create Account
     * <p/>
     * Creates a new account. You may optionally include billing information.
     *
     * @param account account object
     * @return the newly created account object on success, null otherwise
     */
    public Account createAccount(final Account account) {
        return doPOST(Account.ACCOUNT_RESOURCE, account, Account.class);
    }

    /**
     * Get Accounts
     * <p/>
     * Returns information about all accounts.
     *
     * @return account object on success, null otherwise
     */
    public Accounts getAccounts() {
        return doGET(Accounts.ACCOUNTS_RESOURCE, Accounts.class);
    }

    public Coupons getCoupons() {
        return doGET(Coupons.COUPONS_RESOURCE, Coupons.class);
    }

    /**
     * Get Account
     * <p/>
     * Returns information about a single account.
     *
     * @param accountCode recurly account id
     * @return account object on success, null otherwise
     */
    public Account getAccount(final String accountCode) {
        return doGET(Account.ACCOUNT_RESOURCE + "/" + accountCode, Account.class);
    }

    /**
     * Update Account
     * <p/>
     * Updates an existing account.
     *
     * @param accountCode recurly account id
     * @param account     account object
     * @return the updated account object on success, null otherwise
     */
    public Account updateAccount(final String accountCode, final Account account) {
        return doPUT(Account.ACCOUNT_RESOURCE + "/" + accountCode, account, Account.class);
    }

    /**
     * Close Account
     * <p/>
     * Marks an account as closed and cancels any active subscriptions. Any saved billing information will also be
     * permanently removed from the account.
     *
     * @param accountCode recurly account id
     */
    public void closeAccount(final String accountCode) {
        doDELETE(Account.ACCOUNT_RESOURCE + "/" + accountCode);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Account adjustments

    public Adjustments getAccountAdjustments(final String accountCode, final Adjustments.AdjustmentType type) {
        return getAccountAdjustments(accountCode, type, null);
    }

    public Adjustments getAccountAdjustments(final String accountCode, final Adjustments.AdjustmentType type, final Adjustments.AdjustmentState state) {
        String url = Account.ACCOUNT_RESOURCE + "/" + accountCode + Adjustments.ADJUSTMENTS_RESOURCE;
        if (type != null || state != null) {
            url += "?";
        }

        if (type != null) {
            url += "type=" + type.getType();
            if (state != null) {
                url += "&";
            }
        }

        if (state != null) {
            url += "state=" + state.getState();
        }

        return doGET(url, Adjustments.class);
    }

    public Adjustment createAccountAdjustment(final String accountCode, final Adjustment adjustment) {
        return doPOST(Account.ACCOUNT_RESOURCE + "/" + accountCode + Adjustments.ADJUSTMENTS_RESOURCE,
                      adjustment,
                      Adjustment.class);
    }

    public void deleteAccountAdjustment(final String accountCode) {
        doDELETE(Account.ACCOUNT_RESOURCE + "/" + accountCode + Adjustments.ADJUSTMENTS_RESOURCE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Create a subscription
     * <p/>
     * Creates a subscription for an account.
     *
     * @param subscription Subscription object
     * @return the newly created Subscription object on success, null otherwise
     */
    public Subscription createSubscription(final Subscription subscription) {
        return doPOST(Subscription.SUBSCRIPTION_RESOURCE,
                      subscription, Subscription.class);
    }

    /**
     * Preview a subscription
     * <p/>
     * Previews a subscription for an account.
     *
     * @param subscription Subscription object
     * @return the newly created Subscription object on success, null otherwise
     */
    public Subscription previewSubscription(final Subscription subscription) {
        return doPOST(Subscription.SUBSCRIPTION_RESOURCE
                      + "/preview",
                      subscription, Subscription.class);
    }

    /**
     * Get a particular {@link Subscription} by it's UUID
     * <p/>
     * Returns information about a single subscription.
     *
     * @param uuid UUID of the subscription to lookup
     * @return Subscription
     */
    public Subscription getSubscription(final String uuid) {
        return doGET(Subscriptions.SUBSCRIPTIONS_RESOURCE
                     + "/" + uuid,
                     Subscription.class);
    }

    /**
     * Cancel a subscription
     * <p/>
     * Cancel a subscription so it remains active and then expires at the end of the current bill cycle.
     *
     * @param subscription Subscription object
     * @return -?-
     */
    public Subscription cancelSubscription(final Subscription subscription) {
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscription.getUuid() + "/cancel",
                     subscription, Subscription.class);
    }

    /**
     * Postpone a subscription
     * <p/>
     * postpone a subscription, setting a new renewal date.
     *
     * @param subscription Subscription object
     * @return -?-
     */
    public Subscription postponeSubscription(final Subscription subscription, final DateTime renewaldate) {
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscription.getUuid() + "/postpone?next_renewal_date=" + renewaldate,
                     subscription, Subscription.class);
    }

    /**
     * Terminate a particular {@link Subscription} by it's UUID
     *
     * @param subscription Subscription to terminate
     */
    public void terminateSubscription(final Subscription subscription, final RefundOption refund) {
        doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscription.getUuid() + "/terminate?refund=" + refund,
              subscription, Subscription.class);
    }

    /**
     * Reactivating a canceled subscription
     * <p/>
     * Reactivate a canceled subscription so it renews at the end of the current bill cycle.
     *
     * @param subscription Subscription object
     * @return -?-
     */
    public Subscription reactivateSubscription(final Subscription subscription) {
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscription.getUuid() + "/reactivate",
                     subscription, Subscription.class);
    }

    /**
     * Update a particular {@link Subscription} by it's UUID
     * <p/>
     * Returns information about a single subscription.
     *
     * @param uuid               UUID of the subscription to update
     * @param subscriptionUpdate subscriptionUpdate object
     * @return Subscription the updated subscription
     */
    public Subscription updateSubscription(final String uuid, final SubscriptionUpdate subscriptionUpdate) {
        return doPUT(Subscriptions.SUBSCRIPTIONS_RESOURCE
                     + "/" + uuid,
                     subscriptionUpdate,
                     Subscription.class);
    }

    /**
     * Preview an update to a particular {@link Subscription} by it's UUID
     * <p/>
     * Returns information about a single subscription.
     *
     * @param uuid UUID of the subscription to preview an update for
     * @return Subscription the updated subscription preview
     */
    public Subscription updateSubscriptionPreview(final String uuid, final SubscriptionUpdate subscriptionUpdate) {
        return doPOST(Subscriptions.SUBSCRIPTIONS_RESOURCE
                      + "/" + uuid + "/preview",
                      subscriptionUpdate,
                      Subscription.class);
    }


    /**
     * Update to a particular {@link Subscription}'s notes by it's UUID
     * <p/>
     * Returns information about a single subscription.
     *
     * @param uuid UUID of the subscription to preview an update for
     * @param subscriptionNotes SubscriptionNotes object
     * @return Subscription the updated subscription
     */
    public Subscription updateSubscriptionNotes(final String uuid, final SubscriptionNotes subscriptionNotes) {
      return doPUT(SubscriptionNotes.SUBSCRIPTION_RESOURCE + "/" + uuid + "/notes",
                   subscriptionNotes, Subscription.class);
    }

    /**
     * Get the subscriptions for an {@link Account}.
     * <p/>
     * Returns information about a single {@link Account}.
     *
     * @param accountCode recurly account id
     * @return Subscriptions for the specified user
     */
    public Subscriptions getAccountSubscriptions(final String accountCode) {
        return doGET(Account.ACCOUNT_RESOURCE
                     + "/" + accountCode
                     + Subscriptions.SUBSCRIPTIONS_RESOURCE,
                     Subscriptions.class);
    }

    /**
     * Get the subscriptions for an account.
     * <p/>
     * Returns information about a single account.
     *
     * @param accountCode recurly account id
     * @param status      Only accounts in this status will be returned
     * @return Subscriptions for the specified user
     */
    public Subscriptions getAccountSubscriptions(final String accountCode, final String status) {
        return doGET(Account.ACCOUNT_RESOURCE
                     + "/" + accountCode
                     + Subscriptions.SUBSCRIPTIONS_RESOURCE
                     + "?state="
                     + status,
                     Subscriptions.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Update an account's billing info
     * <p/>
     * When new or updated credit card information is updated, the billing information is only saved if the credit card
     * is valid. If the account has a past due invoice, the outstanding balance will be collected to validate the
     * billing information.
     * <p/>
     * If the account does not exist before the API request, the account will be created if the billing information
     * is valid.
     * <p/>
     * Please note: this API end-point may be used to import billing information without security codes (CVV).
     * Recurly recommends requiring CVV from your customers when collecting new or updated billing information.
     *
     * @param billingInfo billing info object to create or update
     * @return the newly created or update billing info object on success, null otherwise
     */
    public BillingInfo createOrUpdateBillingInfo(final BillingInfo billingInfo) {
        final String accountCode = billingInfo.getAccount().getAccountCode();
        // Unset it to avoid confusing Recurly
        billingInfo.setAccount(null);
        return doPUT(Account.ACCOUNT_RESOURCE + "/" + accountCode + BillingInfo.BILLING_INFO_RESOURCE,
                     billingInfo, BillingInfo.class);
    }

    /**
     * Lookup an account's billing info
     * <p/>
     * Returns only the account's current billing information.
     *
     * @param accountCode recurly account id
     * @return the current billing info object associated with this account on success, null otherwise
     */
    public BillingInfo getBillingInfo(final String accountCode) {
        return doGET(Account.ACCOUNT_RESOURCE + "/" + accountCode + BillingInfo.BILLING_INFO_RESOURCE,
                     BillingInfo.class);
    }

    /**
     * Clear an account's billing info
     * <p/>
     * You may remove any stored billing information for an account. If the account has a subscription, the renewal will
     * go into past due unless you update the billing info before the renewal occurs
     *
     * @param accountCode recurly account id
     */
    public void clearBillingInfo(final String accountCode) {
        doDELETE(Account.ACCOUNT_RESOURCE + "/" + accountCode + BillingInfo.BILLING_INFO_RESOURCE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // User transactions

    /**
     * Lookup an account's transactions history
     * <p/>
     * Returns the account's transaction history
     *
     * @param accountCode recurly account id
     * @return the transaction history associated with this account on success, null otherwise
     */
    public Transactions getAccountTransactions(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Transactions.TRANSACTIONS_RESOURCE,
                     Transactions.class);
    }

    /**
     * Lookup a transaction
     *
     * @param transactionId recurly transaction id
     * @return the transaction if found, null otherwise
     */
    public Transaction getTransaction(final String transactionId) {
        return doGET(Transactions.TRANSACTIONS_RESOURCE + "/" + transactionId,
                     Transaction.class);
    }

    /**
     * Creates a {@link Transaction} through the Recurly API.
     *
     * @param trans The {@link Transaction} to create
     * @return The created {@link Transaction} object
     */
    public Transaction createTransaction(final Transaction trans) {
        return doPOST(Transactions.TRANSACTIONS_RESOURCE, trans, Transaction.class);
    }

    /**
     * Refund a transaction
     *
     * @param transactionId recurly transaction id
     * @param amount        amount to refund, null for full refund
     */
    public void refundTransaction(final String transactionId, @Nullable final BigDecimal amount) {
        String url = Transactions.TRANSACTIONS_RESOURCE + "/" + transactionId;
        if (amount != null) {
            url = url + "?amount_in_cents=" + (amount.intValue() * 100);
        }
        doDELETE(url);
    }

    ///////////////////////////////////////////////////////////////////////////
    // User invoices

    /**
     * Lookup an invoice
     * <p/>
     * Returns the invoice
     *
     * @param invoiceId Recurly Invoice ID
     * @return the invoice
     */
    public Invoice getInvoice(final Integer invoiceId) {
        return doGET(Invoices.INVOICES_RESOURCE + "/" + invoiceId, Invoice.class);
    }

    /**
     * Lookup an account's invoices
     * <p/>
     * Returns the account's invoices
     *
     * @param accountCode recurly account id
     * @return the invoices associated with this account on success, null otherwise
     */
    public Invoices getAccountInvoices(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Invoices.INVOICES_RESOURCE,
                     Invoices.class);
    }

    /**
     * Post an invoice: invoice pending charges on an account
     * <p/>
     * Returns an invoice
     *
     * @param accountCode
     * @return the invoice that was generated on success, null otherwise
     */
    public Invoice postAccountInvoice(final String accountCode, final Invoice invoice) {
        return doPOST(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Invoices.INVOICES_RESOURCE, invoice, Invoice.class);
    }

    /**
     * Mark an invoice as paid successfully - Recurly Enterprise Feature
     *
     * @param invoiceId Recurly Invoice ID
     */
    public Invoice markInvoiceSuccessful(final Integer invoiceId) {
        return doPUT(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/mark_successful", null, Invoice.class);
    }

    /**
     * Mark an invoice as failed collection
     *
     * @param invoiceId Recurly Invoice ID
     */
    public Invoice markInvoiceFailed(final Integer invoiceId) {
        return doPUT(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/mark_failed", null, Invoice.class);
    }

    /**
     * Enter an offline payment for a manual invoice (beta) - Recurly Enterprise Feature
     *
     * @param invoiceId Recurly Invoice ID
     * @param payment   The external payment
     */
    public Transaction enterOfflinePayment(final Integer invoiceId, final Transaction payment) {
        return doPOST(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/transactions", payment, Transaction.class);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create a Plan's info
     * <p/>
     *
     * @param plan The plan to create on recurly
     * @return the plan object as identified by the passed in ID
     */
    public Plan createPlan(final Plan plan) {
        return doPOST(Plan.PLANS_RESOURCE, plan, Plan.class);
    }

    /**
     * Get a Plan's details
     * <p/>
     *
     * @param planCode recurly id of plan
     * @return the plan object as identified by the passed in ID
     */
    public Plan getPlan(final String planCode) {
        return doGET(Plan.PLANS_RESOURCE + "/" + planCode, Plan.class);
    }

    /**
     * Return all the plans
     * <p/>
     *
     * @return the plan object as identified by the passed in ID
     */
    public Plans getPlans() {
        return doGET(Plans.PLANS_RESOURCE, Plans.class);
    }

    /**
     * Deletes a {@link Plan}
     * <p/>
     *
     * @param planCode The {@link Plan} object to delete.
     */
    public void deletePlan(final String planCode) {
        doDELETE(Plan.PLANS_RESOURCE +
                 "/" +
                 planCode);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create an AddOn to a Plan
     * <p/>
     *
     * @param planCode The planCode of the {@link Plan } to create within recurly
     * @param addOn    The {@link AddOn} to create within recurly
     * @return the {@link AddOn} object as identified by the passed in object
     */
    public AddOn createPlanAddOn(final String planCode, final AddOn addOn) {
        return doPOST(Plan.PLANS_RESOURCE +
                      "/" +
                      planCode +
                      AddOn.ADDONS_RESOURCE,
                      addOn, AddOn.class);
    }

    /**
     * Get an AddOn's details
     * <p/>
     *
     * @param addOnCode recurly id of {@link AddOn}
     * @param planCode  recurly id of {@link Plan}
     * @return the {@link AddOn} object as identified by the passed in plan and add-on IDs
     */
    public AddOn getAddOn(final String planCode, final String addOnCode) {
        return doGET(Plan.PLANS_RESOURCE +
                     "/" +
                     planCode +
                     AddOn.ADDONS_RESOURCE +
                     "/" +
                     addOnCode, AddOn.class);
    }

    /**
     * Return all the {@link AddOn} for a {@link Plan}
     * <p/>
     *
     * @return the {@link AddOn} objects as identified by the passed plan ID
     */
    public AddOns getAddOns(final String planCode) {
        return doGET(Plan.PLANS_RESOURCE +
                     "/" +
                     planCode +
                     AddOn.ADDONS_RESOURCE, AddOns.class);
    }

    /**
     * Deletes a {@link AddOn} for a Plan
     * <p/>
     *
     * @param planCode  The {@link Plan} object.
     * @param addOnCode The {@link AddOn} object to delete.
     */
    public void deleteAddOn(final String planCode, final String addOnCode) {
        doDELETE(Plan.PLANS_RESOURCE +
                 "/" +
                 planCode +
                 AddOn.ADDONS_RESOURCE +
                 "/" +
                 addOnCode);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create a {@link Coupon}
     * <p/>
     *
     * @param coupon The coupon to create on recurly
     * @return the {@link Coupon} object
     */
    public Coupon createCoupon(final Coupon coupon) {
        return doPOST(Coupon.COUPON_RESOURCE, coupon, Coupon.class);
    }

    /**
     * Get a Coupon
     * <p/>
     *
     * @param couponCode The code for the {@link Coupon}
     * @return The {@link Coupon} object as identified by the passed in code
     */
    public Coupon getCoupon(final String couponCode) {
        return doGET(Coupon.COUPON_RESOURCE + "/" + couponCode, Coupon.class);
    }

    /**
     * Delete a {@link Coupon}
     * <p/>
     *
     * @param couponCode The code for the {@link Coupon}
     */
    public void deleteCoupon(final String couponCode) {
        doDELETE(Coupon.COUPON_RESOURCE + "/" + couponCode);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Redeem a {@link Coupon} on an account.
     *
     * @param couponCode redeemed coupon id
     * @return the {@link Coupon} object
     */
    public Redemption redeemCoupon(final String couponCode, final Redemption redemption) {
        return doPOST(Coupon.COUPON_RESOURCE + "/" + couponCode + Redemption.REDEEM_RESOURCE,
                      redemption, Redemption.class);
    }

    /**
     * Lookup a coupon redemption on an invoice.
     *
     * @param accountCode recurly account id
     * @return the coupon redemption for this account on success, null otherwise
     */
    public Redemption getCouponRedemptionByAccount(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Redemption.REDEMPTION_RESOURCE,
                     Redemption.class);
    }

    /**
     * Lookup a coupon redemption on an invoice.
     *
     * @param invoiceNumber invoice number
     * @return the coupon redemption for this invoice on success, null otherwise
     */
    public Redemption getCouponRedemptionByInvoice(final Integer invoiceNumber) {
        return doGET(Invoices.INVOICES_RESOURCE + "/" + invoiceNumber + Redemption.REDEMPTION_RESOURCE,
                     Redemption.class);
    }

    /**
     * Deletes a coupon from an account.
     *
     * @param accountCode recurly account id
     */
    public void deleteCouponRedemption(final String accountCode) {
        doDELETE(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Redemption.REDEMPTION_RESOURCE);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    // Recurly.js API
    //
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Fetch Subscription
     * <p/>
     * Returns subscription from a recurly.js token.
     *
     * @param recurlyToken token given by recurly.js
     * @return subscription object on success, null otherwise
     */
    public Subscription fetchSubscription(final String recurlyToken) {
        return fetch(recurlyToken, Subscription.class);
    }

    /**
     * Fetch BillingInfo
     * <p/>
     * Returns billing info from a recurly.js token.
     *
     * @param recurlyToken token given by recurly.js
     * @return billing info object on success, null otherwise
     */
    public BillingInfo fetchBillingInfo(final String recurlyToken) {
        return fetch(recurlyToken, BillingInfo.class);
    }

    /**
     * Fetch Invoice
     * <p/>
     * Returns invoice from a recurly.js token.
     *
     * @param recurlyToken token given by recurly.js
     * @return invoice object on success, null otherwise
     */
    public Invoice fetchInvoice(final String recurlyToken) {
        return fetch(recurlyToken, Invoice.class);
    }

    private <T> T fetch(final String recurlyToken, final Class<T> clazz) {
        return doGET(FETCH_RESOURCE + "/" + recurlyToken, clazz);
    }

    ///////////////////////////////////////////////////////////////////////////

    private <T> T doGET(final String resource, final Class<T> clazz) {
        final StringBuffer url = new StringBuffer(baseUrl);
        url.append(resource);
        if (resource != null && !resource.contains("?")) {
            url.append("?");
        } else {
            url.append("&");
            url.append("&");
        }
        url.append(getPageSizeGetParam());

        return doGETWithFullURL(clazz, url.toString());
    }

    public <T> T doGETWithFullURL(final Class<T> clazz, final String url) {
        if (debug()) {
            log.info("Msg to Recurly API [GET] :: URL : {}", url);
        }
        return callRecurlySafe(client.prepareGet(url), clazz);
    }

    private <T> T doPOST(final String resource, final RecurlyObject payload, final Class<T> clazz) {
        final String xmlPayload;
        try {
            xmlPayload = xmlMapper.writeValueAsString(payload);
            if (debug()) {
                log.info("Msg to Recurly API [POST]:: URL : {}", baseUrl + resource);
                log.info("Payload for [POST]:: {}", xmlPayload);
            }
        } catch (IOException e) {
            log.warn("Unable to serialize {} object as XML: {}", clazz.getName(), payload.toString());
            return null;
        }

        return callRecurlySafe(client.preparePost(baseUrl + resource).setBody(xmlPayload), clazz);
    }

    private <T> T doPUT(final String resource, final RecurlyObject payload, final Class<T> clazz) {
        final String xmlPayload;
        try {
            if (payload != null) {
                xmlPayload = xmlMapper.writeValueAsString(payload);
            } else {
                xmlPayload = null;
            }

            if (debug()) {
                log.info("Msg to Recurly API [PUT]:: URL : {}", baseUrl + resource);
                log.info("Payload for [PUT]:: {}", xmlPayload);
            }
        } catch (IOException e) {
            log.warn("Unable to serialize {} object as XML: {}", clazz.getName(), payload.toString());
            return null;
        }

        return callRecurlySafe(client.preparePut(baseUrl + resource).setBody(xmlPayload), clazz);
    }

    private void doDELETE(final String resource) {
        callRecurlySafe(client.prepareDelete(baseUrl + resource), null);
    }

    private <T> T callRecurlySafe(final AsyncHttpClient.BoundRequestBuilder builder, @Nullable final Class<T> clazz) {
        try {
            return callRecurly(builder, clazz);
        } catch (IOException e) {
            log.warn("Error while calling Recurly", e);
            return null;
        } catch (ExecutionException e) {
            // Extract the errors exception, if any
            if (e.getCause() != null &&
                e.getCause().getCause() != null &&
                e.getCause().getCause() instanceof TransactionErrorException) {
                throw (TransactionErrorException) e.getCause().getCause();
            } else if (e.getCause() != null &&
                       e.getCause() instanceof TransactionErrorException) {
                // See https://github.com/killbilling/recurly-java-library/issues/16
                throw (TransactionErrorException) e.getCause();
            }
            log.error("Execution error", e);
            return null;
        } catch (InterruptedException e) {
            log.error("Interrupted while calling Recurly", e);
            return null;
        }
    }

    private <T> T callRecurly(final AsyncHttpClient.BoundRequestBuilder builder, @Nullable final Class<T> clazz)
            throws IOException, ExecutionException, InterruptedException {
        final Response response = builder.addHeader("Authorization", "Basic " + key)
                                         .addHeader("Accept", "application/xml")
                                         .addHeader("Content-Type", "application/xml; charset=utf-8")
                                         .addHeader(HttpHeaders.USER_AGENT, userAgent)
                                         .setBodyEncoding("UTF-8")
                                         .execute()
                                         .get();

        final InputStream in = response.getResponseBodyAsStream();
        try {
            final String payload = convertStreamToString(in);
            if (debug()) {
                log.info("Msg from Recurly API :: {}", payload);
            }

            // Handle errors payload
            if (response.getStatusCode() >= 300) {
                log.warn("Recurly error whilst calling: {}\n{}", response.getUri(), payload);

                if (response.getStatusCode() == 422) {
                    final Errors errors;
                    try {
                        errors = xmlMapper.readValue(payload, Errors.class);
                    } catch (Exception e) {
                        // 422 is returned for transaction errors (see https://recurly.readme.io/v2.0/page/transaction-errors)
                        // as well as bad input payloads
                        log.debug("Unable to extract error", e);
                        return null;
                    }
                    throw new TransactionErrorException(errors);
                } else {
                    RecurlyAPIError recurlyError = null;
                    try {
                        recurlyError = xmlMapper.readValue(payload, RecurlyAPIError.class);
                    } catch (Exception e) {
                        log.debug("Unable to extract error", e);
                    }
                    throw new RecurlyAPIException(recurlyError);
                }
            }

            if (clazz == null) {
                return null;
            }

            final T obj = xmlMapper.readValue(payload, clazz);
            if (obj instanceof RecurlyObject) {
                ((RecurlyObject) obj).setRecurlyClient(this);
            } else if (obj instanceof RecurlyObjects) {
                final RecurlyObjects recurlyObjects = (RecurlyObjects) obj;
                recurlyObjects.setRecurlyClient(this);

                // Set the RecurlyClient on all objects for later use
                for (final Object object : recurlyObjects) {
                    ((RecurlyObject) object).setRecurlyClient(this);
                }

                // Set the total number of records
                final String xRecords = response.getHeader(X_RECORDS_HEADER_NAME);
                if (xRecords != null) {
                    recurlyObjects.setNbRecords(Integer.valueOf(xRecords));
                }

                // Set links for pagination
                final String linkHeader = response.getHeader(LINK_HEADER_NAME);
                if (linkHeader != null) {
                    final String[] links = PaginationUtils.getLinks(linkHeader);
                    recurlyObjects.setStartUrl(links[0]);
                    recurlyObjects.setPrevUrl(links[1]);
                    recurlyObjects.setNextUrl(links[2]);
                }
            }
            return obj;
        } finally {
            closeStream(in);
        }
    }

    private String convertStreamToString(final java.io.InputStream is) {
        try {
            return new Scanner(is).useDelimiter("\\A").next();
        } catch (final NoSuchElementException e) {
            return "";
        }
    }

    private void closeStream(final InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                log.warn("Failed to close http-client - provided InputStream: {}", e.getLocalizedMessage());
            }
        }
    }

    private AsyncHttpClient createHttpClient() {
        // Don't limit the number of connections per host
        // See https://github.com/ning/async-http-client/issues/issue/28
        final AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
        builder.setMaxConnectionsPerHost(-1);
        return new AsyncHttpClient(builder.build());
    }

    @VisibleForTesting
    String getUserAgent() {
        return userAgent;
    }

    private String buildUserAgent() {
        final String defaultVersion = "0.0.0";
        final String defaultJavaVersion = "0.0.0";

        try {
            final Properties gitRepositoryState = new Properties();
            final URL resourceURL = Resources.getResource(GIT_PROPERTIES_FILE);
            final CharSource charSource = Resources.asCharSource(resourceURL, Charset.forName("UTF-8"));

            Reader reader = null;
            try {
                reader = charSource.openStream();
                gitRepositoryState.load(reader);
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }

            final String version = Objects.firstNonNull(getVersionFromGitRepositoryState(gitRepositoryState), defaultVersion);
            final String javaVersion = Objects.firstNonNull(StandardSystemProperty.JAVA_VERSION.value(), defaultJavaVersion);
            return String.format("KillBill/%s; %s", version, javaVersion);
        } catch (final Exception e) {
            return String.format("KillBill/%s; %s", defaultVersion, defaultJavaVersion);
        }
    }

    @VisibleForTesting
    String getVersionFromGitRepositoryState(final Properties gitRepositoryState) {
        final String gitDescribe = gitRepositoryState.getProperty(GIT_COMMIT_ID_DESCRIBE_SHORT);
        if (gitDescribe == null) {
            return null;
        }
        final Matcher matcher = TAG_FROM_GIT_DESCRIBE_PATTERN.matcher(gitDescribe);
        return matcher.find() ? matcher.group(1) : null;
    }
}
