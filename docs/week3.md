# Week 3

## What I have done this week and program progression

I made a minimum heap for holding the nodes, increased the test coverage and started implementing IDA\*.

## What I have learned this week

I have learned that IDA\* is a mixture of BFS and DFS search with heuristic acting as a bound. I also noticed that A\* performs fine overall, but JPS and IDA\* outperforms A\* depending on the complexity of the map. For example,
IDA\* is the fastest of the three when there are no obstacles/few obstacles, but JPS is the fastest of the three when the map has corridors.

## What is unclear or has proven to be difficult?

I've done IDA\* according to the pseudo code. It runs well when there are no obstacles, but on a path with obstacles it usually timeouts.

Edit: I added a tiebreaker for IDA\*, now it finds a path when there is an obstacle, but is still rather slow when facing an obstacle.

## What should I do next

I'll start to create the other documents for the project.
