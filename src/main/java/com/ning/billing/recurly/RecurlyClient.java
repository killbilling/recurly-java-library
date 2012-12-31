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

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;
import javax.xml.bind.DatatypeConverter;

import com.ning.billing.recurly.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class RecurlyClient {

    private static final Logger log = LoggerFactory.getLogger(RecurlyClient.class);

    public static final String RECURLY_DEBUG_KEY = "recurly.debug";
    public static final String RECURLY_PAGE_SIZE_KEY = "recurly.page.size";

    private static final Integer DEFAULT_PAGE_SIZE = new Integer(20);
    private static final String PER_PAGE = "per_page=";

    public static final String FETCH_RESOURCE = "/recurly_js/result";

    /**
     * Checks a system property to see if debugging output is
     * required. Used internally by the client to decide whether to
     * generate debug output
     */
    private static final boolean debug() {
        return Boolean.getBoolean(RECURLY_DEBUG_KEY);
    }

    /**
     * Returns the page Size to use when querying. The page size
     * is set as System.property: recurly.page.size
     */
    public static final Integer getPageSize() {
        Integer pageSize;
        try {
            pageSize = new Integer(System.getProperty(RECURLY_PAGE_SIZE_KEY));
        } catch (NumberFormatException nfex) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public static final String getPageSizeGetParam() {
        return PER_PAGE + getPageSize().toString();
    }

    private final XmlMapper xmlMapper = new XmlMapper();

    private final String key;
    private final String baseUrl;
    private AsyncHttpClient client;

    public RecurlyClient(final String apiKey) {
        this(apiKey, "api.recurly.com", 443, "v2");
    }

    public RecurlyClient(final String apiKey, final String host, final int port, final String version) {
        this.key = DatatypeConverter.printBase64Binary(apiKey.getBytes());
        this.baseUrl = String.format("https://%s:%d/%s", host, port, version);

        final AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
        final AnnotationIntrospector secondary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        final AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
        xmlMapper.setAnnotationIntrospector(pair);
        xmlMapper.registerModule(new JodaModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
     * Get a particular {@link Subscription} by it's UUID
     * <p/>
     * Returns information about a single account.
     *
     * @param uuid UUID of the subscription to lookup
     * @return Subscriptions for the specified user
     */
    public Subscription getSubscription(final String uuid) {
        return doGET(Subscriptions.SUBSCRIPTIONS_RESOURCE
                     + "/" + uuid,
                     Subscription.class);
    }

    /**
     * Update a particular {@link Subscription} by it's UUID
     * <p/>
     * Returns information about a single account.
     *
     * @param uuid UUID of the subscription to update
     * @return Subscription the updated subscription
     */
    public Subscription updateSubscription(final String uuid, SubscriptionUpdate subscriptionUpdate) {
        return doPUT(Subscriptions.SUBSCRIPTIONS_RESOURCE
                + "/" + uuid,
                subscriptionUpdate,
                Subscription.class);
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
     * Creates a {@link Transaction} throgh the Recurly API.
     *
     * @param trans The {@link Transaction} to create
     * @return The created {@link Transaction} object
     */
    public Transaction createTransaction(final Transaction trans) {
        return doPOST(Transactions.TRANSACTIONS_RESOURCE, trans, Transaction.class);
    }

    ///////////////////////////////////////////////////////////////////////////
    // User invoices

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
    public AddOn getAddOns(final String planCode) {
        return doGET(Plan.PLANS_RESOURCE +
                     "/" +
                     planCode +
                     AddOn.ADDONS_RESOURCE, AddOn.class);
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
        StringBuffer url = new StringBuffer(baseUrl);
        url.append(resource);
        if (resource != null && !resource.contains("?")) {
            url.append("?");
        } else {
            url.append("&");
            url.append("&");
        }
        url.append(getPageSizeGetParam());

        if (debug()) {
            log.info("Msg to Recurly API [GET] :: URL : {}", url);
        }
        return callRecurlySafe(client.prepareGet(url.toString()), clazz);
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
            xmlPayload = xmlMapper.writeValueAsString(payload);
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
            log.error("Execution error", e);
            return null;
        } catch (InterruptedException e) {
            log.error("Interrupted while calling Recurly", e);
            return null;
        }
    }

    private <T> T callRecurly(final AsyncHttpClient.BoundRequestBuilder builder, @Nullable final Class<T> clazz)
            throws IOException, ExecutionException, InterruptedException {
        return builder.addHeader("Authorization", "Basic " + key)
                      .addHeader("Accept", "application/xml")
                      .addHeader("Content-Type", "application/xml; charset=utf-8")
                      .execute(new AsyncCompletionHandler<T>() {
                          @Override
                          public T onCompleted(final Response response) throws Exception {
                              if (response.getStatusCode() >= 300) {
                                  log.warn("Recurly error whilst calling: {}", response.getUri());
                                  log.warn("Recurly error: {}", response.getResponseBody());
                                  return null;
                              }

                              if (clazz == null) {
                                  return null;
                              }

                              final InputStream in = response.getResponseBodyAsStream();
                              try {
                                  String payload = convertStreamToString(in);
                                  if (debug()) {
                                      log.info("Msg from Recurly API :: {}", payload);
                                  }
                                  T obj = xmlMapper.readValue(payload, clazz);
                                  return obj;
                              } finally {
                                  closeStream(in);
                              }
                          }
                      }).get();
    }

    private String convertStreamToString(java.io.InputStream is) {
        try {
            return new java.util.Scanner(is).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
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
        builder.setMaximumConnectionsPerHost(-1);
        return new AsyncHttpClient(builder.build());
    }
}
