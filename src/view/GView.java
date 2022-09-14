package view;

import controller.Features;

/**
 * The interface for our game view class.
 * This class helps in starting and ending
 * the game view.
 */
public interface GView {


  /**
   * Display the view.
   */
  void display();

  /**
   * refreshes the view.
   */
  void refresh();

  /**
   * closes the view.
   */
  void close();

  /**
   * initializes the game view.
   * Sets up the game board.
   */
  void initialize();


  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param features the set of feature callbacks as a Features object
   * @throws IllegalArgumentException if features is null.
   */
  void setFeatures(Features features);

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard
   * listener attached to it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Show Game over Dialog.
   */
  void gameOver();


  /**
   * Show Game over Pit Dialog.
   */
  void gameOverPit();

  /**
   * Show Game over when killed by monster Dialog.
   */
  void gameOverKilled();
}
