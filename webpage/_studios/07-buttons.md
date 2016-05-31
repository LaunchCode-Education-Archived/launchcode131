---
title: Digital Ins and Multiplexed Outs
author: Roger Chamberlain
week: 7
assigned: 2016-03-07
due: 2016-03-07
---

## Introduction

* this will be a TOC 
{:toc}

We've explored analog output, analog input, and digital output.
Now it's time to look at digital inputs.
We'll also extend our investigation of display technology by starting
the exploration of multiplexed displays.

### Objectives

By the end of this studio, you should know:

- how to read digital inputs
- how to debounce a mechanical push-button switch
- how to follow a slightly more sophisticated schematic diagram
- how to light individual LEDs in a multiplexed display.

## Push-button inputs

The first thing we'll do today is to get the push-button inputs working
on your Arduino.  This includes investigating the bouncing properties
of the buttons themselves.

### Mirror

Write a sketch called `mirror` (an empty sketch is available in
`Arduino/studio7/mirror/mirror.ino`) that reads the state of the push-button
input (using `digitalRead()`) and sends it to an LED digital output.
The program should repeat this operation continuously.

Button SW1 is on digital pin 12, SW2 is on digital pin 13,
SW3 is on pin 16, and SW4 is on pin 15.
The `pinMode()` will need to be `INPUT_PULLUP`.
Is the input `0` or `1` when the button is pressed?

If you wish to use one of the two discrete LEDs built onto the board,
they are connected to digital pins 7 and 8.

Extend this program to count the number of times the push-button has
been pressed.  You can detect a press either on the 0 to 1 or the 1 to 0
transition of the digital input port.  Each time the count increases,
display (on the LCD display or on the Serial Monitor) the current value
of the count.

Does the count seem high at times?  Why might this be the case?

### Debounce

