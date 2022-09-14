package controllertest;

import java.util.ArrayList;
import java.util.List;

import dungeon.Dungeon;
import location.Cave;
import location.Direction;
import location.Location;
import player.Player;

/**
 * This is a mock model class used
 * for testing our controller.
 */
public class MockModel implements Dungeon {

  private StringBuilder log;

  /**
   * Constructs a {@link MockModel} object in terms of log.
   *
   * @param log a log to record inputs by model.
   * @throws IllegalArgumentException if log is null.
   */
  public MockModel(StringBuilder log) {
    if (log == null) {
      throw new IllegalArgumentException("model is null");
    }
    this.log = log;
  }

  @Override
  public void assignTreasuresAndWeapons(int percentage) throws IllegalArgumentException {
    return;
  }

  @Override
  public void assignMonsters(int numMonsters) throws IllegalArgumentException {
    return;
  }

  @Override
  public String getPlayerDescription() {
    return "Player Description\n";
  }

  @Override
  public String getLocationDescription() {
    return "Location Description\n";
  }

  @Override
  public boolean makeMove(Direction direction) throws IllegalArgumentException {
    log.append(String.format("Direction: %s\n", direction.toString()));
    return false;
  }

  @Override
  public boolean shootArrow(int distance, Direction direction) throws IllegalArgumentException {
    log.append(String.format("Distance: %d\n", distance));
    log.append(String.format("Direction: %s\n", direction.toString()));
    return false;
  }

  @Override
  public boolean canShoot() {
    log.append("canShoot\n");
    return true;
  }

  @Override
  public Location getStartLocation() {
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.EAST);
    Location location = new Cave(0, 0, directionList);
    return location;
  }

  @Override
  public Location getCurrentLocation() {
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.EAST);
    Location location = new Cave(0, 1, directionList);
    return location;
  }

  @Override
  public Location getEndLocation() {
    List<Direction> directionList = new ArrayList<Direction>();
    directionList.add(Direction.EAST);
    Location location = new Cave(0, 2, directionList);
    return location;
  }

  @Override
  public Location[][] getLocationGrid() {
    log.append(String.format("getLocationGridCalled\n"));
    return new Location[4][4];
  }

  @Override
  public void pickupTreasures() {
    log.append("pickupTreasures\n");
    return;
  }

  @Override
  public Location getPitLocation() {
    log.append("getPitLocation\n");
    return null;
  }

  @Override
  public void pickupArrows() {
    log.append("pickupArrows\n");
    return;
  }

  @Override
  public boolean isGoalState() {
    log.append("isGoalState\n");
    return false;
  }

  @Override
  public boolean isGameOver() {
    log.append("isGameOver\n");
    return false;
  }

  @Override
  public void assignPitLocation() {
    log.append("assignPitLocation\n");
  }

  @Override
  public boolean detectStrongSmell() {
    log.append("detectStrongSmell\n");
    return false;
  }

  @Override
  public boolean detectWeakSmell() {
    log.append("detectWeakSmell\n");
    return false;
  }

  @Override
  public boolean detectPitNearby() {
    return false;
  }

  @Override
  public boolean[][] getVisited() {
    return new boolean[0][];
  }

  @Override
  public Player getPlayer() {
    return null;
  }

  @Override
  public void resetGameState() {
    log.append("resetGameState Called");
  }

  @Override
  public void assignThiefLocation() {
    log.append("thief location called");
  }

  @Override
  public Location getThiefLocation() {
    return null;
  }

}
