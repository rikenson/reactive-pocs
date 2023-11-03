package com.tiger.pocs.paramResolver;

import com.tiger.rpocs.payload.SampleResponse;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static com.tiger.pocs.utils.Constants.*;


public class UpdatedSampleRequestParameterResolver implements ParameterResolver {
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == SampleResponse.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        var model = new SampleResponse();
        model.setCurrentId(1L);
        model.setName(SAMPLE_NAME);
        model.setDescription(SAMPLE_DESCRIPTION);
        model.setStatus(UPDATED_SAMPLE_STATUS);
        model.setStartDateTime(SAMPLE_START_DATE_TIME);
        model.setEndDateTime(SAMPLE_END_DATE_TIME);
        return model;
    }
}
