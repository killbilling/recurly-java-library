/*
 * Copyright 2018 The Billing Project, LLC
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ItemCodesSerializer extends StdSerializer<ItemCodes> {

    public ItemCodesSerializer() {
        this(null);
    }

    public ItemCodesSerializer(final Class<ItemCodes> t) {
        super(t);
    }

    @Override
    public void serialize(final ItemCodes value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        for (final ItemCode itemCode : value) {
            jgen.writeObject(itemCode);
        }
    }
}
