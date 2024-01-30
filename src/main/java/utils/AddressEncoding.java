/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** An utility class for decoding and checking email addresses. */
public class AddressEncoding {
  private static final String ADDRESS_PART = "[\\w\\d.!$%&'*+/=?^_`{|}~-]+";

  private static final Predicate<String> ADDRESS_PART_PREDICATE =
      Pattern.compile(ADDRESS_PART).asMatchPredicate();

  private static final Pattern ADDRESS_PATTERN =
      Pattern.compile(
          String.format(
              """
                \\G
                (
                  (
                    (?<name>[-\\w]+(\\s+[-\\w]+)?) |
                    (\"(?<qname>[^\"]*)\")
                  )\\s+
                )?
                (
                  (
                    ((?<local>%1$s)@(?<domain>%1$s)) |
                    (<(?<rlocal>%1$s)@(?<rdomain>%1$s)>)
                  )
                )(\\s*,\\s*)?
              """,
              ADDRESS_PART),
          Pattern.COMMENTS);

  private AddressEncoding() {}

  /**
   * Checks if the given string is a <em>valid</em> email local or domain part.
   *
   * <p>A valid email local or domain part is non {@code null} string that contains only ASCII
   * characters and matches the {@code [\w\d.!$%&'*+/=?^_`{|}~-]+} regular expression.
   *
   * @param part the part.
   * @return if the part is a valid email local or domain part.
   */
  public static boolean isValidAddressPart(final String part) {
    return ASCIICharSequence.isAscii(part) && ADDRESS_PART_PREDICATE.test(part);
  }

  /**
   * Tries to decodes a {@link ASCIICharSequence} into a list of email addresses parts.
   *
   * <p>Every element of the returned list is in turn a list of three non {@code null} {@link
   * ASCIICharSequence}s corresponding respectively to valid <em>display name</em>, <em>local</em>,
   * and <em>domain</em> parts of an email address.
   *
   * <p>The {@code sequence} should contain a sequence of email addresses separated by commas; every
   * address is given by the display name (enclosed in quotes, if composed by more than two words or
   * containing non alphabetic characters) followed by the local part and the domain part separated
   * by the {@code @} sign. Examples of encoded email addresses are:
   *
   * <ul>
   *   <li>{@code rossi@libero.com}
   *   <li>{@code "Mario, Carlo Rossi" <mcr@gmail.com>}
   *   <li>{@code Ronaldo <ronaldo.callegari@foo.it>}
   *   <li>{@code Piero Carli <pc@mac.mec.it>}
   * </ul>
   *
   * the result of this method applied to the above sequences is the list of the following lists:
   *
   * <ul>
   *   <li>{@code "", "rossi", "libero.com"}
   *   <li>{@code "Mario, Carlo Rossi", "mcr", "gmail.com"}
   *   <li>{@code "Ronaldo", "ronaldo.callegari", "foo.it"}
   *   <li>{@code "Piero Carli", "pc", "mac.mec.it"}
   * </ul>
   *
   * In case the {@code sequence} contains an invalid address (that is, an address that do not
   * conform to the prescribed format, or that has the local or domain parte that are not
   * <em>valid</em>), this method returns {@code null}.
   *
   * @param sequence the sequence encoding the email addresses.
   * @return the list of email addresses parts, or {@code null} if some address can't be decoded.
   * @throws NullPointerException if the given sequence is {@code null}.
   */
  public static List<List<ASCIICharSequence>> decode(ASCIICharSequence sequence)
      throws NullPointerException {
    final List<List<ASCIICharSequence>> result = new ArrayList<>();
    final Matcher m = ADDRESS_PATTERN.matcher(Objects.requireNonNull(sequence));
    int last = 0;
    while (m.find()) {
      final String name, local, domain;
      if (m.group("name") != null) name = m.group("name");
      else if (m.group("qname") != null) name = m.group("qname");
      else name = "";
      if (m.group("local") != null) {
        local = m.group("local");
        domain = m.group("domain");
      } else {
        local = m.group("rlocal");
        domain = m.group("rdomain");
      }
      if (ASCIICharSequence.isAscii(name)
          && ASCIICharSequence.isAscii(local)
          && ASCIICharSequence.isAscii(domain))
        result.add(
            List.of(
                ASCIICharSequence.of(name),
                ASCIICharSequence.of(local),
                ASCIICharSequence.of(domain)));
      else return null;
      last = m.end();
    }
    if (last != sequence.length()) return null;
    return List.copyOf(result);
  }
}
