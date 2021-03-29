# Week 2

## What I have done this week and program progression

I have tried to implement A* and JPS algorithms, written some JavaDoc and tests.

## What I have learned this week

About different Heuristics and their use.

## What is unclear or has proven to be difficult?

I'm not sure if A* is actually really slow in 500x500 spaces or is my algorithm just running very slowly. If I disable backtracking, A\* will work at super speeds, but paths are not optimal.
Maybe I should reduce the map sizes even further. Maybe the problem are tiebreakers?

For now i have reduced the map size to 150x150, A\* works on it quite well most of the time. Sometimes JPS can finish a route in 15ms where as A\* has been running 10 seconds.

Edit 30.3:

- A\* was running slow because it seems I made a mistake with the hashing and HashMap didn't work as intended. Problem did not appear in tests. Now I can finally concentrate on JPS and IDA\*.

## What should I do next

I have to continue to implement IDA* and my own data structures even though I'm not sure if A\* is still working like it should.
