package pathfinding.solvers;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import pathfinding.collections.MinHeap;
import pathfinding.entities.Node;

/** Class for Iterative Deepening A*. */
public class Ida extends Pathfinding {

  private Instant start;
  private Node endNode;

  private int[][] visited;

  public Ida(int startX, int startY, int endX, int endY, BufferedImage map) {
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

    double threshold = distance(startX, startY, endX, endY);
    start = Instant.now();
    while (true) {
      visited = new int[map.getWidth() + 1][map.getHeight() + 1];
      visitedNodes = 0;
      open.add(new Node(null, startX, startY));
      threshold = search(0, threshold);
      if (threshold == 0) {
        return "Timeout.";
      }
      if (threshold < 0) {
        Instant end = Instant.now();
        drawPath(endNode);
        endTime = (int) Duration.between(start, end).toMillis();
        return "Path found in " + endTime + "ms. ";
      }
      if (threshold == Double.POSITIVE_INFINITY) {
        return "No solution.";
      }
    }
  }

  /**
   * Returns node successor array sorted by f(x) = g(x) + h(x), where g(x) = movement cost so far
   * and h(x) is the cost approximation (heuristic) from current position to the end.
   *
   * @param current Node to be expanded
   * @param movementCost Movement cost so far, g(x)
   * @return Minimum Heap with successors
   */
  private MinHeap successors(Node current, double movementCost) {
    MinHeap minHeap = new MinHeap();
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if (x == 0 && y == 0) {
          continue;
        }
        Node node = new Node(current, current.getPosX() + x, current.getPosY() + y);
        node.setTotalCost(movementCost + distance(node.getPosX(), node.getPosY(), endX, endY));
        minHeap.add(node);
      }
    }
    return minHeap;
  }

  /**
   * Recursive deepening function with a upper bound threshold.
   *
   * @param currentCost Movement cost so far
   * @param threshold Upper bound for total movement cost f(x) = g(x) + h(x)
   * @return 0 if path finding takes too much time, f(x) + 1 if current upperbound is breached or
   *     -f(x) if goal is found.
   */
  private double search(double movementCost, double threshold) {
    Node node = open.poll();
    double f = movementCost + distance(node.getPosX(), node.getPosY(), endX, endY);
    Instant runtime = Instant.now();
    if (Duration.between(start, runtime).toMillis() > timeout) {
      return 0;
    }
    if (f > threshold) {
      return f;
    }
    if (node.getPosX() == endX && node.getPosY() == endY) {
      this.endNode = node;
      return -f;
    }
    double minimum = Double.POSITIVE_INFINITY;
    MinHeap neighbours = successors(node, movementCost);
    while (!neighbours.isEmpty()) {
      Node next = neighbours.poll();
      int nextX = next.getPosX();
      int nextY = next.getPosY();

      if (visited[nextX][nextY] == 1) {
        continue;
      }
      if (!isEligibleMove(nextX, nextY)) {
        continue;
      }

      visited[nextX][nextY] = 1;

      visitedNodes++;

      double newCost = movementCost + distance(node.getPosX(), node.getPosY(), nextX, nextY);
      next.setTotalCost(newCost + distance(nextX, nextY, endX, endY));
      open.add(next);
      double solution = search(newCost, threshold);

      if (solution == 0) {
        return 0;
      }
      if (solution < 0) {
        next.setParent(node);
        return -f;
      }
      minimum = (solution < minimum) ? solution : minimum;
    }
    return minimum;
  }
}
