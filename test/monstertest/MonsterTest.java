package monstertest;

import org.junit.Test;

import monster.Monster;
import monster.Otyugh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the Location class.
 */
public class MonsterTest {

  @Test
  public void testConstructors() {
    Monster monster = new Otyugh(2);
    assertEquals(monster.getId(), 2);
    assertEquals(monster.getHealth(), 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMonsterId() {
    Monster monster = new Otyugh(-1);
  }

  @Test
  public void testReduceHealth() {
    Monster monster = new Otyugh(2);
    monster.reduceHealth();
    assertEquals(monster.getHealth(), 1);
    monster.reduceHealth();
    assertEquals(monster.getHealth(), 0);
  }

  @Test
  public void testGetCopy() {
    Monster monster = new Otyugh(3);
    monster.reduceHealth();
    Monster monsterCopy = monster.getCopy();
    assertEquals(monsterCopy.getId(), 3);
    assertEquals(monsterCopy.getHealth(), 1);
  }

  @Test
  public void testObjectMethods() {
    // hashcode and monster depends only on id.
    Monster monster = new Otyugh(3);
    Monster monsterCopy = monster.getCopy();
    Monster monster1 = new Otyugh(2);
    Monster monster2 = new Otyugh(4);
    monsterCopy.reduceHealth();
    assertEquals(monster, monsterCopy);
    assertEquals(monster.hashCode(), monsterCopy.hashCode());
    assertEquals("id : 3, health : 2. ", monster.toString());
    assertTrue(monster.compareTo(monster1) < 0);
    assertTrue(monster.compareTo(monster2) > 0);
    assertTrue(monster.compareTo(monsterCopy) == 0);
  }


}
