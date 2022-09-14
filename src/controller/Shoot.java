package controller;

import dungeon.Dungeon;
import location.Direction;

/**
 * Shoot is a dungeon command that allows the
 * player to shoot in a direction and to a
 * specified distance.This command changes the
 * number of arrows the player has after he shoots.
 * it also aids in damaging the monsters at a distance.
 */
class Shoot extends AbstractDungeonCommand {
  private String inputDistance;

  /**
   * Constructor for the Move command.
   *
   * @param inputDirection the direction in which the
   *                       arrow is shot.
   * @param inputDistance  the distance to which the arrow
   *                       moves.
   */
  public Shoot(String inputDirection, String inputDistance) {
    super(inputDirection);
    if (inputDistance == null) {
      throw new IllegalArgumentException("direction or distance is null");
    }
    this.inputDistance = inputDistance;
  }

  @Override
  public String apply(Dungeon model) {
    Direction direction = this.translateInputDirection(getInput());
    if (direction == null) {
      return "invalid direction";
    }
    int dist = 0;
    try {
      int temp = Integer.parseInt(inputDistance);
      if (temp < 0) {
        return "negative input distance";
      }
      dist = temp;
    } catch (NumberFormatException exception) {
      return "invalid number";
    }
    if (!model.canShoot()) {
      return "Out of Arrows. Explore Dungeon";
    }
    boolean result = model.shootArrow(dist, direction);
    if (result) {
      return "There is a huge Growl. You have hit something.";
    }
    return "Arrow Shot into darkness.";
  }
}
