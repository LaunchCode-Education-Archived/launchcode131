---
title: "Dot Dot Dot, Dash Dash Dash, Dot Dot Dot"
author: Julia Vogl
week: 5
assigned: 2016-02-24
due: 2016-03-09
---

## The idea

* This will be a toc
{:toc}

In this assignment, you will use the combined power of Java and Arduino's
built-in streams and LEDs to communicate in
[**Morse code**](https://en.wikipedia.org/wiki/Morse_code).
While the Morse code part is a bit of bonus fun, the main objective of this assignment
is to give you experience with stream communication.
In this week's stream encounter, the stream flows from the PC
to the Arduino.

By the end of this assignment, you will have an even better understanding of
how streams work and how they can be of use, as well as how information
is represented in streams.

**Schedule note:** This assignment is due in two weeks (March 9)
because of the exam next week.

## The background

<!-- If you haven't already, I would _highly_ recommend reading through our guide to streams before continuing any further. 

... Have you done it? Great! Let's move on. -->

### The Java side of the stream

In Java, we will be using the `OutputStream` class, which is very similar
to (just in the opposite direction of) the `InputStream` class from last week.
The `OutputStream` provided by `SerialComm` takes a sequence of bytes
in the Java program and delivers them, via the serial port, to the
Arduino. The `write()` method of `OutputStream` takes a single argument
that represents the byte to be delivered over the stream.

As with `InputStream`, an `OutputStream` can be wrapped (using the decorator
pattern---we "decorate" the original class with more functionality) to do more
things. In our case, we will use a pair of classes for this purpose. One is
`ViewOutputStream`, which you will author, and whose purpose is analagous to
`ViewInputStream` (observing every byte that flows through the stream). The
other is `DataOutputStream`, which is a built-in class, and provides features
to send multi-byte data elements out the stream (e.g., integers, floats,
etc.).

### The Arduino

#### Serial stream

The two primary functions you will be using for this assignment
are [`Serial.available()`](https://www.arduino.cc/en/Serial/Available)
and [`Serial.read()`](https://www.arduino.cc/en/Serial/Read).

`Serial.available()` returns an estimate of how many bytes are available to the `Serial.read()` function, always returning more than 0 if there is pending data, and `Serial.read()` reads the next byte from this stream. In other words, they behave very similarly to Java's `available()` and `read()` methods.

#### Including files

There is a file in the `Arduino/assignment5/morse/` directory called `morse.h`
(in addition to the expected `morse.ino`). This **header** file provides
supporting code for converting text into Morse code.

In order to access this handy conversion chart, you will need
to **include** it. The `.h` extension indicates this file is a
**header file**.

Header files are an important part of code organization in C. You've used them
before, so you should know that they're usually paired with a `.c` **source
file**. This separation helps for several reasons: it speeds up compile time,
keeps code organized, and in some cases, prevents order of declaration errors.
In our case, we created this new file to keep a super long array from clogging
up your code. ;)

By separating the public **interface** of your source files, you can be sure
that no one accidentally uses internal functions not intended to be exposed by
just not including them in the header file. In general, then, public
**declarations** appear in a header file while private declarations and the
actual, source code **definitions** appear in the source file.

Since we don't really have much source code in this support file, we chose to call
it a `.h` header file. It was very arbitrary, but probably the idiomatic one.
(The previous sentence can be understood to mean, "We cheated just a bit on the
normal rules, but it's ok because they really aren't hard and fast rules, they're
guidelines.")

## The assignment

### Getting the Arduino to recognize input from the Serial Monitor

1. In the Arduino sketch `morse.ino`, read in bytes from the serial
port using `Serial.read()`. Make sure that
you only invoke `Serial.read()` when `Serial.available()` indicates there
is data ready to be read.

2. Make sure each incoming byte is a morse code value by using the provided routine `convertByte()` (in `morse.h`---make sure you `#include` it): read until `convertByte()` returns a `-1` (which it will do for un-Morse-able bytes like the `\r`
character that Serial Monitor sends when you hit `return`).

3. For the incoming bytes for which `convertByte()` returns a non-negative
value, print the received byte on the LCD. Don't print the return value
of `convertByte()` to the LCD, send the return value of `Serial.read()`
to the LCD. You might want to organize the LCD output as you did in studio,
swapping back and forth between lines 0 and 1 of the LCD each time that
`convertByte()` returns `-1`.

	Exercise your sketch using the Serial Monitor to send lower case
letters, upper case letters, numbers, and a special character or two (e.g.,
`%` or `$`). What do you expect will happen for the special characters?

### Using the provided `.h` file, convert the input characters to Morse code

1. The file `morse.h` contains an array that will allow you to easily convert
   received bytes into their Morse code representations. You've already
   `#include`d the header file to use `convertByte()`, so you don't have to
   worry about that. If you look at the return value of `convertByte()`, you
   can see we have done much of the conversion for you. Use the
   provided `convertByte()` function to give you the first index into the Morse pattern
   array.

2. Note that the array `morse_chars[][]` is actually a two-dimensional array.
The `convertByte()` function returns the first dimension of `morse_chars`, i.e.
it identifes the correct *row* in `morse_chars` for a given letter.
You are expected to iterate through the second dimension until you find
a `0` entry (which indicates the end of a Morse character) or you have
observed all 5 positions in the row (which would also indicate the end of
the Morse character).
As you iterate through a row of the `morse_chars` array,
flash an LED on your Arduino
as indicated below for each position in the row (make sure the LED flashes
an appropriate duration so that you can see the difference between a long
flash and a short flash). 
You may use one of the LEDs built into your Arduino kit, or you may use
one that you wire up yourself (remember to stay away from digital output
pins 0 and 1).

	Some notes about the entries in `morse_chars`:

- a `0` indicates the end of a character or a "blank space"
- a `1` indicates a short flash (dot)
- a `3` indicates a long flash (dash)

	Some notes about timing of Morse code:

- the length of a dot is 1 unit
- a dash is 3 units
- the space between parts of the same letter is 1 unit
- the space between letters is 3 units
- the space between words is 7 units

3. Make sure your sketch handles illegal characters appropriately (ignore
   them) and can continue to receive a new characters from the data stream
   once it has finished displaying a given character in Morse code. When not
   outputting a dot or a dash of Morse code the LED should be turned off.

	**NO `delay()` allowed**! Hopefully, this won't cause too much of an issue for you ;)

4. Exercise this expanded sketch again using the Serial Monitor.

### Sending the stream from Java

Your final task is to author a Java program that performs very close to the
same function as the Serial Monitor. This was part of **Studio 5** as an
optional section. It is no longer optional. It is required for this
assignment.

1. In `java/`, create a new Package called `assignment5/`.

2. Create a new Java class called `SerialOutput` in `java/assignment5`.
It should perform the same as the `SerialTestOutput` class of **Studio 5**.

	Feel free to just copy the code and fixup the necessary
`package` statements and `import` statements if you have already implemented
`SerialTestOutput`. Otherwise, follow the directions in **Studio 5: Section 3**.

3. Create a new Java class called `ViewOutputStream` in `java/assignment5`,
again following the specifications described in the optional portion of
**Studio 5**. (Again, feel free to copy your previous implementation
if you have one.)

4. Exercise your stream that is now accepting characters typed into a
Java program that you wrote, sending those characters across a serial
link to an Arudino sketch that you wrote, and flashing an LED in Morse
code. I don't know about you, but personally I think this is pretty cool![^ed]

[^ed]: The editor thinks this very cool as well.

## The check-in

1. Fill out the `cover-page.txt` file in `Arduino/assignment5`.
2. Commit all your code. **Do not ask to be checked out by a TA until
after you have made certain that your work is committed**. Failing to
do this may result in you losing points on your assignment. 
3. Check out with a TA.

New files:

<section class="tree">

- `Arduino/`
	- `assignment5/`
		- `cover-page.txt`
		- `morse/morse.h`
		- `morse/morse.ino`
- `java/`
	- `assignment5/`
		- `SerialOutput.java`
		- `ViewOutputStream.java`

</section>

### The rubric

- 15pts: Did the demoed assignment work?
	- Receives data from PC
	- Prints on LCD
	- Properly flashes LED
	- Handles non-letters/numbers appropriately
	- Easy to grade?
- 5pts: Is the cover page correct?
	- Cover page completely filled out <!-- 1 -->
	- Correctness --- cover page questions <!-- 2 each -->
