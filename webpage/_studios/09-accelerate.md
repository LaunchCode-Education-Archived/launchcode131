---
title: Accelerometer and Step counts
author: Roger Chamberlain
week: 9
assigned: 2016-03-21
due: 2016-03-21
---

## Introduction

* this will be a TOC 
{:toc}

Some peripheral devices are considerably more complex than others.
E.g., the LCD display is way more involved than any of the LED displays
we've encountered, even the 5x7 matrix display. The LCD display may be
easier to *work* with, but that's because we provide an API.
This week, we'll explore another fairly sophisticated input device,
an accelerometer, and its API, which will allow us to build a pedometer.

### Objectives

By the end of this studio, you should know:

- how to read data from the accelerometer
- how to perform peak detection in software

## Accelerometer setup

The accelerometer we will use is the MMA8441Q manufactured
by Freescale.  Take a look at its
[data sheet](http://www.adafruit.com/datasheets/Freescale_MMA8451QR1.pdf),
If you check out the block diagram (**Figure 1** on page 3) on the data sheet,
you will notice that the part is actually quite noticably more complex
than our Arduino processor!  It has a built-in processor of its own
that performs the `Embedded DSP Functions` on the block diagram,
in addition to three transducers (oriented along each axis) to detect motion,
separate analog-to-digital conversion, and various support hardware.

Adafruit's [Accelerometer Breakout Guide](https://learn.adafruit.com/downloads/pdf/adafruit-mma8451-accelerometer-breakout.pdf) does a great job of describing how to hook up
the accelerometer in general (see below).  It also provide a nice set of
example software and use case. Pages 16 to 18 give the API for the library.

If you have an Arduino kit that was used last semester, your accelerometer
is already connected, you can proceed to software development in the next
paragraph.  If you have a new Arduino kit, you will need to install your
accelerometer. The board is a small, blue circuit board with an 8-pin
header.  On the back it is labeled as a 3-Axis 14-Bit Accelerometer.
Insert it into the socket below the LCD display on the right side (the
socket is labeled U6).  The accelerometer board should hover over the
Arduino circuit board, not hang off the side.

1. The sketch `accelDemo` is in your repository and is based on the example
sketch you would build if you followed the breakout guide linked above. 

	Execute it, holding your Arduino case in various orientations so as to
understand the positive and negative directions in `x`, `y`, and `z`. 
If you look closely at the accelerometer board, it has labels for the
three directions.

	*Note, the baud rate is higher than we have used before, you will need to
alter it in your Serial Monitor.* The argument passed to `Serial.begin()` is
the baud rate your Arduino will use. Make sure Serial Monitor uses this baud
rate, too, by changing the value in Serial Monitor's dropdown menu.

## Research peak detection

Now, in your `Arduino/studio9/` directory, author a sketch called `steps`
that counts steps (i.e., functions as a pedometer).  The step count should
(by the time you are completely finished) be displayed on your LCD display.

You can either: (1) adapt to the current orientation of the sensor (i.e.,
sense which direction is down and look for steps in that direciton), or (2)
require the user to orient the sensor in a particular direction.

In either case, you may assume that the orientation does not substantially
change during the period of time over which you are counting steps.

To detect individual steps, we must figure out when the accelerometer
signal peaks, and then count these peaks.

1. Make the aforementioned `steps` sketch in your `Arduino/studio9/` directory.
2. To start things off, send a stream of accelerometer measurements to the PC
   and plot them on the screen. To make this easier, format your data as a 
   `.csv` (as you did earlier in the year): print each group of x, y, and z
   data on one line, separated by commas---but no spaces.

   You will want to save this in the `data/studio9/` directory, and you will
   use it later in your Java program. Also note that `Serial` is not
   automatically started. You will need to `begin()` it with the correct
   baud rate.

   Is the sample rate fast enough to see what is going on as you move your
   pedometer up and down?

## Doing peak detection

There are many approaches to peak detection, but the simplest have the
basic form shown below.

		if (y(i) > y(i-1)) && (y(i) > y(i+1))
			sample i is a peak

This is frequently augmented by adding a test to ensure that the values
are above the mean (remember, we are in the presence of a 1G field, so
the mean should not be zero).

1. Author Java code (new package `studio9`, new class `CountSteps`) to
detect the peaks in the signal, using signals saved from the Arduino.

	We will not be working with a messaging protocol this week. Instead, we
will be working with another type of stream: a **file**. We suggest using a
[`FileReader`](https://docs.oracle.com/javase/8/docs/api/java/io/FileReader.html)
(which is a `Reader` rather than an `InputStream`, technically, because it
reads characters instead of bytes, but otherwise operates very similarly).

	Have the `FileReader` open the `.csv` file you saved earlier. Its
constructor accepts a path to a file, relative to the top of your repo
(because that happens to be where Eclipse executes this code). You might want
to load, then,  `data/studio9/YOUR_FILENAME.csv`.

	Wrap this `FileReader` with a `BufferedReader` and you are in business
(just like with the protocol earlier in the year).
The `split()` method in the `String` class is also quite useful.

	*Note that I haven't given you much detail here.  Feel free to look around
in the Java documentation or elsewhere on the web for good ways to parse
`.csv` files (there are many).*
2. Once you're confidently loading the data properly and parsing it (separating
it into x, y, and z values as appropriate), compare the different step-counting
algorithms.

	Try simple peak detection as detailed above, or perhaps count zero-crossings
(where, between two reads, the measurement switched polarity, positive to 
negative or vice versa). More robust than zero-crossings is mean-crossings,
since gravity constantly pulls us down.   Maybe even more robust is a peak paired
with a zero-crossing counting as a step.

	Explore these algorithmic alternatives.
3. After you have a step counting algorithm working with saved data, port
it to the Arduino and count steps on live data coming from the sensor.
You might continue to send it to the PC for debugging purposes as you
continue to tune your algorithm.
4. If you wish, you may include a "clear" option (e.g., using a pushbutton),
which tells the system to reset the step count to 0.  This is not
essential for studio, but we'll see it again in an assignment.

## Finish up

1. Commit your code and verify in your web browser that it is all there.
2. Check out with a TA.

Things that should be present in your repo structure:

<section class="tree">

- `Arduino/`
	- `studio9/`
		- `accelDemo/accelDemo.ino`
		- `steps/steps.ino`
- `data/`
	- `studio9/`
		- `YOUR_FILENAME.csv`
- `java/`
	- `studio9/`
		- `CountSteps.java`
</section>
