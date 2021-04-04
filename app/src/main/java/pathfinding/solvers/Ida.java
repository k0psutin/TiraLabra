package pathfinding.solvers;

import static pathfinding.tools.ImgTools.setPixelColor;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import pathfinding.collections.MinHeap;
import pathfinding.entities.Node;

/** Class for Iterative Deepening A*. */
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
    List<Node> path = new ArrayList<>();
    start = Instant.now();
    while (true) {
      open = new MinHeap();
      open.add(startNode);
      closed = new boolean[map.getWidth() + 1][map.getHeight() + 1];
      double temp = search(path, endNode, 0, threshold);
      if (temp == 0) {
        return "Timeout.";
      }
      if (temp < 0) {
        path.add(startNode);
        Instant end = Instant.now();
        for (Node node : path) {
          setPixelColor(node.getPosX(), node.getPosY(), 255, 0, 0, map);
        }
        return "Path found in " + Duration.between(start, end).toMillis() + "ms. ";
      }
      if (temp == 999999999) {
        return "No solution.";
      }

      threshold = temp;
    }
  }

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

  private double search(List<Node> path, Node endNode, double currentCost, double threshold) {
    if (open.isEmpty()) {
      return 0;
    }
    Node node = open.poll();
    double f =
        currentCost
            + distance(node.getPosX(), node.getPosY(), endNode.getPosX(), endNode.getPosY());
    Instant runtime = Instant.now();
    if (Duration.between(start, runtime).toMillis() > 5000) {
      return 0;
    }
    if (f > threshold) {
      return f + 1;
    }
    if (node.getPosX() == endNode.getPosX() && node.getPosY() == endNode.getPosY()) {
      return -f;
    }
    double min = 999999999;
    MinHeap neighbours = successors(node, currentCost);
    while (!neighbours.isEmpty()) {
      Node next = neighbours.poll();
      int nextX = next.getPosX();
      int nextY = next.getPosY();
      if (!isEligibleMove(nextX, nextY)) {
        continue;
      }
      if (closed[nextX][nextY]) {
        continue;
      }
      closed[nextX][nextY] = true;

      double newCost = currentCost + distance(node.getPosX(), node.getPosY(), nextX, nextY);

      next.setTotalCost(newCost + distance(nextX, nextY, endX, endY));
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
}
