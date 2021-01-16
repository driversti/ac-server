package com.github.driversti.ac.domain.brand

import static java.time.Month.JUNE
import static java.time.Month.MAY

import groovy.transform.PackageScope
import java.time.LocalDate

@PackageScope
class BrandTestData {

    static final String BRAND_NAME = "Horch"
    static final LocalDate FOUNDED = LocalDate.of(1904, MAY, 10)
    static final LocalDate DEFUNCT = LocalDate.of(1932, JUNE, 29)

    static Brand.BrandBuilder brandBuilder() {
        Brand.builder()
                .name(BRAND_NAME).founded(FOUNDED).defunct(DEFUNCT)
    }

    static BrandAddRequest fromEntity(Brand entity) {
        BrandAddRequest.builder()
                .name(entity.name)
                .founded(entity.founded)
                .defunct(entity.defunct)
                .build()
    }

    static BrandAddRequest.BrandAddRequestBuilder brandAddBuilder() {
        BrandAddRequest.builder()
                .name(BRAND_NAME).founded(FOUNDED).defunct(DEFUNCT)
    }
}
