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

public class TestAccount extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        // See https://dev.recurly.com/docs/list-accounts
        final String accountData = "<account href=\"https://api.recurly.com/v2/accounts/1\">\n" +
                                   "  <adjustments href=\"https://api.recurly.com/v2/accounts/1/adjustments\"/>\n" +
                                   "  <billing_info href=\"https://api.recurly.com/v2/accounts/1/billing_info\"/>\n" +
                                   "  <invoices href=\"https://api.recurly.com/v2/accounts/1/invoices\"/>\n" +
                                   "  <redemption href=\"https://api.recurly.com/v2/accounts/1/redemption\"/>\n" +
                                   "  <subscriptions href=\"https://api.recurly.com/v2/accounts/1/subscriptions\"/>\n" +
                                   "  <transactions href=\"https://api.recurly.com/v2/accounts/1/transactions\"/>\n" +
                                   "  <account_code>1</account_code>\n" +
                                   "  <state>active</state>\n" +
                                   "  <username nil=\"nil\"></username>\n" +
                                   "  <email>verena@example.com</email>\n" +
                                   "  <first_name>Verena</first_name>\n" +
                                   "  <last_name>Example</last_name>\n" +
                                   "  <tax_exempt type=\"boolean\">false</tax_exempt>\n\n" +
                                   "  <accept_language nil=\"nil\"></accept_language>\n" +
                                   "  <hosted_login_token>a92468579e9c4231a6c0031c4716c01d</hosted_login_token>\n" +
                                   "  <created_at type=\"dateTime\">2011-10-25T12:00:00</created_at>\n" +
                                   "  <updated_at type=\"dateTime\">2011-10-25T12:00:00</updated_at>\n" +
                                   "  <has_live_subscription type=\"boolean\">true</has_live_subscription>\n" +
                                   "  <has_active_subscription type=\"boolean\">true</has_active_subscription>\n" +
                                   "  <has_future_subscription type=\"boolean\">false</has_future_subscription>\n" +
                                   "  <has_canceled_subscription type=\"boolean\">false</has_canceled_subscription>\n" +
                                   "  <has_past_due_invoice type=\"boolean\">false</has_past_due_invoice>\n" +
                                   "  <vat_number>U12345678</vat_number>\n" +
                                   "  <address>\n" +
                                   "      <address1>123 Main St.</address1>\n" +
                                   "      <address2 nil=\"nil\"></address2>\n" +
                                   "      <city>San Francisco</city>\n" +
                                   "      <state>CA</state>\n" +
                                   "      <zip>94105-1804</zip>\n" +
                                   "      <country>US</country>\n" +
                                   "      <phone nil=\"nil\"></phone>\n" +
                                   "  </address>\n" +
                                   "  <custom_fields type=\"array\">\n" +
                                   "    <custom_field>\n" +
                                   "      <name>acct_field</name>\n" +
                                   "      <value>some account value</value>\n" +
                                   "    </custom_field>\n" +
                                   "  </custom_fields>\n" +
                                   "</account>";

        final Account account = xmlMapper.readValue(accountData, Account.class);
        Assert.assertEquals(account.getHref(), "https://api.recurly.com/v2/accounts/1");
        verifyAccount(account);

        // Verify serialization
        final String accountSerialized = xmlMapper.writeValueAsString(account);
        final Account account2 = xmlMapper.readValue(accountSerialized, Account.class);
        Assert.assertNull(account2.getHref());
        verifyAccount(account2);
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create accounts of the same value but difference references
        Account account = TestUtils.createRandomAccount(0);
        Account otherAccount = TestUtils.createRandomAccount(0);

        assertNotEquals(System.identityHashCode(account), System.identityHashCode(otherAccount));
        assertEquals(account.hashCode(), otherAccount.hashCode());
        assertEquals(account, otherAccount);
    }

    private void verifyAccount(final Account account) {
        Assert.assertEquals(account.getAccountCode(), "1");
        Assert.assertEquals(account.getState(), "active");
        Assert.assertNull(account.getUsername());
        Assert.assertEquals(account.getEmail(), "verena@example.com");
        Assert.assertEquals(account.getFirstName(), "Verena");
        Assert.assertEquals(account.getLastName(), "Example");
        Assert.assertNull(account.getAcceptLanguage());
        Assert.assertEquals(account.getHostedLoginToken(), "a92468579e9c4231a6c0031c4716c01d");
        Assert.assertEquals(account.getCreatedAt(), new DateTime("2011-10-25T12:00:00"));
        Assert.assertEquals(account.getUpdatedAt(), new DateTime("2011-10-25T12:00:00"));
        Assert.assertEquals(account.getAddress().getAddress1(), "123 Main St.");
        Assert.assertNull(account.getAddress().getAddress2());
        Assert.assertEquals(account.getAddress().getCity(), "San Francisco");
        Assert.assertEquals(account.getAddress().getState(), "CA");
        Assert.assertEquals(account.getAddress().getZip(), "94105-1804");
        Assert.assertEquals(account.getAddress().getCountry(), "US");
        Assert.assertFalse(account.getTaxExempt());
        Assert.assertNull(account.getAddress().getPhone());
        Assert.assertEquals(account.getCustomFields(), getTestFields());
        Assert.assertTrue(account.getHasLiveSubscription());
        Assert.assertTrue(account.getHasActiveSubscription());
        Assert.assertFalse(account.getHasFutureSubscription());
        Assert.assertFalse(account.getHasCanceledSubscription());
        Assert.assertFalse(account.getHasPastDueInvoice());
        Assert.assertEquals(account.getVatNumber(), "U12345678");
    }

    private CustomFields getTestFields() {
        CustomField cf = new CustomField();
        cf.setName("acct_field");
        cf.setValue("some account value");
        CustomFields fields = new CustomFields();
        fields.add(cf);
        return fields;
    }

}
