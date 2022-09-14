package location;

import java.util.ArrayList;
import java.util.List;

import monster.Monster;
import player.Treasure;
import weapon.Arrow;

/**
 * A cave is a location which can have
 * multiple entrances. it is represented
 * by coordinates, valid moves and treasures.
 */
public class Cave extends AbstractLocation {
  /**
   * Constructs a {@link Cave} object in terms of xLocation,
   * yLocation, validMoves and treasures
   * Protected constructor for use by subclasses.
   *
   * @param xLocation  xCoordinate of the Location.
   * @param yLocation  yCoordinate of the Location.
   * @param validMoves the valid moves possible from
   *                   that location.
   * @throws IllegalArgumentException if valid moves is null
   *                                  or duplicate moves are present.
   * @throws IllegalArgumentException if directions in the valid moves
   *                                  size is 1,3,4.
   * @throws IllegalArgumentException if directions in the valid moves
   *                                  is null.
   */
  public Cave(int xLocation, int yLocation, List<Direction> validMoves) {
    super(xLocation, yLocation, validMoves);
    int size = validMoves.size();
    if (size != 1 && size != 3 && size != 4) {
      throw new IllegalArgumentException("The cave valid moves size is incorrect");
    }
  }


  @Override
  public Location getCopy() {
    List<Direction> directions = new ArrayList<Direction>();
    for (Direction direction : this.getValidMoves()) {
      directions.add(direction);
    }
    Location location = new Cave(getXLocation(), getYLocation(), directions);
    for (Treasure treasure : this.getTreasures()) {
      location.addTreasure(treasure);
    }
    for (Monster monster : getMonsters()) {
      location.addMonster(monster.getCopy());
    }
    for (Arrow arrow : getArrows()) {
      location.addArrow(arrow);
    }
    return location;
  }

  @Override
  public String getType() {
    return "Cave";
  }


}
