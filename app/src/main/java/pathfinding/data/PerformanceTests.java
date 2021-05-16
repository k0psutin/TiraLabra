package pathfinding.data;

import static pathfinding.tools.ImgTools.loadImage;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import javax.swing.ProgressMonitor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import pathfinding.solvers.Astar;
import pathfinding.solvers.Ida;
import pathfinding.solvers.Jps;
import pathfinding.solvers.Pathfinding;

/** Class for running perfomance tests. */
public class PerformanceTests {

  private String[] paths = {"A*", "JPS", "IDA*"};
  private String results = "# results.md\n\n## Test results\n";
  private int reps = 20;
  private int testsDone = 0;

  private boolean isCanceled = false;

  /**
   * Run performance tests for all pathfinding algorithms.
   *
   * <p>Tests will use coordinates from \resources\data.json
   *
   * @param pm ProgressMonitor
   */
  public void runTests(ProgressMonitor pm) {
    JSONObject file = null;
    try {
      URL res = getClass().getResource("/data.json");
      InputStream is = res.openStream();
      JSONTokener tokener = new JSONTokener(is);
      file = new JSONObject(tokener);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }

    JSONArray tests = file.getJSONArray("tests");
    int size = 800;

    for (int k = 0; k < tests.length(); k++) {
      JSONObject obj = tests.getJSONObject(k);
      String filename = (String) obj.getString("image");
      JSONArray startX = (JSONArray) obj.getJSONArray("startx");
      JSONArray startY = (JSONArray) obj.getJSONArray("starty");
      JSONArray endX = (JSONArray) obj.getJSONArray("endx");
      JSONArray endY = (JSONArray) obj.getJSONArray("endy");

      results += "\n### " + filename + "\n";

      for (int i = 0; i < startX.length(); i++) {

        int sx = (int) (startX.getInt(i));
        int sy = (int) (startY.getInt(i));
        int ex = (int) (endX.getInt(i));
        int ey = (int) (endY.getInt(i));

        results += "\n</br>\n\n";
        results += "StartX: " + sx + " StartY: " + sy + " EndX: " + ex + " EndY: " + ey + "\n";
        results += "| Algorithm | Solvetime (ms) | Nodes visited | Path cost |\n";
        results += "|--|--|--|--|\n";

        for (String path : paths) {
          int avgTime = 0;
          double avgCost = 0;
          int avgNodes = 0;

          for (int l = 1; l <= reps; l++) {
            Pathfinding pathfinding = getAlgorithm(path, sx, sy, ex, ey, filename, size);
            pathfinding.findPath();
            avgTime += pathfinding.getEndTime();
            avgCost += pathfinding.getPathCost();
            avgNodes += pathfinding.getVisitedNodes();
            if (avgCost == 0.0) {
              break;
            }
          }
          testsDone++;
          isCanceled = pm.isCanceled();

          if (isCanceled) {
            return;
          }
          pm.setProgress(testsDone);
          pm.setNote("Tests completed: " + testsDone);
          addResult(path, avgTime, avgNodes, avgCost, reps);
        }
      }
    }
    try {
      String filePath = System.getProperty("user.dir");
      filePath = (filePath.contains("app") ? ".." : filePath) + File.separatorChar + "results.md";
      FileWriter result = new FileWriter(filePath);
      result.write(results);
      result.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private Pathfinding getAlgorithm(
      String path, int sx, int sy, int ex, int ey, String filename, int size) {
    switch (path) {
      case "A*":
        return new Astar(sx, sy, ex, ey, loadImage(filename, size));
      case "JPS":
        return new Jps(sx, sy, ex, ey, loadImage(filename, size));
      case "IDA*":
        return new Ida(sx, sy, ex, ey, loadImage(filename, size));
      default:
        return new Astar(sx, sy, ex, ey, loadImage(filename, size));
    }
  }

  private void addResult(String name, int avgTime, int avgNodes, double avgCost, int div) {
    results += "|" + name;
    results += "|" + ((avgCost == 0.0) ? "Timeout" : (avgTime / div));
    results += "|" + ((avgCost == 0.0) ? "-" : (avgNodes / div));
    results += "|" + ((avgCost == 0.0) ? "-" : (avgCost / div)) + "|\n";
  }
}
