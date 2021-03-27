package pathfinding.tools;

import static org.junit.Assert.assertEquals;
import static pathfinding.tools.ImgTools.getPixelColor;
import static pathfinding.tools.ImgTools.loadImage;
import static pathfinding.tools.ImgTools.resizeImage;

import java.awt.image.BufferedImage;
import org.junit.Test;

public class ImgToolsTest {
  @Test
  public void testGetPixelColor() {
    BufferedImage buffImg = resizeImage(500, 500, loadImage("arena.png"));
    String pixelColor = getPixelColor(100, 100, buffImg);
    assertEquals("(229,229,229)", pixelColor);
  }
}
