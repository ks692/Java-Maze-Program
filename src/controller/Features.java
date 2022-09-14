package controller;

import location.Direction;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller. How
 * the view uses them as callbacks is completely up to how the view is designed
 * (e.g. it could use them as a callback for a button, or a callback for a
 * dialog box, or a set of text inputs, etc.)
 *
 * <p>Each function is designed to take in the necessary data to complete that
 * functionality.
 */
public interface Features {
  /**
   * This method creates a model based on the configuration
   * given in the intro-view and displays game view if the
   * model is successfully created.
   *
   * @param rows              the number of rows in the configuration.
   * @param cols              the number of cols in the configuration.
   * @param interconnectivity the number of extra connectivity
   *                          edges in the configuration.
   * @param wrapping          the wrapping configuration of the dungeon.
   * @param percentage        the total treasure percentage in the configuration.
   * @param monsters          the total number of monsters in the dungeon.
   */
  public void submitConfiguration(int rows,
                                  int cols,
                                  int interconnectivity,
                                  boolean wrapping,
                                  int percentage,
                                  int monsters);

  /**
   * Quits the game. Closes the game
   * view as well as exits the game.
   */
  public void quitGame();

  /**
   * restart the game as a new game.
   * The User is redirected to introduction
   * screen and can select to restart game.
   */
  public void restartGame();

  /**
   * restart the same game
   * again. with the same
   * dungeon.
   */
  public void restartSameGame();

  /**
   * Player picks up treasures.
   */
  public void pickupTreasures();

  /**
   * Player picks up Arrows.
   */
  public void pickupArrows();

  /**
   * move player based on the
   * row and column as the input.
   *
   * @param row row which was clicked.
   * @param col col which was clicked.
   */
  public void movePlayer(int row, int col);

  /**
   * move player based on the
   * input direction.
   *
   * @param direction direction in which player
   *                  should move.
   */
  public void movePlayer(Direction direction);

  /**
   * returns if the player can shoot arrows.
   *
   * @return true if the player can shoot.
   */
  public boolean canShoot();

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
   */
  boolean shootArrow(int distance, Direction direction);

}
