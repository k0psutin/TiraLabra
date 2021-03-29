package pathfinding.solvers;

import static pathfinding.tools.ImgTools.drawLine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import pathfinding.entities.Node;

public class Jps extends Astar {

  public Jps(int startX, int startY, int endX, int endY, BufferedImage map) {
    super(startX, startY, endX, endY, map);
  }

  @Override
  public void drawPath(Node current) {
    Node previous = null;
    while (current.posX != startX || current.posY != startY) {
      previous = current;
      current = current.parent;
      drawLine(previous.posX, previous.posY, current.posX, current.posY, this.map);
    }
  }

  private Node jumpSuccessor(Node current, int posX, int posY, int x, int y) {
    float newG = gscores.get(current);
    while (true) {
      posX += x;
      posY += y;
      newG += (x != 0 && y != 0) ? 1.42f : 1f;

      if (!isEligibleMove(posX, posY)) {
        return null;
      }

      if (posX == endX && posY == endY) {
        gscores.put(new Node(current, posX, posY), newG);
        return new Node(current, posX, posY);
      }
      if (x == 0 || y == 0) {
        if (x != 0) {
          if ((isEligibleMove(posX + x, posY + 1) && !isEligibleMove(posX, posY + 1))
              || (isEligibleMove(posX + x, posY - 1) && !isEligibleMove(posX, posY - 1))) {
            gscores.put(new Node(current, posX, posY), newG);
            return new Node(current, posX, posY);
          }
        } else {
          if ((isEligibleMove(posX + 1, posY + y) && !isEligibleMove(posX + 1, posY))
              || (isEligibleMove(posX - 1, posY + y) && !isEligibleMove(posX - 1, posY))) {
            gscores.put(new Node(current, posX, posY), newG);
            return new Node(current, posX, posY);
          }
        }
      } else if (x != 0 && y != 0) {
        if (!isEligibleMove(posX + x, posY + y)
            && isEligibleMove(posX + x, posY)
            && isEligibleMove(posX, posY + y)) {
          gscores.put(new Node(current, posX, posY), newG);
          return new Node(current, posX, posY);
        } else if (jumpSuccessor(current, posX + x, posY, x, 0) != null
            || jumpSuccessor(current, posX, posY + y, 0, y) != null) {
          gscores.put(new Node(current, posX, posY), newG);
          return new Node(current, posX, posY);
        }
      }
    }
  }

  public List<Node> pruneNeighbours(Node current) {
    List<Node> neighbours = new ArrayList<>();
    int px = current.parent.posX;
    int py = current.parent.posY;
    int nx = current.posX;
    int ny = current.posY;
    int dx = normalize(nx, px);
    int dy = normalize(ny, py);
    float score = (dx != 0 && dy != 0) ? 1.42f : 1f;
    if (dx != 0 && dy != 0) {

      if (isEligibleMove(nx, ny + dy)) {
        neighbours.add(new Node(current, nx, ny + dy));
      }
      if (isEligibleMove(nx + dx, ny)) {
        neighbours.add(new Node(current, nx + dx, ny));
      }
      if (isEligibleMove(nx + dx, ny + dy)) {
        neighbours.add(new Node(current, nx + dx, ny + dy));
      }
      if (!isEligibleMove(nx - dx, ny)) {
        neighbours.add(new Node(current, nx - dx, ny + dy));
      }
      if (!isEligibleMove(nx, ny - dy)) {
        neighbours.add(new Node(current, nx + dx, ny - dy));
      }
    } else {
      if (dx == 0) {
        if (isEligibleMove(nx, ny + dy)) {
          neighbours.add(new Node(current, nx, ny + dy));
        }
        if (!isEligibleMove(nx + 1, ny)) {
          neighbours.add(new Node(current, nx + 1, ny + dy));
        }
        if (!isEligibleMove(nx - 1, ny)) {
          neighbours.add(new Node(current, nx - 1, ny + dy));
        }
      } else {
        if (isEligibleMove(nx + dx, ny)) {
          neighbours.add(new Node(current, nx + dx, ny));
        }
        if (!isEligibleMove(nx, ny + 1)) {
          neighbours.add(new Node(current, nx + dx, ny + 1));
        }
        if (!isEligibleMove(nx, ny - 1)) {
          neighbours.add(new Node(current, nx + dx, ny - 1));
        }
      }
    }
    return neighbours;
  }

  @Override
  public void addNeighbours(Node current) {
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        int newX = current.posX + x;
        int newY = current.posY + y;

        if (x == 0 && y == 0) {
          continue;
        }

        if (!isEligibleMove(newX, newY)) {
          continue;
        }
        if (current.parent == null) {
          float movementCost = (x != 0 && y != 0) ? 1.42f : 1f;
          Node neighbour = new Node(current, newX, newY);

          if (closed.contains(neighbour)) {
            continue;
          }

          float currentCost = gscores.getOrDefault(neighbour, 0f);
          float newCost = currentCost + movementCost;
          neighbour.scoreF = newCost + distance(neighbour.posX, neighbour.posY);
          open.add(neighbour);
          gscores.put(neighbour, newCost);
        } else {
          int dx = normalize(newX, current.posX);
          int dy = normalize(newY, current.posY);
          Node jump = jumpSuccessor(current, newX, newY, dx, dy);
          if (jump == null) {
            continue;
          }

          if (closed.contains(jump)) {
            continue;
          }

          float movementCost = (dx != 0 && dy != 0) ? 1.42f : 1f;
          float currentCost = gscores.getOrDefault(jump, 0f);
          float newCost = currentCost + movementCost;

          if (!open.contains(jump) || newCost < currentCost) {
            gscores.put(jump, newCost);
            jump.scoreF = newCost + distance(jump.posX, jump.posY);
            open.add(jump);
          }
        }
      }
    }
  }

  public int normalize(int to, int from) {
    return (to - from) / Math.max(Math.abs(to - from), 1);
  }
}
