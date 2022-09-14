package driver;

import java.io.InputStreamReader;

import controller.DungeonConsoleController;
import controller.DungeonController;
import controller.GraphicalController;
import controller.GraphicalControllerImpl;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.DungeonViewModel;
import dungeon.DungeonViewModelImpl;
import player.Player;
import player.PlayerImpl;
import view.GameView;
import view.IView;
import view.IntroView;

/**
 * This class is used to run our Program and display
 * usability to the client. Shows
 * how to handle various functionalities of the system.
 */
public class Driver {
  /**
   * Example Run:
   * Demonstrates various functions of the dungeon.
   */
  public static void main(String[] args) {
    //arguments should contain rows, cols, interconnectivity, wrapping, percentage,monsters.
    if (args == null) {
      System.out.println("Illegal Arguments");
      return;
    }
    Dungeon model;
    if (args.length == 0) {
      IView introView = new IntroView();
      Player player = new PlayerImpl("Mario");
      model = new DungeonImpl(5, 5, 0, false, player, 3);
      model.assignTreasuresAndWeapons(55);
      model.assignMonsters(7);
      DungeonViewModel viewModel = new DungeonViewModelImpl(model);
      GameView gameView = new GameView(viewModel);
      GraphicalController controller = new GraphicalControllerImpl(model, introView, gameView);
      controller.playGame();
      return;
    }
    if (args.length != 6) {
      System.out.println("Wrong number of arguments");
      return;
    }
    for (int i = 0; i < 6; i++) {
      if (args[i] == null) {
        System.out.println("Illegal Arguments");
        return;
      }
    }
    int rows;
    int cols;
    int interConnectivity;
    int percentage;
    int numMonsters;
    try {
      rows = Integer.parseInt(args[0]);
      cols = Integer.parseInt(args[1]);
      interConnectivity = Integer.parseInt(args[2]);
      percentage = Integer.parseInt(args[4]);
      numMonsters = Integer.parseInt(args[5]);
    } catch (NumberFormatException exception) {
      System.out.println("Wrong arguments");
      return;
    }
    boolean wrapping;
    String wrap = args[3].toLowerCase();
    if (wrap.equals("true")) {
      wrapping = true;
    } else if (wrap.equals("false")) {
      wrapping = false;
    } else {
      System.out.println("Illegal Arguments");
      return;
    }
    Player player = new PlayerImpl("Mario");
    Dungeon dungeon;
    try {
      dungeon = new DungeonImpl(rows, cols, interConnectivity, wrapping, player);
      dungeon.assignTreasuresAndWeapons(percentage);
      dungeon.assignMonsters(numMonsters);
    } catch (IllegalArgumentException exception) {
      System.out.println("Caves cannot be formed with following arguments");
      return;
    }
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    DungeonController controller = new DungeonConsoleController(input, output);
    try {
      controller.playDungeonGame(dungeon);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid model");
      System.out.println(e.getMessage());
      return;
    }
  }

}
