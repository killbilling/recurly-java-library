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
import com.ning.billing.recurly.model.AccountAcquisition;
import com.ning.billing.recurly.model.AcquisitionChannel;
import com.ning.billing.recurly.model.AddOn;
import com.ning.billing.recurly.model.Address;
import com.ning.billing.recurly.model.Adjustment;
import com.ning.billing.recurly.model.Adjustments;
import com.ning.billing.recurly.model.BillingInfo;
import com.ning.billing.recurly.model.Coupon;
import com.ning.billing.recurly.model.CustomField;
import com.ning.billing.recurly.model.CustomFields;
import com.ning.billing.recurly.model.Delivery;
import com.ning.billing.recurly.model.GiftCard;
import com.ning.billing.recurly.model.Invoice;
import com.ning.billing.recurly.model.Item;
import com.ning.billing.recurly.model.MeasuredUnit;
import com.ning.billing.recurly.model.Plan;
import com.ning.billing.recurly.model.Purchase;
import com.ning.billing.recurly.model.RecurlyUnitCurrency;
import com.ning.billing.recurly.model.Redemption;
import com.ning.billing.recurly.model.ShippingAddress;
import com.ning.billing.recurly.model.Subscription;
import com.ning.billing.recurly.model.SubscriptionAddOn;
import com.ning.billing.recurly.model.SubscriptionAddOns;
import com.ning.billing.recurly.model.Transaction;
import com.ning.billing.recurly.model.Transactions;
import com.ning.billing.recurly.model.Usage;
import org.joda.time.DateTime;

import java.util.Random;

public class TestUtils {

    private static enum StringMode {
        ALPHA,
        ALPHA_NUMERIC,
        NUMERIC,
    };

    private static final char[] SYMBOLS;

    // Build an array of symbols
    static {
        int idx = 0;
        SYMBOLS = new char[62];

        for (char ch = 'A'; ch <= 'Z'; ++ch) SYMBOLS[idx++] = ch;
        for (char ch = 'a'; ch <= 'z'; ++ch) SYMBOLS[idx++] = ch;
        for (char ch = '0'; ch <= '9'; ++ch) SYMBOLS[idx++] = ch;
    }

    private static final DateTime NOW = new DateTime();
    private static final DateTime TOMORROW = new DateTime(NOW.toDate().getTime() + (1000 * 60 * 60 * 24));

    /**
     * Returns a random {@link String}.
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @param lowerIdx The low index to use on the SYMBOLS table
     * @param upperIdx The upper index to use on the SYMBOLS table
     * @param seed The RNG seed
     * @return The random {@link String}
     */
    public static String randomString(final int length, final int lowerIdx, final int upperIdx, final int seed) {
        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder();
        int range = upperIdx - lowerIdx;

        for (int i = 0; i < length; i++)
            sb.append(SYMBOLS[lowerIdx + random.nextInt(range)]);

        return sb.toString();
    }

    /**
     * Returns a random {@link String} but deterministic
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @param mode
     * @param seed The RNG seed
     * @return The random {@link String}
     */
    public static String randomString(final int length, final StringMode mode, final int seed) {
        switch (mode) {
            case ALPHA:
                return randomString(length, 0, 52, seed);
            case ALPHA_NUMERIC:
                return randomString(length, 0, 62, seed);
            case NUMERIC:
                return randomString(length, 52, 62, seed);
            default:
                return null;
        }
    }

    /**
     * Returns a random {@link String}.
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @return The random {@link String}
     */
    public static String randomString(final int length, final StringMode mode) {
        return randomString(length, mode, randomSeed());
    }

    /**
     * Returns a random alpha {@link String}.
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @return The random {@link String}
     */
    public static String randomAlphaString(final int length) {
        return randomAlphaString(length, randomSeed());
    }

    /**
     * Returns a random alpha {@link String} given a seed
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @param seed The RNG seed
     * @return The random {@link String}
     */
    public static String randomAlphaString(final int length, final int seed) {
        return randomString(length, StringMode.ALPHA, seed);
    }

    /**
     * Returns a random alpha-numeric {@link String} given a seed.
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @param seed The RNG seed
     * @return The random {@link String}
     */
    public static String randomAlphaNumericString(final int length, final int seed) {
        return randomString(length, StringMode.ALPHA_NUMERIC, seed);
    }

