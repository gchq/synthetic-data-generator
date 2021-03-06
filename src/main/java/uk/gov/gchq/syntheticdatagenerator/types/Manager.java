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

import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

public class Manager {
    private String uid;
    private Manager[] managers;
    private String managerType;

    public static Manager[] generateMany(final Random random, final int chain) {
        return new Manager[]{
                generateRecursive(random, chain, "Line Manager"),
                generateRecursive(random, chain, "Task Manager"),
                generateRecursive(random, chain, "Career Manager")
        };
    }


    public static Manager generateRecursive(final Random random, final int chain, final String managerType) {
        Manager manager = Manager.generate(random, managerType);
        if (chain <= 1) {
            manager.setManager(null);
        } else {
            manager.setManager(Manager.generateMany(random, chain - 1));
        }
        return manager;
    }

    public static Manager generate(final Random random, final String managerType) {
        Manager manager = new Manager();
        manager.setUid(Employee.generateUID(random));
        manager.setManagerType(managerType);

        return manager;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(final String uid) {
        requireNonNull(uid);
        this.uid = uid;
    }

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(final String managerType) {
        requireNonNull(managerType);
        this.managerType = managerType;
    }

    public Manager[] getManager() {
        if (null == managers) {
            return null;
        } else {
            return managers.clone();
        }
    }

    public void setManager(final Manager[] managers) {
        if (null == managers) {
            this.managers = null;
        } else {
            this.managers = managers.clone();
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Manager.class.getSimpleName() + "[", "]")
                .add("uid=" + uid)
                .add("manager=" + Arrays.toString(managers))
                .add("managerType='" + managerType + "'")
                .toString();
    }
}
