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

import com.ning.billing.recurly.model.Account;
import com.ning.billing.recurly.model.AccountBalance;
import com.ning.billing.recurly.model.AccountNotes;
import com.ning.billing.recurly.model.Accounts;
import com.ning.billing.recurly.model.AddOn;
import com.ning.billing.recurly.model.AddOns;
import com.ning.billing.recurly.model.Adjustment;
import com.ning.billing.recurly.model.AdjustmentRefund;
import com.ning.billing.recurly.model.Adjustments;
import com.ning.billing.recurly.model.BillingInfo;
import com.ning.billing.recurly.model.Coupon;
import com.ning.billing.recurly.model.Coupons;
import com.ning.billing.recurly.model.CreditPayments;
import com.ning.billing.recurly.model.Errors;
import com.ning.billing.recurly.model.GiftCard;
import com.ning.billing.recurly.model.GiftCards;
import com.ning.billing.recurly.model.Invoice;
import com.ning.billing.recurly.model.InvoiceCollection;
import com.ning.billing.recurly.model.InvoiceRefund;
import com.ning.billing.recurly.model.InvoiceState;
import com.ning.billing.recurly.model.Invoices;
import com.ning.billing.recurly.model.Item;
import com.ning.billing.recurly.model.Items;
import com.ning.billing.recurly.model.Plan;
import com.ning.billing.recurly.model.Plans;
import com.ning.billing.recurly.model.Purchase;
import com.ning.billing.recurly.model.RecurlyAPIError;
import com.ning.billing.recurly.model.RecurlyObject;
import com.ning.billing.recurly.model.RecurlyObjects;
import com.ning.billing.recurly.model.Redemption;
import com.ning.billing.recurly.model.Redemptions;
import com.ning.billing.recurly.model.RefundMethod;
import com.ning.billing.recurly.model.RefundOption;
import com.ning.billing.recurly.model.ResponseMetadata;
import com.ning.billing.recurly.model.ShippingAddress;
import com.ning.billing.recurly.model.ShippingAddresses;
import com.ning.billing.recurly.model.Subscription;
import com.ning.billing.recurly.model.SubscriptionState;
import com.ning.billing.recurly.model.SubscriptionUpdate;
import com.ning.billing.recurly.model.SubscriptionNotes;
import com.ning.billing.recurly.model.Subscriptions;
import com.ning.billing.recurly.model.Transaction;
import com.ning.billing.recurly.model.TransactionState;
import com.ning.billing.recurly.model.TransactionType;
import com.ning.billing.recurly.model.Transactions;
import com.ning.billing.recurly.model.Usage;
import com.ning.billing.recurly.model.Usages;
import com.ning.billing.recurly.model.MeasuredUnit;
import com.ning.billing.recurly.model.MeasuredUnits;
import com.ning.billing.recurly.model.AccountAcquisition;
import com.ning.billing.recurly.model.ShippingMethod;
import com.ning.billing.recurly.model.ShippingMethods;
import com.fasterxml.jackson.annotation.JsonTypeInfo.None;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.StandardSystemProperty;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.google.common.net.HttpHeaders;

import com.ning.billing.recurly.util.http.SslUtils;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Response;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;

public class RecurlyClient {

    private static final Logger log = LoggerFactory.getLogger(RecurlyClient.class);

    public static final String RECURLY_DEBUG_KEY = "recurly.debug";
    public static final String RECURLY_API_VERSION = "2.26";

    private static final String X_RATELIMIT_REMAINING_HEADER_NAME = "X-RateLimit-Remaining";
    private static final String X_RECORDS_HEADER_NAME = "X-Records";
    private static final String LINK_HEADER_NAME = "Link";

    private static final String GIT_PROPERTIES_FILE = "com/ning/billing/recurly/git.properties";
    @VisibleForTesting
    static final String GIT_COMMIT_ID_DESCRIBE_SHORT = "git.commit.id.describe-short";
    private static final Pattern TAG_FROM_GIT_DESCRIBE_PATTERN = Pattern.compile("recurly-java-library-([0-9]*\\.[0-9]*\\.[0-9]*)(-[0-9]*)?");

    public static final String FETCH_RESOURCE = "/recurly_js/result";

    private static final List<String> validHosts = Arrays.asList("recurly.com");

    /**
     * Checks a system property to see if debugging output is
     * required. Used internally by the client to decide whether to
     * generate debug output
     */
    private static boolean debug() {
        return Boolean.getBoolean(RECURLY_DEBUG_KEY);
    }

    /**
     * Warns the user about logging PII in production environments
     */
    private static void loggerWarning() {
        if (debug())
        {
            log.warn("[WARNING] Logger enabled. The logger has the potential to leak " +
            "PII and should never be used in production environments.");
        }
    }

    // TODO: should we make it static?
    private final XmlMapper xmlMapper;
    private final String userAgent;

    private final String key;
    private final String baseUrl;
    private AsyncHttpClient client;

    // Allows error messages to be returned in a specified language
    private String acceptLanguage = "en-US";

    // Stores the number of requests remaining before rate limiting takes effect
    private int rateLimitRemaining;

    public RecurlyClient(final String apiKey) {
        this(apiKey, "api");
        loggerWarning();
    }

    public RecurlyClient(final String apiKey, final String subDomain) {
        this(apiKey, subDomain + ".recurly.com", 443, "v2");
        loggerWarning();
    }

    public RecurlyClient(final String apiKey, final String host, final int port, final String version) {
        this(apiKey, "https", host, port, version);
        loggerWarning();
    }

    public RecurlyClient(final String apiKey, final String scheme, final String host, final int port, final String version) {
        this.key = DatatypeConverter.printBase64Binary(apiKey.getBytes());
        this.baseUrl = String.format("%s://%s:%d/%s", scheme, host, port, version);
        this.xmlMapper = RecurlyObject.newXmlMapper();
        this.userAgent = buildUserAgent();
        this.rateLimitRemaining = -1;
        loggerWarning();
    }

