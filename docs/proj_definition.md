# Project definition

## Faculty

Bachelor's in Computer Science (BCS)

## Language used in documentation

All comments, variables, documentation etc is written in english.

## Programming language

Java

## Algorithms and data structures

The goal is to compare the efficiency of pathfinding algorithms A\*, Jumping Point Search and Iterative Deepening A\* (I listed A\* because the latter derives from it).

Comparison is done via benchmarking the time they take to solve finding a path from A to B in different kind of maps with different settings (Open/narrow spaces/etc).

I might implement more pathfinding algorithms if i have time.

For the algorithms i have to implement at least HashSet, HashMap, PriorityQueue and Queue data structures.

## Input / Output

### Input

- Program has a GUI where you can load an image for presentation.
- Testing is done on predetermined images.

### Output

- GUI displays pathfinding time and route. Limited benchmarks.
- Testing generates a plot with different settings.

## Time and space complexity

### A\*

Time complexity O(b^d)

Space complexity O(b^d)

### Iterative Deepening A\*

Time complexity O(bd), where b is the branching factor and d is the depth.

Space complexity O(d), where d is the depth.

### Jump Point Search

Since JPS is an optimization to A\* the time and space complexity remains the same, but running time is potentially reduced by an order of magnitude.

Time complexity O(b^d).

Space complexity O(b^d).

## Sources

- [https://en.wikipedia.org/wiki/Iterative_deepening_A*](https://en.wikipedia.org/wiki/Iterative_deepening_A*)
- [https://en.wikipedia.org/wiki/Jump_point_search](https://en.wikipedia.org/wiki/Jump_point_search)
