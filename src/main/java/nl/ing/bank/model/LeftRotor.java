package nl.ing.bank.model;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public final class LeftRotor extends AbstractRotor {

  public LeftRotor(final Character initialSetting) {
    super(initialSetting);
    rotorMapping = new LinkedHashMap<>();
    rotorMapping.put('A','E');
    rotorMapping.put('B','K');
    rotorMapping.put('C','M');
    rotorMapping.put('D','F');
    rotorMapping.put('E','L');
    rotorMapping.put('F','G');
    rotorMapping.put('G','D');
    rotorMapping.put('H','Q');
    rotorMapping.put('I','V');
    rotorMapping.put('J','Z');
    rotorMapping.put('K','N');
    rotorMapping.put('L','T');
    rotorMapping.put('M','O');
    rotorMapping.put('N','W');
    rotorMapping.put('O','Y');
    rotorMapping.put('P','H');
    rotorMapping.put('Q','X');
    rotorMapping.put('R','U');
    rotorMapping.put('S','S');
    rotorMapping.put('T','P');
    rotorMapping.put('U','A');
    rotorMapping.put('V','I');
    rotorMapping.put('W','B');
    rotorMapping.put('X','R');
    rotorMapping.put('Y','C');
    rotorMapping.put('Z','J');

    workingWindow = getInitialisedWindow();
  }

  public Map<Character, Character> getTopIndicatorEntry() {
    final Map<Character, Character> topIndicator = new HashMap<>();
    topIndicator.put('Q', 'X');
    return Collections.unmodifiableMap(topIndicator);
  }

}
