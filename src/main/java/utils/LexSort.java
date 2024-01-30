/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.util.Comparator;
import java.util.Iterator;

/** An utility class to obtain a lexicographic comparator for lists. */
public class LexSort {

  /**
   * Returns a lexicographic comparator for lists.
   *
   * @param <T> the type of the elements of the lists.
   * @return a lexicographic comparator for lists.
   */
  public static <T extends Comparable<T>> Comparator<Iterable<T>> lexicographicComparator() {
    return new Comparator<Iterable<T>>() {

      @Override
      public int compare(Iterable<T> o1, Iterable<T> o2) {
        Iterator<T> it1 = o1.iterator(), it2 = o2.iterator();
        int lc = 0;
        while (it1.hasNext() && it2.hasNext())
          if ((lc = it1.next().compareTo(it2.next())) != 0) break;
        if (lc != 0) return lc;
        if (it1.hasNext()) return 1;
        if (it2.hasNext()) return -1;
        return 0;
      }
    };
  }
}
