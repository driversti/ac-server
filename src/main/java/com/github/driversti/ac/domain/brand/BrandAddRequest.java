package com.github.driversti.ac.domain.brand;

import static com.github.driversti.ac.common.RegexpConstants.BRAND_NAME;
import static com.github.driversti.ac.common.RegexpConstants.WRONG_BRAND_NAME;

import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class BrandAddRequest {

  @NotEmpty(message = "Brand name cannot be empty")
  @Pattern(regexp = BRAND_NAME, message = WRONG_BRAND_NAME)
  private String name;
  private LocalDate founded;
  private LocalDate defunct;
}
