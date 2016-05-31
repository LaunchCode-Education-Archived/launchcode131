---
title: Streaming Data from the PC to the Arduino
author: Roger Chamberlain
week: 5
assigned: 2016-02-22
due: 2016-02-22
---

## Introduction

* this will be a TOC 
{:toc}

After sending data from an Arduino to the PC running Java, this week
we turn things around. We will send data from the PC to the Arduino
and display it on an LCD.

### Objectives

By the end of this studio, you should know:

- how to use the LCD display in an Arduino sketch,
- how to receive data from the serial port in an Arduino sketch,
- a bit about how to work with strings in C, and
- (optionally) how to send serial data in a Java program.

## LCD Display

The first thing we'll do today is to get the LCD display working so that
we can display things on the Arduino itself (and not just send it to the PC).

1. The first step is getting the LCD working. Because communicating with the
   LCD is complex (and requires a very large number pins), we use a prebuilt
   library to communicate with it. The library files needed to use the LCD are
   `ST7036.h`, `ST7036.cpp`, and `lcd.h`, all of which should be in the
   libraries directory, `Arduino/libraries/LCD/`. If they are not there, update
   your local copy from the repository.

	In order to use these files, you need to have your `Sketchbook location`
set properly. This needs to be the `Arduino/` directory of your checked-out
repository. Use `File>Preferences` in the Arduino IDE to check
(`Arduino>Preferences` on a Mac), and if you need to change it to get it
right, you will also need to restart the Arduino IDE.

	Execute the example application and look over its code.

2. Open the blank sketch `Arduino/studio5/serial2LCD/serial2LCD.ino` and get
   it to display some text of your choice.  Once you have declared an object
   of type `ST7036` (e.g., named `lcd` as in the example code), you can use
   `lcd.print()` just like you use `Serial.print()` for sending things to the
   Serial Monitor on the PC.

	The LCD coordinate system (see `setCursor()` in the example) has the
character position indicated with an ordered pair `(x, y)`, where `x`
indicates the display row (either `0` or `1`) and `y` indicates the display
column (ranging from `0` to `15`).

