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

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestRecurlyObject extends TestModelBase {

    @Test(groups = "fast")
    public void testNull() {
        Assert.assertEquals(null, RecurlyObject.booleanOrNull(null));
        Assert.assertEquals(null, RecurlyObject.dateTimeOrNull(null));
        Assert.assertEquals(null, RecurlyObject.integerOrNull(null));
        Assert.assertEquals(null, RecurlyObject.stringOrNull(null));

        for (final String nil : RecurlyObject.NIL_VAL) {
            final HashMap<String, String> nilMap = new HashMap<String, String>();
            nilMap.put(RecurlyObject.NIL_STR, nil);
            Assert.assertEquals(null, RecurlyObject.booleanOrNull(nilMap));
            Assert.assertEquals(null, RecurlyObject.dateTimeOrNull(nilMap));
            Assert.assertEquals(null, RecurlyObject.integerOrNull(nilMap));
            Assert.assertEquals(null, RecurlyObject.stringOrNull(nilMap));
        }

        final HashMap<String, String> nonNilMap = new HashMap<String, String>();
        nonNilMap.put("foo", "bar");
        Assert.assertNotNull(RecurlyObject.isNull(nonNilMap));
    }
}
