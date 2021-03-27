package pathfinding.entities;

/**
 * Node class. Holds the information of current coordinates and moving costs.
 *
 * <p>Implements Comparable and sorts PriorityQueue by the lowest F score.
 */
public class Node implements Comparable<Node> {

  public int posX;
  public int posY;
  public int dx;
  public int dy;
  public float scoreG;
  public float scoreH;
  public float scoreF;
  public Node parent;

  /**
   * Node constructor.
   *
   * @param parent Node parent.
   * @param posX Node position x.
   * @param posY Node position y.
   * @param scoreG Node total movement score.
   * @param scoreH Node estimated cost from current position to end.
   */
  public Node(Node parent, int posX, int posY, float scoreG, float scoreH) {
    this.parent = parent;
    this.posX = posX;
    this.posY = posY;
    this.scoreG = scoreG;
    this.scoreH = scoreH;
    this.scoreF = scoreG + scoreH; // Total cost of travel F = G + H
    this.dx = 0;
    this.dy = 0;
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
  public String toString() {
    return "(" + posX + "," + posY + ")";
  }
}
