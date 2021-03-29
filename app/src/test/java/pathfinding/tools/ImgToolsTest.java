package pathfinding.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pathfinding.tools.ImgTools.getPixelColor;
import static pathfinding.tools.ImgTools.loadImage;
import static pathfinding.tools.ImgTools.resizeImage;

import java.awt.image.BufferedImage;
import org.junit.Test;

public class ImgToolsTest {
  @Test
  public void testGetPixelColorReturnsCorrectColorFromLocation() {
    BufferedImage buffImg = resizeImage(500, 500, loadImage("arena.png", 150));
    String pixelColor = getPixelColor(100, 100, buffImg);
    assertEquals("(229,229,229)", pixelColor);
  }

  @Test
  public void testResizeImage() {
    BufferedImage buffImg = resizeImage(300, 300, loadImage("arena.png", 150));
    assertTrue((buffImg.getWidth() == 300 && buffImg.getHeight() == 300));
  }
}
