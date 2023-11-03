package com.tiger.pocs.service.sample;

import com.tiger.pocs.domain.entity.SampleEntity;
import com.tiger.pocs.mapper.CustomMapper;
import com.tiger.pocs.repository.SampleRepository;
import com.tiger.rpocs.payload.PatchedSampleRequest;
import com.tiger.rpocs.payload.SampleRequest;
import com.tiger.rpocs.payload.SampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SampleService implements ISample {

    private final CustomMapper mapper;
    private final SampleRepository repository;

    @Override
    public Mono<SampleResponse> add(Mono<SampleRequest> request) {
        return request
                .flatMap(sampleRequest -> repository.save(mapper.requestToSampleEntity(sampleRequest)))
                .map(mapper::entityToSampleResponse);
    }

    @Override
    public Mono<SampleResponse> partialEdit(Long currentId, Mono<PatchedSampleRequest> request) {

        return repository.findById(currentId)
                .flatMap(toEntity(request))
                .flatMap(repository::save)
                .map(mapper::entityToSampleResponse);
    }

    @Override
    public Mono<SampleResponse> retrieve(Long currentId) {
        return repository
                .findById(currentId)
                .map(mapper::entityToSampleResponse);
    }

    @Override
    public Flux<SampleResponse> retrieveAll() {
        return repository
                .findAll()
                .map(mapper::entityToSampleResponse);
    }
    
    @Override
    public void remove(Long currentId) {
        repository
                .findById(currentId)
                .flatMap(sample -> repository.deleteById(sample.getCurrentId()))
                .subscribe();
    }

    private Function<SampleEntity, Mono<? extends SampleEntity>> toEntity(Mono<PatchedSampleRequest> request) {
        return entity -> request.map(toBeUpdated -> {
            mapper.mapPatchedProps(entity, toBeUpdated);
            return entity;
        });
    }

}
