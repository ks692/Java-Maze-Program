package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import dungeon.Dungeon;
import dungeon.DungeonImpl;
import location.Direction;
import location.Location;
import monster.Monster;
import player.Player;
import player.PlayerImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the Dungeon class.
 */
public class DungeonTest {

  private Player player;

  @Before
  public void setup() {
    player = new PlayerImpl("tester");
  }

  @Test
  public void dungeonConstructionTest() {
    //creating a non wrapping dungeon with zero connectivity.
    int interconnectivity = 0;
    Dungeon dungeon = new DungeonImpl(5, 5, interconnectivity, false, player, 3);
    //check if a path exists between every node.
    Location[][] locations = dungeon.getLocationGrid();
    Location location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }

    //check if there is path between start and end node.
    Location start = dungeon.getStartLocation();
    Location end = dungeon.getEndLocation();
    assertTrue(distanceBetweenLocations(
            dungeon.getLocationGrid(), start, end)
               != Integer.MIN_VALUE);

    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(
                    locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());

    //checking the number of edges
    // will tell us if there is
    // exactly one path from one
    // location to another.
    assertEquals(locations.length
                 * locations[0].length - 1
                 + interconnectivity,
            countEdges(locations));

    //check by printing the dungeon for wrapping and non wrapping and paths.
    String res = "                         \n"
                 + "   0--  1    2    3--  4 \n"
                 + "   |         |    |    | \n"
                 + "   |         |    |    | \n"
                 + "   5--  6    7    8    9 \n"
                 + "        |    |         | \n"
                 + "        |    |         | \n"
                 + "  10   11-- 12-- 13-- 14 \n"
                 + "   |    |              | \n"
                 + "   |    |              | \n"
                 + "  15   16-- 17   18-- 19 \n"
                 + "   |                   | \n"
                 + "   |                   | \n"
                 + "  20-- 21-- 22-- 23-- 24 \n"
                 + "                         \n";
    assertEquals(res, printDungeon(locations));

