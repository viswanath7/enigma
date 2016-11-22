package nl.ing.bank.model;

import static nl.ing.bank.util.StreamUtility.zipWithIndex;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import nl.ing.bank.exception.RotorInternalError;
import nl.ing.bank.util.StreamUtility;


public class Machine {

  private final LeftRotor leftRotor;
  private final CentreRotor centreRotor;
  private final RightRotor rightRotor;

  private final LinkedList<Character> reflector = new LinkedList<>(Arrays.asList(
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'D', 'I', 'J', 'K', 'G', 'M', 'K', 'M', 'I', 'E', 'B', 'F', 'T', 'C', 'V', 'V', 'J', 'A', 'T'
  ));

  public Machine(final Character leftInitial, final Character centreInitial, final Character rightInitial) {

    if(!Character.isLetter(leftInitial) || !Character.isLetter(centreInitial) || !Character.isLetter(rightInitial)) {
      throw new IllegalArgumentException("The Enigma machine can be only be initialised with alphabetical characters!");
    }

    leftRotor = new LeftRotor(Character.toUpperCase(leftInitial));
    centreRotor = new CentreRotor(Character.toUpperCase(centreInitial));
    rightRotor = new RightRotor(Character.toUpperCase(rightInitial));

    /* Stream.of(leftRotor, centreRotor, rightRotor)
        .collect(Collectors.toCollection(LinkedHashSet::new)); */
  }

  public Map<Long, Character> getReflector() {
    return zipWithIndex(reflector);
  }

  public Map<Long, Character> getInput() {
    return zipWithIndex(getAlphabeticalCharacters());
  }

  private List<Character> getAlphabeticalCharacters() {
    return IntStream.rangeClosed('A', 'Z').mapToObj(c -> (char) c).collect(Collectors.toList());
  }

  public Character decode(final Character input) {
    final Long inputRowNumber = getInputRowNumber(input);
    final Long centreRotorRowNumberEntry = getCentreRotorRowNumberEntry(inputRowNumber);
    final Long leftRotorRowNumberEntry = getLeftRotorRowNumberEntry(centreRotorRowNumberEntry);
    final Long reflectorRowNumberEntry = getReflectorRowNumberEntry(leftRotorRowNumberEntry);
    //TODO: Repeat similar logic to traverse across rotors from reflector
    return null;
  }


  private Long getInputRowNumber(final Character input) {
    return StreamUtility.convert(getInput()).getKey(input);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getCentreRotorRowNumberEntry(final Long inputRowNumber) {

    final Character rightRotorRightValue = rightRotor.getWorkingWindow().get(inputRowNumber).getRight();

    final ImmutablePair<Character, Character> pair = new ImmutablePair<>(rightRotorRightValue,
        rightRotor.getMapping().get(rightRotorRightValue));

    for(Map.Entry<Long, Pair<Character, Character>> entry: rightRotor.getWorkingWindow().entrySet()) {
      if(entry.getValue().compareTo(pair)==0) return entry.getKey();
    }
    throw new RotorInternalError(rightRotor, "Error encountered while working with right motor", pair);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getLeftRotorRowNumberEntry(Long centreRotorRowNumber) {
    final Character centreRotorRightValue = centreRotor.getWorkingWindow().get(centreRotorRowNumber).getRight();

    final ImmutablePair<Character, Character> pair = new ImmutablePair<>(centreRotorRightValue,
        centreRotor.getMapping().get(centreRotorRightValue));

    for(Map.Entry<Long, Pair<Character, Character>> entry: centreRotor.getWorkingWindow().entrySet()) {
      if(entry.getValue().compareTo(pair)==0) return entry.getKey();
    }
    throw new RotorInternalError(centreRotor, "Error encountered while working with right motor", pair);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getReflectorRowNumberEntry(Long leftRotorRowNumber) {
    final Character leftRotorRightValue = leftRotor.getWorkingWindow().get(leftRotorRowNumber).getRight();

    final ImmutablePair<Character, Character> pair = new ImmutablePair<>(leftRotorRightValue,
        leftRotor.getMapping().get(leftRotorRightValue));

    for(Map.Entry<Long, Pair<Character, Character>> entry: leftRotor.getWorkingWindow().entrySet()) {
      if(entry.getValue().compareTo(pair)==0) return entry.getKey();
    }
    throw new RotorInternalError(leftRotor, "Error encountered while working with right motor", pair);
  }

}
