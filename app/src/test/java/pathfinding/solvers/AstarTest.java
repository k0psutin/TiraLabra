package pathfinding.solvers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pathfinding.tools.ImgTools.loadImage;

import org.junit.Before;
import org.junit.Test;

public class AstarTest {

  private Astar astar;

  @Before
  public void setUp() {
    astar = new Astar(30, 25, 119, 24, loadImage("arena.png", 150));
  }

  @Test
  public void testIllegalStartPositionReturnsStartPositionNotReachable() {
    astar = new Astar(0, 0, 10, 24, loadImage("arena.png", 150));
    String result = astar.findPath();
    assertEquals(
        "Expected ''Start position not reachable.'' instead it was " + result,
        "Start position not reachable.",
        result);
  }

  @Test
  public void testIllegalEndPositionReturnsEndPositionNotReachable() {
    astar = new Astar(22, 22, 150, 150, loadImage("arena.png", 150));
    String result = astar.findPath();
    assertEquals(
        "Expected ''End position not reachable'' instead it was " + result,
        "End position not reachable.",
        result);
  }

  @Test
  public void testPathFound() {
    String answer = astar.findPath();
    assertTrue("Expected ''Path found'' but " + answer, answer.contains("Path found"));
  }

  @Test
  public void testPathNotFound() {
    astar = new Astar(38, 111, 52, 121, loadImage("brc000d.png", 150));
    String answer = astar.findPath();
    assertTrue("Expected ''No solution.'' but was " + answer, answer.contains("No solution."));
  }

  @Test
  public void testDistanceApproximationReturnsCorrectAmount() {
    double distH = astar.distance(20, 30, 119, 24);
    assertEquals(101.48528137423857, distH, 0.01);
  }

  @Test
  public void testIsEligibleMoveReturnsFalseIfNotEligibleMove() {
    boolean passable = astar.isEligibleMove(0, 0);
    assertFalse("Expected false, but was instead " + passable, passable);
  }

  @Test
  public void testIsEligibleMoveReturnsTrueIfEligibleMove() {
    boolean passable = astar.isEligibleMove(10, 10);
    assertTrue("Expected true, but was instead " + passable, passable);
  }
}
