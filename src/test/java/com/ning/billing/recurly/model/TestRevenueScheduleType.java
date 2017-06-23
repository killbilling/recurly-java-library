package com.ning.billing.recurly.model;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ning.billing.recurly.TestUtils;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class TestRevenueScheduleType extends TestModelBase {

    @Test(groups = "fast")
    public void testSerializationAllValues() throws Exception {
        for (RevenueScheduleType revenueScheduleType : RevenueScheduleType.values()) {
            testSerializationCommon(revenueScheduleType, revenueScheduleType.name().toLowerCase());
        }
    }


    @Test(groups = "fast")
    public void testDeserializationAllValues() throws Exception {
        for (RevenueScheduleType revenueScheduleType : RevenueScheduleType.values()) {
            testDeserializationCommon(revenueScheduleType.name().toLowerCase(), revenueScheduleType);
        }
    }

    @Test(groups = "fast")
    public void testSerializationNullValue() throws Exception {
        Adjustment adjustment = TestUtils.createRandomAdjustment();
        adjustment.setRevenueScheduleType(null);
        String xml = xmlMapper.writeValueAsString(adjustment);
        assert(!xml.contains("<revenue_schedule_type>"));
    }

    @Test(groups = "fast")
    public void testDeserializationWhenElementNotPresent() throws Exception {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                      "</adjustment>";
        Adjustment adjustment = xmlMapper.readValue(xml, Adjustment.class);
        assertEquals(adjustment.getRevenueScheduleType(), null);
    }

    @Test(groups = "fast")
    public void testDeserializationWhenValueNotPresent() throws Exception {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                        "<revenue_schedule_type></revenue_schedule_type>\n" +
                                      "</adjustment>";
        Adjustment adjustment = xmlMapper.readValue(xml, Adjustment.class);
        assertEquals(adjustment.getRevenueScheduleType(), null);
    }

    @Test(groups = "fast", expectedExceptions = InvalidFormatException.class)
    public void testDeserializationWhenValueUnknown() throws IOException {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                        "<revenue_schedule_type>covfefe</revenue_schedule_type>\n" +
                                      "</adjustment>";
        xmlMapper.readValue(xml, Adjustment.class);

    }

    private void testSerializationCommon(RevenueScheduleType revenueScheduleType, String expectedSerializedValue) throws Exception {
        Adjustment adjustment = TestUtils.createRandomAdjustment();
        adjustment.setRevenueScheduleType(revenueScheduleType);
        String xml = xmlMapper.writeValueAsString(adjustment);
        assert(xml.contains("<revenue_schedule_type>" + expectedSerializedValue + "</revenue_schedule_type>"));
    }

     private void testDeserializationCommon(String value, RevenueScheduleType expectedRevenueScheduleType) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                      "<adjustment type=\"credit\" href=\"https://api.recurly.com/v2/adjustments/626db120a84102b1809909071c701c60\">\n" +
                                        "<revenue_schedule_type>" + value + "</revenue_schedule_type>\n" +
                                      "</adjustment>";

        Adjustment adjustment = xmlMapper.readValue(xml, Adjustment.class);
        assertEquals(adjustment.getRevenueScheduleType(), expectedRevenueScheduleType);
    }
}
