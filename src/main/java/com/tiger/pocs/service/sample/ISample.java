package com.tiger.pocs.service.sample;


import com.tiger.rpocs.payload.PatchedSampleRequest;
import com.tiger.rpocs.payload.SampleFilter;
import com.tiger.rpocs.payload.SampleRequest;
import com.tiger.rpocs.payload.SampleResponse;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;


public interface ISample {

    Mono<SampleResponse> add(Mono<SampleRequest> request);

    Mono<SampleResponse> partialEdit(Long currentId, Mono<PatchedSampleRequest> request);

    Mono<SampleResponse> retrieve(Long currentId);

    Mono<Page<SampleResponse>> retrieveAll(Mono<SampleFilter> filter);

    void remove(Long currentId);
}
