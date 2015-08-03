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

public class TestAdjustements extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-an-accounts-adjustments
        final String adjustmentsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                       "<adjustments type=\"array\">\n" +
                                       "  <adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                       "    <account href=\"https://api.recurly.com/v2/accounts/100\"/>\n" +
                                       "    <uuid>626db120a84102b1809909071c701c60</uuid>\n" +
                                       "    <description>Refund for being a great customer</description>\n" +
                                       "    <accounting_code nil=\"nil\"></accounting_code>\n" +
                                       "    <origin>credit</origin>\n" +
                                       "    <unit_amount_in_cents type=\"integer\">-2000</unit_amount_in_cents>\n" +
                                       "    <quantity type=\"integer\">1</quantity>\n" +
                                       "    <discount_in_cents type=\"integer\">0</discount_in_cents>\n" +
                                       "    <tax_in_cents type=\"integer\">0</tax_in_cents>\n" +
                                       "    <total_in_cents type=\"integer\">-2000</total_in_cents>\n" +
                                       "    <currency>USD</currency>\n" +
                                       "    <taxable type=\"boolean\">false</taxable>\n" +
                                       "    <start_date type=\"datetime\">2011-08-31T03:30:00Z</start_date>\n" +
                                       "    <end_date nil=\"nil\"></end_date>\n" +
                                       "    <created_at type=\"datetime\">2011-08-31T03:30:00Z</created_at>\n" +
                                       "  </adjustment>\n" +
                                       "  <!-- Continued... -->\n" +
                                       "</adjustments>";

        final Adjustments adjustments = xmlMapper.readValue(adjustmentsData, Adjustments.class);
        Assert.assertEquals(adjustments.size(), 1);

        final Adjustment adjustment = adjustments.get(0);

        Assert.assertEquals(adjustment.getAccount().getHref(), "https://api.recurly.com/v2/accounts/100");
        Assert.assertEquals(adjustment.getUuid(), "626db120a84102b1809909071c701c60");
        Assert.assertEquals(adjustment.getDescription(), "Refund for being a great customer");
        Assert.assertNull(adjustment.getAccountingCode());
        Assert.assertEquals(adjustment.getOrigin(), "credit");
        Assert.assertEquals((int) adjustment.getUnitAmountInCents(), -2000);
        Assert.assertEquals((int) adjustment.getQuantity(), 1);
        Assert.assertEquals((int) adjustment.getDiscountInCents(), 0);
        Assert.assertEquals((int) adjustment.getTotalInCents(), -2000);
        Assert.assertEquals(adjustment.getCurrency(), "USD");
        Assert.assertEquals((boolean) adjustment.getTaxable(), false);
        Assert.assertEquals(adjustment.getStartDate(), new DateTime("2011-08-31T03:30:00Z"));
        Assert.assertNull(adjustment.getEndDate());
        Assert.assertEquals(adjustment.getCreatedAt(), new DateTime("2011-08-31T03:30:00Z"));
    }
}
