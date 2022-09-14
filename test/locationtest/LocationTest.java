package locationtest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import location.Cave;
import location.Direction;
import location.Location;
import location.Tunnel;
import monster.Monster;
import monster.Otyugh;
import player.Treasure;
import weapon.Arrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the Location class.
 */
public class LocationTest {
  Location cave;
  Location tunnel;

  @Before
  public void setup() {
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.NORTH);
    cave = new Cave(0, 0, directionList);
    directionList.add(Direction.SOUTH);
    tunnel = new Tunnel(0, 0, directionList);
  }

  @Test
  public void testConstructorsGetters() {
    //testing cave
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.NORTH);
    Location location = new Cave(1, 1, directionList);
    location.addTreasure(Treasure.DIAMOND);
    location.addArrow(Arrow.CROOKED);
    location.addArrow(Arrow.CROOKED);
    Monster monster = new Otyugh(1);
    location.addMonster(monster);
    assertEquals(location.getXLocation(), 1);
    assertEquals(location.getYLocation(), 1);
    assertEquals(location.getValidMoves().size(), 1);
    assertEquals(location.getTreasures().size(), 1);
    assertTrue(location.getTreasures().contains(Treasure.DIAMOND));
    assertTrue(location.getArrows().size() == 2);
    assertTrue(location.getMonsters().size() == 1);
    assertTrue(location.getMonsters().contains(monster));


    //testing tunnel
    directionList.add(Direction.SOUTH);
    location = new Tunnel(1, 1, directionList);
    location.addTreasure(Treasure.DIAMOND);
    location.addArrow(Arrow.CROOKED);
    location.addArrow(Arrow.CROOKED);
    monster = new Otyugh(1);
    location.addMonster(monster);
    assertEquals(location.getXLocation(), 1);
    assertEquals(location.getYLocation(), 1);
    assertEquals(location.getValidMoves().size(), 2);
    assertTrue(location.getValidMoves().contains(Direction.SOUTH));
    assertTrue(location.getValidMoves().contains(Direction.NORTH));
    assertEquals(location.getTreasures().size(), 0);
    assertFalse(location.getTreasures().contains(Treasure.DIAMOND));
    assertTrue(location.getArrows().size() == 2);
    assertTrue(location.getMonsters().size() == 0);
    assertFalse(location.getMonsters().contains(monster));


    directionList.add(Direction.EAST);
    location = new Cave(1, 1, directionList);
    location.addTreasure(Treasure.DIAMOND);
    assertEquals(location.getXLocation(), 1);
    assertEquals(location.getYLocation(), 1);
    assertEquals(location.getValidMoves().size(), 3);
    assertTrue(location.getValidMoves().contains(Direction.SOUTH));
    assertTrue(location.getValidMoves().contains(Direction.NORTH));
    assertTrue(location.getValidMoves().contains(Direction.EAST));
    assertEquals(location.getTreasures().size(), 1);
    assertTrue(location.getTreasures().contains(Treasure.DIAMOND));

    directionList.add(Direction.WEST);
    location = new Cave(1, 1, directionList);
    location.addTreasure(Treasure.DIAMOND);
    assertEquals(location.getXLocation(), 1);
    assertEquals(location.getYLocation(), 1);
    assertEquals(location.getValidMoves().size(), 4);
    assertTrue(location.getValidMoves().contains(Direction.SOUTH));
    assertTrue(location.getValidMoves().contains(Direction.NORTH));
    assertTrue(location.getValidMoves().contains(Direction.EAST));
    assertTrue(location.getValidMoves().contains(Direction.WEST));
    assertEquals(location.getTreasures().size(), 1);
    assertTrue(location.getTreasures().contains(Treasure.DIAMOND));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidCave() {
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.NORTH);
    directionList.add(Direction.EAST);
    Location cave = new Cave(0, 0, directionList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidCave2() {
    Location cave = new Cave(0, 0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTunnel() {
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.EAST);
    Location tunnel = new Tunnel(0, 0, directionList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTunnel3() {
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.EAST);
    directionList.add(Direction.EAST);
    Location tunnel = new Tunnel(0, 0, directionList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTunnel4() {
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.WEST);
    directionList.add(Direction.EAST);
    directionList.add(Direction.NORTH);
    directionList.add(Direction.SOUTH);
    Location tunnel = new Tunnel(0, 0, directionList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTunnel2() {
    Location tunnel = new Tunnel(0, 0, null);
  }

  @Test
  public void testAddTreasure() {
    //test add to cave.
    List<Direction> directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    directions.add(Direction.EAST);
    Location location = new Cave(0, 0, directions);
    location.addTreasure(Treasure.SAPPHIRE);
    location.addTreasure(Treasure.DIAMOND);
    assertEquals(location.getTreasures().size(), 2);
    assertTrue(location.getTreasures().contains(Treasure.SAPPHIRE));
    assertTrue(location.getTreasures().contains(Treasure.DIAMOND));

    //test add to tunnel.
    directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    location = new Tunnel(0, 0, directions);
    location.addTreasure(Treasure.SAPPHIRE);
    assertEquals(location.getTreasures().size(), 0);
    assertFalse(location.getTreasures().contains(Treasure.SAPPHIRE));
  }

  @Test
  public void testAddArrow() {
    //test add to cave.
    List<Direction> directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    directions.add(Direction.EAST);
    Location location = new Cave(0, 0, directions);
    location.addArrow(Arrow.CROOKED);
    location.addArrow(Arrow.CROOKED);
    assertEquals(location.getArrows().size(), 2);
    assertTrue(location.getArrows().contains(Arrow.CROOKED));


    //test add to tunnel.
    directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    location = new Tunnel(0, 0, directions);
    location.addArrow(Arrow.CROOKED);
    location.addArrow(Arrow.CROOKED);
    assertEquals(location.getArrows().size(), 2);
    assertTrue(location.getArrows().contains(Arrow.CROOKED));
  }

  @Test
  public void testAddMonsters() {
    //test add to cave.
    List<Direction> directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    directions.add(Direction.EAST);
    Location location = new Cave(0, 0, directions);
    Monster monster = new Otyugh(1);
    location.addMonster(monster);
    assertEquals(location.getMonsters().size(), 1);
    assertTrue(location.getMonsters().contains(monster));


    //test add to tunnel.
    directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    location = new Tunnel(0, 0, directions);
    monster = new Otyugh(1);
    location.addMonster(monster);
    assertTrue(location.getMonsters().size() == 0);
    assertFalse(location.getMonsters().contains(monster));
  }

  @Test
  public void testRemoveMonsters() {
    //test add to cave.
    List<Direction> directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    directions.add(Direction.EAST);
    Location location = new Cave(0, 0, directions);
    Monster monster = new Otyugh(1);
    location.addMonster(monster);
    assertEquals(location.getMonsters().size(), 1);
    assertTrue(location.getMonsters().contains(monster));
    location.removeMonster(monster);
    assertEquals(location.getMonsters().size(), 0);
    assertFalse(location.getMonsters().contains(monster));


    //test add to tunnel.
    directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    location = new Tunnel(0, 0, directions);
    monster = new Otyugh(1);
    location.addMonster(monster);
    assertTrue(location.getMonsters().size() == 0);
    assertFalse(location.getMonsters().contains(monster));
    location.removeMonster(monster);
    assertEquals(location.getMonsters().size(), 0);
    assertFalse(location.getMonsters().contains(monster));
  }

  @Test
  public void testPickupTreasures() {
    //test cave
    List<Direction> directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    directions.add(Direction.EAST);
    Location location = new Cave(0, 0, directions);
    location.addTreasure(Treasure.SAPPHIRE);
    location.addTreasure(Treasure.DIAMOND);
    location.pickUpTreasures();
    assertEquals(location.getTreasures().size(), 0);
    assertFalse(location.getTreasures().contains(Treasure.SAPPHIRE));
    assertFalse(location.getTreasures().contains(Treasure.DIAMOND));

    //test tunnel
    directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    location = new Tunnel(0, 0, directions);
    location.addTreasure(Treasure.SAPPHIRE);
    location.pickUpTreasures();
    assertEquals(location.getTreasures().size(), 0);
    assertFalse(location.getTreasures().contains(Treasure.SAPPHIRE));
  }

  @Test
  public void testPickupArrows() {
    //test cave
    List<Direction> directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    directions.add(Direction.EAST);
    Location location = new Cave(0, 0, directions);
    location.addArrow(Arrow.CROOKED);
    location.addArrow(Arrow.CROOKED);
    location.pickUpArrows();
    assertEquals(location.getArrows().size(), 0);
    assertFalse(location.getArrows().contains(Arrow.CROOKED));

    //test tunnel
    directions = new ArrayList<Direction>();
    directions.add(Direction.NORTH);
    directions.add(Direction.SOUTH);
    location = new Tunnel(0, 0, directions);
    location.addTreasure(Treasure.SAPPHIRE);
    location.pickUpTreasures();
    assertEquals(location.getArrows().size(), 0);
    assertFalse(location.getArrows().contains(Arrow.CROOKED));
  }

  @Test
  public void testGetCopy() {
    //test cave
    List<Direction> validMove = new ArrayList<Direction>();
    validMove.add(Direction.SOUTH);
    Location location = new Cave(2, 1, validMove);
    location.addArrow(Arrow.CROOKED);
    location.addMonster(new Otyugh(2));
    location.addTreasure(Treasure.SAPPHIRE);
    location.equals(location.getCopy());
    assertTrue(location.equals(location.getCopy()));

    //test tunnel
    validMove = new ArrayList<Direction>();
    validMove.add(Direction.SOUTH);
    validMove.add(Direction.NORTH);
    location = new Tunnel(2, 1, validMove);
    location.addArrow(Arrow.CROOKED);
    location.addMonster(new Otyugh(2));
    location.addTreasure(Treasure.SAPPHIRE);
    assertTrue(location.equals(location.getCopy()));

  }

  @Test
  public void testEquals() {
    List<Direction> validMove = new ArrayList<Direction>();
    validMove.add(Direction.EAST);
    Location location1 = new Cave(2, 3, validMove);
    location1.addTreasure(Treasure.DIAMOND);
    Location location2 = new Cave(2, 3, validMove);
    location2.addTreasure(Treasure.DIAMOND);
    assertEquals(location1, location2);
    location2.addTreasure(Treasure.SAPPHIRE);
    assertNotEquals(location1, location2);
  }

  @Test
  public void testHashCode() {
    List<Direction> validMove = new ArrayList<Direction>();
    validMove.add(Direction.EAST);
    validMove.add(Direction.NORTH);
    Location location1 = new Tunnel(2, 3, validMove);
    location1.addTreasure(Treasure.DIAMOND);
    Location location2 = new Tunnel(2, 3, validMove);
    location2.addTreasure(Treasure.DIAMOND);
    assertEquals(location1.hashCode(), location2.hashCode());
    location2.addTreasure(Treasure.SAPPHIRE);
    assertEquals(location1.hashCode(), location2.hashCode());
  }


}
