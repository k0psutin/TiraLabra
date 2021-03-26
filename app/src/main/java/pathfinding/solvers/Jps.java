package pathfinding.solvers;

import java.awt.image.BufferedImage;

public class Jps extends Astar {

  public Jps(int startX, int startY, int endX, int endY, BufferedImage map) {
    super(startX, startY, endX, endY, map);
  }
  /*
  final double sqrtTwo = 1.42;

  public Node jumpSuccessor(Node current, int posX, int posY, int x, int y) {
    double newG = current.scoreG;
    while (true) {

      posX += x;
      posY += y;
      newG += 1;

      if (!isEligibleMove(posX, posY)) {
        return null;
      }

      if (posX == endX && posY == endY) {
        isEndReached = true;
        return new Node(current, posX, posY, newG, distance(posX, posY));
      }
      if (x == 0 || y == 0) {
        if (x != 0) {
          if ((isEligibleMove(posX + x, posY + 1) && !isEligibleMove(posX, posY + 1))
              || (isEligibleMove(posX + x, posY - 1) && !isEligibleMove(posX, posY - 1))) {
            return new Node(current, posX, posY, newG, distance(posX, posY));
          }
        } else {
          if ((isEligibleMove(posX + 1, posY + y) && !isEligibleMove(posX + 1, posY))
              || (isEligibleMove(posX - 1, posY + y) && !isEligibleMove(posX - 1, posY))) {
            return new Node(current, posX, posY, newG, distance(posX, posY));
          }
        }
      } else if (x != 0 && y != 0) {
        if (!isEligibleMove(posX + x, posY + y)
            && isEligibleMove(posX + x, posY)
            && isEligibleMove(posX, posY + y)) {
          return new Node(current, posX, posY, newG, distance(posX, posY));
        } else if (jumpSuccessor(current, posX + x, posY, x, 0) != null
            || jumpSuccessor(current, posX, posY + y, 0, y) != null) {
          return new Node(current, posX, posY, newG, distance(posX, posY));
        }
      }
    }
  }

  public List<Node> joo(Node current) {
    List<Node> neighbours = new ArrayList<>();
    if (current.parent == null) {
      for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
          if (x == 0 && y == 0) {
            continue;
          }
          double score = 1;
          if (!canWalk(current.posX + x, current.posY + y)) {
            continue;
          }
          neighbours.add(
              new Node(
                  current,
                  current.posX + x,
                  current.posY + y,
                  current.scoreG + score,
                  distance(current.posX + x, current.posY + y)));
        }
      }
      return neighbours;
    } else {
      int px = current.parent.posX;
      int py = current.parent.posY;
      int nx = current.posX;
      int ny = current.posY;
      int dx = normalize(nx, px);
      int dy = normalize(ny, py);
      double score = 1;

      if (dx != 0 && dy != 0) {
        if (isEligibleMove(nx, ny + dy)) {
          neighbours.add(
              new Node(current, nx, ny + dy, current.scoreG + score, distance(nx, ny + dy)));
        }
        if (isEligibleMove(nx + dx, ny)) {
          neighbours.add(
              new Node(current, nx + dx, ny, current.scoreG + score, distance(nx + dx, ny)));
        }
        if (isEligibleMove(nx + dx, ny + dy)) {
          neighbours.add(
              new Node(
                  current, nx + dx, ny + dy, current.scoreG + score, distance(nx + dx, ny + dx)));
        }
        if (!isEligibleMove(nx - dx, ny)) {
          neighbours.add(
              new Node(
                  current, nx - dx, ny + dy, current.scoreG + score, distance(nx - dx, ny + dx)));
        }
        if (!isEligibleMove(nx, ny - dy)) {
          neighbours.add(
              new Node(
                  current, nx + dx, ny - dy, current.scoreG + score, distance(nx + dx, ny - dy)));
        }
      } else {
        if (dx == 0) {
          if (isEligibleMove(nx, ny + dy)) {
            neighbours.add(
                new Node(current, nx, ny + dy, current.scoreG + score, distance(nx, ny + dy)));
          }
          if (!isEligibleMove(nx + 1, ny)) {
            neighbours.add(
                new Node(
                    current, nx + 1, ny + dy, current.scoreG + score, distance(nx + 1, ny + 1)));
          }
          if (!isEligibleMove(nx - 1, ny)) {
            neighbours.add(
                new Node(
                    current, nx - 1, ny + dy, current.scoreG + score, distance(nx - 1, ny + dy)));
          }
        } else {
          if (isEligibleMove(nx + dx, ny)) {
            neighbours.add(
                new Node(current, nx + dx, ny, current.scoreG + score, distance(nx + dx, ny)));
          }
          if (!isEligibleMove(nx, ny + 1)) {
            neighbours.add(
                new Node(
                    current, nx + dx, ny + 1, current.scoreG + score, distance(nx + dx, ny + 1)));
          }
          if (!isEligibleMove(nx, ny - 1)) {
            neighbours.add(
                new Node(
                    current, nx + dx, ny - 1, current.scoreG + score, distance(nx + dx, ny - 1)));
          }
        }
      }
      return neighbours;
    }
  }

  public void pruneOpen(Node node) {
    PriorityQueue<Node> newOpen = new PriorityQueue<>();
    while (!open.isEmpty()) {
      Node next = open.poll();
      if (node != next) {
        newOpen.add(next);
      }
    }
    open = newOpen;
  }

  public void pruneNeighbours(Node current) {
    List<Node> neighbours = getNeighbours(current);
    if (neighbours.size() == 0) {
      return;
    }
    for (Node node : neighbours) {
      int dx = normalize(node.posX, current.posX);
      int dy = normalize(node.posY, current.posY);
      Node jump = jumpSuccessor(current, node.posX, node.posY, dx, dy);
      if (jump != null) {
        visited.add(new Point(jump.posX, jump.posY));
        if (!open.contains(jump) || !closed.contains(jump)) {
          open.add(jump);
        }
      }
    }
  }

  public static int normalize(int a, int b) {
    return (a - b) / Math.max(Math.abs(a - b), 1);
  }
  */
}
