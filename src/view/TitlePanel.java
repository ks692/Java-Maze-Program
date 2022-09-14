package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import dungeon.DungeonViewModel;
import location.Location;
import player.Player;
import player.Treasure;
import weapon.Arrow;

/**
 * This class creates and draws a
 * player description and location
 * description.
 */
class TitlePanel extends JPanel {
  private DungeonViewModel model;
  private boolean showPlayerInformation;
  private BufferedImage emerald;
  private BufferedImage ruby;
  private BufferedImage diamond;
  private BufferedImage arrow;
  private JPanel playerPanel;
  private JLabel playerSapphire;
  private JLabel playerDiamond;
  private JLabel playerRuby;
  private JLabel playerArrow;
  private JLabel locationSapphire;
  private JLabel locationDiamond;
  private JLabel locationRuby;
  private JLabel locationArrow;
  private JLabel coordinates;

  /**
   * Constructor for the Title panel.
   * it is represented by a read only
   * Dungeon model.
   *
   * @param model the read only
   *              dungeon model.
   */
  public TitlePanel(DungeonViewModel model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("view model is null");
    }
    this.model = model;
    showPlayerInformation = true;
    this.setBackground(Color.WHITE);
    this.setLayout(new GridLayout(1, 2));
    fetchImages();
    setUpElements();
  }

  private void fetchImages() {
    try {
      emerald = ImageIO.read(getClass().getClassLoader().getResourceAsStream("emerald.png"));
      diamond = ImageIO.read(getClass().getClassLoader().getResourceAsStream("diamond.png"));
      ruby = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ruby.png"));
      arrow = ImageIO.read(getClass().getClassLoader().getResourceAsStream("arrow-black.png"));
    } catch (IOException exception) {
      throw new IllegalArgumentException("Image file not found");
    }
  }

  /**
   * This method turns on and off the ability
   * to show player information.
   *
   * @param showPlayerInformation shows the player
   *                              information or turns
   *                              it off.
   */
  public void setPlayerInfo(boolean showPlayerInformation) {
    this.showPlayerInformation = showPlayerInformation;
  }

  /**
   * This method sets up the UI
   * elements required for the
   * title panel.
   */
  public void setUpElements() {
    playerPanel = new JPanel();
    playerPanel.setLayout(new FlowLayout());
    playerPanel.setBorder(new TitledBorder(new EtchedBorder(), "player Information"));
    JLabel label = new JLabel(model.getPlayer().getName());
    label.setForeground(Color.RED);
    playerPanel.add(label);
    label = new JLabel(" ");
    playerPanel.add(label);
    addUIElementsPlayer(playerPanel);

    JPanel locationPanel = new JPanel();
    locationPanel.setLayout(new FlowLayout());
    locationPanel.setBorder(new TitledBorder(new EtchedBorder(), "Location Description"));
    Location location = model.getCurrentLocation();
    coordinates = new JLabel(
            String.format("x:%d y:"
                          + "%d",
                    location.getXLocation(),
                    location.getYLocation()));
    coordinates.setForeground(Color.RED);
    locationPanel.add(coordinates);
    label = new JLabel(" ");
    locationPanel.add(label);
    addUIElementsLocation(locationPanel);
    this.add(locationPanel);
    this.add(playerPanel);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    updatePlayerElements();
    updateLocationElements();
  }

  private void updatePlayerElements() {
    int sapphireCount = 0;
    int diamondCount = 0;
    int rubyCount = 0;
    Player player = model.getPlayer();
    List<Treasure> treasureList = player.getTreasures();
    for (int i = 0; i < treasureList.size(); i++) {
      if (treasureList.get(i) == Treasure.SAPPHIRE) {
        sapphireCount++;
      }
      if (treasureList.get(i) == Treasure.DIAMOND) {
        diamondCount++;
      }
      if (treasureList.get(i) == Treasure.RUBY) {
        rubyCount++;
      }
    }
    playerSapphire.setText(String.format("X %d", sapphireCount));
    playerDiamond.setText(String.format("X %d", diamondCount));
    playerRuby.setText(String.format("X %d", rubyCount));
    playerArrow.setText(String.format("X %d",
            player.getArrows()
                    .get(Arrow.CROOKED)));
    if (!showPlayerInformation) {
      playerPanel.setVisible(false);
    } else {
      playerPanel.setVisible(true);
    }
  }

  private void updateLocationElements() {
    int sapphireCount = 0;
    int diamondCount = 0;
    int rubyCount = 0;
    Location location = model.getCurrentLocation();
    List<Treasure> treasureList = location.getTreasures();
    for (int i = 0; i < treasureList.size(); i++) {
      if (treasureList.get(i) == Treasure.SAPPHIRE) {
        sapphireCount++;
      }
      if (treasureList.get(i) == Treasure.DIAMOND) {
        diamondCount++;
      }
      if (treasureList.get(i) == Treasure.RUBY) {
        rubyCount++;
      }
    }
    coordinates.setText(
            String.format("x:%d y:"
                          + "%d",
                    location.getXLocation(),
                    location.getYLocation()));
    locationSapphire.setText(String.format("X %d", sapphireCount));
    locationDiamond.setText(String.format("X %d", diamondCount));
    locationRuby.setText(String.format("X %d", rubyCount));
    locationArrow.setText(String.format("X %d",
            location.getArrows().size()));
  }

  private void addUIElementsPlayer(JPanel panel) {
    JLabel label = new JLabel(" ");
    panel.add(label);
    playerSapphire = new JLabel(String.format("X %d", 0));
    playerSapphire.setIcon(new ImageIcon(emerald));
    panel.add(playerSapphire);
    playerDiamond = new JLabel(String.format("X %d", 0));
    playerDiamond.setIcon(new ImageIcon(diamond));
    panel.add(playerDiamond);
    playerRuby = new JLabel(String.format("X %d", 0));
    playerRuby.setIcon(new ImageIcon(ruby));
    panel.add(playerRuby);
    playerArrow = new JLabel(String.format("X %d", 0));
    playerArrow.setIcon(new ImageIcon(arrow));
    panel.add(playerArrow);
  }

  private void addUIElementsLocation(JPanel panel) {
    JLabel label = new JLabel(" ");
    panel.add(label);
    locationSapphire = new JLabel(String.format("X %d", 0));
    locationSapphire.setIcon(new ImageIcon(emerald));
    panel.add(locationSapphire);
    locationDiamond = new JLabel(String.format("X %d", 0));
    locationDiamond.setIcon(new ImageIcon(diamond));
    panel.add(locationDiamond);
    locationRuby = new JLabel(String.format("X %d", 0));
    locationRuby.setIcon(new ImageIcon(ruby));
    panel.add(locationRuby);
    locationArrow = new JLabel(String.format("X %d", 0));
    locationArrow.setIcon(new ImageIcon(arrow));
    panel.add(locationArrow);
  }


}
