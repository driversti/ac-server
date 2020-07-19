package com.github.driversti.ac.domain.brand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class BrandFactory {

  Brand fromAddRequest(BrandAddRequest addRequest) {
    return Brand.builder()
        .name(addRequest.getName())
        .founded(addRequest.getFounded())
        .defunct(addRequest.getDefunct())
        .build();
  }
}
