package pathfinding.solvers;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import pathfinding.collections.MinHeap;
import pathfinding.entities.Node;

/** Class for Iterative Deepening A*. */
public class Ida extends Astar {

  private Instant start;
  private Node endNode;

  public float timeout = 5000;

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
      open.add(new Node(null, startX, startY));
      double solution = search(0, threshold);
      if (solution == 0) {
        return "Timeout.";
      }
      if (solution < 0) {
        Instant end = Instant.now();
        drawPath(endNode);
        return "Path found in " + Duration.between(start, end).toMillis() + "ms. ";
      }
      if (solution == Double.POSITIVE_INFINITY) {
        return "No solution.";
      }
      closed = new boolean[map.getWidth() + 1][map.getHeight() + 1];
      threshold = solution;
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
  private double search(double currentCost, double threshold) {
    Node node = open.poll();
    double f = currentCost + distance(node.getPosX(), node.getPosY(), endX, endY);
    Instant runtime = Instant.now();
    if (Duration.between(start, runtime).toMillis() > timeout) {
      return 0;
    }
    if (f > threshold) {
      // By enlarging the threshold by 1, we can eliminate ties when facing an obstacle.
      return f + 1;
    }
    if (node.getPosX() == endX && node.getPosY() == endY) {
      this.endNode = node;
      return -f;
    }
    double minimum = Double.POSITIVE_INFINITY;
    MinHeap neighbours = successors(node, currentCost);
    while (!neighbours.isEmpty()) {
      Node next = neighbours.poll();
      int nextX = next.getPosX();
      int nextY = next.getPosY();

      if (!isEligibleMove(nextX, nextY) || closed[nextX][nextY]) {
        continue;
      }
      closed[nextX][nextY] = true;

      double newCost = currentCost + distance(node.getPosX(), node.getPosY(), nextX, nextY);
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
      minimum = Math.min(solution, minimum);
    }
    return minimum;
  }
}
