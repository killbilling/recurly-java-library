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

public class TestExternalProducts extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String externalProductsData = 
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
          "<external_products type=\"array\">" +
          "  <external_product href=\"https://your-subdomain.recurly.com/v2/external_products/rg6is8p7w4us\">" +
          "    <plan href=\"https://your-subdomain.recurly.com/v2/plans/platinum\">" +
          "      <plan_code>platinum</plan_code>" +
          "      <name>Platinum plan</name>" +
          "    </plan>" +
          "    <name>External Product Name</name>" +
          "    <created_at type=\"datetime\">2022-08-25T22:00:32Z</created_at>" +
          "    <updated_at type=\"datetime\">2022-10-10T21:40:50Z</updated_at>" +
          "    <external_product_references type=\"array\">" +
          "      <external_product_reference>" +
          "        <id>rgycreiyhs1q</id>" +
          "        <reference_code>apple-code</reference_code>" +
          "        <external_connection_type>apple_app_store</external_connection_type>" +
          "        <created_at type=\"datetime\">2022-08-29T19:36:39Z</created_at>" +
          "        <updated_at type=\"datetime\">2022-10-10T21:40:50Z</updated_at>" +
          "      </external_product_reference>" +
          "    </external_product_references>" +
          "  </external_product>" +
          "</external_products>";

        final ExternalProducts externalProducts = xmlMapper.readValue(externalProductsData, ExternalProducts.class);
        Assert.assertEquals(externalProducts.size(), 1);

        final ExternalProduct externalProduct = externalProducts.get(0);
        Assert.assertEquals(externalProduct.getHref(), "https://your-subdomain.recurly.com/v2/external_products/rg6is8p7w4us");
        Assert.assertEquals(externalProduct.getPlan().getPlanCode(), "platinum");
        Assert.assertEquals(externalProduct.getPlan().getName(), "Platinum plan");
        Assert.assertEquals(externalProduct.getName(), "External Product Name");
        Assert.assertEquals(externalProduct.getCreatedAt(), new DateTime("2022-08-25T22:00:32Z"));
        Assert.assertEquals(externalProduct.getUpdatedAt(), new DateTime("2022-10-10T21:40:50Z"));

        final ExternalProductReferences externalProductReferences = externalProduct.getExternalProductReferences();
        Assert.assertEquals(externalProductReferences.size(), 1);

        final ExternalProductReference externalProductReference = externalProductReferences.get(0);
        Assert.assertEquals(externalProductReference.getId(), "rgycreiyhs1q");
        Assert.assertEquals(externalProductReference.getReferenceCode(), "apple-code");
        Assert.assertEquals(externalProductReference.getExternalConnectionType(), "apple_app_store");
        Assert.assertEquals(externalProductReference.getCreatedAt(), new DateTime("2022-08-29T19:36:39Z"));
        Assert.assertEquals(externalProductReference.getUpdatedAt(), new DateTime("2022-10-10T21:40:50Z"));
    }
}
