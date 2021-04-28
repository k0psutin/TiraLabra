# Testing document

## Unit testing

GUI, ImgTools, Interfaces and Data were excluded from the test coverage.

Unit tests can be executed via command `gradlew clean test`. Command generates the test coverage report that can be found at [/app/build/reports/jacoco/test/html/](/app/build/reports/jacoco/test/html/) (local).

Alternatively, the test coverage can also be seen at [CodeCov](https://codecov.io/gh/k0psutin/TiraLabra/tree/main/app/src/main/java).

### MinHeap

Unit tests made sure that the custom minimum heap worked as intended. Has over 90% test coverage.

### Astar/Ida/JPS

Simple tests for pathfinding and distance approximation to indicate coding errors. Has over 90% test coverage. Pathfinding were mostly tested manually via GUI.

## Performance testing

Performance testing related code is at `\app\src\main\java\pathfinding\data\PerformanceTest.java` and tests can be executed from GUI via button `Run tests`. Results can be seen in a markdown file `results.md`.

Performance testing was done in three different maps, and collected data included pathfinding time, visited node count and path cost. Each tests were repeated 10 times. For the tests I used Dragon Age Origin maps from [Moving Ai](https://movingai.com/benchmarks/dao/index.html).

Maps with size of 800x800 were used for all tests with three different start and end points listed in `data.json` file:

```[JSON]
{ 
  "tests": [
    {
        "image": "arena.png",
        "startx": [100,100,150],
        "starty": [90,90,280],
        "endx": [700,700,660],
        "endy": [90,630,285]
    },
    {
        "image": "arena2.png",
        "startx": [280, 18, 237],
        "starty": [130, 402, 536],
        "endx": [730, 750, 505],
        "endy": [225, 655, 268]
    },
    {
        "image": "orz103d.png",
        "startx": [145,145,35],
        "starty": [30,30,344],
        "endx": [753,580,717],
        "endy": [356,418,351]
    }
  ]
}
```

### Test 1: arena.png

![arena.png](/docs/images/arena.png)

</br>

StartX: 100 StartY: 90 EndX: 700 EndY: 90

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|3|5280|660.0|
|JPS|45|4|660.0|
|IDA*|1|660|660.0|

</br>StartX: 100 StartY: 90 EndX: 700 EndY: 630

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|24|55389|906.0428560496241|
|JPS|161|23|906.0428560496184|
|IDA*|37|660|906.0428560496241|

</br>StartX: 150 StartY: 280 EndX: 660 EndY: 285

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|10|109178|589.4380951166244|
|JPS|63|14|586.971190360793|
|IDA*|745|8159|596.5261110119103|

</br>

### Test 2: arena2.png

![arena2.png](/docs/images/arena2.png)

</br>

StartX: 280 StartY: 130 EndX: 730 EndY: 225

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|37|387025|819.523570827293|
|JPS|39|248|802.743253431757|
|IDA*|Timeout|9|0.0|

</br>StartX: 18 StartY: 402 EndX: 750 EndY: 655

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|123|1191055|1133.6734916059543|
|JPS|88|495|1096.2008727101709|
|IDA*|Timeout|741|0.0|

</br>StartX: 237 StartY: 536 EndX: 505 EndY: 268

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|45|502280|594.2368250243156|
|JPS|34|200|543.2057141399491|
|IDA*|Timeout|192|0.0|

</br>

### Test 3: orz103d.png

![orz103d.png](/docs/images/orz103d.png)

</br>

StartX: 145 StartY: 30 EndX: 753 EndY: 356

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|78|876401|2355.5380146198468|
|JPS|51|2470|2291.1426976069424|
|IDA*|Timeout|91|0.0|

</br>StartX: 145 StartY: 30 EndX: 580 EndY: 418

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|67|725095|1899.3886496660016|
|JPS|46|2083|1807.9299992347346|
|IDA*|Timeout|0|0.0|

</br>StartX: 35 StartY: 344 EndX: 717 EndY: 351

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|84|939499|2475.243728759788|
|JPS|47|2626|2409.6702371538404|
|IDA*|Timeout|520|0.0|

</br>

## Test observations

It seems that A\* has even performance across all tests maps, but as seen from the results the visited node count is very large (large memory consumption) when compared with JPS or IDA\*.

IDA\* performs very well if there are no obstacles on the path or the path grazes few obstacles. On a path with a direct obstacle, the performance drops considerably, as seen on the last test path of test 1 and on both tests 2 and 3.

JPS was the fastest algorithm on more complex and cavern-like maps than A\* and IDA\*, and visited nodes remained moderate. It also seemed that JPS yield a path with less cost than A\* and outperformed A\* solvetime on complex maps by 10 to 50%.

## Conclusion

Based on the testing, A\* is a versatile algorithm that performs evenly on all maps, but comes with a large memory overhead.

IDA\* has the best performance and memory overhead of the three if you have a grid with no obstacles.

JPS\* is best for maps with no vast open spaces.
