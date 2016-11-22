package nl.ing.bank.util;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;


public class StreamUtilityTest {

  final LinkedHashMap<Character, Character> prepopulatedMap = new LinkedHashMap<>();

  @Before
  public void setUp() throws Exception {
    prepopulatedMap.put('K','X');
    prepopulatedMap.put('L','V');
    prepopulatedMap.put('M','Z');
    prepopulatedMap.put('N','N');
  }

  @Test
  public void should_convert_list_of_character_to_map_with_index_as_key() throws Exception {
    final LinkedList<Character> prePopulatedList = new LinkedList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G'));
    final Map<Long, Character> result = StreamUtility.zipWithIndex(prePopulatedList);
    assertThat(result, is( allOf(notNullValue(), hasEntry(5L, 'F') )) );
  }

  @Test
  public void should_convert_map_of_entries_to_map_of_indexes_and_pair() {


    final Map<Long, Pair<Character, Character>> result = StreamUtility.zipWithIndex(prepopulatedMap);
    assertThat(result, allOf(notNullValue(), hasEntry(3L, new ImmutablePair<>('N','N')) ) );
  }

  @Test
  public void should_split_map_into_two_at_given_key() {
    final Pair<LinkedHashMap<Character, Character>, LinkedHashMap<Character, Character>> result = StreamUtility.split(prepopulatedMap, 'M');
    assertThat(result, notNullValue());
    assertThat(result.getLeft(), allOf( notNullValue(), hasEntry('L','V') ));
    assertThat(result.getRight(), allOf( notNullValue(), hasEntry('M','Z') ));
  }

  @Test
  public void should_combine_two_maps() {
    final LinkedHashMap<Character, Character> first = new LinkedHashMap<>();
    first.put('A','A');
    first.put('B','J');
    final LinkedHashMap<Character, Character> second = new LinkedHashMap<>();
    second.put('C','D');
    second.put('D','K');
    second.put('E','S');
    final LinkedHashMap<Character, Character> combined = StreamUtility.combine(first, second);
    assertThat(combined.size(), equalTo(5));
  }

}