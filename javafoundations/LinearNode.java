/**
* LinearNode.java
*
* Represents a node in a linked linear structure.
*/

package javafoundations;

public class LinearNode<T>
{
   private LinearNode<T> next;
   private T element;

   /**
    * Constructor. Creates an empty node.
    * 
    */
   public LinearNode()
   {
      next = null;
      element = null;
   }

  /**
    * Creates a node storing the specified element.
    *
    * @param The element to be stored in this node
    */
   public LinearNode (T elem)
   {
      next = null;
      element = elem;
   }

  /**
    * Returns the node that follows this one.
    *
    * @return The node that follows this one
    */
   public LinearNode<T> getNext()
   {
      return next;
   }

  /**
    * Sets the node that follows this one.
    *
    * @param The node that the next node is set to
    */

   public void setNext (LinearNode<T> node)
   {
      next = node;
   }

   /**
     *  Returns the element stored in this node.
     * @return The element stored in this node
    */
   public T getElement()
   {
      return element;
   }

    /**
     *  Sets the element stored in this node.
     * @param The element to be stored in this node
    */
   public void setElement (T elem)
   {
      element = elem;
   }
}
