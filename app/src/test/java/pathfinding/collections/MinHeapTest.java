package pathfinding.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import pathfinding.entities.Node;

public class MinHeapTest {

  public MinHeap minHeap;

  @Before
  public void setUp() {
    minHeap = new MinHeap();
  }

  @Test(expected = Test.None.class)
  public void testMinHeapAddObjectWorks() {
    Node first = new Node(null, 0, 0);
    first.setTotalCost(1);
    minHeap.add(first);
  }

  @Test
  public void testMinHeapSizeIncreases() {
    Node first = new Node(null, 0, 0);
    first.setTotalCost(1);
    minHeap.add(first);
    assertEquals(1, minHeap.size());
  }

  @Test
  public void testAddingAndRemovingWorks() {
    Node first = new Node(null, 0, 0);
    first.setTotalCost(1);
    minHeap.add(first);
    minHeap.poll();
    assertEquals(0, minHeap.size());
  }

  @Test
  public void testRemoveReturnsCorrectNode() {
    Node first = new Node(null, 0, 0);
    first.setTotalCost(1);
    minHeap.add(first);
    Node node = minHeap.poll();
    assertSame(first, node);
  }

  @Test(expected = Test.None.class)
  public void testAddMultipleObjectsWorks() {
    for (int i = 0; i < 10; i++) {
      Node node = new Node(null, 0, i);
      node.setTotalCost(20 - i);
      minHeap.add(node);
    }
  }

  @Test
  public void testMinimumValuesComesFirst() {
    Node node1 = new Node(null, 0, 0);
    node1.setTotalCost(10);
    Node node2 = new Node(null, 0, 0);
    node2.setTotalCost(5);
    Node node3 = new Node(null, 0, 0);
    node3.setTotalCost(1);
    minHeap.add(node1);
    minHeap.add(node2);
    minHeap.add(node3);

    Node node = minHeap.poll();
    assertEquals(1, node.getTotalCost(), 0.01);
  }

  @Test
  public void testPollGivesCorrectElements() {
    for (int i = 0; i < 10; i++) {
      Node node = new Node(null, 0, i);
      node.setTotalCost(20 - i);
      minHeap.add(node);
    }

    boolean correct = false;
    int i = 9;
    while (!minHeap.isEmpty()) {
      double score = (minHeap.poll().getTotalCost() + i);
      correct = (score == 20);
      i--;
    }
    assertTrue(correct);
  }

  @Test
  public void testContainsReturnsTrueIfFound() {
    for (int i = 0; i < 10; i++) {
      Node node = new Node(null, 0, i);
      node.setTotalCost(20 - i);
      minHeap.add(node);
    }
    assertTrue(minHeap.contains(new Node(new Node(null, 0, 0), 0, 5)));
  }

  @Test
  public void testContainsReturnsFalseIfNotFound() {
    for (int i = 0; i < 10; i++) {
      Node node = new Node(null, 0, i);
      node.setTotalCost(20 - i);
      minHeap.add(node);
    }
    assertFalse(minHeap.contains(new Node(null, 0, 15)));
  }
}
