---
title: Health Monitor
author: Roger Chamberlain
week: 10
assigned: 2016-03-30
due: 2016-04-13
---

## The idea

* this will be a TOC 
{:toc}

In this assignment, we will build a system that monitors health, similar
to a FitBit.
Your health monitor will count steps, monitor heart rate (pulse), and
optionally measure temperature.

## The background

The health monitor you will build for this assignment will be comprised
of your Arduino and the desktop (or laptop) PC.  Sensing will happen
on the Arduino and a simple display will be on the Arduino (using the LCD
display).  An additional display (optionally including graphs of
measurements vs. time) will be on the PC.

The required computation will happen on the platform(s) of your choice.
Clearly raw sensor inputs need to be processed before they are ready
for display.  You can do this on the Arduino or on the desktop PC,
as you decide.

The look and feel of the health monitor is primarily up to you.  Required
elements include:

1. Arduino display of steps and pulse (optionally including temperature).

2. PC display of steps and pulse (optionally including temperature).

4. Ability to reset step count.

How each of the above displays looks is entirely up to you.  For example,
it is OK for the steps and pulse display on the PC to be strings printed
to the console.  Alternatively, you can do something more visually
interesting, like a graph of values vs. time.
If you plan to do the optional temperature measurement,
consider that fact as you design your visual layouts.

The step count can either be reset via the Arduino (e.g., using a
pushbutton) or on the PC.  Again, your choice.  Alternatively, you can
make the reset step count functionality available both places.

## The assignment

### Hardware and sensing operation

We have used all the hardware needed for this assignment already.
We explored the accelerometer in Studio 9 and the pulse oximeter
in Studio 10 (if you did not complete the exercises in those two
studios, completing them is a good place to start).

### System design

The challenge for this assignment should not be getting individual pieces
to work, we've done all the pieces already. The challenge for this
assignment is to decide what functions are to be completed on what
computing platforms, ensuring that the data are where they need to be,
and coordinating the entire process.

Things that you will need to consider include:

- Think about debugging early and often, because you will be doing a lot
of it.

1. Insert options for observability, anywhere it might be remotely
usable.

2. Make sure you can get at the raw sensor data, otherwise it is hard to
know whether or not your sensor processing is happening correctly.

- Sensing happens on the Arduino.  Sensor processing (turning raw data
into displayable data) happens on the platform of your choice.

1. What sample rate should you use for raw readings? This is something
you need to consider carefully.  It is unlikely to be the same for
all of the types of sensor data.

2. For the accelerometer, a peak detector is appropriate.  What length
window is appropriate for reasonable step counts?

3. For the pulse oximeter, more processing is required.  How are the time
scales different here?

- Processed sensor inputs need to be displayed both on the Arduino
and on the PC.  At a minimum, this requires data to move between the
two platforms.

1. Feel free to use the communication protocol we built earlier this
semester, adding a few more `keys` as needed.  However, you are not
required to use it in exactly that form.  Free free to design an
altered (or completely new) protocol.

2. The system might require communications in both directions.  Don't
neglect this fact in the design process.

- There are things to be displayed on both compute platforms.

1. Make sure the displays are usable (i.e., understandable) by anyone
who just walks up to the screen.  This means reasonable labels, etc.

2. If you do the optional graphs on the PC, they should be real time
(i.e., plotting as the system is running).  You can make reasonable
decisions as to how to show both steps and pulse (and even temperature)
on the same display (i.e., no need to get super fancy).
It is also OK to wipe the graph clean and restart at the left when the
plot(s) get all the way over to the right.


### Build the system


1. Author a sketch called `health` that runs on the Arduino.
It should be in the `Arduino/assignment10/heath/` directory.

2. Author a Java class called `Health` that is in the package
`assignment10` in the `java/` directory.  Feel free to add other
classes to the `assignment10` package as you see fit to complete
the applications.

## The check-in

1. Complete your cover page, which *again this time* is not a text
file in your repo, but is instead an on-line form.
The link is [here](http://goo.gl/forms/k5nbBXefYV).
You should do this *prior* to checkout.
2. Commit your code and verify in your web browser that it is all there.
3. Check out with a TA.

Things that should be present in your repo structure:

<section class="tree">

- `Arduino/`
	- `assignment10/`
		- `health/`
			- `health.ino`
- `java/`
	- `assignment10/`
		- `Health.java`
</section>

### The rubric

- 15pts: Did the demoed assignment work?
	- Displays reasonable step counts on either platform <!-- (3 pts) -->
	- Displays reasonable pulse on either platform <!-- (3 pts) -->
	- Resets steps correctly <!-- (3 pts) -->
	- Displays values on other platform <!-- (3 pts) -->
	- Easy to grade? <!-- (3 pts) -->
- 5pts: Is the cover page correct?
	- Cover page completely filled out <!-- (1 pt) -->
	- Correctness --- cover page questions <!-- (1 pt each) -->
- 5pts: Extra credit options
	- Displays temperature <!-- (2 pts) -->
	- Graphs displayed values in real time <!-- (3 pts) -->
