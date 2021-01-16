package com.github.driversti.ac.domain.brand;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/brand")
public class BrandController {

  private final BrandFacade brandFacade;

  @GetMapping("/overview")
  public ResponseEntity<List<BrandOverviewResponse>> overviewBrands() {
    return ResponseEntity.ok(brandFacade.overviewBrands());
  }

  @PostMapping("/add")
  public ResponseEntity<BrandAddResponse> addBrand(@RequestBody @Valid BrandAddRequest request) {
    return ResponseEntity.ok(brandFacade.addBrand(request));
  }
}
