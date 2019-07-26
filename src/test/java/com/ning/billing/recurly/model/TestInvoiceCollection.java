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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestInvoiceCollection extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String invoiceCollectionData = "<invoice_collection>\n" +
                "  <charge_invoice>\n" +
                "    <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                "    <subscriptions href=\"https://api.recurly.com/v2/invoices/1007/subscriptions\"/>\n" +
                "    <address>\n" +
                "      <address1>123 Main St.</address1>\n" +
                "      <address2 nil=\"nil\"/>\n" +
                "      <city>San Francisco</city>\n" +
                "      <state>CA</state>\n" +
                "      <zip>94105</zip>\n" +
                "      <country>US</country>\n" +
                "      <phone nil=\"nil\"/>\n" +
                "    </address>\n" +
                "    <uuid>37c0057d2f641f4e2fcbda4b5b833633</uuid>\n" +
                "    <state>paid</state>\n" +
                "    <invoice_number_prefix/>\n" +
                "    <invoice_number type=\"integer\">1007</invoice_number>\n" +
                "    <po_number nil=\"nil\"/>\n" +
                "    <vat_number nil=\"nil\"/>\n" +
                "    <subtotal_in_cents type=\"integer\">2000</subtotal_in_cents>\n" +
                "    <discount_in_cents type=\"integer\">0</discount_in_cents>\n" +
                "    <due_on type=\"datetime\">2018-01-30T21:11:50Z</due_on>\n" +
                "    <balance_in_cents type=\"integer\">0</balance_in_cents>\n" +
                "    <type>charge</type>\n" +
                "    <origin>purchase</origin>\n" +
                "    <credit_invoices href=\"https://api.recurly.com/v2/invoices/1325/credit_invoices\"/>\n" +
                "    <refundable_total_in_cents type=\"integer\">2000</refundable_total_in_cents>\n" +
                "    <credit_payments type=\"array\">\n" +
                "    </credit_payments>\n" +
                "    <tax_in_cents type=\"integer\">424</tax_in_cents>\n" +
                "    <total_in_cents type=\"integer\">5274</total_in_cents>\n" +
                "    <currency>EUR</currency>\n" +
                "    <created_at type=\"datetime\">2016-08-03T16:26:26Z</created_at>\n" +
                "    <updated_at type=\"datetime\">2016-08-03T16:26:26Z</updated_at>\n" +
                "    <closed_at type=\"datetime\">2016-08-03T16:26:26Z</closed_at>\n" +
                "    <terms_and_conditions nil=\"nil\"/>\n" +
                "    <customer_notes nil=\"nil\"/>\n" +
                "    <tax_type>usst</tax_type>\n" +
                "    <tax_region>CA</tax_region>\n" +
                "    <tax_rate type=\"float\">0.0875</tax_rate>\n" +
                "    <net_terms type=\"integer\">0</net_terms>\n" +
                "    <collection_method>automatic</collection_method>\n" +
                "    <redemptions href=\"https://api.recurly.com/v2/invoices/1007/redemptions\"/>\n" +
                "    <line_items type=\"array\">\n" +
                "      <adjustment href=\"https:/api.recurly.com/v2/adjustments/37c0057d2a3ace0c3f3d674ae89fdabd\" type=\"charge\">\n" +
                "        <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                "        <invoice href=\"https://api.recurly.com/v2/invoices/1007\"/>\n" +
                "        <subscription href=\"https://api.recurly.com/v2/subscriptions/37c0057cb105f67b6b8a1146f7b59c41\"/>\n" +
                "        <uuid>37c0057d2a3ace0c3f3d674ae89fdabd</uuid>\n" +
                "        <state>invoiced</state>\n" +
                "        <description>Setup fee: Gold plan</description>\n" +
                "        <accounting_code nil=\"nil\"/>\n" +
                "        <product_code>gold</product_code>\n" +
                "        <origin>setup_fee</origin>\n" +
                "        <unit_amount_in_cents type=\"integer\">800</unit_amount_in_cents>\n" +
                "        <quantity type=\"integer\">1</quantity>\n" +
                "        <discount_in_cents type=\"integer\">0</discount_in_cents>\n" +
                "        <tax_in_cents type=\"integer\">70</tax_in_cents>\n" +
                "        <total_in_cents type=\"integer\">870</total_in_cents>\n" +
                "        <currency>EUR</currency>\n" +
                "        <taxable type=\"boolean\">false</taxable>\n" +
                "        <tax_type>usst</tax_type>\n" +
                "        <tax_region>CA</tax_region>\n" +
                "        <tax_rate type=\"float\">0.0875</tax_rate>\n" +
                "        <tax_exempt type=\"boolean\">false</tax_exempt>\n" +
                "        <tax_code nil=\"nil\"/>\n" +
                "        <shipping_address>\n" +
                "          <name>Lon Doner</name>\n" +
                "          <address1>400 Alabama St</address1>\n" +
                "          <address2></address2>\n" +
                "          <city>San Francisco</city>\n" +
                "          <state>CA</state>\n" +
                "          <zip>94110</zip>\n" +
                "          <country>US</country>\n" +
                "          <phone></phone>\n" +
                "          <nickname>home</nickname>\n" +
                "        </shipping_address>\n" +
                "        <start_date type=\"datetime\">2016-08-03T16:26:26Z</start_date>\n" +
                "        <end_date nil=\"nil\"/>\n" +
                "        <created_at type=\"datetime\">2016-08-03T16:26:26Z</created_at>\n" +
                "        <updated_at type=\"datetime\">2016-08-03T16:26:26Z</updated_at>\n" +
                "        <revenue_schedule_type>evenly</revenue_schedule_type>\n" +
                "      </adjustment>\n" +
                "      <!-- Continued... -->\n" +
                "    </line_items>\n" +
                "    <transactions type=\"array\">\n" +
                "      <!-- Detail. -->\n" +
                "    </transactions>\n" +
                "    <a name=\"refund\" href=\"https://api.recurly.com/v2/invoices/1007/refund\" method=\"post\"/>\n" +
                "  </charge_invoice>\n" +
                "  <credit_invoices type=\"array\">\n" +
                "  <credit_invoice>\n" +
                "    <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                "    <subscriptions href=\"https://api.recurly.com/v2/invoices/1007/subscriptions\"/>\n" +
                "    <address>\n" +
                "      <address1>123 Main St.</address1>\n" +
                "      <address2 nil=\"nil\"/>\n" +
                "      <city>San Francisco</city>\n" +
                "      <state>CA</state>\n" +
                "      <zip>94105</zip>\n" +
                "      <country>US</country>\n" +
                "      <phone nil=\"nil\"/>\n" +
                "    </address>\n" +
                "    <uuid>37c0057d2f641f4e2fcbda4b5b833633</uuid>\n" +
                "    <state>paid</state>\n" +
                "    <invoice_number_prefix/>\n" +
                "    <invoice_number type=\"integer\">1007</invoice_number>\n" +
                "    <po_number nil=\"nil\"/>\n" +
                "    <vat_number nil=\"nil\"/>\n" +
                "    <subtotal_in_cents type=\"integer\">2000</subtotal_in_cents>\n" +
                "    <discount_in_cents type=\"integer\">0</discount_in_cents>\n" +
                "    <due_on type=\"datetime\">2018-01-30T21:11:50Z</due_on>\n" +
                "    <balance_in_cents type=\"integer\">0</balance_in_cents>\n" +
                "    <type>charge</type>\n" +
                "    <origin>purchase</origin>\n" +
                "    <credit_invoices href=\"https://api.recurly.com/v2/invoices/1325/credit_invoices\"/>\n" +
                "    <refundable_total_in_cents type=\"integer\">2000</refundable_total_in_cents>\n" +
                "    <credit_payments type=\"array\">\n" +
                "    </credit_payments>\n" +
                "    <tax_in_cents type=\"integer\">424</tax_in_cents>\n" +
                "    <total_in_cents type=\"integer\">5274</total_in_cents>\n" +
                "    <currency>EUR</currency>\n" +
                "    <created_at type=\"datetime\">2016-08-03T16:26:26Z</created_at>\n" +
                "    <updated_at type=\"datetime\">2016-08-03T16:26:26Z</updated_at>\n" +
                "    <closed_at type=\"datetime\">2016-08-03T16:26:26Z</closed_at>\n" +
                "    <terms_and_conditions nil=\"nil\"/>\n" +
                "    <customer_notes nil=\"nil\"/>\n" +
                "    <tax_type>usst</tax_type>\n" +
                "    <tax_region>CA</tax_region>\n" +
                "    <tax_rate type=\"float\">0.0875</tax_rate>\n" +
                "    <net_terms type=\"integer\">0</net_terms>\n" +
                "    <collection_method>automatic</collection_method>\n" +
                "    <redemptions href=\"https://api.recurly.com/v2/invoices/1007/redemptions\"/>\n" +
                "    <line_items type=\"array\">\n" +
                "      <adjustment href=\"https:/api.recurly.com/v2/adjustments/37c0057d2a3ace0c3f3d674ae89fdabd\" type=\"charge\">\n" +
                "        <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                "        <invoice href=\"https://api.recurly.com/v2/invoices/1007\"/>\n" +
                "        <subscription href=\"https://api.recurly.com/v2/subscriptions/37c0057cb105f67b6b8a1146f7b59c41\"/>\n" +
                "        <uuid>37c0057d2a3ace0c3f3d674ae89fdabd</uuid>\n" +
                "        <state>invoiced</state>\n" +
                "        <description>Setup fee: Gold plan</description>\n" +
                "        <accounting_code nil=\"nil\"/>\n" +
                "        <product_code>gold</product_code>\n" +
                "        <origin>setup_fee</origin>\n" +
                "        <unit_amount_in_cents type=\"integer\">800</unit_amount_in_cents>\n" +
                "        <quantity type=\"integer\">1</quantity>\n" +
                "        <discount_in_cents type=\"integer\">0</discount_in_cents>\n" +
                "        <tax_in_cents type=\"integer\">70</tax_in_cents>\n" +
                "        <total_in_cents type=\"integer\">870</total_in_cents>\n" +
                "        <currency>EUR</currency>\n" +
                "        <taxable type=\"boolean\">false</taxable>\n" +
                "        <tax_type>usst</tax_type>\n" +
                "        <tax_region>CA</tax_region>\n" +
                "        <tax_rate type=\"float\">0.0875</tax_rate>\n" +
                "        <tax_exempt type=\"boolean\">false</tax_exempt>\n" +
                "        <tax_code nil=\"nil\"/>\n" +
                "        <shipping_address>\n" +
                "          <name>Lon Doner</name>\n" +
                "          <address1>400 Alabama St</address1>\n" +
                "          <address2></address2>\n" +
                "          <city>San Francisco</city>\n" +
                "          <state>CA</state>\n" +
                "          <zip>94110</zip>\n" +
                "          <country>US</country>\n" +
                "          <phone></phone>\n" +
                "          <nickname>home</nickname>\n" +
                "        </shipping_address>\n" +
                "        <start_date type=\"datetime\">2016-08-03T16:26:26Z</start_date>\n" +
                "        <end_date nil=\"nil\"/>\n" +
                "        <created_at type=\"datetime\">2016-08-03T16:26:26Z</created_at>\n" +
                "        <updated_at type=\"datetime\">2016-08-03T16:26:26Z</updated_at>\n" +
                "        <revenue_schedule_type>evenly</revenue_schedule_type>\n" +
                "      </adjustment>\n" +
                "      <!-- Continued... -->\n" +
                "    </line_items>\n" +
                "    <transactions type=\"array\">\n" +
                "      <!-- Detail. -->\n" +
                "    </transactions>\n" +
                "    <a name=\"refund\" href=\"https://api.recurly.com/v2/invoices/1007/refund\" method=\"post\"/>\n" +
                "  </credit_invoice>\n" +
                "  </credit_invoices>\n" +
                "</invoice_collection>";

        final InvoiceCollection invoiceCollection = xmlMapper.readValue(invoiceCollectionData, InvoiceCollection.class);

        // check charge invoice
        assertEquals(invoiceCollection.getChargeInvoice().getId(), "1007");

        // check credit invoices
        assertEquals(invoiceCollection.getCreditInvoices().size(), 1);
        assertEquals(invoiceCollection.getCreditInvoices().get(0).getId(), "1007");
    }
}
