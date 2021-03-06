/**
 * Copyright (c) 2017, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.itesla_project.security.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import eu.itesla_project.commons.json.JsonUtil;
import eu.itesla_project.security.LimitViolation;

import java.io.IOException;

/**
 * @author Mathieu Bague <mathieu.bague@rte-france.com>
 */
public class LimitViolationSerializer extends StdSerializer<LimitViolation> {

    public LimitViolationSerializer() {
        super(LimitViolation.class);
    }

    @Override
    public void serialize(LimitViolation limitViolation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("subjectId", limitViolation.getSubjectId());
        jsonGenerator.writeStringField("limitType", limitViolation.getLimitType().name());
        JsonUtil.writeOptionalStringField(jsonGenerator, "limitName", limitViolation.getLimitName());
        jsonGenerator.writeNumberField("limit", limitViolation.getLimit());
        jsonGenerator.writeNumberField("limitReduction", limitViolation.getLimitReduction());
        jsonGenerator.writeNumberField("value", limitViolation.getValue());
        JsonUtil.writeOptionalEnumField(jsonGenerator, "country", limitViolation.getCountry());
        JsonUtil.writeOptionalFloatField(jsonGenerator, "baseVoltage", limitViolation.getBaseVoltage());

        jsonGenerator.writeEndObject();
    }
}
