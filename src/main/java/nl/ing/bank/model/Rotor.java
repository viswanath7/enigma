package nl.ing.bank.model;


import java.util.Map;

import org.apache.commons.collections4.SortedBidiMap;

public interface Rotor {

  /**
   * Returns the initial setting of the rotor
   *
   * @return
   */
  Character getInitialSetting();

  /**
   * Returns the rotor's character mapping
   * @return
   */
  SortedBidiMap<Character, Character> getMapping();

  /**
   *
   * @return
   */
  Map<Character, Character> getTopIndicatorEntry();
}
