package com.github.driversti.ac.domain.brand;

import static java.lang.String.format;

import com.github.driversti.ac.errorhandling.exceptions.ConflictException;
import java.util.List;
import java.util.stream.Collectors;
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

  // todo allowed for privileged users only
  public BrandAddResponse addBrand(BrandAddRequest request) {
    checkExisting(request);
    Brand entityToSave = brandFactory.fromAddRequest(request);
    Brand savedEntity = brandRepository.save(entityToSave);
    return brandMapper.toAddResponse(savedEntity);
  }

  public List<BrandOverviewResponse> overviewBrands() {
    final List<Brand> all = brandRepository.findAll();
    return all.stream()
        .map(b -> new BrandOverviewResponse(b.getId(), b.getName()))
        .collect(Collectors.toList());
  }

  private void checkExisting(BrandAddRequest request) {
    if (brandRepository.existsByNameIgnoreCase(request.getName())) {
      throw new ConflictException(format("Brand %s already exists", request.getName()));
    }
  }
}
