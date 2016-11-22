package nl.ing.bank.exception;


import org.apache.commons.lang3.tuple.Pair;

import nl.ing.bank.model.Rotor;

public class RotorInternalError extends RuntimeException {

  private final Rotor rotor;
  private final Pair<Character, Character> pairSought;


  public RotorInternalError(final String errorMessage, final Rotor rotor) {
    this(errorMessage, rotor, null);
  }

  public RotorInternalError(final String errorMessage, final Rotor rotor, final Pair<Character, Character> pairSought) {
    super(errorMessage);
    this.rotor = rotor;
    this.pairSought = pairSought;
  }

  public Rotor getRotor() {
    return rotor;
  }

  public Pair<Character, Character> getPairSought() {
    return pairSought;
  }
}
