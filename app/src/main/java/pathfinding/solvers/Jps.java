package pathfinding.solvers;

import java.awt.image.BufferedImage;
import pathfinding.entities.Node;
import pathfinding.interfaces.Generated;

/** Class for Jump Point Search. */
public class Jps extends Astar {

  @Generated
  public Jps(int startX, int startY, int endX, int endY, BufferedImage map) {
    super(startX, startY, endX, endY, map);
  }

  /**
   * Transports the current node until a valid/invalid jump location is found.
   *
   * @param current Current node being 'jumped'.
   * @param posX Current node x position.
   * @param posY Current node y position.
   * @param x Horizontal direction: -1,0,1.
   * @param y Vertical direction: -1,0,1.
   * @return Returns a node if suitable place is found, null otherwise.
   */
  private Node jumpSuccessor(Node current, int posX, int posY, int x, int y) {
    while (true) {
      if (!isEligibleMove(posX, posY)) {
        return null;
      }

      if (posX == endX && posY == endY) {
        return new Node(current, posX, posY);
      }
      if (x == 0 || y == 0) {
        if (x != 0) {
          if ((isEligibleMove(posX + x, posY + 1) && !isEligibleMove(posX, posY + 1))
              || (isEligibleMove(posX + x, posY - 1) && !isEligibleMove(posX, posY - 1))) {
            return new Node(current, posX, posY);
          }
        } else {
          if ((isEligibleMove(posX + 1, posY + y) && !isEligibleMove(posX + 1, posY))
              || (isEligibleMove(posX - 1, posY + y) && !isEligibleMove(posX - 1, posY))) {
            return new Node(current, posX, posY);
          }
        }
      } else if (x != 0 && y != 0) {
        if (!isEligibleMove(posX + x, posY + y)
            && isEligibleMove(posX + x, posY)
            && isEligibleMove(posX, posY + y)) {
          return new Node(current, posX, posY);
        } else if (jumpSuccessor(current, posX + x, posY, x, 0) != null
            || jumpSuccessor(current, posX, posY + y, 0, y) != null) {
          return new Node(current, posX, posY);
        }
      }
      posX += x;
      posY += y;
    }
  }

  protected Node[] getNeighbours(Node current) {
    if (current.getParent() == null) {
      return super.getNeighbours(current);
    } else {
      return pruneNeighbours(current);
    }
  }

  /**
   * Prunes neighbours based on direction. No need to expand all 8 nodes.
   *
   * @param current Node to be expanded.
   * @return Node array with pruned neighbours.
   */
  private Node[] pruneNeighbours(Node current) {
    Node[] neighbours = new Node[5];
    int parentX = current.getParent().getPosX();
    int parentY = current.getParent().getPosY();
    int currentX = current.getPosX();
    int currentY = current.getPosY();
    int dx = normalize(parentX, currentX);
    int dy = normalize(parentY, currentY);

    if (dx != 0 && dy != 0) {
      if (isEligibleMove(currentX, currentY + dy)) {
        neighbours[0] = new Node(current, currentX, currentY + dy);
      }
      if (isEligibleMove(currentX + dx, currentY)) {
        neighbours[1] = new Node(current, currentX + dx, currentY);
      }
      if (isEligibleMove(currentX + dx, currentY + dy)) {
        neighbours[2] = new Node(current, currentX + dx, currentY + dy);
      }
      if (!isEligibleMove(currentX - dx, currentY)) {
        neighbours[3] = new Node(current, currentX - dx, currentY + dy);
      }
      if (!isEligibleMove(currentX, currentY - dy)) {
        neighbours[4] = new Node(current, currentX + dx, currentY - dy);
      }
    } else {
      if (dx == 0) {
        if (isEligibleMove(currentX, currentY + dy)) {
          neighbours[0] = new Node(current, currentX, currentY + dy);
        }
        if (!isEligibleMove(currentX + 1, currentY)) {
          neighbours[1] = new Node(current, currentX + 1, currentY + dy);
        }
        if (!isEligibleMove(currentX - 1, currentY)) {
          neighbours[2] = new Node(current, currentX - 1, currentY + dy);
        }
      } else {
        if (isEligibleMove(currentX + dx, currentY)) {
          neighbours[0] = new Node(current, currentX + dx, currentY);
        }
        if (!isEligibleMove(currentX, currentY + 1)) {
          neighbours[1] = new Node(current, currentX + dx, currentY + 1);
        }
        if (!isEligibleMove(currentX, currentY - 1)) {
          neighbours[2] = new Node(current, currentX + dx, currentY - 1);
        }
      }
    }
    return neighbours;
  }

  /**
   * Evaluates a node.
   *
   * <p>Calculates f(x) = g(x) + h(x), where g(x) is the current movement cost up to this point and
   * h(x) is the approximation (heuristic) of the cost to reach the end from this point.
   *
   * @param current Current node
   * @param neighbour Neighbour node
   */
  protected void addNeighbour(Node current, Node neighbour) {
    int nextX = neighbour.getPosX();
    int nextY = neighbour.getPosY();

    // Use the direction of the parent to guide the expanded node.
    int dx = normalize(current.getPosX(), neighbour.getPosX());
    int dy = normalize(current.getPosY(), neighbour.getPosY());

    Node jump = jumpSuccessor(current, nextX, nextY, dx, dy);
    if (jump == null) {
      return;
    }

    super.addNeighbour(current, jump);
  }

  /**
   * Get normalized direction of two points.
   *
   * @param from Coordinate from
   * @param to Coordinate to
   * @return Return an integer, e.g. -1, 0, 1, depending on direction.
   */
  private int normalize(int from, int to) {
    int sub = (to - from);
    int abs = sub > 0 ? sub : -sub;
    int max = abs > 1 ? abs : 1;
    return sub / max;
  }
}
