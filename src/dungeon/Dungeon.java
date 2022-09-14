package dungeon;


import location.Direction;
import location.Location;
import player.Player;

/**
 * Dungeon is a game model. it allows the player to
 * navigate a maze made of tunnels and caves, collect
 * treasures and reach the end location.
 */
public interface Dungeon {

  /**
   * Assign treasures to the certain percentage of
   * locations available. The percentage is defined
   * by the given percentage and a random percentage
   * less than a whole. you can only assign treasures
   * before the game has started.
   *
   * @throws IllegalArgumentException if percentage is less than zero.
   * @throws IllegalArgumentException if game has already started.
   */
  void assignTreasuresAndWeapons(int percentage) throws IllegalArgumentException;


  /**
   * Assign Monsters to the dungeon based on the input number.
   *
   * @param numMonsters number of monsters to assign.
   * @throws IllegalArgumentException if numMonsters is less than 1.
   * @throws IllegalArgumentException if numMonsters is greater than caves formed.
   * @throws IllegalArgumentException if game has already started.
   */
  void assignMonsters(int numMonsters) throws IllegalArgumentException;

  /**
   * Create the player Description which
   * includes the player name,location and treasures
   * he has collected till now.
   *
   * @return the player description.
   */
  String getPlayerDescription();

  /**
   * Create the location Description which
   * includes the coordinates, valid moves,
   * treasures available in the location.
   *
   * @return the location description.
   */
  String getLocationDescription();

  /**
   * Move the player from the current
   * location in the direction.
   *
   * @param direction the direction for the
   *                  player to move in.
   * @return true if the move is valid.
   *         false if the move is invalid.
   * @throws IllegalArgumentException if the direction is null.
   * @throws IllegalArgumentException if the game is over.
   */
  boolean makeMove(Direction direction) throws IllegalArgumentException;

  /**
   * Aim and Shoot an arrow at a monster in a direction
   * and to a certain distance.
   *
   * @param direction the direction for the
   *                  arrow to move in.
   * @param distance  distance to which the arrow
   *                  is shot.
   * @return true if the arrow successfully hit a monster.
   *         false if the arrow does not hit a monster.
   * @throws IllegalArgumentException if the direction is null.
   * @throws IllegalArgumentException if the distance is negative.
   * @throws IllegalStateException    if the user does not have any arrows.
   */
  boolean shootArrow(int distance, Direction direction) throws IllegalArgumentException;

  /**
   * Returns if the user has enough arrows to shoot.
   *
   * @return if the user has arrows or else false.
   */
  boolean canShoot();

  /**
   * Get the start location of the
   * player in the game.
   *
   * @return the start location of the
   *         player in the game.
   */
  Location getStartLocation();

  /**
   * Get the current location of the
   * player in the game.
   *
   * @return the current location of the
   *         player in the game.
   */
  Location getCurrentLocation();

  /**
   * Get the end location of the
   * player in the game. Game will stop
   * after reaching the end location.
   *
   * @return the end location of the
   *         player in the game.
   */
  Location getEndLocation();

  /**
   * Get the exact location grid of the player.
   *
   * @return the location grid of the player.
   */
  Location[][] getLocationGrid();

  /**
   * Player picks up the treasures in the current location.
   */
  void pickupTreasures();

  /**
   * get the pit location.
   *
   * @return the pit location.
   */
  Location getPitLocation();

  /**
   * Player picks up the arrows in the current location.
   */
  void pickupArrows();

  /**
   * return if the current location is the end location.
   *
   * @return true if the current location is end location
   *         else return false.
   */
  boolean isGoalState();

  /**
   * return true if player is killed or
   * player reaches end Location.
   *
   * @return true if the game has ended.
   *         else return false.
   */
  boolean isGameOver();

  /**
   * assign a pit location to the dungeon.
   */
  void assignPitLocation();

  /**
   * return if the current location has a
   * strong smell of  the monster. A strong
   * smell appears if the monster is one
   * distance away.
   *
   * @return true if the current location
   *         contains strong smell
   *         else return false.
   */
  boolean detectStrongSmell();

  /**
   * return if the current location has a
   * weak smell of  the monster. A weak
   * smell appears if the monster is two
   * distance away.
   *
   * @return true if the current location
   *         contains strong smell
   *         else return false.
   */
  boolean detectWeakSmell();

  /**
   * return if the current location has a
   * pit nearby.
   *
   * @return true if the current location
   *         contains pit nearby.
   *         else return false.
   */
  boolean detectPitNearby();

  /**
   * return the boolean array of visited nodes.
   *
   * @return return the boolean array of visited nodes.
   */
  boolean[][] getVisited();

  /**
   * Get the player and his attributes.
   *
   * @return return the player.
   */
  Player getPlayer();

  /**
   * reset the game state. restart the whole
   * game with same locations, treasures and monsters.
   */
  void resetGameState();


  /**
   * assigns the thief location to
   * the dungeon. thief starts moving
   * randomly after that.
   */
  void assignThiefLocation();

  /**
   * get the thief location.
   */
  Location getThiefLocation();


}
