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

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAccountBalance extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final String accountBalanceData = "<account_balance href=\"https://api.recurly.com/v2/accounts/1/balance\">\n" +
                "<account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                "<past_due type=\"boolean\">true</past_due>\n" +
                "<balance_in_cents>\n" +
                    "<USD type=\"integer\">400</USD>\n" +
                "</balance_in_cents>\n" +
                "</account_balance>\n";

        final AccountBalance balance = xmlMapper.readValue(accountBalanceData, AccountBalance.class);

        Assert.assertEquals(balance.getHref(), "https://api.recurly.com/v2/accounts/1/balance");
        Assert.assertEquals(balance.getPastDue(), Boolean.TRUE);
        Assert.assertEquals(balance.getBalanceInCents().getUnitAmountUSD(), Integer.valueOf(400));
    }
}
