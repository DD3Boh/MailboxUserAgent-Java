package mua;

import utils.ASCIICharSequence;

/** Represents a MIME-Version header. */
public final class MimeVersionHeader implements Header<Double> {
  /*
   * Abstraction Function:
   * Represents a MIME-Version header with a value.
   * The value is represented as a Double.
   *
   * Representation Invariant:
   * - The value cannot be null or empty.
   */
  /** The value of the MIME-Version header, represented as a Double */
  private final Double value;

  /**
   * Constructs a new MIME-Version header with the specified version.
   *
   * @param value the MIME version
   * @throws IllegalArgumentException if the value is null or empty
   * @throws NumberFormatException if the value is not a valid MIME version
   */
  public MimeVersionHeader(String value) {
    if (value == null || value.isEmpty()) throw new IllegalArgumentException("The value cannot be null or empty");

    this.value = Double.parseDouble(value);
  }

  /**
   * Returns the type of the header.
   *
   * @return the type of the header, "MIME-Version"
   */
  @Override
  public String getType() {
    return "MIME-Version";
  }

  /**
   * Returns the value of the MIME-Version header.
   *
   * @return the MIME version value
   */
  @Override
  public Double getValue() {
    return value;
  }

  /**
   * Returns an ASCII representation of the MIME-Version header.
   * The ASCII representation is of the form "MIME-Version: value".
   *
   * @return an ASCII representation of the MIME-Version header
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    return ASCIICharSequence.of(getType() + ": " + getValue());
  }
}
