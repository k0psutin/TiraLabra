# User guide

## Installation

Download the latest [release](https://github.com/k0psutin/TiraLabra/releases) or clone the repository from GitHub.

Clone from GitHub and change directory:

```[CMD]
git clone https://github.com/k0psutin/TiraLabra
cd tiralabra
```

## Setup

Run `gradlew clean build` in command line or terminal.

Note: Running this command will generate all reports. However, if you want to generate any given report individually, see sections *[Checkstyle](#checkstyle)* or *[Jacoco test coverage](#jacoco-test-coverage)*.

All reports can be found from the following folder: `\app\build\reports\`

## Run program

You can start the program with the following command:

```[CMD]
gradlew run
```

## Checkstyle

You can generate the checkstyle report with

```[CMD]
gradlew clean checkstyleMain
```

Once finished, a report will be generated to the folder `\app\build\reports\checkstyle`.

## Jacoco test coverage

You can generate Jacoco test coverage report with:

```[CMD]
gradlew clean test
```

Once finished, a report will be generated to the folder `\app\build\reports\jacoco`.
