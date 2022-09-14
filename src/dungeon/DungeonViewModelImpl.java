package dungeon;

import location.Location;
import player.Player;

/**
 * This Class acts as an intermediate between
 * view and model providing only relevant information
 * to the view from the model.
 */
public class DungeonViewModelImpl implements DungeonViewModel {
  private Dungeon model;

  /**
   * Constructor for the DungeonViewModelImpl.
   * it takes input as the dungeon model.
   *
   * @param dungeon The dungeon model.
   * @throws IllegalArgumentException if the dungeon is null.
   */
  public DungeonViewModelImpl(Dungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("dungeon is null");
    }
    this.model = dungeon;
  }

  @Override
  public Location[][] getLocations() {
    return model.getLocationGrid();
  }

  @Override
  public boolean[][] getVisited() {
    return model.getVisited();
  }

  @Override
  public Location getCurrentLocation() {
    return model.getCurrentLocation();
  }

  @Override
  public Player getPlayer() {
    return model.getPlayer();
  }


  @Override
  public boolean getStrongSmell() {
    return model.detectStrongSmell();
  }

  @Override
  public boolean getWeakSmell() {
    return model.detectWeakSmell();
  }

  @Override
  public boolean detectPitNearby() {
    return model.detectPitNearby();
  }

  @Override
  public Location getPitLocation() {
    return model.getPitLocation();
  }

  @Override
  public Location getThiefLocation() {
    return model.getThiefLocation();
  }

}
