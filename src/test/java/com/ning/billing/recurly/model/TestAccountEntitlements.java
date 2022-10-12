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
import java.util.Arrays;

public class TestAccountEntitlements extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String accountEntitlementsData = 
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
          "<entitlements type=\"array\">\n" +
          "  <entitlement>" +
          "    <customer_permission>" +
          "      <id>rjpzkhp9dham</id>" +
          "      <code>perm-plans</code>" +
          "      <name>perm with all plans</name>" +
          "      <description></description>" +
          "    </customer_permission>" +
          "    <granted_by type=\"array\">\n" +
          "      <subscription href=\"https://your-subdomain.recurly.com/v2/subscriptions/qmehc1ucmsjs\"/>" +
          "    </granted_by>" +
          "    <created_at type=\"datetime\">2022-09-12T18:40:51Z</created_at>" +
          "    <updated_at type=\"datetime\">2022-09-12T18:40:51Z</updated_at>" +
          "  </entitlement>" +
          "  <entitlement>" +
          "    <customer_permission>" +
          "      <id>rjpxq1aacqrb</id>" +
          "      <code>per-123</code>" +
          "      <name>client permission</name>" +
          "      <description>description of perm</description>" +
          "    </customer_permission>" +
          "    <granted_by type=\"array\">\n" +
          "      <subscription href=\"https://your-subdomain.recurly.com/v2/subscriptions/qmehc1ucmsjs\"/>" +
          "      <external_subscription href=\"https://your-subdomain.recurly.com/v2/external_subscriptions/qolyu1a4fyi1\"/>" +                                    
          "    </granted_by>" +
          "    <created_at type=\"datetime\">2022-10-12T18:40:51Z</created_at>" +
          "    <updated_at type=\"datetime\">2022-10-12T18:40:51Z</updated_at>" +
          "  </entitlement>" +
          "</entitlements>";

        final Entitlements accountEntitlements = xmlMapper.readValue(accountEntitlementsData, Entitlements.class);
        Assert.assertEquals(accountEntitlements.size(), 2);

        final Entitlement entitlement1 = accountEntitlements.get(0);
        Assert.assertEquals(entitlement1.getCustomerPermission().getId(), "rjpzkhp9dham");
        Assert.assertEquals(entitlement1.getCustomerPermission().getCode(), "perm-plans");
        Assert.assertEquals(entitlement1.getCustomerPermission().getName(), "perm with all plans");
        Assert.assertEquals(entitlement1.getCustomerPermission().getDescription(), null);
        Assert.assertEquals(entitlement1.getCreatedAt(), new DateTime("2022-09-12T18:40:51Z"));
        Assert.assertEquals(entitlement1.getUpdatedAt(), new DateTime("2022-09-12T18:40:51Z"));
        String entitlement1Hrefs[] = new String[] { "https://your-subdomain.recurly.com/v2/subscriptions/qmehc1ucmsjs" };
        Assert.assertEquals(entitlement1.getGrantedBy(), Arrays.asList(entitlement1Hrefs));

        final Entitlement entitlement2 = accountEntitlements.get(1);
        Assert.assertEquals(entitlement2.getCustomerPermission().getId(), "rjpxq1aacqrb");
        Assert.assertEquals(entitlement2.getCustomerPermission().getCode(), "per-123");
        Assert.assertEquals(entitlement2.getCustomerPermission().getName(), "client permission");
        Assert.assertEquals(entitlement2.getCustomerPermission().getDescription(), "description of perm");
        Assert.assertEquals(entitlement2.getCreatedAt(), new DateTime("2022-10-12T18:40:51Z"));
        Assert.assertEquals(entitlement2.getUpdatedAt(), new DateTime("2022-10-12T18:40:51Z"));
        String entitlement2Hrefs[] = new String[] { "https://your-subdomain.recurly.com/v2/subscriptions/qmehc1ucmsjs",
                                                    "https://your-subdomain.recurly.com/v2/external_subscriptions/qolyu1a4fyi1" };
        Assert.assertEquals(entitlement2.getGrantedBy(), Arrays.asList(entitlement2Hrefs));
    }
}
