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

 import java.util.List;

 import org.joda.time.DateTime;
 import org.testng.Assert;
 import org.testng.annotations.Test;
 
 public class TestBusinessEntity extends TestModelBase {
 
     @Test(groups = "fast")
     public void testDeserialization() throws Exception {
        final String businessEntityData = 
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
           "<business_entity href=\"https://your-subdomain.recurly.com/v2/business_entities/scaig66ovogw\">" +
           "  <invoices href=\"https://your-subdomain.recurly.com/v2/business_entities/scaig66ovogw/invoices\"/>" +
           "  <id>scaig66ovogw</id>" +
           "  <code>legacy_usa</code>" +
           "  <name>Legacy USA</name>" +
           "  <invoice_display_address>" +
           "     <address1>94250 Sadye Ramp</address1>" +
           "     <address2>Apt. 771</address2>" +
           "     <city>Oakland</city>" +
           "     <state>CA</state>" +
           "     <zip>94605</zip>" +
           "     <country>US</country>" +
           "     <phone>718-555-1234</phone>" +
           "  </invoice_display_address>" +
           "  <tax_address>" +
           "     <address1>94250 Sadye Ramp</address1>" +
           "     <address2>Apt. 771</address2>" +
           "     <city>Oakland</city>" +
           "     <state>CA</state>" +
           "     <zip>94605</zip>" +
           "     <country>US</country>" +
           "     <phone>718-555-1234</phone>" +
           "  </tax_address>" +
           "  <subscriber_location_countries type=\"array\">" +
           "     <subscriber_location_country>GB</subscriber_location_country>" +
           "     <subscriber_location_country>CO</subscriber_location_country>" +
           "  </subscriber_location_countries>" +
           "  <default_vat_number>1234</default_vat_number>" +
           "  <default_registration_number>5678</default_registration_number>" +
           "  <created_at type=\"datetime\">2023-05-04T17:45:43Z</created_at>" +
           "  <updated_at type=\"datetime\">2023-05-04T17:45:43Z</updated_at>" +
           "</business_entity>";
 
        final BusinessEntity businessEntity = xmlMapper.readValue(businessEntityData, BusinessEntity.class);
        final List<String> subscriberLocationCountries = businessEntity.getSubscriberLocationCountries();

        Assert.assertEquals(businessEntity.getHref(), "https://your-subdomain.recurly.com/v2/business_entities/scaig66ovogw");
        Assert.assertEquals(businessEntity.getInvoices().getHref(), "https://your-subdomain.recurly.com/v2/business_entities/scaig66ovogw/invoices");
        Assert.assertEquals(businessEntity.getId(), "scaig66ovogw");
        Assert.assertEquals(businessEntity.getCode(), "legacy_usa");
        Assert.assertEquals(businessEntity.getName(), "Legacy USA");
        Assert.assertEquals(businessEntity.getInvoiceDisplayAddress().getAddress1(), "94250 Sadye Ramp");
        Assert.assertEquals(businessEntity.getInvoiceDisplayAddress().getAddress2(), "Apt. 771");
        Assert.assertEquals(businessEntity.getInvoiceDisplayAddress().getCity(), "Oakland");
        Assert.assertEquals(businessEntity.getInvoiceDisplayAddress().getState(), "CA");
        Assert.assertEquals(businessEntity.getInvoiceDisplayAddress().getZip(), "94605");
        Assert.assertEquals(businessEntity.getInvoiceDisplayAddress().getCountry(), "US");
        Assert.assertEquals(businessEntity.getInvoiceDisplayAddress().getPhone(), "718-555-1234");
        Assert.assertEquals(businessEntity.getTaxAddress().getAddress1(), "94250 Sadye Ramp");
        Assert.assertEquals(businessEntity.getTaxAddress().getAddress2(), "Apt. 771");
        Assert.assertEquals(businessEntity.getTaxAddress().getCity(), "Oakland");
        Assert.assertEquals(businessEntity.getTaxAddress().getState(), "CA");
        Assert.assertEquals(businessEntity.getTaxAddress().getZip(), "94605");
        Assert.assertEquals(businessEntity.getTaxAddress().getCountry(), "US");
        Assert.assertEquals(businessEntity.getTaxAddress().getPhone(), "718-555-1234");
        Assert.assertEquals(subscriberLocationCountries.get(0), "GB");
        Assert.assertEquals(subscriberLocationCountries.get(1), "CO");
        Assert.assertEquals(businessEntity.getDefaultVatNumber(), "1234");
        Assert.assertEquals(businessEntity.getDefaultRegistrationNumber(), "5678");
        Assert.assertEquals(businessEntity.getCreatedAt(), new DateTime("2023-05-04T17:45:43Z"));
        Assert.assertEquals(businessEntity.getUpdatedAt(), new DateTime("2023-05-04T17:45:43Z"));
     }
 }
 