package com.ning.billing.recurly.model;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "custom_field_definition")
public class CustomFieldDefinition extends RecurlyObject {

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "related_type")
    private String relatedType;

    @XmlElement(name = "user_access")
    private String userAccess;

    @XmlElement(name = "display_name")
    private String displayName;

    @XmlElement(name = "tooltip")
    private String tooltip;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRelatedType() {
        return relatedType;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTooltip() {
        return tooltip;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomFieldDefinition{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", relatedType=").append(relatedType);
        sb.append(", userAccess=").append(userAccess);
        sb.append(", displayName=").append(displayName);
        sb.append(", tooltip=").append(tooltip);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CustomFieldDefinition definition = (CustomFieldDefinition) o;

        if (id != null ? !id.equals(definition.id) : definition.id != null) {
            return false;
        }
        if (name != null ? !name.equals(definition.name) : definition.name != null) {
            return false;
        }
        if (relatedType != null ? !relatedType.equals(definition.relatedType) : definition.relatedType != null) {
            return false;
        }
        if (userAccess != null ? !userAccess.equals(definition.userAccess) : definition.userAccess != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(definition.displayName) : definition.displayName != null) {
            return false;
        }
        if (tooltip != null ? !tooltip.equals(definition.tooltip) : definition.tooltip != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(definition.updatedAt) != 0 : definition.updatedAt != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(definition.updatedAt) != 0 : definition.createdAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                id,
                name,
                relatedType,
                userAccess,
                displayName,
                tooltip,
                createdAt,
                updatedAt
        );
    }
}
