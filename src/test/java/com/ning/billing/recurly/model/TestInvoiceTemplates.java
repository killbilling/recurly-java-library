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

public class TestInvoiceTemplates extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-invoice-templates
        final String invoiceTemplatesData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                    "<invoice_templates type=\"array\">\n" +
                                    "  <invoice_template href=\"https://api.recurly.com/v2/invoice_templates/q0tzf7o7fpbl\">\n" +
                                    "    <accounts href=\"https://api.recurly.com/v2/invoice_templates/q0tzf7o7fpbl/accounts\"/>\n" +
                                    "    <uuid>q0tzf7o7fpbl</uuid>\n" +
                                    "    <code>somecode</code>\n" +
                                    "    <name>Template</name>\n" +
                                    "    <description>Description</description>\n" +
                                    "    <created_at type=\"dateTime\">2011-10-25T12:00:00</created_at>\n" +
                                    "    <updated_at type=\"dateTime\">2011-10-23T12:00:00</updated_at>\n" +
                                    "  </invoice_template>\n" +
                                    "  <!-- Continued... -->\n" +
                                    "</invoice_templates>";

        final InvoiceTemplates invoiceTemplates = xmlMapper.readValue(invoiceTemplatesData, InvoiceTemplates.class);
        Assert.assertEquals(invoiceTemplates.size(), 1);

        final InvoiceTemplate invoiceTemplate = invoiceTemplates.get(0);
        Assert.assertEquals(invoiceTemplate.getUuid(), "q0tzf7o7fpbl");
        Assert.assertEquals(invoiceTemplate.getCode(), "somecode");
        Assert.assertEquals(invoiceTemplate.getName(), "Template");
        Assert.assertEquals(invoiceTemplate.getDescription(), "Description");
        Assert.assertEquals(invoiceTemplate.getCreatedAt(), new DateTime("2011-10-25T12:00:00"));
        Assert.assertEquals(invoiceTemplate.getUpdatedAt(), new DateTime("2011-10-23T12:00:00"));
    }
}
