package pathfinding.solvers;

import static pathfinding.tools.ImgTools.setPixelColor;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.Stack;
import pathfinding.collections.MinHeap;
import pathfinding.entities.Node;

/** Class for Iterative Deepening Astar. */
public class Ida extends Astar {

  private Instant start;

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
    Node startNode = new Node(null, startX, startY);
    Node endNode = new Node(null, endX, endY);
    Stack<Node> path = new Stack<>();
    open.add(startNode);
    start = Instant.now();
    while (true) {
      double temp = search(path, endNode, 0, threshold);
      if (temp == 0) {
        return "Timeout.";
      }
      if (temp < 0) {
        hasSolution = true;
        path.add(startNode);
        break;
      }
      if (temp == 999999999) {
        break;
      }

      threshold = temp;
    }
    Instant end = Instant.now();
    if (hasSolution) {
      while (!path.isEmpty()) {
        Node node = path.pop();
        setPixelColor(node.getPosX(), node.getPosY(), 255, 0, 0, map);
      }
    }
    String solution =
        (hasSolution)
            ? "Path found in " + Duration.between(start, end).toMillis() + "ms. "
            : "No solution.";

    return solution;
  }

  private double search(Stack<Node> path, Node endNode, double g, double threshold) {
    Node node = null;
    if (!open.isEmpty()) {
      node = open.poll();
    } else {
      return 0;
    }
    double f = g + distance(node.getPosX(), node.getPosY(), endNode.getPosX(), endNode.getPosY());
    Instant runtime = Instant.now();
    if (Duration.between(start, runtime).toMillis() > 1000) {
      return 0;
    }
    if (f > threshold) {
      return f;
    }
    if (node.getPosX() == endNode.getPosX() && node.getPosY() == endNode.getPosY()) {
      return -f;
    }
    double min = 999999999;
    MinHeap neighbours = expand(node, g);
    while (!neighbours.isEmpty()) {
      Node next = neighbours.poll();
      int nextX = next.getPosX();
      int nextY = next.getPosY();
      double newCost = g + distance(node.getPosX(), node.getPosY(), nextX, nextY);
      open.add(next);
      double temp = search(path, endNode, newCost, threshold);
      if (temp == 0) {
        return 0;
      }
      if (temp < 0) {
        path.add(node);
        return -f;
      }
      if (temp < min) {
        min = temp;
      }
    }
    return min;
  }

  private MinHeap expand(Node node, double movementCost) {
    MinHeap minHeap = new MinHeap();
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if (x == 0 && y == 0) {
          continue;
        }
        int newX = node.getPosX() + x;
        int newY = node.getPosY() + y;
        double cost = (x != 0 && y != 0) ? sqrtTwo : 1;
        if (isEligibleMove(newX, newY)) {
          Node next = new Node(node, newX, newY);
          next.setTotalCost(movementCost + cost);
          minHeap.add(next);
        }
      }
    }
    return minHeap;
  }
}
