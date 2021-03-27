package pathfinding.solvers;

import static pathfinding.tools.ImgTools.getPixelColor;
import static pathfinding.tools.ImgTools.setPixelColor;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import pathfinding.entities.Node;

public class Astar {
  public PriorityQueue<Node> open;
  public Set<Node> closed;
  public BufferedImage map;

  public int endX;
  public int endY;
  public int startX;
  public int startY;

  public boolean hasSolution = false;

  public Astar(int startX, int startY, int endX, int endY, BufferedImage map) {
    this.map = map;
    this.open = new PriorityQueue<>();
    this.closed = new HashSet<>();

    this.endX = endX;
    this.endY = endY;
    this.startX = startX;
    this.startY = startY;
  }

  public BufferedImage getMap() {
    return this.map;
  }

  public String findPath() {
    Node current = new Node(null, startX, startY, 0, distance(startX, startY));
    open.add(current);
    Instant start = Instant.now();
    Instant running = Instant.now();
    while (current.posX != endX || current.posY != endY || this.open.isEmpty()) {
      current = this.open.poll();
      addNeighbours(current);
      if (Duration.between(start, running).toMillis() > 1000) {
        break;
      }
      closed.add(current);
    }
    hasSolution = (current.posX == endX && current.posY == endY);
    Instant end = Instant.now();
    String solution =
        (hasSolution)
            ? "Path found in " + Duration.between(start, end).toMillis() + "ms. "
            : "No solution.";

    if (hasSolution) {
      drawPath(current);
    }
    return solution;
  }

  public void drawPath(Node current) {
    setPixelColor(current.posX, current.posY, 255, 0, 0, this.map);
    while (current.posX != startX || current.posY != startY) {
      current = current.parent;
      setPixelColor(current.posX, current.posY, 255, 0, 0, this.map);
    }
  }

  public boolean isEligibleMove(int x, int y) {
    return getPixelColor(x, y, map).equals("(229,229,229)")
        || getPixelColor(x, y, map).equals("(255,155,155)")
        || getPixelColor(x, y, map).equals("(255,0,0)");
  }

  public float distance(int currentX, int currentY) {
    int dx = Math.abs(currentX - endX);
    int dy = Math.abs(currentY - endY);
    int c = 1;
    return (float) (c * (dx + dy) + 0.42 * c * Math.min(dx, dy));
  }

  public void addNeighbours(Node current) {
    setPixelColor(current.posX, current.posY, 255, 155, 155, this.map);
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        int newX = current.posX + x;
        int newY = current.posY + y;

        if (!isEligibleMove(newX, newY)) {
          continue;
        }

        float score = (x != 0 && y != 0) ? 1.42f : 1f;
        Node neighbour =
            new Node(current, newX, newY, current.scoreG + score, distance(newX, newY));

        if (!closed.contains(neighbour)) {
          if (!open.contains(neighbour)) {
            if (neighbour.scoreG > current.scoreG) {
              continue;
            }
            open.add(neighbour);
            setPixelColor(newX, newY, 255, 0, 0, this.map);
            open.remove(neighbour);
            open.add(neighbour);
            System.out.println("Found shorter path, updating");
          }
        }
      }
    }
  }
}
