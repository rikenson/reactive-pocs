package com.tiger.pocs.service.sample;


import com.tiger.rpocs.payload.PatchedSampleRequest;
import com.tiger.rpocs.payload.SampleRequest;
import com.tiger.rpocs.payload.SampleResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ISample {

    Mono<SampleResponse> add(Mono<SampleRequest> request);

    Mono<SampleResponse> partialEdit(Long currentId, Mono<PatchedSampleRequest> request);

    Mono<SampleResponse> retrieve(Long currentId);

    Flux<SampleResponse> retrieveAll();

    void remove(Long currentId);
}
