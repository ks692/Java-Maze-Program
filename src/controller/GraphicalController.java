package controller;

/**
 * Represents a Graphical Controller for Dungeon:
 * this controller follows mvc pattern and acts as
 * interface between view and controller. This interface
 * provides a method to start the game.
 */
public interface GraphicalController {

  /**
   * Execute a single game of Dungeon given a Dungeon Model. When the game is over,
   * the playGame method ends.
   */
  public void playGame();


}
