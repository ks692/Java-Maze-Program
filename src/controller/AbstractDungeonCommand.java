package controller;


import location.Direction;

/**
 * Abstract implementation of dungeon commands.
 * This class contains all the common methods
 * for the dungeon commands that can be reused
 * again.
 */
abstract class AbstractDungeonCommand implements DungeonCommand {

  private final String input;

  /**
   * Constructor for the AbstractDungeon command.
   *
   * @param input takes the input from the user
   *              and passes it to the command.
   */
  public AbstractDungeonCommand(String input) {
    if (input == null) {
      throw new IllegalArgumentException("direction is null");
    }
    this.input = input;
  }

  /**
   * get the input given by the user for the command.
   *
   * @return the input given by the user for the command.
   */
  protected String getInput() {
    return input;
  }


  /**
   * The method translates the user commands
   * W,A,S,D into true directions for the player
   * if the input is not one among W,A,S,D it
   * returns null.
   *
   * @param input input String given by the user.
   * @return a direction for valid direction.
   *         otherwise, return null.
   */
  protected Direction translateInputDirection(String input) {
    if (input.equalsIgnoreCase("W")) {
      return Direction.NORTH;
    } else if (input.equalsIgnoreCase("A")) {
      return Direction.WEST;
    } else if (input.equalsIgnoreCase("D")) {
      return Direction.EAST;
    } else if (input.equalsIgnoreCase("S")) {
      return Direction.SOUTH;
    } else {
      return null;
    }
  }

}
