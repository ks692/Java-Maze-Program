package dungeon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import location.Cave;
import location.Direction;
import location.Location;
import location.Tunnel;
import monster.Monster;
import monster.Otyugh;
import player.Player;
import player.PlayerImpl;
import player.Treasure;
import randomgenerator.RandomGenerator;
import randomgenerator.RangeRandomGenerator;
import weapon.Arrow;

/**
 * DungeonImpl is a game model. it allows the player to
 * navigate a maze made of tunnels and caves, collect
 * treasures and reach the end location. it is represented
 * by the rows, columns, interconnectivity, wrapping.
 * locationGenerator,caveGenerator, treasureGenerator.
 */
public final class DungeonImpl implements Dungeon {
  private final int rows;
  private final int cols;
  private final int interConnectivity;
  private final boolean wrapping;
  private final RandomGenerator edgeGenerator;
  private final RandomGenerator coinGenerator;
  private final int seed;
  private final boolean[][] visited;
  private final RandomGenerator thiefRowGenerator;
  private final RandomGenerator thiefColGenerator;
  private Player player;
  private Location[][] locations;
  private Location startLocation;
  private Location endLocation;
  private Location currentLocation;
  private boolean isGameStarted;
  private boolean isGameOver;
  private Location[][] locationsCopy;
  private Location startLocationCopy;
  private Location endLocationCopy;
  private Location pitLocation;
  private Location pitLocationCopy;
  private Location thiefLocation;
  private Location thiefLocationCopy;
  private int distance;


  /**
   * Constructs a {@link DungeonImpl} object in terms of row,
   * cols,interconnectivity,wrapping and player.
   *
   * @param rows              number of rows in the Dungeon.
   * @param cols              number of cols in the Dungeon.
   * @param interConnectivity number of extra edges in mst.
   * @param wrapping          if the corner edges wrap around connecting back to the dungeon front.
   * @param player            player to be added to dungeon.
   * @throws IllegalArgumentException if rows or cols should be less than 5.
   * @throws IllegalArgumentException if interconnectivity is less than 0.
   * @throws IllegalArgumentException if the player is null.
   */
  public DungeonImpl(int rows,
                     int cols,
                     int interConnectivity,
                     boolean wrapping,
                     Player player) {
    if (interConnectivity < 0 || rows < 5 || cols < 5) {
      throw new IllegalArgumentException("Illegal arguments");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player is null");
    }
    this.rows = rows;
    this.cols = cols;
    this.interConnectivity = interConnectivity;
    this.wrapping = wrapping;
    this.seed = 0;
    this.edgeGenerator = new RangeRandomGenerator(0, getPossibleEdges(rows, cols, wrapping), seed);
    this.coinGenerator = new RangeRandomGenerator(0, 2, seed);
    createLocations(rows, cols, interConnectivity, wrapping);
    this.player = player;
    RandomGenerator caveGenerator = new RangeRandomGenerator(0, getCaves().size(), seed);
    boolean assigned = assignLocations(caveGenerator);
    if (!assigned) {
      recreateDungeon(caveGenerator);
    }
    visited = new boolean[rows][cols];
    this.currentLocation = this.locations
            [startLocation.getXLocation()]
            [startLocation.getYLocation()];
    visited[startLocation.getXLocation()]
            [startLocation.getYLocation()]
            = true;
    this.addArrows();
    isGameOver = false;
    isGameStarted = false;
    this.thiefRowGenerator = new RangeRandomGenerator(0, rows, seed);
    this.thiefColGenerator = new RangeRandomGenerator(0, cols, seed);
  }


