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

package com.ning.billing.recurly;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPaginationUtils {

    @Test(groups = "fast")
    public void testParserNext() throws Exception {
        final String linkHeader = "<https://your-subdomain.recurly.com/v2/accounts?cursor=1304958672>; rel=\"next\"";
        final String[] links = PaginationUtils.getLinks(linkHeader);
        Assert.assertNull(links[0]);
        Assert.assertNull(links[1]);
        Assert.assertEquals(links[2], "https://your-subdomain.recurly.com/v2/accounts?cursor=1304958672");
    }

    @Test(groups = "fast")
    public void testParserAll() throws Exception {
        final String linkHeader = "<https://your-subdomain.recurly.com/v2/transactions>; rel=\"start\",\n" +
                                  "  <https://your-subdomain.recurly.com/v2/transactions?cursor=-1318344434>; rel=\"prev\"\n" +
                                  "  <https://your-subdomain.recurly.com/v2/transactions?cursor=1318388868>; rel=\"next\"";
        final String[] links = PaginationUtils.getLinks(linkHeader);
        Assert.assertEquals(links[0], "https://your-subdomain.recurly.com/v2/transactions");
        Assert.assertEquals(links[1], "https://your-subdomain.recurly.com/v2/transactions?cursor=-1318344434");
        Assert.assertEquals(links[2], "https://your-subdomain.recurly.com/v2/transactions?cursor=1318388868");
    }
}
