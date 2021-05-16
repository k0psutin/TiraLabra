# User guide

## Installation

Download the latest [release](https://github.com/k0psutin/TiraLabra/releases) or clone the repository from GitHub.

Clone from GitHub and change directory:

```[CMD]
git clone https://github.com/k0psutin/TiraLabra
cd tiralabra
```

Jar:

```[CMD]
java -jar pathfinding-1.0.jar
```

## Setup

Run `gradlew build` in command line or terminal.

Note: Running this command will generate all reports (not Javadoc). However, if you want to generate any given report individually, see sections *[Checkstyle](#checkstyle)* or *[Jacoco test coverage](#jacoco-test-coverage)*.

All reports can be found from the following folder: `\app\build\reports\`

## Run program

You can start the program with the following command:

```[CMD]
gradlew run
```

## Generate Javadoc

Run `gradlew javadoc` in terminal.

Javadoc will be generated to `\app\build\docs\javadoc`

## Build .jar

```[CMD]
gradlew packJar
```

`.jar` will be located at: `\app\build\libs\`

## Checkstyle

You can generate the checkstyle report with

```[CMD]
gradlew clean checkstyleMain
```

Once finished, a report will be generated to the folder `\app\build\reports\checkstyle`.

## Jacoco test coverage

You can generate the Jacoco test coverage report with:

```[CMD]
gradlew jacocoTestReport
```

Once finished, a report will be generated to the folder `\app\build\reports\jacoco`.
