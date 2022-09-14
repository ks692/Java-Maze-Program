package monster;

import java.util.Objects;

/**
 * Otyugh is a smelly monster that reside in dungeon
 * and are ready to hunt and devour players.
 */
public final class Otyugh implements Monster {

  private final int id;
  private int health;

  /**
   * Constructs a {@link Otyugh} Monster object in terms of unique id.
   *
   * @param id the unique id assigned to a {@link Otyugh}.
   * @throws IllegalArgumentException if {@code id} arguments is a negative integer.
   */
  public Otyugh(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("id cannot be negative");
    }
    this.health = 2;
    this.id = id;
  }

  /**
   * Constructs a {@link Otyugh} Monster object in terms of unique id,health.
   *
   * @param id     the unique id assigned to a {@link Otyugh}.
   * @param health the health of {@link Otyugh}.
   * @throws IllegalArgumentException if {@code id} arguments is a negative integer.
   */
  private Otyugh(int id, int health) {
    if (id < 0) {
      throw new IllegalArgumentException("id cannot be negative");
    }
    this.health = health;
    this.id = id;
  }


  @Override
  public int getId() {
    return id;
  }

  @Override
  public int getHealth() {
    return this.health;
  }

  @Override
  public void reduceHealth() {
    if (this.health == 0) {
      return;
    }
    this.health = this.health - 1;
  }

  @Override
  public Monster getCopy() {
    return new Otyugh(this.id, this.health);
  }

  @Override
  public boolean equals(Object o) {
    // check if it is equal to reference of the object.
    if (this == o) {
      return true;
    }
    //check if o is the instance of Monkey object
    if (!(o instanceof Otyugh)) {
      return false;
    }

    Monster that = (Otyugh) o;
    return this.id == that.getId();
  }


  /**
   * Creates a hashcode for the object.
   *
   * @return Returns a hash code value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }


  @Override
  public int compareTo(Monster o) {
    if (o.getId() == this.id) {
      return 0;
    }
    return o.getId() - this.id;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("id : ");
    builder.append(getId());
    builder.append(", ");
    builder.append("health : ");
    builder.append(getHealth());
    builder.append(". ");
    return builder.toString();
  }
}
