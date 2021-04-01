package pathfinding.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import pathfinding.collections.MinHeap;
import pathfinding.entities.Node;

public class NodeTest {

  private Node first;
  private Node second;
  private MinHeap queue;

  @Before
  public void setUp() throws Exception {
    first = new Node(null, 0, 0);
    second = new Node(first, 0, 0);
    queue = new MinHeap();
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
  public void testReturnTrueIfSmallerFscore() {
    first.setTotalCost(1);
    second.setTotalCost(2);
    assertTrue(first.compareTo(second) == -1);
  }

  @Test
  public void testReturnFalseIfNotSameCoordinates() {
    second = new Node(first, 0, 1);
    assertFalse((first.equals(second)));
  }

  @Test
  public void testReturnTrueIfInQueue() {
    queue.add(new Node(null, 0, 0));
    assertTrue(queue.contains(new Node(null, 0, 0)));
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
    node.setTotalCost(1 + distance(1, 0, 3, 0));
    Node node2 = new Node(null, 1, -1);
    node2.setTotalCost(1.42 + distance(1, -1, 3, 0));
    Node node3 = new Node(null, 1, 1);
    node3.setTotalCost(1.42 + distance(1, 1, 3, 0));
    queue.add(node3);
    queue.add(node2);
    queue.add(node);
    double fscore = queue.poll().getTotalCost();
    String message = "Expected fScore to be " + node.getTotalCost() + ", instead it was " + fscore;
    assertEquals(message, fscore, node.getTotalCost(), 0.001);
  }
}
