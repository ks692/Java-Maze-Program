package dungeon;

import location.Location;
import player.Player;

/**
 * This interface has the Dungeon View
 * model. This interface can be used
 * by the views to pull data from the
 * model without mutating it.
 */
public interface DungeonViewModel {

  /**
   * Get the exact locations and its information
   * in the dungeon.
   *
   * @return the locations in the dungeon.
   */
  public Location[][] getLocations();

  /**
   * Get the visited locations in the dungeon.
   *
   * @return the visited locations in the dungeon.
   */
  public boolean[][] getVisited();

  /**
   * Get the current Location of the player.
   *
   * @return the current location of the player.
   */
  public Location getCurrentLocation();

  /**
   * Get the details of the player.
   *
   * @return the player details.
   */
  public Player getPlayer();


  /**
   * Get if there is a strong smell in the
   * location.
   *
   * @return the Strong smell in the location.
   */
  public boolean getStrongSmell();

  /**
   * Get if there is a Weak smell in the
   * location.
   *
   * @return the Weak smell in the location.
   */
  public boolean getWeakSmell();

  /**
   * Get if there is a pit
   * location nearby.
   *
   * @return the pit location traces.
   */
  public boolean detectPitNearby();

  /**
   * Get the pit location in the dungeon.
   *
   * @return the pit location.
   */
  public Location getPitLocation();

  /**
   * Get the thief location in the dungeon.
   *
   * @return the thief location.
   */
  public Location getThiefLocation();


}
