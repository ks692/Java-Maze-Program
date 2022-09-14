package location;

import java.util.List;

import monster.Monster;
import player.Treasure;
import weapon.Arrow;

/**
 * This interface represents a Location in Dungeon. Provides
 * all the  methods helpful for storing and retrieving
 * information about a location.
 */
public interface Location {

  /**
   * Get the x co-ordinate of the location.
   *
   * @return x co-ordinate of the location.
   */
  int getXLocation();

  /**
   * Get the y co-ordinate of the location.
   *
   * @return y co-ordinate of the location.
   */
  int getYLocation();

  /**
   * Get the Treasures available in the location.
   *
   * @return the treasures in the location.
   */
  List<Treasure> getTreasures();

  /**
   * Get the Monsters in the location.
   *
   * @return the Monsters in the location.
   */
  List<Monster> getMonsters();

  /**
   * Get the Arrows in the location.
   *
   * @return the Arrows in the location.
   */
  List<Arrow> getArrows();

  /**
   * add a particular Monster to the location.
   * Caves can hold Monsters while tunnels cannot.
   * adding a Monster to tunnel returns false.
   *
   * @param monster Monster to be added to the location.
   * @return true if the monster is added or else false.
   * @throws IllegalArgumentException if monster is null.
   */
  boolean addMonster(Monster monster) throws IllegalArgumentException;

  /**
   * remove a particular Monster from the location.
   * Caves can hold Monsters while tunnels cannot.
   * removing a Monster to tunnel returns false.
   *
   * @param monster Monster to be added to the location.
   * @return true if the monster is removed or else false.
   * @throws IllegalArgumentException if monster is null.
   */
  boolean removeMonster(Monster monster) throws IllegalArgumentException;

  /**
   * add a particular treasure to the location.
   * Caves can hold treasures while tunnels cannot.
   * adding a treasure to tunnel returns false.
   *
   * @param treasure treasure to be added to the location.
   * @return true if the treasure is added or else false.
   * @throws IllegalArgumentException if treasure is null.
   */
  boolean addTreasure(Treasure treasure) throws IllegalArgumentException;

  /**
   * add a particular arrow to the location.
   * Arrows can be added to cave and tunnels.
   *
   * @param arrow arrow to be added to the location.
   * @throws IllegalArgumentException if treasure is null.
   */
  void addArrow(Arrow arrow) throws IllegalArgumentException;


  /**
   * Empties the treasures in that location.
   */
  void pickUpTreasures();

  /**
   * Empties the arrows in that location.
   */
  void pickUpArrows();


  /**
   * Get the possible Directions in which the
   * player can move.
   *
   * @return the directions in which the player can move.
   */
  List<Direction> getValidMoves();


  /**
   * Create a new copy of the Location object
   * with the given parameters. Written to maintain immutability.
   *
   * @return a new Location  object with the same information.
   */
  Location getCopy();

  /**
   * Get the type of location.
   *
   * @return the type of the location.
   */
  String getType();


}
