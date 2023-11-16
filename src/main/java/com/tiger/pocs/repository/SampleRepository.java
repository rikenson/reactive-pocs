package com.tiger.pocs.repository;

import com.tiger.pocs.domain.entity.SampleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface SampleRepository extends ReactiveCrudRepository<SampleEntity, Long> {
    Flux<SampleEntity> findAllBy(Pageable pageable);
}
