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

 import java.util.List;

 import org.joda.time.DateTime;
 import org.testng.Assert;
 import org.testng.annotations.Test;

 public class TestGeneralLedgerAccounts extends TestModelBase {

     @Test(groups = "fast")
     public void testDeserialization() throws Exception {
        final String generalLedgerAccountsData =
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
           "<general_ledger_accounts type=\"array\">" +
           "  <general_ledger_account href=\"https://your-subdomain.recurly.com/v2/general_ledger_account/scaig66ovogw\">" +
           "    <id>scaig66ovogw</id>" +
           "    <code>rev1989</code>" +
           "    <account_type>revenue</account_type>" +
           "    <description>general description</description>" +
           "    <created_at type=\"datetime\">2023-05-04T17:45:43Z</created_at>" +
           "    <updated_at type=\"datetime\">2023-05-04T17:45:43Z</updated_at>" +
           "  </general_ledger_account>" +
           "</general_ledger_accounts>";

        final GeneralLedgerAccounts generalLedgerAccounts = xmlMapper.readValue(generalLedgerAccountsData, GeneralLedgerAccounts.class);
        Assert.assertEquals(generalLedgerAccounts.size(), 1);

        final GeneralLedgerAccount generalLedgerAccount = generalLedgerAccounts.get(0);

        Assert.assertEquals(generalLedgerAccount.getHref(), "https://your-subdomain.recurly.com/v2/general_ledger_account/scaig66ovogw");
        Assert.assertEquals(generalLedgerAccount.getId(), "scaig66ovogw");
        Assert.assertEquals(generalLedgerAccount.getCode(), "rev1989");
        Assert.assertEquals(generalLedgerAccount.getAccountType(), "revenue");
        Assert.assertEquals(generalLedgerAccount.getDescription(), "general description");
        Assert.assertEquals(generalLedgerAccount.getCreatedAt(), new DateTime("2023-05-04T17:45:43Z"));
        Assert.assertEquals(generalLedgerAccount.getUpdatedAt(), new DateTime("2023-05-04T17:45:43Z"));
     }
 }
