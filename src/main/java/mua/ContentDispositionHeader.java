/*
 * Copyright (C) 2024 Davide Garberi
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package mua;

import utils.ASCIICharSequence;

/** Represents a Content-Disposition header. Implements the Header interface with a String value. */
public final class ContentDispositionHeader implements Header {
  /*
   * Abstraction Function:
   * Represents a Content-Disposition header with a value.
   * The Content-Disposition header is used to specify that the content is an attachment and to
   * provide a filename for the attachment.
   * The value of the header is the filename of the attachment.
   *
   * Representation Invariant:
   * The value of the header cannot be empty or contain only whitespace characters.
   * The value of the header cannot be null.
   */
  /** the value of the Content-Disposition header */
  private final String value;

  /**
   * Constructs a ContentDispositionHeader object with the given value. If the value contains
   * "filename=", the value is extracted from the string. If the value does not contain "filename=",
   * the value is set as is.
   *
   * @param value the value of the Content-Disposition header
   * @throws IllegalArgumentException if the value is empty
   */
  public ContentDispositionHeader(String value) throws IllegalArgumentException {
    String filename = null;
    if (value == null)
      throw new IllegalArgumentException("Content-Disposition header value cannot be null");

    int filenameIndex = value.indexOf("filename=");

    if (filenameIndex == -1) filename = value;
    else filename = value.substring(filenameIndex + 9).replace("\"", "");

    if (filename == null)
      throw new IllegalArgumentException("Content-Disposition header value cannot be null");

    if (filename.isBlank())
      throw new IllegalArgumentException("Content-Disposition header value cannot be empty");

    this.value = filename;
  }

  /**
   * Returns the type of the Content-Disposition header.
   *
   * @return the type of the Content-Disposition header
   */
  @Override
  public String getType() {
    return "Content-Disposition";
  }

  /**
   * Returns the value of the Content-Disposition header.
   *
   * @return the value of the Content-Disposition header
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * Encodes the Content-Disposition header to ASCII representation, ready to be written to the
   * disk.
   *
   * @return the ASCII representation of the Content-Disposition header
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    return ASCIICharSequence.of(getType() + ": attachment; filename=\"" + value + "\"");
  }

  /**
   * Encodes the Content Disposition Header's name to its UI representation, in a String format. The
   * UI representation is the representation of the header's name that needs to be displayed to the
   * user when creating cards or tables. The UI representation of the header's name is "Text
   * Attachment\n" followed by the value of the header.
   *
   * @param isExtended whether the current UI representation is the extended version
   * @return the UI representation of the header
   */
  @Override
  public String encodeUIName(boolean isExtended) {
    if (!isExtended) return "";
    return "Text Attachment\n" + value;
  }
}
