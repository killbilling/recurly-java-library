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

public class TestShippingMethods extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String shippingMethodsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                 "<shippind_methods type=\"array\">\n" +
                                    "<shipping_method href=\"https://api.recurly.com/v2/shipping_methods/tree_fiddy\">\n" +
                                    "  <code>tree_fiddy</code>\n" +
                                    "  <name>Lochness Monster</name>\n" +
                                    "  <accounting_code>8vn3j9</accounting_code>\n" +
                                    "  <tax_code>p3o838oske</tax_code>\n" +
                                    "  <liability_gl_account_id>lk73h77s</liability_gl_account_id>\n" +
                                    "  <revenue_gl_account_id>oiv983l</revenue_gl_account_id>\n" +
                                    "  <performance_obligation_id>5</performance_obligation_id>\n" +
                                    "  <created_at type=\"datetime\">2019-04-19T07:00:00Z</created_at>\n" +
                                    "  <updated_at type=\"datetime\">2019-04-19T07:00:00Z</updated_at>\n" +
                                    "</shipping_method>" +
                                    "<shipping_method href=\"https://api.recurly.com/v2/shipping_methods/chevrolet_movie_theater\">\n" +
                                    "  <code>chevrolet_movie_theater</code>\n" +
                                    "  <name>Interior</name>\n" +
                                    "  <accounting_code>crocodile</accounting_code>\n" +
                                    "  <tax_code>alligator</tax_code>\n" +
                                    "  <liability_gl_account_id>av3af224</liability_gl_account_id>\n" +
                                    "  <revenue_gl_account_id>j8sd89aa</revenue_gl_account_id>\n" +
                                    "  <performance_obligation_id>6</performance_obligation_id>\n" +
                                    "  <created_at type=\"datetime\">2019-04-19T07:00:00Z</created_at>\n" +
                                    "  <updated_at type=\"datetime\">2019-04-19T07:00:00Z</updated_at>\n" +
                                    "</shipping_method>" +
                                 "</shippind_methods>";

        final ShippingMethods shippingMethods = xmlMapper.readValue(shippingMethodsData, ShippingMethods.class);
        Assert.assertEquals(shippingMethods.size(), 2);

        final ShippingMethod shippingMethod1 = shippingMethods.get(0);
        Assert.assertEquals(shippingMethod1.getCode(), "tree_fiddy");
        Assert.assertEquals(shippingMethod1.getName(), "Lochness Monster");
        Assert.assertEquals(shippingMethod1.getAccountingCode(), "8vn3j9");
        Assert.assertEquals(shippingMethod1.getTaxCode(), "p3o838oske");
        Assert.assertEquals(shippingMethod1.getLiabilityGlAccountId(), "lk73h77s");
        Assert.assertEquals(shippingMethod1.getRevenueGlAccountId(), "oiv983l");
        Assert.assertEquals(shippingMethod1.getPerformanceObligationId(), "5");
        Assert.assertEquals(shippingMethod1.getCreatedAt(), new DateTime("2019-04-19T07:00:00Z"));
        Assert.assertEquals(shippingMethod1.getUpdatedAt(), new DateTime("2019-04-19T07:00:00Z"));

        final ShippingMethod shippingMethod2 = shippingMethods.get(1);
        Assert.assertEquals(shippingMethod2.getCode(), "chevrolet_movie_theater");
        Assert.assertEquals(shippingMethod2.getName(), "Interior");
        Assert.assertEquals(shippingMethod2.getAccountingCode(), "crocodile");
        Assert.assertEquals(shippingMethod2.getTaxCode(), "alligator");
        Assert.assertEquals(shippingMethod2.getLiabilityGlAccountId(), "av3af224");
        Assert.assertEquals(shippingMethod2.getRevenueGlAccountId(), "j8sd89aa");
        Assert.assertEquals(shippingMethod2.getPerformanceObligationId(), "6");
        Assert.assertEquals(shippingMethod2.getCreatedAt(), new DateTime("2019-04-19T07:00:00Z"));
        Assert.assertEquals(shippingMethod2.getUpdatedAt(), new DateTime("2019-04-19T07:00:00Z"));
    }
}
