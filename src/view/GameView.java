package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import controller.Features;
import dungeon.DungeonViewModel;

/**
 * This class creates a swing implementation
 * of the Dungeon board. The board is drawn
 * using a PANEL depending on the game state.
 * it also displays information regarding player
 * turn and move winning.
 */
public class GameView extends JFrame implements GView {
  private GamePanel gamePanel;
  private JMenuItem quit;
  private JMenuItem restartNewGame;
  private JMenuItem restartSameGame;
  private JMenuItem showPlayerInformation;
  private boolean shouldDisplayPlayerInfo;
  private TitlePanel titlePanel;

  /**
   * Constructs a {@link GameView} object in terms of
   * a read only Dungeon model.
   *
   * @param model the read only
   *              Dungeon model.
   */
  public GameView(DungeonViewModel model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("view model is null");
    }
    this.setTitle("Dungeon");
    this.setSize(700, 500);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    gamePanel = new GamePanel(model);
    gamePanel.refreshPanelInfo();
    titlePanel = new TitlePanel(model);
    JPanel jPanel = new JPanel();
    jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
    jPanel.add(titlePanel);
    //.add(gamePanel);
    shouldDisplayPlayerInfo = true;
    JScrollPane scrollPane = new JScrollPane(this.gamePanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jPanel.add(scrollPane);
    this.add(jPanel);
    setupUIElements();
  }


  private void setupUIElements() {
    JMenuBar jMenuBar = new JMenuBar();
    JMenu menu = new JMenu("Options");
    restartNewGame = new JMenuItem(String.format("restart as new game"));
    menu.add(restartNewGame);
    restartSameGame = new JMenuItem(String.format("restart same game"));
    menu.add(restartSameGame);
    quit = new JMenuItem(String.format("quit"));
    menu.add(quit);
    showPlayerInformation = new JMenuItem("Player Information");
    menu.add(showPlayerInformation);
    jMenuBar.add(menu);
    this.setJMenuBar(jMenuBar);
    this.setFont(new Font("Serif", Font.BOLD, this.getHeight() / 15));
  }


  @Override
  public void display() {
    gamePanel.repaint();
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    titlePanel.repaint();
    gamePanel.refreshPanelInfo();
    repaint();
    revalidate();
  }

  @Override
  public void close() {
    this.dispose();
  }

  @Override
  public void initialize() {
    gamePanel.initializeGrid();
    gamePanel.repaint();
  }


  @Override
  public void setFeatures(Features features) {
    this.resetFocus();
    quit.addActionListener(l -> features.quitGame());
    //restart
    restartNewGame.addActionListener(l -> features.restartGame());
    restartSameGame.addActionListener(l -> features.restartSameGame());
    gamePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel) e.getComponent().getComponentAt(e.getX(), e.getY());
        //System.out.println(label.getText());
        String[] locationData = label.getText().split(" ");
        int row = Integer.parseInt(locationData[0]);
        int col = Integer.parseInt(locationData[1]);
        //handle move
        features.movePlayer(row, col);
      }
    });
    showPlayerInformation.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        shouldDisplayPlayerInfo = !shouldDisplayPlayerInfo;
        titlePanel.setPlayerInfo(shouldDisplayPlayerInfo);
        titlePanel.repaint();
      }
    });
    gamePanel.addKeyListener(new KeyboardHandler(features, gamePanel));
  }


  @Override
  public void resetFocus() {
    gamePanel.setFocusable(true);
    gamePanel.requestFocus();
  }

  @Override
  public void gameOver() {
    JOptionPane.showMessageDialog(this, "Game is Over. Reached end location.");
  }

  @Override
  public void gameOverPit() {
    JOptionPane.showMessageDialog(this, "Game is Over. fell into the pit.");
  }

  @Override
  public void gameOverKilled() {
    JOptionPane.showMessageDialog(this, "Game is Over. Killed by monster.");
  }

}
