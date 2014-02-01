This directory contains the source code for Team BHD's Assessment 3 submission. It is based on the Assessment 2 project inherited from Team WAW.

## Building and Running the Game

### Using Ant

The easiest way to build and run the game is to use the [Apache Ant](http://ant.apache.org) Java build system. It can be downloaded from the website linked above, or installed through a package manager, such as apt-get on Ubuntu, [brew](http://brew.sh) on Mac, or [cinst](http://chocolatey.org) on Windows.

Once ant is installed the game can be run by opening a terminal window in the project directory and then entering the following commands:

    ant clean
    ant build
    ant game

The included unit tests can be run (after building the game) with `ant test`. Subsequently running `ant report` will generate a html version of the test report within the junit directory.

### Using Eclipse

TODO

## Understanding the Source Code

TODO