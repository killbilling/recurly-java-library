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

public class TestInvoices extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-invoices
        final String invoicesData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                    + "<invoices type=\"array\">\n"
                                    + "  <invoice href=\"https://api.recurly.com/v2/invoices/e3f0a9e084a2468480d00ee61b090d4d\">\n"
                                    + "    <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n"
                                    + "    <uuid>421f7b7d414e4c6792938e7c49d552e9</uuid>\n"
                                    + "    <state>open</state>\n"
                                    + "    <invoice_number type=\"integer\">1005</invoice_number>\n"
                                    + "    <po_number>abc-123</po_number>\n"
                                    + "    <vat_number></vat_number>\n"
                                    + "    <subtotal_in_cents type=\"integer\">1200</subtotal_in_cents>\n"
                                    + "    <tax_in_cents type=\"integer\">0</tax_in_cents>\n"
                                    + "    <total_in_cents type=\"integer\">1200</total_in_cents>\n"
                                    + "    <currency>USD</currency>\n"
                                    + "    <subtotal_after_discount_in_cents type=\"integer\">300</subtotal_after_discount_in_cents>\n"
                                    + "    <attempt_next_collection_at type=\"datetime\">2017-08-31T00:00:00Z</attempt_next_collection_at>\n"
                                    + "    <recovery_reason>Reason</recovery_reason>\n"
                                    + "    <tax_types nil=\"nil\"></tax_types>"
                                    + "    <created_at type=\"datetime\">2011-08-25T12:00:00Z</created_at>\n"
                                    + "    <line_items type=\"array\">\n"
                                    + "      <adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n"
                                    + "        <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n"
                                    + "        <uuid>626db120a84102b1809909071c701c60</uuid>\n"
                                    + "        <description>Charge for extra bandwidth</description>\n"
                                    + "        <accounting_code>bandwidth</accounting_code>\n"
                                    + "        <origin>charge</origin>\n"
                                    + "        <unit_amount_in_cents type=\"integer\">5000</unit_amount_in_cents>\n"
                                    + "        <quantity type=\"integer\">1</quantity>\n"
                                    + "        <discount_in_cents type=\"integer\">0</discount_in_cents>\n"
                                    + "        <tax_in_cents type=\"integer\">0</tax_in_cents>\n"
                                    + "        <total_in_cents type=\"integer\">5000</total_in_cents>\n"
                                    + "        <currency>USD</currency>\n"
                                    + "        <taxable type=\"boolean\">false</taxable>\n"
                                    + "        <start_date type=\"datetime\">2011-08-31T03:30:00Z</start_date>\n"
                                    + "        <end_date nil=\"nil\"></end_date>\n"
                                    + "        <created_at type=\"datetime\">2011-08-31T03:30:00Z</created_at>\n"
                                    + "      </adjustment>\n"
                                    + "    </line_items>\n"
                                    + "    <transactions type=\"array\">\n"
                                    + "    </transactions>\n"
                                    + "  </invoice>\n"
                                    + "  <!-- Continued... -->\n"
                                    + "</invoices>";

        final Invoices invoices = xmlMapper.readValue(invoicesData, Invoices.class);
        Assert.assertEquals(invoices.size(), 1);

        final Invoice invoice = invoices.get(0);
        Assert.assertEquals(invoice.getAccount().getHref(), "https://api.recurly.com/v2/accounts/1");
        Assert.assertEquals(invoice.getUuid(), "421f7b7d414e4c6792938e7c49d552e9");
        Assert.assertEquals(invoice.getState(), "open");
        Assert.assertEquals((int) invoice.getInvoiceNumber(), 1005);
        Assert.assertEquals(invoice.getPoNumber(), "abc-123");
        Assert.assertNull(invoice.getVatNumber());
        Assert.assertEquals((int) invoice.getSubtotalInCents(), 1200);
        Assert.assertEquals((int) invoice.getTaxInCents(), 0);
        Assert.assertEquals((int) invoice.getTotalInCents(), 1200);
        Assert.assertEquals(invoice.getCurrency(), "USD");
        Assert.assertEquals(invoice.getCreatedAt(), new DateTime("2011-08-25T12:00:00Z"));
        Assert.assertNotNull(invoice.getLineItems());
        Assert.assertEquals(invoice.getLineItems().size(), 1);
        Assert.assertEquals(invoice.getTransactions().size(), 0);
        Assert.assertEquals(invoice.getSubtotalAfterDiscountInCents(), Integer.valueOf(300));
        Assert.assertEquals(invoice.getAttemptNextCollectionAt(), new DateTime("2017-08-31T00:00:00Z"));
        Assert.assertEquals(invoice.getRecoveryReason(), "Reason");
        Assert.assertNull(invoice.getTaxType());
    }
}
