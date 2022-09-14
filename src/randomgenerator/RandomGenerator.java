package randomgenerator;


/**
 * This interface represents a random number generator.
 */
public interface RandomGenerator {

  /**
   * get the next random number generated.
   *
   * @return the next random number generated.
   */
  public int getNextNumber();

  /**
   * Lower the upperLimit by 1. Can be used when the random limit has
   * to be lowered.
   *
   * @throws IllegalArgumentException if the limit is lowered to negative.
   */
  public void lowerLimit();

  /**
   * get Copy of the generator object.
   * Used to prevent mutability.
   *
   * @return replica of the generator.
   */
  public RandomGenerator getCopy();


}
