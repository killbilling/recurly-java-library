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
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestPurchase extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final String purchaseData = "<purchase xmlns=\"\">" +
                "<currency>USD</currency>" +
                "  <collection_method>automatic</collection_method>" +
                "  <net_terms>30</net_terms>" +
                "  <currency>USD</currency>" +
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
                "      <year>2019</year>" +
                "      <month>12</month>" +
                "      <number>4000-0000-0000-0000</number>" +
                "    </billing_info>" +
                "  </account>" +
                "  <adjustments>" +
                "    <adjustment>" +
                "      <unit_amount_in_cents>1000</unit_amount_in_cents>" +
                "      <quantity>1</quantity>" +
                "      <currency>USD</currency>" +
                "      <product_code>product-code</product_code>" +
                "    </adjustment>" +
                "  </adjustments>" +
                "</purchase>";

        final Purchase purchase = new Purchase();
        purchase.setCollectionMethod("automatic");
        purchase.setCurrency("USD");
        purchase.setNetTerms(30);

        final Account account = new Account();
        account.setAccountCode("test");

        final BillingInfo billingInfo = new BillingInfo();
        billingInfo.setAddress1("400 Alabama St");
        billingInfo.setCity("San Francisco");
        billingInfo.setCountry("US");
        billingInfo.setFirstName("Benjamin");
        billingInfo.setLastName("Du Monde");
        billingInfo.setMonth(12);
        billingInfo.setNumber("4000-0000-0000-0000");
        billingInfo.setState("CA");
        billingInfo.setYear(2019);
        billingInfo.setZip("94110");
        account.setBillingInfo(billingInfo);

        final Adjustments adjustments = new Adjustments();
        final Adjustment adjustment = new Adjustment();
        adjustment.setCurrency("USD");
        adjustment.setProductCode("product-code");
        adjustment.setQuantity(1);
        adjustment.setUnitAmountInCents(1000);
        adjustments.add(adjustment);

        purchase.setAccount(account);
        purchase.setAdjustments(adjustments);

        final String xml = xmlMapper.writeValueAsString(purchase);
        final Purchase purchaseExpected = xmlMapper.readValue(xml, Purchase.class);

        assertEquals(purchase, purchaseExpected);
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
