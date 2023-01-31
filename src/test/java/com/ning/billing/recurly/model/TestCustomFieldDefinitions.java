package com.ning.billing.recurly.model;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCustomFieldDefinitions extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String customFieldDefinitionsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                           "<custom_field_definitions type=\"array\">\n" +
                                           "  <custom_field_definition type=\"credit\" href=\"https://api.recurly.com/v2/custom_field_definitions/626db120a84102b1809909071c701c60\">\n" +
                                           "    <id>626db120a84102b1809909071c701c60</id>\n" +
                                           "    <related_type>charge</related_type>\n" +
                                           "    <name>size</name>\n" +
                                           "    <user_access>writable</user_access>\n" +
                                           "    <display_name>Size</display_name>\n" +
                                           "    <tooltip></tooltip>\n" +
                                           "    <created_at type=\"dateTime\">2023-01-31T03:30:00Z</created_at>\n" +
                                           "    <updated_at type=\"dateTime\">2023-01-31T03:30:00Z</updated_at>\n" +
                                           "    <deleted_at nil=\"nil\"></deleted_at>\n" +
                                           "  </custom_field_definition>\n" +
                                           "  <!-- Continued... -->\n" +
                                           "</custom_field_definitions>";

        final CustomFieldDefinitions customFieldDefinitions = xmlMapper.readValue(customFieldDefinitionsData, CustomFieldDefinitions.class);
        Assert.assertEquals(customFieldDefinitions.size(), 1);

        final CustomFieldDefinition customFieldDefinition = customFieldDefinitions.get(0);

        Assert.assertEquals(customFieldDefinition.getId(), "626db120a84102b1809909071c701c60");
        Assert.assertEquals(customFieldDefinition.getName(), "size");
        Assert.assertEquals(customFieldDefinition.getRelatedType(), "charge");
        Assert.assertEquals(customFieldDefinition.getUserAccess(), "writable");
        Assert.assertEquals(customFieldDefinition.getDisplayName(), "Size");
        Assert.assertNull(customFieldDefinition.getTooltip());
        Assert.assertEquals(customFieldDefinition.getCreatedAt(), new DateTime("2023-01-31T03:30:00Z"));
        Assert.assertEquals(customFieldDefinition.getUpdatedAt(), new DateTime("2023-01-31T03:30:00Z"));
    }
}
