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
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestCustomField extends TestModelBase {
    
    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create addresses of the same value but difference references
        CustomField field = TestUtils.createRandomCustomField("field_name", 0);
        CustomField otherField = TestUtils.createRandomCustomField("field_name", 0);

        assertNotEquals(System.identityHashCode(field), System.identityHashCode(otherField));
        assertEquals(field.hashCode(), otherField.hashCode());
        assertEquals(field, otherField);
    }

}
