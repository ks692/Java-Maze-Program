package controller;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import dungeon.Dungeon;

/**
 * This is an implementation of the Dungeon
 * controller where a player can play a single
 * dungeon game.
 */
public class DungeonConsoleController implements DungeonController {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }


  private void printMoveInformation(Dungeon model, Appendable out) throws IOException {
    out.append("\n");
    out.append(model.getLocationDescription());
    out.append("\n");
    out.append(model.getPlayerDescription());
    out.append("\n");
    out.append(startString());
    out.append("\n");
    if (model.detectStrongSmell()) {
      out.append("Strong monster smell detected. Beware XX\n");
    }
    if (model.detectWeakSmell()) {
      out.append("Weak monster smell detected. Beware X\n");
    }
  }

  private String startString() {
    StringBuilder builder = new StringBuilder();
    builder.append("The player has the following actions: \n");
    builder.append("Move, Pickup, or Shoot (M-P-S)\n");
    return builder.toString();
  }

  private String welcomeString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Welcome to the Dangerous Cave \n");
    builder.append("Get Ready for the adventure \n");
    return builder.toString();
  }

  @Override
  public void playDungeonGame(Dungeon model) {
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    }
    //IntroView view=new IntroView();
    //view.display();
    //GameView view=new GameView();
    //view.initialize(model.getLocationGrid(),model.getVisited(),model.getCurrentLocation());
    //view.display();
    try {
      out.append(welcomeString());
      printMoveInformation(model, out);
      while (scan.hasNext()) {
        DungeonCommand command;
        String in = scan.next();
        switch (in) {
          case "M":
            out.append("\nEnter W,A,S,D for North,West,South,East .\n");
            command = new Move(scan.next());
            break;
          case "P":
            out.append("\nEnter Arrow or Treasure.\n");
            command = new Pickup(scan.next());
            break;
          case "S":
            out.append("\nEnter W,A,S,D for North,West,South,East.\n");
            String direction = scan.next();
            out.append("\nEnter positive distance.\n");
            String distance = scan.next();
            command = new Shoot(direction, distance);
            break;
          default:
            out.append(String.format("Unknown command %s", in));
            command = null;
            break;
        }
        if (command != null) {
          out.append(command.apply(model));
          //view.refresh(model.getLocationGrid(),model.getVisited(),model.getCurrentLocation());
          out.append("\n");
          command = null;
          if (model.isGameOver()) {
            out.append("Game is Over.");
            if (model.isGoalState()
                && model.getCurrentLocation()
                        .getMonsters().size() == 0) {
              out.append("You have reached the end. and won the game.");
            } else {
              out.append("You have been slayed.");
            }
            return;
          }
          printMoveInformation(model, out);
        }
      }
    } catch (IOException exception) {
      throw new IllegalStateException("Append failed", exception);
    } catch (InputMismatchException ime) {
      throw new IllegalArgumentException("Bad Input");
    }
  }
}
