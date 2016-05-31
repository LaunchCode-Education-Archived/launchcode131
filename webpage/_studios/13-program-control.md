---
title: "Control yourself"
author: Ben Stolovitz
week: 13
assigned: 2016-04-18
due: 2016-04-18
---

## Introduction

* this will be a TOC
{:toc}

Last week we looked at the basic operation of assembly: how can you load
values, work with them, and write functioning programs.

Now we look at the thing that makes computers special: logic.

### Objectives

By the end of this studio, you should:

- know how to compare two values in assembly
- know how to use the result of a comparison to branch in your program
- know how to translate C-style loops into assembly, and
- know how to translate *any* C into assembly.

Yup, it's a lot. Let's get started.

## Compare is nondestructive.

The key to any logic is the compare instruction: `cp`. Let's use it.

1. We have written a barebones project, `programControl` (very creatively
named). In it are assembly stubs for three functions. Let's start by writing
`greaterThan`.
2. In AVR assembly there is only one "real" way to compare values: `cp`.
There are others, but they are all modifications to the basic function, and
all work pretty much the same way.

	The compare instruction `cp Rd, Rr` takes two registers and subtracts
them: `Rd - Rr`. The result is thrown away, but the processor sets the various
**flags** properly. The flags:

	- `Z`: the zero flag, high if the result is `0`.
	- `C`: the carry flag, high if there was an operational carry.
	- `N`: the negative flag, high if the result is negative.
	- `V`: the overflow flag, high if there was overflow.
	- `S`: equivalent to `N XOR V`, the signed flag
	- `H`: the half-carry flag, high if the low nibble carried from the high.

	Once you do a `cp` between two variables, these flags are set 
appropriately. Try comparing the arguments passed into the `greaterThan`
function, and use `printRegs` to examine how the flags are set. What happens
when you change the order? 

	We don't expect you to be too familiar with each of the flags because most
of the time you don't have to care about them, as you'll see in a minute.

## Jumping around

Assembly languages move linearly, executing one line after another, 
mindlessly. This is very different from C, which has loops and conditionals
and blocks of code.

Assembly languages control program "flow"---how the program executes different
code depending on the situation---by **jumping** and **branching**. 

For example, the command `jmp` takes as its argument a label and then *jumps*
from the `jmp` command to that label, without executing any of the code in
between. You can jump forwards or backwards to any location in your assembly
code merely by placing a label at the location you want to go and jumping
there.

		jmp myLabel
		nop ; this code will be skipped
	myLabel:
		nop ; this code runs

This is not useful most of the time, as people tend not to write code they
always want to skip. Instead, most of the time we write conditional jumps, or
**branches**, to control the flow of our program based on how two registers
compare with each other.

Branches jump based on the current status of the your CPU's flags, like the
carry bit and all the other flags set by the `cp` instruction. For example,
`brcs` branches if and only if the carry flag is set. By clever use of the
`cp` and `br**` instructions (where `**` is two letters, like `lt` or `ge`),
you can write comparisons for any type of boolean or mathematical tests by
choosing which flags to branch on.

Luckily, most of the time you do not need to be clever or pay attention to
the flags at all because AVR assembly has convenient branch commands for
most mathematical operations *and* includes a cheatsheet for all logical
comparisons 
(on page 10 of [the AVR manual](http://www.atmel.com/images/atmel-0856-avr-instruction-set-manual.pdf)). 
Using the various branch commands (like `brne`, `brlt`), you can do various
tests between registers, like `Rd != Rr` or `Rd < Rr`.

Just like a `jmp`, Aa branch command takes a label as an argument, jumping the
program to the label if its condition passes, or *continuing* to the next line
of the program if the condition fails:

		cp r18, r20 ; compare r18 and r20
		brne myLabel
		nop ; this code runs if r18 and r20 are equal
	myLabel:
		nop ; this code runs in either case

1. Author the `greaterThan` in assembly command to return one if `a` is
greater than `b` (if `r24` is greater than `r22`), and zero otherwise. Make
sure the results in `programControl.ino` make sense.
		
		char greaterThan(char a, char b) {
			// Yes, we know this is bad programming form
			// you should just `return (a > b);`
			// but we're illustrating a point here, ok?
			if(a > b) {
				return 1;
			} else {
				return 0;
			}
		}

## Get in the loop.

The `greaterThan` code can be thought of as, basically, an `if` statement.
If `a` was greater than `b`, return `1`, else `0`. Loops in C can be thought
of in the same way. A `while` loop might look like this:

	char i = 0;
	while(i < 5) {
		// code
		i++;
	}

But you can also use labels and unconditional jumps (`goto`s) in C to write it
with an if statement (C can have labels, just like assembly, but people prefer
not to use them. In fact, they're a bad practice):

	char i = 0;
	loopBegin:
		if(i >= 5) {
			goto loopEnd;
		}
		// code
		i++;
		goto loopBegin;
	loopEnd:
		// end

Since you should already be comfortable writing `if` statements in assembly,
you should now be able to write `while` loops in assembly (and `for` loops,
too).

1. We would like to implement multiplication of two numbers as a loop using addition:

		char mult(char a, char b) {
			int sum = 0;
			char counter = 0;
			while(counter < a) {
				sum += b;
			}
			return sum;
		}

	It turns out that there *is* a multiply instruction in AVR, but we've
never been a fan of doing things the easy way in this class. Implement
`slowMultiply` with add using the above algorithm. You will need to use other
registers for your `counter` and `sum` (no need to actually allocate memory
for them, keeping them in registers is fine).

	We do not care about overflow, and you're only multiplying `char`s, so no 
carries either.

	Write and test this function by calling it in `programControl.ino`.	
2. Just to prove once and for all that you understand program control, we want
you to implement exponentiation in a similar way. Sure, you could just call
your new multiply function the correct number of times (in a loop), but that's
no fun. Write `slowExponent` to calculate exponents using addition.

		char pow(char a, char power) {
			char sum = 1;
			char i = 0;
			while(i < power) {
				char innerSum = 0;
				char j = 0;
				while(j < a) {
					innerSum += sum;
					j++;
				}
				sum = innerSum;
				i++;
			}
			return sum;
		}

	Test this function by calling it in `programControl.ino`. Fix any logic
	errors you find. We still don't care about overflow.
3. Once you've completed all that, you should, at least theoretically, be able
to translate any C code into assembly with all the AVR you've learned in the
past two weeks. You are now a compiler! 

	You may spend time working on your assignments. 

	Notice that throughout this studio, we often first wrote out programs in C
before translating them to assembly. C is a more expressive language, so it's
often easier to write out what you want to do first and then directly
translate it to assembly. This may be a good way to complete your assembly
assignments: understand what you are trying to do, line by line in C, and then
implement the assembly. This let's you separate mentally the algorithms you
want to implement and the assembly boilerplate you have to write.