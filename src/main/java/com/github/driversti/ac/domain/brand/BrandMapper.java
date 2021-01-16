package com.github.driversti.ac.domain.brand;

import org.mapstruct.Mapper;

@Mapper
interface BrandMapper {

  BrandAddResponse toAddResponse(Brand savedEntity);
}
