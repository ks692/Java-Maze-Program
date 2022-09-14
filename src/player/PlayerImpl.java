package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weapon.Arrow;

/**
 * PlayerImpl  is a player that contests in a game
 * by moving through a dungeon, collecting treasures
 * and finding the end node. it is represented by a
 * name, current location and treasures.
 */
public final class PlayerImpl implements Player {

  private final String name;
  private final List<Treasure> treasureBag;
  private final Map<Arrow, Integer> arrowBag;


  /**
   * Constructs a {@link Player} object in terms of name.
   * this constructor initializes based on the input params.
   *
   * @param name The name of the player.
   * @throws IllegalArgumentException if name is null.
   */
  public PlayerImpl(String name) {
    if (name == null) {
      throw new IllegalArgumentException("name is null");
    }
    //add the location.
    this.name = name;
    this.treasureBag = new ArrayList<>();
    this.arrowBag = new HashMap<Arrow, Integer>();
  }

  /**
   * Constructs a {@link Player} object in terms of name,
   * treasure bag. Used to create a copy.
   */
  private PlayerImpl(String name, List<Treasure> treasureBag, Map<Arrow, Integer> arrowBag) {
    if (name == null) {
      throw new IllegalArgumentException("name is null");
    }
    this.name = name;
    this.treasureBag = treasureBag;
    this.arrowBag = arrowBag;
  }


  @Override
  public String getName() {
    return this.name;
  }


  @Override
  public void pickupTreasures(List<Treasure> treasures) {
    List<Treasure> treasureLocation = new ArrayList<Treasure>();
    for (Treasure treasure : treasures) {
      treasureLocation.add(treasure);
    }
    this.treasureBag.addAll(treasureLocation);
  }

  @Override
  public List<Treasure> getTreasures() {
    List<Treasure> temp = new ArrayList<Treasure>();
    for (Treasure treasure : treasureBag) {
      temp.add(treasure);
    }
    return temp;
  }

  @Override
  public Map<Arrow, Integer> getArrows() {
    Map<Arrow, Integer> map = new HashMap<Arrow, Integer>();
    for (Map.Entry<Arrow, Integer> entry : arrowBag.entrySet()) {
      map.put(entry.getKey(), entry.getValue());
    }
    return map;
  }

  @Override
  public void addArrow(Arrow arrow) {
    if (arrow == null) {
      throw new IllegalArgumentException("arrow is null");
    }
    if (this.arrowBag.containsKey(arrow)) {
      this.arrowBag.put(arrow, this.arrowBag.get(arrow) + 1);
    } else {
      this.arrowBag.put(arrow, 1);
    }
  }

  @Override
  public void useArrow(Arrow arrow) {
    if (arrow == null) {
      throw new IllegalArgumentException("arrow is null");
    }
    if (!this.arrowBag.containsKey(arrow)) {
      throw new IllegalArgumentException("arrow is not in the bag");
    }
    if (this.arrowBag.containsKey(arrow)) {
      int count = this.arrowBag.get(arrow);
      if (count == 0) {
        throw new IllegalArgumentException("bag is empty");
      }
      this.arrowBag.put(arrow, count - 1);
    }
  }

  @Override
  public void emptyTreasures() {
    this.treasureBag.clear();
  }

  @Override
  public Player getCopy() {
    return new PlayerImpl(name, getTreasures(), getArrows());
  }


}
