/*
 * Copyright 2010-2012 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

@XmlRootElement(name = "plans")
public class Plans extends RecurlyObject {
    @XmlTransient
    public static final String PLANS_RESOURCE = "/plans";
    
    @XmlElement(name = "plan", type = SimplePlan.class)
    private List<SimplePlan> plans;

    public List<SimplePlan> getPlans() {
        return this.plans;
    }

    public void setPlans(final List<SimplePlan> plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Plans [");
        sb.append(getPlans().toString());
        for (SimplePlan p : getPlans()) {
             sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
             if (null != p) {
                 sb.append(p.toString());
                 sb.append("\n");
             }
             sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
        sb.append(']');
        return sb.toString();
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    protected static class SimplePlan {

        public SimplePlan() {
        }

        // public SimplePlan(String data) {
        //     this.data = data;
        // }

        private String data;

        @XmlElement(name = "name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(final Object name) {
            this.name = stringOrNull(name);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("Plan");
            sb.append("{name='").append(name).append('\'');
            sb.append(", data='").append(data).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
