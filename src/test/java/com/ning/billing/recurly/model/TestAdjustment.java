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

import com.ning.billing.recurly.model.TaxDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAdjustment extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        // See https://dev.recurly.com/docs/list-an-accounts-adjustments
        final String adjustmentData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                      "  <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                                      "  <uuid>626db120a84102b1809909071c701c60</uuid>\n" +
                                      "  <type>charge</type>\n" +
                                      "  <state>invoiced</state>\n" +
                                      "  <description>Charge for extra bandwidth</description>\n" +
                                      "  <refundable_total_in_cents type=\"integer\">1000</refundable_total_in_cents>\n" +
                                      "  <accounting_code>bandwidth</accounting_code>\n" +
                                      "  <origin>charge</origin>\n" +
                                      "  <unit_amount_in_cents type=\"integer\">5000</unit_amount_in_cents>\n" +
                                      "  <quantity type=\"integer\">1</quantity>\n" +
                                      "  <discount_in_cents type=\"integer\">0</discount_in_cents>\n" +
                                      "  <tax_in_cents type=\"integer\">0</tax_in_cents>\n" +
                                      "  <total_in_cents type=\"integer\">5000</total_in_cents>\n" +
                                      "  <currency>USD</currency>\n" +
                                      "  <proration_rate type=\"float\">0.133</proration_rate>\n" +
                                      "  <product_code>product123</product_code>\n" +
                                      "  <taxable type=\"boolean\">false</taxable>\n" +
                                      "  <surcharge_in_cents type=\"integer\">100</surcharge_in_cents>\n" +
                                      "  <tax_type>usst</tax_type>\n" +
                                      "  <tax_region>CA</tax_region>\n" +
                                      "  <tax_rate type=\"float\">0.0875</tax_rate>\n" +
                                      "  <tax_exempt type=\"boolean\">false</tax_exempt>\n" +
                                      "  <tax_code>digital</tax_code>\n" +
                                      "  <tax_details type=\"array\">\n" +
                                      "    <tax_detail>\n" +
                                      "      <name>Special Tax</name>\n" +
                                      "      <type>state</type>\n" +
                                      "      <tax_rate type=\"float\">0.065</tax_rate>\n" +
                                      "      <tax_in_cents type=\"integer\">-52</tax_in_cents>\n" +
                                      "    </tax_detail>\n" +
                                      "  </tax_details>\n" +
                                      "  <start_date type=\"dateTime\">2011-08-31T03:30:00Z</start_date>\n" +
                                      "  <revenue_schedule_type>at_invoice</revenue_schedule_type>\n" +
                                      "  <end_date nil=\"nil\"></end_date>\n" +
                                      "  <created_at type=\"dateTime\">2011-08-31T03:30:00Z</created_at>\n" +
                                      "</adjustment>";

        final Adjustment adjustment = xmlMapper.readValue(adjustmentData, Adjustment.class);

        Assert.assertEquals(adjustment.getAccount().getHref(), "https://api.recurly.com/v2/accounts/1");
        Assert.assertEquals(adjustment.getUuid(), "626db120a84102b1809909071c701c60");
        Assert.assertEquals(adjustment.getDescription(), "Charge for extra bandwidth");
        Assert.assertEquals(adjustment.getRefundableTotalInCents(), new Integer(1000));
        Assert.assertEquals(adjustment.getState(), "invoiced");
        Assert.assertEquals(adjustment.getProrationRate(), new BigDecimal("0.133"));
        Assert.assertEquals(adjustment.getAccountingCode(), "bandwidth");
        Assert.assertEquals(adjustment.getOrigin(), "charge");
        Assert.assertEquals((int) adjustment.getUnitAmountInCents(), 5000);
        Assert.assertEquals((int) adjustment.getQuantity(), 1);
        Assert.assertEquals((int) adjustment.getDiscountInCents(), 0);
        Assert.assertEquals((int) adjustment.getTotalInCents(), 5000);
        Assert.assertEquals(adjustment.getCurrency(), "USD");
        Assert.assertEquals((boolean) adjustment.getTaxable(), false);
        Assert.assertEquals(adjustment.getType(), "charge");
        Assert.assertEquals(adjustment.getTaxType(), "usst");
        Assert.assertEquals(adjustment.getTaxRegion(), "CA");
        Assert.assertEquals(adjustment.getTaxCode(), "digital");
        Assert.assertEquals(adjustment.getTaxDetails(), this.getTaxDetails());
        Assert.assertEquals(adjustment.getProductCode(), "product123");
        Assert.assertEquals(adjustment.getStartDate(), new DateTime("2011-08-31T03:30:00Z"));
        Assert.assertNull(adjustment.getEndDate());
        Assert.assertEquals(adjustment.getCreatedAt(), new DateTime("2011-08-31T03:30:00Z"));
        Assert.assertEquals(adjustment.getRevenueScheduleType(), RevenueScheduleType.AT_INVOICE);
        Assert.assertEquals(adjustment.getSurchargeInCents(), new Integer(100));

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
        Assert.assertEquals(readValue.getRevenueScheduleType(), adjustment.getRevenueScheduleType());
    }

    private List<TaxDetail> getTaxDetails() {
        final List<TaxDetail> taxDetails = new ArrayList<>();
        final TaxDetail taxDetail = new TaxDetail();

        taxDetail.setName("Special Tax");
        taxDetail.setType("state");
        taxDetail.setTaxRate(BigDecimal.valueOf(0.065));
        taxDetail.setTaxInCents(-52);
        taxDetails.add(taxDetail);

        return taxDetails;
    }
}
