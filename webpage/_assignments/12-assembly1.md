---
title: Assembly—A Few Pointers
author: Ben Stolovitz
week: 12
assigned: 2016-04-13
due: 2016-04-27
---

**EDIT: The writeup for this assignment has been altered, keep an eye out
for bold text with the EDIT mark.**

## The idea

We've spent a fair amount of time in this class implementing filters and data-
processing algorithms, even if we haven't really talked about the mathematics
behind these algorithms. Instead, we focussed on the design considerations
around implementing these algorithms: do you offload the work to a more
powerful device? Do you slow down processing? How can you implement a fast
version of an expensive calculation?

There's a myth in the darker underbelly of the computer science world---among
some kernel programmers and performance snobs and game engine designers---that
a program written in assembly will run faster than a program written in a
higher-level language (like C). Though this used to be the case, it is no
longer true (most of the time): compilers these days are so advanced that they
produce programs almost indistinguishable from hand-written assembly in
performance. This myth is a remnant of a time when C was considered the
cutting-edge in high-level languages and ease-of-use.

However, assembly remains useful on two main levels. The first is simple:
understanding assembly, and how your code gets compiled to assembly, makes it
easier to understand how your computer "thinks," and thus makes it easier to
understand how to write performant programs. The second is also simple---
assembly forces you to be explicit and *truly* learn one of the most ubiquitious
parts of computer science: **pointers**.

<aside class="warning">
This will probably be, for some of you, the most difficult assignment of the
semester. Pointers are *hard* when you first see them. It takes a fair amount
of time to understand them.

Plus assembly is a rather slow language to work in. I spent about an hour
figuring out that I had written an `add` instead of an `adc` in one spot.

Start early.
</aside>

### Objectives

By the end of this assignment, you should:

- understand **pointers** and their use, in both assembly and (rudimentarily)
in C,
- know how to do assembly operations on datatypes larger than one byte, and
- understand the purpose of a code library, and, abstractly, how to build one.

## The background

### A FIR filter

In this assignment you will be building a **FIR filter**---a Finite Impulse
Response filter---to filter a noisy sine wave. We designed the filter and the
data so that it does a very good job at cleaning up the wave, so keep this in
mind when you test your program.

A finite impulse response filter is a class of filter that stops being
affected by a given signal (**impulse**) after a finite amount of time.
This is in contrast to, say, the Butterworth filter we saw earlier.

To implement our filter, we take two arrays: an array of data and an array of
filter coefficients. For any given *output value*, we multiply the last several
data points by the filter coefficients, in reverse order, and sum them. If I were
generating the 5th output value, for example, I would multiply the 5th input
value by the 1st filter coefficient, then the 4th input by the 2nd filter
coefficient, then
the 3rd by the 3rd, and so on, until I run out of input data or filter
coefficients.

In math speak, a given output value $Y_n$ is defined as:

$$Y_n = \sum_{k=0}^{m-1} f_k \cdot X_{n-k}$$

where $X$ is the array of input data and $f$ is the array of filter coefficients,
$\left|f\right| = m$ (the number of elements in $f$ is $m$). This means the
output for the first $m-1$ elements is poorly defined, so in our code we just
ignore them.

Each multiply-accumulate operation (multiply and sum) is referred to as a
filter tap.  A filter tap uses one coefficient and one input value, multiplies
them together, and adds the product into the running sum (accumulator).

### Fixed point numbers

Though it is possible to do this entire lab without being
*completely* aware of the fact, the data you will be working with are not
actually pure `int`s. They are fixed point numbers, which we touched upon
briefly in the first few weeks of class.

Since there is no native language support for these kind of numbers, we store
them as `int`s with the understanding that they are not. These are `Q15`
numbers, alternatively known as `Q1.15` numbers: the first (`1`) bit occurs
before the radix point and the remaining `15` bits occur after the radix
point. 

You can consider this just a shift of the radix point that is 
implied in every `int`: consider the number 32767, or `0b0111 1111 1111 1111`.
The number has an implicit radix point at the very end---to the left of the
radix lie increasing powers of 2: $2^0$, then $2^1$, then $2^2$, etc. A `Q`
number shifts that radix: in Q15, that same arrangement of bits describes
the number `.999969482` (`0b0.111 1111 1111 1111`, where each point to the
right of the radix point is a *decreasing* power of 2).

