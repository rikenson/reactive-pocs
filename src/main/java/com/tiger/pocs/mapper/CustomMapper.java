package com.tiger.pocs.mapper;

import com.tiger.pocs.domain.entity.SampleEntity;
import com.tiger.rpocs.payload.PatchedSampleRequest;
import com.tiger.rpocs.payload.SampleRequest;
import com.tiger.rpocs.payload.SampleResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CustomMapper {
    SampleEntity requestToSampleEntity(SampleRequest request);

    SampleResponse entityToSampleResponse(SampleEntity entity);

    void mapPatchedProps(@MappingTarget SampleEntity entity, PatchedSampleRequest request);

}