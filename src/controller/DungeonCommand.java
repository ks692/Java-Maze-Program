package controller;

import dungeon.Dungeon;

/**
 * Commands for the dungeon interface.
 * All the user inputs can be converted
 * to command. They would be implementing
 * the interface.
 */
public interface DungeonCommand {

  /**
   * Starting point for the controller.
   * it processes the user input and
   * provides it to the model.
   *
   * @param model the model to use
   * @return a result of performing
   *         the particular command.
   */
  String apply(Dungeon model);

}
