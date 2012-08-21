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

        HashMap<String, String> nilMap = new HashMap<String, String>();
        nilMap.put(RecurlyObject.NIL_STR, RecurlyObject.NIL_STR);
        Assert.assertEquals(null, RecurlyObject.booleanOrNull(nilMap));
        Assert.assertEquals(null, RecurlyObject.dateTimeOrNull(nilMap));
        Assert.assertEquals(null, RecurlyObject.integerOrNull(nilMap));
        Assert.assertEquals(null, RecurlyObject.stringOrNull(nilMap));
    }
}
