package com.github.driversti.ac.domain.brand;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/brand", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class BrandController {

  private final BrandFacade brandFacade;

  @PostMapping("/add")
  public ResponseEntity<BrandAddResponse> addBrand(
      @RequestBody @Validated BrandAddRequest request) {
    return ResponseEntity.ok(brandFacade.addBrand(request));
  }
}
