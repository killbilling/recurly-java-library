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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.ning.billing.recurly.model.RecurlyObject;
import com.ning.billing.recurly.model.RecurlyObjects;

import javax.xml.namespace.QName;
import java.io.IOException;

public class RecurlyObjectsSerializer<T extends RecurlyObjects<U>, U extends RecurlyObject> extends StdSerializer<T> {

    private final String elementName;

    public RecurlyObjectsSerializer(final Class<T> recurlyObjectsClassName, final String elementName) {
        super(recurlyObjectsClassName);
        this.elementName = elementName;
    }

    @Override
    public void serialize(final T values, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
        if (values.isEmpty()) {
            jgen.writeStartArray();
            jgen.writeEndArray();
            return;
        }

        final ToXmlGenerator xmlgen = (ToXmlGenerator) jgen;
        // Nested RecurlyObjects
        JsonWriteContext jsonWriteContext = ((JsonWriteContext) xmlgen.getOutputContext());
        jsonWriteContext.writeFieldName(elementName);
        boolean firstValue = true;
        for (final U value : values) {
            if (firstValue) {
                xmlgen.setNextName(new QName(null, elementName));
            } else {
                xmlgen.writeFieldName(elementName);
            }
            firstValue = false;

            xmlgen.writeObject(value);
        }
    }
}
