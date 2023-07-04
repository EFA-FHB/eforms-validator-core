package com.nortal.efafhb.eforms.validator.validation.schematron;

import com.helger.schematron.svrl.AbstractSVRLMessage;
import com.helger.schematron.svrl.jaxb.FailedAssert;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Represents a failed assertion in a Schematron validation result.
 */
@Immutable
public class SchematronFailedAssert extends AbstractSVRLMessage {

  private final String flagValue;

  /**
   * Constructs a SchematronFailedAssert instance from a FailedAssert object.
   *
   * @param failedAssert the FailedAssert object representing the failed assertion
   */
  public SchematronFailedAssert(@Nonnull FailedAssert failedAssert) {
    this(
        failedAssert,
        var0 -> SchematronHelper.getBeautifiedLocation(Objects.requireNonNull(var0.getLocation())));
  }

  /**
   * Constructs a SchematronFailedAssert instance from a FailedAssert object and a function for obtaining
   * the beautified location of the failed assertion.
   *
   * @param failedAssert the FailedAssert object representing the failed assertion
   * @param getBeautifiedLocation the function for obtaining the beautified location of the failed assertion
   */
  public SchematronFailedAssert(
      @Nonnull FailedAssert failedAssert,
      @Nonnull Function<? super FailedAssert, String> getBeautifiedLocation) {
    super(
        null,
        failedAssert.getId(),
        SchematronHelper.getAsString(SchematronHelper.getText(failedAssert)),
        getBeautifiedLocation.apply(failedAssert),
        failedAssert.getTest(),
        failedAssert.getRole(),
        null);
    this.flagValue = failedAssert.getFlag();
  }

  /**
   * Get the flag value associated with the failed assertion.
   *
   * @return the flag value
   */
  @Nonnull
  public final String getFlagValue() {
    return this.flagValue;
  }
}
