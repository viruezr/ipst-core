/**
 * Copyright (c) 2017, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.itesla_project.action.util

import com.google.auto.service.AutoService
import eu.itesla_project.action.dsl.spi.DslTaskExtension
import eu.itesla_project.contingency.tasks.ModificationTask

@AutoService(DslTaskExtension.class)
class PhaseShifterFixTapTaskExtension implements DslTaskExtension {
    @Override
    void addToSpec(MetaClass tasksSpecMetaClass, List<ModificationTask> tasks, Binding binding) {
        tasksSpecMetaClass.fixPhaseShifterTap = { String id, int position ->
            tasks.add(new PhaseShifterFixTapTask(id, position))
        }
    }
}
