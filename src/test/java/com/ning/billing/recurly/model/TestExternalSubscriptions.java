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

public class TestExternalSubscriptions extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String externalSubscriptionsData = 
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
          "<external_subscriptions>" +
          "  <external_subscription href=\"https://your-subdomain.recurly.com/v2/external_subscriptions/rpap82ntgqqh\">" +
          "    <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>" +
          "    <external_product_reference>" +
          "      <id>rgybkg3d1l41</id>" +
          "      <reference_code>apple-code</reference_code>" +
          "      <external_connection_type>apple_app_store</external_connection_type>" +
          "      <created_at type=\"datetime\">2022-09-12T18:40:51Z</created_at>" +
          "      <updated_at type=\"datetime\">2022-09-12T18:40:51Z</updated_at>" +
          "    </external_product_reference>" +
          "    <last_purchased type=\"datetime\">2022-09-12T18:40:51Z</last_purchased>" +
          "    <auto_renew type=\"boolean\">false</auto_renew>" +
          "    <app_identifier>com.foo.id</app_identifier>" +
          "    <quantity type=\"integer\">1</quantity>" +
          "    <external_id>1_ext_id</external_id>" +
          "    <activated_at type=\"datetime\">2022-09-12T18:40:51Z</activated_at>" +
          "    <expires_at type=\"datetime\">2022-09-12T18:40:51Z</expires_at>" +
          "    <state>active</state>" +
          "    <created_at type=\"datetime\">2022-09-12T18:40:51Z</created_at>" +
          "    <updated_at type=\"datetime\">2022-09-12T18:40:51Z</updated_at>" +
          "  </external_subscription>" +
          "</external_subscriptions>";

        final ExternalSubscriptions externalSubscriptions = xmlMapper.readValue(externalSubscriptionsData, ExternalSubscriptions.class);
        Assert.assertEquals(externalSubscriptions.size(), 1);

        final ExternalSubscription externalSubscription = externalSubscriptions.get(0);
        Assert.assertEquals(externalSubscription.getHref(), "https://your-subdomain.recurly.com/v2/external_subscriptions/rpap82ntgqqh");
        Assert.assertEquals(externalSubscription.getAccount().getHref(), "https://your-subdomain.recurly.com/v2/accounts/1");
        Assert.assertEquals(externalSubscription.getExternalProductReference().getId(), "rgybkg3d1l41");
        Assert.assertEquals(externalSubscription.getExternalProductReference().getReferenceCode(), "apple-code");
        Assert.assertEquals(externalSubscription.getExternalProductReference().getExternalConnectionType(), "apple_app_store");
        Assert.assertEquals(externalSubscription.getExternalProductReference().getCreatedAt(), new DateTime("2022-09-12T18:40:51Z"));
        Assert.assertEquals(externalSubscription.getExternalProductReference().getUpdatedAt(), new DateTime("2022-09-12T18:40:51Z"));
        Assert.assertEquals(externalSubscription.getLastPurchased(), new DateTime("2022-09-12T18:40:51Z"));
        Assert.assertEquals(externalSubscription.getAutoRenew(), Boolean.FALSE);
        Assert.assertEquals(externalSubscription.getAppIdentifier(), "com.foo.id");
        Assert.assertEquals(externalSubscription.getQuantity(), new Integer(1));
        Assert.assertEquals(externalSubscription.getExternalId(), "1_ext_id");
        Assert.assertEquals(externalSubscription.getActivatedAt(), new DateTime("2022-09-12T18:40:51Z"));
        Assert.assertEquals(externalSubscription.getExpiresAt(), new DateTime("2022-09-12T18:40:51Z"));
        Assert.assertEquals(externalSubscription.getState(), "active");
        Assert.assertEquals(externalSubscription.getCreatedAt(), new DateTime("2022-09-12T18:40:51Z"));
        Assert.assertEquals(externalSubscription.getUpdatedAt(), new DateTime("2022-09-12T18:40:51Z"));
    }
}
