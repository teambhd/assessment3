This directory contains the source code for Team BHD's Assessment 3 submission. It is based on the Assessment 2 project inherited from Team WAW.

## Building and Running the Game

### Using Ant

The easiest way to build and run the game is to use the [Apache Ant](http://ant.apache.org) Java build system. It can be downloaded from the website linked above, or installed through a package manager, such as apt-get on Ubuntu, [brew](http://brew.sh) on Mac, or [cinst](http://chocolatey.org) on Windows.

Once ant is installed the game can be run by opening a terminal window in the project directory and then executing `ant build` followed by `ant play`.

The included unit tests can be run (after building the game) with `ant test`. Subsequently running `ant report` will generate a html version of the test report within the junit directory.

The `ant clean` command will clean-up the working directory, by removing both the generated binaries and JUnit reports.

### Using Eclipse

The following instructions assume that Eclipse is already installed and configured on your computer. If not it can be downloaded by following [instructions on the project website](http://www.eclipse.org/downloads/).

Firstly, we'll need to import the downloaded project into Eclipse. In the main menu go to *File* > *Import...* and in the dialogue box that appears expand the "General" category and double-click "Existing Projects into Workspace". On the next screen click the *Browse...* button next to "Select root directory" and find the directory containing this Readme file. Then leave all the checkboxes in the bottom half of the dialogue unticked and click *Finish*.

To run the game, select the top-level "Game" item in the Package Explorer on the left-hand side of the screen and then go to *Run* > *Run Configurations...*. In the window that appears, expand the "Java Application" item in the left-hand column and then double click on the "game" item beneath it. 

To run the tests go to the same dialogue box and double-click the "tests" item within the "JUnit" category.

## Understanding the Source Code

TODO