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

Performance testing related code is at `\app\src\main\java\pathfinding\data\PerformanceTest.java` and tests can be executed from GUI via button `Run performance tests`. Results can be seen in a markdown file `results.md` located at the root of the project.

Performance testing was done in three different maps, and collected data included pathfinding time, visited node count and path cost. Each of the tests were repeated for 20 times. For the tests I used Dragon Age Origin maps from [Moving Ai](https://movingai.com/benchmarks/dao/index.html).

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
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|2|1806|600.0|
|JPS|27|4|600.0|
|IDA*|0|600|600.0|

</br>

StartX: 100 StartY: 90 EndX: 700 EndY: 630
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|5|11644|823.6753236814764|
|JPS|141|21|823.6753236814714|
|IDA*|27|600|823.6753236814764|

</br>

StartX: 150 StartY: 280 EndX: 660 EndY: 285
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|14|14862|535.8528137423857|
|JPS|82|13|533.6101730552662|
|IDA*|814|14961|545.3675323681474|

</br>

### Test 2: arena2.png

![arena2.png](/docs/images/arena2.png)

</br>

StartX: 280 StartY: 130 EndX: 730 EndY: 225
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|33|45563|745.0214280248115|
|JPS|35|178|729.7665940288699|
|IDA*|Timeout|-|-|

</br>

StartX: 18 StartY: 402 EndX: 750 EndY: 655
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|108|138662|1030.612265096322|
|JPS|77|371|996.5462479183373|
|IDA*|Timeout|-|-|

</br>

StartX: 237 StartY: 536 EndX: 505 EndY: 268
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|41|59305|540.2152954766505|
|JPS|33|159|493.8233764908629|
|IDA*|Timeout|-|-|

### Test 3: orz103d.png

![orz103d.png](/docs/images/orz103d.png)

</br>

StartX: 145 StartY: 30 EndX: 753 EndY: 356
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|69|105879|2141.3981951089518|
|JPS|40|2015|2082.856997824493|
|IDA*|Timeout|-|-|

</br>

StartX: 145 StartY: 30 EndX: 580 EndY: 418
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|56|87674|1726.7169542418196|
|JPS|33|1687|1643.5727265770322|
|IDA*|Timeout|-|-|

</br>

StartX: 35 StartY: 344 EndX: 717 EndY: 351
| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|73|113135|2250.2215715998077|
|JPS|44|2131|2190.609306503492|
|IDA*|Timeout|-|-|

</br>

## Test observations

It seems that A\* has even performance across all tests maps, but as seen from the results, the visited node count is very high (large memory consumption) compared to JPS or IDA\*.

IDA\* performs very well if there are no obstacles on the path or if the path grazes few obstacles. On a path with a direct obstacle, the performance drops considerably, as seen on the last test path of test 1 and on both tests 2 and 3 (timeout).

JPS was the fastest algorithm on more complex and cavern-like maps and the visited nodes remained moderate. It also seemed that JPS found a path with a lesser cost than A\* and outperformed A\* solvetime on complex maps by 10 to 50%.

## Conclusion

Based on the testing, A\* is a versatile algorithm that performs evenly on all maps, but comes with a large memory overhead.

IDA\* has the best performance of the three if you have a grid with no obstacles.

JPS\* is the best for maps which has alot "surface", ie. mazes.
