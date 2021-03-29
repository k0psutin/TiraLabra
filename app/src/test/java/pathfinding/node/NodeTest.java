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
    first = new Node(null, 0, 0);
    second = new Node(first, 0, 0);
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
    second = new Node(first, 0, 1);
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
    second = new Node(first, 0, 1);
    queue.add(second);
    assertFalse(queue.contains(new Node(second, 0, 2)));
  }

  @Test
  public void testQueueReturnsSmallestFscore() {
    Node node = new Node(null, 1, 0);
    node.scoreF = 1f + distance(1, 0, 3, 0);
    Node node2 = new Node(null, 1, -1);
    node2.scoreF = 1.42f + distance(1, -1, 3, 0);
    Node node3 = new Node(null, 1, 1);
    node3.scoreF = 1.42f + distance(1, 1, 3, 0);
    queue.add(node3);
    queue.add(node2);
    queue.add(node);
    float fscore = queue.poll().scoreF;
    String message = "Expected fScore to be " + node.scoreF + ", instead it was " + fscore;
    assertEquals(message, fscore, node.scoreF, 0.001);
  }
}
