package location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import monster.Monster;
import player.Treasure;
import weapon.Arrow;

/**
 * Abstract base class for implementation of Location. This
 * class contains all the method definitions common to the
 * concrete implementation of the {@link Location}.
 * This class is represented by a xLocation,yLocation valid moves
 * and treasures in the location.
 */
public abstract class AbstractLocation implements Location {
  private final int xLocation;
  private final int yLocation;
  protected List<Direction> validMoves;
  protected List<Treasure> treasures;
  protected List<Monster> monsters;
  protected List<Arrow> arrows;

  /**
   * Constructs a {@link AbstractLocation} object in terms of xLocation,
   * yLocation, validMoves and treasures
   * Protected constructor for use by subclasses.
   *
   * @param xLocation  xCoordinate of the Location.
   * @param yLocation  yCoordinate of the Location.
   * @param validMoves the valid moves possible from
   *                   that location.
   * @throws IllegalArgumentException if valid moves or treasures or monsters or
   *                                  arrows are null.
   *                                  are null or duplicate moves are present.
   * @throws IllegalArgumentException if directions or treasures in
   *                                  the valid moves or treasures is null.
   * @Param arrows the arrows in that Location.
   */
  protected AbstractLocation(int xLocation,
                             int yLocation,
                             List<Direction> validMoves) {
    if (validMoves == null) {
      throw new IllegalArgumentException("null argument inputs");
    }
    if (validMoves.size() > 4) {
      throw new IllegalArgumentException("valid moves can only be 4");
    }


    validateDirection(validMoves);

    this.xLocation = xLocation;
    this.yLocation = yLocation;
    this.treasures = new ArrayList<Treasure>();
    this.validMoves = validMoves;
    this.monsters = new ArrayList<Monster>();
    this.arrows = new ArrayList<Arrow>();

  }


  /**
   * validates the Treasures by checking for null value and null
   * value in the treasures.
   */
  protected void validateTreasures(List<Treasure> treasures) {
    if (treasures == null) {
      throw new IllegalArgumentException("treasures is null");
    }
    for (Treasure treasure : treasures) {
      if (treasure == null) {
        throw new IllegalArgumentException("treasure is null");
      }
    }
  }


  private void validateDirection(List<Direction> validMoves) {
    Set<Direction> temp = new HashSet<Direction>();
    for (Direction direction : validMoves) {
      if (direction == null) {
        throw new IllegalArgumentException("direction cannot be null");
      }
      if (temp.contains(direction)) {
        throw new IllegalArgumentException("duplicate direction");
      }
      temp.add(direction);
    }
  }

  /**
   * Update the Treasures available in the location.
   */
  private void updateTreasures(List<Treasure> treasures) {
    List<Treasure> treasureBag = new ArrayList<Treasure>();
    for (Treasure treasure : treasureBag) {
      treasureBag.add(treasure);
    }
    validateTreasures(treasureBag);
    this.treasures = treasureBag;
  }

  @Override
  public int getXLocation() {
    return xLocation;
  }

  @Override
  public int getYLocation() {
    return yLocation;
  }

  @Override
  public List<Treasure> getTreasures() {
    final List<Treasure> temp = new ArrayList<Treasure>();
    for (Treasure treasure : treasures) {
      temp.add(treasure);
    }
    return temp;
  }

  @Override
  public List<Arrow> getArrows() {
    final List<Arrow> temp = new ArrayList<Arrow>();
    for (Arrow arrow : arrows) {
      temp.add(arrow);
    }
    return temp;
  }

  @Override
  public List<Monster> getMonsters() {
    final List<Monster> temp = new ArrayList<Monster>();
    for (Monster monster : monsters) {
      temp.add(monster.getCopy());
    }
    return temp;
  }

  @Override
  public List<Direction> getValidMoves() {
    final List<Direction> temp = new ArrayList<Direction>();
    for (Direction direction : validMoves) {
      temp.add(direction);
    }
    return temp;
  }

  @Override
  public void pickUpTreasures() {
    this.updateTreasures(new ArrayList<Treasure>());
  }

  @Override
  public void pickUpArrows() {
    this.arrows = new ArrayList<Arrow>();
  }


  private <T extends Comparable> boolean compareLists(List<T> list1, List<T> list2) {
    boolean res = true;
    if (list1.size() != list2.size()) {
      return false;
    }
    Collections.sort(list1);
    Collections.sort(list2);
    for (int i = 0; i < list1.size(); i++) {
      if (!list1.get(i).equals(list2.get(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Location is equal to other location if coordinates
   * valid moves and treasures are same.
   *
   * @return the true if two locations are equal else false.
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof AbstractLocation)) {
      return false;
    }
    AbstractLocation location = (AbstractLocation) object;
    return this.xLocation == location.getXLocation()
           && this.yLocation == location.getYLocation()
           && this.compareLists(this.treasures, location.getTreasures())
           && this.compareLists(this.validMoves, location.getValidMoves())
           && this.compareLists(this.monsters, location.getMonsters())
           && this.compareLists(this.arrows, location.getArrows());
  }

  /**
   * Location unique hashcode.
   *
   * @return the hashcode for location.
   */
  @Override
  public int hashCode() {
    Collections.sort(treasures);
    Collections.sort(validMoves);
    Collections.sort(monsters);
    Collections.sort(arrows);
    return Objects.hash(xLocation, yLocation, treasures, validMoves, monsters, arrows);
  }

  @Override
  public boolean addMonster(Monster monster) throws IllegalArgumentException {
    if (monster == null) {
      throw new IllegalArgumentException("monster is null");
    }
    if (isTunnel()) {
      return false;
    }
    this.monsters.add(0, monster.getCopy());
    return true;
  }

  @Override
  public boolean removeMonster(Monster monster) throws IllegalArgumentException {
    if (monster == null) {
      throw new IllegalArgumentException("monster is null");
    }
    if (isTunnel()) {
      return false;
    }
    return monsters.remove(monster.getCopy());
  }

  @Override
  public boolean addTreasure(Treasure treasure) throws IllegalArgumentException {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure is null");
    }
    if (isTunnel()) {
      return false;
    }
    this.treasures.add(treasure);
    return true;
  }

  @Override
  public void addArrow(Arrow arrow) throws IllegalArgumentException {
    if (arrow == null) {
      throw new IllegalArgumentException("arrow is null");
    }
    this.arrows.add(arrow);
  }


  private boolean isTunnel() {
    final int tunnelSize = 2;
    return (this.validMoves.size()
            == tunnelSize);
  }

}
