package com.tiger.pocs.repository;

import com.tiger.pocs.domain.entity.SampleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface SampleRepository extends ReactiveCrudRepository<SampleEntity, Long> {
}
