package com.ning.billing.recurly.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@XmlRootElement(name = "custom_field_definitions")
public class CustomFieldDefinitions extends RecurlyObjects<CustomFieldDefinition> {

    @XmlTransient
    public static final String CUSTOM_FIELD_DEFINITIONS_RESOURCE = "/custom_field_definitions";

    @XmlTransient
    private static final String PROPERTY_NAME = "custom_field_definition";

    public enum CustomFieldDefinitionRelatedType {
        ACCOUNT("account"),
        SUBSCRIPTION("subscription"),
        PRODUCT("product"),
        PLAN("plan"),
        CHARGE("charge");

        private final String relatedType;

        private CustomFieldDefinitionRelatedType(final String relatedType) {
            this.relatedType = relatedType;
        }

        public String getRelatedType() {
            return relatedType;
        }
    }

    @JsonSetter(value = PROPERTY_NAME)
    @Override
    public void setRecurlyObject(final CustomFieldDefinition value) {
        super.setRecurlyObject(value);
    }

    @JsonIgnore
    @Override
    public CustomFieldDefinitions getStart() {
        return getStart(CustomFieldDefinitions.class);
    }

    @JsonIgnore
    @Override
    public CustomFieldDefinitions getNext() {
        return getNext(CustomFieldDefinitions.class);
    }
}
