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

Performance testing related code is at `\app\src\main\java\pathfinding\data\PerformanceTest.java` and tests can be executed from GUI, under the button `Run tests`.

Performance testing is done in three different maps, and collected data included pathfinding time, visited node count and path cost. For the tests I used Dragon Age Origin maps that were downloaded from [Moving Ai](https://movingai.com/benchmarks/dao/index.html).

Map size of 800x800 were used for all tests.

Each algorithm used same starting and ending points listed in `data.json` file:

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

Testdata uses average results from 10 consecutive tests.

### arena.png

![arena.png](/docs/arena.png)

### arena2.png

![arena2.png](/docs/arena2.png)

### orz103d.png

![orz103d.png](/docs/orz103d.png)
