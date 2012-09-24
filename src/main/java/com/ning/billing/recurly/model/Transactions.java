package com.ning.billing.recurly.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Kristian.foster@gmail.com
 */
@XmlRootElement(name = "transaction")
public class Transactions extends RecurlyObjects<Transaction> {

    @XmlTransient
    public static final String TRANSACTIONS_RESOURCE
            = "/transactions";

}
