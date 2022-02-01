/*
 * Copyright 2018-2021 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.syntheticdatagenerator.types;

import net.datafaker.Faker;

import java.util.Random;
import java.util.StringJoiner;

public class WorkLocation {
    private WorkLocationName workLocationName;
    private Address address;

    public static WorkLocation generate(final Faker faker, final Random random) {
        WorkLocation workLocation = new WorkLocation();
        workLocation.setAddress(Address.generate(faker, random));
        workLocation.setWorkLocationName(WorkLocationName.generate(random));
        return workLocation;
    }

    public WorkLocationName getWorkLocationName() {
        return workLocationName;
    }

    public void setWorkLocationName(final WorkLocationName workLocationName) {
        this.workLocationName = workLocationName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WorkLocation.class.getSimpleName() + "[", "]")
                .add("workLocationName=" + workLocationName)
                .add("address=" + address)
                .toString();
    }
}


