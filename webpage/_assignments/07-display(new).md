---
title: Pixel Display
author: Roger Chamberlain
week: 7
assigned: 2016-03-09
due: 2016-03-30
---

## The idea

* this will be a TOC 
{:toc}

In this assignment, we will build a method of displaying characters and images
on the array of LEDs in the 5 by 7 matrix on your Arduino shield. This extends
the investigation we began in studio by using the entire display at any given
time via **multiplexing**.

## The background

<aside class="sidenote">
### Hardware setup

You should be familiar with how to use the LED display from studio. If not,
[**Studio 7: LED-based 5x7 pixel display**](http://classes.cec.wustl.edu/~cse132/weeks/7/studio/#led-based-5x7-pixel-display) should have you covered.

Remember that the pins on the display correspond to either a row or column
on the display, and that LEDs will only light if the *column* is at a higher
voltage than the *row*. When trying to turn on LEDs, do it one column at a
time, turning the column `HIGH` and all the rows you want *on* to `LOW`. Keep
all the rows you want *off* `HIGH`. This way there is a path from high to low
for your *on* LEDs, and not your *off* ones.

You can find the pinout of the display on the first page of the
[manufacturer's data sheet](http://www.kingbrightusa.com/images/catalog/spec/ta07-11srwa.pdf),
reproduced here:

![The pinout of the 5x7 pixel display](../img/5x7display.png)

The display's rows connect to Arduino pins 0 to 6, and have a resistor
attached to them on the shield. The columns are attached to pins 7 to 11, and
are not resisted. Disconnect all the other hardware you can if it's attached 
to these pins.

The circuit diagram below represents this. The digital pins on the Arduino
are on the far left of the image, while the display itself is part `U2` on
the top-right.

![The 5x7 pixel display schematic](../img/displaySchematic.png)
</aside>

### Output multiplexing

It is not physically possible to fully control more than one row of this LED
display at once, due to the way it's wired (what if I wanted to make a
checkerboard pattern?).

In order to control the *entire* board at any given time, we must **multiplex**
our connection. Multiplexing is communication with mutiple outputs (the
columns, in this case) using shared wires (the rows in this case). There are
many different [types of multiplexing](https://en.wikipedia.org/wiki/Multiplexing#Types),
but today we will be using **time-division multiplexing**.

Taking advantage of the very slow speed of the human eye (it thinks that
static images played back at 30fps or so look like motion), we can *cycle
through* the columns, one by one, very quickly, so that the change is
invisible to our perception.

For example, if I wanted to draw two columns "at once," I could do this:

- Set column 1's output `HIGH` and all the other column outputs `LOW`.
- Output `LOW` on the rows that are to be lit, and `HIGH` on the rows
that are to stay dark.
- Wait for $\frac{1}{2}$ of the length of a single **frame**, or the time
period that this whole process takes. You will choose this in a bit.
- Set column 2's output `HIGH` and all the other column outputs `LOW`.
- Output `LOW` on the rows that are to be lit, and `HIGH` on the rows
that are to stay dark.
- Wait for $\frac{1}{2}$ of the length of a single frame.
- Repeat.

An overall **frame length** of 5 seconds or so is good for debugging purposes,
but you will want the length to be less than about 30ms for
normal operation (33fps).

## The assignment

### Fonts?

We've provided a file, `font.h` (in `Arduino/assignment6/display/font.h`),
that defines an array called `font_5x7` that defines which LEDs should be on
or off to display any of the 96 printable ASCII characters on a 5 by 7 pixel
display.

It's a two-dimensional array: the first dimension corresponds to the character
code (using its ASCII code minus `0x20`, so `A` is index 33, not 65), while
the second dimension corresponds to the column (of the 5 by 7 display). The
individual bits of each index of the array represent the state of the LEDs in
each row, with an extra useless bit tacked on at the end (bytes are 8 bits,
but our LED only has 7 rows).

1. Make sure you understand how the font is represented by printing some
different characters in binary form:

		c = 0x03;  // '#'
		Serial.println(font_5x7[c][0],BIN);
		Serial.println(font_5x7[c][1],BIN);
		Serial.println(font_5x7[c][2],BIN);
		Serial.println(font_5x7[c][3],BIN);
		Serial.println(font_5x7[c][4],BIN);

	Hint: look at it sideways.

	For this piece of the assignment, do not set the `pinMode()` of the
display pins to `OUTPUT`, as that will interfere with serial communications.
The point of this part is just to help you understand how to interpret the
contents of `font_5x7[][]`.
2. Try the above with different values of `c`, especially values for which
the character is not symmetric left and right.
3. The serial connection to the desktop PC cannot be used at the same time as
the 5 by 7 pixel display, so after you have completed the above investigation,
remove the `Serial.begin()` call from your startup code. 

	You will need to print debugging output for the rest of this assignment
on the *LCD* display.

### Author the Arduino sketch

1. Open the `display` Arduino sketch.
2. 
	<aside class="sidenote">
	#### Difficulties
	{:.no_toc}

	If you have difficulty uploading new software with the 5 by 7
display attached, add a delay of about 5 seconds or so (using `delay()`)
prior to setting the pins to `OUTPUT` with `pinMode()`.
When you wish to upload new software to the Arduino, press and release
the reset button (`RESET`) and then start your upload.  This should ensure
that your upload starts prior to the pin modes being set to `OUTPUT`,
so pins shared with the serial port can be effective in uploading the software.
	</aside>

	Author an Arduino sketch that displays individual characters
on the 5 by 7 pixel display, using the provided font file `font.h`.

	It should also print the current character on the LCD.

	Initially, the display should be blank (the space character).  When the
user presses buttons on the Arduino, the displayed character should change.
The `UP` and `DOWN` buttons should change the character by 1 (increment the
current ASCII character displayed by 1 or decrement it by 1).  Wrap around to
the beginning (or end) as appropriate.

	The `LEFT` and `RIGHT` buttons should change the character by 16 (i.e., skip
16 characters ahead, or behind).  Again, wrap around as appropriate.

	All these buttons are on analog pin 0. What does `analogRead()` return when they're pressed?
3. As you will notice as soon as you look for it, the actual font information
for the ASCII characters `'0'` through `'9'` is not present in the file that
we provided (the entries are all there, but they are all blank).

	Design an appropriate image for each of these characters and include it in
the font file.

#### Guidelines

A few things to watch out for and/or consider:

- The polarity of the row outputs is not the same as the encoding in the
font file.  The font file has a `1` where the LEDs should be on, but the display
wants a `LOW` on the row pin to turn the LED on.
- It is possible to extract the individual bit information from the
font file in a single line of code, `if` statements are not needed. Think back
to your bitwise arithmetic.

	(You will not be penalized if you revert to a solution that does use
`if` statements.  I'm just nudging you to try it without to begin with.)
- Make sure you update your font file to draw numbers.
- You might find it useful to use the LCD for debugging, so setting it up
first (*before* you use the LED) might be very helpful.

## The check-in

1. Complete your cover page, which *new this week* is not a text
file in your repo, but is instead an on-line form.
The link is [here](http://goo.gl/forms/uHWxdR1fts).
You should do this *prior* to checkout.
2. Commit your code and verify in your web browser that it is all there.
3. Check out with a TA. Make sure to show off the characters that you designed.

Things that should be present in your repo structure:

<section class="tree">

- `Arduino/`
	- `assignment6/`
		- `display/`
			- `display.ino`
			- `font_5x7.h`
</section>

### The rubric

- 15pts: Did the demoed assignment work?
	- Displays characters correctly <!-- (5 pts) -->
	- Moves between characters correctly <!-- (3 pts) -->
	- Reasonable character design for 0 to 9 <!-- (5 pts) -->
	- Easy to grade? <!-- (2 pts) -->
- 5pts: Is the cover page correct?
	- Cover page completely filled out <!-- (1 pt) -->
	- Correctness --- cover page questions <!-- (1 pt each) -->