1. By refering to the example program `LCDExample.ino`, modify your current
   Arduino program to receive strings from the serial port (of length up to 16
   characters, feel free to truncate if longer) and display them on the LCD
   display. You can use
   [`Serial.available()`](https://www.arduino.cc/en/Serial/Available) and
   [`Serial.read()`](https://www.arduino.cc/en/Serial/Read) or
   [`Serial.readbytes()`](https://www.arduino.cc/en/Serial/ReadBytes)
   (the first two work similarly to Java) to receive characters from the serial port.

	The first received string should be displayed on the top row (row 0), and
the second received string should be displayed on the bottom row (row 1).
When the third string is received, the display should be cleared and
the cycle repeated (first displaying on the top row).

	When using the Serial Monitor in the Arduino IDE to test the code above,
the line that you type in is actually delivered to the Arduino when
you hit the `return` key, which causes a `'\r'` character (carriage return)
to be sent as well.  Do not send this character to the LCD display, but do
use it for the sketch on the Arduino to understand that the string is complete.

	You will need to do some (fairly simple) string manipulation
within your Arduino sketch to accomplish this[^note].  Take a look at the
[string][stringref]
documentation for Arduino C, and use the array of characters approach
to strings, not the `String` class.
Given the need to support 16 characters that will be displayed on the LCD,
and a `'\r'`, and a `NULL` termination, what is the minimum size you should
declare an array of characters for use in this sketch?

4. In addition to displaying the received strings, echo them back to the
sender (including the trailing carriage return).  You will likely want to
turn this feature on and off at various times, so feel free to
put it in a conditional that is set at compile time (e.g., whether the
conditional passes or not is determined by some value in a `#define` or
a `const int` declaration near the top of the sketch).

[stringref]: https://www.arduino.cc/en/Reference/String
[^note]: There is actually a built-in function in `Serial` that does this. 
	Please don't use it here.

Show off your sketch to an instructor or TA.

## Optional additional stuff

If you are this far by the end of studio time, consider this studio
a success and jump ahead to Finishing up.  If you have time, there are
some additional things you can play with that are part
of Assignment 5, so doing them now gets you ahead of the game.

### Enabling Java communications

<aside class="warning">
The next step rehashes setting up the necessary Java libraries.
If you did this before (e.g., in studio last week) you do not need to
do it again. Skip ahead to **Authoring Java tools**.
</aside>

1. The following files have been made available in the `jars/` directory of your
repositories: `RXTXcomm.jar`, `rxtxSerial.dll`, and `librxtxSerial.jnilib`.
Also, `SerialComm.java` and `PrintStreamPanel.java` have been provided in
the `java/studio4/` directory.
If you don't see them, you need to perform a repository update.

2. Next, you need to alter the build path for Java.  Right click on the
project name (i.e., your repository checkout), go down to Properties and pick
Java Build Path.  Then click libraries and add the `RXTXcomm.jar` file as
a library (using the `Add Jars ...` button on the right).

3. Once the jar file is listed as a library, expand it and double click
on "Native library location", indicating the `jars/` directory in the
workspace.  Note: during this last step, the `rxtxSerial.dll` and
`librxtxSerial.jnilib` files will not be visible during the selection.
You are selecting the directory that contains them.  FYI: `rxtxSerial.dll`
is the native library for Windows and `librxtxSerial.jnilib` is the native
library for the Mac.

4. The `SerialComm` and `PrintStreamPanel` class files
(found in `java/studio4`) enable
`InputStream` and `OutputStream` access to the serial port
and provide additional GUI support for diagnostic printing, respectively.
You will use them both to accomplish the tasks below.

### Authoring Java tools

1. The first task is to have a Java program send data to the Arduino.
We will start simple.

	In `java/`, create a new Package called `studio5`.

	In `java/studio5/`, create a new Class (called `SerialTestOutput`) that
will eventually (in its `main()` method) ask for a string of user input (up to
16 characters) and send it out the serial port.

	You may have noticed that the Java Console where `System.out` prints to
has a text box below it. You can send data *into* Java with this textbox: it
goes to an `InputStream` called `System.in`. You've used `InputStream`s
before, so this should not be too difficult.

	First, simply echo these strings of 16 characters back to `System.out`.
2.  Next, we want to send them out to your Arduino.

	You will need to create a `new` `SerialComm` (which we gave you
in studio 4---you will need to `import` it) and use the `connect()` method---
which takes a string argument with the port name---to connect to your Arduino.
Note (in the code for the `SerialComm` class) where to set the baud rate (feel
free to leave it at the current 9600 baud). Example code for guidance is
provided in the `main()` method of `SerialComm`.

	The `write()` method from the `OutputStream` object (use `getOutputStream()` to
get it) is appropriate for sending the character data.
You will want your `SerialTestOutput` to mimic the functionality of 
the Arduino IDE's Serial Monitor.  Make sure a `'\r'` is the
last character sent to the serial port.

	Using the Arduino sketch you developed above,
exercise your Java program and ensure
that you get the same text output on the LCD console that you previously
were getting when using the Serial Monitor built into the Arduino IDE.

3. The second task is to build a tool (that operates on `OutputStream`s) that
you can use to get visibility (observability) into the stream.
The new class is called `ViewOutputStream`, and it extends the pre-existing
`FilterOutputStream` class, which, as indicated by its name, filters output
streams. In our case, the "filter"ing we do is displaying the data.

	You can create `ViewOutputStream.java` either by creating a new Class within
`java/studio5` or by copying/pasting `ViewInputStream.java` from `java/assignment4`
and renaming it `ViewOutputStream.java`. Obviously, you will have to turn some
input references into output references if you use the copy/paste approach.
Also, don't forget to edit the `package` statement at the beginning to reflect
the fact that this Class is in package `studio5`, not `assignment4`.
*Don't* start from the `ViewInputStream.java` in `java/studio4`, as it had
missing material.
If you haven't done this already, feel free to
take a look at the documentation for `JFrame` (e.g., the `setBounds()` method)
so you can put your `ViewOutputStream`
window in a different initial position on the screen than your `ViewInputStream`
window.

	Your `ViewOutputStream`'s `write()` method should `super.write()` from the
parent `FilterInputStream` and also display the byte that has just been written in
ASCII hex form (using the provided `PrintStreamPanel` class from studio 4). In
other words, this class displays bytes being sent through the output stream,
but does not alter them. This should look very similar to `ViewInputStream`.

	ASCII hex form means that for every byte that goes through the stream, the display
should show 3 characters: a space character and 2 characters that
represent the data byte in hexadecimal (e.g., if the byte in the data
stream is `0x5f`, the string sent to the `PrintStreamPanel` should be
"` 5f`" (or "` 5F`" if you prefer).

	Wrap your `OutputStream` in your newly created `ViewOutputStream`.
Repeat task 1 and 2 above now using your `ViewOutputStream`'s `write()` method
rather than your original `OutputStream`'s `write()` method.
	
## Finish up

1. Commit your code and verify in your web browser that it is all there.
2. Check out with a TA.

Things that should be present in your repo structure:

<section class="tree">

- `Arduino/`
	- `studio5/`
		- `LCDExample/LCDExample.ino`
		- `Serial2LCD/Serial2LCD.ino`
- `jars/`
	- `RXTXcomm.jar`
	- `rxtsSerial.dll`
	- `librxtsSerial.jnilib`
- `java/`
	- `assignment4/`
		- `ViewInputStream.java`
	- `studio4/`
		- `PrintStreamPanel.java`
		- `SerialComm.java`
		- `SerialTestInput.java`
	- `studio5/`
		- `ViewOutputStream.java`
		- `SerialTestOutput.java`
</section>
