package pathfinding.entities;

public class Node implements Comparable<Node> {

  public int posX;
  public int posY;
  public int dx;
  public int dy;
  public float scoreG;
  public float scoreH;
  public float scoreF;
  public Node parent;

  public Node(Node parent, int posX, int posY, float scoreG, float scoreH) {
    this.parent = parent;
    this.posX = posX;
    this.posY = posY;
    this.scoreG = scoreG;
    this.scoreH = scoreH;
    this.scoreF = scoreG + scoreH;
    this.dx = 0;
    this.dy = 0;
  }

  @Override
  public int compareTo(Node o) {
    return (int) (scoreF - o.scoreF);
  }

  @Override
  public String toString() {
    return "(" + posX + "," + posY + ")";
  }

  public Node getParent() {
    return (parent == null) ? parent : null;
  }
}
