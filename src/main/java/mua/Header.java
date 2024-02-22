/*
 * Copyright (C) 2024 Davide Garberi
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package mua;

import utils.ASCIICharSequence;

/** Represents a generic interface for message headers. */
public interface Header {
  /**
   * Returns the type of the header.
   *
   * @return the type of the header
   */
  String getType();

  /**
   * Returns the value of the header.
   *
   * @return the value of the header
   */
  Object getValue();

  /**
   * Returns an ASCII representation of the header.
   *
   * @return an ASCII representation of the header
   */
  ASCIICharSequence encodeToASCII();

  /**
   * Encode the header value to its UI representation, in a String format. The UI representation is
   * the representation of the header that needs to be displayed to the user when creating cards or
   * tables.
   *
   * <p>The default implementation returns an empty string, meaning that the header does not have a UI
   * representation.
   *
   * @param extended whether to return the extended version of the header
   * @return the UI representation of the header
   */
  default String encodeUIValue(boolean extended) {
    return "";
  }
  ;

  /**
   * Encode the header's name to its UI representation, in a String format. The UI representation is
   * the representation of the header that needs to be displayed to the user when creating cards or
   * tables.
   *
   * <p>The default implementation returns an empty string, meaning that the header does not have a UI
   * representation, hence the name is not displayed.
   *
   * The isExtended parameter is used to determine whether the current UI representation is the
   * extended version. This allows to return an empty string when the header shouldn't be displayed.
   *
   * @param isExtended whether the current UI representation is the extended version
   * @return the UI representation of the header's name
   */
  default String encodeUIName(boolean isExtended) {
    return "";
  }
  ;
}
