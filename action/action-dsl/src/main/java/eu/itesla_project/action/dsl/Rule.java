/**
 * Copyright (c) 2017, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.itesla_project.action.dsl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
public class Rule {

    private final String id;

    private String description;

    private final Condition condition;

    private final int life;

    private final List<String> actions;

    public Rule(String id, Condition condition, int life, List<String> actions) {
        if (life < 0) {
            throw new IllegalArgumentException("Invalid life value, has to be >= 0");
        }
        this.id = Objects.requireNonNull(id);
        this.condition = Objects.requireNonNull(condition);
        this.life = life;
        this.actions = Objects.requireNonNull(actions);
    }

    public Rule(String id, Condition condition, int life, String... actions) {
        this(id, condition, life, Arrays.asList(actions));
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Condition getCondition() {
        return condition;
    }

    public int getLife() {
        return life;
    }

    public List<String> getActions() {
        return actions;
    }
}
