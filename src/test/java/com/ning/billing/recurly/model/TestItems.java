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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestItems extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String itemsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                "<items type=\"array\">\n" +
                                "   <item href=\"https://api.recurly.com/v2/items/plastic_gloves\">\n" +
                                "       <item_code>plastic_gloves</item_code>\n" +
                                "       <name>Awesome Plastic Gloves</name>\n" +
                                "       <description>Sleek Plastic</description>\n" +
                                "       <external_sku>awesome-plastic-gloves</external_sku>\n" +
                                "       <revenue_schedule_type>never</revenue_schedule_type>\n" +
                                "       <accounting_code nil=\"nil\"></accounting_code>\n" +
                                "       <created_at type=\"dateTime\">2019-04-19T07:00:00Z</created_at>\n" +
                                "       <updated_at type=\"dateTime\">2019-04-19T07:00:00Z</updated_at>\n" +
                                "       <state>active</state>\n" +
                                "   </item>\n" +
                                "</items>";

        final Items items = xmlMapper.readValue(itemsData, Items.class);
        Assert.assertEquals(items.size(), 1);

        final Item item = items.get(0);
        Assert.assertEquals(item.getItemCode(), "plastic_gloves");
        Assert.assertEquals(item.getName(), "Awesome Plastic Gloves");
        Assert.assertEquals(item.getDescription(), "Sleek Plastic");
        Assert.assertEquals(item.getExternalSku(), "awesome-plastic-gloves");
        Assert.assertEquals(item.getRevenueScheduleType(), "never");
        Assert.assertNull(item.getAccountingCode());
        Assert.assertEquals(item.getCreatedAt(), new DateTime("2019-04-19T07:00:00Z"));
        Assert.assertEquals(item.getUpdatedAt(), new DateTime("2019-04-19T07:00:00Z"));
        Assert.assertEquals(item.getState(), "active");
    }
}

