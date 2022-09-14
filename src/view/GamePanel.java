package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dungeon.DungeonViewModel;
import location.Direction;
import location.Location;

/**
 * This class creates and draws a Dungeon
 * board which takes a read only model as an
 * input. it also draws important information
 * about the state of the game.
 */
class GamePanel extends JPanel {

  private Location[][] locations;
  private boolean[][] visited;
  private Location currentLocation;
  private Location pitLocation;
  private DungeonViewModel model;
  private Map<String, BufferedImage> imageMap;


  /**
   * Constructor for the Game panel.
   * it is represented by a read only
   * Dungeon model.
   *
   * @param model the read only
   *              tic-tac-toe model.
   */
  public GamePanel(DungeonViewModel model) {
    super();
    this.model = model;
    this.setBackground(Color.WHITE);
    loadResources();
  }

  /**
   * initializes the dungeon grid.
   */
  public void initializeGrid() {
    Location[][] locations = model.getLocations();
    boolean[][] visited = model.getVisited();
    Location currentLocation = model.getCurrentLocation();
    this.setLayout(new GridLayout(locations.length, locations[0].length));
    this.locations = new Location[locations.length][locations[0].length];
    this.visited = new boolean[locations.length][locations[0].length];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        this.locations[i][j] = locations[i][j].getCopy();
        JLabel label = new JLabel(String.format("%d %d", i, j));
        this.add(label);
      }
    }
    this.currentLocation = currentLocation.getCopy();
    if (model.getPitLocation() != null) {
      this.pitLocation = model.getPitLocation().getCopy();
    }
  }

  /**
   * Refreshes the panel info.
   */
  public void refreshPanelInfo() {
    Location[][] locations = model.getLocations();
    boolean[][] visited = model.getVisited();
    Location currentLocation = model.getCurrentLocation();
    this.locations = new Location[locations.length][locations[0].length];
    this.visited = new boolean[locations.length][locations[0].length];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        this.locations[i][j] = locations[i][j].getCopy();
        this.visited[i][j] = visited[i][j];
      }
    }
    this.currentLocation = currentLocation.getCopy();
    if (model.getPitLocation() != null) {
      this.pitLocation = model.getPitLocation().getCopy();
    }
  }

  private void loadResources() {
    try {
      //fetching arrow
      imageMap = new HashMap<String, BufferedImage>();
      BufferedImage temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("arrow-black.png"));
      imageMap.put("arrow", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("diamond.png"));
      imageMap.put("diamond", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("emerald.png"));
      imageMap.put("emerald", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("ruby.png"));
      imageMap.put("ruby", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("otyugh.png"));
      imageMap.put("otyugh", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("stench01.png"));
      imageMap.put("weakSmell", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("stench02.png"));
      imageMap.put("strongSmell", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/E.png"));
      imageMap.put("E", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/EW.png"));
      imageMap.put("EW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/N.png"));
      imageMap.put("N", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/NE.png"));
      imageMap.put("NE", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/NEW.png"));
      imageMap.put("NEW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/NS.png"));
      imageMap.put("NS", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/NSE.png"));
      imageMap.put("NSE", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/NSEW.png"));
      imageMap.put("NSEW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/NSW.png"));
      imageMap.put("NSW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/NW.png"));
      imageMap.put("NW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/S.png"));
      imageMap.put("S", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/SE.png"));
      imageMap.put("SE", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/SEW.png"));
      imageMap.put("SEW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/SW.png"));
      imageMap.put("SW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("color-cells/W.png"));
      imageMap.put("W", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("man.png"));
      imageMap.put("man", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("blood.png"));
      imageMap.put("blood", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("pit.png"));
      imageMap.put("pit", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/E.png"));
      imageMap.put("bE", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/ES.png"));
      imageMap.put("bES", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/ESW.png"));
      imageMap.put("bESW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/EW.png"));
      imageMap.put("bEW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/N.png"));
      imageMap.put("bN", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/NE.png"));
      imageMap.put("bNE", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/NES.png"));
      imageMap.put("bNES", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/NESW.png"));
      imageMap.put("bNESW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/NEW.png"));
      imageMap.put("bNEW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/NS.png"));
      imageMap.put("bNS", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/S.png"));
      imageMap.put("bS", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/SW.png"));
      imageMap.put("bSW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/SWN.png"));
      imageMap.put("bNSW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/W.png"));
      imageMap.put("bW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/W.png"));
      imageMap.put("bW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("bw-cells/WN.png"));
      imageMap.put("bNW", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("ghole.png"));
      imageMap.put("ghole", temp);
      temp = ImageIO.read(
              getClass()
                      .getClassLoader()
                      .getResourceAsStream("robber.png"));
      imageMap.put("robber", temp);
    } catch (IOException exception) {
      throw new IllegalArgumentException(exception.getMessage());
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int count = 0;
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        Location location = locations[i][j];
        boolean visited = this.visited[i][j];
        boolean isCurrent = (currentLocation.getXLocation() == i
                             && currentLocation.getYLocation() == j);
        boolean isThief = false;
        Location thiefLocation = model.getThiefLocation();
        boolean isPit = false;
        if (pitLocation != null
            && (pitLocation.getXLocation() == i
             && pitLocation.getYLocation() == j)) {
          isPit = true;
        }
        if (thiefLocation != null
            && (thiefLocation.getXLocation() == i
             && thiefLocation.getYLocation() == j)) {
          isThief = true;
        }
        ImageIcon icon = new ImageIcon(getIcon(location, isCurrent, isPit, isThief));
        JLabel label = (JLabel) getComponent(count);
        label.setIcon(icon);
        if (isCurrent) {
          scrollRectToVisible(label.getBounds());
        }
        if (visited) {
          label.setVisible(true);
        } else {
          label.setVisible(false);
        }
        count++;
      }
    }
  }

  private BufferedImage getIcon(
          Location location,
          boolean isCurrentLocation,
          boolean isPitLocation,
          boolean isThief) {
    BufferedImage image = null;
    Location temp = location.getCopy();
    String filename = getFileName(temp);

    try {
      image = imageMap.get(filename);
      if (isCurrentLocation) {
        if (model.detectPitNearby()) {
          filename = getFileNamePit(temp);
          image = imageMap.get(filename);
        }
        image = overlay(image, "man", 0);
        if (model.getWeakSmell()) {
          image = overlay(image, "weakSmell", 0);
        }
        if (model.getStrongSmell()) {
          image = overlay(image, "strongSmell", 0);
        }
      }
      if (isThief) {
        image = overlay(image, "robber", 0);
      }
      if (temp.getMonsters().size() > 0) {
        image = overlay(image, "otyugh", 0);
        if (temp.getMonsters().get(0).getHealth() == 1) {
          image = overlay(image, "blood", 0);
        }
      }
      if (isPitLocation) {
        image = overlay(image, "ghole", 0);
      }

    } catch (IOException e) {
      throw new IllegalArgumentException("Image not found");
    }
    return image;
  }

  private String getFileName(Location location) {
    Location temp = location.getCopy();
    StringBuilder filename = new StringBuilder();
    if (temp.getValidMoves().contains(Direction.NORTH)) {
      filename.append("N");
    }
    if (temp.getValidMoves().contains(Direction.SOUTH)) {
      filename.append("S");
    }
    if (temp.getValidMoves().contains(Direction.EAST)) {
      filename.append("E");
    }
    if (temp.getValidMoves().contains(Direction.WEST)) {
      filename.append("W");
    }
    return filename.toString();
  }

  private String getFileNamePit(Location location) {
    Location temp = location.getCopy();
    StringBuilder filename = new StringBuilder();
    filename.append("b");
    if (temp.getValidMoves().contains(Direction.NORTH)) {
      filename.append("N");
    }
    if (temp.getValidMoves().contains(Direction.EAST)) {
      filename.append("E");
    }
    if (temp.getValidMoves().contains(Direction.SOUTH)) {
      filename.append("S");
    }
    if (temp.getValidMoves().contains(Direction.WEST)) {
      filename.append("W");
    }
    return filename.toString();
  }

  private BufferedImage overlay(BufferedImage starting,
                                String filename,
                                int offset) throws IOException {
    BufferedImage overlay = imageMap.get(filename);
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }
}
