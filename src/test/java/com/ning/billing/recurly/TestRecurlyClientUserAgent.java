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

import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "fast", description = "https://github.com/killbilling/recurly-java-library/pull/59")
public class TestRecurlyClientUserAgent {

    private RecurlyClient recurlyClient;

    @BeforeMethod(groups = "fast")
    public void setUp() throws Exception {
        recurlyClient = new RecurlyClient(UUID.randomUUID().toString(), "api");
    }

    @Test(groups = "fast")
    public void testNormalUserAgent() throws Exception {
        // In case of exception
        Assert.assertNotEquals(recurlyClient.getUserAgent(), "KillBill/0.0.0; 0.0.0");
        Assert.assertTrue(Pattern.compile("KillBill/0[.]\\d+[.]\\d; 1*\\d[.]\\d[.]*\\d*").matcher(recurlyClient.getUserAgent()).matches());
    }

    @Test(groups = "fast")
    public void testUnreleasedVersion() throws Exception {
        final Properties gitRepositoryState = new Properties();
        gitRepositoryState.put(RecurlyClient.GIT_COMMIT_ID_DESCRIBE_SHORT, "recurly-java-library-0.2.4-12");
        Assert.assertEquals(recurlyClient.getVersionFromGitRepositoryState(gitRepositoryState), "0.2.4");
    }

    @Test(groups = "fast")
    public void testReleasedVersion() throws Exception {
        final Properties gitRepositoryState = new Properties();
        gitRepositoryState.put(RecurlyClient.GIT_COMMIT_ID_DESCRIBE_SHORT, "recurly-java-library-0.2.5");
        Assert.assertEquals(recurlyClient.getVersionFromGitRepositoryState(gitRepositoryState), "0.2.5");
    }

    @Test(groups = "fast")
    public void testEmptyGitRepositoryState() throws Exception {
        final Properties gitRepositoryState = new Properties();
        Assert.assertNull(recurlyClient.getVersionFromGitRepositoryState(gitRepositoryState));
    }
}
