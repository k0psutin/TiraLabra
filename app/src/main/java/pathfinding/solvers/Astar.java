package pathfinding.solvers;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import pathfinding.entities.Node;

/** Class for A* algorithm. */
public class Astar extends Pathfinding {

  /**
   * Constructor for Astar pathfinding.
   *
   * @param startX Start position X.
   * @param startY Start position Y.
   * @param endX End coordinate X.
   * @param endY End coordinate Y.
   * @param map The map where the algorithm will find the path.
   */
  public Astar(int startX, int startY, int endX, int endY, BufferedImage map) {
    super(startX, startY, endX, endY, map);
  }

  @Override
  public String findPath() {
    if (!isEligibleMove(startX, startY)) {
      return "Start position not reachable.";
    }
    if (!isEligibleMove(endX, endY)) {
      return "End position not reachable.";
    }

    Node current = new Node(null, startX, startY);
    open.add(new Node(null, startX, startY));

    travelCosts[startX][startY] = 0;

    start = Instant.now();
    while (!open.isEmpty()) {
      current = open.poll();
      // setPixelColor(current.getPosX(), current.getPosY(), 150, 208, 255, this.map);
      if (current.getPosX() == endX && current.getPosY() == endY) {
        Instant end = Instant.now();
        endTime = (int) Duration.between(start, end).toMillis();
        drawPath(current);
        return "Path found in " + endTime + "ms. ";
      }
      Instant runtime = Instant.now();
      if (Duration.between(start, runtime).toMillis() > timeout) {
        return "Timeout.";
      }
      for (Node neighbour : getNeighbours(current)) {
        if (neighbour == null) {
          continue;
        }
        addNeighbour(current, neighbour);
      }
    }
    return "No solution.";
  }

  /**
   * Expands node and return its neighbour nodes.
   *
   * @param current Node to be expanded
   * @return Node[] array of neighbours.
   */
  protected Node[] getNeighbours(Node current) {
    Node[] neighbours = new Node[8];

    neighbours[0] = new Node(current, current.getPosX() + 1, current.getPosY());
    neighbours[1] = new Node(current, current.getPosX() - 1, current.getPosY());
    neighbours[2] = new Node(current, current.getPosX(), current.getPosY() + 1);
    neighbours[3] = new Node(current, current.getPosX(), current.getPosY() - 1);
    neighbours[4] = new Node(current, current.getPosX() - 1, current.getPosY() - 1);
    neighbours[5] = new Node(current, current.getPosX() + 1, current.getPosY() - 1);
    neighbours[6] = new Node(current, current.getPosX() - 1, current.getPosY() + 1);
    neighbours[7] = new Node(current, current.getPosX() + 1, current.getPosY() + 1);

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

    if (!isEligibleMove(nextX, nextY) || visited[nextX][nextY] == 1) {
      return;
    }

    visitedNodes++;
    visited[nextX][nextY] = 1;

    double currentCost = travelCosts[current.getPosX()][current.getPosY()];
    double newCost = currentCost + distance(current.getPosX(), current.getPosY(), nextX, nextY);

    if (travelCosts[nextX][nextY] == 0.0 || currentCost > newCost) {
      // setPixelColor(nextX, nextY, 124, 255, 110, this.map);
      travelCosts[nextX][nextY] = newCost;
      neighbour.setTotalCost(newCost + distance(nextX, nextY, endX, endY));
      neighbour.setParent(current);
      open.add(neighbour);
    }
  }
}
