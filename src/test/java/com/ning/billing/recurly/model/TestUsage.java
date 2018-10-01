/*
 * Copyright 2010-2013 Ning, Inc.
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

import com.ning.billing.recurly.TestUtils;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestUsage extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/log-usage
        final String transactionData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n <usage href=\"https://your-subdomain.recurly.com/v2/subscriptions/3aaf63884971dbc5414bc845678a3593/add_ons/CD_USAGE_DATA_USAGE/usage/570702996069942457\">\n" +
                "  <measured_unit href=\"https://your-subdomain.recurly.com/v2/measured_units/556493119596529478\"/>\n" +
                "  <id type=\"integer\">570702996069942457</id>\n" +
                "  <amount type=\"integer\">10</amount>\n" +
                "  <merchant_tag>test_merchant</merchant_tag>\n" +
                "  <recording_timestamp type=\"dateTime\">2016-12-27T14:04:01Z</recording_timestamp>\n" +
                "  <usage_timestamp type=\"dateTime\">2016-12-27T14:04:01Z</usage_timestamp>\n" +
                "  <created_at type=\"dateTime\">2016-12-27T14:04:58Z</created_at>\n" +
                "  <updated_at type=\"dateTime\">2016-12-27T14:04:58Z</updated_at>\n" +
                "  <billed_at nil=\"nil\"></billed_at>\n" +
                "  <usage_type>price</usage_type>\n" +
                "  <unit_amount_in_cents type=\"integer\">150</unit_amount_in_cents>\n" +
                "  <usage_percentage type=\"float\">100</usage_percentage>\n" +
                "</usage>" ;

        final Usage usage = xmlMapper.readValue(transactionData, Usage.class);
        Assert.assertEquals(usage.getHref(), "https://your-subdomain.recurly.com/v2/subscriptions/3aaf63884971dbc5414bc845678a3593/add_ons/CD_USAGE_DATA_USAGE/usage/570702996069942457");
        Assert.assertEquals(usage.getAmount(), Integer.valueOf(10));
        Assert.assertEquals(usage.getMerchantTag(), "test_merchant");
        Assert.assertEquals(usage.getRecordingAt(), new DateTime("2016-12-27T14:04:01Z"));
        Assert.assertEquals(usage.getUsageAt(), new DateTime("2016-12-27T14:04:01Z"));
        Assert.assertEquals(usage.getCreatedAt(), new DateTime("2016-12-27T14:04:58Z"));
        Assert.assertEquals(usage.getUpdatedAt(), new DateTime("2016-12-27T14:04:58Z"));
        Assert.assertEquals(usage.getUsageType(), "price");
        Assert.assertEquals(usage.getUnitAmountInCents(), Integer.valueOf(150));
        Assert.assertEquals(usage.getUsagePercentage(), BigDecimal.valueOf(100));
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create Usage of the same value but difference references
        Usage usage = TestUtils.createRandomUsage(0);
        Usage otherUsage = TestUtils.createRandomUsage(0);

        assertNotEquals(System.identityHashCode(usage), System.identityHashCode(otherUsage));
        assertEquals(usage.hashCode(), otherUsage.hashCode());
        assertEquals(usage, otherUsage);
    }
}