  /**
   * Constructs a {@link DungeonImpl} object in terms of row,
   * cols,interconnectivity,wrapping and player.
   *
   * @param rows              number of rows in the Dungeon.
   * @param cols              number of cols in the Dungeon.
   * @param interConnectivity number of extra edges in mst.
   * @param wrapping          if the corner edges wrap around connecting back to the dungeon front.
   * @param player            player to be added to dungeon.
   * @param seed              seed to give to dungeon. if the seed is zero the dungeon is random.
   * @throws IllegalArgumentException if rows or cols should be less than 5.
   * @throws IllegalArgumentException if interconnectivity is less than 0.
   * @throws IllegalArgumentException if the player is null.
   */
  public DungeonImpl(int rows,
                     int cols,
                     int interConnectivity,
                     boolean wrapping,
                     Player player,
                     int seed) {
    if (interConnectivity < 0 || rows < 5 || cols < 5) {
      throw new IllegalArgumentException("Illegal arguments");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player is null");
    }
    this.rows = rows;
    this.cols = cols;
    this.interConnectivity = interConnectivity;
    this.wrapping = wrapping;
    this.seed = seed;
    this.edgeGenerator = new RangeRandomGenerator(0, getPossibleEdges(rows, cols, wrapping), seed);
    this.coinGenerator = new RangeRandomGenerator(0, 2, seed);
    createLocations(rows, cols, interConnectivity, wrapping);
    this.player = player;
    RandomGenerator caveGenerator = new RangeRandomGenerator(0, getCaves().size(), seed);
    boolean assigned = assignLocations(caveGenerator);
    if (!assigned) {
      recreateDungeon(caveGenerator);
    }
    visited = new boolean[rows][cols];
    this.currentLocation = this.locations
            [startLocation.getXLocation()]
            [startLocation.getYLocation()];
    this.addArrows();
    visited[startLocation.getXLocation()]
            [startLocation.getYLocation()]
            = true;
    this.thiefRowGenerator = new RangeRandomGenerator(0, rows, seed);
    this.thiefColGenerator = new RangeRandomGenerator(0, cols, seed);
    isGameStarted = false;
  }

