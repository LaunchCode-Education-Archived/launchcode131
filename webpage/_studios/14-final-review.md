---
title: Final Review (Optional)
author: Roger Chamberlain
week: 14
assigned: 2016-04-25
due: 2016-04-25
---

## Introduction

* this will be a TOC 
{:toc}

This optional studio is review material for the final exam.
The TAs are here to help today, but no need to checkout.

### Objectives

By the end of this studio, you should know:

- what to expect on the final exam

### Studio organization

As a review, this studio is organized around the set of subjects we
have covered since the midterm exam.  The exam is comprehensive, so
returning to Studio 6 and reviewing that material is also a good idea.

Each section will have some review
questions, some pencil and paper style, some expecting you to author some
code. For the questions where you are authoring code, note that exam questions
that ask you to author code will by necessity be short.

Do not feel obligated in any way to do these in order.  Jump
around and focus on the ones that will give you the most benefit in terms
of preparation for the final exam.

## Streams and communication

- What are the basic operations to receive/send a byte from/to the serial
port in Arduino C?

- Using `InputStream`/`OutputStream` in Java?

- Using `DataInputStream`/`DataOutputStream` in Java?

### Communicating a Color object

In Arduino C, a color is represented by three bytes, e.g.:

	byte red = 0x7f;
	byte green = 0x00;
	byte blue = 0x3f;

Design a protocol to send colors from Java to the Arduino.

In Java, we use a `Color` object (see below). Using only an `OutputStream`
on the sending side, write Java code to send messages (using the protocol
you designed above) that communicates `Color` objects to the Arduino.

	class Color {
		int red;
		int green;
		int blue;
		...

		public void sendColor(OutputStream out) {
			//insert your code here
		}
	}

Write Arduino C code to receive messages using your protocol and illuminate
the tri-color LED with that color.

## Digital inputs and filtering

- Draw a diagram that contains the Arduino, a pushbutton switch, and a
resistor such that when the switch is pressed, the `digitalRead()`
routine returns `HIGH` and when the switch is not pressed, it returns `LOW`.

- What is the correct `pinMode()` for the above digital input?

- Treating the input debouncing code that you authored in Studio 7 as a filter,
would you characterize it as an FIR filter, or an IIR filter?

- Is it a linear or a non-linear filter?

- Is it an analog or a digital filter?

- Is it a continuous-time or a discrete-time filter?

## Networking

- Google "IP address" so see the IP address of the machine you are working on.

- Ask your neighbor to do the same.  Which bytes of your IP address are the same as that of your neighbor?

- I'm a gamer and want the lowest latency option to play my online games.
Do I use UDP or TCP?

## Assembly language

No, don't panic.  The final exam will not expect you to author a complete
program of this complexity, and you will have access to the manual pages
for whatever assembly language instructions are needed.

However, the best way to prepare is still to author code.

Given the following C code, provide an equivalent assembly program.
You may assume that the size of an `int` is 16 bits.
You are free to allocate `i` and `sum` either to registers or to
memory during the execution of the loop,
but please store the result in the variable `result` at the end.
Provide appropriate comments in your assembly code.  

	const int n = XX; // n is the input value, give it a value
	unsigned char i;  // when allocating space for i, you may assume n < 256
	int sum;          // make sure that sum is two bytes
	int result;       // result is definitely two bytes

	sum = 0;          // write assembly language code for this line and the next 4 lines
	for (i=0; i<n; i++) {
		sum = sum + i;
	}
	result = sum;

The first thing to do is simply execute the above code in Arduino C.  Then
author the last 5 lines in assembly language.
Obviously, add `Serial.println()` statements as needed to see what is going on.

## Finish up

If you haven't yet done so, it's a good idea to review the material from
the midterm exam as well.  Remember, it is a comprehensive final, and this
set of exercises is mostly about the material since the midterm.
