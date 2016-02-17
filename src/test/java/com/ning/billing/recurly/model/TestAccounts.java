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

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAccounts extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-accounts
        final String accountsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                    "<accounts type=\"array\">\n" +
                                    "  <account href=\"https://api.recurly.com/v2/accounts/1\">\n" +
                                    "    <adjustments href=\"https://api.recurly.com/v2/accounts/1/adjustments\"/>\n" +
                                    "    <billing_info href=\"https://api.recurly.com/v2/accounts/1/billing_info\"/>\n" +
                                    "    <invoices href=\"https://api.recurly.com/v2/accounts/1/invoices\"/>\n" +
                                    "    <redemption href=\"https://api.recurly.com/v2/accounts/1/redemption\"/>\n" +
                                    "    <subscriptions href=\"https://api.recurly.com/v2/accounts/1/subscriptions\"/>\n" +
                                    "    <transactions href=\"https://api.recurly.com/v2/accounts/1/transactions\"/>\n" +
                                    "    <account_code>1</account_code>\n" +
                                    "    <state>active</state>\n" +
                                    "    <username nil=\"nil\"></username>\n" +
                                    "    <email>verena@example.com</email>\n" +
                                    "    <first_name>Verena</first_name>\n" +
                                    "    <last_name>Example</last_name>\n" +
                                    "    <accept_language nil=\"nil\"></accept_language>\n" +
                                    "    <hosted_login_token>a92468579e9c4231a6c0031c4716c01d</hosted_login_token>\n" +
                                    "    <created_at type=\"datetime\">2011-10-25T12:00:00</created_at>\n" +
                                    "  </account>\n" +
                                    "  <!-- Continued... -->\n" +
                                    "</accounts>";

        final Accounts accounts = xmlMapper.readValue(accountsData, Accounts.class);
        Assert.assertEquals(accounts.size(), 1);

        final Account account = accounts.get(0);
        Assert.assertEquals(account.getHref(), "https://api.recurly.com/v2/accounts/1");
        Assert.assertEquals(account.getAccountCode(), "1");
        Assert.assertEquals(account.getState(), "active");
        Assert.assertNull(account.getUsername());
        Assert.assertEquals(account.getEmail(), "verena@example.com");
        Assert.assertEquals(account.getFirstName(), "Verena");
        Assert.assertEquals(account.getLastName(), "Example");
        Assert.assertNull(account.getAcceptLanguage());
        Assert.assertEquals(account.getHostedLoginToken(), "a92468579e9c4231a6c0031c4716c01d");
        Assert.assertEquals(account.getCreatedAt(), new DateTime("2011-10-25T12:00:00"));
    }
}
