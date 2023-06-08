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
                                   + "  <original_invoices href=\"https://api.recurly.com/v2/invoices/1192/original_invoices\"/>\n"
                                   + "  <business_entity href=\"https://api.recurly.com/v2/business_entities/1\"/>\n"
                                   + "  <uuid>421f7b7d414e4c6792938e7c49d552e9</uuid>\n"
                                   + "  <state>open</state>\n"
                                   + "  <invoice_number type=\"integer\">1402</invoice_number>\n"
                                   + "  <invoice_number_prefix>FR</invoice_number_prefix>\n"
                                   + "  <po_number>abc-123</po_number>\n"
                                   + "  <vat_number></vat_number>\n"
                                   + "  <subtotal_in_cents type=\"integer\">9900</subtotal_in_cents>\n"
                                   + "  <tax_in_cents type=\"integer\">0</tax_in_cents>\n"
                                   + "  <total_in_cents type=\"integer\">9900</total_in_cents>\n"
                                   + "  <vat_reverse_charge_notes>Some reverse charge notes</vat_reverse_charge_notes>\n"
                                   + "  <customer_notes>Some notes</customer_notes>\n"
                                   + "  <terms_and_conditions>t and c</terms_and_conditions>\n"
                                   + "  <gateway_code>Some Gateway Code</gateway_code>\n"
                                   + "  <net_terms type=\"integer\">0</net_terms>\n"
                                   + "  <currency>USD</currency>\n"
                                   + "  <tax_type>usst</tax_type>\n"
                                   + "  <tax_region>CA</tax_region>\n"
                                   + "  <tax_rate type=\"float\">0.0875</tax_rate>\n"
                                   + "  <tax_details type=\"array\">\n"
                                   + "    <tax_detail>\n"
                                   + "      <tax_type>GST</tax_type>\n"
                                   + "      <tax_region>CA</tax_region>\n"
                                   + "      <tax_rate type=\"float\">0.05</tax_rate>\n"
                                   + "      <tax_in_cents type=\"integer\">20</tax_in_cents>\n"
                                   + "    </tax_detail>\n"
                                   + "  </tax_details>\n"
                                   + "  <used_tax_service>true</used_tax_service>\n"
                                   + "  <surcharge_in_cents type=\"integer\">100</surcharge_in_cents>\n"
                                   + "  <created_at type=\"dateTime\">2011-08-25T12:00:00Z</created_at>\n"
                                   + "  <updated_at type=\"dateTime\">2011-08-25T12:00:00Z</updated_at>\n"
                                   + "  <closed_at type=\"dateTime\">2011-08-25T12:00:00Z</closed_at>\n"
                                   + "    <address>\n"
                                   + "        <first_name>John</first_name>\n"
                                   + "        <last_name>Smith</last_name>\n"
                                   + "        <name_on_account>John Smith</name_on_account>\n"
                                   + "        <company>East Atlantic Trading Company</company>\n"
                                   + "        <address1>123 Main St.</address1>\n"
                                   + "        <address2 nil=\"nil\"></address2>\n"
                                   + "        <city>San Francisco</city>\n"
                                   + "        <state>CA</state>\n"
                                   + "        <zip>94105</zip>\n"
                                   + "        <country>US</country>\n"
                                   + "        <phone nil=\"nil\"></phone>\n"
                                   + "    </address>\n"
                                   + "    <shipping_address>\n"
                                   + "        <name>Tester Number 11</name>\n"
                                   + "        <address1>123 Canal St.</address1>\n"
                                   + "        <address2>Suite 101</address2>\n"
                                   + "        <city>San Francisco</city>\n"
                                   + "        <state>CA</state>\n"
                                   + "        <zip>94105</zip>\n"
                                   + "        <country>US</country>\n"
                                   + "        <phone>555-222-1212</phone>\n"
                                   + "    </shipping_address>"
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
                                   + "      <start_date type=\"dateTime\">2011-08-31T03:30:00Z</start_date>\n"
                                   + "      <end_date nil=\"nil\"></end_date>\n"
                                   + "      <created_at type=\"dateTime\">2011-08-31T03:30:00Z</created_at>\n"
                                   + "    </adjustment>\n"
                                   + "  </line_items>\n"
                                   + "  <transactions type=\"array\">\n"
                                   + "  </transactions>\n"
                                   + "  <credit_payments type=\"array\">\n"
                                   + "    <credit_payment href=\"https://api.recurly.com/v2/credit_payments/4f1dd58d3cb9af5a09ba634dcca690a6\">\n"
                                   + "      <account href=\"https://api.recurly.com/v2/accounts/1\"></account>\n"
                                   + "      <action>write_off</action>\n"
                                   + "    </credit_payment>\n"
                                   + "  </credit_payments>\n"
                                   + "  <dunning_campaign_id>1234abcd</dunning_campaign_id>\n"
                                   + "</invoice>";

        final Invoice invoice = xmlMapper.readValue(invoiceData, Invoice.class);

        Assert.assertEquals(invoice.getAccount().getHref(), "https://api.recurly.com/v2/accounts/1");
        Assert.assertEquals(invoice.getBusinessEntity().getHref(), "https://api.recurly.com/v2/business_entities/1");
        Assert.assertTrue(invoice.hasOriginalInvoices());
        Assert.assertEquals(invoice.getUuid(), "421f7b7d414e4c6792938e7c49d552e9");
        Assert.assertEquals(invoice.getState(), "open");
        Assert.assertEquals((int) invoice.getInvoiceNumber(), 1402);
        Assert.assertEquals(invoice.getPoNumber(), "abc-123");
        Assert.assertEquals(invoice.getVatReverseChargeNotes(), "Some reverse charge notes");
        Assert.assertEquals(invoice.getCustomerNotes(), "Some notes");
        Assert.assertEquals(invoice.getTermsAndConditions(), "t and c");
        Assert.assertEquals((int) invoice.getNetTerms(), 0);
        Assert.assertNull(invoice.getVatNumber());
        Assert.assertEquals(invoice.getGatewayCode(), "Some Gateway Code");
        Assert.assertEquals((int) invoice.getSubtotalInCents(), 9900);
        Assert.assertEquals((int) invoice.getTaxInCents(), 0);
        Assert.assertEquals((int) invoice.getTotalInCents(), 9900);
        Assert.assertEquals(invoice.getCurrency(), "USD");
        Assert.assertEquals(invoice.getTaxType(), "usst");
        Assert.assertEquals(invoice.getTaxRegion(), "CA");
        Assert.assertEquals(invoice.getTaxRate(), new BigDecimal("0.0875"));
        Assert.assertTrue(invoice.getUsedTaxService());
        Assert.assertEquals(invoice.getCreatedAt(), new DateTime("2011-08-25T12:00:00Z"));
        Assert.assertEquals(invoice.getUpdatedAt(), new DateTime("2011-08-25T12:00:00Z"));
        Assert.assertEquals(invoice.getClosedAt(), new DateTime("2011-08-25T12:00:00Z"));
        Assert.assertNotNull(invoice.getLineItems());
        Assert.assertEquals(invoice.getLineItems().size(), 1);
        Assert.assertEquals(invoice.getInvoiceNumberPrefix(), "FR");
        Assert.assertEquals(invoice.getId(), "FR1402");
        Assert.assertEquals(invoice.getSurchargeInCents(), new Integer(100));

        Assert.assertEquals(invoice.getTaxDetails().size(), 1);
        TaxDetail taxDetail = invoice.getTaxDetails().get(0);
        Assert.assertEquals(taxDetail.getTaxRate(), new BigDecimal("0.05"));
        Assert.assertEquals((int) taxDetail.getTaxInCents(), 20);
        Assert.assertEquals(taxDetail.getTaxRegion(), "CA");
        Assert.assertEquals(taxDetail.getTaxType(), "GST");

        final Adjustment adjustment = invoice.getLineItems().get(0);
        Assert.assertEquals(adjustment.getDescription(), "Charge for extra bandwidth");
        Assert.assertEquals((int) adjustment.getTotalInCents(), 5000);
        Assert.assertEquals(adjustment.getStartDate(), new DateTime("2011-08-31T03:30:00Z"));

        Assert.assertEquals(invoice.getTransactions().size(), 0);
        Assert.assertEquals(invoice.getCreditPayments().size(), 1);

        Assert.assertEquals(invoice.getAddress().getAddress1(), "123 Main St.");
        Assert.assertEquals(invoice.getShippingAddress().getAddress1(), "123 Canal St.");
        Assert.assertEquals(invoice.getDunningCampaignId(), "1234abcd");

        // test setting billing info uuid
        invoice.setBillingInfoUuid("iiznlrvdt8py");
    }

    @Test(groups = "fast")
    public void testGetId() throws Exception {
        final Invoice invoice = new Invoice();
        Assert.assertNull(invoice.getId());
        invoice.setInvoiceNumber(9999);
        Assert.assertEquals(invoice.getId(), "9999");
        invoice.setInvoiceNumberPrefix("FR");
        Assert.assertEquals(invoice.getId(), "FR9999");
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
