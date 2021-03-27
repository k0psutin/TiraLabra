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

/** Class for A* algorithm. */
public class Astar {

  public PriorityQueue<Node> open;

  public Set<Node> closed;
  public BufferedImage map;

  public int endX;
  public int endY;
  public int startX;
  public int startY;

  public boolean hasSolution = false;

  public long timeout = 5000;

  /**
   * Constructor for Astar pathfinding.
   *
   * @param startX Start position X.
   * @param startY Start position Y.
   * @param endX End coordinate X.
   * @param endY End coordinate Y.
   * @param map Image where algorithm will find the path.
   */
  public Astar(int startX, int startY, int endX, int endY, BufferedImage map) {
    this.map = map;
    this.open = new PriorityQueue<Node>();
    this.closed = new HashSet<>();

    this.endX = endX;
    this.endY = endY;
    this.startX = startX;
    this.startY = startY;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public BufferedImage getMap() {
    return this.map;
  }

  /**
   * If path is found, returns solve time in milliseconds.
   *
   * <p>Else return "No solution" if path is not found or "Timeout" if algorithm takes too much
   * time.
   *
   * @return String
   */
  public String findPath() {
    if (!isEligibleMove(startX, startY)) {
      return "Start position not reachable.";
    }
    if (!isEligibleMove(endX, endY)) {
      return "End position not reachable.";
    }
    Node current = new Node(null, startX, startY, 0, 0);
    open.add(current);
    Instant start = Instant.now();
    while (!open.isEmpty()) {
      current = open.poll();
      Instant running = Instant.now();
      setPixelColor(current.posX, current.posY, 255, 155, 155, this.map);
      if (current.posX == endX && current.posY == endY) {
        break;
      }
      if (Duration.between(start, running).toMillis() > timeout) {
        return "Timeout.";
      }
      addNeighbours(current);
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

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public void drawPath(Node current) {
    setPixelColor(current.posX, current.posY, 255, 0, 0, this.map);
    while (current.posX != startX || current.posY != startY) {
      current = current.parent;
      setPixelColor(current.posX, current.posY, 255, 0, 0, this.map);
    }
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public boolean isEligibleMove(int x, int y) {
    return getPixelColor(x, y, map).equals("(229,229,229)")
        || getPixelColor(x, y, map).equals("(255,155,155)")
        || getPixelColor(x, y, map).equals("(255,0,0)");
  }

  /**
   * Returns the octile distance between two points. Approximation of total cost between two points.
   *
   * @param currentX Current x coordinate.
   * @param currentY Current y coordinate.
   * @return float
   */
  public float distance(int currentX, int currentY) {
    int dx = Math.abs(currentX - endX);
    int dy = Math.abs(currentY - endY);
    return (Math.max(dx, dy) + 0.42f * Math.min(dx, dy));
  }

  /**
   * Expands current node and add its neighbours to openlist.
   *
   * @param current Node to be expanded.
   */
  public void addNeighbours(Node current) {
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        int newX = current.posX + x;
        int newY = current.posY + y;

        if (!isEligibleMove(newX, newY)) {
          continue;
        }

        setPixelColor(newX, newY, 255, 155, 155, this.map);

        float score = (x != 0 && y != 0) ? 1.42f : 1f;
        Node neighbour =
            new Node(current, newX, newY, current.scoreG + score, distance(newX, newY));

        if (closed.contains(neighbour)) {
          continue;
        }

        if (open.contains(neighbour)) {
          continue;
        }

        open.add(neighbour);
      }
    }
  }
}
