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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plan")
public class Plan extends RecurlyObject {

    @XmlElement(name = "plan_code")
    private String planCode;

    @XmlElement(name = "name")
    private String name;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(final Object planCode) {
        this.planCode = stringOrNull(planCode);
    }

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
        sb.append("{planCode='").append(planCode).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Plan plan = (Plan) o;

        if (name != null ? !name.equals(plan.name) : plan.name != null) {
            return false;
        }
        if (planCode != null ? !planCode.equals(plan.planCode) : plan.planCode != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = planCode != null ? planCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
