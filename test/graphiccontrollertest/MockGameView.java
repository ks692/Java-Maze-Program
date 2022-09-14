package graphiccontrollertest;

import controller.Features;
import view.GView;

/**
 * This is a mock game view class used
 * for testing our controller.
 */
public class MockGameView implements GView {

  private StringBuilder log;

  /**
   * Constructs a {@link MockIntroView} object in terms of log.
   *
   * @param log a log to record inputs by model.
   * @throws IllegalArgumentException if log is null.
   */
  public MockGameView(StringBuilder log) {
    if (log == null) {
      throw new IllegalArgumentException("model is null");
    }
    this.log = log;
  }

  @Override
  public void display() {
    log.append("game display called.\n");
  }

  @Override
  public void refresh() {
    log.append("game refresh called.\n");
  }

  @Override
  public void close() {
    log.append("game close called.\n");
  }

  @Override
  public void initialize() {
    log.append("game initialize called.\n");
  }

  @Override
  public void setFeatures(Features features) {
    log.append("game features called.\n");
  }

  @Override
  public void resetFocus() {
    log.append("reset game focus called.\n");
  }

  @Override
  public void gameOver() {
    log.append("game over called.\n");
  }

  @Override
  public void gameOverPit() {
    log.append("game over pit called.\n");
  }

  @Override
  public void gameOverKilled() {
    log.append("game over killed called.\n");
  }
}