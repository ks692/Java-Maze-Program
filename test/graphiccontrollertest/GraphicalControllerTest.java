package graphiccontrollertest;


import org.junit.Test;

import controller.GraphicalController;
import controller.GraphicalControllerImpl;
import controllertest.MockModel;
import location.Direction;
import view.GView;
import view.IView;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing the Graphical Controller.
 */
public class GraphicalControllerTest {


  @Test
  public void testGraphicalController() {
    StringBuilder builder = new StringBuilder();
    MockModel model = new MockModel(builder);
    StringBuilder builder2 = new StringBuilder();
    IView iView = new MockIntroView(builder2);
    StringBuilder builder3 = new StringBuilder();
    GView gView = new MockGameView(builder3);
    GraphicalController controller = new GraphicalControllerImpl(model, iView, gView);
    controller.playGame();
    assertEquals("", builder.toString());
    assertEquals("set intro features called\n"
                 + "display intro called\n", builder2.toString());
    assertEquals("game features called.\n", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
    GraphicalControllerImpl gImpl = new GraphicalControllerImpl(model, iView, gView);
    gImpl.canShoot();
    assertEquals("canShoot\n", builder.toString());
    assertEquals("set intro features called\n", builder2.toString());
    assertEquals("game features called.\n", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
    gImpl.movePlayer(0, 4);
    assertEquals("getLocationGridCalled\n"
                 + "getLocationGridCalled\n"
                 + "isGameOver\n"
                 + "isGameOver\n"
                 + "Direction: WEST\n", builder.toString());
    assertEquals("", builder2.toString());
    assertEquals("", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
    gImpl.movePlayer(Direction.EAST);
    assertEquals("isGameOver\n"
                 + "isGameOver\n"
                 + "Direction: EAST\n", builder.toString());
    assertEquals("", builder2.toString());
    assertEquals("", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
    gImpl.pickupArrows();
    assertEquals("pickupArrows\n", builder.toString());
    assertEquals("", builder2.toString());
    assertEquals("game refresh called.\n", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
    gImpl.pickupTreasures();
    assertEquals("pickupTreasures\n", builder.toString());
    assertEquals("", builder2.toString());
    assertEquals("game refresh called.\n", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
    gImpl.shootArrow(2, Direction.WEST);
    assertEquals("Distance: 2\n"
                 + "Direction: WEST\n", builder.toString());
    assertEquals("", builder2.toString());
    assertEquals("game refresh called.\n", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
    gImpl.restartGame();
    assertEquals("", builder.toString());
    assertEquals("display intro called\n", builder2.toString());
    assertEquals("game close called.\n", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
    gImpl.restartSameGame();
    assertEquals("resetGameState Called", builder.toString());
    assertEquals("", builder2.toString());
    assertEquals("game refresh called.\n", builder3.toString());
    builder.setLength(0);
    builder2.setLength(0);
    builder3.setLength(0);
  }

}
