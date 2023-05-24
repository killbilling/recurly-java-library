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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.ning.billing.recurly.RecurlyClient;
import com.ning.billing.recurly.model.jackson.RecurlyObjectsSerializer;
import com.ning.billing.recurly.model.jackson.RecurlyXmlSerializerProvider;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.stream.XMLInputFactory;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RecurlyObject {

    @XmlTransient
    private RecurlyClient recurlyClient;

    @XmlTransient
    protected String href;

    public static final String NIL_STR = "nil";
    public static final List<String> NIL_VAL = Arrays.asList("nil", "true");

    // See https://github.com/killbilling/recurly-java-library/issues/4 for why
    // @JsonIgnore is required here and @XmlTransient is not enough
    @JsonIgnore
    public String getHref() {
        return href;
    }

    @JsonProperty
    public void setHref(final Object href) {
        this.href = stringOrNull(href);
    }

    public static XmlMapper newXmlMapper() {
        final XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        final XmlMapper xmlMapper = new XmlMapper(xmlInputFactory);
        xmlMapper.setSerializerProvider(new RecurlyXmlSerializerProvider());
        final AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
        final AnnotationIntrospector secondary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        final AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
        xmlMapper.setAnnotationIntrospector(pair);
        xmlMapper.registerModule(new JodaModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        xmlMapper.registerModule(new JaxbAnnotationModule());

        final SimpleModule m = new SimpleModule("module", new Version(1, 0, 0, null, null, null));
        m.addSerializer(Accounts.class, new RecurlyObjectsSerializer<Accounts, Account>(Accounts.class, "account"));
        m.addSerializer(AddOns.class, new RecurlyObjectsSerializer<AddOns, AddOn>(AddOns.class, "add_on"));
        m.addSerializer(Adjustments.class, new RecurlyObjectsSerializer<Adjustments, Adjustment>(Adjustments.class, "adjustment"));
        m.addSerializer(Coupons.class, new RecurlyObjectsSerializer<Coupons, Coupon>(Coupons.class, "coupon"));
        m.addSerializer(CustomFields.class, new RecurlyObjectsSerializer<CustomFields, CustomField>(CustomFields.class, "custom_field"));
        m.addSerializer(Invoices.class, new RecurlyObjectsSerializer<Invoices, Invoice>(Invoices.class, "invoice"));
        m.addSerializer(Plans.class, new RecurlyObjectsSerializer<Plans, Plan>(Plans.class, "plan"));
        m.addSerializer(PlanRampIntervals.class, new RecurlyObjectsSerializer<PlanRampIntervals, PlanRampInterval>(PlanRampIntervals.class, "ramp_interval"));
        m.addSerializer(SubscriptionRampIntervals.class, new RecurlyObjectsSerializer<SubscriptionRampIntervals, SubscriptionRampInterval>(SubscriptionRampIntervals.class, "ramp_interval"));
        m.addSerializer(RecurlyErrors.class, new RecurlyObjectsSerializer<RecurlyErrors, RecurlyError>(RecurlyErrors.class, "error"));
        m.addSerializer(ShippingAddresses.class, new RecurlyObjectsSerializer<ShippingAddresses, ShippingAddress>(ShippingAddresses.class, "shipping_address"));
        m.addSerializer(ShippingFees.class, new RecurlyObjectsSerializer<ShippingFees, ShippingFee>(ShippingFees.class, "shipping_fee"));
        m.addSerializer(SubscriptionAddOns.class, new RecurlyObjectsSerializer<SubscriptionAddOns, SubscriptionAddOn>(SubscriptionAddOns.class, "subscription_add_on"));
        m.addSerializer(Subscriptions.class, new RecurlyObjectsSerializer<Subscriptions, Subscription>(Subscriptions.class, "subscription"));
        m.addSerializer(Tiers.class, new RecurlyObjectsSerializer<Tiers,Tier>(Tiers.class, "tier"));
        m.addSerializer(AddonPercentageTiers.class, new RecurlyObjectsSerializer<AddonPercentageTiers,AddonPercentageTier>(AddonPercentageTiers.class, "tier"));
        m.addSerializer(CurrencyPercentageTiers.class, new RecurlyObjectsSerializer<CurrencyPercentageTiers,CurrencyPercentageTier>(CurrencyPercentageTiers.class, "percentage_tier"));
        m.addSerializer(PercentageTiers.class, new RecurlyObjectsSerializer<PercentageTiers,PercentageTier>(PercentageTiers.class, "percentage_tier"));
        m.addSerializer(Transactions.class, new RecurlyObjectsSerializer<Transactions, Transaction>(Transactions.class, "transaction"));
        m.addSerializer(Usages.class, new RecurlyObjectsSerializer<Usages, Usage>(Usages.class, "usage"));
        m.addSerializer(ExternalProductReferences.class, new RecurlyObjectsSerializer<ExternalProductReferences, ExternalProductReference>(ExternalProductReferences.class, "external_product_reference"));
        xmlMapper.registerModule(m);

        return xmlMapper;
    }

    public static XmlMapper sharedXmlMapper() {
        return XmlMapperHolder.xmlMapper;
    }

    public static Boolean booleanOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        // Booleans are represented as objects (e.g. <display_quantity type="boolean">false</display_quantity>), which Jackson
        // will interpret as an Object (Map), not Booleans.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() == 2 && "boolean".equalsIgnoreCase((String) map.get("type"))) {
                return Boolean.valueOf((String) map.get(""));
            }
        }

        return Boolean.valueOf(object.toString());
    }

    public static String stringOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        return object.toString().trim();
    }

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E enumOrNull(Class<E> enumClass, @Nullable final Object object, final Boolean upCase) {
        if (isNull(object)) {
            return null;
        } else if (enumClass.isAssignableFrom(object.getClass())) {
            return (E) object;
        }

        String value =  object.toString().trim();

        if (upCase) {
            value = value.toUpperCase();
        }

        return (E) Enum.valueOf(enumClass, value);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E enumOrNull(Class<E> enumClass, @Nullable final Object object) {
        return enumOrNull(enumClass, object, false);
    }

    public static Integer integerOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        // Integers are represented as objects (e.g. <year type="integer">2015</year>), which Jackson
        // will interpret as an Object (Map), not Integers.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() == 2 && "integer".equalsIgnoreCase((String) map.get("type"))) {
                return Integer.valueOf((String) map.get(""));
            }
        }

        return Integer.valueOf(object.toString());
    }

    public static Long longOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        // Ids are represented as objects (e.g. <id type="integer">1988596967980562362</id>), which Jackson
        // will interpret as an Object (Map), not Longs.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() == 2 && "integer".equalsIgnoreCase((String) map.get("type"))) {
                return Long.valueOf((String) map.get(""));
            }
        }

        return Long.valueOf(object.toString());
    }

    public static BigDecimal bigDecimalOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        // BigDecimals are represented as objects (e.g. <tax_rate type="float">0.0875</tax_rate>), which Jackson
        // will interpret as an Object (Map), not Longs.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() == 2 && "float".equalsIgnoreCase((String) map.get("type"))) {
                return new BigDecimal((String) map.get(""));
            }
        }

        return new BigDecimal(object.toString());
    }

    public static DateTime dateTimeOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        // DateTimes are represented as objects (e.g. <created_at type="dateTime">2011-04-19T07:00:00Z</created_at>), which Jackson
        // will interpret as an Object (Map), not DateTimes.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() == 2 && "dateTime".equalsIgnoreCase((String) map.get("type"))) {
                return new DateTime(map.get(""));
            }
        }

        return new DateTime(object.toString());
    }

    public static boolean isNull(@Nullable final Object object) {
        if (object == null) {
            return true;
        }

        // Hack to work around Recurly output for nil values: the response will contain
        // an element with a nil attribute (e.g. <city nil="nil"></city> or <username nil="true"></username>) which Jackson will
        // interpret as an Object (Map), not a String.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() >= 1 && map.get(NIL_STR) != null && NIL_VAL.contains(map.get(NIL_STR).toString())) {
                return true;
            }
        }

        return false;
    }

    <T extends RecurlyObject> T fetch(final T object, final Class<T> clazz) {
        if (object.getHref() == null || recurlyClient == null) {
            return object;
        }
        return recurlyClient.doGETWithFullURL(clazz, object.getHref());
    }

    public void setRecurlyClient(final RecurlyClient recurlyClient) {
        this.recurlyClient = recurlyClient;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return this.hashCode() == o.hashCode();
    }

    /**
     * Holder for the shared {@link XmlMapper}. Not putting it directly under {@link RecurlyObject}
     * to maker it (sort of) lazy.
     */
    private static class XmlMapperHolder {

        private static final XmlMapper xmlMapper = newXmlMapper();

    }
}
