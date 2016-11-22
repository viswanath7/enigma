package nl.ing.bank.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CentreRotor extends AbstractRotor {

  public CentreRotor(final Character initialSetting) {
    super(initialSetting);
    rotorMapping = new LinkedHashMap<>();
    rotorMapping.put('A','A');
    rotorMapping.put('B','J');
    rotorMapping.put('C','D');
    rotorMapping.put('D','K');
    rotorMapping.put('E','S');
    rotorMapping.put('F','I');
    rotorMapping.put('G','R');
    rotorMapping.put('H','U');
    rotorMapping.put('I','X');
    rotorMapping.put('J','B');
    rotorMapping.put('K','L');
    rotorMapping.put('L','H');
    rotorMapping.put('M','W');
    rotorMapping.put('N','T');
    rotorMapping.put('O','M');
    rotorMapping.put('P','C');
    rotorMapping.put('Q','Q');
    rotorMapping.put('R','G');
    rotorMapping.put('S','Z');
    rotorMapping.put('T','N');
    rotorMapping.put('U','P');
    rotorMapping.put('V','Y');
    rotorMapping.put('W','F');
    rotorMapping.put('X','V');
    rotorMapping.put('Y','O');
    rotorMapping.put('Z','E');

    workingWindow = getInitialisedWindow();
  }

  public Map<Character, Character> getTopIndicatorEntry() {
    final Map<Character, Character> topIndicator = new HashMap<>();
    topIndicator.put('E', 'S');
    return Collections.unmodifiableMap(topIndicator);
  }

}