    /**
     * Open the underlying http client
     */
    public synchronized void open() throws NoSuchAlgorithmException, KeyManagementException {
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
     * Set the Accept-Language header
     * <p>
     * Sets the Accept-Language header for all requests made by this client. Note: this is not thread-safe!
     * See https://github.com/killbilling/recurly-java-library/pull/298 for more details about thread safety.
     *
     * @param language The language to set in the header. E.g., "en-US"
     */
    public void setAcceptLanguage(String language) {
        this.acceptLanguage = language;
    }

    /**
     * Returns the number of requests remaining until requests will be denied by rate limiting.
     * @return Number of requests remaining. Value is valid (> -1) after a successful API call.
     */
    public int getRateLimitRemaining() {
        return rateLimitRemaining;
    }

    /**
     * Create Account
     * <p>
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
     * <p>
     * Returns information about all accounts.
     *
     * @return Accounts on success, null otherwise
     */
    public Accounts getAccounts() {
        return doGET(Accounts.ACCOUNTS_RESOURCE, Accounts.class, new QueryParams());
    }

    /**
     * Get Accounts given query params
     * <p>
     * Returns information about all accounts.
     *
     * @param params {@link QueryParams}
     * @return Accounts on success, null otherwise
     */
    public Accounts getAccounts(final QueryParams params) {
        return doGET(Accounts.ACCOUNTS_RESOURCE, Accounts.class, params);
    }

    /**
     * Get number of Accounts matching the query params
     *
     * @param params {@link QueryParams}
     * @return Integer on success, null otherwise
     */
    public Integer getAccountsCount(final QueryParams params) {
        FluentCaseInsensitiveStringsMap map = doHEAD(Accounts.ACCOUNTS_RESOURCE, params);
        return Integer.parseInt(map.getFirstValue(X_RECORDS_HEADER_NAME));
    }

    /**
     * Get Coupons
     * <p>
     * Returns information about all accounts.
     *
     * @return Coupons on success, null otherwise
     */
    public Coupons getCoupons() {
        return doGET(Coupons.COUPONS_RESOURCE, Coupons.class, new QueryParams());
    }

    /**
     * Get Coupons given query params
     * <p>
     * Returns information about all accounts.
     *
     * @param params {@link QueryParams}
     * @return Coupons on success, null otherwise
     */
    public Coupons getCoupons(final QueryParams params) {
        return doGET(Coupons.COUPONS_RESOURCE, Coupons.class, params);
    }

    /**
     * Get number of Coupons matching the query params
     *
     * @param params {@link QueryParams}
     * @return Integer on success, null otherwise
     */
    public Integer getCouponsCount(final QueryParams params) {
        FluentCaseInsensitiveStringsMap map = doHEAD(Coupons.COUPONS_RESOURCE, params);
        return Integer.parseInt(map.getFirstValue(X_RECORDS_HEADER_NAME));
    }

    /**
     * Get Account
     * <p>
     * Returns information about a single account.
     *
     * @param accountCode recurly account id
     * @return account object on success, null otherwise
     */
    public Account getAccount(final String accountCode) {
        if (accountCode == null || accountCode.isEmpty())
            throw new RuntimeException("accountCode cannot be empty!");

        return doGET(Account.ACCOUNT_RESOURCE + "/" + accountCode, Account.class);
    }

    /**
     * Update Account
     * <p>
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
     * Get Account Balance
     * <p>
     * Retrieves the remaining balance on the account
     *
     * @param accountCode recurly account id
     * @return the updated AccountBalance if success, null otherwise
     */
    public AccountBalance getAccountBalance(final String accountCode) {
        return doGET(Account.ACCOUNT_RESOURCE + "/" + accountCode + AccountBalance.ACCOUNT_BALANCE_RESOURCE, AccountBalance.class);
    }

    /**
     * Close Account
     * <p>
     * Marks an account as closed and cancels any active subscriptions. Any saved billing information will also be
     * permanently removed from the account.
     *
     * @param accountCode recurly account id
     */
    public void closeAccount(final String accountCode) {
        doDELETE(Account.ACCOUNT_RESOURCE + "/" + accountCode);
    }

    /**
     * Reopen Account
     * <p>
     * Transitions a closed account back to active.
     *
     * @param accountCode recurly account id
     */
    public Account reopenAccount(final String accountCode) {
        return doPUT(Account.ACCOUNT_RESOURCE + "/" + accountCode + "/reopen",
                     null, Account.class);
    }


    /**
     * Get Child Accounts
     * <p>
     * Returns information about a the child accounts of an account.
     *
     * @param accountCode recurly account id
     * @return Accounts on success, null otherwise
     */
    public Accounts getChildAccounts(final String accountCode) {
        return doGET(Account.ACCOUNT_RESOURCE + "/" + accountCode + "/child_accounts", Accounts.class, new QueryParams());
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Account adjustments

    /**
     * Get Account Adjustments
     * <p>
     *
     * @param accountCode recurly account id
     * @return the adjustments on the account
     */
    public Adjustments getAccountAdjustments(final String accountCode) {
        return getAccountAdjustments(accountCode, null, null, new QueryParams());
    }

    /**
     * Get Account Adjustments
     * <p>
     *
     * @param accountCode recurly account id
     * @param type {@link com.ning.billing.recurly.model.Adjustments.AdjustmentType}
     * @return the adjustments on the account
     */
    public Adjustments getAccountAdjustments(final String accountCode, final Adjustments.AdjustmentType type) {
        return getAccountAdjustments(accountCode, type, null, new QueryParams());
    }

    /**
     * Get Account Adjustments
     * <p>
     *
     * @param accountCode recurly account id
     * @param type {@link com.ning.billing.recurly.model.Adjustments.AdjustmentType}
     * @param state {@link com.ning.billing.recurly.model.Adjustments.AdjustmentState}
     * @return the adjustments on the account
     */
    public Adjustments getAccountAdjustments(final String accountCode, final Adjustments.AdjustmentType type, final Adjustments.AdjustmentState state) {
        return getAccountAdjustments(accountCode, type, state, new QueryParams());
    }

    /**
     * Get Account Adjustments
     * <p>
     *
     * @param accountCode recurly account id
     * @param type {@link com.ning.billing.recurly.model.Adjustments.AdjustmentType}
     * @param state {@link com.ning.billing.recurly.model.Adjustments.AdjustmentState}
     * @param params {@link QueryParams}
     * @return the adjustments on the account
     */
    public Adjustments getAccountAdjustments(final String accountCode, final Adjustments.AdjustmentType type, final Adjustments.AdjustmentState state, final QueryParams params) {
        final String url = Account.ACCOUNT_RESOURCE + "/" + accountCode + Adjustments.ADJUSTMENTS_RESOURCE;

        if (type != null) params.put("type", type.getType());
        if (state != null) params.put("state", state.getState());

        return doGET(url, Adjustments.class, params);
    }

    public Adjustment getAdjustment(final String adjustmentUuid) {
        if (adjustmentUuid == null || adjustmentUuid.isEmpty())
            throw new RuntimeException("adjustmentUuid cannot be empty!");

        return doGET(Adjustments.ADJUSTMENTS_RESOURCE + "/" + adjustmentUuid, Adjustment.class);
    }

    public Adjustment createAccountAdjustment(final String accountCode, final Adjustment adjustment) {
        return doPOST(Account.ACCOUNT_RESOURCE + "/" + accountCode + Adjustments.ADJUSTMENTS_RESOURCE,
                      adjustment,
                      Adjustment.class);
    }

    public void deleteAccountAdjustment(final String accountCode) {
        doDELETE(Account.ACCOUNT_RESOURCE + "/" + accountCode + Adjustments.ADJUSTMENTS_RESOURCE);
    }

    public void deleteAdjustment(final String adjustmentUuid) {
        doDELETE(Adjustments.ADJUSTMENTS_RESOURCE + "/" + adjustmentUuid);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Create a subscription
     * <p>
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
     * <p>
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
     * <p>
     * Returns information about a single subscription.
     *
     * @param uuid UUID of the subscription to lookup
     * @return Subscription
     */
    public Subscription getSubscription(final String uuid) {
        if (uuid == null || uuid.isEmpty())
            throw new RuntimeException("uuid cannot be empty!");

        return doGET(Subscriptions.SUBSCRIPTIONS_RESOURCE
                     + "/" + uuid,
                     Subscription.class);
    }

    /**
     * Cancel a subscription
     * <p>
     * Cancel a subscription so it remains active and then expires at the end of the current bill cycle.
     *
     * @param subscription Subscription object
     * @return Subscription
     */
    public Subscription cancelSubscription(final Subscription subscription) {
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscription.getUuid() + "/cancel",
                     subscription, Subscription.class);
    }

    /**
     * Cancel a subscription
     * <p>
     * Cancel a subscription so it remains active and then expires at the end of the current bill cycle.
     *
     * @param subscriptionUuid String uuid of the subscription to cancel
     * @param timeframe SubscriptionUpdate.TimeFrame the timeframe in which to cancel. Only accepts bill_date or term_end
     * @return Subscription
     */
    public Subscription cancelSubscription(final String subscriptionUuid, final SubscriptionUpdate.Timeframe timeframe) {
        final QueryParams qp = new QueryParams();
        if (timeframe != null) qp.put("timeframe", timeframe.toString());
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscriptionUuid + "/cancel",
                     null, Subscription.class, qp);
    }

    /**
     * Pause a subscription or cancel a scheduled pause on a subscription.
     * <p>
     * * For an active subscription without a pause scheduled already, this will
     *   schedule a pause period to begin at the next renewal date for the specified
     *   number of billing cycles (remaining_pause_cycles).
     * * When a scheduled pause already exists, this will update the remaining pause
     *   cycles with the new value sent. When zero (0) remaining_pause_cycles is sent
     *   for a subscription with a scheduled pause, the pause will be canceled.
     * * For a paused subscription, the remaining_pause_cycles will adjust the
     *   length of the current pause period. Sending zero (0) in the remaining_pause_cycles
     *   field will cause the subscription to be resumed at the next renewal date.
     *
     * @param subscriptionUuid The uuid for the subscription you wish to pause.
     * @param remainingPauseCycles The number of billing cycles that the subscription will be paused.
     * @return Subscription
     */
    public Subscription pauseSubscription(final String subscriptionUuid, final int remainingPauseCycles) {
        Subscription request = new Subscription();
        request.setRemainingPauseCycles(remainingPauseCycles);
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscriptionUuid + "/pause",
                     request, Subscription.class);
    }

