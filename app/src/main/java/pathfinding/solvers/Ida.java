package pathfinding.solvers;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import pathfinding.collections.MinHeap;
import pathfinding.entities.Node;

/** Class for Iterative Deepening A*. */
public class Ida extends Pathfinding {

  private Node endNode;

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

    double bound = distance(startX, startY, endX, endY);
    int depth = 1;
    start = Instant.now();

    while (true) {
      bound = search(new Node(null, startX, startY), 0, bound, depth);
      if (bound == 0) {
        return "Timeout.";
      }
      if (bound < 0) {
        Instant end = Instant.now();
        endTime = (int) Duration.between(start, end).toMillis();
        drawPath(endNode);
        return "Path found in " + endTime + "ms. ";
      }
      if (bound == Double.POSITIVE_INFINITY) {
        return "No solution.";
      }
      depth++;
      visitedNodes = 0;
    }
  }

  /**
   * Returns node successor array sorted by f(x) = g(x) + h(x), where g(x) = movement cost so far
   * and h(x) is the cost approximation (heuristic) from current position to the end.
   *
   * @param current Node to be expanded
   * @param g Movement cost so far, g(x)
   * @param depth Current depth
   * @return Minimum Heap with successors
   */
  private MinHeap successors(Node current, double g, int depth) {
    MinHeap minHeap = new MinHeap();
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if ((x == 0 && y == 0)) {
          continue;
        }

        int currX = current.getPosX();
        int currY = current.getPosY();
        int nextX = currX + x;
        int nextY = currY + y;

        if (visited[nextX][nextY] == depth) {
          continue;
        }

        if (!isEligibleMove(nextX, nextY)) {
          continue;
        }

        Node node = new Node(current, nextX, nextY);
        node.setTotalCost(g + distance(node.getPosX(), node.getPosY(), endX, endY));
        minHeap.add(node);
      }
    }
    return minHeap;
  }

  /**
   * Recursive deepening function with a upper bound bound.
   *
   * @param node Current node
   * @param currentCost Movement cost so far
   * @param bound Upper bound for total movement cost f(x) = g(x) + h(x)
   * @param depth Current depth
   * @return 0 if path finding takes too much time, f(x) + 1 if current upperbound is breached or
   *     -f(x) if goal is found.
   */
  private double search(Node node, double g, double bound, int depth) {
    double h = distance(node.getPosX(), node.getPosY(), endX, endY);
    double f = g + h;
    Instant runtime = Instant.now();
    if (Duration.between(start, runtime).toMillis() >= timeout) {
      return 0;
    }

    if (f > bound) {
      return f;
    }

    if (h == 0) {
      this.endNode = node;
      return -f;
    }

    double newBound = Double.POSITIVE_INFINITY;
    MinHeap neighbours = successors(node, g, depth);
    while (!neighbours.isEmpty()) {
      Node next = neighbours.poll();
      int nextX = next.getPosX();
      int nextY = next.getPosY();

      double newCost = g + distance(node.getPosX(), node.getPosY(), nextX, nextY);

      visited[nextX][nextY] = depth;
      visitedNodes++;

      next.setTotalCost(newCost + distance(nextX, nextY, endX, endY));
      double nextBound = search(next, newCost, bound, depth);

      if (nextBound == 0) {
        return 0;
      }
      if (nextBound < 0) {
        next.setParent(node);
        return -f;
      }
      newBound = (nextBound < newBound) ? nextBound : newBound;
    }
    return newBound;
  }
}
