package pathfinding.entities;

import java.util.Objects;

/**
 * Node class. Holds the information of current coordinates and moving costs.
 *
 * <p>Implements Comparable and sorts PriorityQueue by the lowest F score.
 */
public class Node implements Comparable<Node> {

  public int posX;
  public int posY;
  public float scoreF;
  public Node parent;

  /**
   * Node constructor.
   *
   * @param parent Node parent.
   * @param posX Node position x.
   * @param posY Node position y.
   */
  public Node(Node parent, int posX, int posY) {
    this.parent = parent;
    this.posX = posX;
    this.posY = posY;
    this.scoreF = 0;
  }

  @Override
  public int compareTo(Node o) {
    return (scoreF < o.scoreF) ? -1 : (o.scoreF < scoreF) ? 1 : 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Node)) {
      return false;
    }
    Node other = (Node) obj;
    return ((posX == other.posX) && (posY == other.posY));
  }

  @Override
  public int hashCode() {
    return Objects.hash(posX, posY);
  }

  @Override
  public String toString() {
    return "(" + posX + "," + posY + ") - f-score: " + scoreF;
  }
}
