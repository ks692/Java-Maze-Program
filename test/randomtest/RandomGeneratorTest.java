package randomtest;


import org.junit.Before;
import org.junit.Test;

import randomgenerator.RandomGenerator;
import randomgenerator.RangeRandomGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the Random class.
 */
public class RandomGeneratorTest {
  RandomGenerator randomGenerator;
  RandomGenerator randomGenerator1;

  @Before
  public void setup() {
    randomGenerator = new RangeRandomGenerator(2, 6, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalRangeRandomGeneratorLowNegative() {
    randomGenerator = new RangeRandomGenerator(-1, 6, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalRangeRandomGeneratorHighNegative() {
    randomGenerator = new RangeRandomGenerator(1, -3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalRangeRandomGeneratorHighLow() {
    randomGenerator = new RangeRandomGenerator(3, 1, 0);
  }

  @Test
  public void testWithSeed() {
    RandomGenerator randomGenerator = new RangeRandomGenerator(2, 5, 1);
    assertEquals(randomGenerator.getNextNumber(), 2);
  }

  @Test
  public void testGetNextNumber() {
    for (int i = 0; i < 5000; i++) {
      assertTrue(randomGenerator.getNextNumber() >= 2 || randomGenerator.getNextNumber() <= 6);
    }
  }
}
