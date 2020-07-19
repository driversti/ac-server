package com.github.driversti.ac.domain.brand;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class BrandAddResponse {

  private Long id;
  private String name;
  private LocalDate founded;
  private LocalDate defunct;
}
