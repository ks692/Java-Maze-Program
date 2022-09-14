package dungeon;

/**
 * Edge represents a directed edge in a graph.
 * it is represented as source and destination.
 * it is used as a helper class for making locations
 * in the kruskal algorithm.
 */
final class Edge {
  private final int src;
  private final int dest;

  /**
   * Constructs a {@link Edge} object in terms of
   * source and destination.
   *
   * @param source      source of the edge.
   * @param destination destination of a edge.
   */
  Edge(int source, int destination) {
    this.src = source;
    this.dest = destination;
  }


  /**
   * Get the source of the edge.
   *
   * @return source of the edge.
   */
  public int getSrc() {
    return src;
  }

  /**
   * Get the destination of the edge.
   *
   * @return destination of the edge.
   */
  public int getDest() {
    return dest;
  }

}
