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

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ning.billing.recurly.TestUtils;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class TestRevenueScheduleType extends TestModelBase {

    @Test(groups = "fast")
    public void testSerializationAllValues() throws Exception {
        for (RevenueScheduleType revenueScheduleType : RevenueScheduleType.values()) {
            testSerializationCommon(revenueScheduleType, revenueScheduleType.name().toLowerCase());
        }
    }


    @Test(groups = "fast")
    public void testDeserializationAllValues() throws Exception {
        for (RevenueScheduleType revenueScheduleType : RevenueScheduleType.values()) {
            testDeserializationCommon(revenueScheduleType.name().toLowerCase(), revenueScheduleType);
        }
    }

    @Test(groups = "fast")
    public void testSerializationNullValue() throws Exception {
        Adjustment adjustment = TestUtils.createRandomAdjustment();
        adjustment.setRevenueScheduleType(null);
        String xml = xmlMapper.writeValueAsString(adjustment);
        assert(!xml.contains("<revenue_schedule_type>"));
    }

    @Test(groups = "fast")
    public void testDeserializationWhenElementNotPresent() throws Exception {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                      "</adjustment>";
        Adjustment adjustment = xmlMapper.readValue(xml, Adjustment.class);
        assertEquals(adjustment.getRevenueScheduleType(), null);
    }

    @Test(groups = "fast")
    public void testDeserializationWhenValueNotPresent() throws Exception {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                        "<revenue_schedule_type></revenue_schedule_type>\n" +
                                      "</adjustment>";
        Adjustment adjustment = xmlMapper.readValue(xml, Adjustment.class);
        assertEquals(adjustment.getRevenueScheduleType(), null);
    }

    @Test(groups = "fast", expectedExceptions = JsonMappingException.class)
    public void testDeserializationWhenValueUnknown() throws IOException {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                        "<revenue_schedule_type>covfefe</revenue_schedule_type>\n" +
                                      "</adjustment>";
        xmlMapper.readValue(xml, Adjustment.class);

    }

    private void testSerializationCommon(RevenueScheduleType revenueScheduleType, String expectedSerializedValue) throws Exception {
        Adjustment adjustment = TestUtils.createRandomAdjustment();
        adjustment.setRevenueScheduleType(revenueScheduleType);
        String xml = xmlMapper.writeValueAsString(adjustment);
        assert(xml.contains("<revenue_schedule_type>" + expectedSerializedValue + "</revenue_schedule_type>"));
    }

     private void testDeserializationCommon(String value, RevenueScheduleType expectedRevenueScheduleType) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                        "<revenue_schedule_type>" + value + "</revenue_schedule_type>\n" +
                                      "</adjustment>";

        Adjustment adjustment = xmlMapper.readValue(xml, Adjustment.class);
        assertEquals(adjustment.getRevenueScheduleType(), expectedRevenueScheduleType);
    }
}
