package com.tiger.pocs.service.sample;

import com.tiger.pocs.domain.entity.SampleEntity;
import com.tiger.pocs.mapper.CustomMapper;
import com.tiger.pocs.repository.SampleRepository;
import com.tiger.rpocs.payload.PatchedSampleRequest;
import com.tiger.rpocs.payload.SampleFilter;
import com.tiger.rpocs.payload.SampleRequest;
import com.tiger.rpocs.payload.SampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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
    public Mono<Page<SampleResponse>> retrieveAll(Mono<SampleFilter> filter) {
        return filter
                .map(item -> PageRequest
                        .of(item.getPage(), item.getSize())
                        .withSort(Sort.by(item.getPreferredField()).ascending()))
                .map(this::getPageMono).flatMap(pageSampleResponse -> pageSampleResponse);
    }

    @Override
    public void remove(Long currentId) {
        repository
                .findById(currentId)
                .flatMap(sample -> repository.deleteById(sample.getCurrentId()))
                .subscribe();
    }

    private Mono<PageImpl<SampleResponse>> getPageMono(PageRequest pageRequest) {
        return repository.findAllBy(pageRequest)
                .map(mapper::entityToSampleResponse)
                .collectList()
                .zipWith(repository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }

    private Function<SampleEntity, Mono<? extends SampleEntity>> toEntity(Mono<PatchedSampleRequest> request) {
        return entity -> request.map(toBeUpdated -> {
            mapper.mapPatchedProps(entity, toBeUpdated);
            return entity;
        });
    }

}
