package controller;


import dungeon.Dungeon;

/**
 * Pickup is a dungeon command that allows the
 * player to gather either the treasures or arrows
 * in the place. This move will increase the treasures
 * or arrows of the player if it is present in the
 * location.
 */
class Pickup extends AbstractDungeonCommand {
  /**
   * Constructor for the Pickup command.
   *
   * @param input the input can be either
   *              "Treasure" or "Arrows"
   *              in upper or lower case
   *              to collect either treasures
   *              or arrows.
   */
  public Pickup(String input) {
    super(input);
  }

  @Override
  public String apply(Dungeon model) {
    String input = getInput();
    if (input.equalsIgnoreCase("treasure")) {
      model.pickupTreasures();
      StringBuilder builder = new StringBuilder();
      builder.append("The player has picked up ");
      builder.append("the treasures in that location.");
      return builder.toString();
    }

    if (input.equalsIgnoreCase("arrow")) {
      model.pickupArrows();
      StringBuilder builder = new StringBuilder();
      builder.append("The player has picked up ");
      builder.append("the arrows in that location.");
      return builder.toString();
    }
    return "Wrong Input for Pickup. Try Again.";
  }
}
