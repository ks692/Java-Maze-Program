package controller;


import dungeon.Dungeon;

/**
 * Represents a Controller for Dungeon:
 * handles user moves by executing them using the model;
 * conveys move outcomes to the user in some form.
 */
public interface DungeonController {
  /**
   * Execute a single game of Dungeon given a Dungeon Model. When the game is over,
   * the playGame method ends.
   *
   * @param model a non-null Dungeon Model.
   * @throws IllegalArgumentException if the model is null.
   * @throws IllegalStateException    if the input is not being able to get appended.
   */
  void playDungeonGame(Dungeon model);
}
