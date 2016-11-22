package nl.ing.bank.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SortedBidiMap;
import org.apache.commons.collections4.bidimap.DualTreeBidiMap;
import org.apache.commons.collections4.bidimap.UnmodifiableSortedBidiMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;


public class StreamUtility {

  /**
   * Converts a list of items to a map of items with their index as key
   *
   * @param input
   * @return
   */
  public static <T> Map<Long, T> zipWithIndex(final List<T> input) {
    if(CollectionUtils.isEmpty(input)) {
      return Collections.emptyMap();
    }
    return StreamUtils.zipWithIndex(input.stream())
        .collect(Collectors.toMap(Indexed::getIndex, Indexed::getValue));
  }

  public static <K,V> Map<Long, Pair<K,V>> zipWithIndex(final Map<K,V> input) {
    if(MapUtils.isEmpty(input)) {
      return Collections.emptyMap();
    }
    return StreamUtils.zipWithIndex(input.entrySet().stream())
        .collect(Collectors.toMap(Indexed::getIndex,
            entry-> new ImmutablePair<>(entry.getValue().getKey(), entry.getValue().getValue())
        ));
  }

  /**
   * Splits a given map into two at the provided key if present
   * @param input
   * @param splitPosition
   * @param <K>
   * @param <V>
   * @return
   */
  public static <K,V> Pair<LinkedHashMap<K,V>, LinkedHashMap<K,V>> split(final LinkedHashMap<K,V> input, final K splitPosition) {
    if(MapUtils.isEmpty(input)) {
      return new ImmutablePair<>(new LinkedHashMap<K,V>(), new LinkedHashMap<K,V>());
    }

    final LinkedHashMap<K,V> firstHalf = new LinkedHashMap<>();
    final LinkedHashMap<K,V> secondHalf = new LinkedHashMap<>();
    boolean firstHalfElements= true;

    for (final Map.Entry<K, V> entry : input.entrySet()) {
      if(entry.getKey().equals(splitPosition)) {
        firstHalfElements = false;
      }
      if(firstHalfElements) {
        firstHalf.put(entry.getKey(), entry.getValue());
      } else {
        secondHalf.put(entry.getKey(), entry.getValue());
      }
    }
    return new ImmutablePair<>(firstHalf, secondHalf);
  }

  public static <K,V> LinkedHashMap<K,V> combine(final LinkedHashMap<K,V> first, final LinkedHashMap<K,V> second) {
    if (MapUtils.isEmpty(first)) {
      return second;
    }

    if(MapUtils.isEmpty(second)) {
      return first;
    }

    final LinkedHashMap<K,V> combined = new LinkedHashMap<K, V>();
    combined.putAll(first);
    combined.putAll(second);
    return combined;
  }

  public static <K,V> SortedBidiMap<K, V> convert(final Map<K,V> input) {
    return UnmodifiableSortedBidiMap.unmodifiableSortedBidiMap(new DualTreeBidiMap<>(input));
  }

}