    /**
     * Convert trial to paid subscription when TransactionType = "moto".
     * @param subscriptionUuid The uuid for the subscription you want to convert from trial to paid.
     * @return Subscription
     */
    public Subscription convertTrialMoto(final String subscriptionUuid) {
        Subscription request = new Subscription();
        request.setTransactionType("moto");
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscriptionUuid + "/convert_trial",
            request, Subscription.class);
    }

    /**
     * Convert trial to paid subscription without 3DS token
     * @param subscriptionUuid The uuid for the subscription you want to convert from trial to paid.
     * @return Subscription
     */
    public Subscription convertTrial(final String subscriptionUuid) {
        return convertTrial(subscriptionUuid, null);
    }

    /**
     * Convert trial to paid subscription with 3DS token
     * @param subscriptionUuid The uuid for the subscription you want to convert from trial to paid.
     * @param ThreeDSecureActionResultTokenId 3DS secure action result token id in billing info.
     * @return Subscription
     */
    public Subscription convertTrial(final String subscriptionUuid, final String ThreeDSecureActionResultTokenId) {
        Subscription request;
        if (ThreeDSecureActionResultTokenId == null) {
            request = null;
        } else {
            request = new Subscription();
            Account account = new Account();
            BillingInfo billingInfo = new BillingInfo();
            billingInfo.setThreeDSecureActionResultTokenId(ThreeDSecureActionResultTokenId); 
            account.setBillingInfo(billingInfo);
            request.setAccount(account);   
        }
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscriptionUuid + "/convert_trial",
            request, Subscription.class);
    }

    /**
     * Immediately resumes a currently paused subscription.
     * <p>
     * For a paused subscription, this will immediately resume the subscription
     * from the pause, produce an invoice, and return the newly resumed subscription.
     * Any at-renewal subscription changes will be immediately applied when
     * the subscription resumes.
     *
     * @param subscriptionUuid The uuid for the subscription you wish to pause.
     * @return Subscription
     */
    public Subscription resumeSubscription(final String subscriptionUuid) {
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscriptionUuid + "/resume",
                null, Subscription.class);
    }

    /**
     * Postpone a subscription
     * <p>
     * postpone a subscription, setting a new renewal date.
     *
     * @param subscription Subscription object
     * @return Subscription
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
     * <p>
     * Reactivate a canceled subscription so it renews at the end of the current bill cycle.
     *
     * @param subscription Subscription object
     * @return Subscription
     */
    public Subscription reactivateSubscription(final Subscription subscription) {
        return doPUT(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscription.getUuid() + "/reactivate",
                     subscription, Subscription.class);
    }

    /**
     * Update a particular {@link Subscription} by it's UUID
     * <p>
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
     * <p>
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
     * <p>
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
     * <p>
     * Returns subscriptions associated with an account
     *
     * @param accountCode recurly account id
     * @return Subscriptions on the account
     */
    public Subscriptions getAccountSubscriptions(final String accountCode) {
        return doGET(Account.ACCOUNT_RESOURCE
                     + "/" + accountCode
                     + Subscriptions.SUBSCRIPTIONS_RESOURCE,
                     Subscriptions.class,
                     new QueryParams());
    }

    /**
     * Get all the subscriptions on the site
     * <p>
     * Returns all the subscriptions on the site
     *
     * @return Subscriptions on the site
     */
    public Subscriptions getSubscriptions() {
        return doGET(Subscriptions.SUBSCRIPTIONS_RESOURCE,
                Subscriptions.class, new QueryParams());
    }

    /**
     * Get all the subscriptions on the site given some sort and filter params.
     * <p>
     * Returns all the subscriptions on the site
     *
     * @param state {@link SubscriptionState}
     * @param params {@link QueryParams}
     * @return Subscriptions on the site
     */
    public Subscriptions getSubscriptions(final SubscriptionState state, final QueryParams params) {
        if (state != null) { params.put("state", state.getType()); }

        return doGET(Subscriptions.SUBSCRIPTIONS_RESOURCE,
                Subscriptions.class, params);
    }

    /**
     * Get number of Subscriptions matching the query params
     *
     * @param params {@link QueryParams}
     * @return Integer on success, null otherwise
     */
    public Integer getSubscriptionsCount(final QueryParams params) {
        FluentCaseInsensitiveStringsMap map = doHEAD(Subscription.SUBSCRIPTION_RESOURCE,  params);
        return Integer.parseInt(map.getFirstValue(X_RECORDS_HEADER_NAME));
    }

    /**
     * Get the subscriptions for an {@link Account} given query params
     * <p>
     * Returns subscriptions associated with an account
     *
     * @param accountCode recurly account id
     * @param state {@link SubscriptionState}
     * @param params {@link QueryParams}
     * @return Subscriptions on the account
     */
    public Subscriptions getAccountSubscriptions(final String accountCode, final SubscriptionState state, final QueryParams params) {
        if (state != null) params.put("state", state.getType());

        return doGET(Account.ACCOUNT_RESOURCE
                        + "/" + accountCode
                        + Subscriptions.SUBSCRIPTIONS_RESOURCE,
                Subscriptions.class,
                params);
    }

    /**
     * Return all the subscriptions on an invoice.
     *
     * @param invoiceId String Recurly Invoice ID
     * @return all the subscriptions on the invoice
     */
    public Subscriptions getInvoiceSubscriptions(final String invoiceId) {
        return getInvoiceSubscriptions(invoiceId, new QueryParams());
    }

    /**
     * Return all the subscriptions on an invoice given query params.
     *
     * @param invoiceId String Recurly Invoice ID
     * @param params {@link QueryParams}
     * @return all the subscriptions on the invoice
     */
    public Subscriptions getInvoiceSubscriptions(final String invoiceId, final QueryParams params) {
        return doGET(Invoices.INVOICES_RESOURCE
                        + "/" + invoiceId 
                        + Subscriptions.SUBSCRIPTIONS_RESOURCE,
                Subscriptions.class, 
                params);
    }

    /**
     * Post usage to subscription
     * <p>
     *
     * @param subscriptionCode The recurly id of the {@link Subscription }
     * @param addOnCode recurly id of {@link AddOn}
     * @param usage the usage to post on recurly
     * @return the {@link Usage} object as identified by the passed in object
     */
    public Usage postSubscriptionUsage(final String subscriptionCode, final String addOnCode, final Usage usage) {
        return doPOST(Subscription.SUBSCRIPTION_RESOURCE +
                        "/" +
                        subscriptionCode +
                        AddOn.ADDONS_RESOURCE +
                        "/" +
                        addOnCode +
                        Usage.USAGE_RESOURCE,
                usage, Usage.class);
    }

    /**
     * Get Subscription Addon Usages
     * <p>
     *
     * @param subscriptionCode The recurly id of the {@link Subscription }
     * @param addOnCode recurly id of {@link AddOn}
     * @return {@link Usages} for the specified subscription and addOn
     */
    public Usages getSubscriptionUsages(final String subscriptionCode, final String addOnCode, final QueryParams params) {
       return doGET(Subscription.SUBSCRIPTION_RESOURCE +
                        "/" +
                        subscriptionCode +
                        AddOn.ADDONS_RESOURCE +
                        "/" +
                        addOnCode +
                        Usage.USAGE_RESOURCE, Usages.class, params );
    }


    /**
     * Get the subscriptions for an account.
     * This is deprecated. Please use getAccountSubscriptions(String, Subscriptions.State, QueryParams)
     * <p>
     * Returns information about a single account.
     *
     * @param accountCode recurly account id
     * @param status      Only accounts in this status will be returned
     * @return Subscriptions on the account
     */
    @Deprecated
    public Subscriptions getAccountSubscriptions(final String accountCode, final String status) {
        final QueryParams params = new QueryParams();
        if (status != null) params.put("state", status);

        return doGET(Account.ACCOUNT_RESOURCE
                        + "/" + accountCode
                        + Subscriptions.SUBSCRIPTIONS_RESOURCE,
                Subscriptions.class, params);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Update an account's billing info
     * <p>
     * When new or updated credit card information is updated, the billing information is only saved if the credit card
     * is valid. If the account has a past due invoice, the outstanding balance will be collected to validate the
     * billing information.
     * <p>
     * If the account does not exist before the API request, the account will be created if the billing information
     * is valid.
     * <p>
     * Please note: this API end-point may be used to import billing information without security codes (CVV).
     * Recurly recommends requiring CVV from your customers when collecting new or updated billing information.
     *
     * @param accountCode recurly account id
     * @param billingInfo billing info object to create or update
     * @return the newly created or update billing info object on success, null otherwise
     */
    public BillingInfo createOrUpdateBillingInfo(final String accountCode, final BillingInfo billingInfo) {
        return doPUT(Account.ACCOUNT_RESOURCE + "/" + accountCode + BillingInfo.BILLING_INFO_RESOURCE,
                     billingInfo, BillingInfo.class);
    }

    /**
     * Update an account's billing info
     * <p>
     * When new or updated credit card information is updated, the billing information is only saved if the credit card
     * is valid. If the account has a past due invoice, the outstanding balance will be collected to validate the
     * billing information.
     * <p>
     * If the account does not exist before the API request, the account will be created if the billing information
     * is valid.
     * <p>
     * Please note: this API end-point may be used to import billing information without security codes (CVV).
     * Recurly recommends requiring CVV from your customers when collecting new or updated billing information.
     *
     * @deprecated Replaced by {@link #createOrUpdateBillingInfo(String, BillingInfo)} Please pass in the account code rather than setting the account on the BillingInfo object
     *
     * @param billingInfo billing info object to create or update
     * @return the newly created or update billing info object on success, null otherwise
     */
    @Deprecated
    public BillingInfo createOrUpdateBillingInfo(final BillingInfo billingInfo) {
        final String accountCode = billingInfo.getAccount().getAccountCode();
        // Unset it to avoid confusing Recurly
        billingInfo.setAccount(null);
        return doPUT(Account.ACCOUNT_RESOURCE + "/" + accountCode + BillingInfo.BILLING_INFO_RESOURCE,
                     billingInfo, BillingInfo.class);
    }

    /**
     * Lookup an account's billing info
     * <p>
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
     * <p>
     * You may remove any stored billing information for an account. If the account has a subscription, the renewal will
     * go into past due unless you update the billing info before the renewal occurs
     *
     * @param accountCode recurly account id
     */
    public void clearBillingInfo(final String accountCode) {
        doDELETE(Account.ACCOUNT_RESOURCE + "/" + accountCode + BillingInfo.BILLING_INFO_RESOURCE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Account Notes

    /**
     * List an account's notes
     * <p>
     * Returns the account's notes
     *
     * @param accountCode recurly account id
     * @return the notes associated with this account on success, null otherwise
     */
    public AccountNotes getAccountNotes(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + AccountNotes.ACCOUNT_NOTES_RESOURCE,
                     AccountNotes.class, new QueryParams());
    }

    ///////////////////////////////////////////////////////////////////////////
    // User transactions

    /**
     * Lookup an account's transactions history
     * <p>
     * Returns the account's transaction history
     *
     * @param accountCode recurly account id
     * @return the transaction history associated with this account on success, null otherwise
     */
    public Transactions getAccountTransactions(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Transactions.TRANSACTIONS_RESOURCE,
                     Transactions.class, new QueryParams());
    }

    /**
     * Lookup an account's transactions history given query params
     * <p>
     * Returns the account's transaction history
     *
     * @param accountCode recurly account id
     * @param state {@link TransactionState}
     * @param type {@link TransactionType}
     * @param params {@link QueryParams}
     * @return the transaction history associated with this account on success, null otherwise
     */
    public Transactions getAccountTransactions(final String accountCode, final TransactionState state, final TransactionType type, final QueryParams params) {
        if (state != null) params.put("state", state.getType());
        if (type != null) params.put("type", type.getType());

        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Transactions.TRANSACTIONS_RESOURCE,
                Transactions.class, params);
    }

    /**
     * Get site's transaction history
     * <p>
     * All transactions on the site
     *
     * @return the transaction history of the site on success, null otherwise
     */
    public Transactions getTransactions() {
        return doGET(Transactions.TRANSACTIONS_RESOURCE, Transactions.class, new QueryParams());
    }

    /**
     * Get site's transaction history
     * <p>
     * All transactions on the site
     *
     * @param state {@link TransactionState}
     * @param type {@link TransactionType}
     * @param params {@link QueryParams}
     * @return the transaction history of the site on success, null otherwise
     */
    public Transactions getTransactions(final TransactionState state, final TransactionType type, final QueryParams params) {
        if (state != null) params.put("state", state.getType());
        if (type != null) params.put("type", type.getType());

        return doGET(Transactions.TRANSACTIONS_RESOURCE, Transactions.class, params);
    }

    /**
     * Get number of Transactions matching the query params
     *
     * @param params {@link QueryParams}
     * @return Integer on success, null otherwise
     */
    public Integer getTransactionsCount(final QueryParams params) {
        FluentCaseInsensitiveStringsMap map = doHEAD(Transactions.TRANSACTIONS_RESOURCE, params);
        return Integer.parseInt(map.getFirstValue(X_RECORDS_HEADER_NAME));
    }

    /**
     * Lookup a transaction
     *
     * @param transactionId recurly transaction id
     * @return the transaction if found, null otherwise
     */
    public Transaction getTransaction(final String transactionId) {
        if (transactionId == null || transactionId.isEmpty())
            throw new RuntimeException("transactionId cannot be empty!");

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

    /**
     * Get the subscriptions for a {@link Transaction}.
     * <p>
     * Returns subscriptions associated with a transaction
     *
     * @param transactionId recurly transaction id
     * @return Subscriptions on the transaction
     */
    public Subscriptions getTransactionSubscriptions(final String transactionId) {
        return doGET(Transactions.TRANSACTIONS_RESOURCE
                        + "/" + transactionId
                        + Subscriptions.SUBSCRIPTIONS_RESOURCE,
                Subscriptions.class,
                new QueryParams());
    }

    ///////////////////////////////////////////////////////////////////////////
    // User invoices

    /**
     * Lookup an invoice
     * <p>
     * Returns the invoice given an integer id
     *
     * @deprecated Please switch to using a string for invoice ids
     *
     * @param invoiceId Recurly Invoice ID
     * @return the invoice
     */
    @Deprecated
    public Invoice getInvoice(final Integer invoiceId) {
        return getInvoice(invoiceId.toString());
    }

    /**
     * Lookup an invoice given an invoice id
     *
     * <p>
     * Returns the invoice given a string id.
     * The invoice may or may not have acountry code prefix (ex: IE1023).
     * For more information on invoicing and prefixes, see:
     * https://docs.recurly.com/docs/site-settings#section-invoice-prefixing
     *
     * @param invoiceId String Recurly Invoice ID
     * @return the invoice
     */
    public Invoice getInvoice(final String invoiceId) {
        if (invoiceId == null || invoiceId.isEmpty())
            throw new RuntimeException("invoiceId cannot be empty!");

        return doGET(Invoices.INVOICES_RESOURCE + "/" + invoiceId, Invoice.class);
    }

    /**
     * Update an invoice
     * <p>
     * Updates an existing invoice.
     *
     * @param invoiceId String Recurly Invoice ID
     * @return the updated invoice object on success, null otherwise
     */
    public Invoice updateInvoice(final String invoiceId, final Invoice invoice) {
        return doPUT(Invoices.INVOICES_RESOURCE + "/" + invoiceId, invoice, Invoice.class);
    }

    /**
     * Fetch invoice pdf
     * <p>
     * Returns the invoice pdf as an inputStream
     *
     * @deprecated Prefer using Invoice#getId() as the id param (which is a String)
     *
     * @param invoiceId Recurly Invoice ID
     * @return the invoice pdf as an inputStream
     */
    @Deprecated
    public InputStream getInvoicePdf(final Integer invoiceId) {
        return getInvoicePdf(invoiceId.toString());
    }

    /**
     * Fetch invoice pdf
     * <p>
     * Returns the invoice pdf as an inputStream
     *
     * @param invoiceId String Recurly Invoice ID
     * @return the invoice pdf as an inputStream
     */
    public InputStream getInvoicePdf(final String invoiceId) {
        if (invoiceId == null || invoiceId.isEmpty())
            throw new RuntimeException("invoiceId cannot be empty!");

        return doGETPdf(Invoices.INVOICES_RESOURCE + "/" + invoiceId);
    }

    /**
     * Lookup all invoices
     * <p>
     * Returns all invoices on the site
     *
     * @return the invoices associated with this site on success, null otherwise
     */
    public Invoices getInvoices() {
        return doGET(Invoices.INVOICES_RESOURCE, Invoices.class, new QueryParams());
    }

    /**
     * Return all the invoices given query params
     * <p>
     *
     * @param params {@link QueryParams}
     * @return all invoices matching the query
     */
    public Invoices getInvoices(final QueryParams params) {
        return doGET(Invoices.INVOICES_RESOURCE, Invoices.class, params);
    }

    /**
     * Return all the invoices given query params
     * <p>
     *
     * @param params {@link QueryParams}
     * @return the count of invoices matching the query
     */
    public int getInvoicesCount(final QueryParams params) {
        FluentCaseInsensitiveStringsMap map = doHEAD(Invoices.INVOICES_RESOURCE, params);
        return Integer.parseInt(map.getFirstValue(X_RECORDS_HEADER_NAME));
    }

    /**
     * Return all the transactions on an invoice. Only use this endpoint
     * if you have more than 500 transactions on an invoice.
     * <p>
     *
     * @param invoiceId String Recurly Invoice ID
     * @return all the transactions on the invoice
     */
    public Transactions getInvoiceTransactions(final String invoiceId) {
        return doGET(Invoices.INVOICES_RESOURCE + "/" + invoiceId + Transactions.TRANSACTIONS_RESOURCE,
                     Transactions.class, new QueryParams());
    }
    
    /**
     * Lookup an account's invoices
     * <p>
     * Returns the account's invoices
     *
     * @param accountCode recurly account id
     * @return the invoices associated with this account on success, null otherwise
     */
    public Invoices getAccountInvoices(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Invoices.INVOICES_RESOURCE,
                     Invoices.class, new QueryParams());
    }

    /**
     * Lookup an invoice's original invoices (e.g. a refund invoice has original_invoices)
     * <p>
     * Returns the invoice's original invoices
     *
     * @param invoiceId the invoice id
     * @return the original invoices associated with this invoice on success. Throws RecurlyAPIError if not found
     */
    public Invoices getOriginalInvoices(final String invoiceId) {
        return doGET(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/original_invoices",
                    Invoices.class, new QueryParams());
    }

    /**
     * Refund an invoice given an open amount
     * <p/>
     * Returns the refunded invoice
     *
     * @deprecated Please use refundInvoice(String, InvoiceRefund)
     *
     * @param invoiceId The id of the invoice to refund
     * @param amountInCents The open amount to refund
     * @param method If credit line items exist on the invoice, this parameter specifies which refund method to use first
     * @return the refunded invoice
     */
    @Deprecated
    public Invoice refundInvoice(final String invoiceId, final Integer amountInCents, final RefundMethod method) {
        final InvoiceRefund invoiceRefund = new InvoiceRefund();
        invoiceRefund.setRefundMethod(method);
        invoiceRefund.setAmountInCents(amountInCents);

        return refundInvoice(invoiceId, invoiceRefund);
    }

    /**
     * Refund an invoice given some line items
     * <p/>
     * Returns the refunded invoice
     *
     * @deprecated Please use refundInvoice(String, InvoiceRefund)
     *
     * @param invoiceId The id of the invoice to refund
     * @param lineItems The list of adjustment refund objects
     * @param method If credit line items exist on the invoice, this parameter specifies which refund method to use first
     * @return the refunded invoice
     */
    @Deprecated
    public Invoice refundInvoice(final String invoiceId, List<AdjustmentRefund> lineItems, final RefundMethod method) {
        final InvoiceRefund invoiceRefund = new InvoiceRefund();
        invoiceRefund.setRefundMethod(method);
        invoiceRefund.setLineItems(lineItems);

        return refundInvoice(invoiceId, invoiceRefund);
    }

    /**
     * Refund an invoice given some options
     * <p/>
     * Returns the refunded invoice
     *
     * @param invoiceId The id of the invoice to refund
     * @param refundOptions The options for the refund
     * @return the refunded invoice
     */
    public Invoice refundInvoice(final String invoiceId, final InvoiceRefund refundOptions) {
        return doPOST(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/refund", refundOptions, Invoice.class);
    }

    /**
     * Lookup an account's shipping addresses
     * <p>
     * Returns the account's shipping addresses
     *
     * @param accountCode recurly account id
     * @return the shipping addresses associated with this account on success, null otherwise
     */
    public ShippingAddresses getAccountShippingAddresses(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + ShippingAddresses.SHIPPING_ADDRESSES_RESOURCE,
                ShippingAddresses.class, new QueryParams());
    }

    /**
     * Get an existing shipping address
     * <p>
     *
     * @param accountCode recurly account id
     * @param shippingAddressId the shipping address id to fetch
     * @return the newly created shipping address on success
     */
    public ShippingAddress getShippingAddress(final String accountCode, final long shippingAddressId) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + ShippingAddresses.SHIPPING_ADDRESSES_RESOURCE + "/" + shippingAddressId,
                ShippingAddress.class);
    }

    /**
     * Create a shipping address on an existing account
     * <p>
     *
     * @param accountCode recurly account id
     * @param shippingAddress the shipping address request data
     * @return the newly created shipping address on success
     */
    public ShippingAddress createShippingAddress(final String accountCode, final ShippingAddress shippingAddress) {
        return doPOST(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + ShippingAddresses.SHIPPING_ADDRESSES_RESOURCE, shippingAddress,
                ShippingAddress.class);
    }

    /**
     * Update an existing shipping address
     * <p>
     *
     * @param accountCode recurly account id
     * @param shippingAddressId the shipping address id to update
     * @param shippingAddress the shipping address request data
     * @return the updated shipping address on success
     */
    public ShippingAddress updateShippingAddress(final String accountCode, final long shippingAddressId, ShippingAddress shippingAddress) {
        return doPUT(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + ShippingAddresses.SHIPPING_ADDRESSES_RESOURCE + "/" + shippingAddressId, shippingAddress,
                ShippingAddress.class);
    }

    /**
     * Delete an existing shipping address
     * <p>
     *
     * @param accountCode recurly account id
     * @param shippingAddressId the shipping address id to delete
     */
    public void deleteShippingAddress(final String accountCode, final long shippingAddressId) {
        doDELETE(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + ShippingAddresses.SHIPPING_ADDRESSES_RESOURCE + "/" + shippingAddressId);
    }

    /**
     * Lookup an account's invoices given query params
     * <p>
     * Returns the account's invoices
     *
     * @param accountCode recurly account id
     * @param state {@link InvoiceState} state of the invoices
     * @param params {@link QueryParams}
     * @return the invoices associated with this account on success, null otherwise
     */
    public Invoices getAccountInvoices(final String accountCode, final InvoiceState state, final QueryParams params) {
        if (state != null) params.put("state", state.getType());
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Invoices.INVOICES_RESOURCE,
                Invoices.class, params);
    }

    /**
     * Post an invoice: invoice pending charges on an account
     * <p>
     * Returns an invoice collection
     *
     * @param accountCode
     * @return the invoice collection that was generated on success, null otherwise
     */
    public InvoiceCollection postAccountInvoice(final String accountCode, final Invoice invoice) {
        return doPOST(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Invoices.INVOICES_RESOURCE, invoice, InvoiceCollection.class);
    }

    /**
     * Mark an invoice as paid successfully - Recurly Enterprise Feature
     *
     * @deprecated Prefer using Invoice#getId() as the id param (which is a String)
     *
     * @param invoiceId Recurly Invoice ID
     */
    @Deprecated
    public Invoice markInvoiceSuccessful(final Integer invoiceId) {
        return markInvoiceSuccessful(invoiceId.toString());
    }

    /**
     * Mark an invoice as paid successfully - Recurly Enterprise Feature
     *
     * @param invoiceId String Recurly Invoice ID
     */
    public Invoice markInvoiceSuccessful(final String invoiceId) {
        return doPUT(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/mark_successful", null, Invoice.class);
    }

    /**
     * Mark an invoice as failed collection
     *
     * @deprecated Prefer using Invoice#getId() as the id param (which is a String)
     *
     * @param invoiceId Recurly Invoice ID
     */
    @Deprecated
    public InvoiceCollection markInvoiceFailed(final Integer invoiceId) {
        return markInvoiceFailed(invoiceId.toString());
    }

    /**
     * Mark an invoice as failed collection
     *
     * @param invoiceId String Recurly Invoice ID
     */
    public InvoiceCollection markInvoiceFailed(final String invoiceId) {
        return doPUT(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/mark_failed", null, InvoiceCollection.class);
    }

    /**
     * Force collect an invoice
     *
     * @param invoiceId String Recurly Invoice ID
     */
    public Invoice forceCollectInvoice(final String invoiceId) {
        return doPUT(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/collect", null, Invoice.class);
    }

    /**
     * Force collect an invoice
     *
     * @param transactionType String The gateway transaction type. Currency accepts value "moto".
     * @param invoiceId String Recurly Invoice ID
     */
    public Invoice forceCollectInvoice(final String invoiceId, final String transactionType) {
        Invoice request = new Invoice();
        request.setTransactionType(transactionType);
        return doPUT(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/collect", request, Invoice.class);
    }

    /**
     * Void Invoice
     *
     * @param invoiceId String Recurly Invoice ID
     */
    public Invoice voidInvoice(final String invoiceId) {
        return doPUT(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/void", null, Invoice.class);
    }

    /**
     * Enter an offline payment for a manual invoice (beta) - Recurly Enterprise Feature
     *
     * @deprecated Prefer using Invoice#getId() as the id param (which is a String)
     *
     * @param invoiceId Recurly Invoice ID
     * @param payment   The external payment
     */
    @Deprecated
    public Transaction enterOfflinePayment(final Integer invoiceId, final Transaction payment) {
        return enterOfflinePayment(invoiceId.toString(), payment);
    }

    /**
     * Enter an offline payment for a manual invoice (beta) - Recurly Enterprise Feature
     *
     * @param invoiceId String Recurly Invoice ID
     * @param payment   The external payment
     */
    public Transaction enterOfflinePayment(final String invoiceId, final Transaction payment) {
        return doPOST(Invoices.INVOICES_RESOURCE + "/" + invoiceId + "/transactions", payment, Transaction.class);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create an Item's info
     * <p>
     *
     * @param item The item to create on recurly
     * @return the item object as identified by the passed in ID
     */
    public Item createItem(final Item item) {
        return doPOST(Item.ITEMS_RESOURCE, item, Item.class);
    }

    /**
     * Update an Item's info
     * <p>
     *
     * @param item The Item to update on recurly
     * @return the updated item object
     */
    public Item updateItem(final String itemCode, final Item item) {
        return doPUT(Item.ITEMS_RESOURCE + "/" + itemCode, item, Item.class);
    }

    /**
     * Get a Item's details
     * <p>
     *
     * @param itemCode recurly id of item
     * @return the item object as identified by the passed in ID
     */
    public Item getItem(final String itemCode) {
        if (itemCode == null || itemCode.isEmpty())
            throw new RuntimeException("itemCode cannot be empty!");

        return doGET(Item.ITEMS_RESOURCE + "/" + itemCode, Item.class);
    }

    /**
     * Return all the items
     * <p>
     *
     * @return the item object as identified by the passed in ID
     */
    public Items getItems() {
        return doGET(Items.ITEMS_RESOURCE, Items.class, new QueryParams());
    }

    /**
     * Deletes a {@link Item}
     * <p>
     *
     * @param itemCode The {@link Item} object to delete.
     */
    public void deleteItem(final String itemCode) {
        doDELETE(Item.ITEMS_RESOURCE +
                "/" +
                itemCode);
    }

    /**
     * Reactivating a canceled item
     * <p>
     * Reactivate a canceled item.
     *
     * @param item Item object
     * @return Item
     */
    public Item reactivateItem(final String itemCode) {
        return doPUT(Item.ITEMS_RESOURCE + "/" + itemCode + "/reactivate",
                null, Item.class);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create a Plan's info
     * <p>
     *
     * @param plan The plan to create on recurly
     * @return the plan object as identified by the passed in ID
     */
    public Plan createPlan(final Plan plan) {
        return doPOST(Plan.PLANS_RESOURCE, plan, Plan.class);
    }

    /**
     * Update a Plan's info
     * <p>
     *
     * @param plan The plan to update on recurly
     * @return the updated plan object
     */
    public Plan updatePlan(final Plan plan) {
        return doPUT(Plan.PLANS_RESOURCE + "/" + plan.getPlanCode(), plan, Plan.class);
    }

    /**
     * Get a Plan's details
     * <p>
     *
     * @param planCode recurly id of plan
     * @return the plan object as identified by the passed in ID
     */
    public Plan getPlan(final String planCode) {
        if (planCode == null || planCode.isEmpty())
            throw new RuntimeException("planCode cannot be empty!");

        return doGET(Plan.PLANS_RESOURCE + "/" + planCode, Plan.class);
    }

    /**
     * Return all the plans
     * <p>
     *
     * @return the plan object as identified by the passed in ID
     */
    public Plans getPlans() {
        return doGET(Plans.PLANS_RESOURCE, Plans.class, new QueryParams());
    }

    /**
     * Return all the plans given query params
     * <p>
     *
     * @param params {@link QueryParams}
     * @return the plan object as identified by the passed in ID
     */
    public Plans getPlans(final QueryParams params) {
        return doGET(Plans.PLANS_RESOURCE, Plans.class, params);
    }

    /**
     * Get number of Plans matching the query params
     *
     * @param params {@link QueryParams}
     * @return Integer on success, null otherwise
     */
    public Integer getPlansCount(final QueryParams params) {
        FluentCaseInsensitiveStringsMap map = doHEAD(Plans.PLANS_RESOURCE, params);
        return Integer.parseInt(map.getFirstValue(X_RECORDS_HEADER_NAME));
    }

    /**
     * Deletes a {@link Plan}
     * <p>
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
     * <p>
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
     * <p>
     *
     * @param addOnCode recurly id of {@link AddOn}
     * @param planCode  recurly id of {@link Plan}
     * @return the {@link AddOn} object as identified by the passed in plan and add-on IDs
     */
    public AddOn getAddOn(final String planCode, final String addOnCode) {
        if (addOnCode == null || addOnCode.isEmpty())
            throw new RuntimeException("addOnCode cannot be empty!");

        return doGET(Plan.PLANS_RESOURCE +
                     "/" +
                     planCode +
                     AddOn.ADDONS_RESOURCE +
                     "/" +
                     addOnCode, AddOn.class);
    }

    /**
     * Return all the {@link AddOn} for a {@link Plan}
     * <p>
     *
     * @param planCode
     * @return the {@link AddOn} objects as identified by the passed plan ID
     */
    public AddOns getAddOns(final String planCode) {
        return doGET(Plan.PLANS_RESOURCE +
                "/" +
                planCode +
                AddOn.ADDONS_RESOURCE,
                AddOns.class,
                new QueryParams());
    }

    /**
     * Return all the {@link AddOn} for a {@link Plan}
     * <p>
     *
     * @param planCode
     * @param params {@link QueryParams}
     * @return the {@link AddOn} objects as identified by the passed plan ID
     */
    public AddOns getAddOns(final String planCode, final QueryParams params) {
        return doGET(Plan.PLANS_RESOURCE +
                "/" +
                planCode +
                AddOn.ADDONS_RESOURCE,
                AddOns.class,
                params);
    }

    /**
     * Deletes an {@link AddOn} for a Plan
     * <p>
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

    /**
     * Updates an {@link AddOn} for a Plan
     * <p>
     *
     * @param planCode  The {@link Plan} object.
     * @param addOnCode The {@link AddOn} object to update.
     * @param addOn The updated {@link AddOn} data.
     *
     * @return the updated {@link AddOn} object.
     */
    public AddOn updateAddOn(final String planCode, final String addOnCode, final AddOn addOn) {
        return doPUT(Plan.PLANS_RESOURCE +
                "/" +
                planCode +
                AddOn.ADDONS_RESOURCE +
                "/" +
                addOnCode,
                addOn,
                AddOn.class);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create a {@link Coupon}
     * <p>
     *
     * @param coupon The coupon to create on recurly
     * @return the {@link Coupon} object
     */
    public Coupon createCoupon(final Coupon coupon) {
        return doPOST(Coupon.COUPON_RESOURCE, coupon, Coupon.class);
    }

    /**
     * Get a Coupon
     * <p>
     *
     * @param couponCode The code for the {@link Coupon}
     * @return The {@link Coupon} object as identified by the passed in code
     */
    public Coupon getCoupon(final String couponCode) {
        if (couponCode == null || couponCode.isEmpty())
            throw new RuntimeException("couponCode cannot be empty!");

        return doGET(Coupon.COUPON_RESOURCE + "/" + couponCode, Coupon.class);
    }

    /**
     * Delete a {@link Coupon}
     * <p>
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
     * Lookup the first coupon redemption on an account.
     *
     * @param accountCode recurly account id
     * @return the coupon redemption for this account on success, null otherwise
     */
    public Redemption getCouponRedemptionByAccount(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Redemption.REDEMPTION_RESOURCE,
                     Redemption.class);
    }

    /**
     * Lookup all coupon redemptions on an account.
     *
     * @param accountCode recurly account id
     * @return the coupon redemptions for this account on success, null otherwise
     */
    public Redemptions getCouponRedemptionsByAccount(final String accountCode) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Redemption.REDEMPTIONS_RESOURCE,
                Redemptions.class, new QueryParams());
    }

    /**
     * Lookup all coupon redemptions on an account given query params.
     *
     * @param accountCode recurly account id
     * @param params {@link QueryParams}
     * @return the coupon redemptions for this account on success, null otherwise
     */
    public Redemptions getCouponRedemptionsByAccount(final String accountCode, final QueryParams params) {
        return doGET(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Redemption.REDEMPTIONS_RESOURCE,
                Redemptions.class, params);
    }

    /**
     * Lookup the first coupon redemption on an invoice.
     *
     * @deprecated Prefer using Invoice#getId() as the id param (which is a String)
     *
     * @param invoiceNumber invoice number
     * @return the coupon redemption for this invoice on success, null otherwise
     */
    @Deprecated
    public Redemption getCouponRedemptionByInvoice(final Integer invoiceNumber) {
        return getCouponRedemptionByInvoice(invoiceNumber.toString());
    }

    /**
     * Lookup the first coupon redemption on an invoice.
     *
     * @param invoiceId String invoice id
     * @return the coupon redemption for this invoice on success, null otherwise
     */
    public Redemption getCouponRedemptionByInvoice(final String invoiceId) {
        return doGET(Invoices.INVOICES_RESOURCE + "/" + invoiceId + Redemption.REDEMPTION_RESOURCE,
                Redemption.class);
    }


    /**
     * Lookup all coupon redemptions on an invoice.
     *
     * @deprecated Prefer using Invoice#getId() as the id param (which is a String)
     *
     * @param invoiceNumber invoice number
     * @return the coupon redemptions for this invoice on success, null otherwise
     */
    @Deprecated
    public Redemptions getCouponRedemptionsByInvoice(final Integer invoiceNumber) {
        return getCouponRedemptionsByInvoice(invoiceNumber.toString(), new QueryParams());
    }

    /**
     * Lookup all coupon redemptions on an invoice.
     *
     * @param invoiceId String invoice id
     * @return the coupon redemptions for this invoice on success, null otherwise
     */
    public Redemptions getCouponRedemptionsByInvoice(final String invoiceId) {
        return getCouponRedemptionsByInvoice(invoiceId, new QueryParams());
    }

    /**
     * Lookup all coupon redemptions on an invoice given query params.
     *
     * @deprecated Prefer using Invoice#getId() as the id param (which is a String)
     *
     * @param invoiceNumber invoice number
     * @param params {@link QueryParams}
     * @return the coupon redemptions for this invoice on success, null otherwise
     */
    @Deprecated
    public Redemptions getCouponRedemptionsByInvoice(final Integer invoiceNumber, final QueryParams params) {
        return getCouponRedemptionsByInvoice(invoiceNumber.toString(), params);
    }

    /**
     * Lookup all coupon redemptions on an invoice given query params.
     *
     * @param invoiceId String invoice id
     * @param params {@link QueryParams}
     * @return the coupon redemptions for this invoice on success, null otherwise
     */
    public Redemptions getCouponRedemptionsByInvoice(final String invoiceId, final QueryParams params) {
        return doGET(Invoices.INVOICES_RESOURCE + "/" + invoiceId + Redemption.REDEMPTIONS_RESOURCE,
                Redemptions.class, params);
    }

    /**
     * Lookup all coupon redemptions on a subscription given query params.
     *
     * @param subscriptionUuid String subscription uuid
     * @param params {@link QueryParams}
     * @return the coupon redemptions for this subscription on success, null otherwise
     */
    public Redemptions getCouponRedemptionsBySubscription(final String subscriptionUuid, final QueryParams params) {
        return doGET(Subscription.SUBSCRIPTION_RESOURCE + "/" + subscriptionUuid + Redemptions.REDEMPTIONS_RESOURCE,
                Redemptions.class, params);
    }

    /**
     * Deletes a coupon redemption from an account.
     *
     * @param accountCode recurly account id
     */
    public void deleteCouponRedemption(final String accountCode) {
        doDELETE(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Redemption.REDEMPTION_RESOURCE);
    }

    /**
     * Deletes a specific redemption.
     *
     * @param accountCode recurly account id
     * @param redemptionUuid recurly coupon redemption uuid
     */
    public void deleteCouponRedemption(final String accountCode, final String redemptionUuid) {
        doDELETE(Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + Redemption.REDEMPTIONS_RESOURCE + "/" + redemptionUuid);
    }

    /**
     * Generates unique codes for a bulk coupon.
     *
     * @param couponCode recurly coupon code (must have been created as type: bulk)
     * @param coupon A coupon with number of unique codes set
     */
    public Coupons generateUniqueCodes(final String couponCode, final Coupon coupon) {
        Coupons coupons = doPOST(Coupon.COUPON_RESOURCE + "/" + couponCode + Coupon.GENERATE_RESOURCE, coupon, Coupons.class);
        return coupons.getStart();
    }

    /**
     * Lookup all unique codes for a bulk coupon given query params.
     *
     * @param couponCode String coupon code
     * @param params {@link QueryParams}
     * @return the unique coupon codes for the coupon code on success, null otherwise
     */
    public Coupons getUniqueCouponCodes(final String couponCode, final QueryParams params) {
        return doGET(Coupon.COUPON_RESOURCE + "/" + couponCode + Coupon.UNIQUE_CODES_RESOURCE,
                Coupons.class, params);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    // Recurly.js API
    //
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Fetch Subscription
     * <p>
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
     * <p>
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
     * <p>
     * Returns invoice from a recurly.js token.
     *
     * @param recurlyToken token given by recurly.js
     * @return invoice object on success, null otherwise
     */
    public Invoice fetchInvoice(final String recurlyToken) {
        return fetch(recurlyToken, Invoice.class);
    }

    /**
     * Get Gift Cards given query params
     * <p>
     * Returns information about all gift cards.
     *
     * @param params {@link QueryParams}
     * @return gitfcards object on success, null otherwise
     */
    public GiftCards getGiftCards(final QueryParams params) {
        return doGET(GiftCards.GIFT_CARDS_RESOURCE, GiftCards.class, params);
    }

    /**
     * Get Gift Cards
     * <p>
     * Returns information about all gift cards.
     *
     * @return gitfcards object on success, null otherwise
     */
    public GiftCards getGiftCards() {
        return doGET(GiftCards.GIFT_CARDS_RESOURCE, GiftCards.class, new QueryParams());
    }

    /**
     * Get number of GiftCards matching the query params
     *
     * @param params {@link QueryParams}
     * @return Integer on success, null otherwise
     */
    public Integer getGiftCardsCount(final QueryParams params) {
        FluentCaseInsensitiveStringsMap map = doHEAD(GiftCards.GIFT_CARDS_RESOURCE, params);
        return Integer.parseInt(map.getFirstValue(X_RECORDS_HEADER_NAME));
    }

    /**
     * Get a Gift Card
     * <p>
     *
     * @param giftCardId The id for the {@link GiftCard}
     * @return The {@link GiftCard} object as identified by the passed in id
     */
    public GiftCard getGiftCard(final Long giftCardId) {
        return doGET(GiftCards.GIFT_CARDS_RESOURCE + "/" + Long.toString(giftCardId), GiftCard.class);
    }

    /**
     * Redeem a Gift Card
     * <p>
     *
     * @param redemptionCode The redemption code the {@link GiftCard}
     * @param accountCode The account code for the {@link Account}
     * @return The updated {@link GiftCard} object as identified by the passed in id
     */
    public GiftCard redeemGiftCard(final String redemptionCode, final String accountCode) {
        final GiftCard.Redemption redemptionData = GiftCard.createRedemption(accountCode);
        final String url = GiftCards.GIFT_CARDS_RESOURCE + "/" + redemptionCode + "/redeem";

        return doPOST(url, redemptionData, GiftCard.class);
    }

    /**
     * Purchase a GiftCard
     * <p>
     *
     * @param giftCard The giftCard data
     * @return the giftCard object
     */
    public GiftCard purchaseGiftCard(final GiftCard giftCard) {
        return doPOST(GiftCards.GIFT_CARDS_RESOURCE, giftCard, GiftCard.class);
    }

    /**
     * Preview a GiftCard
     * <p>
     *
     * @param giftCard The giftCard data
     * @return the giftCard object
     */
    public GiftCard previewGiftCard(final GiftCard giftCard) {
        return doPOST(GiftCards.GIFT_CARDS_RESOURCE + "/preview", giftCard, GiftCard.class);
    }

    /**
     * Return all the MeasuredUnits
     * <p>
     *
     * @return the MeasuredUnits object as identified by the passed in ID
     */
    public MeasuredUnits getMeasuredUnits() {
        return doGET(MeasuredUnits.MEASURED_UNITS_RESOURCE, MeasuredUnits.class, new QueryParams());
    }

    /**
     * Create a MeasuredUnit's info
     * <p>
     *
     * @param measuredUnit The measuredUnit to create on recurly
     * @return the measuredUnit object as identified by the passed in ID
     */
    public MeasuredUnit createMeasuredUnit(final MeasuredUnit measuredUnit) {
        return doPOST(MeasuredUnit.MEASURED_UNITS_RESOURCE, measuredUnit, MeasuredUnit.class);
    }

    /**
     * Purchases endpoint
     * <p>
     * https://dev.recurly.com/docs/create-purchase
     *
     * @param purchase The purchase data
     * @return The created invoice collection
     */
    public InvoiceCollection purchase(final Purchase purchase) {
        return doPOST(Purchase.PURCHASES_ENDPOINT, purchase, InvoiceCollection.class);
    }

    /**
     * Purchases preview endpoint
     * <p>
     * https://dev.recurly.com/docs/preview-purchase
     *
     * @param purchase The purchase data
     * @return The preview invoice collection
     */
    public InvoiceCollection previewPurchase(final Purchase purchase) {
        return doPOST(Purchase.PURCHASES_ENDPOINT + "/preview", purchase, InvoiceCollection.class);
    }

    /**
     * Purchases authorize endpoint.
     *
     * Generate an authorized invoice for the purchase. Runs validations
     + but does not run any transactions. This endpoint will create a
     + pending purchase that can be activated at a later time once payment
     + has been completed on an external source (e.g. Adyen's Hosted
     + Payment Pages).
     *
     * <p>
     * https://dev.recurly.com/docs/authorize-purchase
     *
     * @param purchase The purchase data
     * @return The authorized invoice collection
     */
    public InvoiceCollection authorizePurchase(final Purchase purchase) {
        return doPOST(Purchase.PURCHASES_ENDPOINT + "/authorize", purchase, InvoiceCollection.class);
    }

    /**
     * Purchases pending endpoint.
     *
     * Use for Adyen HPP transaction requests. Runs validations
     + but does not run any transactions.
     *
     * <p>
     * https://dev.recurly.com/docs/pending-purchase
     *
     * @param purchase The purchase data
     * @return The authorized invoice collection
     */
    public InvoiceCollection pendingPurchase(final Purchase purchase) {
        return doPOST(Purchase.PURCHASES_ENDPOINT + "/pending", purchase, InvoiceCollection.class);
    }

    /**
     * Sets the acquisition details for an account
     * <p>
     * https://dev.recurly.com/docs/create-account-acquisition
     *
     * @param accountCode The account's account code
     * @param acquisition The AccountAcquisition data
     * @return The created AccountAcquisition object
     */
    public AccountAcquisition createAccountAcquisition(final String accountCode, final AccountAcquisition acquisition) {
        final String path = Account.ACCOUNT_RESOURCE + "/" + accountCode + AccountAcquisition.ACCOUNT_ACQUISITION_RESOURCE;
        return doPOST(path, acquisition, AccountAcquisition.class);
    }

    /**
     * Gets the acquisition details for an account
     * <p>
     * https://dev.recurly.com/docs/create-account-acquisition
     *
     * @param accountCode The account's account code
     * @return The created AccountAcquisition object
     */
    public AccountAcquisition getAccountAcquisition(final String accountCode) {
        final String path = Account.ACCOUNT_RESOURCE + "/" + accountCode + AccountAcquisition.ACCOUNT_ACQUISITION_RESOURCE;
        return doGET(path, AccountAcquisition.class);
    }

    /**
     * Updates the acquisition details for an account
     * <p>
     * https://dev.recurly.com/docs/update-account-acquisition
     *
     * @param accountCode The account's account code
     * @param acquisition The AccountAcquisition data
     * @return The created AccountAcquisition object
     */
    public AccountAcquisition updateAccountAcquisition(final String accountCode, final AccountAcquisition acquisition) {
        final String path = Account.ACCOUNT_RESOURCE + "/" + accountCode + AccountAcquisition.ACCOUNT_ACQUISITION_RESOURCE;
        return doPUT(path, acquisition, AccountAcquisition.class);
    }

    /**
     * Clear the acquisition details for an account
     * <p>
     * https://dev.recurly.com/docs/clear-account-acquisition
     *
     * @param accountCode The account's account code
     */
    public void deleteAccountAcquisition(final String accountCode) {
        doDELETE(Account.ACCOUNT_RESOURCE + "/" + accountCode + AccountAcquisition.ACCOUNT_ACQUISITION_RESOURCE);
    }


    /**
     * Get Credit Payments
     * <p>
     * Returns information about all credit payments.
     *
     * @return CreditPayments on success, null otherwise
     */
    public CreditPayments getCreditPayments() {
        return doGET(CreditPayments.CREDIT_PAYMENTS_RESOURCE, CreditPayments.class, new QueryParams());
    }

    /**
     * Get Credit Payments
     * <p>
     * Returns information about all credit payments.
     *
     * @param params {@link QueryParams}
     * @return CreditPayments on success, null otherwise
     */
    public CreditPayments getCreditPayments(final QueryParams params) {
        return doGET(CreditPayments.CREDIT_PAYMENTS_RESOURCE, CreditPayments.class, params);
    }

    /**
     * Get Credit Payments for a given account
     * <p>
     * Returns information about all credit payments.
     *
     * @param accountCode The account code to filter
     * @param params {@link QueryParams}
     * @return CreditPayments on success, null otherwise
     */
    public CreditPayments getCreditPayments(final String accountCode, final QueryParams params) {
        final String path = Accounts.ACCOUNTS_RESOURCE + "/" + accountCode + CreditPayments.CREDIT_PAYMENTS_RESOURCE;
        return doGET(path, CreditPayments.class, params);
    }

    /**
     * Get Shipping Methods for the site
     * <p>
     * https://dev.recurly.com/docs/list-shipping-methods
     *
     * @return ShippingMethods on success, null otherwise
     */
    public ShippingMethods getShippingMethods() {
        return doGET(ShippingMethods.SHIPPING_METHODS_RESOURCE, ShippingMethods.class, new QueryParams());
    }

    /**
     * Get Shipping Methods for the site
     * <p>
     * https://dev.recurly.com/docs/list-shipping-methods
     *
     * @param params {@link QueryParams}
     * @return ShippingMethods on success, null otherwise
     */
    public ShippingMethods getShippingMethods(final QueryParams params) {
        return doGET(ShippingMethods.SHIPPING_METHODS_RESOURCE, ShippingMethods.class, params);
    }

    /**
     * Look up a shipping method
     * <p>
     * https://dev.recurly.com/docs/lookup-shipping-method
     *
     * @param shippingMethodCode The code for the {@link ShippingMethod}
     * @return The {@link ShippingMethod} object as identified by the passed in code
     */
    public ShippingMethod getShippingMethod(final String shippingMethodCode) {
        if (shippingMethodCode == null || shippingMethodCode.isEmpty())
            throw new RuntimeException("shippingMethodCode cannot be empty!");

        return doGET(ShippingMethod.SHIPPING_METHOD_RESOURCE + "/" + shippingMethodCode, ShippingMethod.class);
    }

    private <T> T fetch(final String recurlyToken, final Class<T> clazz) {
        return doGET(FETCH_RESOURCE + "/" + recurlyToken, clazz);
    }

    ///////////////////////////////////////////////////////////////////////////

    private InputStream doGETPdf(final String resource) {
        return doGETPdfWithFullURL(baseUrl + resource);
    }

    private <T> T doGET(final String resource, final Class<T> clazz) {
        return doGETWithFullURL(clazz, baseUrl + resource);
    }

    private <T> T doGET(final String resource, final Class<T> clazz, QueryParams params) {
        return doGETWithFullURL(clazz, constructUrl(resource, params));
    }

    private String constructUrl(final String resource, QueryParams params) {
        return baseUrl + resource + params.toString();
    }

    public <T> T doGETWithFullURL(final Class<T> clazz, final String url) {
        if (debug()) {
            log.info("Msg to Recurly API [GET] :: URL : {}", url);
        }
        validateHost(url);
        return callRecurlySafeXmlContent(client.prepareGet(url), clazz);
    }

    private InputStream doGETPdfWithFullURL(final String url) {
        if (debug()) {
            log.info(" [GET] :: URL : {}", url);
        }

        return callRecurlySafeGetPdf(url);
    }

    private InputStream callRecurlySafeGetPdf(String url) {
        validateHost(url);

        final Response response;
        final InputStream pdfInputStream;
        try {
            response = clientRequestBuilderCommon(client.prepareGet(url))
                    .addHeader("Accept", "application/pdf")
                    .addHeader("Content-Type", "application/pdf")
                    .execute()
                    .get();
            pdfInputStream = response.getResponseBodyAsStream();

        } catch (InterruptedException e) {
            log.error("Interrupted while calling recurly", e);
            return null;
        } catch (ExecutionException e) {
            log.error("Execution error", e);
            return null;
        } catch (IOException e) {
            log.error("Error retrieving response body", e);
            return null;
        }

        if (response.getStatusCode() != 200) {
            final RecurlyAPIError recurlyAPIError = RecurlyAPIError.buildFromResponse(response);
            throw new RecurlyAPIException(recurlyAPIError);
        }

        return pdfInputStream;
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

        validateHost(baseUrl + resource);

        return callRecurlySafeXmlContent(client.preparePost(baseUrl + resource).setBody(xmlPayload), clazz);
    }

    private <T> T doPUT(final String resource, final RecurlyObject payload, final Class<T> clazz) {
        return doPUT(resource, payload, clazz, new QueryParams());
    }

    private <T> T doPUT(final String resource, final RecurlyObject payload, final Class<T> clazz, final QueryParams params) {
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

        final String url = baseUrl + resource;
        validateHost(url);

        return callRecurlySafeXmlContent(client.preparePut(url).setBody(xmlPayload), clazz);
    }

    private FluentCaseInsensitiveStringsMap doHEAD(final String resource, QueryParams params) {
        if (params == null) {
            params = new QueryParams();
        }

        final String url = constructUrl(resource, params);
        if (debug()) {
            log.info("Msg to Recurly API [HEAD]:: URL : {}", url);
        }

        validateHost(url);

        return callRecurlyNoContent(client.prepareHead(url));
    }

    private void doDELETE(final String resource) {
        validateHost(baseUrl + resource);

        callRecurlySafeXmlContent(client.prepareDelete(baseUrl + resource), null);
    }

    private FluentCaseInsensitiveStringsMap callRecurlyNoContent(final AsyncHttpClient.BoundRequestBuilder builder) {
        try {
            final Response response = clientRequestBuilderCommon(builder)
                    .addHeader("Accept", "application/xml")
                    .addHeader("Content-Type", "application/xml; charset=utf-8")
                    .execute()
                    .get();

            return response.getHeaders();
        } catch (ExecutionException e) {
            log.error("Execution error", e);
            return null;
        }
        catch (InterruptedException e) {
            log.error("Interrupted while calling Recurly", e);
            return null;
        }
    }

    private <T> T callRecurlySafeXmlContent(final AsyncHttpClient.BoundRequestBuilder builder, @Nullable final Class<T> clazz) {
        try {
            return callRecurlyXmlContent(builder, clazz);
        } catch (IOException e) {
            log.warn("Error while calling Recurly", e);
            return null;
        } catch (ExecutionException e) {
            // Extract the errors exception, if any
            if (e.getCause() instanceof ConnectException) {
                // See https://github.com/killbilling/recurly-java-library/issues/185
                throw new ConnectionErrorException(e.getCause());
            } else if (e.getCause() != null &&
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

    private <T> T callRecurlyXmlContent(final AsyncHttpClient.BoundRequestBuilder builder, @Nullable final Class<T> clazz)
            throws IOException, ExecutionException, InterruptedException {
        final Response response = clientRequestBuilderCommon(builder)
                .addHeader("Accept", "application/xml")
                .addHeader("Content-Type", "application/xml; charset=utf-8")
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
                log.warn("Error status code: {}\n", response.getStatusCode());
                RecurlyAPIError recurlyError = RecurlyAPIError.buildFromResponse(response);

                if (response.getStatusCode() == 422) {
                    // 422 is returned for transaction errors (see https://dev.recurly.com/page/transaction-errors)
                    // as well as bad input payloads
                    final Errors errors;
                    try {
                        errors = xmlMapper.readValue(payload, Errors.class);
                    } catch (Exception e) {
                        log.warn("Unable to extract error", e);
                        return null;
                    }

                    // Sometimes a single `Error` response is returned rather than `Errors`.
                    // In this case, all fields will be null.
                    if (errors == null || (
                        errors.getRecurlyErrors() == null &&
                        errors.getTransaction() == null &&
                        errors.getTransactionError() == null
                    )) {
                        recurlyError = RecurlyAPIError.buildFromXml(xmlMapper, payload, response);
                        throw new RecurlyAPIException(recurlyError);
                    }
                    throw new TransactionErrorException(errors);
                } else if (response.getStatusCode() == 401) {
                    recurlyError.setSymbol("unauthorized");
                    recurlyError.setDescription("We could not authenticate your request. Either your subdomain and private key are not set or incorrect");

                    throw new RecurlyAPIException(recurlyError);
                } else {
                    try {
                        recurlyError = RecurlyAPIError.buildFromXml(xmlMapper, payload, response);
                    } catch (Exception e) {
                        log.debug("Unable to extract error", e);
                    }

                    throw new RecurlyAPIException(recurlyError);
                }
            }

            if (clazz == null) {
                return null;
            }

            String location = response.getHeader("Location");
            if (clazz == Coupons.class && location != null && !location.isEmpty()) {
                final RecurlyObjects recurlyObjects = new Coupons();
                recurlyObjects.setRecurlyClient(this);
                recurlyObjects.setStartUrl(location);
                return (T) recurlyObjects;
            }

            final T obj = xmlMapper.readValue(payload, clazz);
            final ResponseMetadata meta = new ResponseMetadata(response);
            if (obj instanceof RecurlyObject) {
                ((RecurlyObject) obj).setRecurlyClient(this);
            } else if (obj instanceof RecurlyObjects) {
                final RecurlyObjects recurlyObjects = (RecurlyObjects) obj;
                recurlyObjects.setRecurlyClient(this);

                // Set the RecurlyClient on all objects for later use
                for (final Object object : recurlyObjects) {
                    ((RecurlyObject) object).setRecurlyClient(this);
                }

                // Set links for pagination
                final String linkHeader = response.getHeader(LINK_HEADER_NAME);
                if (linkHeader != null) {
                    final String[] links = PaginationUtils.getLinks(linkHeader);
                    recurlyObjects.setStartUrl(links[0]);
                    recurlyObjects.setNextUrl(links[1]);
                }
            }

            // Save value of rate limit remaining header
            String rateLimitRemainingString = response.getHeader(X_RATELIMIT_REMAINING_HEADER_NAME);
            if (rateLimitRemainingString != null)
                rateLimitRemaining = Integer.parseInt(rateLimitRemainingString);

            return obj;
        } finally {
            closeStream(in);
        }
    }

    private AsyncHttpClient.BoundRequestBuilder clientRequestBuilderCommon(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
        return requestBuilder.addHeader("Authorization", "Basic " + key)
                .addHeader("X-Api-Version", RECURLY_API_VERSION)
                .addHeader(HttpHeaders.USER_AGENT, userAgent)
                .addHeader("Accept-Language", acceptLanguage)
                .setBodyEncoding("UTF-8");
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

    protected AsyncHttpClient createHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
        final AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();

        // Don't limit the number of connections per host
        // See https://github.com/ning/async-http-client/issues/issue/28
        builder.setMaxConnectionsPerHost(-1);
        builder.setSSLContext(SslUtils.getInstance().getSSLContext());

        return new AsyncHttpClient(builder.build());
    }

    private void validateHost(String url) {
        String host = URI.create(url).getHost();

        // Remove the subdomain from the host
        host = host.substring(host.indexOf(".")+1);

        if (!validHosts.contains(host)) {
            String exc = String.format("Attempted to make call to %s instead of Recurly", host);
            throw new RuntimeException(exc);
        }
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

            final String version = MoreObjects.firstNonNull(getVersionFromGitRepositoryState(gitRepositoryState), defaultVersion);
            final String javaVersion = MoreObjects.firstNonNull(StandardSystemProperty.JAVA_VERSION.value(), defaultJavaVersion);
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
