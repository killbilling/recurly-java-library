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

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMeasuredUnits extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-measured-units
        final String measuredUnitsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                 "<measured_units type=\"array\">\n" +
                                "  <measured_unit href=\"https://your-subdomain.recurly.com/v2/measured_units/12345678\">\n" +
                                "    <id type=\"integer\">12345678</id>\n" +
                                "    <name>Ad Impressions</name>\n" +
                                "    <display_name>Impression</display_name>\n" +
                                "    <description>Number of ad impressions</description>\n" +
                                "  </measured_unit>\n" +
                                "  <measured_unit href=\"https://your-subdomain.recurly.com/v2/measured_units/12345679\">\n" +
                                "    <id type=\"integer\">12345679</id>\n" +
                                "    <name>Streaming Bandwidth</name>\n" +
                                "    <display_name>GB</display_name>\n" +
                                "    <description>Video steaming bandwidth measured in gigabytes</description>\n" +
                                "  </measured_unit>\n" +
                                "</measured_units>";

        final MeasuredUnits measuredUnits = xmlMapper.readValue(measuredUnitsData, MeasuredUnits.class);
        Assert.assertEquals(measuredUnits.size(), 2);

        final MeasuredUnit measuredUnit1 = measuredUnits.get(0);
        Assert.assertEquals(measuredUnit1.getId(), Long.valueOf(12345678));
        Assert.assertEquals(measuredUnit1.getName(), "Ad Impressions");
        Assert.assertEquals(measuredUnit1.getDisplayName(), "Impression");
        Assert.assertEquals(measuredUnit1.getDescription(), "Number of ad impressions");

        final MeasuredUnit measuredUnit2 = measuredUnits.get(1);
        Assert.assertEquals(measuredUnit2.getId(), Long.valueOf(12345679));
        Assert.assertEquals(measuredUnit2.getName(), "Streaming Bandwidth");
        Assert.assertEquals(measuredUnit2.getDisplayName(), "GB");
        Assert.assertEquals(measuredUnit2.getDescription(), "Video steaming bandwidth measured in gigabytes");

    }
}
