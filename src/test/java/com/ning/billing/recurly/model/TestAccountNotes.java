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

public class TestAccountNotes extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-account-notes
        final String accountNotesData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                    "<notes type=\"array\">\n" +
                                    "  <note>" +
                                    "    <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>" +
                                    "    <message>This is my second note</message>" +
                                    "    <created_at type=\"datetime\">2015-06-14T16:08:41Z</created_at>" +
                                    "  </note>" +
                                    "  <note>" +
                                    "    <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>" +
                                    "    <message>This is my first note</message>" +
                                    "    <created_at type=\"datetime\">2016-06-13T16:06:21Z</created_at>" +
                                    "  </note>" +
                                    "  <!-- Continued... -->" +
                                    "</notes>";

        final AccountNotes accountNotes = xmlMapper.readValue(accountNotesData, AccountNotes.class);
        Assert.assertEquals(accountNotes.size(), 2);

        final AccountNote note = accountNotes.get(0);
        Assert.assertEquals(note.getMessage(), "This is my second note");
        Assert.assertEquals(note.getCreatedAt(), new DateTime("2015-06-14T16:08:41Z"));
        final AccountNote note1 = accountNotes.get(1);
        Assert.assertEquals(note1.getMessage(), "This is my first note");
        Assert.assertEquals(note1.getCreatedAt(), new DateTime("2016-06-13T16:06:21Z"));
    }
}
