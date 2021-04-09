package pathfinding.collections;

import pathfinding.entities.Node;

/** Class for Minimum Heap. */
public class MinHeap {

  private Node[] heap;
  private int size;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public MinHeap() {
    size = 0;
    heap = new Node[10];
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  private int parent(int pos) {
    return (pos - 1) / 2;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  private int leftChild(int pos) {
    return (2 * pos) + 1;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  private int rightChild(int pos) {
    return (2 * pos) + 2;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  private boolean isLeaf(int pos) {
    return (rightChild(pos) >= size || leftChild(pos) >= size) ? true : false;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  private void swap(int first, int second) {
    Node tmp;
    tmp = heap[first];
    heap[first] = heap[second];
    heap[second] = tmp;
  }

  /**
   * Rearranges Heap, lower priority nodes are brought upward towards root.
   *
   * @param pos {@value integer}
   */
  private void heapify(int pos) {
    if (!isLeaf(pos)) {
      int leftPos = leftChild(pos);
      int rightPos = rightChild(pos);
      Node first = heap[pos];
      Node right = heap[rightPos];
      Node left = heap[leftPos];

      if (first.compareTo(right) == 1 || first.compareTo(left) == 1) {
        if (left.compareTo(right) == -1) {
          swap(pos, leftPos);
          heapify(leftPos);
        } else {
          swap(pos, rightPos);
          heapify(rightPos);
        }
      }
    }
  }

  /**
   * Checks the tree for the given node. Returns true if found, false otherwise.
   *
   * @param node {@value Node}
   * @return true/false
   */
  public boolean contains(Node node) {
    for (int i = 0; i <= (size / 2); i++) {
      if (parent(i) < size) {
        if (heap[i].equals(node)) {
          return true;
        }
      }
      if (leftChild(i) < size) {
        if (heap[leftChild(i)].equals(node)) {
          return true;
        }
      }
      if (rightChild(i) < size) {
        if (heap[rightChild(i)].equals(node)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Inserts a Node to the heap and shifts it down, depending on the priority.
   *
   * @param node Node
   */
  public void add(Node node) {
    if (size == heap.length) {
      expandSize();
    }
    heap[size] = node;
    int current = size;
    while (heap[current].compareTo(heap[parent(current)]) == -1) {
      swap(current, parent(current));
      current = parent(current);
    }
    size++;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  private void expandSize() {
    int newSize = heap.length * 2;
    heap = copy(heap, newSize);
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  private Node[] copy(Node[] heap, int newSize) {
    Node[] elementsCopy = new Node[newSize];
    for (int i = 0; i < size; i++) {
      elementsCopy[i] = heap[i];
    }
    return elementsCopy;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the Node that has the smallest totalCost.
   *
   * @return Node with the smallest totalCost (f(x)).
   */
  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
  public Node poll() {
    Node item = heap[0];
    heap[0] = heap[size - 1];
    size--;
    heapify(0);
    return item;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public int size() {
    return size;
  }

  public Node[] getHeap() {
    return this.heap;
  }
}
