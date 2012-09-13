package com.ning.billing.recurly.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "subscriptions")
public class Subscriptions extends RecurlyObjects<Subscription> {
  
    @XmlTransient
    public static final String SUBSCRIPTION_RESOURCE = "/subscriptions";
}
