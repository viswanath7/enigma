package nl.ing.bank.exception;


public class ReflectorException extends RuntimeException {

  final Character reflectorCharacter;

  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
   */
  public ReflectorException(final String message, final Character reflectorCharacter) {
    super(message);
    this.reflectorCharacter = reflectorCharacter;
  }
}
