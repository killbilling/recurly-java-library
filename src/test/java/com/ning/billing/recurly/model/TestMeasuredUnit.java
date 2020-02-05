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
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestMeasuredUnit extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-measured-units
        final String measuredUnitData =
                                " <measured_unit href=\"https://your-subdomain.recurly.com/v2/measured_units/1234567890\">\n" +
                                    " <id type=\"integer\">1234567890</id>" +
                                    "  <name>Streaming Bandwidth</name>\n" +
                                    "  <display_name>GB</display_name>\n" +
                                    "  <description>Video steaming bandwidth measured in gigabytes</description>\n" +
                                "  </measured_unit>";

        final MeasuredUnit measuredUnit = xmlMapper.readValue(measuredUnitData, MeasuredUnit.class);
        Assert.assertEquals(measuredUnit.getId(), Long.valueOf(1234567890));
        Assert.assertEquals(measuredUnit.getName(), "Streaming Bandwidth");
        Assert.assertEquals(measuredUnit.getDisplayName(), "GB");
        Assert.assertEquals(measuredUnit.getDescription(), "Video steaming bandwidth measured in gigabytes");
    }


    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create MeasuredUnit of the same value but difference references
        MeasuredUnit measuredUnit = TestUtils.createRandomMeasuredUnit(0);
        MeasuredUnit otherMeasuredUnit = TestUtils.createRandomMeasuredUnit(0);

        assertNotEquals(System.identityHashCode(measuredUnit), System.identityHashCode(otherMeasuredUnit));
        assertEquals(measuredUnit.hashCode(), otherMeasuredUnit.hashCode());
        assertEquals(measuredUnit, otherMeasuredUnit);
    }
}
