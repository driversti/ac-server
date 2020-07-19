package com.github.driversti.ac.common;

import lombok.experimental.UtilityClass;
import org.intellij.lang.annotations.Language;

@UtilityClass
public class RegexpConstants {

  @Language("RegExp") // accepts: BMW, Opel, Alfa Romeo, Mercedes-Benz
  public static final String BRAND_NAME = "^([A-Z]+)$|^([A-Z][a-z]+)$|^([A-Z][a-z]+\\s[A-Z][a-z]+)$|^([A-Z][a-z]+-[A-Z][a-z]+)$";
  public static final String WRONG_BRAND_NAME = "Wrong Brand's name format";
  @Language("RegExp") // accepts: W123, G30
  public static final String GENERATION_CODE_NAME = "^[A-Z][a-z_0-9\\s]{0,32}$";
  public static final String WRONG_GENERATION_CODE_NAME = "Wrong Generation's name format";
  @Language("RegExp")
  public static final String CURRENCY_CODE = "^[A-Z]{3}$";
  public static final String WRONG_CURRENCY_CODE = "Currency code must contain 3 uppercase letters";
  public static final String MUST_BE_POSITIVE = "The value must be positive";
}
