package controller;


import dungeon.Dungeon;
import location.Direction;

/**
 * Move is a dungeon command that allows the
 * player to move along a certain specified
 * direction in the dungeon.This command changes the
 * current location of the player. it also ends the
 * game if a monster is in the location or if the
 * player has reached the end state.
 */
class Move extends AbstractDungeonCommand {
  /**
   * Constructor for the Move command.
   *
   * @param input the direction in which
   *              the player is going to move in
   *              the dungeon. Players use W,A,S,D
   *              to move in the dungeon.
   */
  public Move(String input) {
    super(input);
  }


  @Override
  public String apply(Dungeon model) {
    Direction direction = this.translateInputDirection(getInput());
    if (direction == null) {
      return "Invalid input";
    }
    boolean result = model.makeMove(direction);
    if (result) {
      return String.format("Move Successful: %s", direction);
    }
    return "Invalid Move";
  }
}
