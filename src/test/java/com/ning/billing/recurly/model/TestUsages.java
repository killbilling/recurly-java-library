/*
 * Copyright 2018 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
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

public class TestUsages extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-add-ons-usage
        final String transactionData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<usages type=\"array\">\n" +
                "  <usage href=\"https://your-subdomain.recurly.com/v2/subscriptions/374ae5e848adcfd332fdd3469d89c888/add_ons/video_streaming/usage/450646065398417338\">\n" +
                "    <measured_unit href=\"https://your-subdomain.recurly.com/v2/measured_units/450622522661012652\"/>\n" +
                "    <amount type=\"integer\">1</amount>\n" +
                "    <merchant_tag>Order ID: 4939853977878713</merchant_tag>\n" +
                "    <recording_timestamp type=\"datetime\">2016-07-14T13:09:15+00:00</recording_timestamp>\n" +
                "    <usage_timestamp type=\"datetime\">2016-07-14T22:30:15+00:00</usage_timestamp>\n" +
                "    <created_at type=\"datetime\">2016-07-14T22:33:17+00:00</created_at>\n" +
                "    <updated_at nil=\"nil\"/>\n" +
                "    <billed_at nil=\"nil\"/>\n" +
                "    <usage_type>price</usage_type>\n" +
                "    <unit_amount_in_cents type=\"integer\">45</unit_amount_in_cents>\n" +
                "    <usage_percentage nil=\"nil\"/>\n" +
                "  </usage>\n" +
                "  <!-- Continued... -->\n" +
                "</usages>" ;

        final Usages usages = xmlMapper.readValue(transactionData, Usages.class);
        final Usage usage = usages.get(0);
        Assert.assertEquals(usage.getHref(), "https://your-subdomain.recurly.com/v2/subscriptions/374ae5e848adcfd332fdd3469d89c888/add_ons/video_streaming/usage/450646065398417338");
        Assert.assertEquals(usage.getAmount(), Integer.valueOf(1));
        Assert.assertEquals(usage.getMerchantTag(), "Order ID: 4939853977878713");
        Assert.assertEquals(usage.getRecordingAt(), new DateTime("2016-07-14T13:09:15Z"));
        Assert.assertEquals(usage.getUsageAt(), new DateTime("2016-07-14T22:30:15Z"));
        Assert.assertEquals(usage.getCreatedAt(), new DateTime("2016-07-14T22:33:17Z"));
        Assert.assertNull(usage.getUpdatedAt());
        Assert.assertEquals(usage.getUsageType(), "price");
        Assert.assertEquals(usage.getUnitAmountInCents(), Integer.valueOf(45));
        Assert.assertNull(usage.getUsagePercentage());
    }
}
