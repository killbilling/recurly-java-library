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

package com.ning.billing.recurly.model.jackson;

import java.io.IOException;

import javax.xml.namespace.QName;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.dataformat.xml.ser.XmlSerializerProvider;
import com.fasterxml.jackson.dataformat.xml.util.XmlRootNameLookup;

// Custom XmlSerializerProvider which delegates the writing of field names in array to
// the object serializer (RecurlyObjectsSerializer). The default implementation hardcodes them to "item".
public final class RecurlyXmlSerializerProvider extends XmlSerializerProvider {

    public RecurlyXmlSerializerProvider() {
        this(new XmlRootNameLookup());
    }

    public RecurlyXmlSerializerProvider(final XmlRootNameLookup rootNames) {
        super(rootNames);
    }

    public RecurlyXmlSerializerProvider(final XmlSerializerProvider src, final SerializationConfig config, final SerializerFactory f) {
        super(src, config, f);
    }

    @Override
    public DefaultSerializerProvider createInstance(final SerializationConfig config, final SerializerFactory jsf) {
        return new RecurlyXmlSerializerProvider(this, config, jsf);
    }

    @Override
    protected void _startRootArray(final ToXmlGenerator xgen, final QName rootName) throws IOException {
        xgen.writeStartObject();
    }
}
