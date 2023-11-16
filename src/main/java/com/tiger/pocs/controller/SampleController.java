package com.tiger.pocs.controller;

import com.tiger.pocs.service.sample.ISample;
import com.tiger.rpocs.handler.SamplesApi;
import com.tiger.rpocs.payload.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;


@Controller
@RequiredArgsConstructor
public class SampleController implements SamplesApi {

    private final ISample service;

    @Override
    public Mono<ResponseEntity<SampleResponse>> addSample(
            Mono<SampleRequest> sampleRequest,
            ServerWebExchange exchange) {
        return service
                .add(sampleRequest)
                .map(sampleResponse -> new ResponseEntity<>(sampleResponse, HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<DeleteSampleResponse>> deleteSample(String id, ServerWebExchange exchange) {

        service.remove(Long.parseLong(id));
        var response = new DeleteSampleResponse();
        response.setCurrentId(Long.parseLong(id));
        return Mono
                .just(ResponseEntity.ok(response))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<SampleResponse>> patchSample(
            String id,
            Mono<PatchedSampleRequest> patchedSampleRequest,
            ServerWebExchange exchange) {
        return service
                .partialEdit(Long.parseLong(id), patchedSampleRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<SampleResponse>> searchCurrentSample(String id, ServerWebExchange exchange) {
        return service
                .retrieve(Long.parseLong(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Page<SampleResponse>>> searchSamples(Mono<SampleFilter> sampleFilter, ServerWebExchange exchange) {

        var sf = sampleFilter.map(sFilter -> {
            if (Objects.isNull(sFilter.getStartDateTime()))
                sFilter.setStartDateTime(LocalDateTime.now().minusYears(10));
            if (Objects.isNull(sFilter.getEndDateTime()))
                sFilter.setStartDateTime(LocalDateTime.now());
            return sFilter;
        });

        return service.retrieveAll(sf)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.ok().build());


    }
}
