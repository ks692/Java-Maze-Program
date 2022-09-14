package playertest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import location.Cave;
import location.Direction;
import location.Location;
import player.Player;
import player.PlayerImpl;
import player.Treasure;
import weapon.Arrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the player class.
 */
public class PlayerTest {

  Player player;

  @Before
  public void setup() {
    player = new PlayerImpl("Tester");
  }

  @Test
  public void testPlayerConstructor() {
    player = new PlayerImpl("Tester");
    assertEquals("Tester", player.getName());
    assertEquals(player.getTreasures().size(), 0);
    assertEquals(player.getArrows().size(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidName() {
    new PlayerImpl(null);
  }


  @Test
  public void testPickupTreasures() {
    List<Direction> validMove = new ArrayList<Direction>();
    validMove.add(Direction.NORTH);
    Location location = new Cave(1, 1, validMove);
    location.addTreasure(Treasure.DIAMOND);
    player.pickupTreasures(location.getTreasures());
    assertTrue(player.getTreasures().size() == 1);
    assertTrue(player.getTreasures().contains(Treasure.DIAMOND));
  }

  @Test
  public void testGetTreasures() {
    List<Direction> validMove = new ArrayList<Direction>();
    validMove.add(Direction.SOUTH);
    Location location = new Cave(1, 1, validMove);
    location.addTreasure(Treasure.DIAMOND);
    location.addTreasure(Treasure.SAPPHIRE);
    player.pickupTreasures(location.getTreasures());
    assertTrue(player.getTreasures().size() == 2);
    assertTrue(player.getTreasures().contains(Treasure.DIAMOND));
    assertTrue(player.getTreasures().contains(Treasure.SAPPHIRE));
  }

  @Test
  public void testGetArrows() {
    Player player = new PlayerImpl("Sri");
    for (int i = 0; i < 100; i++) {
      player.addArrow(Arrow.CROOKED);
    }
    assertEquals(player
                    .getArrows()
                    .toString(),
            "{CROOKED=100}");
    for (int i = 0; i < 29; i++) {
      player.useArrow(Arrow.CROOKED);
    }
    assertEquals(player
                    .getArrows()
                    .toString(),
            "{CROOKED=71}");
  }

  @Test
  public void testAddArrow() {
    Player player = new PlayerImpl("Sri");
    for (int i = 0; i < 100; i++) {
      player.addArrow(Arrow.CROOKED);
      assertTrue(player
                         .getArrows()
                         .get(Arrow.CROOKED) == i + 1);
    }
  }


  @Test
  public void testUseArrow() {
    Player player = new PlayerImpl("Sri");
    for (int i = 0; i < 100; i++) {
      player.addArrow(Arrow.CROOKED);
    }
    for (int i = 0; i < 100; i++) {
      player.useArrow(Arrow.CROOKED);
      assertTrue(player.getArrows().get(Arrow.CROOKED) == 100 - (i + 1));
    }
  }

  @Test
  public void testGetCopy() {
    Player player = new PlayerImpl("Sri");
    Player playerCopy = player.getCopy();
    assertEquals("Sri", playerCopy.getName());
    assertEquals(playerCopy.getTreasures().size(), 0);
    assertEquals(playerCopy.getArrows().size(), 0);

    player = new PlayerImpl("Sri");
    List<Treasure> treasures = new ArrayList<Treasure>();
    treasures.add(Treasure.SAPPHIRE);
    player.pickupTreasures(treasures);
    int count = 0;
    for (int i = 0; i < 2; i++) {
      player.addArrow(Arrow.CROOKED);
    }
    playerCopy = player.getCopy();
    assertEquals("Sri", playerCopy.getName());
    assertEquals(playerCopy.getTreasures().size(), 1);
    assertTrue(playerCopy.getArrows().get(Arrow.CROOKED) == 2);
    assertEquals(playerCopy.getTreasures(), treasures);
  }

}
