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
import org.joda.time.Interval;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class TestAdjustment extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        // See https://dev.recurly.com/docs/list-an-accounts-adjustments
        final String adjustmentData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                      "  <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                                      "  <uuid>626db120a84102b1809909071c701c60</uuid>\n" +
                                      "  <description>Charge for extra bandwidth</description>\n" +
                                      "  <accounting_code>bandwidth</accounting_code>\n" +
                                      "  <origin>charge</origin>\n" +
                                      "  <unit_amount_in_cents type=\"integer\">5000</unit_amount_in_cents>\n" +
                                      "  <quantity type=\"integer\">1</quantity>\n" +
                                      "  <discount_in_cents type=\"integer\">0</discount_in_cents>\n" +
                                      "  <tax_in_cents type=\"integer\">0</tax_in_cents>\n" +
                                      "  <total_in_cents type=\"integer\">5000</total_in_cents>\n" +
                                      "  <currency>USD</currency>\n" +
                                      "  <taxable type=\"boolean\">false</taxable>\n" +
                                      "  <start_date type=\"datetime\">2011-08-31T03:30:00Z</start_date>\n" +
                                      "  <end_date nil=\"nil\"></end_date>\n" +
                                      "  <created_at type=\"datetime\">2011-08-31T03:30:00Z</created_at>\n" +
                                      "</adjustment>";

        final Adjustment adjustment = xmlMapper.readValue(adjustmentData, Adjustment.class);

        Assert.assertEquals(adjustment.getAccount().getHref(), "https://api.recurly.com/v2/accounts/1");
        Assert.assertEquals(adjustment.getUuid(), "626db120a84102b1809909071c701c60");
        Assert.assertEquals(adjustment.getDescription(), "Charge for extra bandwidth");
        Assert.assertEquals(adjustment.getAccountingCode(), "bandwidth");
        Assert.assertEquals(adjustment.getOrigin(), "charge");
        Assert.assertEquals((int) adjustment.getUnitAmountInCents(), 5000);
        Assert.assertEquals((int) adjustment.getQuantity(), 1);
        Assert.assertEquals((int) adjustment.getDiscountInCents(), 0);
        Assert.assertEquals((int) adjustment.getTotalInCents(), 5000);
        Assert.assertEquals(adjustment.getCurrency(), "USD");
        Assert.assertEquals((boolean) adjustment.getTaxable(), false);
        Assert.assertEquals(adjustment.getStartDate(), new DateTime("2011-08-31T03:30:00Z"));
        Assert.assertNull(adjustment.getEndDate());
        Assert.assertEquals(adjustment.getCreatedAt(), new DateTime("2011-08-31T03:30:00Z"));

        // Test Serialization
        final String xml = xmlMapper.writeValueAsString(adjustment);
        final Adjustment readValue = xmlMapper.readValue(xml, Adjustment.class);

        Assert.assertEquals(readValue.getUuid(), adjustment.getUuid());
        Assert.assertEquals(readValue.getDescription(), adjustment.getDescription());
        Assert.assertEquals(readValue.getAccountingCode(), adjustment.getAccountingCode());
        Assert.assertEquals(readValue.getOrigin(), adjustment.getOrigin());
        Assert.assertEquals((int) readValue.getUnitAmountInCents(), (int) adjustment.getUnitAmountInCents());
        Assert.assertEquals((int) readValue.getQuantity(), (int) adjustment.getQuantity());
        Assert.assertEquals((int) readValue.getDiscountInCents(), (int) adjustment.getDiscountInCents());
        Assert.assertEquals((int) readValue.getTotalInCents(), (int) adjustment.getTotalInCents());
        Assert.assertEquals(readValue.getCurrency(), adjustment.getCurrency());
        Assert.assertEquals((boolean) readValue.getTaxable(), (boolean) adjustment.getTaxable());
        Assert.assertEquals(readValue.getStartDate(), adjustment.getStartDate());
        Assert.assertEquals(readValue.getEndDate(), adjustment.getEndDate());
        Assert.assertEquals(readValue.getCreatedAt(), adjustment.getCreatedAt());
    }

    @Test(groups = "fast")
    public void textDeserializationWithVertexData() throws Exception {
        final String adjustmentData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                "  <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                "  <uuid>626db120a84102b1809909071c701c60</uuid>\n" +
                "  <description>Charge for extra bandwidth</description>\n" +
                "  <accounting_code>bandwidth</accounting_code>\n" +
                "  <origin>charge</origin>\n" +
                "  <unit_amount_in_cents type=\"integer\">5000</unit_amount_in_cents>\n" +
                "  <quantity type=\"integer\">1</quantity>\n" +
                "  <discount_in_cents type=\"integer\">0</discount_in_cents>\n" +
                "  <tax_in_cents type=\"integer\">0</tax_in_cents>\n" +
                "  <total_in_cents type=\"integer\">5000</total_in_cents>\n" +
                "  <currency>USD</currency>\n" +
                "  <taxable type=\"boolean\">false</taxable>\n" +
                "  <!-- Vertex -->\n" +
                "  <tax_types type=\"array\">\n" +
                "    <tax_type>\n" +
                "      <type>General Sales and Use Tax</type>\n" +
                "      <juris_details type=\"array\">\n" +
                "        <juris_detail>\n" +
                "          <jurisdiction>STATE</jurisdiction>\n" +
                "          <jurisdiction_name nil=\"nil\"></jurisdiction_name>\n" +
                "          <tax_in_cents type=\"integer\">115</tax_in_cents>\n" +
                "          <description>Sales Tax</description>\n" +
                "          <rate type=\"float\">0.056</rate>\n" +
                "        </juris_detail>\n" +
                "        <juris_detail>\n" +
                "          <jurisdiction>COUNTY</jurisdiction>\n" +
                "          <jurisdiction_name nil=\"nil\"></jurisdiction_name>\n" +
                "          <tax_in_cents type=\"integer\">10</tax_in_cents>\n" +
                "          <description>Sales Tax</description>\n" +
                "          <rate type=\"float\">0.005</rate>\n" +
                "        </juris_detail>\n" +
                "        <juris_detail>\n" +
                "          <jurisdiction>DISTRICT</jurisdiction>\n" +
                "          <jurisdiction_name nil=\"nil\"></jurisdiction_name>\n" +
                "          <tax_in_cents type=\"integer\">103</tax_in_cents>\n" +
                "          <description>Sales Tax</description>\n" +
                "          <rate type=\"float\">0.05</rate>\n" +
                "        </juris_detail>\n" +
                "      </juris_details>\n" +
                "    </tax_type>\n" +
                "  </tax_types>\n" +
                "  <!-- /Vertex -->\n" +
                "  <start_date type=\"datetime\">2011-08-31T03:30:00Z</start_date>\n" +
                "  <end_date nil=\"nil\"></end_date>\n" +
                "  <created_at type=\"datetime\">2011-08-31T03:30:00Z</created_at>\n" +
                "</adjustment>";

        final Adjustment adjustment = xmlMapper.readValue(adjustmentData, Adjustment.class);
        Assert.assertEquals(adjustment.getTaxTypes().size(), 1);

        final TaxType taxType = adjustment.getTaxTypes().get(0);

        Assert.assertEquals(taxType.getType(), "General Sales and Use Tax");
        Assert.assertEquals(taxType.getJurisDetails().size(), 3);

        final JurisDetail jurisDetail = taxType.getJurisDetails().get(0);

        Assert.assertEquals(jurisDetail.getJurisdiction(), "STATE");
        Assert.assertEquals(jurisDetail.getJurisdictionName(), null);
        Assert.assertEquals(jurisDetail.getTaxInCents(), new Integer(115));
        Assert.assertEquals(jurisDetail.getDescription(), "Sales Tax");
        Assert.assertEquals(jurisDetail.getRate(), new BigDecimal("0.056"));
    }
}