    //creating a wrapping dungeon with zero connectivity.
    interconnectivity = 0;
    dungeon = new DungeonImpl(5, 5, interconnectivity, true, player, 3);
    //check if a path exists between every node.
    locations = dungeon.getLocationGrid();
    location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }
    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());

    //checking the number
    // of edges will tell us
    // if there is exactly one
    // path from one location to another.
    assertEquals(
            locations.length
            * locations[0].length - 1
            + interconnectivity,
            countEdges(locations));

    //check by printing the dungeon for wrapping and non wrapping and paths.
    res = "   |    |         |      \n"
          + "   0    1--  2    3--  4 \n"
          + "   |    |    |           \n"
          + "   |    |    |           \n"
          + "-  5    6    7--  8--  9-\n"
          + "                         \n"
          + "                         \n"
          + "  10   11-- 12   13   14 \n"
          + "   |    |         |    | \n"
          + "   |    |         |    | \n"
          + "- 15   16-- 17-- 18   19-\n"
          + "   |              |      \n"
          + "   |              |      \n"
          + "- 20   21-- 22   23-- 24-\n"
          + "   |    |         |      \n";
    assertEquals(res, printDungeon(locations));


    //creating a wrapping dungeon with zero connectivity.
    interconnectivity = 0;
    dungeon = new DungeonImpl(5, 5, interconnectivity, true, player);
    //check if a path exists between every node.
    locations = dungeon.getLocationGrid();
    location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }
    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());


    //creating a wrapping dungeon with zero connectivity.
    interconnectivity = 5;
    dungeon = new DungeonImpl(5, 5, interconnectivity, true, player);
    //check if a path exists between every node.
    locations = dungeon.getLocationGrid();
    location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }
    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());

    //checking the number
    // of edges will tell us
    // if there is exactly one
    // path from one location to another.
    assertEquals(
            locations.length
            * locations[0].length - 1
            + interconnectivity,
            countEdges(locations));

    //creating a wrapping dungeon with 5 connectivity.
    interconnectivity = 5;
    dungeon = new DungeonImpl(5, 5, interconnectivity, true, player, 3);
    //check if a path exists between every node.
    locations = dungeon.getLocationGrid();
    location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }
    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(
                    locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());

    //checking the number of
    // edges will tell us if
    // there is exactly one path
    // from one location to another.
    assertEquals(
            locations.length
            * locations[0].length - 1
            + interconnectivity,
            countEdges(locations));

    //check by printing the dungeon for wrapping and non wrapping and paths.
    res = "   |    |    |    |      \n"
          + "   0    1--  2    3--  4 \n"
          + "   |    |    |           \n"
          + "   |    |    |           \n"
          + "-  5--  6    7--  8--  9-\n"
          + "                         \n"
          + "                         \n"
          + "  10   11-- 12-- 13   14 \n"
          + "   |    |         |    | \n"
          + "   |    |         |    | \n"
          + "- 15   16-- 17-- 18   19-\n"
          + "   |    |         |    | \n"
          + "   |    |         |    | \n"
          + "- 20   21-- 22   23-- 24-\n"
          + "   |    |    |    |      \n";
    assertEquals(res, printDungeon(locations));

    //rows>cols creating a non wrapping dungeon with 3 connectivity.
    interconnectivity = 3;
    dungeon = new DungeonImpl(6, 5, interconnectivity, false, player, 3);
    //check if a path exists between every node.
    locations = dungeon.getLocationGrid();
    location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }
    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(
                    locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());

    //checking the number of
    // edges will tell us if
    // there is exactly one path
    // from one location to another.
    assertEquals(locations.length
                 * locations[0].length - 1
                 + interconnectivity,
            countEdges(locations));

    //check by printing the dungeon for wrapping and non wrapping and paths.
    res = "                         \n"
          + "   0    1--  2--  3    4 \n"
          + "   |    |    |    |    | \n"
          + "   |    |    |    |    | \n"
          + "   5--  6--  7    8--  9 \n"
          + "             |    |    | \n"
          + "             |    |    | \n"
          + "  10-- 11-- 12-- 13-- 14 \n"
          + "   |                   | \n"
          + "   |                   | \n"
          + "  15   16-- 17-- 18   19 \n"
          + "   |              |    | \n"
          + "   |              |    | \n"
          + "  20-- 21-- 22-- 23   24 \n"
          + "                  |    | \n"
          + "                  |    | \n"
          + "  25-- 26-- 27-- 28   29 \n"
          + "                         \n";
    assertEquals(res, printDungeon(locations));


    //rows>cols creating a wrapping dungeon with 6 connectivity.
    interconnectivity = 6;
    dungeon = new DungeonImpl(6, 5, interconnectivity, true, player, 3);
    //check if a path exists between every node.
    locations = dungeon.getLocationGrid();
    location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }
    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(
                    locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());

    //checking the number of
    // edges will tell us if
    // there is exactly one path
    // from one location to another.
    assertEquals(locations.length
                 * locations[0].length - 1
                 + interconnectivity,
            countEdges(locations));

    //check by printing the dungeon for wrapping and non wrapping and paths.
    res = "             |    |    | \n"
          + "-  0--  1--  2--  3    4-\n"
          + "   |    |    |         | \n"
          + "   |    |    |         | \n"
          + "-  5--  6    7--  8    9-\n"
          + "        |                \n"
          + "        |                \n"
          + "- 10-- 11-- 12-- 13-- 14-\n"
          + "        |    |    |    | \n"
          + "        |    |    |    | \n"
          + "- 15   16   17   18   19-\n"
          + "   |                   | \n"
          + "   |                   | \n"
          + "- 20   21-- 22-- 23   24-\n"
          + "   |    |                \n"
          + "   |    |                \n"
          + "- 25-- 26-- 27   28   29-\n"
          + "             |    |    | \n";
    assertEquals(res, printDungeon(locations));


    //rows<cols creating a non wrapping dungeon with 6 connectivity.
    interconnectivity = 6;
    dungeon = new DungeonImpl(5, 6, interconnectivity, false, player, 3);
    //check if a path exists between every node.
    locations = dungeon.getLocationGrid();
    location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }
    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(
                    locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());

    //checking the number of edges
    // will tell us if
    // there is exactly one path
    // from one location to another.
    assertEquals(locations.length
                 * locations[0].length - 1
                 + interconnectivity,
            countEdges(locations));

    //check by printing the dungeon for wrapping and non wrapping and paths.
    res = "                              \n"
          + "   0    1--  2--  3    4--  5 \n"
          + "   |    |    |    |    |    | \n"
          + "   |    |    |    |    |    | \n"
          + "   6--  7    8--  9-- 10-- 11 \n"
          + "        |    |    |    |      \n"
          + "        |    |    |    |      \n"
          + "  12-- 13-- 14-- 15-- 16-- 17 \n"
          + "        |         |    |      \n"
          + "        |         |    |      \n"
          + "  18   19-- 20   21   22   23 \n"
          + "   |    |    |    |         | \n"
          + "   |    |    |    |         | \n"
          + "  24-- 25   26-- 27-- 28-- 29 \n"
          + "                              \n";
    assertEquals(res, printDungeon(locations));


    //rows<cols creating a wrapping dungeon with 0 connectivity.
    interconnectivity = 0;
    dungeon = new DungeonImpl(5, 6, interconnectivity, true, player, 3);
    //check if a path exists between every node.
    locations = dungeon.getLocationGrid();
    location = locations[0][0];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        assertNotEquals(
                distanceBetweenLocations(
                        locations,
                        location,
                        locations[i][j]),
                Integer.MIN_VALUE);
      }
    }
    //check caves and tunnel formation
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getValidMoves().size() == 2) {
          assertEquals(locations[i][j].getType(), "Tunnel");
        } else {
          assertEquals(locations[i][j].getType(), "Cave");
        }
      }
    }
    //check cave start and end have a distance of five.
    //checking the player location and player.
    assertEquals(dungeon.getStartLocation().getType(), "Cave");
    assertEquals(dungeon.getEndLocation().getType(), "Cave");
    assertTrue(
            distanceBetweenLocations(
                    locations,
                    dungeon.getStartLocation(),
                    dungeon.getEndLocation()) >= 5);
    assertEquals(dungeon.getCurrentLocation(), dungeon.getStartLocation());

    //checking the number
    // of edges will tell us
    // if there is exactly one
    // path from one location to another.
    assertEquals(locations.length
                 * locations[0].length - 1
                 + interconnectivity,
            countEdges(locations));

    //check by printing the dungeon for wrapping and non wrapping and paths.
    res = "                       |    | \n"
          + "   0--  1--  2--  3    4--  5 \n"
          + "   |         |         |      \n"
          + "   |         |         |      \n"
          + "-  6    7--  8    9-- 10-- 11-\n"
          + "   |                        | \n"
          + "   |                        | \n"
          + "  12   13   14-- 15-- 16   17 \n"
          + "   |    |    |    |           \n"
          + "   |    |    |    |           \n"
          + "  18   19   20   21-- 22-- 23 \n"
          + "        |         |           \n"
          + "        |         |           \n"
          + "- 24-- 25-- 26-- 27   28   29-\n"
          + "                       |    | \n";
    assertEquals(res, printDungeon(locations));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonIllegalArguments() {
    //illegal rows
    Dungeon dungeon = new DungeonImpl(3, 5, 0, false, player);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonIllegalArguments2() {
    //illegal cols
    Dungeon dungeon = new DungeonImpl(5, 3, 0, false, player);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonIllegalArguments3() {
    //illegal interconnectivity
    Dungeon dungeon = new DungeonImpl(5, 5, 41, false, player);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testDungeonIllegalArguments4() {
    //illegal interconnectivity
    Dungeon dungeon = new DungeonImpl(5, 5, 51, true, player);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonIllegalArguments5() {
    //illegal player
    Dungeon dungeon = new DungeonImpl(5, 5, 3, true, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonIllegalArguments6() {
    //illegal player
    Dungeon dungeon = new DungeonImpl(5, 5, -1, true, player);
  }

  @Test
  public void testAssignTreasuresAndWeapons() {
    Dungeon dungeon = new DungeonImpl(5, 5, 3, true, player);
    int percentage = 40;
    dungeon.assignTreasuresAndWeapons(percentage);
    //check if treasures assigned to cave only
    Location[][] locations = dungeon.getLocationGrid();
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (locations[i][j].getTreasures().size() > 0) {
          //cave
          assertTrue(locations[i][j].getValidMoves().size() != 2);
        }
        if (locations[i][j].getValidMoves().size() == 2) {
          assertTrue(locations[i][j].getTreasures().size() == 0);
        }

      }
    }
    //test treasures assigned to caves
    int caves = getNumberOfCaves(dungeon.getLocationGrid());
    int cavesTreasures = getTreasureCaves(dungeon.getLocationGrid());
    double check = ((double) cavesTreasures / caves) * 100;
    assertTrue(check >= percentage);
    int allLocations = locations.length * locations[0].length;
    int arrowLocations = getArrowCaves(locations);
    double checkArrows = ((double) arrowLocations / allLocations) * 100;
    assertTrue(checkArrows >= percentage);
    assertEquals(check, checkArrows, 99);

    dungeon = new DungeonImpl(5, 5, 0, true, player);
    percentage = 30;
    dungeon.assignTreasuresAndWeapons(percentage);
    locations = dungeon.getLocationGrid();
    //test treasures assigned to caves
    caves = getNumberOfCaves(dungeon.getLocationGrid());
    cavesTreasures = getTreasureCaves(dungeon.getLocationGrid());
    check = ((double) cavesTreasures / caves) * 100;
    assertTrue(check >= percentage);

    allLocations = locations.length * locations[0].length;
    arrowLocations = getArrowCaves(locations);
    checkArrows = ((double) arrowLocations / allLocations) * 100;
    assertTrue(checkArrows >= percentage);
    assertEquals(check, checkArrows, 99);

    dungeon = new DungeonImpl(5, 6, 3, false, player);
    percentage = 60;
    dungeon.assignTreasuresAndWeapons(percentage);
    locations = dungeon.getLocationGrid();
    //test treasures assigned to caves
    caves = getNumberOfCaves(dungeon.getLocationGrid());
    cavesTreasures = getTreasureCaves(dungeon.getLocationGrid());
    check = ((double) cavesTreasures / caves) * 100;
    assertTrue(check >= percentage);
    allLocations = locations.length * locations[0].length;
    arrowLocations = getArrowCaves(locations);
    checkArrows = ((double) arrowLocations / allLocations) * 100;
    assertTrue(checkArrows >= percentage);
    assertEquals(check, checkArrows, 99);

    dungeon = new DungeonImpl(6, 5, 0, false, player);
    percentage = 25;
    dungeon.assignTreasuresAndWeapons(percentage);
    locations = dungeon.getLocationGrid();
    //test treasures assigned to caves
    caves = getNumberOfCaves(dungeon.getLocationGrid());
    cavesTreasures = getTreasureCaves(dungeon.getLocationGrid());
    check = ((double) cavesTreasures / caves) * 100;
    assertTrue(check >= percentage);
    allLocations = locations.length * locations[0].length;
    arrowLocations = getArrowCaves(locations);
    checkArrows = ((double) arrowLocations / allLocations) * 100;
    assertTrue(checkArrows >= percentage);
    assertEquals(check, checkArrows, 99);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAssignTreasuresAndWeapons() {
    Dungeon dungeon = new DungeonImpl(5, 5, 3, true, player, 2);
    int percentage = 40;
    dungeon.assignTreasuresAndWeapons(percentage);
    boolean res = dungeon.makeMove(Direction.NORTH);
    dungeon.assignTreasuresAndWeapons(33);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAssignTreasuresAndWeapons2() {
    Dungeon dungeon = new DungeonImpl(5, 5, 3, true, player);
    int percentage = -10;
    dungeon.assignTreasuresAndWeapons(percentage);
  }


  @Test
  public void testAllLocateMonsters() {
    //testing the end location Monster.
    Dungeon dungeon = new DungeonImpl(5, 5, 0, false, player);
    dungeon.assignMonsters(5);
    Location end = dungeon.getEndLocation();
    Location[][] locations = dungeon.getLocationGrid();
    assertTrue(locations[end.getXLocation()][end.getYLocation()].getMonsters().size() > 0);
    dungeon = new DungeonImpl(5, 5, 0, false, player, 5);
    dungeon.assignMonsters(10);
    dungeon.assignTreasuresAndWeapons(50);
    Location start = dungeon.getStartLocation();
    end = dungeon.getEndLocation();
    locations = dungeon.getLocationGrid();
    //verify at start location
    assertTrue(locations[start.getXLocation()][start.getYLocation()].getMonsters().size() == 0);
    //verify at end location.
    assertFalse(locations[end.getXLocation()][end.getYLocation()].getMonsters().size() <= 0);
    //check if otyughs can occupy only caves.
    int count = 0;
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getMonsters().size() > 0) {
          count = count + locations[i][j].getMonsters().size();
          assertTrue(locations[i][j].getValidMoves().size() != 2);
          assertFalse(locations[i][j].getValidMoves().size() == 2);
        }
      }
    }
    assertTrue(locations[3][1].getMonsters().size() > 0
               && locations[3][1].getTreasures().size() > 0);
    assertEquals(count, 10);
    //wrapping and interconnectivity.
    dungeon = new DungeonImpl(5, 5, 5, true, player, 5);
    dungeon.assignMonsters(12);
    start = dungeon.getStartLocation();
    end = dungeon.getEndLocation();
    locations = dungeon.getLocationGrid();
    //verify at start location
    assertTrue(locations[start.getXLocation()][start.getYLocation()].getMonsters().size() == 0);
    //verify at end location.
    assertFalse(locations[end.getXLocation()][end.getYLocation()].getMonsters().size() <= 0);
    //check if otyughs can occupy only caves.
    count = 0;
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getMonsters().size() > 0) {
          count++;
          assertTrue(locations[i][j].getValidMoves().size() != 2);
          assertFalse(locations[i][j].getValidMoves().size() == 2);
        }
      }
    }
    assertEquals(count, 12);
  }

  @Test
  public void testSmell() {
    Dungeon dungeon = new DungeonImpl(5, 5, 5, false, player, 5);
    dungeon.assignMonsters(10);
    dungeon.assignTreasuresAndWeapons(50);
    Location[][] locations = dungeon.getLocationGrid();
    //1 monsters each 1 dist away.
    assertTrue(dungeon.detectStrongSmell());
    //0 monster 2 distance away.
    assertFalse(dungeon.detectWeakSmell());
    dungeon.pickupArrows();
    dungeon.shootArrow(1, Direction.WEST);
    dungeon.shootArrow(1, Direction.WEST);
    dungeon.makeMove(Direction.WEST);
    dungeon.pickupArrows();
    dungeon.shootArrow(1, Direction.NORTH);
    dungeon.shootArrow(1, Direction.NORTH);
    //0 monsters each 1 dist away.
    //2 monsters 2 dist away
    assertTrue(dungeon.detectStrongSmell());
    assertFalse(dungeon.detectWeakSmell());
    dungeon.makeMove(Direction.NORTH);
    dungeon.pickupArrows();
    dungeon.shootArrow(1, Direction.EAST);
    dungeon.shootArrow(1, Direction.EAST);
    dungeon.makeMove(Direction.SOUTH);
    dungeon.makeMove(Direction.WEST);
    dungeon.pickupArrows();
    //monster two distance away
    assertFalse(dungeon.detectStrongSmell());
    assertTrue(dungeon.detectWeakSmell());
    dungeon.makeMove(Direction.NORTH);
    dungeon.makeMove(Direction.NORTH);
    //1 otyugh 1 distance away
    assertTrue(dungeon.detectStrongSmell());
    assertFalse(dungeon.detectWeakSmell());
    //dungeon.makeMove(Direction.SOUTH);
    dungeon.shootArrow(1, Direction.EAST);
    dungeon.shootArrow(1, Direction.EAST);
    assertFalse(dungeon.detectStrongSmell());
    assertFalse(dungeon.detectWeakSmell());
  }


  @Test
  public void testArrows() {
    Dungeon dungeon = new DungeonImpl(5, 5, 5, false, player, 5);
    dungeon.assignMonsters(10);
    dungeon.assignTreasuresAndWeapons(50);
    //arrows in both tunnels and caves.
    Location[][] locations = dungeon.getLocationGrid();
    //cave
    assertTrue(locations[0][1].getValidMoves().size() != 2);
    assertTrue(locations[0][1].getArrows().size() > 0);
    //tunnel
    assertTrue(locations[1][0].getValidMoves().size() == 2);
    assertTrue(locations[1][0].getArrows().size() > 0);
    //test if the player has 3 arrows initially
    assertEquals("Player : tester \n"
                 + "Player Coordinates: (2,2)\n"
                 + "Player Treasures: {}\n"
                 + "Player Arrows: {CROOKED=3}", dungeon.getPlayerDescription());

    //test picking up arrows.
    dungeon.makeMove(Direction.EAST);
    assertTrue(dungeon.getCurrentLocation().getTreasures().size() > 0);
    assertTrue(dungeon.getCurrentLocation().getArrows().size() == 2);
    assertEquals("Location Treasures: [DIAMOND] \n"
                 + "Location Arrows: [CROOKED, CROOKED]\n"
                 + "Location Valid Moves: [SOUTH, EAST, WEST] \n",
            dungeon.getLocationDescription());
    dungeon.pickupArrows();
    assertEquals("Player : tester \n"
                 + "Player Coordinates: (2,3)\n"
                 + "Player Treasures: {}\n"
                 + "Player Arrows: {CROOKED=5}",
            dungeon.getPlayerDescription());
    assertEquals("Location Treasures: [DIAMOND] \n"
                 + "Location Arrows: []\n"
                 + "Location Valid Moves: [SOUTH, EAST, WEST] \n",
            dungeon.getLocationDescription());
    dungeon.makeMove(Direction.SOUTH);
    dungeon.makeMove(Direction.SOUTH);
    assertTrue(dungeon.getCurrentLocation().getTreasures().size() == 0);
    assertTrue(dungeon.getCurrentLocation().getArrows().size() > 0);


    //test monster slaying.
    player = new PlayerImpl("tester");
    dungeon = new DungeonImpl(5, 5, 10, false, player, 5);
    dungeon.assignMonsters(10);
    dungeon.assignTreasuresAndWeapons(50);

    locations = dungeon.getLocationGrid();
    //shooting to a distance 1.
    //current location is 1,0 monster in 2,0.
    assertTrue(dungeon.getCurrentLocation().getXLocation() == 1
               && dungeon.getCurrentLocation().getYLocation() == 0);
    assertTrue(locations[2][0].getMonsters().size() > 0);

    //shooting one arrow
    dungeon.shootArrow(1, Direction.SOUTH);
    //check monster damage.
    locations = dungeon.getLocationGrid();
    assertTrue(locations[2][0]
                       .getMonsters()
                       .get(0)
                       .getHealth() == 1);

    //shooting one arrow
    assertEquals(dungeon.getPlayerDescription(), "Player : tester \n"
                                                 + "Player Coordinates: (1,0)\n"
                                                 + "Player Treasures: {}\n"
                                                 + "Player Arrows: {CROOKED=2}");
    dungeon.shootArrow(1, Direction.SOUTH);
    //check if arrow decreased.
    assertEquals(dungeon.getPlayerDescription(), "Player : tester \n"
                                                 + "Player Coordinates: (1,0)\n"
                                                 + "Player Treasures: {}\n"
                                                 + "Player Arrows: {CROOKED=1}");
    //check monster damage.
    locations = dungeon.getLocationGrid();
    assertTrue(locations[2][0]
                       .getMonsters().size() == 0);
    dungeon.pickupArrows();
    //shooting at a distance of two.
    //shooting to a distance 1.
    //current location is 1,0 monster in 3,0.
    assertTrue(dungeon.getCurrentLocation().getXLocation() == 1
               && dungeon.getCurrentLocation().getYLocation() == 0);
    assertTrue(locations[3][0].getMonsters().size() > 0);

    //shooting at a distance of two.
    dungeon.shootArrow(2, Direction.SOUTH);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[3][0]
                       .getMonsters()
                       .get(0)
                       .getHealth() == 1);
    dungeon.pickupArrows();
    dungeon.shootArrow(2, Direction.SOUTH);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[3][0]
                       .getMonsters().size() == 0);


    dungeon.makeMove(Direction.EAST);
    dungeon.pickupArrows();
    dungeon.makeMove(Direction.WEST);
    dungeon.makeMove(Direction.NORTH);
    //shooting multiple distance away through tunnels and caves.
    dungeon.shootArrow(6, Direction.SOUTH);
    locations = dungeon.getLocationGrid();

    assertTrue(locations[4][3]
                       .getMonsters()
                       .get(0)
                       .getHealth() == 1);
    dungeon.pickupArrows();
    dungeon.shootArrow(6, Direction.SOUTH);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[4][3]
                       .getMonsters().size() == 0);

    //arrow misses if given greater distance.
    dungeon.makeMove(Direction.EAST);
    dungeon.pickupArrows();
    dungeon.makeMove(Direction.WEST);
    dungeon.shootArrow(8, Direction.SOUTH);

    locations = dungeon.getLocationGrid();

    assertTrue(locations[4][4]
                       .getMonsters()
                       .get(0)
                       .getHealth() == 2);
    dungeon.pickupArrows();
    dungeon.shootArrow(8, Direction.SOUTH);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[4][4]
                       .getMonsters().size() == 1);

    //shooting through multiple caves
    dungeon.makeMove(Direction.SOUTH);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[1][4]
                       .getMonsters()
                       .get(0)
                       .getHealth() == 2);
    dungeon.shootArrow(4, Direction.EAST);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[1][4]
                       .getMonsters()
                       .get(0)
                       .getHealth() == 1);
    dungeon.shootArrow(4, Direction.EAST);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[1][4]
                       .getMonsters()
                       .size() == 0);

    //shoot from a tunnel.
    dungeon.makeMove(Direction.NORTH);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[4][4]
                       .getMonsters()
                       .get(0)
                       .getHealth() == 2);
    dungeon.shootArrow(7, Direction.SOUTH);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[4][4]
                       .getMonsters()
                       .get(0)
                       .getHealth() == 1);
    dungeon.shootArrow(7, Direction.SOUTH);
    locations = dungeon.getLocationGrid();
    assertTrue(locations[4][4]
                       .getMonsters()
                       .size() == 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testOutOfArrows() {
    Dungeon dungeon = new DungeonImpl(5, 5, 5, false, player, 5);
    dungeon.shootArrow(1, Direction.EAST);
    dungeon.shootArrow(1, Direction.EAST);
    dungeon.shootArrow(1, Direction.EAST);
    dungeon.shootArrow(1, Direction.EAST);
  }


  @Test
  public void testPlayerDying() {
    Dungeon dungeon = new DungeonImpl(5, 5, 5, false, player, 23);
    dungeon.assignTreasuresAndWeapons(50);
    dungeon.assignMonsters(10);

    Location[][] locations = dungeon.getLocationGrid();
    //monster in west location.
    assertTrue(locations[4][1].getMonsters().size() > 0);
    dungeon.makeMove(Direction.WEST);
    //player should be killed.
    //Game over
    assertTrue(dungeon.isGameOver());
    assertFalse(dungeon.isGoalState());

    //player does not survive injured monster.
    dungeon = new DungeonImpl(5, 5, 5, false, player, 23);
    dungeon.assignTreasuresAndWeapons(50);
    dungeon.assignMonsters(10);
    locations = dungeon.getLocationGrid();
    //monster in west location.
    assertTrue(locations[4][1].getMonsters().size() > 0);
    dungeon.shootArrow(1, Direction.WEST);
    dungeon.makeMove(Direction.WEST);
    //player should not be killed.
    //Game over
    assertFalse(dungeon.isGameOver());
    assertFalse(dungeon.isGoalState());
    dungeon.makeMove(Direction.WEST);
    dungeon.makeMove(Direction.EAST);
    assertTrue(dungeon.isGameOver());
    assertFalse(dungeon.isGoalState());


    //player survive injured monster.
    dungeon = new DungeonImpl(5, 5, 5, false, player, 11);
    dungeon.assignTreasuresAndWeapons(50);
    dungeon.assignMonsters(10);
    dungeon.makeMove(Direction.NORTH);
    dungeon.shootArrow(1, Direction.EAST);
    dungeon.makeMove(Direction.EAST);
    dungeon.makeMove(Direction.WEST);
    dungeon.makeMove(Direction.EAST);
    assertFalse(dungeon.isGameOver());
    assertFalse(dungeon.isGoalState());
  }


  @Test
  public void getPlayerDescriptionTest() {
    Dungeon dungeon = new DungeonImpl(6, 5, 0, false, player, 5);
    dungeon.assignTreasuresAndWeapons(50);
    //check start location of the player
    assertEquals(
            dungeon.getCurrentLocation()
                    .getXLocation(),
            dungeon.getStartLocation()
                    .getXLocation());
    assertEquals(
            dungeon.getCurrentLocation()
                    .getYLocation(),
            dungeon.getStartLocation()
                    .getYLocation());
    String res = "Player : tester \n"
                 + "Player Coordinates: (4,2)\n"
                 + "Player Treasures: {}\n"
                 + "Player Arrows: {CROOKED=3}";
    assertEquals(res, dungeon.getPlayerDescription());
    dungeon.pickupTreasures();
    res = "Player : tester \n"
          + "Player Coordinates: (4,2)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(res, dungeon.getPlayerDescription());
    dungeon.makeMove(Direction.NORTH);
    dungeon.pickupTreasures();
    dungeon.makeMove(Direction.WEST);
    dungeon.pickupTreasures();
    dungeon.makeMove(Direction.NORTH);
    dungeon.pickupTreasures();
    dungeon.makeMove(Direction.EAST);
    dungeon.pickupTreasures();
    res = "Player : tester \n"
          + "Player Coordinates: (2,2)\n"
          + "Player Treasures: {RUBY=2, SAPPHIRE=3}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(res, dungeon.getPlayerDescription());
  }

  @Test
  public void getLocationDescriptionTest() {
    Dungeon dungeon = new DungeonImpl(6, 5, 0, true, player, 5);
    dungeon.assignTreasuresAndWeapons(50);
    Location[][] locations = dungeon.getLocationGrid();
    String res = "Location Treasures: [] \n"
                 + "Location Arrows: [CROOKED, CROOKED]\n"
                 + "Location Valid Moves: [NORTH, SOUTH, EAST] \n";
    assertEquals(res, dungeon.getLocationDescription());
    res = "Player : tester \n"
          + "Player Coordinates: (1,1)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(res, dungeon.getPlayerDescription());
    dungeon.pickupTreasures();
    dungeon.makeMove(Direction.NORTH);
    //pickup treasures is tested here.
    res = "Location Treasures: [RUBY, SAPPHIRE] \n"
          + "Location Arrows: [CROOKED, CROOKED]\n"
          + "Location Valid Moves: [NORTH, SOUTH, EAST, WEST] \n";
    assertEquals(res, dungeon.getLocationDescription());
    dungeon.pickupTreasures();
    res = "Player : tester \n"
          + "Player Coordinates: (0,1)\n"
          + "Player Treasures: {RUBY=1, SAPPHIRE=1}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(res, dungeon.getPlayerDescription());
    res = "Location Treasures: [] \n"
          + "Location Arrows: [CROOKED, CROOKED]\n"
          + "Location Valid Moves: [NORTH, SOUTH, EAST, WEST] \n";
    assertEquals(res, dungeon.getLocationDescription());
    dungeon.makeMove(Direction.EAST);
    dungeon.pickupTreasures();
    //check location
    res = "Location Treasures: [] \n"
          + "Location Arrows: [CROOKED, CROOKED]\n"
          + "Location Valid Moves: [WEST] \n";
    assertEquals(res, dungeon.getLocationDescription());
    dungeon.pickupTreasures();
  }


  @Test
  public void testPlayerMove() {
    Dungeon dungeon = new DungeonImpl(6, 5, 0, true, player, 12);
    //moving player from start state to end state.
    String res = "Player : tester \n"
                 + "Player Coordinates: (1,4)\n"
                 + "Player Treasures: {}\n"
                 + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 1);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 4);
    assertEquals(dungeon.getPlayerDescription(), res);
    boolean result = dungeon.makeMove(Direction.NORTH);
    //move invalid
    assertFalse(result);
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 1);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 4);
    //move west success
    assertTrue(dungeon.makeMove(Direction.WEST));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 1);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 3);
    res = "Player : tester \n"
          + "Player Coordinates: (1,3)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);

    //move North success
    assertTrue(dungeon.makeMove(Direction.NORTH));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 0);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 3);
    res = "Player : tester \n"
          + "Player Coordinates: (0,3)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);

    //move East success
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 0);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 4);
    res = "Player : tester \n"
          + "Player Coordinates: (0,4)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);
    //move north success wrapping vertical.
    assertTrue(dungeon.makeMove(Direction.NORTH));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 5);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 4);
    res = "Player : tester \n"
          + "Player Coordinates: (5,4)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);
    //move north success
    assertTrue(dungeon.makeMove(Direction.NORTH));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 4);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 4);
    res = "Player : tester \n"
          + "Player Coordinates: (4,4)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);
    //move east success wrapping valid
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 4);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 0);
    res = "Player : tester \n"
          + "Player Coordinates: (4,0)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);
    //move South success
    assertTrue(dungeon.makeMove(Direction.SOUTH));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 5);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 0);
    res = "Player : tester \n"
          + "Player Coordinates: (5,0)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);

    //move East success
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 5);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 1);
    res = "Player : tester \n"
          + "Player Coordinates: (5,1)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);

    //move South success wrapping
    assertTrue(dungeon.makeMove(Direction.SOUTH));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 0);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 1);
    res = "Player : tester \n"
          + "Player Coordinates: (0,1)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);

    //move West success
    assertTrue(dungeon.makeMove(Direction.WEST));
    assertEquals(dungeon.getCurrentLocation().getXLocation(), 0);
    assertEquals(dungeon.getCurrentLocation().getYLocation(), 0);
    res = "Player : tester \n"
          + "Player Coordinates: (0,0)\n"
          + "Player Treasures: {}\n"
          + "Player Arrows: {CROOKED=3}";
    assertEquals(dungeon.getPlayerDescription(), res);
    //tested goal state and player reached end state.
    assertEquals(
            dungeon.getCurrentLocation()
                    .getXLocation(),
            dungeon.getEndLocation()
                    .getXLocation());
    assertEquals(
            dungeon.getCurrentLocation()
                    .getYLocation(),
            dungeon.getEndLocation()
                    .getYLocation());
    assertTrue(dungeon.isGoalState());

    //make move all locations traverse
    dungeon = new DungeonImpl(5, 5, 26, true, player, 12);
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.SOUTH));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.SOUTH));
    assertTrue(dungeon.makeMove(Direction.SOUTH));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.EAST));
    assertTrue(dungeon.makeMove(Direction.NORTH));
    assertTrue(dungeon.makeMove(Direction.WEST));
    assertTrue(dungeon.makeMove(Direction.WEST));
    assertTrue(dungeon.makeMove(Direction.WEST));
    assertEquals(
            dungeon.getCurrentLocation()
                    .getXLocation(),
            dungeon.getEndLocation()
                    .getXLocation());
    assertEquals(
            dungeon.getCurrentLocation()
                    .getYLocation(),
            dungeon.getEndLocation()
                    .getYLocation());
    assertTrue(dungeon.isGoalState());
    assertTrue(dungeon.isGameOver());
  }

  @Test
  public void testLocationGrid() {
    Dungeon dungeon = new DungeonImpl(6, 5, 0, true, player, 12);
    dungeon.assignMonsters(3);
    dungeon.assignTreasuresAndWeapons(48);
    assertEquals(dungeon.toString(), this.toString(dungeon.getLocationGrid()));
  }

  @Test
  public void testToString() {
    Dungeon dungeon = new DungeonImpl(6, 5, 0, true, player, 12);
    String res = "0 0 [EAST] Cave [] Monsters : {} "
                 + "[] 0 1 [NORTH, EAST, WEST] Cave"
                 + " [] Monsters : {} [] 0 2 [WEST]"
                 + " Cave [] Monsters : {} []"
                 + " 0 3 [EAST, SOUTH] Tunnel []"
                 + " Monsters : {} [] 0 4 [WEST, NORTH]"
                 + " Tunnel [] Monsters : {} [] \n"
                 + "1 0 [SOUTH] Cave [] Monsters : {} []"
                 + " 1 1 [EAST] Cave [] Monsters : {} []"
                 + " 1 2 [EAST, WEST] Tunnel [] Monsters "
                 + ": {} [] 1 3 [SOUTH, EAST, WEST, NORTH]"
                 + " Cave [] Monsters : {} [] 1 4 [WEST]"
                 + " Cave [] Monsters : {} [] \n"
                 + "2 0 [EAST, NORTH] Tunnel [] "
                 + "Monsters : {} [] 2 1 [EAST, WEST, SOUTH]"
                 + " Cave [] Monsters : {} [] "
                 + "2 2 [WEST, EAST] Tunnel []"
                 + " Monsters : {} [] 2 3 [WEST, NORTH]"
                 + " Tunnel [] Monsters : {} [] 2 4 [SOUTH]"
                 + " Cave [] Monsters : {} [] \n"
                 + "3 0 [WEST] Cave [] Monsters : {}"
                 + " [] 3 1 [SOUTH, NORTH] Tunnel []"
                 + " Monsters : {} [] 3 2 [SOUTH, EAST]"
                 + " Tunnel [] Monsters : {} [] "
                 + "3 3 [WEST, EAST] Tunnel [] "
                 + "Monsters : {} [] 3 4 [NORTH, WEST, EAST] "
                 + "Cave [] Monsters : {} [] \n"
                 + "4 0 [SOUTH, WEST] Tunnel [] Monsters : "
                 + "{} [] 4 1 [NORTH] Cave [] Monsters : {} "
                 + "[] 4 2 [NORTH, EAST] Tunnel [] Monsters : "
                 + "{} [] 4 3 [SOUTH, WEST] Tunnel [] Monsters "
                 + ": {} [] 4 4 [SOUTH, EAST] Tunnel [] "
                 + "Monsters : {} [] \n"
                 + "5 0 [NORTH, EAST] Tunnel [] Monsters : "
                 + "{} [] 5 1 [EAST, SOUTH, WEST] Cave [] "
                 + "Monsters : {} [] 5 2 [WEST] Cave [] "
                 + "Monsters : {} [] 5 3 [NORTH, EAST] "
                 + "Tunnel [] Monsters : {} [] "
                 + "5 4 [SOUTH, NORTH, WEST] "
                 + "Cave [] Monsters : {} [] \n";
    assertEquals(dungeon.toString(), res);
    dungeon.assignTreasuresAndWeapons(40);
    res = "0 0 [EAST] Cave [SAPPHIRE, RUBY] "
          + "Monsters : {} [CROOKED, CROOKED] 0 1 "
          + "[NORTH, EAST, WEST] Cave [] "
          + "Monsters : {} [] 0 2 [WEST] Cave [] "
          + "Monsters : {} [] 0 3 [EAST, SOUTH] "
          + "Tunnel [] Monsters : {} "
          + "[CROOKED, CROOKED, CROOKED] "
          + "0 4 [WEST, NORTH] Tunnel [] "
          + "Monsters : {} [] \n"
          + "1 0 [SOUTH] Cave [DIAMOND, DIAMOND]"
          + " Monsters : {} [] 1 1 [EAST] Cave [] "
          + "Monsters : {} [] 1 2 [EAST, WEST] "
          + "Tunnel [] Monsters : {} [] "
          + "1 3 [SOUTH, EAST, WEST, NORTH] "
          + "Cave [] Monsters : {} [] "
          + "1 4 [WEST] Cave [] Monsters "
          + ": {} [CROOKED, CROOKED, CROOKED] \n"
          + "2 0 [EAST, NORTH] Tunnel [] "
          + "Monsters : {} [] 2 1 [EAST, WEST, SOUTH]"
          + " Cave [RUBY, SAPPHIRE, SAPPHIRE] Monsters"
          + " : {} [] 2 2 [WEST, EAST] Tunnel [] Monsters"
          + " : {} [] 2 3 [WEST, NORTH] Tunnel [] Monsters"
          + " : {} [] 2 4 [SOUTH] Cave [DIAMOND, SAPPHIRE]"
          + " Monsters : {} [CROOKED, CROOKED, CROOKED] \n"
          + "3 0 [WEST] Cave [SAPPHIRE, SAPPHIRE, DIAMOND]"
          + " Monsters : {} [] 3 1 [SOUTH, NORTH]"
          + " Tunnel [] Monsters : {} []"
          + " 3 2 [SOUTH, EAST] Tunnel []"
          + " Monsters : {} [CROOKED, CROOKED]"
          + " 3 3 [WEST, EAST] Tunnel [] Monsters"
          + " : {} [CROOKED, CROOKED, CROOKED]"
          + " 3 4 [NORTH, WEST, EAST] Cave []"
          + " Monsters : {} [CROOKED, CROOKED] \n"
          + "4 0 [SOUTH, WEST] Tunnel [] Monsters"
          + " : {} [CROOKED, CROOKED] 4 1 [NORTH] "
          + "Cave [] Monsters : {} [CROOKED, CROOKED, CROOKED] "
          + "4 2 [NORTH, EAST] Tunnel [] "
          + "Monsters : {} [CROOKED] 4 3 "
          + "[SOUTH, WEST] Tunnel [] "
          + "Monsters : {} [CROOKED, CROOKED] "
          + "4 4 [SOUTH, EAST] Tunnel [] "
          + "Monsters : {} [] \n"
          + "5 0 [NORTH, EAST] Tunnel [] "
          + "Monsters : {} [] 5 1 [EAST, SOUTH, WEST] "
          + "Cave [RUBY, SAPPHIRE] "
          + "Monsters : {} [] 5 2 [WEST] Cave [] "
          + "Monsters : {} [CROOKED] 5 3 "
          + "[NORTH, EAST] Tunnel [] "
          + "Monsters : {} [CROOKED] "
          + "5 4 [SOUTH, NORTH, WEST] "
          + "Cave [DIAMOND, SAPPHIRE] "
          + "Monsters : {} [CROOKED, CROOKED, CROOKED] \n";
    assertEquals(dungeon.toString(), res);
    dungeon.assignMonsters(12);
    res = "0 0 [EAST] Cave [RUBY, SAPPHIRE]"
          + " Monsters : {id : 0, health : 2. } "
          + "[CROOKED, CROOKED] 0 1 [NORTH, EAST, WEST] "
          + "Cave [] Monsters : {id : 3, health : 2. } [] "
          + "0 2 [WEST] Cave [] Monsters : {id : 6, health : 2. }"
          + " [] 0 3 [EAST, SOUTH] Tunnel [] "
          + "Monsters : {} [CROOKED, CROOKED, CROOKED] "
          + "0 4 [WEST, NORTH] Tunnel [] Monsters : {} [] \n"
          + "1 0 [SOUTH] Cave [DIAMOND, DIAMOND] "
          + "Monsters : {id : 5, health : 2. } "
          + "[] 1 1 [EAST] Cave [] Monsters : {} "
          + "[] 1 2 [EAST, WEST] Tunnel [] "
          + "Monsters : {} [] 1 3 [SOUTH, EAST, WEST, NORTH]"
          + " Cave [] Monsters : {id : 4, health : 2. } "
          + "[] 1 4 [WEST] Cave [] "
          + "Monsters : {} [CROOKED, CROOKED, CROOKED] \n"
          + "2 0 [EAST, NORTH] Tunnel [] "
          + "Monsters : {} [] 2 1 [EAST, WEST, SOUTH] "
          + "Cave [RUBY, SAPPHIRE, SAPPHIRE] "
          + "Monsters : {id : 9, health : 2. } "
          + "[] 2 2 [WEST, EAST] Tunnel [] "
          + "Monsters : {} [] 2 3 [WEST, NORTH] "
          + "Tunnel [] Monsters : {} [] "
          + "2 4 [SOUTH] Cave [DIAMOND, SAPPHIRE] "
          + "Monsters : {} [CROOKED, CROOKED, CROOKED] \n"
          + "3 0 [WEST] Cave [SAPPHIRE, SAPPHIRE, DIAMOND]"
          + " Monsters : {id : 11, health : 2. } [] 3 1 "
          + "[SOUTH, NORTH] Tunnel [] Monsters : {} "
          + "[] 3 2 [SOUTH, EAST] Tunnel [] "
          + "Monsters : {} [CROOKED, CROOKED] "
          + "3 3 [WEST, EAST] Tunnel [] "
          + "Monsters : {} [CROOKED, CROOKED, CROOKED] "
          + "3 4 [NORTH, WEST, EAST] Cave [] "
          + "Monsters : {id : 2, health : 2. } "
          + "[CROOKED, CROOKED] \n"
          + "4 0 [SOUTH, WEST] Tunnel [] "
          + "Monsters : {} [CROOKED, CROOKED] "
          + "4 1 [NORTH] Cave [] Monsters : {id : 7, health : 2. }"
          + " [CROOKED, CROOKED, CROOKED] 4 2 [NORTH, EAST]"
          + " Tunnel [] Monsters : {} [CROOKED]"
          + " 4 3 [SOUTH, WEST] Tunnel []"
          + " Monsters : {} [CROOKED, CROOKED]"
          + " 4 4 [SOUTH, EAST] Tunnel []"
          + " Monsters : {} [] \n"
          + "5 0 [NORTH, EAST] Tunnel []"
          + " Monsters : {} [] 5 1 [EAST, SOUTH, WEST]"
          + " Cave [RUBY, SAPPHIRE] "
          + "Monsters : {id : 10, health : 2. } "
          + "[] 5 2 [WEST] Cave [] "
          + "Monsters : {id : 8, health : 2. } "
          + "[CROOKED] 5 3 [NORTH, EAST] "
          + "Tunnel [] Monsters : {} [CROOKED]"
          + " 5 4 [SOUTH, NORTH, WEST] "
          + "Cave [DIAMOND, SAPPHIRE]"
          + " Monsters : {id : 1, health : 2. }"
          + " [CROOKED, CROOKED, CROOKED] \n";
    assertEquals(dungeon.toString(), res);
  }


  private String toString(Location[][] locationsHeader) {
    Location[][] locations = new Location[locationsHeader.length][locationsHeader[0].length];
    for (int i = 0; i < locationsHeader.length; i++) {
      for (int j = 0; j < locationsHeader[0].length; j++) {
        locations[i][j] = locationsHeader[i][j].getCopy();
      }
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < locationsHeader.length; i++) {
      for (int j = 0; j < locationsHeader[0].length; j++) {
        builder.append(i);
        builder.append(" ");
        builder.append(j);
        builder.append(" ");
        builder.append(locations[i][j].getValidMoves().toString());
        builder.append(" ");
        builder.append(getType(locations[i][j].getCopy()));
        builder.append(" ");
        builder.append(locations[i][j].getTreasures().toString());
        builder.append(" ");
        List<Monster> monsters = locations[i][j].getMonsters();
        String result = monsters.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining("-", "{", "}"));
        builder.append("Monsters : ");
        builder.append(result);
        builder.append(" ");
        builder.append(locations[i][j].getArrows().toString());
        builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  private String getType(Location location) {
    Location locationCopy = location.getCopy();
    if (location.getValidMoves().size() == 2) {
      return "Tunnel";
    } else {
      return "Cave";
    }
  }


  private int getTreasureCaves(Location[][] locations) {
    int count = 0;
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getTreasures().size() > 0) {
          count++;
        }
      }
    }
    return count;
  }

  private int getArrowCaves(Location[][] locations) {
    int count = 0;
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getArrows().size() > 0) {
          count++;
        }
      }
    }
    return count;
  }

  private int getNumberOfCaves(Location[][] locations) {
    int count = 0;
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        if (locations[i][j].getType().equals("Cave")) {
          count++;
        }
      }
    }
    return count;
  }

  private String printDungeon(Location[][] locations) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        builder.append(" ");
        if (locations[i][j].getValidMoves().contains(Direction.NORTH)) {
          builder.append(String.format("%3s", "|"));
        } else {
          builder.append("   ");
        }
        builder.append(" ");
      }
      builder.append("\n");
      for (int j = 0; j < locations[0].length; j++) {
        List<Direction> moves = locations[i][j].getValidMoves();
        if (moves.contains(Direction.WEST)) {
          builder.append("-");
        } else {
          builder.append(" ");
        }
        int nodeNumber = i * locations[0].length + j;
        builder.append(String.format("%3d", nodeNumber));
        if (moves.contains(Direction.EAST)) {
          builder.append("-");
        } else {
          builder.append(" ");
        }
      }
      builder.append("\n");
      for (int j = 0; j < locations[0].length; j++) {
        builder.append(" ");
        if (locations[i][j].getValidMoves().contains(Direction.SOUTH)) {
          builder.append(String.format("%3s", "|"));
        } else {
          builder.append("   ");
        }
        builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }


  private int countEdges(Location[][] locations) {
    int count = 0;
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[i].length; j++) {
        if (locations[i][j].getValidMoves().contains(Direction.SOUTH)) {
          count++;
        }
        if (locations[i][j].getValidMoves().contains(Direction.EAST)) {
          count++;
        }
      }
    }
    return count;
  }


  private int distanceBetweenLocations(Location[][] locationsHeader,
                                       Location locationHeader1,
                                       Location locationHeader2) {
    Location location1 = locationHeader1.getCopy();
    Location location2 = locationHeader2.getCopy();
    Location[][] locations = new Location[locationsHeader.length][locationsHeader[0].length];
    for (int i = 0; i < locationsHeader.length; i++) {
      for (int j = 0; j < locationsHeader[0].length; j++) {
        locations[i][j] = locationsHeader[i][j].getCopy();
      }
    }
    int rows = locations.length;
    int cols = locations[0].length;
    boolean[][] visited = new boolean[rows][cols];
    Deque<Location> deque = new ArrayDeque<Location>();
    Deque<Integer> distance = new ArrayDeque<Integer>();
    visited[location1.getXLocation()][location1.getYLocation()] = true;
    deque.addLast(location1);
    distance.addLast(0);
    while (deque.size() != 0) {
      Location location = deque.pop();
      visited[location.getXLocation()][location.getYLocation()] = true;
      int dist = distance.pop();
      if (location.getXLocation() == location2.getXLocation()
          && location.getYLocation() == location2.getYLocation()) {
        return dist;
      }
      for (Direction direction : location.getValidMoves()) {
        Location child = getChild(location, direction, locations);
        if (!visited[child.getXLocation()][child.getYLocation()]) {
          deque.addLast(child);
          distance.addLast(dist + 1);
        }
      }
    }
    return Integer.MIN_VALUE;
  }

  private Location getChild(Location locationHeader,
                            Direction direction,
                            Location[][] locationsHeader) {
    Location location = locationHeader.getCopy();
    Location[][] locations = new Location[locationsHeader.length][locationsHeader[0].length];
    for (int i = 0; i < locationsHeader.length; i++) {
      for (int j = 0; j < locationsHeader[0].length; j++) {
        locations[i][j] = locationsHeader[i][j].getCopy();
      }
    }
    int rows = locations.length;
    int cols = locations[0].length;
    int x = location.getXLocation();
    int y = location.getYLocation();
    if (direction == Direction.NORTH) {
      x = x - 1;
      if (x < 0) {
        x = rows - 1;
      }
    }
    if (direction == Direction.SOUTH) {
      x = x + 1;
      if (x >= rows) {
        x = 0;
      }
    }
    if (direction == Direction.EAST) {
      y = y + 1;
      if (y >= cols) {
        y = 0;
      }
    }
    if (direction == Direction.WEST) {
      y = y - 1;
      if (y < 0) {
        y = cols - 1;
      }
    }
    return locations[x][y].getCopy();
  }

  //testing pit
  @Test
  public void testPit() {
    //tests both pit neighbours and player falling into the pit.
    Player player = new PlayerImpl("mario");
    Dungeon model = new DungeonImpl(5, 5, 3, true, player, 6);
    model.assignMonsters(1);
    model.assignPitLocation();
    //pit allocated.
    assertTrue(model.getPitLocation() != null);
    model.makeMove(Direction.EAST);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.WEST);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.EAST);
    model.makeMove(Direction.EAST);
    //detect a pit neighbour.
    assertTrue(model.detectPitNearby());
    model.makeMove(Direction.EAST);
    //assert game is over
    assertTrue(model.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAfterPit() {
    //tests both pit neighbours and player falling into the pit.
    Player player = new PlayerImpl("mario");
    Dungeon model = new DungeonImpl(5, 5, 3, true, player, 6);
    model.assignMonsters(1);
    model.assignPitLocation();
    //pit allocated.
    model.makeMove(Direction.WEST);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.EAST);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.EAST);
    model.makeMove(Direction.EAST);
    model.makeMove(Direction.EAST);
    model.makeMove(Direction.EAST);
    model.makeMove(Direction.SOUTH);
    model.makeMove(Direction.WEST);
    //moving after falling into pit.
    model.makeMove(Direction.EAST);
  }

  //testing pit
  @Test
  public void testThief() {
    //tests both pit neighbours and player falling into the pit.
    Player player = new PlayerImpl("mario");
    Dungeon model = new DungeonImpl(5, 5, 3, true, player, 6);
    model.assignMonsters(1);
    model.assignTreasuresAndWeapons(55);

    model.assignThiefLocation();
    model.makeMove(Direction.WEST);
    model.makeMove(Direction.NORTH);
    model.pickupTreasures();
    assertTrue(player.getTreasures().size() > 0);
    model.makeMove(Direction.SOUTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.SOUTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.SOUTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.SOUTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.SOUTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.SOUTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.SOUTH);
    model.makeMove(Direction.NORTH);
    model.makeMove(Direction.SOUTH);
    Location currentLocation = model.getCurrentLocation();
    Location thiefLocation = model.getThiefLocation();
    assertTrue(currentLocation.getXLocation() == thiefLocation.getXLocation());
    assertTrue(currentLocation.getYLocation() == thiefLocation.getYLocation());
    assertTrue(player.getTreasures().size() == 0);
  }


}
