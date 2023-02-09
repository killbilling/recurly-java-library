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

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCustomFieldDefinition extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final String customFieldDefinitionData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<custom_field_definition type=\"credit\" href=\"https://api.recurly.com/v2/custom_field_definitions/626db120a84102b1809909071c701c60\">\n" +
                                      "  <id>626db120a84102b1809909071c701c60</id>\n" +
                                      "  <related_type>charge</related_type>\n" +
                                      "  <name>size</name>\n" +
                                      "  <user_access>writable</user_access>\n" +
                                      "  <display_name>Size</display_name>\n" +
                                      "  <tooltip>Click twice</tooltip>\n" +
                                      "  <created_at type=\"dateTime\">2023-01-31T03:30:00Z</created_at>\n" +
                                      "  <updated_at type=\"dateTime\">2023-01-31T03:30:00Z</updated_at>\n" +
                                      "  <deleted_at nil=\"nil\"></deleted_at>\n" +
                                      "</custom_field_definition>";

        final CustomFieldDefinition customFieldDefinition = xmlMapper.readValue(customFieldDefinitionData, CustomFieldDefinition.class);

        Assert.assertEquals(customFieldDefinition.getId(), "626db120a84102b1809909071c701c60");
        Assert.assertEquals(customFieldDefinition.getName(), "size");
        Assert.assertEquals(customFieldDefinition.getRelatedType(), "charge");
        Assert.assertEquals(customFieldDefinition.getUserAccess(), "writable");
        Assert.assertEquals(customFieldDefinition.getDisplayName(), "Size");
        Assert.assertEquals(customFieldDefinition.getTooltip(), "Click twice");
        Assert.assertEquals(customFieldDefinition.getCreatedAt(), new DateTime("2023-01-31T03:30:00Z"));
        Assert.assertEquals(customFieldDefinition.getUpdatedAt(), new DateTime("2023-01-31T03:30:00Z"));

        // Test Serialization
        final String xml = xmlMapper.writeValueAsString(customFieldDefinition);
        final CustomFieldDefinition readValue = xmlMapper.readValue(xml, CustomFieldDefinition.class);

        Assert.assertEquals(readValue.getId(), customFieldDefinition.getId());
        Assert.assertEquals(readValue.getName(), customFieldDefinition.getName());
        Assert.assertEquals(readValue.getRelatedType(), customFieldDefinition.getRelatedType());
        Assert.assertEquals(readValue.getUserAccess(), customFieldDefinition.getUserAccess());
        Assert.assertEquals(readValue.getDisplayName(), customFieldDefinition.getDisplayName());
        Assert.assertEquals(readValue.getTooltip(), customFieldDefinition.getTooltip());
        Assert.assertEquals(readValue.getCreatedAt(), customFieldDefinition.getCreatedAt());
        Assert.assertEquals(readValue.getUpdatedAt(), customFieldDefinition.getUpdatedAt());
    }
}