  private void copyLocations() {
    locationsCopy = new Location[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        locationsCopy[i][j] = locations[i][j].getCopy();
      }
    }
  }

  private void addArrows() {
    for (int i = 0; i < 3; i++) {
      this.player.addArrow(Arrow.CROOKED);
    }
  }


  private int getPossibleEdges(int rows, int cols, boolean wrapping) {
    int sum = rows * (cols - 1) + cols * (rows - 1);
    if (!wrapping) {
      return sum;
    }
    return sum + rows + cols;
  }

  private void recreateDungeon(RandomGenerator caveGenerator) {
    RandomGenerator testGenerator = caveGenerator.getCopy();
    boolean assigned = false;
    for (int i = 0; i < rows * cols; i++) {
      if (!assigned) {
        createLocations(rows, cols, interConnectivity, wrapping);
        testGenerator = new RangeRandomGenerator(0, getCaves().size(), seed);
        assigned = assignLocations(testGenerator);
      }
    }
    if (!assigned) {
      throw new IllegalArgumentException("caves cannot be created");
    }
  }

  private boolean assignLocations(RandomGenerator randomGenerator) {
    //collect caves
    RandomGenerator testGenerator = randomGenerator.getCopy();
    List<Location> caves = this.getCaves();
    final int size = 2;
    if (caves.size() < size) {
      return false;
    }
    int number = testGenerator.getNextNumber();
    Location location = caves.get(number);
    caves.remove(location);
    final int dist = 5;
    Map<Location, Integer> map = distanceBetweenLocations(location);
    //int pitDistance=Integer.MIN_VALUE;
    for (int i = 0; i < caves.size(); i++) {
      int distance = map.get(caves.get(i));
      if (distance >= dist) {
        this.startLocation = this.locations[location.getXLocation()][location.getYLocation()];
        this.startLocationCopy = this.locationsCopy
                [location.getXLocation()]
                [location.getYLocation()];
        Location temp = caves.get(i);
        this.endLocation = this.locations[temp.getXLocation()][temp.getYLocation()];
        this.endLocationCopy = this.locationsCopy[temp.getXLocation()][temp.getYLocation()];
        this.distance = distance;
        //System.out.println("distance:"+distance);
        return true;
      }
    }
    return false;
  }

  @Override
  public void assignPitLocation() {
    int findDistance = this.distance + 1;
    Map<Location, Integer> map = sortByValue(distanceBetweenLocations(startLocation.getCopy()));

    for (Map.Entry<Location, Integer> entry : map.entrySet()) {
      if (entry.getValue() >= findDistance) {
        Location temp = entry.getKey();
        this.pitLocation = this.locations[temp.getXLocation()][temp.getYLocation()];
        this.pitLocationCopy = this.locationsCopy[temp.getXLocation()][temp.getYLocation()];
        break;
      }
    }
  }

  private HashMap<Location, Integer> sortByValue(Map<Location, Integer> hm) {
    List<Map.Entry<Location, Integer>> list =
            new LinkedList<Map.Entry<Location, Integer>>(hm.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<Location, Integer>>() {
      public int compare(Map.Entry<Location, Integer> o1,
                         Map.Entry<Location, Integer> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });
    HashMap<Location, Integer> temp = new LinkedHashMap<>();
    for (Map.Entry<Location, Integer> mapVal : list) {
      temp.put(mapVal.getKey(), mapVal.getValue());
    }
    return temp;
  }


  @Override
  public void assignThiefLocation() {
    int rand = thiefRowGenerator.getNextNumber();
    int rand2 = thiefColGenerator.getNextNumber();
    this.thiefLocation = this.locations[rand][rand2];
    this.thiefLocationCopy = this.locations[rand][rand2];
  }

  @Override
  public Location getThiefLocation() {
    if (thiefLocation == null) {
      return null;
    }
    return this.thiefLocation.getCopy();
  }


  private Map<Location, Integer> distanceBetweenLocations(Location location1) {
    Location locationHolder1 = location1.getCopy();
    boolean[][] visited = new boolean[rows][cols];
    Deque<Location> deque = new ArrayDeque<Location>();
    Deque<Integer> distance = new ArrayDeque<Integer>();
    Map<Location, Integer> map = new HashMap<Location, Integer>();
    visited[locationHolder1.getXLocation()][locationHolder1.getYLocation()] = true;
    deque.addLast(locationHolder1);
    distance.addLast(0);
    while (!deque.isEmpty()) {
      Location location = deque.pop();
      visited[location.getXLocation()][location.getYLocation()] = true;
      int dist = distance.pop();
      map.put(location, dist);
      for (Direction direction : location.getValidMoves()) {
        Location child = getChild(location, direction);
        if (!visited[child.getXLocation()][child.getYLocation()]) {
          deque.addLast(child);
          distance.addLast(dist + 1);
        }
      }
    }
    return map;
  }

  private Location getChild(Location location, Direction direction) {
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

  private List<Location> getCaves() {
    List<Location> caves = new ArrayList<Location>();
    final int size = 2;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (locations[i][j].getValidMoves().size() != size) {
          caves.add(locations[i][j].getCopy());
        }
      }
    }
    return caves;
  }


  private List<Location> getAllLocations() {
    List<Location> locations = new ArrayList<Location>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        locations.add(this.locations[i][j].getCopy());
      }
    }
    return locations;
  }

  private void createLocations(int rows, int cols, int interConnectivity, boolean wrapping) {
    //1.Make valid Sets.
    List<Edge> edges = validEdgesResult(rows, cols, interConnectivity, wrapping, edgeGenerator);
    Location[][] locations = new Location[rows][cols];
    this.locations = locations;
    for (Edge edge : edges) {
      int k = edge.getSrc();
      int x1 = k / cols;
      int y1 = k % cols;
      k = edge.getDest();
      int x2 = k / cols;
      int y2 = k % cols;
      addLocation(x1, y1, getValidDirection(x1, y1, x2, y2));
      addLocation(x2, y2, getValidDirection(x2, y2, x1, y1));
    }
    copyLocations();
  }

  private void addLocation(int x, int y, Direction direction) {
    List<Direction> directionList;
    if (this.locations[x][y] == null) {
      directionList = new ArrayList<Direction>();
      directionList.add(direction);
      this.locations[x][y] = new Cave(x, y, directionList);
      return;
    }
    directionList = this.locations[x][y].getValidMoves();
    directionList.add(direction);
    int size = directionList.size();
    if (size == 2) {
      this.locations[x][y] = new Tunnel(x, y, directionList);
      return;
    }
    this.locations[x][y] = new Cave(x, y, directionList);
    return;
  }


  private Direction getValidDirection(int x1, int y1, int x2, int y2) {
    if ((x2 - x1 == 1 && y2 - y1 == 0) || (x2 - x1 < -1 && y2 - y1 == 0)) {
      return Direction.SOUTH;
    } else if ((x2 - x1 == -1 && y2 - y1 == 0) || (x2 - x1 > 1 && y2 - y1 == 0)) {
      return Direction.NORTH;
    } else if ((y2 - y1 == 1 && x2 - x1 == 0) || (y2 - y1 < -1 && x2 - x1 == 0)) {
      return Direction.EAST;
    } else if ((y2 - y1 == -1 && x2 - x1 == 0) || (y2 - y1 > 1 && x2 - x1 == 0)) {
      return Direction.WEST;
    }
    return null;
  }

  private List<Edge> validEdgesResult(int rows,
                                      int cols,
                                      int interConnectivity,
                                      boolean wrapping,
                                      RandomGenerator edgeGenerator) {
    RandomGenerator edgeCopy = edgeGenerator.getCopy();
    List<Edge> possibleEdges;
    final int product = rows * cols;
    if (!wrapping) {
      possibleEdges = possibleEdgesNonWrapping(rows, cols);
    } else {
      possibleEdges = possibleEdgesWrapping(rows, cols);
    }
    //create sets
    List<Set<Integer>> sets = new ArrayList<Set<Integer>>();
    for (int i = 0; i < product; i++) {
      Set<Integer> set = new HashSet<Integer>();
      set.add(i);
      sets.add(set);
    }
    List<Edge> selectedEdges = getSelectedEdges(sets, possibleEdges, edgeCopy, interConnectivity);
    return selectedEdges;
  }

  private List<Edge> getSelectedEdges(List<Set<Integer>> sets,
                                      List<Edge> possibleEdges,
                                      RandomGenerator randomGenerator,
                                      int interConnectivity) {
    List<Edge> selectedEdges = new ArrayList<Edge>();
    List<Edge> unselectedEdges = new ArrayList<Edge>();
    int count = possibleEdges.size();
    int counter = 0;
    while (counter < count) {
      int temp = randomGenerator.getNextNumber();
      counter++;
      Edge edge = possibleEdges.get(temp);
      int index1 = findSet(sets, edge.getSrc());
      int index2 = findSet(sets, edge.getDest());
      if (index1 == index2) {
        unselectedEdges.add(edge);
      } else {
        selectedEdges.add(edge);
        sets = union(sets, index1, index2);
      }
      possibleEdges.remove(edge);
      randomGenerator.lowerLimit();
    }

    if (interConnectivity > unselectedEdges.size()) {
      throw new IllegalArgumentException("interconnectivity > Edges");
    }
    for (int i = 0; i < interConnectivity; i++) {
      selectedEdges.add(unselectedEdges.get(i));
    }
    return selectedEdges;

  }

  private List<Set<Integer>> union(List<Set<Integer>> sets, int index1, int index2) {
    Set<Integer> set = new HashSet<Integer>();
    Set<Integer> set1 = sets.get(index1);
    set.addAll(set1);
    Set<Integer> set2 = sets.get(index2);
    set.addAll(set2);
    sets.remove(set1);
    sets.remove(set2);
    sets.add(set);
    return sets;
  }

  private int findSet(List<Set<Integer>> sets, int x) {
    for (Set<Integer> set : sets) {
      if (set.contains(x)) {
        return sets.indexOf(set);
      }
    }
    return -1;
  }


  private List<Edge> possibleEdgesNonWrapping(int rows, int cols) {
    final List<Edge> possibleEdges = new ArrayList<Edge>();
    final int product = rows * cols;
    for (int i = 0; i < product; i++) {
      if ((i + 1) < product && (i + 1) % cols != 0) {
        possibleEdges.add(new Edge(i, i + 1));
      }
      if ((i + cols) < product) {
        possibleEdges.add(new Edge(i, i + cols));
      }
    }
    return possibleEdges;
  }

  private List<Edge> possibleEdgesWrapping(int rows, int cols) {
    final List<Edge> possibleEdges = new ArrayList<Edge>();
    final int product = rows * cols;
    for (int i = 0; i < product; i++) {
      if ((i + 1) < product && (i + 1) % cols != 0) {
        possibleEdges.add(new Edge(i, i + 1));
      }
      if ((i + 1) % cols == 0) {
        int temp = (i + 1) - cols;
        if (temp != i) {
          possibleEdges.add(new Edge(i, temp));
        }
      }
      if ((i + cols) < product) {
        possibleEdges.add(new Edge(i, i + cols));
      }
      if ((i + cols) >= product) {
        if ((i + cols - product) != i) {
          possibleEdges.add(new Edge(i, (i + cols - product)));
        }
      }
    }
    return possibleEdges;
  }


  private void assignArtifacts(double resultPercentage,
                               List<Location> locations,
                               String artifact)
          throws IllegalArgumentException {
    resultPercentage = resultPercentage * locations.size();
    resultPercentage = Math.ceil(resultPercentage);
    RandomGenerator randomSelector = new RangeRandomGenerator(0, locations.size(), seed);
    double size = locations.size() - resultPercentage;
    RandomGenerator treasureGenerator = new RangeRandomGenerator(0, 3, seed);
    for (int i = 0; i < locations.size(); i++) {
      for (int j = 0; j < 1 + treasureGenerator.getNextNumber(); j++) {
        int xLoc = locations.get(i).getXLocation();
        int yLoc = locations.get(i).getYLocation();
        if (artifact.equalsIgnoreCase("treasure")) {
          this.locations[xLoc][yLoc].addTreasure(getRandomTreasure(treasureGenerator));
        }
        if (artifact.equalsIgnoreCase("arrow")) {
          this.locations[xLoc][yLoc].addArrow(Arrow.CROOKED);
        }
      }
    }
    HashSet<Integer> hs = new HashSet<Integer>();
    int count = 0;
    while (count < size) {
      int index = randomSelector.getNextNumber();
      if (hs.contains(index)) {
        continue;
      }
      int xLoc = locations.get(index).getXLocation();
      int yLoc = locations.get(index).getYLocation();
      if (artifact.equalsIgnoreCase("treasure")) {
        this.locations[xLoc][yLoc].pickUpTreasures();
      }
      if (artifact.equalsIgnoreCase("arrow")) {
        this.locations[xLoc][yLoc].pickUpArrows();
      }
      hs.add(index);
      count++;
    }

  }


  private Treasure getRandomTreasure(RandomGenerator random) {
    int temp = random.getNextNumber();
    final List<Treasure> treasures = new ArrayList<Treasure>();
    treasures.add(Treasure.DIAMOND);
    treasures.add(Treasure.RUBY);
    treasures.add(Treasure.SAPPHIRE);
    return treasures.get(temp);
  }

  @Override
  public void assignTreasuresAndWeapons(int percentage) throws IllegalArgumentException {
    if (isGameStarted) {
      throw new IllegalArgumentException("Game already started.");
    }
    if (percentage < 0) {
      throw new IllegalArgumentException("Percentage less than zero");
    }
    RandomGenerator randomGenerator = new RangeRandomGenerator(0, (100 - percentage), seed);
    int random = randomGenerator.getNextNumber();
    double result = random + percentage;
    result = result / 100;
    final String treasure = "treasure";
    final String arrow = "arrow";
    this.assignArtifacts(result, getCaves(), treasure);
    this.assignArtifacts(result, getAllLocations(), arrow);
    copyLocations();
  }

  @Override
  public void assignMonsters(int numCaves) throws IllegalArgumentException {
    if (numCaves < 1) {
      throw new IllegalArgumentException("numCaves must be at least 1");
    }
    if (numCaves > getCaves().size()) {
      throw new IllegalArgumentException("number of monsters greater than number of caves");
    }
    if (isGameStarted) {
      throw new IllegalArgumentException("Game already started.");
    }
    List<Location> caves = this.getCaves();
    int id = 0;
    caves.remove(startLocation);
    Location tempCave = null;
    for (Location location : caves) {
      if (endLocation.equals(location)) {
        //add a monster to the cave.
        this.endLocation.addMonster(new Otyugh(id));
        tempCave = location;
        id++;
        numCaves--;
        break;
      }
    }
    caves.remove(tempCave);
    int size = 0;
    RandomGenerator randomGenerator = new RangeRandomGenerator(0, caves.size(), seed);
    int temp = 0;
    while (numCaves != size) {
      numCaves--;
      temp = randomGenerator.getNextNumber();
      Location location = caves.get(temp);
      Location actualLocation = this.locations
              [caves.get(temp).getXLocation()]
              [caves.get(temp).getYLocation()];
      actualLocation.addMonster(new Otyugh(id));
      caves.remove(temp);
      randomGenerator.lowerLimit();
      id++;
    }
    copyLocations();
  }

  @Override
  public String getPlayerDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("Player : %s \n", player.getName()));
    Location tempLocation = this.currentLocation;
    Location location = this.locations[tempLocation.getXLocation()][tempLocation.getYLocation()];
    stringBuilder.append(
            String.format(
                    "Player Coordinates: (%d,%d)\n",
                    location.getXLocation(),
                    location.getYLocation()));
    Map<Treasure, Integer> hm = getTreasureMap();
    stringBuilder.append(String.format("Player Treasures: %s\n", hm.toString()));
    stringBuilder.append(String.format("Player Arrows: %s", this.player.getArrows().toString()));
    return stringBuilder.toString();
  }

  private Map<Treasure, Integer> getTreasureMap() {
    Map<Treasure, Integer> hm = new TreeMap<Treasure, Integer>();
    for (int i = 0; i < player.getTreasures().size(); i++) {
      if (!hm.containsKey(player.getTreasures().get(i))) {
        hm.put(player.getTreasures().get(i), 1);
      } else {
        int quantity = hm.get(player.getTreasures().get(i));
        quantity++;
        hm.put(player.getTreasures().get(i), quantity);
      }
    }
    return hm;
  }

  @Override
  public String getLocationDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    Location tempLocation = this.currentLocation;
    Location location = this.locations[tempLocation.getXLocation()][tempLocation.getYLocation()];
    stringBuilder.append(
            String.format("Location Treasures: %s \n",
                    location.getTreasures().toString()));
    stringBuilder.append(String.format(
            "Location Arrows: %s\n", this.currentLocation.getArrows().toString()));
    List<Direction> directions = location.getValidMoves();
    Collections.sort(directions);
    stringBuilder.append(
            String.format("Location Valid Moves: %s \n",
                    directions.toString()));
    return stringBuilder.toString();
  }

  @Override
  public boolean makeMove(Direction direction) throws IllegalArgumentException {
    isGameStarted = true;
    if (isGameOver) {
      throw new IllegalArgumentException("Game is Over");
    }
    if (direction == null) {
      throw new IllegalArgumentException("direction is null");
    }
    if (!this.currentLocation.getValidMoves().contains(direction)) {
      return false;
    }
    Location tempLocation = this.currentLocation;
    Location originalLocation = this.locations
            [tempLocation.getXLocation()]
            [tempLocation.getYLocation()];
    Location location = getChild(originalLocation, direction);
    //check for monsters
    List<Monster> monsters = this.locations
            [location.getXLocation()]
            [location.getYLocation()]
            .getMonsters();
    if (monsters.size() > 1) {
      this.isGameOver = true;
    } else if (monsters.size() == 1) {
      if (monsters.get(0).getHealth() == 2) {
        isGameOver = true;
      } else {
        if (coinGenerator.getNextNumber() != 1) {
          this.isGameOver = true;
        }
      }
    } else if (isPitLocation(location)) {
      this.isGameOver = true;
    }

    //update current location
    this.currentLocation = locations
            [location.getXLocation()]
            [location.getYLocation()];
    visited[location.getXLocation()]
            [location.getYLocation()]
            = true;
    if (this.currentLocation == locations
            [endLocation.getXLocation()]
            [endLocation.getYLocation()]) {
      this.isGameOver = true;
    }
    if (this.thiefLocation != null) {
      int rand = thiefRowGenerator.getNextNumber();
      int rand2 = thiefColGenerator.getNextNumber();
      this.thiefLocation = this.locations[rand][rand2];
      if (rand == currentLocation.getXLocation()
          && rand2 == currentLocation.getYLocation()) {
        this.player.emptyTreasures();
      }
    }
    return true;
  }

  private boolean isPitLocation(Location location) {
    Location copy = location.getCopy();
    if (pitLocation == null) {
      return false;
    }
    return (pitLocation.getXLocation() == copy.getXLocation()
            && pitLocation.getYLocation() == copy.getYLocation());
  }


  @Override
  public boolean shootArrow(int distance, Direction direction) throws IllegalArgumentException {
    if (distance < 0) {
      throw new IllegalArgumentException("distance is negative");
    }
    if (direction == null) {
      throw new IllegalArgumentException("direction is null");
    }
    if (!canShoot()) {
      throw new IllegalStateException("Out of arrows");
    }
    int dist = 0;
    Location currLocation = this.currentLocation;
    Direction currDirection = direction;
    Location dest = null;
    while (dist < distance) {
      if (!currLocation.getValidMoves().contains(currDirection)) {
        break;
      }
      dest = getChild(currLocation, currDirection);
      if (dest.getValidMoves().size() != 2) {
        dist++;
        currLocation = dest;
      } else {
        Direction temp = getOppositeDirection(currDirection);
        for (Direction dir : dest.getValidMoves()) {
          if (!dir.equals(temp)) {
            currDirection = dir;
            currLocation = dest;
            break;
          }
        }
      }
    }
    this.player.useArrow(Arrow.CROOKED);
    if (dist == distance) {
      boolean flag = false;
      if (locations
                  [currLocation.getXLocation()]
                  [currLocation.getYLocation()]
                  .getMonsters().size() > 0) {
        flag = true;
      }
      killMonster(currLocation);
      return flag;
    }
    return false;
  }

  @Override
  public boolean canShoot() {
    for (Map.Entry<Arrow, Integer> mapEntry : player.getArrows().entrySet()) {
      if (mapEntry.getValue() > 0) {
        return true;
      }
    }
    return false;
  }

  private void killMonster(Location location) {
    //find monster location
    Location locationCopy = location.getCopy();
    final Location monsterLocation = locations
            [locationCopy.getXLocation()]
            [locationCopy.getYLocation()];
    //kill monster
    if (monsterLocation.getMonsters().size() > 0) {
      Monster monster = monsterLocation.getMonsters().get(0);
      monster.reduceHealth();
      locations
              [monsterLocation.getXLocation()]
              [monsterLocation.getYLocation()]
              .removeMonster(monster);
      if ((monster.getHealth() != 0)) {
        locations
                [monsterLocation.getXLocation()]
                [monsterLocation.getYLocation()]
                .addMonster(monster);
      }
    }
  }

  private Direction getOppositeDirection(Direction direction) {
    if (direction == Direction.SOUTH) {
      return Direction.NORTH;
    }
    if (direction == Direction.NORTH) {
      return Direction.SOUTH;
    }
    if (direction == Direction.EAST) {
      return Direction.WEST;
    }
    if (direction == Direction.WEST) {
      return Direction.EAST;
    }
    return null;
  }


  @Override
  public Location getStartLocation() {
    return this.startLocation.getCopy();
  }

  @Override
  public Location getCurrentLocation() {
    return this.currentLocation.getCopy();
  }

  @Override
  public Location getEndLocation() {
    return this.endLocation.getCopy();
  }

  @Override
  public Location[][] getLocationGrid() {
    Location[][] locationArray = new Location[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        locationArray[i][j] = locations[i][j].getCopy();
      }
    }
    return locationArray;
  }

  @Override
  public void pickupTreasures() {
    Location location = this.currentLocation.getCopy();
    this.player.pickupTreasures(location.getTreasures());
    this.locations[location.getXLocation()][location.getYLocation()].pickUpTreasures();
  }

  @Override
  public Location getPitLocation() {
    if (pitLocation != null) {
      return pitLocation.getCopy();
    }
    return null;
  }

  @Override
  public void pickupArrows() {
    Location location = this.currentLocation.getCopy();
    for (Arrow arrow : location.getArrows()) {
      this.player.addArrow(arrow);
    }
    this.locations[location.getXLocation()][location.getYLocation()].pickUpArrows();
  }

  @Override
  public boolean isGoalState() {
    Location location = this.currentLocation;
    return location.getXLocation() == endLocation.getXLocation()
           && location.getYLocation() == endLocation.getYLocation();
  }

  @Override
  public boolean isGameOver() {
    return this.isGameOver;
  }

  @Override
  public boolean detectStrongSmell() {
    return strongSmellHelper();
  }

  @Override
  public boolean detectWeakSmell() {
    return weakSmellHelper();
  }

  @Override
  public boolean detectPitNearby() {
    if (pitLocation == null) {
      return false;
    }
    for (Direction direction : currentLocation.getValidMoves()) {
      Location location = getChild(currentLocation, direction);
      if (isPitLocation(location)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean[][] getVisited() {
    boolean[][] visitedCopy = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        visitedCopy[i][j] = visited[i][j];
      }
    }
    return visitedCopy;
  }

  @Override
  public Player getPlayer() {
    return this.player.getCopy();
  }

  @Override
  public void resetGameState() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        locations[i][j] = locationsCopy[i][j].getCopy();
        visited[i][j] = false;
      }
    }
    this.isGameStarted = false;
    this.isGameOver = false;
    this.startLocation = this.locations
            [startLocationCopy.getXLocation()]
            [startLocationCopy.getYLocation()];
    this.endLocation = this.locations
            [endLocationCopy.getXLocation()]
            [endLocationCopy.getYLocation()];
    this.currentLocation = this.locations
            [startLocationCopy.getXLocation()]
            [startLocationCopy.getYLocation()];
    visited[startLocation.getXLocation()]
            [startLocation.getYLocation()] = true;
    if (pitLocationCopy != null) {
      this.pitLocation = this.locations
              [pitLocationCopy.getXLocation()]
              [pitLocationCopy.getYLocation()];
    }
    if (thiefLocationCopy != null) {
      this.thiefLocation = this.locations
              [thiefLocationCopy.getXLocation()]
              [thiefLocationCopy.getYLocation()];
    }
    this.player = new PlayerImpl(this.player.getName());
    this.addArrows();

  }


  private boolean weakSmellHelper() {
    Map<Location, Integer> map = distanceBetweenLocations(currentLocation);
    final int dist = 2;
    final int size = 1;
    int count = 0;
    for (Map.Entry<Location, Integer> mapItem : map.entrySet()) {
      if (mapItem.getValue() == dist && mapItem.getKey().getMonsters().size() == size) {
        count++;
      }
    }
    return count == 1;
  }

  private boolean strongSmellHelper() {
    Map<Location, Integer> map = distanceBetweenLocations(currentLocation);
    final int dist = 1;
    final int size = 0;
    int countNear = 0;
    int countFar = 0;
    for (Map.Entry<Location, Integer> mapItem : map.entrySet()) {
      if (mapItem.getValue() <= dist && mapItem.getKey().getMonsters().size() > size) {
        countNear++;
      }
      if (mapItem.getValue() == dist + 1 && mapItem.getKey().getMonsters().size() > size) {
        countFar++;
      }
    }
    return (countNear > 0 || countFar > 1);
  }


  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
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
    final int size = 2;
    if (location.getValidMoves().size() == size) {
      return "Tunnel";
    } else {
      return "Cave";
    }
  }


}
