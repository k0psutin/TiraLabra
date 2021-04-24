package pathfinding.solvers;

import static pathfinding.tools.ImgTools.drawLine;
import static pathfinding.tools.ImgTools.getPixelColor;

import java.awt.image.BufferedImage;
import java.time.Instant;
import pathfinding.collections.MinHeap;
import pathfinding.entities.Node;
import pathfinding.interfaces.Generated;

/** Base class for solvers. */
public abstract class Pathfinding {

  protected MinHeap open;
  protected double[][] travelCosts;

  protected BufferedImage map;

  protected int endX;
  protected int endY;
  protected int startX;
  protected int startY;

  protected int visitedNodes = 0;
  protected double pathCost = 0;
  protected int endTime = 0;

  protected float timeout = 10000;

  protected Instant start;

  protected final double sqrtTwo = Math.sqrt(2);

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  public Pathfinding(int startX, int startY, int endX, int endY, BufferedImage map) {
    this.map = map;
    this.open = new MinHeap();
    this.travelCosts = new double[map.getWidth() + 1][map.getHeight() + 1];

    this.endX = endX;
    this.endY = endY;
    this.startX = startX;
    this.startY = startY;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  protected void drawPath(Node current) {
    Node previous = null;
    pathCost = current.getTotalCost();
    while (current.getPosX() != startX || current.getPosY() != startY) {
      previous = current;
      current = current.getParent();
      drawLine(previous.getPosX(), previous.getPosY(), current.getPosX(), current.getPosY(), map);
    }
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
    int subX = (currentX - endX);
    int subY = (currentY - endY);
    int dx = subX > 0 ? subX : -subX;
    int dy = subY > 0 ? subY : -subY;
    int max = dx > dy ? dx : dy;
    int min = dx < dy ? dx : dy;
    return max + (sqrtTwo - 1) * min;
  }

  /**
   * If path is found, returns solve time in milliseconds.
   *
   * <p>Else return "No solution" if path is not found or "Timeout" if algorithm takes too much
   * time.
   *
   * @return String
   */
  public abstract String findPath();

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  public BufferedImage getMap() {
    return this.map;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  protected boolean isEligibleMove(int x, int y) {
    if (x < 0 || y < 0 || x > map.getWidth() - 1 || y > map.getHeight() - 1) {
      return false;
    }
    return getPixelColor(x, y, map).equals("(229,229,229)");
  }

  public int getVisitedNodes() {
    return visitedNodes;
  }

  public double getPathCost() {
    return pathCost;
  }

  public int getEndTime() {
    return endTime;
  }
}
