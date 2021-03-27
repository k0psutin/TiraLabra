package pathfinding.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.PriorityQueue;
import org.junit.Before;
import org.junit.Test;
import pathfinding.entities.Node;

public class NodeTest {

  private Node first;
  private Node second;
  private PriorityQueue<Node> queue;

  @Before
  public void setUp() throws Exception {
    first = new Node(null, 0, 0, 0, 0);
    second = new Node(first, 0, 0, 10, 10);
    queue = new PriorityQueue<Node>();
  }

  public float distance(int cx, int cy, int ex, int ey) {
    int dx = Math.abs(cx - ex);
    int dy = Math.abs(cy - ey);
    int c = 1;
    return (float) (c * (dx + dy) + 0.42 * c * Math.min(dx, dy));
  }

  @Test
  public void testReturnTrueIfSameCoordinates() {
    assertTrue((first.equals(second)));
  }

  @Test
  public void testReturnFalseIfNotSameCoordinates() {
    second = new Node(first, 0, 1, 10, 10);
    assertFalse((first.equals(second)));
  }

  @Test
  public void testReturnTrueIfInQueue() {
    queue.add(first);
    assertTrue(queue.contains(second));
  }

  @Test
  public void testReturnFalseIfNotInQueue() {
    queue.add(first);
    second = new Node(first, 0, 1, 10, 10);
    queue.add(second);
    assertFalse(queue.contains(new Node(second, 0, 2, 12, 12)));
  }

  @Test
  public void testQueueReturnsSmallestFscore() {
    float score = 0;
    for (int x = 0; x <= 10; x++) {
      score += 1;
      queue.add(new Node(first, x, 0, score, distance(x, 0, 10, 10)));
    }
    Node endNode = new Node(first, 10, 0, 11, distance(10, 0, 10, 10));
    float expected = endNode.scoreF;
    float fscore = queue.peek().scoreF;
    String message = "Expected fScore to be " + expected + ", instead it was " + fscore;
    assertEquals(message, fscore, expected, 0.001);
  }
}
