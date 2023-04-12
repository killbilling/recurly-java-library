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

public class TestExternalAccounts extends TestModelBase {

  @Test(groups = "fast")
  public void testDeserialization() throws Exception {
    final String externalAccountsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<external_accounts type=\"array\">" +
        "  <external_account href=\"https://your-subdomain.recurly.com/v2/external_accounts/scaig66ovogw\">" +
        "    <id>smygx37luhzx</id>" +
        "    <external_account_code>737894a1-aef2-eb15-c820-1dd81d9803f8</external_account_code>" +
        "    <external_connection_type>AppleAppStore</external_connection_type>" +
        "    <created_at type=\"datetime\">2023-04-03T15:41:19Z</created_at>" +
        "    <updated_at type=\"datetime\">2023-04-05T21:03:06Z</updated_at>" +
        "  </external_account>" +
        "</external_accounts>";

    final ExternalAccounts externalAccounts = xmlMapper.readValue(externalAccountsData, ExternalAccounts.class);
    Assert.assertEquals(externalAccounts.size(), 1);

    final ExternalAccount externalAccount = externalAccounts.get(0);

    Assert.assertEquals(externalAccount.getHref(),
        "https://your-subdomain.recurly.com/v2/external_accounts/scaig66ovogw");
    Assert.assertEquals(externalAccount.getId(), "smygx37luhzx");
    Assert.assertEquals(externalAccount.getExternalAccountCode(), "737894a1-aef2-eb15-c820-1dd81d9803f8");
    Assert.assertEquals(externalAccount.getExternalConnectionType(), "AppleAppStore");
    Assert.assertEquals(externalAccount.getCreatedAt(), new DateTime("2023-04-03T15:41:19Z"));
    Assert.assertEquals(externalAccount.getUpdatedAt(), new DateTime("2023-04-05T21:03:06Z"));
  }
}
