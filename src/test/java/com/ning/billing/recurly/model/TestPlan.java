package com.ning.billing.recurly.model;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPlan extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See http://docs.recurly.com/api/plans
        final String planData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<plan href=\"https://api.recurly.com/v2/plans/gold\">\n" +
                "  <add_ons href=\"https://api.recurly.com/v2/plans/gold/add_ons\"/>\n" +
                "  <plan_code>gold</plan_code>\n" +
                "  <name>Gold plan</name>\n" +
                "  <description nil=\"nil\"></description>\n" +
                "  <success_url nil=\"nil\"></success_url>\n" +
                "  <cancel_url nil=\"nil\"></cancel_url>\n" +
                "  <display_donation_amounts type=\"boolean\">false</display_donation_amounts>\n" +
                "  <display_quantity type=\"boolean\">false</display_quantity>\n" +
                "  <display_phone_number type=\"boolean\">false</display_phone_number>\n" +
                "  <bypass_hosted_confirmation type=\"boolean\">false</bypass_hosted_confirmation>\n" +
                "  <unit_name>unit</unit_name>\n" +
                "  <payment_page_tos_link nil=\"nil\"></payment_page_tos_link>\n" +
                "  <plan_interval_length type=\"integer\">1</plan_interval_length>\n" +
                "  <plan_interval_unit>months</plan_interval_unit>\n" +
                "  <trial_interval_length type=\"integer\">0</trial_interval_length>\n" +
                "  <trial_interval_unit>days</trial_interval_unit>\n" +
                "  <accounting_code nil=\"nil\"></accounting_code>\n" +
                "  <created_at type=\"datetime\">2011-04-19T07:00:00Z</created_at>\n" +
                "  <unit_amount_in_cents>\n" +
                "    <USD type=\"integer\">1000</USD>\n" +
                "    <EUR type=\"integer\">800</EUR>\n" +
                "  </unit_amount_in_cents>\n" +
                "  <setup_fee_in_cents>\n" +
                "    <USD type=\"integer\">6000</USD>\n" +
                "    <EUR type=\"integer\">4500</EUR>\n" +
                "  </setup_fee_in_cents>\n" +
                "</plan>";

        final Plan plan = xmlMapper.readValue(planData, Plan.class);
        Assert.assertEquals(plan.getPlanCode(), "gold");
        Assert.assertEquals(plan.getName(), "Gold plan");
        Assert.assertEquals((int) plan.getPlanIntervalLength(), 1);
        Assert.assertEquals(plan.getPlanIntervalUnit(), "months");
        Assert.assertEquals((int) plan.getTrialIntervalLength(), 0);
        Assert.assertEquals(plan.getTrialIntervalUnit(), "days");
        Assert.assertFalse(plan.getDisplayDonationAmmounts());
        Assert.assertFalse(plan.getDisplayQuantityType());
        Assert.assertEquals(plan.getCreatedAt(), new DateTime("2011-04-19T07:00:00Z"));
        Assert.assertNull(plan.getDescription());
        Assert.assertNull(plan.getSuccessLink());
        Assert.assertNull(plan.getCancelLink());
        Assert.assertNull(plan.getAccountingCode());
    }
}
