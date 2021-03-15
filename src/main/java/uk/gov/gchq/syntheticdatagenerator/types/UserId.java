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

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * A {@link UserId} uniquely identifies a User.
 */
public class UserId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * Constructs an empty {@link UserId}.
     */
    public UserId() {
        //no-args constructor needed for serialization only
    }

    /**
     * Copy constructor for a {@link UserId}.
     *
     * @param userId the {@link UserId} that will be copied.
     */
    UserId(final UserId userId) {
        requireNonNull(userId, "UserId to be cloned cannot be null");
        this.setId(userId.getId());
    }

    /**
     * Updates the id of the UserID
     *
     * @param id a non null String representing the id of the user
     * @return the UserId object
     */
    public UserId id(final String id) {
        this.setId(id);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        requireNonNull(id);
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserId)) {
            return false;
        }
        UserId userId = (UserId) o;
        return id.equals(userId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserId.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .toString();
    }
}
