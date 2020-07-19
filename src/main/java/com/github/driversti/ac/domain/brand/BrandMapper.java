package com.github.driversti.ac.domain.brand;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;

@Mapper(nullValuePropertyMappingStrategy = IGNORE)
interface BrandMapper {

  BrandAddResponse toAddResponse(Brand savedEntity);
}
