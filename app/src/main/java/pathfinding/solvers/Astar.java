package pathfinding.solvers;

import static pathfinding.tools.ImgTools.getPixelColor;
import static pathfinding.tools.ImgTools.setPixelColor;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import pathfinding.collections.MinHeap;
import pathfinding.entities.Node;
import pathfinding.interfaces.Generated;

/** Class for A* algorithm. */
public class Astar {

  public MinHeap open;
  public boolean[][] closed;
  public double[][] travelCosts;

  public BufferedImage map;

  public int endX;
  public int endY;
  public int startX;
  public int startY;

  public boolean hasSolution = false;

  public final double sqrtTwo = Math.sqrt(2);

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
    this.map = map;
    this.open = new MinHeap();
    this.closed = new boolean[map.getWidth() + 1][map.getHeight() + 1];
    this.travelCosts = new double[map.getWidth() + 1][map.getHeight() + 1];

    this.endX = endX;
    this.endY = endY;
    this.startX = startX;
    this.startY = startY;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
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

    Node current = new Node(null, startX, startY);

    open.add(current);
    travelCosts[startX][startY] = 0;
    Instant start = Instant.now();
    while (!open.isEmpty()) {
      current = open.poll();
      // setPixelColor(current.getPosX(), current.getPosY(), 150, 208, 255, this.map);
      if (current.getPosX() == endX && current.getPosY() == endY) {
        break;
      }
      for (Node neighbour : getNeighbours(current)) {
        if (neighbour == null) {
          continue;
        }
        addNeighbour(current, neighbour);
      }

      closed[current.getPosX()][current.getPosY()] = true;
    }
    hasSolution = (current.getPosX() == endX && current.getPosY() == endY);
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
  @Generated
  protected void drawPath(Node current) {
    setPixelColor(current.getPosX(), current.getPosY(), 255, 0, 0, this.map);
    while (current.getPosX() != startX || current.getPosY() != startY) {
      current = current.getParent();
      setPixelColor(current.getPosX(), current.getPosY(), 255, 0, 0, this.map);
    }
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  protected boolean isEligibleMove(int x, int y) {
    return getPixelColor(x, y, map).equals("(229,229,229)");
  }

  /**
   * Returns the octile distance between two points. Approximation of total cost between two points.
   *
   * @param currentX Current x coordinate.
   * @param currentY Current y coordinate.
   * @param endX End x coordinate.
   * @param endY End y coordinate.
   * @return distance between current and end.
   */
  public double distance(int currentX, int currentY, int endX, int endY) {
    int dx = Math.abs(currentX - endX);
    int dy = Math.abs(currentY - endY);
    return (Math.max(dx, dy) + (sqrtTwo - 1) * Math.min(dx, dy));
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

    if (!isEligibleMove(nextX, nextY)) {
      return;
    }

    if (closed[nextX][nextY]) {
      return;
    }

    double currentCost = travelCosts[current.getPosX()][current.getPosY()];

    double newCost = currentCost + distance(current.getPosX(), current.getPosY(), nextX, nextY);

    if (travelCosts[nextX][nextY] == 0) {
      // setPixelColor(nextX, nextY, 124, 255, 110, this.map);
      travelCosts[nextX][nextY] = newCost;
      neighbour.setTotalCost(newCost + distance(nextX, nextY, endX, endY));
      neighbour.setParent(current);
      open.add(neighbour);
      return;
    } else if (newCost < currentCost) {
      travelCosts[nextX][nextY] = newCost;
      neighbour.setTotalCost(newCost + distance(nextX, nextY, endX, endY));
      neighbour.setParent(current);
      open.add(neighbour);
      return;
    }
  }
}
