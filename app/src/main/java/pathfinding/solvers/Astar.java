package pathfinding.solvers;

import static pathfinding.tools.ImgTools.getPixelColor;
import static pathfinding.tools.ImgTools.setPixelColor;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import pathfinding.entities.Node;

public class Astar {
  private PriorityQueue<Node> open;
  private Set<Point> visited;
  private BufferedImage map;

  private int endX;
  private int endY;
  private int startX;
  private int startY;

  private boolean hasSolution = false;

  public Astar(int startX, int startY, int endX, int endY, BufferedImage map) {
    this.map = map;
    this.open = new PriorityQueue<>();
    this.visited = new HashSet<>();

    this.endX = endX;
    this.endY = endY;
    this.startX = startX;
    this.startY = startY;
  }

  public BufferedImage getMap() {
    return this.map;
  }

  public String findPath() {
    Node current = new Node(null, startX, startY, 0, 0);
    addNeighbours(current);
    Instant start = Instant.now();
    while (current.posX != endX || current.posY != endY || this.open.isEmpty()) {
      current = this.open.poll();
      addNeighbours(current);
    }
    hasSolution = (current.posX == endX && current.posY == endY);
    Instant end = Instant.now();
    String solution =
        (hasSolution)
            ? "Path found in " + Duration.between(start, end).toMillis() + "ms. "
            : "No solution.";

    if (hasSolution) {
      setPixelColor(current.posX, current.posY, 255, 0, 0, this.map);
      while (current.posX != startX || current.posY != startY) {
        current = current.parent;
        setPixelColor(current.posX, current.posY, 255, 0, 0, this.map);
      }
    }
    return solution;
  }

  private boolean isEligibleMove(int x, int y) {
    return getPixelColor(x, y, map).equals("(229,229,229)")
        || getPixelColor(x, y, map).equals("(255,155,155)")
        || getPixelColor(x, y, map).equals("(255,0,0)");
  }

  private float distance(int currentX, int currentY) {
    int dx = Math.abs(currentX - endX);
    int dy = Math.abs(currentY - endY);
    int c = 2;
    return (float) (c * (dx + dy) + 0.24 * c * Math.min(dx, dy));
  }

  private void addNeighbours(Node current) {
    setPixelColor(current.posX, current.posY, 255, 155, 155, this.map);
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        int newX = current.posX + x;
        int newY = current.posY + y;

        Point point = new Point(newX, newY);

        if (visited.contains(point)) {
          continue;
        }

        if (isEligibleMove(newX, newY)) {
          float score = (x != 0 && y != 0) ? 1.42f : 1.0f;
          Node node = new Node(current, newX, newY, current.scoreG + score, distance(newX, newY));
          open.add(node);
          visited.add(point);
          setPixelColor(newX, newY, 255, 0, 0, this.map);
        }
      }
    }
  }
}
