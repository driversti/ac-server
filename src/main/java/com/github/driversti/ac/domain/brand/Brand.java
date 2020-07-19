package com.github.driversti.ac.domain.brand;

import static com.github.driversti.ac.common.RegexpConstants.BRAND_NAME;
import static com.github.driversti.ac.common.RegexpConstants.WRONG_BRAND_NAME;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Brand {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "brand_generator", sequenceName = "brand_seq_id", allocationSize = 5)
  private Long id;

  @NotEmpty
  @Pattern(regexp = BRAND_NAME, message = WRONG_BRAND_NAME)
  private String name;
  private LocalDate founded;
  private LocalDate defunct;
}
