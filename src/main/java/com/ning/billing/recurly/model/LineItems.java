package com.ning.billing.recurly.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@XmlRootElement(name = "line_items")
public class LineItems extends RecurlyObjects<ExternalCharge> {
  
  @XmlTransient
  public static final String PROPERTY_NAME = "external_charge";

  @JsonSetter(value = PROPERTY_NAME)
  @Override
  public void setRecurlyObject(final ExternalCharge value) {
      super.setRecurlyObject(value);
  }

  @JsonIgnore
  @Override
  public LineItems getStart() {
      return getStart(LineItems.class);
  }

  @JsonIgnore
  @Override
  public LineItems getNext() {
      return getNext(LineItems.class);
  }
}
