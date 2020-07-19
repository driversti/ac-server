package com.github.driversti.ac.domain.brand;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
class BrandFacade {

  private final BrandRepository brandRepository;
  private final BrandFactory brandFactory;
  private final BrandMapper brandMapper;

  public BrandAddResponse addBrand(BrandAddRequest request) {
    Brand entityToSave = brandFactory.fromAddRequest(request);
    Brand savedEntity = brandRepository.save(entityToSave);
    return brandMapper.toAddResponse(savedEntity);
  }
}
