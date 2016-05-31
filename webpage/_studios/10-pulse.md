---
title: Pulse-oximetry and measuring heartrate
author: Roger Chamberlain
week: 10
assigned: 2016-03-28
due: 2016-03-28
---

## Introduction

* this will be a TOC 
{:toc}

Pulse oximetry techniques 
use the light absorptive characteristics of hemoglobin (blood) and the
pulsating nature of blood flow in 
the blood vessels to determine the oxygen level (or oxygen saturation)
in the blood.  We will not be going all the 
way to measuring oxygen saturation levels,
but we will use pulse oxygenation probes to measure pulse, or 
heart rate.

### Objectives

By the end of this studio, you should know:

- how to read data from the pulse oximeter
- how to perform IIR filtering in software

## Hardware setup and information

Our probe looks like the picture below.

![pulse oximeter probe](../img/pulseOxProbe.png)

The probe houses a light source (both a red LED and an infrared LED,
we will only use the red LED) and a light detector (photodiode).
The probe is placed on the finger so there is a light-transmitting path from 
the source to the detector through a finger.
As the blood flow pulsates in the arteries, the amount of 
light detected varies, and the signal from the 
light detector is used to measure pulse.

More information about the general technique of pulse oximetry is available
[here](http://en.wikipedia.org/wiki/Pulse_oximetry).

Attach the pulse oximeter probe to your Arduino shield by plugging it into
the DB9 connector.

## Reading the raw signal

Wrap the sensor around your finger with the LED on the fingernail side.
Tape the sides shut so that it is a snug (but not tight) fit. 
Using appropriate techniques developed earlier in the semester,
read the analog input at a sample rate of 500 Hz (samples per second).
The probe is wired to analog input pin `A3`.
Note that you might want to use `micros()`
instead of `millis()` as your method of accessing the Arduino's
free-running timer.
This sketch should be called `pulseox.ino` and be in the
`Arduino/studio10/pulseox` directory.

Deliver this streaming data to the PC via the serial port.
Save several data files in the `data/studio10/` directory, and read
them into a Java class `ButterworthFilter` in the package `studio10`.
(You will need to create both the package `studio10` and the class
`ButterworthFilter`.)

## Butterworth bandpass filter

If you look at a plot of the raw data as a function of time,
you will see that it is quite noisy (feel free to just use Excel
for viewing the data).  We must filter the raw data to enable us
to discern the heart rate signal that is buried in all that noise.
For this assignment, 
we will use a bandpass filter (it "passes" frequencies within a specified
"band" in the family of filter designs originally described by British
engineer and physicist
[Stephen Butterworth](http://en.wikipedia.org/wiki/Stephen_Butterworth)
in 1930.
Computationally, the filter has the following properties.
The inputs are a stream of values 
$x_i$
and the filter outputs are a stream of values 
$y_i$
where $i$ is continually increasing.  Given both current and previous 
input values as well as previous output values, the current output
value is computed with the following 
difference equation:

$$ y_i = b_0 \cdot x_i + b_1 \cdot x_{i-1} + b_2 \cdot x_{i-2} - a_1 \cdot y_{i-1} - a_2 \cdot y_{i-2} $$

where the values of $a_j$ and $b_j$ are constant coefficients.

Author Java code to execute this filter, applied to raw data samples
delivered from the Arduino platform.  You may perform this computation
entirely using floating point numbers (`double`s in Java).

Appropriate values for $a_j$ and $b_j$ for this filter are listed
below:

$$ a_1 = -1.9789467705, a_2 = 0.97927235 $$
$$ b_0 = 0.01036382, b_1 = 0.0, b_2 = -0.01036382 $$

## Peak Detector

To determine the heart rate, we must figure out what is the period
of the filtered input signal.  This can then be converted into a rate.
We will measure the period by assessing the time between successive peaks.

Author Java code to detect the peaks in the filtered signal, and convert
these peaks into heart rate.  Take full advantage of what you learned last
week in studio.
Add the class `Pulse` to the package `studio10`.

## Finish up

1. Commit your code and verify in your web browser that it is all there.
2. Check out with a TA.

Things that should be present in your repo structure:

<section class="tree">

- `Arduino/`
	- `studio10/`
		- `pulseox/pulseox.ino`
- `data/`
	- `studio10/`
		- `YOUR_FILENAME.csv`
- `java/`
	- `studio10/`
		- `ButterworthFilter.java`
		- `Pulse.java`
</section>
