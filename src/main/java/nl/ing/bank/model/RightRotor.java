package nl.ing.bank.model;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;

public final class RightRotor extends AbstractRotor {

  public RightRotor(final Character initialSetting) {
    super(initialSetting);
    rotorMapping = new LinkedHashMap<>();
    rotorMapping.put('A','B');
    rotorMapping.put('B','D');
    rotorMapping.put('C','F');
    rotorMapping.put('D','H');
    rotorMapping.put('E','J');
    rotorMapping.put('F','L');
    rotorMapping.put('G','C');
    rotorMapping.put('H','P');
    rotorMapping.put('I','R');
    rotorMapping.put('J','T');
    rotorMapping.put('K','X');
    rotorMapping.put('L','V');
    rotorMapping.put('M','Z');
    rotorMapping.put('N','N');
    rotorMapping.put('O','Y');
    rotorMapping.put('P','E');
    rotorMapping.put('Q','I');
    rotorMapping.put('R','W');
    rotorMapping.put('S','G');
    rotorMapping.put('T','A');
    rotorMapping.put('U','K');
    rotorMapping.put('V','M');
    rotorMapping.put('W','U');
    rotorMapping.put('X','S');
    rotorMapping.put('Y','Q');
    rotorMapping.put('Z','O');

    workingWindow = upByOneRow(getInitialisedWindow());
  }

  public Map<Character, Character> getTopIndicatorEntry() {
    final Map<Character, Character> topIndicator = new HashMap<>();
    topIndicator.put('V', 'M');
    return Collections.unmodifiableMap(topIndicator);
  }

  public Map<Long, Pair<Character, Character>> upByOneRow(final Map<Long, Pair<Character, Character>> input) {
    if(MapUtils.isEmpty(input)) {
      return input;
    }
    return input.entrySet().stream().collect(
        Collectors.toMap(
            in-> (in.getKey()==0)?25:in.getKey()-1,
            in-> input.get(in.getKey())
        ));
  }

}
