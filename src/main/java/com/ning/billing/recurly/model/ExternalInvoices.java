package com.ning.billing.recurly.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@XmlRootElement(name = "external_invoices")
public class ExternalInvoices extends RecurlyObjects<ExternalInvoice> {
  
  @XmlTransient
  public static final String EXTERNAL_INVOICES_RESOURCE = "/external_invoices";

  @XmlTransient
  public static final String PROPERTY_NAME = "external_invoice";

  @JsonSetter(value = PROPERTY_NAME)
  @Override
  public void setRecurlyObject(final ExternalInvoice value) {
      super.setRecurlyObject(value);
  }

  @JsonIgnore
  @Override
  public ExternalInvoices getStart() {
      return getStart(ExternalInvoices.class);
  }

  @JsonIgnore
  @Override
  public ExternalInvoices getNext() {
      return getNext(ExternalInvoices.class);
  }
}