You can then think of normal integers as `Q0` numbers, if that helps. They
have no fractional digits. A `Q2.14` number would have 2 non-fractional digits
and 14 fractional ones.

What does this mean for you?

Well, first off, it might make sense to know how to convert numbers back and
forth between `Q` format and an easy-to-understand format, like `float`.
[The wikipedia article for the Q format](https://en.wikipedia.org/wiki/Q_(number_format))
explicitly gives you the formulas for this conversion---we're not too
concerned about your figuring out how to do that math.

In fact, addition and subtraction require no additional work on your part.
They work just the same with Q numbers and normal integers. Multiplication is
not the same story. Here's why:

You can view Q numbers as some integer $n$ divided by some implied number $d$.
This is why addition is no problem---because
$\frac{n_1}{d} + \frac{n_2}{d} = \frac{n_1 + n_2}{d}$, we can do the operation
on just $n_1$ and $n_2$ (add them), ignoring the $d$.

But what happens when you multiply these two numbers?

$$\frac{n_1}{d} \times \frac{n_2}{d} = \frac{n_1 \times n_2}{d \times d}$$

There's an extra $d$ in the denominator! In other words, $n_1 \times n_2$ does
*not* fully describe the multiplication of the two numbers. We have to divide
the result by $d$ to get the correct answer: $\frac{n_1 \times n_2}{d}$.

That is not the whole story (to be fully correct you must deal with rounding),
but it is good enough for our case. In that regard, we do not expect your
program's output to exactly match the provided results files.

### Libraries

Because it takes a non-negligible amount of effort to multiply Q numbers
properly, and it is hard to reason about them because they don't behave
exactly like integers, we require[^suggest] that you write some helper functions for
handling these numbers. Since we could theoretically use these same functions
in many places and they're somewhat finicky to get right, it's the perfect
time to write a **library**.

[^suggest]: We were going to say *suggest*, but *require* makes things so much clearer.  Yes, it is a requirement.

Unfortunately, Arduino doesn't really like libraries in a way that's easy to
develop with, so we're going to cheat a bit. Instead of writing the code,
testing it separately, and correctly **linking** it to our main program, we're
going to copy and paste it. In the real world, you would probably precompile
your library or at least separate it a bit from your full-fledged program---
the LCD and Serial libraries we use do this, living separately from your code.
Since we want to make this assignment easier, we will not be doing that.

However, this still gives us the "cool features" inherent in libraries. We get
to separately test our code, and we get nice encapsulation of work. Basically,
once you've written this code, you don't need to think about it *at all*: you
can just use the functions as if they were provided to you by the gods of C.

Most libraries use more formal tests, but we've given you a simple program
that does some multiplication for you and lets you verify your results by eye.
Once that's working, throw your "library" code into the main project and get
to work.

### Assembly

So far you've only worked with datatypes that fit in one register. All the
assembly you wrote in studio dealt with `byte` or `char` types, both of which are 1 byte in size.
Likewise, you've never had to handle multiple arguments in assembly, keep
track of several registers, pass arguments to functions, or deal with
pointers. That changes today.

#### Multiple bytes

Lucky for us, assembly was designed to do work on things larger than a byte.
In fact, most of the time ordinary assembly operations do the work for you.

For example, consider multi-byte addition. When you add the two lower bytes of
a multi-byte number, you may or may not generate an overflow: the carry bit.
Assembly keeps track of this for you. When you `add` two registers, the carry
flag (`C`) is set iff the addition generates this sort of overflow. When you
add the next byte, you want to add this carry bit as well. The `adc` (ADd with
Carry) instruction *does this for you*. By `add`-ing the lowest order byte,
then `adc`-ing the rest, you can add arbitrary length numbers.

In fact, for most instructions there is a corresponding instruction for
dealing with multibyte uses. For example, there is a `lsl` (Logical Shift
Left) instruction that bitshifts a given register (a sort of cheap way to
multiply by two). If you wanted to extend that over a multibyte number (say,
an `int` of two bytes), you will want to shift over the most significant bit
of the lower byte into the least significant bit of the higher byte. Well,
`lsl` appropriately sets the carry bit, and `rol` (ROtate Left through carry)
uses that carry bit for the lowest bit. You can thus shift an arbitrary length
number by `lsl`-ing the lowest byte, then `rol`-ing the higher bytes, from low
to high.

Most of the time, working with multiple bytes just requires a scan of the AVR
documentation, or, at worst, a Google search.

#### Multiple arguments

In studio, you dealt with the C **calling conventions**---you wrote assembly
code that worked because you knew how the C code would interface with
assembly. For example, you put return values in `r25:r24`, and got your arguments
from the same place. This does not suffice for multiple arguments *or* multi-
byte arguments. Fortunately, the calling conventions are considerably more
robust than that.

<aside class="sidenote">
##### Register discipline
{:.no_toc}

The problem with accepting (and working with) multiple large arguments is
simple: you only have 31 registers to work with, and the lowest 10 don't do
much. How do you handle more data? As detailed in studio, you can work with
the **stack**.

The stack is a blocked-out section of memory that, in general, stores the
address of the calling function(s). For example, if you have a function
`main()` that calls the function `Serial.println()`, the stack will store the
memory address of the instruction to execute after the function `println()`
returns---the next instruction after `println()`. When a function `ret`s, the
program checks the stack, removes the address from the top of the stack, and
moves the program execution there.

This is a **FILO queue**---a First-In, Last-Out queue. When you read an
element from it, you are always reading the most recently added element. The
first item you put in will be the last item you take out.

This is done by maintaining a **stack pointer** that *points* to the location
of the most recently added element. Every time you access an element, you
decrement the pointer by one, so it now points to the previous element. Every
time you add an element, you increment the pointer, so it points to the new
element.

This is automatically done when `call`ing or `ret`urning from a function, but
you can (and should) do it manually by using the `push` and `pop`
instructions. You can `push` the value of a register onto the stack 
(`push r26`) and `pop` the value on top of the stack into a register 
(`pop r23`).

You will *need* to do this in your code. In C, there are **caller-saved**
(**call-used**) and **callee-saved** (**call-saved**) registers. A call-used
register may be used and modified by a subroutine, so any *calling* function
must save them before calling a subroutine, either by `mov`ing them to another
register or `push`ing them to the stack. A call-saved register cannot be
affected by calling a subroutine, so your subroutine must save them before
modifying them and restore them before returning.

In short, you probably want to `push` both `r28` and `r29` at the beginning
of your assembly code.
</aside>

You can read them yourself in the [AVR libc manual](http://www.atmel.com/webdoc/AVRLibcReferenceManual/FAQ_1faq_reg_usage.html), but the writeup they provide is a little more
confusing than it needs to be. I suggest keeping it open for reference once
you understand it.

As a rule of thumb, C passes arguments in decreasing order *starting* at `r25:r24`
and moving downward until `r9:r8`. For example, the first argument to a function
would be at `r25:r24` and the second at `r23:r22`.

Multiple-byte arguments are passed with the highest-order bytes in the higher-
numbered registers. This means an integer as the first argument will be passed
with the highest order byte in `r25` and the low order byte in `r24`. Thus you
can say that the integer is in registers `r25:r24`. Larger types (like
`long`s) are passed in a similar manner: a `long` as the first argument would
be in registers `r25:r22`.

The return registers work in the same way. Two-byte values in 
`r25:r24`, and 4 bytes in `r25:r22`, etc.

### Pointers

A **pointer** is a variable that *points* to another location in memory. It is
a variable that holds the location of another variable. If you understand
that, even in theory, you understand pointers.

In a little more detail, your computer has memory. When you write a program,
you must choose what parts of memory hold what data of your program. If you
had a bank application, you could choose, for example, to store your current
bank account balance in memory location `0x0003a520` (you could, of course,
choose any memory location for this). When you write C code the compiler does
this for you, choosing addresses as necessary. Data types in C help the
compiler allocate the right space for any given variable.

Each variable, then, has a unique **address**. What if you had more than one
bank account? You could store both balances in two separate locations: maybe
locations `0xa520` and `0x001a`. You could then create another
variable, at another arbitrary location, that keeps track of which bank
account you're currently working with. You could set its value to `1` when you're
working on your first bank account and `2` when you're working with your
second. Or, you could set its value to the *address* of the current bank
account: `0xa520` if you work on the first and `0x001a` if you work
with the second. This "current bank account" variable, which is at its own
location in memory, stores another memory address. It *points* to that memory
address.

That is a pointer. In C, the syntax would look something like this:

	int bankABalance = 40;
	int bankBBalance = 2500;
	int *currentBank = &bankABalance;

The `currentBank` variable is an "int pointer" (`int *`---the space is
arbitrary) that points to the address of the integer `bankABalance` (you
obtain addresses with the `&` operator). Thus the final line of code says
"create a pointer to an integer (call it `currentBank`) and have it point to
the address of `bankABalance`."

This can be kind of abstract and hard to fathom (especially with the weird
symbols). When I first learned it, I was confused for weeks.

But fear not! You can work with assembly.

#### Loading in assembly

In assembly, there are three types of load commands that load a value into a
register. The first, `ldi`, you used in studio. It loads an *immediate* value
into a register: `ldi r21, 0x41`. The second is `lds`, which loads from a
fixed location in memory: `lds r21, 0x41`. Though the syntax looks almost
identical, these two instructions do very different things. After the first
instruction (`ldi`), the value in `r21` is exactly `0x41`. After the second
instruction (`lds`), the value in `r21` is *whatever value is stored at memory
location `0x41`*. In other words, `lds` looks at the specified memory location
and puts the value at that location into your desired register.

Let's look at that another way. Say you have your bank accounts (and their
balances happen to be less than a byte in size). You would like to choose a
place to store them. You choose to store them in memory location `0x14`. Now
you write some code to access the value stored in memory. A friend writes
these two instructions:

- `ldi r21, 0x14`
- `lds r21, 0x14`

Which one loads your bank balance into `r21`? Why? Think about it and come up
with an answer before continuing.

The correct answer follows, so please make sure you don't glance below.

OK

The correct answer is the second one, the `lds` instruction.
The second one will correctly load the value *at that
memory location* into memory. The first one will put the literal value `0x14`
into register `r21`, which is not what we want. Unless...

#### `ldi`, `lds`, then what?

...unless we want to use the address itself---as a pointer. Consider the
problem of summing an array of variables. Each variable has a location, so it
is quite possible to write the following sequence of instructions:

	ldi r16, 0 // set up a register for the sum
	// lds the first value
	// add it to r16
	// lds the second value 
	// add it to r16
	// lds the third value
	// add it to r16
	// etc...

This is certainly possible. But it becomes bothersome to work with. It doesn't
scale well. How well does this work with 10 variables? 40? How about the 120
we need to use for the assignment?

What if the variables occured one after another in memory? Couldn't you have a
pointer iterate through them, one by one? Load the address of the first, use
that address to load the actual value, add it to the sum, increment the
address, load that value, add it to the sum, repeat? You could throw it into a
loop. 

In fact, that's what you do when you iterate through an array
(`for(int i...) { myArray[i] /* do something */ }`). A C array is actually a
pointer to the first value in the array. When you access an element in the 
array (using the `[]` operator), your code increments the pointer 
appropriately and gets the value held at that location.

How do you do that in assembly?

#### The `ld` instruction

The answer is the `ld` instruction. So important it doesn't have a qualifying
letter (like the `i` or `s` used in the *lesser* instructions), the `ld` loads
a value from a memory address held in certain registers (the address
registers `X`, `Y`, or `Z`).

For example, if you knew that your bank account balance was stored in memory
location `0x41` you could *theoretically* (not actually, wait one moment) load
it into `r25` as follows: `ldi r24, 0x41; ld r25, r24`. Load the address into
one register, then load the value that the register now points to into `r25`.

This will not work: memory on your Arduino has a two-byte address. So, do what
C does for multi-byte arguments---pair up some registers. It turns out that
your Arduino is very particular about this and only allows you to use one of
three pairs of registers: `X` (`r27:r26`), `Y` (`r29:r28`), and `Z`
(`r31:r30`). Thus, the correct way to load location `0x41` using pointers is
as follows:

	ldi r26, 0x41
	ldi r27, 0 // the high byte of the address is 0
	ld r25, X // loads the value at location 0x0041 into r25

You may use any of the three pointer registers (`X`, `Y`, and `Z`) in an `ld`
instruction, but when designing your programs keep in mind that only *two* of
them are call-used (and one must be left untouched when a function you write
returns).

#### Return to practicality

That is very, very much all well and good, and unfortunately took a lot of
words to explain, but it's... not that useful for your current program. It
turns out that *you* (as a programmer) do not know the addresses of your
variables! By using C, you let the *compiler* choose the locations of your
variables for you.

Luckily for us, we can get these addresses and use them in assembly, using the
`hi8()` and `lo8()` macros. These are *not* functions, but compiler
instructions ("macros"), and you can use them in your assembly code. `hi8()`
extracts the high byte (`8` bits) of a symbol (e.g., a variable's memory
address), and `lo8()`
extracts the low byte.

For example, say you have a C variable `char myInitial`. If you want to load
it using pointers into `r18`, you would do as follows:

	ldi r30, lo8(myInitial)
	ldi r31, hi8(myInitial)
	ld r18, Z

For variables declared in C,
the name of the variable is available as a symbol in assembly
language, and the symbol is literally the variable's address.
As a result, the two `ldi` instructions load the address of `myInitial` into
the address register `Z`, and the `ld` instruction loads the `char` itself
into `r18`.
You will need to do this to load both your input data points and your filter
coefficients from their arrays into your program.

## The assignment

Now, without further ado, your program.

### Make a library

We've provided you with a basic program that runs test multiplications on
Q (fixed point) numbers. It's up to you to write the functions
(in C, no assembly language yet) that do the multiplications.

1. The first step in any project is to get some visibility into the system.
`fixedPoint.ino` runs some tests and outputs the result nicely, but it
doesn't do much right now. Run it to see how the output looks.

**EDIT: If you haven't done so already, change the `unsigned int` declarations
in `fixedPoint.ino` to `int`.  Treat the entire set of Q15 numbers as
signed throughout the assignment.**

2. The first code to write is the conversion functions `qtof()` and `ftoq()`.

	`qtof()` should take an integer in `Q` format (with a number of 
`fractionalBits`) and convert it to a float (so that you can easily 
understand its true value).

	`ftoq()` should convert a floating point number into a `Q` format integer.
It is the inverse of `qtof()`. You can find the formulas to these conversions
on [the Q format Wikipedia page](https://en.wikipedia.org/wiki/Q_(number_format))
(you should probably understand why these formulas work, but no need to derive
them or anything).
3. Rerun the program. Each integer should be followed by it's decimal 
representation in parentheses. Do some of the conversions manually to make
sure they're correct (you really, *really* want to do this. It will save *so*
much time).
4. Now, write the `qMultiply` function. Take two Q numbers with the same
number of fractional bits, then multiply them. This is not so simple, though
the implementation is also on the Wikipedia page. As a litmus test for 
understanding, you should understand why you need to use a `long` and why you 
need to bitshift. 
5. Test your multiplication by running the program!
Note that the decimal (float)
printouts round slightly, so a little inaccuracy there is not necessarily bad.

	**EDIT: this statement previously had some commentary about numbers greater than 1.  It has been removed, as Q15 signed values cannot be greater than 1.**

	This kind of development, of writing a set of functions and then testing
them outside the environment that they will be eventually used, is a key part
of developing libraries. Once you have written these functions and tested
them, you will be able to quickly isolate bugs in your assembly: you will be
sure that your multiplication works, so you can concentrate on the assembly
itself.

### Make a filter (with the filter tap computations mostly in assembly)

1. Once you've completed the library code, copy and paste it into the 
appropriate files in your `fir.ino`. Test the functions briefly to make
sure they work in this new project.
2. We've stubbed out the basic function `filterAtIndex` for you in 
`assembly.S`. Right now it does nothing but return `0`. You need to write the
function (in assembly language) so it does three things.
	
	1. Load the data point and filter coefficient at the given indices 
	(see `assembly.h` for argument order), i.e., `input1[sampleIndex]` and 
	`fir1[filterIndex]`.
	2. Multiply these numbers together (by `call`ing `qMultiply` with the 
	appropriate arguments, so the actual multiplication happens in your
	C library routing you wrote earlier)
	3. Add this multiplied number to the `sum` argument
	(passed when calling 
	`filterAtIndex`) and return this sum.

	Essentially, `filterAtIndex` is computing one tap of the overall
filter computation.
This requires you to handle several new concepts: loading values from
pointers with `ld`, understanding and parsing multiple arguments of lengths
larger than one register, calling functions and passing arguments to these
functions, then doing multi-byte arithmetic. Take this one step at a time.

	First, understand how arguments are passed to this function (`printRegs`
will be helpful). 

	Then, get `ld` (not `ldi` or `lds`) working to load the first index
of the data array and the filter array. I recommend using two pointer 
registers, one for each array. You will need to `ld` twice, incrementing the
pointer after the first `ld` (which you can do by `ld r15, X+`), to get both
bytes of the integers held in the array.

	Next, focus on correctly adding the index to the addresses so that the 
`ld` commands load the correct index specified by the arguments. You will need
to `add` and `adc` (to do multibyte addition on a two-byte pointer), and you
will need to multiply the index by a certain amount so that you offset the 
pointer correctly (how big are `int`s? how many `ld`s does it take to load
one?). Note that `sampleIndex` starts at `27` because earlier values do not
generate useful data.

	Now focus on calling `qMultiply` properly, passing arguments as specified.
It may be helpful to modify your `qMultiply` to print out its arguments.

	Finally, add the return value of `qMultiply` to the `sum` argument and
return it. You may need to `push` and `pop` registers in order to be able to
use both numbers.
3. Graph the result of this `fir` program compared to the input (the program
as written creates output formatted as a .csv, so use Excel or something).
4. Modify your program to filter the `input2` array (it should require two 
letters of change).
**EDIT: skip this part, as `input2` was accidentally overwritten by
`input1`, so they are the same.**
5. The files `results1.txt` and `results2.txt` in the directory
`data/assignment12` give filtered output values for the two input arrays.
Note that these files don't skip the first $m$ output samples, so pay attention
to where you start looking in the provided output files (and do not worry
about minor rounding errors).
6. **EDIT: this optional portion is for extra credit.**
The file `fir.ino` has the following four lines of code:

		int sum = 0;
		for(int j = 0; j < NUM_TAPS; j++) {
		    sum = filterAtIndex(i - j, j, sum);
		}

Author an assembly language routine, called filterLoop, in newly
created files (that you create) `filterLoop.h` and `filterLoop.S` (or
you can just add to `assembly.h` and `assembly.S`), that
performs the functions of the above inner loop (in `j`).  The routine
`filterLoop` should take an integer index `i` (which you know will be 125
or less, take advantage of this fact!) and return an integer `sum` (the result of the above summation).
It can call `filterAtIndex` as appropriate.

Replace the above lines of code in `fir.ino` with a call to `filterLoop`.

### Guidelines

- Spending the time to test your library and ensure it's perfect is worth the
investment.
- If you don't fully understand the FIR filter, try implementing it in C first
(you should be able to do it in 3 or 4 lines of code, exactly where
`filterAtIndex` is called).
- There are some rounding considerations you need to make when multiplying
Q numbers "perfectly." We don't care that much, so don't worry about your
numbers coming out *exactly* like the results. That being said, this filter is
really good and does not modify the range of the output (though it does have a
phase delay, i.e., the sine wave is delayed in time).
So the results should be close, but they don't have
to be perfect.
- It is a *really* good idea to work through the assembly step-by-step. The
`printRegs` function is your friend (`call printRegs`). It does not modify
anything.
- Save the extra credit stuff for last.

## The check-in

1. Complete the [cover page](http://goo.gl/forms/7FlfFaZBu1).
2. Commit your code and verify in your web browser that is is all there.
3. Check out with a TA.

### The rubric

- 15pts: Did the lab they demoed work?
	- Did the library work?
	- Do they understand pointers?
	- Do they understand the calling conventions?
	- Is it easy to grade?
- 5pts: Is the cover page correct?
	- Cover page completely filled
	- Questions answered correctly <!-- Pointers, Calling convention, Caller vs. callee -->
- 10pts: Is the inner loop functioning in assembly?
