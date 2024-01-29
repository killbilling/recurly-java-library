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
 import static org.testng.Assert.assertTrue;
 import org.testng.annotations.Test;

 public class TestGeneralLedgerAccount extends TestModelBase {

     @Test(groups = "fast")
     public void testDeserialization() throws Exception {
        final String generalLedgerAccountData =
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
           "<general_ledger_account href=\"https://your-subdomain.recurly.com/v2/general_ledger_account/scaig66ovogw\">" +
           "  <id>scaig66ovogw</id>" +
           "  <code>liability2817</code>" +
           "  <account_type>liability</account_type>" +
           "  <description>general description</description>" +
           "  <created_at type=\"datetime\">2023-05-04T17:45:43Z</created_at>" +
           "  <updated_at type=\"datetime\">2023-05-04T17:45:43Z</updated_at>" +
           "</general_ledger_account>";

        final GeneralLedgerAccount generalLedgerAccount = xmlMapper.readValue(generalLedgerAccountData, GeneralLedgerAccount.class);

        Assert.assertEquals(generalLedgerAccount.getHref(), "https://your-subdomain.recurly.com/v2/general_ledger_account/scaig66ovogw");
        Assert.assertEquals(generalLedgerAccount.getId(), "scaig66ovogw");
        Assert.assertEquals(generalLedgerAccount.getCode(), "liability2817");
        Assert.assertEquals(generalLedgerAccount.getAccountType(), "liability");
        Assert.assertEquals(generalLedgerAccount.getDescription(), "general description");
        Assert.assertEquals(generalLedgerAccount.getCreatedAt(), new DateTime("2023-05-04T17:45:43Z"));
        Assert.assertEquals(generalLedgerAccount.getUpdatedAt(), new DateTime("2023-05-04T17:45:43Z"));
     }

    @Test(groups = "fast")
    public void testSerializationWithAccountType() throws Exception {
        final GeneralLedgerAccount gla = new GeneralLedgerAccount();
        gla.setAccountType("revenue");
        final String xmlString = xmlMapper.writeValueAsString(gla);
        assertTrue(xmlString.contains("<account_type>revenue</account_type>"));
    }

    @Test(groups = "fast")
    public void testSerializationWithCode() throws Exception {
        final GeneralLedgerAccount gla = new GeneralLedgerAccount();
        gla.setCode("1234Code");
        final String xmlString = xmlMapper.writeValueAsString(gla);
        assertTrue(xmlString.contains("<code>1234Code</code>"));
    }

    @Test(groups = "fast")
    public void testSerializationWithDescription() throws Exception {
        final GeneralLedgerAccount gla = new GeneralLedgerAccount();
        gla.setDescription("This is a cool GLA");
        final String xmlString = xmlMapper.writeValueAsString(gla);
        assertTrue(xmlString.contains("<description>This is a cool GLA</description>"));
    }
 }
