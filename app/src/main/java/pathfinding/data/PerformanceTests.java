package pathfinding.data;

import static pathfinding.tools.ImgTools.loadImage;

import java.io.FileWriter;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import pathfinding.solvers.Astar;
import pathfinding.solvers.Ida;
import pathfinding.solvers.Jps;

/** Class for running perfomance tests. */
public class PerformanceTests {

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public void runTests() {

    JSONObject file = null;
    try {
      InputStream is = PerformanceTests.class.getResourceAsStream("/data.json");
      JSONTokener tokener = new JSONTokener(is);
      file = new JSONObject(tokener);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }

    String results = "# Test results";

    JSONArray tests = file.getJSONArray("tests");
    int size = 800;

    for (int k = 0; k < tests.length(); k++) {
      JSONObject obj = tests.getJSONObject(k);
      String filename = (String) obj.getString("image");
      JSONArray startX = (JSONArray) obj.getJSONArray("startx");
      JSONArray startY = (JSONArray) obj.getJSONArray("starty");
      JSONArray endX = (JSONArray) obj.getJSONArray("endx");
      JSONArray endY = (JSONArray) obj.getJSONArray("endy");

      results += "\n\n## " + filename + "\n\n</br>\n\n";

      for (int i = 0; i < startX.length(); i++) {

        int sx = (int) (startX.getInt(i));
        int sy = (int) (startY.getInt(i));
        int ex = (int) (endX.getInt(i));
        int ey = (int) (endY.getInt(i));

        results += "StartX: " + sx + " StartY: " + sy + " EndX: " + ex + " EndY: " + ey + "\n";
        results += "\n</br>\n\n";
        results += "| Algorithm | Solvetime (ms) | Nodes visited | Path cost |\n";
        results += "|--|--|--|--|\n";

        int avgTime = 0;
        double avgCost = 0;
        int avgNodes = 0;

        for (int l = 0; l <= 10; l++) {
          Astar astar = new Astar(sx, sy, ex, ey, loadImage(filename, size));
          astar.findPath();
          avgTime += astar.getEndTime();
          avgCost += astar.getPathCost();
          avgNodes += astar.getVisitedNodes();
        }

        results += "|A*";
        results += "|" + (avgTime / 10);
        results += "|" + (avgNodes / 10);
        results += "|" + (avgCost / 10) + "|\n";

        avgTime = 0;
        avgCost = 0;
        avgNodes = 0;

        for (int l = 0; l <= 10; l++) {
          Jps jps = new Jps(sx, sy, ex, ey, loadImage(filename, size));
          jps.findPath();
          avgTime += jps.getEndTime();
          avgCost += jps.getPathCost();
          avgNodes += jps.getVisitedNodes();
        }

        results += "|JPS";
        results += "|" + (avgTime / 10);
        results += "|" + (avgNodes / 10);
        results += "|" + (avgCost / 10) + "|\n";
        avgTime = 0;
        avgCost = 0;
        avgNodes = 0;

        for (int l = 0; l <= 10; l++) {
          Ida ida = new Ida(sx, sy, ex, ey, loadImage(filename, size));
          ida.findPath();
          avgTime += ida.getEndTime();
          avgCost += ida.getPathCost();
          avgNodes += ida.getVisitedNodes();
          if (avgCost == 0.0) {
            avgTime = 10000 * 10;
            break;
          }
        }

        results += "|IDA*";
        results += "|" + (avgTime / 10);
        results += "|" + (avgNodes / 10);
        results += "|" + (avgCost / 10) + "|\n\n</br>";
      }
    }
    try {
      FileWriter result = new FileWriter("results.md");
      result.write(results);
      result.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
