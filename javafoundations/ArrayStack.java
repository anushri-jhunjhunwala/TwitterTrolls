//********************************************************************
//  ArrayStack.java       Java Foundations
//
//  Represents an array implementation of a stack. The bottom of
//  the stack is kept at array index 0.
//********************************************************************

package javafoundations;

import javafoundations.exceptions.*;

public class ArrayStack<T> implements Stack<T>
{
  private final int DEFAULT_CAPACITY = 10;
  private int count;
  private T[] array;
  
  /**
   *    Creates an empty stack using the default capacity.
   */
  public ArrayStack()
  {
    count = 0;
    array = (T[])(new Object[DEFAULT_CAPACITY]);
   
  }
  
  /**
   *  Adds the specified element to the top of this stack, expanding
   *  the capacity of the stack array if necessary.
   */
  public void push (T element)
  {
    if (count == array.length)
      expandCapacity();
    
    array[count] = element;
    count++;
  }
  
  /**
   *  Returns a string representation of this stack.
   */
  public String toString()
  {
    String result = "<top of stack>\n";
    
    for (int index=count-1; index >= 0; index--)
      result += array[index] + "\n";
    
    return result + "<bottom of stack>";
  }
  
  /**
   *  Helper method. 
   *  Creates a new array to store the contents of this stack with
   *  twice the capacity of the old one.
   */
  private void expandCapacity()
  {
      T[] larger = (T[])(new Object[array.length*2]);
    
    for (int index=0; index < array.length; index++)
      larger[index] = array[index];
    
    array = larger;
  }
  
  /**
   *    Removes the element at the top of this stack and returns a
   *  reference to it. Throws an EmptyCollectionException if the
   *  stack contains no elements.
   */
  public T pop () throws EmptyCollectionException 
  {
    if(count == 0)
      throw new EmptyCollectionException("Pop operation failed. Stack is empty.");
    
    T temp = array[count - 1];
    count--;
    return temp;
  }
  
  /**
   *  Returns top without removing it from the stack
   *  @exception EmptyCollectionException when a peek() is attemped on an empty stack
   */
  public T peek () throws EmptyCollectionException 
  {
    if(count == 0)
      throw new EmptyCollectionException("Peek operation failed. Stack is empty.");
    
    return array[count - 1];
  }
  
  /**
   *  Checks if stack is empty
   */
  public boolean isEmpty() 
  {
    return count == 0;
  }
  
  /**
   *  Returns the number of eleements in the stack
   */
  public int size() 
  {
    return count;
  }

}
