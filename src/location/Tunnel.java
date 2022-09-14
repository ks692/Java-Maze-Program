package location;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weapon.Arrow;

/**
 * A Tunnel is a location which can have
 * two entrances. it is represented
 * by coordinates, valid moves and treasures.
 */
public final class Tunnel extends AbstractLocation {

  /**
   * Constructs a {@link Tunnel} object in terms of xLocation,
   * yLocation, validMoves and treasures
   * Protected constructor for use by subclasses.
   *
   * @param xLocation  xCoordinate of the Location.
   * @param yLocation  yCoordinate of the Location.
   * @param validMoves the valid moves possible from
   *                   that location.
   * @throws IllegalArgumentException if valid moves or treasures are null
   *                                  or duplicate moves are present.
   * @throws IllegalArgumentException if directions or treasures in
   *                                  the valid moves or treasures is null.
   */
  public Tunnel(int xLocation, int yLocation, List<Direction> validMoves) {
    super(xLocation, yLocation, validMoves);
    validateDirection(validMoves);
  }

  private void validateDirection(List<Direction> validMoves) {
    Set<Direction> temp = new HashSet<Direction>();
    for (Direction direction : validMoves) {
      temp.add(direction);
    }
    final int size = 2;
    if (temp.size() != size) {
      throw new IllegalArgumentException("entrances not equal to two.");
    }
  }


  @Override
  public Location getCopy() {
    Location location = new Tunnel(getXLocation(), getYLocation(), getValidMoves());
    for (Arrow arrow : arrows) {
      location.addArrow(arrow);
    }
    return location;
  }

  @Override
  public String getType() {
    return "Tunnel";
  }
}
