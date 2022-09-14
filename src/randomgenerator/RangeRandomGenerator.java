package randomgenerator;

import java.util.Random;

/**
 * This class represents a random number generator which gives the values in
 * a range. the range of the random numbers is defined by low,high which
 * represent the lower and upper bound of ranges.
 */
public final class RangeRandomGenerator implements RandomGenerator {
  private int low;
  private int high;
  private int seed;
  private Random random;

  /**
   * Constructs a {@link RangeRandomGenerator} object in terms of low,high
   * which are upper and lower bound of ranges.
   *
   * @param low  lower bound of the range
   * @param high higher bound of the range exclusive.
   * @param seed seed to be given to the generator. if seed is 0 it is
   *             truly random.
   * @throws IllegalArgumentException if low or high is less than zero.
   * @throws IllegalArgumentException if low is greater than high.
   */
  public RangeRandomGenerator(int low, int high, int seed) {
    if (low < 0 || high < 0 || low > high) {
      throw new IllegalArgumentException("Illegal low and high values");
    }
    this.low = low;
    this.high = high;
    this.seed = seed;
    if (seed != 0) {
      random = new Random(seed);
    } else {
      random = new Random();
    }
  }

  @Override
  public int getNextNumber() {
    return low + random.nextInt(high - low);
  }

  @Override
  public void lowerLimit() {
    high = high - 1;
    if (high < 0) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public RandomGenerator getCopy() {
    return new RangeRandomGenerator(this.low, this.high, this.seed);
  }
}
