package monster;

/**
 * Monsters reside in dungeon and are
 * ready to hunt and devour players.
 */
public interface Monster extends Comparable<Monster> {

  /**
   * Get the id of the monster.
   *
   * @return the id of the monster.
   */
  int getId();

  /**
   * Get the health of the monster.
   *
   * @return the health of the monster.
   */
  int getHealth();

  /**
   * reduces the health of the monster.
   * Used when the player hits till zero.
   */
  void reduceHealth();

  /**
   * Create a new copy of the Monster object
   * with the given parameters. Written to maintain immutability.
   *
   * @return a new Monster object with the same information.
   */
  Monster getCopy();
}
