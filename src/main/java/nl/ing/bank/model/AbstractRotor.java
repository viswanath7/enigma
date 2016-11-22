package nl.ing.bank.model;


import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.SortedBidiMap;
import org.apache.commons.lang3.tuple.Pair;

import nl.ing.bank.util.StreamUtility;

public abstract class AbstractRotor implements Rotor {

  private final Character initialSetting;
  protected Map<Long, Pair<Character, Character>> workingWindow;

  protected LinkedHashMap<Character, Character> rotorMapping;

  public AbstractRotor(final Character initialSetting) {
    this.initialSetting = initialSetting;
  }

  public Character getInitialSetting() {
    return initialSetting;
  }

  public Map<Long, Pair<Character, Character>> getWorkingWindow() {
    return workingWindow;
  }

  public SortedBidiMap<Character, Character> getMapping() {
    return StreamUtility.convert(rotorMapping);
  }

  protected Map<Long, Pair<Character, Character>> getInitialisedWindow() {
    final Pair<LinkedHashMap<Character, Character>, LinkedHashMap<Character, Character>> split = StreamUtility.split(rotorMapping, initialSetting);
    final LinkedHashMap<Character, Character> combined = StreamUtility.combine(split.getRight(), split.getLeft());
    return StreamUtility.zipWithIndex(combined);
  }
}
