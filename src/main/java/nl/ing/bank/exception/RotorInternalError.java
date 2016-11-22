package nl.ing.bank.exception;


import org.apache.commons.lang3.tuple.Pair;

import nl.ing.bank.model.Rotor;

public class RotorInternalError extends RuntimeException {

  private final Rotor rotor;
  private final String errorMessage;
  private final Pair<Character, Character> pairSought;


  public RotorInternalError(final Rotor rotor, final String errorMessage) {
    this(rotor, errorMessage, null);
  }


  public RotorInternalError(final Rotor rotor, final String errorMessage, final Pair<Character, Character> pairSought) {
    super();
    this.rotor = rotor;
    this.errorMessage = errorMessage;
    this.pairSought = pairSought;
  }

  public Rotor getRotor() {
    return rotor;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public Pair<Character, Character> getPairSought() {
    return pairSought;
  }
}
