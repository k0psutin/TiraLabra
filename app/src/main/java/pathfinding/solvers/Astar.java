package pathfinding.solvers;

import static pathfinding.tools.ImgTools.getPixelColor;
import static pathfinding.tools.ImgTools.setPixelColor;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.PriorityQueue;
import pathfinding.entities.Node;

public class Astar {
  private PriorityQueue<Node> closed;
  private PriorityQueue<Node> open;
  private BufferedImage map;

  private int width;
  private int height;

  private int endX;
  private int endY;
  public boolean hasSolution = false;

  public Astar(BufferedImage map) {
    this.width = map.getWidth();
    this.height = map.getHeight();
    this.map = map;

    this.closed = new PriorityQueue<>();
    this.open = new PriorityQueue<>();
  }

  public BufferedImage getMap() {
    return this.map;
  }

  public long findPath(int startX, int startY, int endX, int endY) {
    this.endX = endX;
    this.endY = endY;

    Instant start = Instant.now();
    Node current = new Node(null, startX, startY, 0, 0);
    closed.add(current);
    addNeighbours(current);
    while (current.posX != endX || current.posY != endY) {
      if (this.open.isEmpty()) {
        break;
      }
      current = this.open.poll();
      this.closed.add(current);
      addNeighbours(current);
    }
    hasSolution = (current.posX == endX && current.posY == endY);
    Instant end = Instant.now();

    if (hasSolution) {
      setPixelColor(current.posX, current.posY, 0, 0, 255, this.map);
      while (current.posX != startX || current.posY != startY) {
        current = current.parent;
        setPixelColor(current.posX, current.posY, 0, 0, 255, this.map);
      }
    }
    return Duration.between(start, end).toMillis();
  }

  public boolean isBorder(int x, int y) {
    return (x < 0 || x > this.width - 1 || y < 0 || y > this.height - 1);
  }

  public boolean isEligibleMove(int x, int y) {
    boolean goodMove = getPixelColor(x, y, map).equals("(229,229,229)");
    setPixelColor(x, y, 0, 255, 255, this.map);
    return goodMove;
  }

  public int distance(int currentX, int currentY) {
    return Math.abs(currentX - endX) + Math.abs(currentY - endY);
  }

  public void addNeighbours(Node current) {
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        int newX = current.posX + x;
        int newY = current.posY + y;

        if (isEligibleMove(newX, newY)) {
          Node node = new Node(current, newX, newY, current.scoreG + 1, distance(newX, newY));
          if (!this.open.contains(node) && !this.closed.contains(node)) {
            setPixelColor(node.posX, node.posY, 255, 0, 0, this.map);
            this.open.add(node);
          }
        }
      }
    }
  }
}
