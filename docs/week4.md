# Week 4

## What I have done this week and program progression

I've started writing the documents for the project.

## What is unclear or has proven to be difficult?

IDA* performance. On open spaces it works very well, but if the goal is behind obstacles it gets really slow. On more complex maps it is unusable. Seems that it is only good for a clear path.

### arena.png

</br>

StartX: 100 StartY: 90 EndX: 700 EndY: 90 - No obstacles.

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|3|5280|660.0|
|JPS|45|4|660.0|
|IDA*|1|660|660.0|

</br>StartX: 100 StartY: 90 EndX: 700 EndY: 630 - Cutting obstacle from side.

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|24|55389|906.0428560496241|
|JPS|161|23|906.0428560496184|
|IDA*|37|660|906.0428560496241|

</br>StartX: 150 StartY: 280 EndX: 660 EndY: 285 - Obstacle dead ahead.

</br>

| Algorithm | Solvetime (ms) | Nodes visited | Path cost |
|--|--|--|--|
|A*|10|109178|589.4380951166244|
|JPS|63|14|586.971190360793|
|IDA*|745|8159|596.5261110119103|

</br>

## What should I do next

Create more performance tests and documents.
