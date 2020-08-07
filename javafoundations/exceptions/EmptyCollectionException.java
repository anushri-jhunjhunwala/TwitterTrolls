/**
 * EmptyCollectionException.java 
 * To be used when a collection is empty.
 */

package javafoundations.exceptions;

public class EmptyCollectionException extends RuntimeException
{
  /**
   * Constructor. Creates an exception with the provided message
   * @param The message to be presented when the exception is thrown
   **/
  public EmptyCollectionException (String message)
  {
    super (message);
  }
}
