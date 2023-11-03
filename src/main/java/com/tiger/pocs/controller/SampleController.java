package com.tiger.pocs.controller;

import com.tiger.pocs.service.sample.ISample;
import com.tiger.rpocs.handler.SamplesApi;
import com.tiger.rpocs.payload.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Controller
public class SampleController implements SamplesApi {

    private final ISample service;

    public SampleController(ISample service) {
        this.service = service;
    }

//    @PostMapping
//    public ResponseEntity<SampleResponse> samplePost(@RequestBody @Validated SampleRequest request) {
//        return new ResponseEntity<>(service.add(request), HttpStatus.CREATED);
//    }
//
//    @PutMapping("{uuid}")
//    public ResponseEntity<SampleResponse> sampleEdit(
//            @RequestBody @Validated SampleResponse request,
//            @PathVariable("uuid") UUID uuid) {
//        return ResponseEntity.ok(service.edit(request, uuid));
//    }
//
//    @GetMapping("{uuid}")
//    public ResponseEntity<SampleResponse> sampleRetrieve(@PathVariable("uuid") UUID uuid) {
//        return ResponseEntity.ok(service.retrieve(uuid));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<SampleResponse>> sampleRetrieveAll() {
//        return ResponseEntity.ok(service.retrieveAll());
//    }
//
//    @GetMapping("/by-filter")
//    public ResponseEntity<List<SampleResponse>> sampleRetrieveByFilter(
//            @RequestParam("name") String name,
//            @RequestParam("status") String status,
//            @RequestParam("startDateTime") LocalDateTime startDateTime,
//            @RequestParam("endDateTime") LocalDateTime endDateTime,
//            @RequestParam("preferredField") String preferredField) {
//        SampleFilter filter = SampleFilter
//                .builder()
//                .name(name)
//                .status(Status.valueOf(status))
//                .startDateTime(startDateTime)
//                .endDateTime(endDateTime)
//                .preferredField(preferredField)
//                .build();
//        return ResponseEntity.ok(service.retrieveByCriteria(filter));
//    }
//
//    @DeleteMapping("{uuid}")
//    public ResponseEntity<Void> remove(@PathVariable("uuid") UUID uuid) {
//        service.remove(uuid);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    @Override
    public Mono<ResponseEntity<SampleResponse>> addSample(
            Mono<SampleRequest> sampleRequest,
            ServerWebExchange exchange) {
        return service
                .add(sampleRequest)
                .map(sampleResponse -> new ResponseEntity<>(sampleResponse, HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<DeleteSampleResponse>> deleteSample(
            String id,
            ServerWebExchange exchange) {
        service.remove(Long.parseLong(id));
        return Mono
                .just(ResponseEntity
                        .ok(new DeleteSampleResponse(Long.parseLong(id), "Element removed successfully")));
    }

    @Override
    public Mono<ResponseEntity<SampleResponse>> patchSample(
            String id,
            Mono<PatchedSampleRequest> patchedSampleRequest,
            ServerWebExchange exchange) {
        return service
                .partialEdit(Long.parseLong(id), patchedSampleRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<SampleResponse>> searchCurrentSample(
            String id,
            ServerWebExchange exchange) {
        return service
                .retrieve(Long.parseLong(id))
                .map(ResponseEntity::ok);
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

    @Override
    public Mono<ResponseEntity<SampleResponse>> updateSample(
            String id,
            Mono<UpdatedSampleRequest> updatedSampleRequest,
            ServerWebExchange exchange) {
        return SamplesApi.super.updateSample(id, updatedSampleRequest, exchange);
    }
}