    /**
     * Returns a random alpha-numeric {@link String}.
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @return The random {@link String}
     */
    public static String randomAlphaNumericString(final int length) {
        return randomAlphaNumericString(length, randomSeed());
    }

    /**
     * Returns a random numeric {@link String}.
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @return The random {@link String}
     */
    public static String randomNumericString(final int length) {
        return randomNumericString(length, randomSeed());
    }

    /**
     * Returns a random numeric {@link String} given a seed
     *
     * @param length The limit of the upperRange - the random returned value could be upto and including this value
     * @param seed The RNG seed
     * @return The random {@link String}
     */
    public static String randomNumericString(final int length, final int seed) {
        return randomString(length, StringMode.NUMERIC, seed);
    }

    /**
     * Returns a random alpha-numeric {@link String} with a random length b/w 1 and 30.
     *
     * @return The random {@link String}
     */
    public static String randomString() {
        return randomString(1 + randomInteger(30), StringMode.NUMERIC, randomSeed());
    }

    /**
     * Returns a a big random {@link Integer} to be used as a seed
     *
     * @return The random {@link Integer}
     */
    public static int randomSeed() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }

    /**
     * Returns a random {@link Integer} within the range 0 to upperRange
     *
     * @param upperRange The limit of the upperRange - the random returned value could be upto and including this value
     * @return The random integer - from within the range 0 to upperRange
     */
    public static Integer randomInteger(final int upperRange) {
        return randomInteger(upperRange, randomSeed());
    }

    /**
     * Returns a random {@link Integer} within the range 0 to upperRange given a seed
     *
     * @param upperRange The limit of the upperRange - the random returned value could be upto and including this value
     * @param seed The RNG seed
     * @return The random integer - from within the range 0 to upperRange
     */
    public static Integer randomInteger(final int upperRange, final int seed) {
        Random random = new Random(seed);
        return random.nextInt(upperRange);
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
        return "2020";
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Account} object for testing use.
     *
     * @return The random {@link com.ning.billing.recurly.model.Account} object
     */
    public static Account createRandomAccount() {
        return createRandomAccount(randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Account} object for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.Account} object
     */
    public static Account createRandomAccount(final int seed) {
        final Account account = new Account();

        account.setAcceptLanguage("en-US");
        account.setPreferredLocale("en-US");
        account.setAccountCode(randomAlphaNumericString(10, seed));
        account.setCompanyName(randomAlphaNumericString(10, seed));
        account.setEmail(randomAlphaNumericString(4, seed) + "@test.com");
        account.setFirstName(randomAlphaNumericString(5, seed));
        account.setLastName(randomAlphaNumericString(6, seed));
        account.setAddress(createRandomAddress(seed));
        account.setVatNumber(randomAlphaNumericString(15, seed));

        return account;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Address} object for testing use
     *
     * @return The random {@link com.ning.billing.recurly.model.Address} object
     */
    public static Address createRandomAddress() {
        return createRandomAddress(randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Address} object for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.Address} object
     */
    public static Address createRandomAddress(final int seed) {
        final Address address = new Address();

        address.setFirstName(randomAlphaNumericString(10, seed));
        address.setLastName(randomAlphaNumericString(10, seed));
        address.setCompany(randomAlphaNumericString(10, seed));
        address.setAddress1(randomAlphaNumericString(10, seed));
        address.setAddress2(randomAlphaNumericString(10, seed));
        address.setCity(randomAlphaNumericString(10, seed));
        address.setState(randomAlphaString(2, seed).toUpperCase());
        address.setZip("94110");
        address.setCountry("US");
        address.setPhone(randomNumericString(10, seed));

        return address;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.ShippingAddress} object for testing use
     *
     * @return The random {@link com.ning.billing.recurly.model.ShippingAddress} object
     */
    public static ShippingAddress createRandomShippingAddress() {
        return createRandomShippingAddress(randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.ShippingAddress} object for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.ShippingAddress} object
     */
    public static ShippingAddress createRandomShippingAddress(final int seed) {
        final ShippingAddress address = new ShippingAddress();

        address.setAddress1(randomAlphaNumericString(10, seed));
        address.setAddress2(randomAlphaNumericString(10, seed));
        address.setCity(randomAlphaNumericString(10, seed));
        address.setState(randomAlphaString(2, seed).toUpperCase());
        address.setState(randomAlphaString(2, seed).toUpperCase());
        address.setZip("94110");
        address.setCountry("US");
        address.setPhone(randomNumericString(10, seed));
        address.setNickname(randomAlphaNumericString(10, seed));
        address.setFirstName(randomAlphaNumericString(10, seed));
        address.setLastName(randomAlphaNumericString(10, seed));
        address.setCompany(randomAlphaNumericString(10, seed));
        address.setEmail(randomAlphaNumericString(10, seed) + "@email.com");

        return address;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.CustomField} object for testing use
     *
     * @return The random {@link com.ning.billing.recurly.model.CustomField} object
     */
    public static CustomField createRandomCustomField(String name) {
        return createRandomCustomField(name, randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.CustomField} object for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.CustomField} object
     */
    public static CustomField createRandomCustomField(String name, final int seed) {
        final CustomField field = new CustomField();

        field.setName(name);
        field.setValue(randomAlphaNumericString(50, seed));

        return field;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Adjustment} object for testing use
     *
     * @return The random {@link com.ning.billing.recurly.model.Adjustment} object
     */
    public static Adjustment createRandomAdjustment() {
        return createRandomAdjustment(randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Adjustment} object for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.Adjustment} object
     */
    public static Adjustment createRandomAdjustment(final int seed) {
        final Adjustment adjustment = new Adjustment();

        adjustment.setDescription(randomAlphaNumericString(50, seed));
        adjustment.setAccountingCode(randomAlphaNumericString(10, seed));
        adjustment.setUnitAmountInCents(randomInteger(1000, seed));
        adjustment.setQuantity(1 + randomInteger(10, seed));
        adjustment.setCurrency(randomCurrency(seed));
        adjustment.setStartDate(NOW);
        adjustment.setStartDate(TOMORROW);

        return adjustment;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.BillingInfo} object for testing use.
     *
     * @return The random {@link com.ning.billing.recurly.model.BillingInfo} object
     */
    public static BillingInfo createRandomBillingInfo() {
        return createRandomBillingInfo(randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.BillingInfo} object for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.BillingInfo} object
     */
    public static BillingInfo createRandomBillingInfo(final int seed) {
        final BillingInfo info = new BillingInfo();

        info.setAccount(createRandomAccount(seed));
        info.setFirstName(randomAlphaNumericString(5, seed));
        info.setLastName(randomAlphaNumericString(6, seed));
        info.setCompany(randomAlphaNumericString(10, seed));
        info.setAddress1(randomAlphaNumericString(10, seed));
        info.setAddress2(randomAlphaNumericString(10, seed));
        info.setCity(randomAlphaNumericString(10, seed));
        info.setState("CA");
        info.setZip("94110");
        info.setCountry("US");
        info.setPhone(randomInteger(8, seed));
        info.setVatNumber(randomNumericString(8, seed));
        info.setYear(createTestCCYear());
        info.setMonth(createTestCCMonth());
        info.setNumber(createTestCCNumber());
        info.setVerificationValue(createTestCCVerificationNumber());
        return info;
    }

    public static BillingInfo createRandomIbanBillingInfo() {
        return createRandomIbanBillingInfo(randomSeed());
    }

    public static BillingInfo createRandomIbanBillingInfo(final int seed) {
        final BillingInfo info = new BillingInfo();
        info.setIban("FR1420041010050500013M02606");
        info.setNameOnAccount(randomAlphaNumericString(10, seed));
        return info;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Item} object for testing use.
     *
     * @return The random {@link com.ning.billing.recurly.model.Item} object
     */
    public static Item createRandomItem() {
        return createRandomItem(randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Item} object for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.Item} object
     */
    public static Item createRandomItem(final int seed) {
        final Item item = new Item();
        item.setItemCode(randomAlphaNumericString(15).toLowerCase());
        item.setName("A New Item");
        item.setDescription("A random description");
        return item;
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Plan} object for testing use.
     *
     * @return The random {@link com.ning.billing.recurly.model.Plan} object
     */
    public static Plan createRandomPlan() {
        return createRandomPlan(randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Plan} object for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.Plan} object
     */
    public static Plan createRandomPlan(final int seed) {
        final Plan plan = new Plan();

        plan.setPlanCode(randomAlphaNumericString(10, seed));
        plan.setName(randomAlphaNumericString(10, seed));
        plan.setPlanIntervalLength(randomInteger(50, seed) + 1);
        plan.setPlanIntervalUnit("months");
        plan.setSetupFeeInCents(createRandomPrice());
        plan.setUnitAmountInCents(createRandomPrice());
        plan.setDisplayDonationAmounts(true);
        plan.setDisplayPhoneNumber(true);
        plan.setDisplayQuantity(true);
        plan.setBypassHostedConfirmation(true);

        return plan;
    }


    /**
     * Creates a random {@link com.ning.billing.recurly.model.Plan} object with trial period for testing use.
     *
     * @return The random {@link com.ning.billing.recurly.model.Plan} object
     */

    public static Plan createRandomTrialPlan() {
        return createRandomTrialPlan(randomSeed());
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Plan} object with trial period for testing use given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link com.ning.billing.recurly.model.Plan} object
     */
    public static Plan createRandomTrialPlan(final int seed) {
        final Plan plan = new Plan();

        plan.setPlanCode(randomAlphaNumericString(10, seed));
        plan.setName(randomAlphaNumericString(10, seed));
        plan.setTrialIntervalLength(randomInteger(50, seed) + 1);
        plan.setTrialIntervalUnit("months");
        plan.setSetupFeeInCents(createRandomPrice());
        plan.setUnitAmountInCents(createRandomPrice());
        plan.setDisplayDonationAmounts(true);
        plan.setDisplayPhoneNumber(true);
        plan.setDisplayQuantity(true);
        plan.setBypassHostedConfirmation(true);

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
     * Creates a random {@link RecurlyUnitCurrency} object for testing use.
     *
     * @return The random {@link RecurlyUnitCurrency} object
     */
    public static RecurlyUnitCurrency createRandomPrice() {
        final RecurlyUnitCurrency price = new RecurlyUnitCurrency();
        /*
        price.setUnitAmountEUR(LifecycleTest.randomInteger(10));
        price.setUnitAmountGBP(LifecycleTest.randomInteger(10));
        price.setUnitAmountUSD(LifecycleTest.randomInteger(10));
        price.setUnitAmountSEK(LifecycleTest.randomInteger(10));
        */
        price.setUnitAmountUSD(10);

        return price;
    }

    /**
     * Creates a random {@link RecurlyUnitCurrency} object for testing use. The Price
     * object
     *
     * @param currencyCode The currency for which we should return a random Price
     * @return The random {@link RecurlyUnitCurrency} object
     */
    public static RecurlyUnitCurrency createRandomSinglePrice(final String currencyCode) {
        final RecurlyUnitCurrency price = new RecurlyUnitCurrency();

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
    public static String randomCurrency() {
        return randomCurrency(randomSeed());
    }

    /**
     * Creates a random currency {@link String} given a seed from the set:
     * <ul>
     * <li>EUR</li>
     * <li>GBP</li>
     * <li>USD</li>
     * <li>SEK</li>
     * </ul>
     *
     * @param seed The RNG seed
     * @return The {@link String} code for the randomly chosen currency
     */
    public static String randomCurrency(final int seed) {
        final String[] currencies = {"EUR", "GBP", "USD", "SEK"};
        return currencies[randomInteger(currencies.length, seed)];
    }

    /**
     * Creates a random {@link com.ning.billing.recurly.model.Subscription} object for use in tests given a seed
     *
     * @param seed The RNG seed
     * @return The {@link com.ning.billing.recurly.model.Subscription} object
     */
    public static Subscription createRandomSubscription(final int seed) {
        final Subscription sub = new Subscription();
        final Plan plan = createRandomPlan(seed);

        sub.setQuantity(randomInteger(10, seed) + 1);
        sub.setCurrency(randomCurrency(seed));
        sub.setPlanCode(plan.getPlanCode());
        sub.setAccount(createRandomAccount(seed));
        sub.setUnitAmountInCents(randomInteger(10, seed));
        sub.setCurrency(randomCurrency(seed));

        final SubscriptionAddOns addOns = new SubscriptionAddOns();

        for (int i = 0; i < 5; i++) {
            addOns.add(createRandomSubscriptionAddOn("code"+i));
        }

        sub.setAddOns(addOns);

        return sub;
    }


    /**
     * Creates a random {@link com.ning.billing.recurly.model.Subscription} object for use in tests
     *
     * @param currencyCode The currency code for which the subscription will be charged in
     * @param plan         The associated plan
     * @param account      The associated account
     * @param planAddOns   AddOns for that subscription
     * @return The {@link com.ning.billing.recurly.model.Subscription} object
     */
    public static Subscription createRandomSubscription(final String currencyCode, final Plan plan, final Account account, final Iterable<AddOn> planAddOns) {
        final Subscription sub = new Subscription();

        // Make sure the quantity is > 0
        sub.setQuantity(randomInteger(10) + 1);
        sub.setCurrency(randomCurrency());
        sub.setPlanCode(plan.getPlanCode());
        sub.setAccount(account);
        sub.setUnitAmountInCents(randomInteger(10));
        sub.setCurrency(currencyCode);
        final SubscriptionAddOns addOns = new SubscriptionAddOns();
        for (final AddOn addOn : planAddOns) {
            addOns.add(createRandomSubscriptionAddOn(addOn.getAddOnCode()));
        }
        sub.setAddOns(addOns);

        return sub;
    }

    /**
     * Creates a random {@link Transaction} object for use in Tests
     *
     * @return The random {@link Transaction} object
     */
    public static Transaction createRandomTransaction() {
        return createRandomTransaction(randomSeed());
    }

    /**
     * Creates a random {@link Transaction} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link Transaction} object
     */
    public static Transaction createRandomTransaction(final int seed) {
        final Transaction trans = new Transaction();

        trans.setAccount(createRandomAccount(seed));
        trans.setAction(randomAlphaNumericString(5, seed));
        trans.setAmountInCents(randomInteger(1000, seed));
        trans.setTaxInCents(randomInteger(10, seed));
        trans.setDescription(randomAlphaNumericString(50, seed));
        trans.setCurrency(randomCurrency(seed));
        trans.setStatus(randomAlphaNumericString(2, seed));
        trans.setCreatedAt(NOW);

        return trans;
    }

    /**
     * Creates a random {@link AddOn} for use in Tests.
     *
     * @return The random {@link AddOn}
     */
    public static AddOn createRandomAddOn() {
        final AddOn addOn = new AddOn();

        addOn.setAddOnCode(randomAlphaNumericString(10));
        addOn.setName(randomAlphaNumericString(10));
        addOn.setUnitAmountInCents(createRandomPrice());
        addOn.setDefaultQuantity(5);
        addOn.setDisplayQuantityOnHostedPage(false);

        return addOn;
    }

    /**
     * Creates a random {@link AddOn} for use in Tests given a seed.
     *
     * @param seed The RNG seed
     * @return The random {@link AddOn}
     */
    public static AddOn createRandomAddOn(final int seed) {
        final AddOn addOn = new AddOn();

        addOn.setAddOnCode(randomAlphaNumericString(10, seed));
        addOn.setName(randomAlphaNumericString(10, seed));
        addOn.setUnitAmountInCents(createRandomPrice());
        addOn.setDefaultQuantity(5);
        addOn.setDisplayQuantityOnHostedPage(false);

        return addOn;
    }

    /**
     * Creates a random {@link SubscriptionAddOn} for use in Tests.
     *
     * @param addOnCode AddOn code
     * @return The random {@link SubscriptionAddOn}
     */
    public static SubscriptionAddOn createRandomSubscriptionAddOn(final String addOnCode) {
        final SubscriptionAddOn addOn = new SubscriptionAddOn();

        addOn.setAddOnCode(addOnCode);
        addOn.setUnitAmountInCents(42);
        addOn.setQuantity(5);

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

    /**
     * Creates a random {@link Coupon} object for use in Tests
     *
     * @return The random {@link Coupon} object
     */
    public static Coupon createRandomCoupon() {
        return createRandomCoupon(randomSeed());
    }

    /**
     * Creates a random {@link Coupon} object for use in Tests
     *
     * @param seed The RNG seed
     * @return The random {@link Coupon} object
     */
    public static Coupon createRandomCoupon(int seed) {
        final Coupon coupon = new Coupon();

        coupon.setName(randomAlphaNumericString(10, seed));
        coupon.setCouponCode(randomAlphaNumericString(10, seed).toLowerCase());
        coupon.setDiscountType("percent");
        coupon.setDiscountPercent(randomInteger(90, seed) + 1);

        return coupon;
    }

    /**
     * Creates a random {@link Invoice} object for use in Tests
     *
     * @return The random {@link Invoice} object
     */
    public static Invoice createRandomInvoice() {
        return createRandomInvoice(randomSeed());
    }

    /**
     * Creates a random {@link Invoice} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link Invoice} object
     */
    public static Invoice createRandomInvoice(final int seed) {
        final Invoice invoice = new Invoice();

        invoice.setAccount(createRandomAccount(seed));
        invoice.setUuid(randomAlphaNumericString(20, seed));
        invoice.setState("open");
        invoice.setInvoiceNumber(randomInteger(10000, seed));
        invoice.setPoNumber(randomNumericString(5, seed));
        invoice.setVatNumber(randomAlphaNumericString(5, seed));
        invoice.setSubtotalInCents(randomInteger(1000, seed));
        invoice.setTaxInCents(randomInteger(1000, seed));
        invoice.setTotalInCents(randomInteger(1000, seed));
        invoice.setCurrency(randomCurrency(seed));
        invoice.setCreatedAt(NOW);
        invoice.setCollectionMethod("credit_card");
        invoice.setNetTerms(randomInteger(100, seed));
        invoice.setCustomerNotes("Customer Notes " + randomAlphaString(20, seed));
        invoice.setTermsAndConditions("Terms and Conditions " + randomAlphaString(20, seed));
        invoice.setVatReverseChargeNotes("VAT Reverse Charge Notes " + randomAlphaString(20, seed));

        Adjustments adjustments = new Adjustments();
        for (int i = 0; i < 3; i++) {
            adjustments.add(createRandomAdjustment(seed + i));
        }
        invoice.setLineItems(adjustments);

        Transactions transactions = new Transactions();
        for (int i = 0; i < 3; i++) {
            transactions.add(createRandomTransaction(seed + i));
        }
        invoice.setTransactions(transactions);


        return invoice;
    }

    /**
     * Creates a random {@link Redemption} object for use in Tests
     *
     * @return The random {@link Redemption} object
     */
    public static Redemption createRandomRedemption() {
        return createRandomRedemption(randomSeed());
    }

    /**
     * Creates a random {@link Redemption} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link Redemption} object
     */
    public static Redemption createRandomRedemption(final int seed) {
        final Redemption redemption = new Redemption();
        final Account account = createRandomAccount(seed);

        redemption.setAccount(account);
        redemption.setAccountCode(account.getAccountCode());
        redemption.setSubscriptionUuid(randomAlphaNumericString(10, seed));
        redemption.setCoupon(createRandomCoupon(seed));
        redemption.setSingleUse(true);
        redemption.setState("redeemed");
        redemption.setCurrency(randomCurrency(seed));
        redemption.setTotalDiscountedInCents(randomInteger(1000, seed));

        return redemption;
    }

    /**
     * Creates a random {@link Delivery} object for use in Tests
     *
     * @return The random {@link Delivery} object
     */
    public static Delivery createRandomDelivery() {
        return createRandomDelivery(randomSeed());
    }

    /**
     * Creates a random {@link Delivery} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link Delivery} object
     */
    public static Delivery createRandomDelivery(final int seed) {
        Delivery delivery = new Delivery();

        delivery.setAddress(createRandomAddress(seed));
        delivery.setEmailAddress(randomAlphaNumericString(10, seed) + "@email.com");
        delivery.setFirstName(randomAlphaNumericString(5, seed));
        delivery.setLastName(randomAlphaNumericString(5, seed));
        delivery.setGifterName(randomAlphaNumericString(5, seed));
        delivery.setMethod("email");
        delivery.setPersonalMessage(randomAlphaNumericString(100, seed));
        if (seed == 0) { // we want it to be deterministic
            delivery.setDeliverAt(new DateTime("2020-01-01T00:00:00Z"));
        } else {
            delivery.setDeliverAt(new DateTime().plusDays(5)); // needs to be at least 1 hour in future
        }

        return delivery;
    }

    /**
     * Creates a random {@link GiftCard} object for use in Tests
     *
     * @return The random {@link GiftCard} object
     */
    public static GiftCard createRandomGiftCard() {
        return createRandomGiftCard(randomSeed());
    }

    /**
     * Creates a random {@link GiftCard} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link GiftCard} object
     */
    public static GiftCard createRandomGiftCard(final int seed) {
        final GiftCard giftCardData = new GiftCard();
        final Account accountData = createRandomAccount(seed);
        final Delivery deliveryData = createRandomDelivery(seed);
        final BillingInfo billingInfoData = createRandomBillingInfo(seed);

        // need to null out account
        // TODO - account should not be writeable in XML
        billingInfoData.setAccount(null);
        accountData.setBillingInfo(billingInfoData);

        // Gift card product needs to be created on the site with
        // product code "test_gift_card"

        giftCardData.setGifterAccount(accountData);
        giftCardData.setDelivery(deliveryData);
        giftCardData.setCurrency("USD");
        giftCardData.setProductCode("test_gift_card");
        giftCardData.setUnitAmountInCents(2000);

        return giftCardData;
    }

    /**
     * Creates a random {@link Usage} object for use in Tests
     *
     * @return The random {@link Usage} object
     */
    public static Usage createRandomUsage() {
        return createRandomUsage(randomSeed());
    }

    /**
     * Creates a random {@link Usage} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link Usage} object
     */
    public static Usage createRandomUsage(final int seed) {
        final Usage usage = new Usage();

        usage.setAmount(randomInteger(1000, seed));
        usage.setMerchantTag("merchant");
        usage.setUsagePercentage(randomInteger(100, seed));
        usage.setUsageType("price");
        usage.setUnitAmountInCents(randomInteger(1000, seed));
        usage.setUsageAt(NOW);

        return usage;
    }

    /**
     * Creates a random {@link MeasuredUnit} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link MeasuredUnit} object
     */
    public static MeasuredUnit createRandomMeasuredUnit(final int seed) {
        final MeasuredUnit measuredUnit = new MeasuredUnit();

        measuredUnit.setName(randomAlphaNumericString(10, seed));
        measuredUnit.setDisplayName(randomAlphaNumericString(10, seed));
        measuredUnit.setDescription(randomAlphaNumericString(50, seed));

        return measuredUnit;
    }

    /**
     * Creates a random {@link Purchase} object for use in Tests
     *
     * @return The random {@link Purchase} object
     */
    public static Purchase createRandomPurchase() {
        return createRandomPurchase(randomSeed());
    }

    /**
     * Creates a random {@link Purchase} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link Purchase} object
     */
    public static Purchase createRandomPurchase(final int seed) {
        final Purchase purchase = new Purchase();

        purchase.setAccount(createRandomAccount(seed));

        purchase.setShippingAddress(createRandomShippingAddress(seed));

        Adjustments adjustments = new Adjustments();
        adjustments.add(createRandomAdjustment(seed));
        purchase.setAdjustments(adjustments);

        purchase.setCurrency("USD");
        purchase.setCollectionMethod("automatic");
        purchase.setPoNumber("PO12345");
        purchase.setNetTerms(30);

        return purchase;
    }

    /**
     * Creates a random {@link AccountAcquisition} object for use in Tests
     *
     * @return The random {@link AccountAcquisition} object
     */
    public static AccountAcquisition createRandomAccountAcquisition() {
        return createRandomAccountAcquisition(randomSeed());
    }

    /**
     * Creates a random {@link AccountAcquisition} object for use in Tests given a seed
     *
     * @param seed The RNG seed
     * @return The random {@link AccountAcquisition} object
     */
    public static AccountAcquisition createRandomAccountAcquisition(final int seed) {
        final AccountAcquisition acquisition = new AccountAcquisition();

        acquisition.setCurrency("USD");
        acquisition.setCampaign("mailchimp." + randomAlphaNumericString(10, seed));
        acquisition.setChannel(AcquisitionChannel.MARKETING_CONTENT);
        acquisition.setCostInCents(randomInteger(1000, seed));
        acquisition.setSubchannel(randomAlphaNumericString(50, seed));

        return acquisition;
    }
}
