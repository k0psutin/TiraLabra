# Project structure

Project follows a 2-layer architecture and consists of following packages:

![packages.png](/docs/images/packages.png)

Package _ui_ holds program user interface related code, _tools_ image manipulation related code, _solvers_ pathfinding related code, _data_ performance testing related code, _collections_ data structure related code and _entites_ object oriented classes, such as [Node](app/src/main/java/pathfinding/entities/Node.java).

## Class diagrams

Program core functionality consists of classes `A*`, `IDA*` and `JPS` of which `A*` and `IDA*` inherists the abstract class `Pathfinding`. `JPS` inherits the class `A*` because it is an optimization for the `A*` class. These classes utilizes the datastructures from the classes `MinHeap` and `Node`. Also various functions are used from the class `ImgTools` such as `getPixelColor` and `drawLine`.

Program also includes `PerformanceTests` class which is responsible for running various performance related tests. The class also creates a markdown file which includes all the test results.

![classes.png](/docs/images/classes.png)

## Time and Space complexities of data structures

I think I managed to achieve the same time and space complexities for the algorithms and data structures as they were in the pseudocodes (Sources), except for IDA\*.

### MinHeap

The custom MinHeap class has time complexity as `O(log n)` since `heapify` is called upon `poll` and `add` and it has the time complexity of `O(log n)`.

### A\*

Time complexity: `O(b^d)` and Space complexity `O(b^d)`.

### JPS

JPS has the same time and space complexities as A\*, but it still end up being faster in some scenarios than A\*, because of the neighbour pruning and recursive jumping.

### IDA\*

With IDA\* I had to utilize a 2D-array for visited nodes, so IDA\* has the space complexity of `O(N^2)`. IDA\* also has the same time complexity `O(b^d)` as A* and JPS.

## Possible improvements

### A*/JPS

MinHeap could be replaced with Fibonacci Heap where you could decrease/increase priority of an added node, would eliminate the need of having an array for movement costs.

### IDA*

Usage of a cache (Transposition Table) of previously seen positions would eliminate the need of re-calculating nodes on each depth. Time complexity would be better but it would worsen the space complexity by another `N^2`.

## Sources

[A* search algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm) - Wikipedia.

[Iterative Deepening A*](https://en.wikipedia.org/wiki/Iterative_deepening_A*) - Wikipedia.

[Jump Point Search](https://harablog.wordpress.com/2011/09/07/jump-point-search/) - article about JPS by Daniel Harabor.