Write a new sketch called `debounce` (an empty sketch is available in
`Arduino/studio7/debounce/debounce.ino`) that reads the state of the
push-button using proper debouncing techniques.  How to do this is
covered in this [tutorial](https://arduino.cc/en/Tutorial/Debounce)
from Arduino.  In your opinion, does the tutorial use proper delta-time
techniques?

Check that your push-button is appropriately debounced by repeating the
counting experiment above.
What debounce delay is necessary for your push-button to be reliably
debounced?

## LED-based 5x7 pixel display

In the next assignment, you will be asked to display characters on
the 5x7 pixel display.  In this studio, we will start down that path
by lighting individual LEDs in the matrix.

### Light an individual LED

The LED display is organized as 5 columns and 7 rows of pixels (each
an individual LED).
To light an LED, the column connection must be at a higher voltage than
the row connection.  To make that happen, we have to understand how
the internals of the display are organized and how the pins of the
display are connected to the pins of the microcontroller.

The hardware we will be using for this studio is the 5 by 7 display
manufactured by Kingbright.  The full datasheet for the display
is [here](http://www.kingbrightusa.com/images/catalog/spec/ta07-11srwa.pdf),
and the pinout is shown in the image below:

![The pinout of the 5x7 pixel display](../img/5x7display.png)

The rows of the display are connected to digital pins 0 to 6 through
series resistors (that are on the circuit board, you do not need to
construct this).  The columns are directly connected to digital pins
7 to 11 (without a series resistor).  The schematic diagram is shown below.
The digital pins are on the left, numbered 0 to 13 (don't be confused by
the socket pin numbers, which start at 1, you want the numbers farthest
to the left), and the display is part U2 on the right.

![The 5x7 pixel display schematic](../img/displaySchematic.png)

You should ensure that the other hardware that uses these pins (0 through
11) is disconnected (at least to the extent that you can),
and insert the pixel display in its socket (making
sure that pin 1, which is marked on the part, is up and to the left).
Take care to keep the pins straight as you insert it into its socket.

To get an individual LED to light, the digital output associated with
its column must have an output `HIGH` and the digital output associated
with its row must have an output `LOW`.  In this way, there is a path
from the `HIGH` output, into the column conductor in the display,
through the individual LED, to the row conductor in the display, through
the resistor connected to the row, to the `LOW` output pin.
Note that while the pin(s) connected to the rows have an `OUTPUT` pin
mode, the current actually flows into the pin because the output voltage
is `LOW` (which is 0V, or ground).

To get multiple LEDs to light in a single column, the column output
should be `HIGH`, and each row output that is `LOW` will light the
corresponding LED in that row.  Row outputs that are `HIGH` will leave
the LED dark.

Write a sketch called `sequence` (an empty sketch is available in
`Arduino/studio7/sequence/sequence.ino`) that lights LEDs in the
following sequence:

- all LEDs off
- all LEDs in first column on
- a single LED (of your choice) in the first column on, all others off

Spend 1-2 seconds in each state above (`delay()` based timing is fine here),
and then cycle back to the beginning of the sequence.

If you have difficulty with uploading new software with the 5 by 7
display installed, add a delay of about 5 seconds or so (using `delay()`)
prior to setting the pins to `OUTPUT` with `pinMode()`.
When you wish to upload new software to the Arduino, press and release
the reset button (SW5) and then start your upload.  This should ensure
that your upload starts prior to the pin modes being set to `OUTPUT`,
so they can be effective in uploading the software.

Notice what happens with the discrete LEDs on the board.  Can you explain
this?

### Counting with different encodings

Alter your sketch so that it counts by 1 each iteration between 0 and 7 (the
count after 7 recycles back to 0).  Spend 1-2 seconds in each iteration
(again, either `delay()` based timing or delta-time techniques are OK),
or use a push-button press to advance to the next iteration.

#### Binary encoding

Print the value of the count on the LCD display
both in decimal and in binary.
[`Serial.print()`](https://arduino.cc/en/Serial/Print) supports this
directly, as does `lcd.print()`.
Don't use the Serial Monitor this time, as the pins conflict
with pins used to drive the 5x7 display.

Designate 3 LEDs in the first column of your 5x7 LED display as digits 0
(the least significant digit), 1, and 2 (the most significant digit) of
the count, and output the value of the count on these LEDs.  A simple
way to do this is to use brute force testing of each binary digit. E.g.,

		if (count & B00000001)
			digitalWrite(digitPin[0], ROW_ON);
		else
			digitalWrite(digitPin[0], ROW_OFF);
		if (count & B00000010)
			digitalWrite(digitPin[1], ROW_ON);
		else
			digitalWrite(digitPin[1], ROW_OFF);
		if (count & B00000100)
			digitalWrite(digitPin[2], ROW_ON);
		else
			digitalWrite(digitPin[2], ROW_OFF);

The above, of course, requires appropriate definitions and initializations
of the `digitPin` array and the constants `ROW_ON` and `ROW_OFF`.

What would be a better way to accomplish this output?

#### One-hot encoding

An alternative method of encoding a value using digital information
is called "[one-hot](https://en.wikipedia.org/wiki/One-hot)" encoding.
In this method, only one LED is on and
the remaining LEDs are all off.

Designate each of the LEDs in the first column as one of the values 1
through 7 (the value 0 has all LEDs off).  Each iteration, ensure that
no more than one of the LEDs is on (corresponding to the count) and the others
are all off.

### Expand your horizons beyond the first column

In a similar way that you are iterating 0 to 7 through the rows of your
display, now add iterations 1 to 5 that cycle (count) through the columns.
The column sequence should be the outer loop and the row sequence the
inner loop (i.e., for each column, iterate 0 to 7 through the rows).
Alternatively, use one push-button to advance the row count and
another push-button to advance the column count. (If you allow
push-button controlled count-up and count-down, you can effectively
move the LED light in all four directions!)

Display the column count on the LCD display along
with the row count.  Alter the 5x7 display illumination so that the only
LED that is lit is the one that corresponds to the column count and
the row count.  If the row count is 0, the display should be dark.

## Finish up

1. Commit your code and verify in your web browser that it is all there.
2. Check out with a TA.

Things that should be present in your repo structure:

<section class="tree">

- `Arduino/`
	- `studio7/`
		- `debounce/debounce.ino`
		- `mirror/mirror.ino`
		- `sequence/sequence.ino`
</section>
