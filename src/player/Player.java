package player;


import java.util.List;
import java.util.Map;

import weapon.Arrow;

/**
 * Player contests in a game by moving through a
 * dungeon, collecting treasures and finding the end node.
 * This interface consists methods to provide functionality
 * to the player.
 */
public interface Player {

  /**
   * Get the name of the player.
   *
   * @return the name of the player.
   */
  String getName();


  /**
   * Player picks up the treasures in the current location.
   */
  void pickupTreasures(List<Treasure> treasures);

  /**
   * Get the treasures in the bag of the player.
   * The treasures in the bag consists of all the
   * treasure's player has collected while moving
   * through the maze.
   *
   * @return the treasures of the player.
   */
  List<Treasure> getTreasures();

  /**
   * Create a new copy of the Player object
   * with the given parameters. Written to maintain immutability.
   *
   * @return a new Player object with the same information.
   */
  Player getCopy();

  /**
   * Get the weapon and the count of the players arrowBag.
   *
   * @return a map containing the weapon and its count.
   */
  Map<Arrow, Integer> getArrows();

  /**
   * Add the arrow to the players arrow bag.
   *
   * @throws IllegalArgumentException if the arrow is null.
   */
  void addArrow(Arrow arrow);

  /**
   * use the arrow to the players arrow bag.
   *
   * @throws IllegalArgumentException if the arrow is null.
   * @throws IllegalArgumentException if the arrow is not in the bag.
   * @throws IllegalArgumentException if the arrow count is already zero.
   */
  void useArrow(Arrow arrow);

  /**
   * empties treasures from him.
   */
  void emptyTreasures();


}
