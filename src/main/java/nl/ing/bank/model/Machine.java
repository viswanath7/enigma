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

import nl.ing.bank.exception.ReflectorException;
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
    // Input to reflector traversal
    final Long inputRowNumber = getInputRowNumber(input);
    final Long centreRotorRowNumber = getCentreRotorRowNumberEntry(inputRowNumber);
    final Long leftRotorRowNumber = getLeftRotorRowNumberEntry(centreRotorRowNumber);
    final Long reflectorStartPosition = getReflectorRowNumberEntry(leftRotorRowNumber);

    // Reflector to output traversal
    final Long leftRotorStartPosition = getReflectorToLeftRotorRowNumber(reflectorStartPosition);
    final Long centreRotorStartPosition = getLeftRotorToCentreRotorRowNumber(leftRotorStartPosition);
    final Long rightRotorStartPosition = getCentreRotorToRightRotorRowNumber(centreRotorStartPosition);
    final Long outputRowNumber = getRightRotorToOutputRowNumber(rightRotorStartPosition);
    return getInput().get(outputRowNumber);
    //TODO: Repeat similar logic to traverse across rotors from reflector
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
    throw new RotorInternalError("Error encountered while working with right motor", rightRotor, pair);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getLeftRotorRowNumberEntry(Long centreRotorRowNumber) {
    final Character centreRotorRightValue = centreRotor.getWorkingWindow().get(centreRotorRowNumber).getRight();

    final ImmutablePair<Character, Character> pair = new ImmutablePair<>(centreRotorRightValue,
        centreRotor.getMapping().get(centreRotorRightValue));

    for(Map.Entry<Long, Pair<Character, Character>> entry: centreRotor.getWorkingWindow().entrySet()) {
      if(entry.getValue().compareTo(pair)==0) return entry.getKey();
    }
    throw new RotorInternalError("Error encountered while working with right motor", centreRotor, pair);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getReflectorRowNumberEntry(Long leftRotorRowNumber) {
    final Character leftRotorRightValue = leftRotor.getWorkingWindow().get(leftRotorRowNumber).getRight();

    final ImmutablePair<Character, Character> pair = new ImmutablePair<>(leftRotorRightValue,
        leftRotor.getMapping().get(leftRotorRightValue));

    for(Map.Entry<Long, Pair<Character, Character>> entry: leftRotor.getWorkingWindow().entrySet()) {
      if(entry.getValue().compareTo(pair)==0) return entry.getKey();
    }
    throw new RotorInternalError("Error encountered while working with right motor", leftRotor, pair);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getReflectorToLeftRotorRowNumber(Long reflectorStartPosition) {
    final Map<Long, Character> reflectorMapping = getReflector();
    final Character reflectorStart = reflectorMapping.get(reflectorStartPosition);
    for(final Map.Entry<Long, Character> entry: reflectorMapping.entrySet()) {
      if(entry.getKey()>reflectorStartPosition && entry.getValue().compareTo(reflectorStart)==0)
        return entry.getKey();
    }
    throw new ReflectorException("Unable to find mapping in Reflector!", reflectorStart);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getLeftRotorToCentreRotorRowNumber(final Long leftRotorStartPosition) {
    final Character right = leftRotor.getWorkingWindow().get(leftRotorStartPosition).getLeft();
    final Character left = leftRotor.getMapping().getKey(right);
    final ImmutablePair<Character, Character> pair = new ImmutablePair<>(left, right);
    for(final Map.Entry<Long, Pair<Character, Character>> entry : leftRotor.getWorkingWindow().entrySet()) {
      if(entry.getValue().compareTo(pair)==0) return entry.getKey();
    }
    throw new RotorInternalError("Error encountered while traversing from left rotor to centre rotor", leftRotor, pair);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getCentreRotorToRightRotorRowNumber(final Long centreRotorStartPosition) {
    final Character right = centreRotor.getWorkingWindow().get(centreRotorStartPosition).getLeft();
    final Character left = centreRotor.getMapping().getKey(right);
    final ImmutablePair<Character, Character> pair = new ImmutablePair<>(left, right);
    for(final Map.Entry<Long, Pair<Character, Character>> entry : centreRotor.getWorkingWindow().entrySet()) {
      if(entry.getValue().compareTo(pair)==0) return entry.getKey();
    }
    throw new RotorInternalError("Error encountered while traversing from centre rotor to right rotor", centreRotor, pair);
  }

  // TODO: Refactor these methods by moving common logic into AbstractRotor
  private Long getRightRotorToOutputRowNumber(final Long rightRotorStartPosition) {
    final Character right = rightRotor.getWorkingWindow().get(rightRotorStartPosition).getLeft();
    final Character left = rightRotor.getMapping().getKey(right);
    final ImmutablePair<Character, Character> pair = new ImmutablePair<>(left, right);
    for(final Map.Entry<Long, Pair<Character, Character>> entry : rightRotor.getWorkingWindow().entrySet()) {
      if(entry.getValue().compareTo(pair)==0) return entry.getKey();
    }
    throw new RotorInternalError("Error encountered while traversing from right rotor to output", rightRotor, pair);
  }

}
