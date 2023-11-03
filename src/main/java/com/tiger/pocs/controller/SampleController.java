package com.tiger.pocs.controller;

import com.tiger.pocs.service.sample.ISample;
import com.tiger.rpocs.handler.SamplesApi;
import com.tiger.rpocs.payload.DeleteSampleResponse;
import com.tiger.rpocs.payload.PatchedSampleRequest;
import com.tiger.rpocs.payload.SampleRequest;
import com.tiger.rpocs.payload.SampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


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
    public Mono<ResponseEntity<Flux<SampleResponse>>> searchSamples(
            String status,
            String preferredField,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            ServerWebExchange exchange) {

        return Mono.just(ResponseEntity.ok(service.retrieveAll()));

    }
}
