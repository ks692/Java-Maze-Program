package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import controller.Features;
import location.Direction;

/**
 * This class handles keyboard events.
 * Move, Pickup and shoot are implemented
 * by pressing keys.
 */
class KeyboardHandler extends KeyAdapter {
  private Features features;
  private boolean shotFlag;
  private Direction direction;
  private GamePanel panel;

  /**
   * Constructor for the Keyboard Handler.
   * it is represented by Graphical controller.
   *
   * @param listener the Graphical Dungeon controller
   *                 which implements features.
   * @param panel    the parent component where
   *                 keyboard works.
   * @throws IllegalArgumentException if listener or panel is null.
   */
  public KeyboardHandler(Features listener, GamePanel panel) {
    if (listener == null) {
      throw new IllegalArgumentException("listener is null");
    }
    if (panel == null) {
      throw new IllegalArgumentException("panel is null");
    }
    this.panel = panel;
    this.features = listener;
    this.shotFlag = false;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    super.keyPressed(e);
    int key = e.getKeyCode();
    if (key == 's' || key == 'S') {
      shotFlag = true;
      return;
    }
    if (key == 'a' || key == 'A') {
      features.pickupArrows();
    } else if (key == 't' || key == 'T') {
      features.pickupTreasures();
    }
    if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
      if (shotFlag) {
        direction = Direction.WEST;
        return;
      }
      features.movePlayer(Direction.WEST);
    } else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
      if (shotFlag) {
        direction = Direction.EAST;
        return;
      }
      features.movePlayer(Direction.EAST);
    } else if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP) {
      if (shotFlag) {
        direction = Direction.NORTH;
        return;
      }
      features.movePlayer(Direction.NORTH);
    } else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN) {
      if (shotFlag) {
        direction = Direction.SOUTH;
        return;
      }
      features.movePlayer(Direction.SOUTH);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    super.keyReleased(e);
    int key = e.getKeyCode();
    if (key == 's' || key == 'S') {
      shotFlag = false;
      if (direction == null) {
        return;
      }
      if (!features.canShoot()) {
        JOptionPane.showMessageDialog(panel, "No arrows left");
        return;
      }
      String temp = JOptionPane.showInputDialog(panel,
              "Please enter the distance");
      boolean shot = false;
      try {
        int dist = Integer.parseInt(temp);
        shot = features.shootArrow(dist, direction);
      } catch (NumberFormatException exception) {
        JOptionPane.showMessageDialog(panel, "Enter valid distance");
        direction = null;
        return;
      } catch (IllegalArgumentException exception) {
        JOptionPane.showMessageDialog(panel, exception.getMessage());
      }
      if (shot) {
        JOptionPane.showMessageDialog(panel, "You hear a great howl.");
      } else {
        JOptionPane.showMessageDialog(panel, "You shot into darkness.");
      }
      direction = null;
    }
  }


}
