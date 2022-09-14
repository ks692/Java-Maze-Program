package graphiccontrollertest;


import controller.Features;
import view.IView;

/**
 * This is a mock intro view class used
 * for testing our controller.
 */
public class MockIntroView implements IView {

  private StringBuilder log;

  /**
   * Constructs a {@link MockIntroView} object in terms of log.
   *
   * @param log a log to record inputs by model.
   * @throws IllegalArgumentException if log is null.
   */
  public MockIntroView(StringBuilder log) {
    if (log == null) {
      throw new IllegalArgumentException("model is null");
    }
    this.log = log;
  }


  @Override
  public void display() {
    log.append("display intro called\n");
  }

  @Override
  public int close() {
    log.append("close intro called\n");
    return 0;
  }

  @Override
  public void setFeatures(Features features) {
    log.append("set intro features called\n");
  }

  @Override
  public void setLabel(String text) {
    log.append("set label called\n");
  }
}
