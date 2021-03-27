package pathfinding.solvers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pathfinding.tools.ImgTools.loadImage;
import static pathfinding.tools.ImgTools.resizeImage;

import org.junit.Before;
import org.junit.Test;

public class AstarTest {

  private Astar astar;

  @Before
  public void setUp() {
    astar = new Astar(30, 25, 119, 24, resizeImage(150, 150, loadImage("arena.png")));
  }

  @Test
  public void testPathFound() {
    String answer = astar.findPath();
    assertTrue("Expected ''Path found'' but " + answer, answer.contains("Path found"));
  }

  @Test
  public void testPathNotFound() {
    astar = new Astar(38, 111, 52, 121, resizeImage(150, 150, loadImage("brc000d.png")));
    astar.timeout = 200;
    String answer = astar.findPath();
    assertTrue("Expected ''Timeout.'' but " + answer, answer.contains("Timeout."));
  }

  @Test
  public void testDistanceApproximationReturnsCorrectAmount() {
    astar = new Astar(0, 0, 100, 100, null);
    float distH = astar.distance(20, 30);
    assertEquals(109.4f, distH, 0.01f);
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
