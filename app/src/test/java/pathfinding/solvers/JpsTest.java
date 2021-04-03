package pathfinding.solvers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pathfinding.tools.ImgTools.loadImage;

import org.junit.Before;
import org.junit.Test;
import pathfinding.entities.Node;

public class JpsTest {
  private Jps jps;

  @Before
  public void setUp() {
    jps = new Jps(30, 25, 119, 24, loadImage("arena.png", 150));
  }

  @Test
  public void testGetNeighboursGivesEightNodesIfNoParent() {
    Node[] nodes = jps.getNeighbours(new Node(null, 0, 0));
    int count = 0;
    for (Node node : nodes) {
      if (node != null) {
        count++;
      }
    }
    assertEquals(8, count);
  }

  @Test
  public void testGetNeighboursGivesCorrectNodesIfMovingHorizontallyNoForcedNeighbours() {
    Node node = new Node(null, 30, 25);
    int count = 0;
    for (int i = -1; i <= 1; i += 2) {
      for (Node next : jps.getNeighbours(new Node(node, 30 + i, 25))) {
        if (next != null) {
          if (next.getPosX() == node.getPosX() + 2 * i && next.getPosY() == node.getPosY()) {
            count++;
          }
        }
      }
    }

    assertEquals(2, count);
  }

  @Test
  public void testGetNeighboursGivesCorrectNodesIfMovingVerticallyNoForcedNeighbours() {
    Node node = new Node(null, 30, 25);
    int count = 0;
    for (int i = -1; i <= 1; i += 2) {
      for (Node next : jps.getNeighbours(new Node(node, 30, 25 + i))) {
        if (next != null) {
          if (next.getPosX() == node.getPosX() && next.getPosY() == node.getPosY() + 2 * i) {
            count++;
          }
        }
      }
    }

    assertEquals(2, count);
  }

  @Test
  public void testGetNeighbourGivesThreeNodesPerDirectionIfMovingDiagonallyNoForcedNeighbours() {
    Node node = new Node(null, 30, 25);
    int count = 0;
    for (int x = -1; x <= 1; x += 2) {
      for (int y = -1; y <= 1; y += 2) {
        for (Node next : jps.getNeighbours(new Node(node, 30 + x, 25 + y))) {
          if (next != null) {
            count++;
          }
        }
      }
    }
    assertEquals(12, count);
  }

  @Test
  public void testIllegalStartPositionReturnsStartPositionNotReachable() {
    jps = new Jps(0, 0, 10, 24, loadImage("arena.png", 150));
    String result = jps.findPath();
    assertEquals(
        "Expected ''Start position not reachable.'' instead it was " + result,
        "Start position not reachable.",
        result);
  }

  @Test
  public void testIllegalEndPositionReturnsEndPositionNotReachable() {
    jps = new Jps(22, 22, 150, 150, loadImage("arena.png", 150));
    String result = jps.findPath();
    assertEquals(
        "Expected ''End position not reachable'' instead it was " + result,
        "End position not reachable.",
        result);
  }

  @Test
  public void testPathFound() {
    String answer = jps.findPath();
    assertTrue("Expected ''Path found'' but " + answer, answer.contains("Path found"));
  }

  @Test
  public void testPathNotFound() {
    jps = new Jps(38, 111, 52, 121, loadImage("brc000d.png", 150));
    String answer = jps.findPath();
    assertTrue("Expected ''Timeout.'' but was " + answer, answer.contains("No solution."));
  }
}
