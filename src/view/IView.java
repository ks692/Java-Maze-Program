package view;

import controller.Features;

/**
 * The interface for our intro view class.
 * This class helps in creating and starting
 * the model with given specifications.
 */
public interface IView {

  /**
   * Display the view. Starts and shows the view.
   */
  void display();

  /**
   * closes the view.
   *
   * @return exit code.
   */
  int close();

  /**
   * Sets the features in the dungeon.
   */
  void setFeatures(Features features);

  /**
   * Sets the text to status label in the
   * view.
   */
  void setLabel(String text);


}
