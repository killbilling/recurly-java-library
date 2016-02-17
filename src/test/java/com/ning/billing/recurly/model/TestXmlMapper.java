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

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ning.billing.recurly.model.jackson.RecurlyXmlSerializerProvider;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class TestXmlMapper extends TestModelBase {

    @Test(groups = "fast", description = "See https://github.com/killbilling/recurly-java-library/issues/21")
    public void testEmptyArrays() throws Exception {
        final Subscriptions subscriptions = xmlMapper.readValue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                                "  <subscriptions type=\"array\">\n" +
                                                                "  </subscriptions>", Subscriptions.class);
        Assert.assertEquals(subscriptions.size(), 0);
    }

    public class ValuesSerializer extends StdSerializer<Values> {

        protected ValuesSerializer() {
            super(Values.class);
        }

        @Override
        public void serialize(final Values values, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
            for (final String value : values) {
                jgen.writeFieldName("value");
                jgen.writeString(value);
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonRootName("values")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private static final class Values extends ArrayList<String> {

        // Cannot use @JsonAnySetter because the value passed to the setter
        // won't be fully deserialized (e.g. Map instead of POJO)
        @JsonSetter(value = "value")
        private void setHack(final String value) {
            add(value);
        }
    }

    @Test(groups = "fast", description = "See https://github.com/FasterXML/jackson-dataformat-xml/issues/76")
    public void testCollection() throws Exception {
        final XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializerProvider(new RecurlyXmlSerializerProvider());
        final SimpleModule m = new SimpleModule("module", new Version(1, 0, 0, null, null, null));
        m.addSerializer(Values.class, new ValuesSerializer());
        xmlMapper.registerModule(m);

        final Values values = xmlMapper.readValue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                  "  <values type=\"array\">\n" +
                                                  "    <value>Hi!</value>" +
                                                  "    <value>Salut!</value>" +
                                                  "  </values>",
                                                  Values.class);
        Assert.assertEquals(values.size(), 2, values.toString());
        Assert.assertEquals(values.get(0), "Hi!");
        Assert.assertEquals(values.get(1), "Salut!");

        // Test we can re-serialize
        final String valueAsString = xmlMapper.writeValueAsString(values);
        final Values values2 = xmlMapper.readValue(valueAsString, Values.class);
        Assert.assertEquals(values2, values, valueAsString);
    }
}
