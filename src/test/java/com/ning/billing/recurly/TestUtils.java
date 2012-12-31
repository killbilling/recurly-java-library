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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.ning.billing.recurly.model.Account;
import com.ning.billing.recurly.model.AddOn;
import com.ning.billing.recurly.model.BillingInfo;
import com.ning.billing.recurly.model.Plan;
import com.ning.billing.recurly.model.Subscription;
import com.ning.billing.recurly.model.Transaction;

public class TestUtils {

    /**
     * Returns a random {@link String}.
     *
     * @return The random {@link String}
     */
    public static String randomString() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 5);
    }

    /**
     * Generates a String comprised of random alpha-numeric
     * chars upto the length as defined by the input param.
     *
     * @param n The length, in number of chars, of the returned
     *          String
     * @return A random alpah-numeric String
     */
    public static String getRandomAlphaNumString(final int n) {
        int offset = n;
        final String retValue = UUID.randomUUID().toString().replace("-", "");

        if (retValue.length() <= n) {
            offset = retValue.length() - 1;
        }
        return retValue.substring(0, offset);
    }

    /**
     * Generates a String comprised of random numeric
     * chars upto the length as defined by the input param.
     *
     * @param n The length, in number of chars, of the returned
     *          String
     * @return A random numeric String
     */
    public static String getRandomNumString(final int n) {
        return "" + new Double(Math.random() * 100).intValue();
    }

    /**
     * Returns a random {@link Integer} within the range 0 to upperRange
     *
     * @param upperRange The limit of the upperRange - the random returned value could be upto and including this value
     * @return The random integer - from within the range 0 to upperRange
     */
    public static Integer randomInteger(final int upperRange) {
        return (int) (Math.random() * upperRange);
    }

    public static String createTestCCNumber() {
        return "4111-1111-1111-1111";
    }

    public static String createTestCCVerificationNumber() {
        return "123";
    }

    public static String createTestCCMonth() {
        return "11";
    }

    public static String createTestCCYear() {
        return "2015";
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Account} object for testing use.
     *
     * @return The random {@link com.ning.billing.recurly.model.Account} object
     */
    public static Account createRandomAccount() {
        final Account account = new Account();
        account.setAcceptLanguage("en_US");
        account.setAccountCode(UUID.randomUUID().toString());
        account.setCompanyName(getRandomAlphaNumString(10));
        account.setEmail(getRandomAlphaNumString(4) + "@test.com");
        account.setFirstName(getRandomAlphaNumString(5));
        account.setLastName(getRandomAlphaNumString(6));

        return account;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.BillingInfo} object for testing use.
     *
     * @return The random {@link com.ning.billing.recurly.model.BillingInfo} object
     */
    public static BillingInfo createRandomBillingInfo() {
        final BillingInfo info = new BillingInfo();
        info.setAccount(createRandomAccount());
        info.setFirstName(getRandomAlphaNumString(5));
        info.setLastName(getRandomAlphaNumString(6));
        info.setCompany(getRandomAlphaNumString(10));
        info.setAddress1(getRandomAlphaNumString(10));
        info.setAddress2(getRandomAlphaNumString(10));
        info.setCity(getRandomAlphaNumString(10));
        info.setState(getRandomAlphaNumString(10));
        info.setZip(getRandomAlphaNumString(5));
        info.setCountry(getRandomAlphaNumString(5));
        info.setPhone(randomInteger(8));
        info.setVatNumber(getRandomNumString(8));
        //info.setIpAddress(LifecycleTest.getRandomAlphaNumString(5));
        info.setYear(createTestCCYear());
        info.setMonth(createTestCCMonth());
        info.setNumber(createTestCCNumber());
        info.setVerificationValue(createTestCCVerificationNumber());

        return info;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Plan} object for testing use.
     *
     * @return The random {@link com.ning.billing.recurly.model.Plan} object
     */
    public static Plan createRandomPlan() {
        final Plan plan = new Plan();
        plan.setPlanCode(getRandomAlphaNumString(10));
        plan.setName(getRandomAlphaNumString(10));
        plan.setPlanIntervalLength(randomInteger(50) + 1);
        plan.setPlanIntervalUnit("months");
        plan.setSetupFeeInCents(createRandomPrice());
        plan.setUnitAmountInCents(createRandomPrice());

        return plan;
    }

    /**
     * Creates a random {@link Plan} object for testing use.
     */
    public static Plan createRandomPlan(final String currencyCode) {
        final Plan plan = createRandomPlan();
        plan.setSetupFeeInCents(createRandomSinglePrice(currencyCode));
        plan.setUnitAmountInCents(createRandomSinglePrice(currencyCode));

        return plan;
    }

    /**
     * Creates a random {@link Plan.RecurlyUnitCurrency} object for testing use.
     *
     * @return The random {@link Plan.RecurlyUnitCurrency} object
     */
    public static Plan.RecurlyUnitCurrency createRandomPrice() {
        final Plan.RecurlyUnitCurrency price = new Plan.RecurlyUnitCurrency();
        /*
        price.setUnitAmountEUR(LifecycleTest.randomInteger(10));
        price.setUnitAmountGBP(LifecycleTest.randomInteger(10));
        price.setUnitAmountUSD(LifecycleTest.randomInteger(10));
        price.setUnitAmountSEK(LifecycleTest.randomInteger(10));
        */
        price.setUnitAmountEUR(10);

        return price;
    }

    /**
     * Creates a random {@link Plan.RecurlyUnitCurrency} object for testing use. The Price
     * object
     *
     * @param currencyCode The curreny for which we shold return a random Price
     * @return The random {@link Plan.RecurlyUnitCurrency} object
     */
    public static Plan.RecurlyUnitCurrency createRandomSinglePrice(final String currencyCode) {
        final Plan.RecurlyUnitCurrency price = new Plan.RecurlyUnitCurrency();

        // using if's as we are not guaranteed to be on JDK7
        if (currencyCode.endsWith("EUR")) {
            price.setUnitAmountEUR(randomInteger(10));
        }

        if (currencyCode.endsWith("GBP")) {
            price.setUnitAmountGBP(randomInteger(10));
        }

        if (currencyCode.endsWith("USD")) {
            price.setUnitAmountUSD(randomInteger(10));
        }

        if (currencyCode.endsWith("SEK")) {
            price.setUnitAmountSEK(randomInteger(10));
        }

        return price;
    }

    /**
     * Creates a random currency {@link String} from the set:
     * <ul>
     * <li>EUR</li>
     * <li>GBP</li>
     * <li>USD</li>
     * <li>SEK</li>
     * </ul>
     *
     * @return The {@link String} code for the randomly chosen currency
     */
    public static String createRandomCurrency() {
        final String[] currencies = {"EUR", "GBP", "USD", "SEK"};
        return currencies[(int) (Math.random() * currencies.length)];
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Subscription} object for use in tests
     *
     * @param currencyCode The currency code for which the subscription will be charged in
     * @return The {@link com.ning.billing.recurly.model.Subscription} object
     */
    public static Subscription createRandomSubscription(final String currencyCode) {
        //
        final Subscription sub = new Subscription();
        sub.setQuantity(randomInteger(10));
        sub.setActivatedAt(DateTime.now());
        sub.setCanceledAt(DateTime.now());
        sub.setExpiresAt(DateTime.now());
        sub.setCurrency(createRandomCurrency());
        sub.setPlan(createRandomPlan());
        sub.setPlanCode(randomString());
        sub.setState(getRandomAlphaNumString(5));
        sub.setAccount(createRandomAccount());
        sub.setUnitAmountInCents(randomInteger(10));
        sub.setCurrency(currencyCode);
        sub.setCurrentPeriodStartedAt(DateTime.now());
        sub.setCurrentPeriodEndsAt(DateTime.now());
        sub.setTrialStartedAt(DateTime.now());
        sub.setTrialEndsAt(DateTime.now());
        final List<AddOn> addOns = new ArrayList<AddOn>();
        for (int i = 0; i < randomInteger(10); i++) {
            addOns.add(createRandomAddOn());
        }
        sub.setAddOns(addOns);

        //
        return sub;
    }

    /**
     * Creates a random {@link Transaction} object for use in Tests
     *
     * @return The random {@link Transaction} object
     */
    public static Transaction createRandomTransaction() {
        final Transaction trans = new Transaction();
        trans.setAccount(createRandomAccount());
        trans.setAction(getRandomAlphaNumString(5));
        trans.setAmountInCents(getRandomNumString(100));
        trans.setTaxInCents(getRandomNumString(100));
        trans.setCurrency(createRandomCurrency());
        trans.setStatus(getRandomAlphaNumString(2));
        trans.setCreatedAt(DateTime.now());

        return trans;
    }

    /**
     * Creates a random {@link AddOn} for use in Tests.
     *
     * @return The random {@link AddOn}
     */
    public static AddOn createRandomAddOn() {
        final AddOn addOn = new AddOn();
        addOn.setAddOnCode(getRandomAlphaNumString(10));
        addOn.setName(getRandomAlphaNumString(10));
        addOn.setUnitAmountInCents(createRandomPrice());

        return addOn;
    }

    /**
     * Creates a random {@link AddOn} that has only a single price attached to it
     *
     * @param currencyCode The currency code for which the single price should be in. From the set:
     *                     EUR;
     * @return The random {@link AddOn}
     */
    public static AddOn createRandomAddOn(final String currencyCode) {
        final AddOn addOn = createRandomAddOn();
        addOn.setUnitAmountInCents(createRandomSinglePrice(currencyCode));

        return addOn;
    }
}
