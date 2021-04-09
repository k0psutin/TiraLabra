package pathfinding.entities;

import java.util.Objects;
import pathfinding.interfaces.Generated;

/**
 * Node class. Holds the information of current coordinates and moving costs.
 *
 * <p>Implements Comparable and sorts PriorityQueue by the lowest F score.
 */
public class Node implements Comparable<Node> {

  private int posX;
  private int posY;
  private double totalCost;
  private Node parent;

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
    this.totalCost = 0;
  }

  public int getPosX() {
    return this.posX;
  }

  public int getPosY() {
    return this.posY;
  }

  public double getTotalCost() {
    return this.totalCost;
  }

  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }

  public Node getParent() {
    return this.parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  @Override
  public int compareTo(Node o) {
    return (totalCost < o.totalCost) ? -1 : (o.totalCost < totalCost) ? 1 : 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Node)) {
      return false;
    }

    Node other = (Node) obj;

    if (other.posX != posX) {
      return false;
    }
    if (other.posY != posY) {
      return false;
    }
    return true;
  }

  @Override
  @Generated
  public int hashCode() {
    return Objects.hash(posX, posY);
  }

  @Override
  @Generated
  public String toString() {
    return "(" + posX + "," + posY + ") - totalCost: " + totalCost;
  }
}
