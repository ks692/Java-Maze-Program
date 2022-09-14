package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.Features;

/**
 * The IntroView class represents the view which sets the
 * basic attributes of the Dungeon game. The users have
 * an option to edit the settings of the view.
 */
public class IntroView extends JFrame implements IView, ActionListener {
  //arguments should contain rows, cols, interconnectivity, wrapping, percentage,monsters.
  private String rows;
  private String cols;
  private String interconnectivity;
  private String wrapping;
  private String treasurePercentage;
  private String numMonsters;
  private JButton submit;
  private JMenuBar jMenuBar;
  private JLabel label;

  /**
   * Default constructor for the game
   * view.
   */
  public IntroView() {
    super();
    setDefaultVariables();
    this.setTitle("Configure your Game");
    this.setSize(500, 500);
    this.setResizable(false);
    this.setLayout(new GridBagLayout());
    this.setBackground(Color.GRAY);
    setupUIElements();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }


  private void setupUIElements() {
    jMenuBar = new JMenuBar();
    JMenu menu = new JMenu("Configure");
    JMenuItem item;
    item = new JMenuItem(String.format("rows: %s", this.rows));
    item.addActionListener(this);
    menu.add(item);
    item = new JMenuItem(String.format("cols: %s", this.cols));
    item.addActionListener(this);
    menu.add(item);
    item = new JMenuItem(String.format("interconnectivity: %s",
            this.interconnectivity));
    item.addActionListener(this);
    menu.add(item);
    item = new JMenuItem(String.format("wrapping: %s", this.wrapping));
    item.addActionListener(this);
    menu.add(item);
    item = new JMenuItem(String.format("treasure: %s", this.treasurePercentage));
    item.addActionListener(this);
    menu.add(item);
    item = new JMenuItem(String.format("monsters: %s", this.numMonsters));
    item.addActionListener(this);
    menu.add(item);
    jMenuBar.add(menu);
    this.setJMenuBar(jMenuBar);
    submit = new JButton("Play Game");
    this.setFont(new Font("Serif", Font.BOLD, this.getHeight() / 15));
    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.anchor = GridBagConstraints.NORTH;
    add(new JLabel("<html><h1><strong>"
                   + "<i>Welcome to Dangerous Game"
                   + "</i></strong></h1><hr></html>"), c);
    c.weighty = 1;
    this.add(submit, c);
    label = new JLabel();
    this.add(label, c);
  }

  private void setDefaultVariables() {
    this.rows = "5";
    this.cols = "5";
    this.interconnectivity = "0";
    this.wrapping = "false";
    this.treasurePercentage = "55";
    this.numMonsters = "7";
  }


  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public int close() {
    this.dispose();
    return 0;
  }

  @Override
  public void setFeatures(Features features) {
    //button listener.
    submit.addActionListener(l -> features.submitConfiguration(getInteger(rows),
            getInteger(cols),
            getInteger(interconnectivity),
            getWrapping(wrapping),
            getInteger(treasurePercentage),
            getInteger(numMonsters)));
  }

  private Integer getInteger(String input) {
    if (input == null) {
      throw new IllegalArgumentException("input is null");
    }
    return Integer.parseInt(input);
  }

  private boolean getWrapping(String input) {
    if (input == null) {
      throw new IllegalArgumentException("input is null");
    }
    return input.equalsIgnoreCase("true");
  }


  @Override
  public void setLabel(String text) {
    label.setForeground(Color.RED);
    this.label.setText(text);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //row validation
    if (e.getActionCommand().contains("rows")) {

      String temp = JOptionPane.showInputDialog(this,
              "Please enter the number of rows.");
      boolean result = validateInteger(temp);
      if (!result) {
        JOptionPane.showMessageDialog(this, "Wrong number of rows");
      } else {
        this.rows = temp;
        setText(0, String.format("rows: %s", this.rows));
      }
    }

    //cols validation
    if (e.getActionCommand().contains("cols")) {
      String temp = JOptionPane.showInputDialog(this,
              "Please enter the number of cols.");
      boolean result = validateInteger(temp);
      if (!result) {
        JOptionPane.showMessageDialog(this, "Wrong number of cols");
      } else {
        this.cols = temp;
        setText(1, String.format("cols: %s", this.cols));
      }
    }

    //interconnectivity validation
    if (e.getActionCommand().contains("interconnectivity")) {
      String temp = JOptionPane.showInputDialog(this,
              "Please enter interconnectivity.");
      boolean result = validateInteger(temp);
      if (!result) {
        JOptionPane.showMessageDialog(this, "Wrong interconnectivity");
      } else {
        this.interconnectivity = temp;
        setText(2, String.format("interconnectivity: %s", this.interconnectivity));
      }
    }

    //wrapping validation
    if (e.getActionCommand().contains("wrapping")) {
      String temp = JOptionPane.showInputDialog(this,
              "Please enter wrapping.");
      boolean result = validateWrapping(temp);
      if (!result) {
        JOptionPane.showMessageDialog(this, "Wrong wrapping value");
      } else {
        this.wrapping = temp;
        setText(3, String.format("wrapping: %s", this.wrapping));
      }
    }

    //treasurePercentage validation
    if (e.getActionCommand().contains("treasure")) {
      String temp = JOptionPane.showInputDialog(this,
              "Please enter treasure Percentage.");
      boolean result = validateInteger(temp);
      if (!result) {
        JOptionPane.showMessageDialog(this, "Wrong treasure percentage value");
      } else {
        this.treasurePercentage = temp;
        setText(4, String.format("treasure: %s", this.treasurePercentage));
      }
    }

    //numMonsters validation
    if (e.getActionCommand().contains("monsters")) {
      String temp = JOptionPane.showInputDialog(this,
              "Please enter number of monsters.");
      boolean result = validateInteger(temp);
      if (!result) {
        JOptionPane.showMessageDialog(this, "Wrong number of monsters");
      } else {
        this.numMonsters = temp;
        setText(5, String.format("monsters: %s", this.numMonsters));
      }
    }

  }

  private void setText(int index, String text) {
    jMenuBar.getMenu(0).getItem(index).setText(text);
  }

  //validate wrapping
  private boolean validateWrapping(String text) {
    return (text.equalsIgnoreCase("true")
        || text.equalsIgnoreCase("false"));
  }

  //validates if the string is integer and positive.
  private boolean validateInteger(String input) {
    try {
      final int temp = Integer.parseInt(input);
      if (temp < 0) {
        return false;
      }
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

}
