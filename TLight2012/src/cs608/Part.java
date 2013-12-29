package cs608;

/**
 * Class to represent an identifiable part
 * @Author sbrown
 * @Version 1.0
 */
public class Part {

   private String id;

  /**
   * Creates a new part with the specified ID
   *
   * @param newId the ID to give to this object. It is the responsibility of the caller to enforce
   *              unique object IDs if required
   */
   public Part(String newId) {
      id = new String(newId);
   }

  /**
   * Find the object ID
   *
   * @return      the part ID 
   */
   public String getId() {
      return new String(id);
   }

}