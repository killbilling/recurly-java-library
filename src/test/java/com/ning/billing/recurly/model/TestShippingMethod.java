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

import com.ning.billing.recurly.TestUtils;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestShippingMethod extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String shippingMethodData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                "<shipping_method href=\"https://api.recurly.com/v2/shipping_methods/tree_fiddy\">\n" +
                                "  <code>tree_fiddy</code>\n" +
                                "  <name>Lochness Monster</name>\n" +
                                "  <accounting_code>8vn3j9</accounting_code>\n" +
                                "  <tax_code>p3o838oske</tax_code>\n" +
                                "  <liability_gl_account_id>lk73h77s</liability_gl_account_id>\n" +
                                "  <revenue_gl_account_id>oiv983l</revenue_gl_account_id>\n" +
                                "  <performance_obligation_id>vaww4422</performance_obligation_id>\n" +
                                "  <created_at type=\"datetime\">2019-04-19T07:00:00Z</created_at>\n" +
                                "  <updated_at type=\"datetime\">2019-04-19T07:00:00Z</updated_at>\n" +
                                "</shipping_method>";

        final ShippingMethod shippingMethod = xmlMapper.readValue(shippingMethodData, ShippingMethod.class);
        Assert.assertEquals(shippingMethod.getCode(), "tree_fiddy");
        Assert.assertEquals(shippingMethod.getName(), "Lochness Monster");
        Assert.assertEquals(shippingMethod.getAccountingCode(), "8vn3j9");
        Assert.assertEquals(shippingMethod.getTaxCode(), "p3o838oske");
        Assert.assertEquals(shippingMethod.getLiabilityGlAccountId(), "lk73h77s");
        Assert.assertEquals(shippingMethod.getRevenueGlAccountId(), "oiv983l");
        Assert.assertEquals(shippingMethod.getPerformanceObligationId(), "vaww4422");
        Assert.assertEquals(shippingMethod.getCreatedAt(), new DateTime("2019-04-19T07:00:00Z"));
        Assert.assertEquals(shippingMethod.getUpdatedAt(), new DateTime("2019-04-19T07:00:00Z"));
    }
}
