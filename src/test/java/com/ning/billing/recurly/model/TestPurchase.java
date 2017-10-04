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
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestPurchase extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final String purchaseData = "<purchase xmlns=\"\">" +
                "<currency>USD</currency>" +
                "  <collection_method>automatic</collection_method>" +
                "  <net_terms type=\"integer\">30</net_terms>" +
                "  <currency>USD</currency>" +
                "  <customer_notes>Customer Notes</customer_notes>" +
                "  <terms_and_conditions>Terms and Conditions</terms_and_conditions>" +
                "  <vat_reverse_charge_notes>VAT Reverse Charge Notes</vat_reverse_charge_notes>" +
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
                "      <unit_amount_in_cents type=\"integer\">1000</unit_amount_in_cents>" +
                "      <quantity type=\"integer\">1</quantity>" +
                "      <currency>USD</currency>" +
                "      <product_code>product-code</product_code>" +
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

        final Adjustment adjustment = purchase.getAdjustments().get(0);
        assertEquals(adjustment.getCurrency(), "USD");
        assertEquals(adjustment.getProductCode(), "product-code");
        assertEquals(adjustment.getQuantity(), new Integer(1));
        assertEquals(adjustment.getUnitAmountInCents(), new Integer(1000));


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
    public void testHashCodeAndEquality() throws Exception {
        // create purchase of the same value but difference references
        final Purchase purchase = TestUtils.createRandomPurchase(0);
        final Purchase otherPurchase = TestUtils.createRandomPurchase(0);

        assertNotEquals(System.identityHashCode(purchase), System.identityHashCode(otherPurchase));
        assertEquals(purchase.hashCode(), otherPurchase.hashCode());
        assertEquals(purchase, otherPurchase);
    }
}
