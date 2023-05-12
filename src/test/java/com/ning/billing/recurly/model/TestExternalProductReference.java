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
 
 public class TestExternalProductReference extends TestModelBase {
 
     @Test(groups = "fast")
     public void testDeserialization() throws Exception {
         final String externalProductReferenceData = 
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
           "<external_product_reference href=\"https://your-subdomain.recurly.com/v2/external_products/rg6is8p7w4us/external_product_references/swifdua3cvtp\">" +
           "  <id>swifdua3cvtp</id>" +
           "  <reference_code>apple-code</reference_code>" +
           "  <external_connection_type>apple_app_store</external_connection_type>" +
           "  <created_at type=\"datetime\">2022-08-29T19:36:39Z</created_at>" +
           "  <updated_at type=\"datetime\">2022-10-10T21:40:50Z</updated_at>" +
           "</external_product_reference>";
 
         final ExternalProductReference externalProductReference = xmlMapper.readValue(externalProductReferenceData, ExternalProductReference.class);
         Assert.assertEquals(externalProductReference.getHref(), "https://your-subdomain.recurly.com/v2/external_products/rg6is8p7w4us/external_product_references/swifdua3cvtp");
         Assert.assertEquals(externalProductReference.getId(), "swifdua3cvtp");
         Assert.assertEquals(externalProductReference.getReferenceCode(), "apple-code");
         Assert.assertEquals(externalProductReference.getExternalConnectionType(), "apple_app_store");
         Assert.assertEquals(externalProductReference.getCreatedAt(), new DateTime("2022-08-29T19:36:39Z"));
         Assert.assertEquals(externalProductReference.getUpdatedAt(), new DateTime("2022-10-10T21:40:50Z")); 
     }
 }
 