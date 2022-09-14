# About/Overview

The project allows you to run a maze simulation for a player. This Dungeon contains both the options
to run a Dungeon in Graphical view as well as a text version. The Dungeon is a maze which contains a
pool of treasures, arrows and Otyugh Monsters that can kill the player moving in caves. The players
aim is to start from a cave, collect as many treasures as possible and reach the end of the maze. A
dungeon has caves which have 1,3,4 entrances while a tunnel has only 2 entrances. Only a cave can
hold treasures in a dungeon. This dungeon supports three types of treasures which are
Diamonds,Rubies and Sapphires. The arrows are crooked i.e. they can change the direction and pass
through tunnels. The player will be killed if he enters a monster Dungeon. The Graphical version has
special features that include the thief and pit.

# List of features

- Graphical Controller Features:
    - Show the dungeon which has only visited nodes.
    - restart same game.
    - restart as new game.
    - quit the game.
    - configure the game with different input options.
    - toggle player information.
    - Make the player move both by mouse click as well as keyboard arrow keys.
    - Make the player in the dungeon pick up treasures and arrows with keyboard keys.
    - The view auto scrolls if it is a large dungeon.
    - Make the player shoot arrow in a specified distance and direction with keyboard shortcuts.
    - Thief robs the player whenever he encounters the player.
    - Player dies if the falls into the pit.


- Controller Features:
    - Make the player in dungeon move specifying a valid direction from a place.
    - Make the player in the dungeon pick up arrows in a location.
    - Make the player in the dungeon pick up treasures in a location.
    - Make the player shoot arrow in a specified distance and direction.

- Model Features:
    - Player can move in a specified.
    - Player already has three arrows with him at the start of the game.
    - a player with a valid name can enter a dungeon at the start location.
    - assign treasures to a percentage of caves. The user can specify the minimum percentage of
      caves that should be filled with treasures.
    - get the player description which includes player name,current location and treasure
      information.
    - get the player current location description which includes the coordinates, treasures in the
      location and valid moves in the location.
    - get the start location of the maze.
    - get the end location of the maze.
    - move a player in a valid direction.
    - shoot arrows in a specified distance and direction from the player location.
    - Specify if the player has enough arrows to shoot.
    - pick up the arrows in the players current location.
    - pick up the treasures in the players current location.
    - check if the current location of the player is the end location.
    - model can assign a pit at random location in the dungeon.
    - model can assign a thief who moves randomly and steals the treasures from the player.
    - get the location grid which includes complete state information of the maze.

# How To Run

- GRAPHICAL VERSION. Locate the jar file and use the command:
  java -jar [locationtojar]\Dungeon.jar to run the graphical version.
- Do not include any arguments. This only works if arguments length is 0.

Configuration Details:

- rows is a positive integer above 4.
- cols is a positive integer above 4.
- interconnectivity is 0 or greater but less than total possible edges.
- wrapping can be "true" or "false".
- percentage can be an integer between 0 and 100.


- TEXT VERSION .

Locate the jar file and use the command java -jar [locationtojar]
\Dungeon.jar [rows] [cols] [interconnectivity] [wrapping] [percentage] [difficulty] to run the
program.

- rows is a positive integer above 4.
- cols is a positive integer above 4.
- interconnectivity is 0 or greater but less than total possible edges.
- wrapping can be "true" or "false".
- percentage can be an integer between 0 and 100.
- difficulty or number of monsters that must be assigned to the caves. Minimum value is 1.

A valid example of the run would be java -jar dungeon.jar "5" "6" "0" "true" "65" "10"

# How to Use the Program

The user interacts with the graphical controller in the following method.

- User needs to use arrow keyboard clicks or a mouse click on the appropriate possible position to
  move the player.
- player has to press 'a' on the keyboard to pickup arrows
- player has to press 't' on the keyboard to pickup treasures.
- player needs to hold s and use keyboard keys to specify shooting direction. distance can be
  specified in the dialog box.
- player can see various types of smells,monsters in the dungeon.
- player can detect if there is a pit nearby.

The user interacts with the controller, and it takes inputs from the user.

- W or w represents direction North.
- A or a represents direction West.
- S or s represents direction South.
- D or d represents direction East.
- Press M followed by a direction to move in the direction. Valid Examples to move would be M W , M
  e, M s
- Press P followed by arrow or ARROW to make the player collect arrows in the current location.
- Press P followed by treasure or TREASURE to make player collect arrows in the current location.
- Press S followed by direction and a distance. some valid examples are S S 1, S A 2, S D 3, S W 4.
- The game exits in these two conditions:

    - Slayed by a monster.
    - The player reaches end location.

# Description of Examples

Run 1 -- img1

This screenshot demonstrates the intro view.

- Detailed Run Description.

1. This view shows all the configuration information.
2. player can use the Jmenu to edit the configuration.
3. All the basic validations are done in the view.
4. Play Game button starts the game.
5. If the validations to the model are wrong. it is shown using a Jlabel.

Run 2 -- img2

This screenshot demonstrates the Game view.

1. This run shows the game view.
2. The options in the Jmenu include, restarting same game, restarting game as new game,quitting the
   game,toggle player information.
3. The game view shows the player description which contains player name, treasures and arrows in
   the inventory.
4. The game view shows the location description which contains location coordinates, arrows and
   treasures in the location.
5. The game view also shows only the visited nodes.
6. Thief is also seen in this view. Thief moves around trying to rob the player.

# Design/Model Changes

There are the changes from the old model.

1. Many private helper methods were added to the model.

# Assumptions

1. A dungeon must have more than 4 rows and 4 cols i.e. only value 5 and above are valid.This is
   done to ensure that the start and end locations have a distance of five between them.
2. When a player picks up a treasure, the treasure is emptied in that location. Returning to the
   same location will have empty treasures.
3. There are only 4 valid directions in which the player can move. They are North, East, South,
   West.
4. There is only a single otyugh per cave.
5. If there is an injured otyugh in the same place as the player then there is a strong smell.
6. A player can detect two levels of smell both strong pungent smell and also weak pungent smell if
   both exists.

# Limitations

- The graphical controller has the option of thief and pit while the console controller does not.
- The dungeon starts only if the rows and cols are greater than 4.
- The pit is only assigned if it was able to find a location greater than the distance between start
  and end location.

# Citations

- https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
- https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
- https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
- https://www.javatpoint.com/GridLayout





