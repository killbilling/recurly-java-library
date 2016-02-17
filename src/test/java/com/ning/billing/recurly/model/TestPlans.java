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

public class TestPlans extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-plans
        final String plansData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                 "<plans type=\"array\">\n" +
                                 "  <plan href=\"https://api.recurly.com/v2/plans/gold\">\n" +
                                 "    <add_ons href=\"https://api.recurly.com/v2/plans/gold/add_ons\"/>\n" +
                                 "    <plan_code>gold</plan_code>\n" +
                                 "    <name>Gold plan</name>\n" +
                                 "    <description nil=\"nil\"></description>\n" +
                                 "    <success_url nil=\"nil\"></success_url>\n" +
                                 "    <cancel_url nil=\"nil\"></cancel_url>\n" +
                                 "    <display_donation_amounts type=\"boolean\">false</display_donation_amounts>\n" +
                                 "    <display_quantity type=\"boolean\">false</display_quantity>\n" +
                                 "    <display_phone_number type=\"boolean\">false</display_phone_number>\n" +
                                 "    <bypass_hosted_confirmation type=\"boolean\">false</bypass_hosted_confirmation>\n" +
                                 "    <unit_name>unit</unit_name>\n" +
                                 "    <payment_page_tos_link nil=\"nil\"></payment_page_tos_link>\n" +
                                 "    <plan_interval_length type=\"integer\">1</plan_interval_length>\n" +
                                 "    <plan_interval_unit>months</plan_interval_unit>\n" +
                                 "    <trial_interval_length type=\"integer\">0</trial_interval_length>\n" +
                                 "    <trial_interval_unit>days</trial_interval_unit>\n" +
                                 "    <accounting_code nil=\"nil\"></accounting_code>\n" +
                                 "    <created_at type=\"datetime\">2011-04-19T07:00:00Z</created_at>\n" +
                                 "    <unit_amount_in_cents>\n" +
                                 "      <USD type=\"integer\">1000</USD>\n" +
                                 "      <EUR type=\"integer\">800</EUR>\n" +
                                 "    </unit_amount_in_cents>\n" +
                                 "    <setup_fee_in_cents>\n" +
                                 "      <USD type=\"integer\">6000</USD>\n" +
                                 "      <EUR type=\"integer\">4500</EUR>\n" +
                                 "    </setup_fee_in_cents>\n" +
                                 "  </plan>\n" +
                                 "  <!-- Continued... -->\n" +
                                 "</plans>";

        final Plans plans = xmlMapper.readValue(plansData, Plans.class);
        Assert.assertEquals(plans.size(), 1);

        final Plan plan = plans.get(0);
        Assert.assertEquals(plan.getPlanCode(), "gold");
        Assert.assertEquals(plan.getName(), "Gold plan");
        Assert.assertEquals((int) plan.getPlanIntervalLength(), 1);
        Assert.assertEquals(plan.getPlanIntervalUnit(), "months");
        Assert.assertEquals((int) plan.getTrialIntervalLength(), 0);
        Assert.assertEquals(plan.getTrialIntervalUnit(), "days");
        Assert.assertFalse(plan.getDisplayDonationAmounts());
        Assert.assertFalse(plan.getDisplayQuantity());
        Assert.assertEquals(plan.getCreatedAt(), new DateTime("2011-04-19T07:00:00Z"));
        Assert.assertNull(plan.getDescription());
        Assert.assertNull(plan.getSuccessLink());
        Assert.assertNull(plan.getCancelLink());
        Assert.assertNull(plan.getAccountingCode());
    }
}
