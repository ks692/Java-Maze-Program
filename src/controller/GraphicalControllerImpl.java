package controller;

import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.DungeonViewModel;
import dungeon.DungeonViewModelImpl;
import location.Direction;
import location.Location;
import player.Player;
import player.PlayerImpl;
import view.GView;
import view.GameView;
import view.IView;

/**
 * Represents a Graphical Controller for Dungeon:
 * this controller follows mvc pattern and acts as
 * interface between view and controller.
 */
public class GraphicalControllerImpl implements GraphicalController, Features {

  private Dungeon model;
  private IView introView;
  private GView gameView;

  /**
   * Constructs a {@link GraphicalControllerImpl} object in terms of model,
   * intro view and game view. this constructor initializes based on
   * the input params.
   *
   * @param model     The model supplied to the controller.
   * @param introView The intro view for the player to configure.
   * @param gameView  The game view for the player to configure.
   * @throws IllegalArgumentException if any of the input arguments is null.
   */
  public GraphicalControllerImpl(Dungeon model, IView introView, GView gameView) {
    if (model == null || introView == null || gameView == null) {
      throw new IllegalArgumentException("input arguments are invalid");
    }
    this.model = model;
    this.introView = introView;
    this.gameView = gameView;
    introView.setFeatures(this);
    gameView.setFeatures(this);
  }


  @Override
  public void submitConfiguration(int rows,
                                  int cols,
                                  int interconnectivity,
                                  boolean wrapping,
                                  int percentage,
                                  int monsters) {
    //try to create a model.
    try {
      Player player = new PlayerImpl("Mario");
      model = new DungeonImpl(rows, cols, interconnectivity, wrapping, player);
      model.assignTreasuresAndWeapons(percentage);
      model.assignMonsters(monsters);
      model.assignPitLocation();
      model.assignThiefLocation();
    } catch (IllegalArgumentException e) {
      introView.setLabel(e.getMessage());
      return;
    }
    DungeonViewModel viewModel = new DungeonViewModelImpl(model);
    //pass the view model to game view and start it.
    gameView = new GameView(viewModel);
    gameView.setFeatures(this);
    introView.close();
    gameView.initialize();
    gameView.refresh();
    gameView.display();
  }

  @Override
  public void quitGame() {
    gameView.close();
    System.exit(0);
  }

  @Override
  public void restartGame() {
    gameView.close();
    introView.display();
  }

  @Override
  public void restartSameGame() {
    model.resetGameState();
    gameView.refresh();
  }

  @Override
  public void pickupTreasures() {
    model.pickupTreasures();
    gameView.refresh();
  }

  @Override
  public void pickupArrows() {
    model.pickupArrows();
    gameView.refresh();
  }

  @Override
  public void movePlayer(int row, int col) {
    Direction direction = findDirection(row, col);
    makeMove(direction);
  }

  @Override
  public void movePlayer(Direction direction) {
    this.makeMove(direction);
  }

  @Override
  public boolean canShoot() {
    return model.canShoot();
  }

  @Override
  public boolean shootArrow(int distance, Direction direction) {
    boolean flag = model.shootArrow(distance, direction);
    gameView.refresh();
    return flag;
  }

  private void makeMove(Direction direction) {
    checkGameOver();
    if (direction != null) {
      if (!model.isGameOver() && model.makeMove(direction)) {
        gameView.refresh();
        checkGameOver();
      }
    }
  }

  private void checkGameOver() {
    if (model.isGameOver()) {
      if (isPitLocation(model.getCurrentLocation())) {
        gameView.gameOverPit();
      } else if (model.isGoalState()
                 && model.getCurrentLocation()
                         .getMonsters().size() == 0) {
        gameView.gameOver();
      } else {
        gameView.gameOverKilled();
      }
      return;
    }
  }

  private boolean isPitLocation(Location location) {
    Location copy = location.getCopy();
    Location pitLocation = model.getPitLocation();
    if (pitLocation == null) {
      return false;
    }
    return (pitLocation.getXLocation() == copy.getXLocation()
            && pitLocation.getYLocation() == copy.getYLocation());
  }

  private Direction findDirection(int row, int col) {
    Direction direction = null;
    int rowsDungeon = model.getLocationGrid().length;
    int colsDungeon = model.getLocationGrid()[0].length;
    Location location = model.getCurrentLocation();
    int rowVal = row - location.getXLocation();
    int colVal = col - location.getYLocation();
    //System.out.println(rowVal+" "+colVal);
    if (rowVal == 0 && colVal == 1 || rowVal == 0 && colVal == 1 - rowsDungeon) {
      //System.out.println("east");
      direction = Direction.EAST;
    } else if (rowVal == 0 && colVal == -1 || rowVal == 0 && colVal == rowsDungeon - 1) {
      //System.out.println("west");
      direction = Direction.WEST;
    } else if (rowVal == -1 && colVal == 0 || colVal == 0 && rowVal == colsDungeon - 1) {
      //System.out.println("north");
      direction = Direction.NORTH;
    } else if (rowVal == 1 && colVal == 0 || colVal == 0 && rowVal == 1 - colsDungeon) {
      //System.out.println("south");
      direction = Direction.SOUTH;
    }
    return direction;
  }

  @Override
  public void playGame() {
    introView.display();
  }
}
