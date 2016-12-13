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

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestInvoice extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-invoices
        final String invoiceData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                   + "<invoice href=\"https://api.recurly.com/v2/invoices/e3f0a9e084a2468480d00ee61b090d4d\">\n"
                                   + "  <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n"
                                   + "  <original_invoice href=\"https://api.recurly.com/v2/invoices/1192\"/>"
                                   + "  <uuid>421f7b7d414e4c6792938e7c49d552e9</uuid>\n"
                                   + "  <state>open</state>\n"
                                   + "  <invoice_number type=\"integer\">1402</invoice_number>\n"
                                   + "  <po_number>abc-123</po_number>\n"
                                   + "  <vat_number></vat_number>\n"
                                   + "  <subtotal_in_cents type=\"integer\">9900</subtotal_in_cents>\n"
                                   + "  <tax_in_cents type=\"integer\">0</tax_in_cents>\n"
                                   + "  <total_in_cents type=\"integer\">9900</total_in_cents>\n"
                                   + "  <currency>USD</currency>\n"
                                   + "  <tax_type>usst</tax_type>\n"
                                   + "  <tax_region>CA</tax_region>\n"
                                   + "  <tax_rate type=\"float\">0.0875</tax_rate>\n"
                                   + "  <created_at type=\"datetime\">2011-08-25T12:00:00Z</created_at>\n"
                                   + "  <updated_at type=\"datetime\">2011-08-25T12:00:00Z</updated_at>\n"
                                   + "  <closed_at type=\"datetime\">2011-08-25T12:00:00Z</closed_at>\n"
                                   + "  <line_items type=\"array\">\n"
                                   + "    <adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n"
                                   + "      <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n"
                                   + "      <uuid>626db120a84102b1809909071c701c60</uuid>\n"
                                   + "      <description>Charge for extra bandwidth</description>\n"
                                   + "      <accounting_code>bandwidth</accounting_code>\n"
                                   + "      <origin>charge</origin>\n"
                                   + "      <unit_amount_in_cents type=\"integer\">5000</unit_amount_in_cents>\n"
                                   + "      <quantity type=\"integer\">1</quantity>\n"
                                   + "      <discount_in_cents type=\"integer\">0</discount_in_cents>\n"
                                   + "      <tax_in_cents type=\"integer\">0</tax_in_cents>\n"
                                   + "      <total_in_cents type=\"integer\">5000</total_in_cents>\n"
                                   + "      <currency>USD</currency>\n"
                                   + "      <taxable type=\"boolean\">false</taxable>\n"
                                   + "      <start_date type=\"datetime\">2011-08-31T03:30:00Z</start_date>\n"
                                   + "      <end_date nil=\"nil\"></end_date>\n"
                                   + "      <created_at type=\"datetime\">2011-08-31T03:30:00Z</created_at>\n"
                                   + "    </adjustment>\n"
                                   + "  </line_items>\n"
                                   + "  <transactions type=\"array\">\n"
                                   + "  </transactions>\n"
                                   + "</invoice>";

        final Invoice invoice = xmlMapper.readValue(invoiceData, Invoice.class);

        Assert.assertEquals(invoice.getAccount().getHref(), "https://api.recurly.com/v2/accounts/1");
        Assert.assertEquals(invoice.getOriginalInvoice().getHref(), "https://api.recurly.com/v2/invoices/1192");
        Assert.assertEquals(invoice.getUuid(), "421f7b7d414e4c6792938e7c49d552e9");
        Assert.assertEquals(invoice.getState(), "open");
        Assert.assertEquals((int) invoice.getInvoiceNumber(), 1402);
        Assert.assertEquals(invoice.getPoNumber(), "abc-123");
        Assert.assertNull(invoice.getVatNumber());
        Assert.assertEquals((int) invoice.getSubtotalInCents(), 9900);
        Assert.assertEquals((int) invoice.getTaxInCents(), 0);
        Assert.assertEquals((int) invoice.getTotalInCents(), 9900);
        Assert.assertEquals(invoice.getCurrency(), "USD");
        Assert.assertEquals(invoice.getTaxType(), "usst");
        Assert.assertEquals(invoice.getTaxRegion(), "CA");
        Assert.assertEquals(invoice.getTaxRate(), new BigDecimal("0.0875"));
        Assert.assertEquals(invoice.getCreatedAt(), new DateTime("2011-08-25T12:00:00Z"));
        Assert.assertEquals(invoice.getUpdatedAt(), new DateTime("2011-08-25T12:00:00Z"));
        Assert.assertEquals(invoice.getClosedAt(), new DateTime("2011-08-25T12:00:00Z"));
        Assert.assertNotNull(invoice.getLineItems());
        Assert.assertEquals(invoice.getLineItems().size(), 1);

        final Adjustment adjustment = invoice.getLineItems().get(0);
        Assert.assertEquals(adjustment.getDescription(), "Charge for extra bandwidth");
        Assert.assertEquals((int) adjustment.getTotalInCents(), 5000);
        Assert.assertEquals(adjustment.getStartDate(), new DateTime("2011-08-31T03:30:00Z"));

        Assert.assertEquals(invoice.getTransactions().size(), 0);
    }

    @Test(groups = "fast")
    public void textDeserializationWithVertexData() throws Exception {
        final String invoiceData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<invoice href=\"https://api.recurly.com/v2/invoices/e3f0a9e084a2468480d00ee61b090d4d\">\n"
                + "  <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n"
                + "  <original_invoice href=\"https://api.recurly.com/v2/invoices/1192\"/>"
                + "  <uuid>421f7b7d414e4c6792938e7c49d552e9</uuid>\n"
                + "  <state>open</state>\n"
                + "  <invoice_number type=\"integer\">1402</invoice_number>\n"
                + "  <po_number>abc-123</po_number>\n"
                + "  <vat_number></vat_number>\n"
                + "  <subtotal_in_cents type=\"integer\">9900</subtotal_in_cents>\n"
                + "  <tax_in_cents type=\"integer\">0</tax_in_cents>\n"
                + "  <total_in_cents type=\"integer\">9900</total_in_cents>\n"
                + "  <currency>USD</currency>\n"
                + "  <tax_type>usst</tax_type>\n"
                + "  <tax_region>CA</tax_region>\n"
                + "  <tax_rate type=\"float\">0.0875</tax_rate>\n"
                + "  <created_at type=\"datetime\">2011-08-25T12:00:00Z</created_at>\n"
                + "  <updated_at type=\"datetime\">2011-08-25T12:00:00Z</updated_at>\n"
                + "  <closed_at type=\"datetime\">2011-08-25T12:00:00Z</closed_at>\n"
                + "  <line_items type=\"array\">\n"
                + "    <adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n"
                + "      <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n"
                + "      <uuid>626db120a84102b1809909071c701c60</uuid>\n"
                + "      <description>Charge for extra bandwidth</description>\n"
                + "      <accounting_code>bandwidth</accounting_code>\n"
                + "      <origin>charge</origin>\n"
                + "      <unit_amount_in_cents type=\"integer\">5000</unit_amount_in_cents>\n"
                + "      <quantity type=\"integer\">1</quantity>\n"
                + "      <discount_in_cents type=\"integer\">0</discount_in_cents>\n"
                + "      <tax_in_cents type=\"integer\">0</tax_in_cents>\n"
                + "      <total_in_cents type=\"integer\">5000</total_in_cents>\n"
                + "      <currency>USD</currency>\n"
                + "      <taxable type=\"boolean\">false</taxable>\n"
                + "      <start_date type=\"datetime\">2011-08-31T03:30:00Z</start_date>\n"
                + "      <end_date nil=\"nil\"></end_date>\n"
                + "      <created_at type=\"datetime\">2011-08-31T03:30:00Z</created_at>\n"
                + "    </adjustment>\n"
                + "  </line_items>\n"
                + "  <transactions type=\"array\">\n"
                + "  </transactions>\n"
                + "  <!-- Vertex -->\n"
                + "  <refund_tax_date type=\"datetime\">2017-04-30T00:00:00Z</refund_tax_date>\n"
                + "  <refund_geo_code>ABC123</refund_geo_code>\n"
                + "  <tax_types type=\"array\">\n"
                + "    <tax_type>\n"
                + "      <type>STATE</type>\n"
                + "      <tax_in_cents type=\"integer\">115</tax_in_cents>\n"
                + "      <description>Sales Tax</description>\n"
                + "    </tax_type>\n"
                + "    <tax_type>\n"
                + "      <type>COUNTY</type>\n"
                + "      <tax_in_cents type=\"integer\">10</tax_in_cents>\n"
                + "      <description>Sales Tax</description>\n"
                + "    </tax_type>\n"
                + "    <tax_type>\n"
                + "      <type>DISTRICT</type>\n"
                + "      <tax_in_cents type=\"integer\">103</tax_in_cents>\n"
                + "      <description>Sales Tax</description>\n"
                + "    </tax_type>\n"
                + "  </tax_types>\n"
                + "  <!-- /Vertex -->\n"
                + "</invoice>";

        final Invoice invoice = xmlMapper.readValue(invoiceData, Invoice.class);

        Assert.assertEquals(invoice.getRefundTaxDate(), new DateTime("2017-04-30T00:00:00Z"));
        Assert.assertEquals(invoice.getRefundGeoCode(), "ABC123");
        Assert.assertEquals(invoice.getTaxTypes().size(), 3);

        final TaxType taxType = invoice.getTaxTypes().get(0);

        Assert.assertEquals(taxType.getType(), "STATE");
        Assert.assertEquals(taxType.getTaxInCents(), new Integer(115));
        Assert.assertEquals(taxType.getDescription(), "Sales Tax");
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create invoices of the same value but difference references
        Invoice invoice = TestUtils.createRandomInvoice(0);
        Invoice otherInvoice = TestUtils.createRandomInvoice(0);

        assertNotEquals(System.identityHashCode(invoice), System.identityHashCode(otherInvoice));
        assertEquals(invoice.hashCode(), otherInvoice.hashCode());
        assertEquals(invoice, otherInvoice);
    }
}
