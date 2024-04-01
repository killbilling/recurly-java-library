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

package com.ning.billing.recurly.model;

import com.ning.billing.recurly.TestUtils;
import org.testng.annotations.Test;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

public class TestPurchaseWithRevRec extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final String purchaseData = "<purchase xmlns=\"\">" +
                "<currency>USD</currency>" +
                "  <collection_method>automatic</collection_method>" +
                "  <net_terms type=\"integer\">30</net_terms>" +
                "  <net_terms_type>eom</net_terms_type>" +
                "  <currency>USD</currency>" +
                "  <customer_notes>Customer Notes</customer_notes>" +
                "  <terms_and_conditions>Terms and Conditions</terms_and_conditions>" +
                "  <vat_reverse_charge_notes>VAT Reverse Charge Notes</vat_reverse_charge_notes>" +
                "  <shipping_address>\n" +
                "      <first_name>Rumple</first_name>\n" +
                "      <last_name>Violet</last_name>\n" +
                "      <address1>43 Baobab Lane</address1>\n" +
                "      <address2 nil=\"nil\"></address2>\n" +
                "      <city>San Francisco</city>\n" +
                "      <state>CA</state>\n" +
                "      <zip>94105-1804</zip>\n" +
                "      <country>US</country>\n" +
                "      <phone nil=\"nil\"></phone>\n" +
                "  </shipping_address>\n" +
                "  <account>" +
                "    <account_code>test</account_code>" +
                "    <billing_info>" +
                "      <first_name>Benjamin</first_name>" +
                "      <last_name>Du Monde</last_name>" +
                "      <address1>400 Alabama St</address1>" +
                "      <city>San Francisco</city>" +
                "      <state>CA</state>" +
                "      <zip>94110</zip>" +
                "      <country>US</country>" +
                "      <year type=\"integer\">2019</year>" +
                "      <month type=\"integer\">12</month>" +
                "      <number>4000-0000-0000-0000</number>" +
                "    </billing_info>" +
                "  </account>" +
                "  <adjustments>" +
                "    <adjustment>" +
                "      <subscription href=\"https://subdomain.recurly.com/v2/subscriptions/48e99881b3726425bf49e64b45af7fe4\"/>" +
                "      <unit_amount_in_cents type=\"integer\">1000</unit_amount_in_cents>" +
                "      <quantity type=\"integer\">1</quantity>" +
                "      <currency>USD</currency>" +
                "      <product_code>product-code</product_code>" +
                "      <liability_gl_account_code>liability-code-1</liability_gl_account_code>" +
                "      <revenue_gl_account_code>revenue-code-3</revenue_gl_account_code>" +
                "      <performance_obligation_id>5</performance_obligation_id>" +
                "    </adjustment>" +
                "  </adjustments>" +
                "  <subscriptions>" +
                "    <subscription>" +
                "       <plan_code>plan1</plan_code>" +
                "    </subscription>" +
                "    <subscription>" +
                "       <plan_code>plan2</plan_code>" +
                "    </subscription>" +
                "  </subscriptions>" +
                "  <coupon_codes>" +
                "    <coupon_code>coupon1</coupon_code>" +
                "    <coupon_code>coupon2</coupon_code>" +
                "  </coupon_codes>" +
                "  <gift_card>" +
                "    <redemption_code>ABC1234</redemption_code>" +
                "  </gift_card>" +
                "</purchase>";

        // test serialization
        final Purchase purchase = xmlMapper.readValue(purchaseData, Purchase.class);
        verifyPurchase(purchase);

        // test deseralization
        final Purchase purchaseExpected = xmlMapper.readValue(purchaseData, Purchase.class);
        assertEquals(purchase, purchaseExpected);
    }

    public void verifyPurchase(final Purchase purchase) {
        assertEquals(purchase.getCollectionMethod(), "automatic");
        assertEquals(purchase.getCurrency(), "USD");
        assertEquals(purchase.getNetTerms(), new Integer(30));
        assertEquals(purchase.getNetTermsType(), "eom");
        assertEquals(purchase.getCustomerNotes(), "Customer Notes");
        assertEquals(purchase.getTermsAndConditions(), "Terms and Conditions");
        assertEquals(purchase.getVatReverseChargeNotes(), "VAT Reverse Charge Notes");

        final Account account = purchase.getAccount();
        assertEquals(account.getAccountCode(), "test");


        final BillingInfo billingInfo = purchase.getAccount().getBillingInfo();
        assertEquals(billingInfo.getAddress1(), "400 Alabama St");
        assertEquals(billingInfo.getCity(), "San Francisco");
        assertEquals(billingInfo.getState(), "CA");
        assertEquals(billingInfo.getCountry(), "US");
        assertEquals(billingInfo.getZip(), "94110");
        assertEquals(billingInfo.getFirstName(), "Benjamin");
        assertEquals(billingInfo.getLastName(), "Du Monde");
        assertEquals(billingInfo.getMonth(), new Integer(12));
        assertEquals(billingInfo.getYear(), new Integer(2019));
        assertEquals(billingInfo.getNumber(), "4000-0000-0000-0000");

        final ShippingAddress shippingAddress = purchase.getShippingAddress();
        assertEquals(shippingAddress.getFirstName(), "Rumple");
        assertEquals(shippingAddress.getLastName(), "Violet");
        assertEquals(shippingAddress.getAddress1(), "43 Baobab Lane");
        assertEquals(shippingAddress.getCity(), "San Francisco");
        assertEquals(shippingAddress.getState(), "CA");
        assertEquals(shippingAddress.getZip(), "94105-1804");
        assertEquals(shippingAddress.getCountry(), "US");

        final Adjustment adjustment = purchase.getAdjustments().get(0);
        assertEquals(adjustment.getCurrency(), "USD");
        assertEquals(adjustment.getProductCode(), "product-code");
        assertEquals(adjustment.getQuantity(), new Integer(1));
        assertEquals(adjustment.getUnitAmountInCents(), new Integer(1000));
        assertEquals(adjustment.getLiabilityGlAccountCode(), "liability-code-1");
        assertEquals(adjustment.getRevenueGlAccountCode(), "revenue-code-3");
        assertEquals(adjustment.getPerformanceObligationId(), "5");
        assertNull(adjustment.getLiabilityGlAccountId());
        assertNull(adjustment.getRevenueGlAccountId());
        assertEquals(adjustment.getSubscriptionId(), "48e99881b3726425bf49e64b45af7fe4");


        final Subscription sub1 = purchase.getSubscriptions().get(0);
        assertEquals(sub1.getPlanCode(), "plan1");
        final Subscription sub2 = purchase.getSubscriptions().get(1);
        assertEquals(sub2.getPlanCode(), "plan2");

        final List<String> couponCodes = purchase.getCouponCodes();
        assertEquals(couponCodes.get(0), "coupon1");
        assertEquals(couponCodes.get(1), "coupon2");

        final GiftCard giftCard = purchase.getGiftCard();
        assertEquals(giftCard.getRedemptionCode(), "ABC1234");
    }

    @Test(groups = "fast")
    public void testSerializationWithBillingInfoUuid() throws Exception {
        final String purchaseData = "<purchase xmlns=\"\">" +
                "<currency>USD</currency>" +
                "  <collection_method>automatic</collection_method>" +
                "  <net_terms type=\"integer\">30</net_terms>" +
                "  <net_terms_type>net</net_terms_type>" +
                "  <currency>USD</currency>" +
                "  <customer_notes>Customer Notes</customer_notes>" +
                "  <terms_and_conditions>Terms and Conditions</terms_and_conditions>" +
                "  <vat_reverse_charge_notes>VAT Reverse Charge Notes</vat_reverse_charge_notes>" +
                "  <shipping_address>\n" +
                "      <first_name>Rumple</first_name>\n" +
                "      <last_name>Violet</last_name>\n" +
                "      <address1>43 Baobab Lane</address1>\n" +
                "      <address2 nil=\"nil\"></address2>\n" +
                "      <city>San Francisco</city>\n" +
                "      <state>CA</state>\n" +
                "      <zip>94105-1804</zip>\n" +
                "      <country>US</country>\n" +
                "      <phone nil=\"nil\"></phone>\n" +
                "  </shipping_address>\n" +
                "  <account>" +
                "    <account_code>test</account_code>" +
                "  </account>" +
                "  <billing_info_uuid>iiznlrvdt8py</billing_info_uuid>" +
                "  <subscriptions>" +
                "    <subscription>" +
                "       <plan_code>plan1</plan_code>" +
                "    </subscription>" +
                "    <subscription>" +
                "       <plan_code>plan2</plan_code>" +
                "    </subscription>" +
                "  </subscriptions>" +
                "  <coupon_codes>" +
                "    <coupon_code>coupon1</coupon_code>" +
                "    <coupon_code>coupon2</coupon_code>" +
                "  </coupon_codes>" +
                "  <gift_card>" +
                "    <redemption_code>ABC1234</redemption_code>" +
                "  </gift_card>" +
                "</purchase>";

        // test serialization
        final Purchase purchase = xmlMapper.readValue(purchaseData, Purchase.class);
        verifyPurchaseWithBillingInfoUuid(purchase);

        // test deseralization
        final Purchase purchaseExpected = xmlMapper.readValue(purchaseData, Purchase.class);
        assertEquals(purchase, purchaseExpected);
    }

    public void verifyPurchaseWithBillingInfoUuid(final Purchase purchase) {
        assertEquals(purchase.getBillingInfoUuid(), "iiznlrvdt8py");
  }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create purchase of the same value but difference references
        final Purchase purchase = TestUtils.createRandomPurchase(0);
        final Purchase otherPurchase = TestUtils.createRandomPurchase(0);

        assertNotEquals(System.identityHashCode(purchase), System.identityHashCode(otherPurchase));
        assertEquals(purchase.hashCode(), otherPurchase.hashCode());
        assertEquals(purchase, otherPurchase);
    }
}
