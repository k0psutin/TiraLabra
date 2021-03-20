package pathfinding.entities;

public class Node implements Comparable<Node> {

  public int posX;
  public int posY;
  public int scoreG;
  public int scoreH;
  public Node parent;

  public Node(Node parent, int posX, int posY, int scoreG, int scoreH) {
    this.parent = parent;
    this.posX = posX;
    this.posY = posY;
    this.scoreG = scoreG;
    this.scoreH = scoreH;
  }

  @Override
  public int compareTo(Node o) {
    return (this.scoreG + this.scoreH) - (o.scoreG + o.scoreH);
  }
}
