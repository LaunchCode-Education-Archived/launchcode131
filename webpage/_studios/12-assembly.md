---
title: "Assembly: Just the Basics"
author: Ben Stolovitz
week: 12
assigned: 2016-04-11
due: 2016-04-11
---

## What does it mean to be a machine?

* this will be a toc
{:toc}

Earlier in the year, you used an LCD library to handle your interactions with the LCD on your breadboards. We used this library because the actual, `digitalWrite()` methods required to interface with it [are very complicated](http://www6.in.tum.de/pub/Main/TeachingWs2014Echtzeitsysteme/st7036.pdf). 

This leads to an interesting line of questioning: is `Serial` also a library? Both the LCD library and `Serial` work in much the same way. Then, suddenly, you're heading in circles: what is the computer *actually* doing for both libraries? Are these libraries written in C? How do they control the pins on the Arduino? Do they use `digitalWrite()`? What does `digitalWrite()` use? Is it just [libraries all the way down](http://wordplay.blogs.nytimes.com/2011/10/10/numberplay-turtles-all-the-way-down/?_r=0)? (no).

It is correct that the LCD code and `Serial` are both libraries, written in C, and it is correct that `digitalWrite()` is itself a C function. But there is no direct support in C for controling Arduino pins, nor is it possible for your computer to directly run C code: you must compile it first. There is another level of computation. C is a **high-level language**. If you asked us any earlier in the year, we would have said C compiles into **machine code** and left it at that. Without a more complete understanding of how a computer works, any elaboration would have been very, very circular. So first, let's talk about computing.

### Computing and the automata

The importance of the **transistor** is possibly the one piece of information that made it out of computer science and into the larger world. This is all well and good because it is what made microcomputers possible.

It's a bit funny, though, considering most computers use them as simple switches.

You see, it's easy to imagine a "computer" circuit that takes some **input** voltage and sets a separate **output** voltage to exactly the input. It's simple: just directly connect the input and output with a wire. However, any other operation is impossible without some sort of electrically controlled switch. To create, say, an *inverter* circuit, you need something to turn *on* the output if the input is off, and to turn *off* the output when the input is on.

The vacuum tube did exactly that, but was the size of a can of soda. The transistor and vacuum tube are, essentially, *electronic switches*. If a specified *input voltage* is high, these devices either connect or disconnect two other outputs (depending on the type of transistor). Thus, at least abstractly, it is also possible to build an inverter circuit.

By carefully defining data types, it is possible to use Boolean arithmetic (which we can now build circuits for) to do everyday arithmetic. For example, we can [perform integer addition using only Boolean operators](https://www.youtube.com/watch?v=OpLU__bhu2w). The exact way this work is *very* tangential to this class (take 260M or watch the linked video that makes this circuit out of dominoes!), but it becomes clear that it is possible to build circuitry to do any operation you desire, and you could even combine all that circuitry into a big chip that does tons of different operations on a single piece of input.

Now the question is: how do I tell this giant mega-circuit which operation I want to perform? The answer: add another input, an "operation number," which itself selectively connects or disconnects the right part of my circuit. Do I want to add my inputs together? Connect the "addition" part of my circuit to the output, and disconnect everything else. Bitshift left? Connect the bitshift part, disconnect everything else.

### The fetch-decode-execute cycle

Suddenly, you have the makings of a computer (this part is generally called an **arithmetic logic unit**). If you find a way to store data and build a way to read that data as input or write it as output (more transistor circuits), you have exactly a computer.

There arises an "obvious" way of doing things on your computer.

- First, your computer **fetches** the next instruction, identified by a **memory address** stored directly in the processor's **instruction register**. When your Arduino first starts up, the physical circuit places a fixed value in this register, identifying the location where some built-in Arduino code (called the **bootloader**) permanently lives. The instruction register is then incremented appropriately, so that it "points" to the next instruction.
 - Having now loaded the instruction from memory, your computer **decodes** it in exactly the same way as written above. 
- Now, the arguments are appropriately passed into the inputs, and the instruction is **executed**. Sometimes that means loading a value into memory. Sometimes it involves storing it into memory. Sometimes the instruction makes the processor add the two inputs and place the result somewhere else. Each operation has a physical circuit, an electrical pathway that, at least in the simple case, you could build on your own.

Since every aspect of your program, from the data you operate on to the intruction register (also known as the **program counter**), is some collection of bits, every part of the state of your computer can be used as input or output in the computer's operation. In other words, "moving" to the next instruction is the same as incrementing a number in memory to the CPU. If you want to *jump* to a specific part of your program, you just tell the CPU to place that memory location in the instruction register, and the next iteration of the fetch-decode-execute cycle will load that line of code.

### Assembly

These operations all have specific **opcodes** on every machine, each corresponding to some specific operation. By assigning them human-readable ASCII names, it becomes easier to reason about them and write your own code. Building a compiler for this program **assembly** language is simple: just read the ASCII characters and transform them to the appropriate opcodes and arguments.

It's this assembly, (almost) exactly corresponding to the operations done on your microprocessor[^almost], that we work with this week and next.
The full manual for the AVR assembly language instructions is available
[here](http://www.atmel.com/images/doc0856.pdf).
It includes descriptions of the addressing modes as well
as the instructions themselves.

[^almost]: Unfortunately, like everything in computer science, this blanket statement is no longer quite true. x86 processors, like the ones used in (almost) every large computer on the planet, realized fairly recently that their assembly instruction set was too big to create efficient circuits for. Instead, the CPUs themselves translate old x86 assembly to proprietrary internal instructions that the CPU actually operates on.

	Lucky for us, AVR is a **reduced instruction set computer** (RISC) and has physical circuits for most instructions, to the best of my knowledge.

## The first assembly

<aside class="sidenote">
### A cop-out
{:.no-toc}
	
We strongly considered writing this unit *entirely* in assembly. Since C and C++ both can compile into assembly (but usually go straight to machine code), it is theoretically possible. However, we ultimately decided against it.
	
Here's why:
	
- You gain nothing. In fact, it's quite a bit more illuminating to use the two languages together (how do types work in C? What are functions?), and we still get all the assembly gravy anyway.
- We gain a lot. The Arduino C library (with the functions we use like `digitalWrite()` and `Serial`) is secretly C++, as we've mentioned earlier. C++ gives object-orientation and classes to C, but it does so in a bit of a hacky way. Every function you write in C++ is **name-mangled** into something slightly different (`Serial.begin()` becomes `_ZN14HardwareSerial5beginEmh()` on my computer), which makes it extremely hard for us to use `Serial` in assembly-only programs.

Actually, that name-mangling is the reason for a lot of the "weird" bits of our program. The header files we provide wrap all the function names in `extern "C" {}` to tell the compiler, no, these are not C++ functions that should be name-mangled, but C functions, which play nicely with assembly.
</aside>

1. 	Luckily for my sanity and yours, the Arduino IDE now natively supports programming in assembly (this was not the case for previous semesters).

	You don't need special tricks, command line tools, Eclipse, or anything to write assembly for your Arduinos: you just need the Arduino IDE.
	
	Open up `studio12/simpleValues/simpleValues.ino` in your repo.
	
	You'll notice that the code still looks like C. That's because it is! Because we writers are not masochistic, our assembly unit is going to be a combination of C and assembly. If you open the `assembly.S` tab, you'll see the first bit of assembly you'll work on today.
	
	Upload this program now. What does it print to Serial Monitor?
2. Arduino automatically links source files properly. If you look at `assembly.h`, you see a **function prototype**---just like any other header file. We've included that header file in the main Arduino file, `simpleValues(.ino)`, so we can use the function like any other.

	This function, however, does not have a corresponding *C* source file. It's written in *assembly*.
	
	By writing it in a canonical form, we can use our assembly code in C programs without issue. In other words, C code always follows certain rules when compiling into machine code. If we follow those rules, we will have no problem integrating with C code.
	
	For example, Arduino C expects byte-sized function return values in a specific **register**: register 24. There are 31 registers---all 1 byte long---on an Arduino Uno (not including the operation **status register**---keeps track of whether or not addition operations overflowed and the like---and the **instruction register**---keeps track of the current assembly instruction's location), but C expects you not to change most of them.
	
	Therefore, if we change register 24, we change the return value of the function. The `ldi` instruction loads an immediate value into a register. Right now it loads `1`.
	
	Make the function return the largest possible value that can be stored in a single register (registers are one byte).
	
	Rerun the program. What does it print out now?


## Mimicking C

The assembly implementation of this function had 4 main parts. In order to create a "C-style" function in assembly, you need all of them.

All lines of assembly look something like this:
	
`[label:] instruction [argument], [argument]`
	
where square brackets (`[]`) indicate an optional part. For example, the line you edited had the **instruction** `ldi`, load immediate followed by two **arguments**, the destination register and the number to load.
	
Above it was a simple **label**, `giveMeMax`, which just specifically names a line of code so that other parts of the program can jump to it. Notice that this is identical to the function name called in the C code `simpleValues`. With the `.global` **pseudo-operation** above it indicating that this is a label usable elsewhere, that `giveMeMax` label becomes accessible to any code that wants to use it---like our C code.
	
Once the program jumps to that label, it continues linearly as long as it can, so we need to tell our function to go back to whatever call it. We have the function return with the operation `ret`.
	
Thus there are 4 main parts:
	
- a `.global` pseudo-op with a function name (identical to a C function prototype in a header file elsewhere)
- that function name as a label, indicating the beginning of a function's code
- the actual code of the function
- a `ret` operation to make the function return.

Our header file has a prototype for another function called `giveMeZero()`. Let's write it now.

1. `simpleValues.ino` has commented lines of code using this function. Uncomment them. If you try building your project now, the compiler will give you an error: you have not defined your function yet.
2. To remedy that, define your function in assembly. Make a label called `giveMeZero`.
3. You need to expose this label to the compiler, to indicate that this is a "visible" label: a global label. Above your new label, use the `.global` pseudo-op (with the label name as the argument) to tell the compiler that our new label is *global* to the entire program.
4. Assembly runs line after line forever until it is told to stop. Right now, if you called `giveMeZero()` in your C code (which you can! Try it if you're interested), the function will never return. Add the `ret` operation after your new label to make the function return.
	
	If you run it now, what happens? Does it still return a value, even though you did not explicitly set one?
5. Use the `ldi` instruction like you did in `giveMeMax`, but instead have it return a zero.

	What does your program print now?

## Calling all functions

<aside class="sidenote">
### Stacking up
{:.no_toc}

The `call` and `ret` operations (and the `push` and `pop` operations you'll use for the assignment) all operate on the **stack**. This is a *convention* for using memory, though it does blur the line between datatype and convention[^types].

[^types]: Although you can consider any datatype to be a convention, or, at least, you can consider the *implementation* of a datatype to be a convention. e.g. IEEE floating point.

The stack's primary purpose is keeping track of function calls: when you `call` a function, the computer puts the location of the next instruction on the stack, then jumps to the called function. When that function `ret`urns, the computer looks at this stored address in the stack and jumps to the correct point in the program to continue execution.

The way this works is interesting. The stack is simply a segment of memory that we block out arbitrarily (if it gets bigger than this segment you get a **stack overflow**). By keeping track of the latest entry in the stack with a **stack pointer** (just like we keep track of the current instruction with the instruction pointer), we can **push** new data onto the stack and **pop** it off the stack. A push will increment the stack pointer and put this data in the new location, and a pop will read the data then decrement the pointer. Thus a `call` will push the return address onto the stack and a `ret` instruction will pop this address off.

You can use the stack for arbitrary data storage as well, provided you clear that data off before the stack needs to be used elsewhere. The `push` and `pop` instructions let you push and pop arbitrary registers, but remember that the stack knows nothing about data that you put on it. If you don't `pop` all the data you `push`, there will be extra data on the stack. When your program tries to `ret` or read from the stack next, it will read *your* data, and not the data it was expecting. This is called **stack corruption**. Keep track of your stack.

</aside>

You can also *call* functions from assembly. If you `call` a C function (`call functionName`) in assembly your program will call that function. By following a **calling convention**, an agreed-upon standard of how functions should be handled in assembly, it's possible to have your assembly functions have C-style returns (which you did earlier by writing to register 24) and accept C-style arguments.
 
We provide a C function that prints the state of all the registers on your Arduino: `printRegs()`. You can call it from C (just like any other C function) or you can call it from assembly.
	
1. Modify one of your assembly functions to call `printRegs` (using the `call` instruction) before and after `ldi`. What is in `r24` before and after? Do any of the **status register**  bits change?
2. There's another function stub in `assembly.h`, `addFour()`. We want it to take its argument, add four, and return the added value.

	Modify `simpleValues.ino` to call and print the result of `addFour()` on a number of your choice. Since we have not written the `addFour()` definition yet, your program won't compile.
3. Write the basics of the `addFour()` function in assembly: the `.global` pseudo-op, the appropriate label, and a `ret` call. Don't worry about actually doing anything yet, just make sure your program compiles.
4. The AVR Libc (the software that handles C compilation for AVR) documentation's question, "[What registers are used by the C compiler?](http://www.atmel.com/webdoc/AVRLibcReferenceManual/FAQ_1faq_reg_usage.html)," has a section on **function call conventions**. 

	What register do you expect the single byte argument to appear in? Use `call printRegs` to verify your answer.
5. We want to add `4` to this argument and return it. The [`add` instruction](http://www.atmel.com/webdoc/avrassembler/avrassembler.wb_ADD.html) is perfect for this (don't worry about overflow), but it can only operate on registers. To use it properly, you will need to use a second register *besides* the argument register. The AVR Libc documentation [question you looked at earlier](http://www.atmel.com/webdoc/AVRLibcReferenceManual/FAQ_1faq_reg_usage.html) has a list of **call-used registers** (also known as **caller-saved registers**) that you can use freely in your functions without worrying about breaking things.

	Use `ldi` into a call-used register and `add` to properly add four to the function argument.  Since the return value is a `byte`, your function should store this result in register 24. Normally this would require a `mov` operation, but there should be a convenient "coincidence" here.
6. Your `addFour()` function should now work correctly. Test it a bit. What happens when you overflow a byte? What happens to the status register's **carry flag** (`C`)?

## I/O from the get go

<aside class="warning">

If you get to this part and are running out of time, go ahead and check out. Consider this lab a success! This section just delves deeper into assembly's capabilities.

</aside>

You'll deal with further memory manipulation (loading and storing variables, pointers, references, etc) in this week's assignment, but we're going to cover another aspect of output in assembly.

Like your desktop computer, which connects to multiple forms of output (like your monitor, speakers, wi-fi card, and rumble pads on your Xbox controller), your Arduino can also communicate with output pins---you've been working with these pins all year. The `digitalWrite()` command in Arduino is [a simple C function](https://github.com/arduino/Arduino/blob/master/hardware/arduino/avr/cores/arduino/wiring_digital.c#L138-L163), but we're going to do something similar in AVR assembly.

AVR assembly provides two ways to access I/O pins. All of them are numbered and can be accessed using their appropriate address in **I/O space**, but they are also mapped to *different* memory addresses and can be modified by editing their values in **memory space**. These pins then have two addresses: their *I/O space* address and their *memory space* address. 

The address you use depends on the commands you use to access them. If you use dedicated instructions for modifying I/O ports (like we will) you use their I/O space address. If you use memory modification instructions (like the sort we will use to modify variables and memory in the assignment), you use the memory space address.

Unfortunately, the constants given to us in `avr/io.h` are the memory space addresses. We will have to convert them with the `_SFR_IO_ADDR()` macro, which justs subtracts `0x20`.

1. Define the `turnOnLight()` function in assembly, then add some code to `simpleValues.ino` to call it.
2. AVR I/O pins are grouped in banks: a large microcontroller could hypothetically have around 20 banks of 8 ports, but ours only has 3. For each bank there are three corresponding I/O registers, each controlling a single aspect of each pin. For example, the `DDR` register controls data direction (`1` for output, `0` for input), while the `PORT` register will write output to an output pin or selectively enable the pull-up resistor for an input pin. The `PIN` register will read an input pin.

	The three banks, `B`, `C`, and `D`, correspond respectively to digital pins 13-8, analog pins 5-0 (two of `C`'s bits correspond to built-in timers), and digital pins 7-0---yup, the order is weird.
	
	We'll be using the `B` bank to control pin 13, since that requires no additional wiring. *Arduino* pin 13 corresponds to bank B's *fifth* pin.
	
	Use the [`sbi` command](http://www.atmel.com/webdoc/avrassembler/avrassembler.wb_SBI.html) (Set Bit in I/O, which writes a `1` to a single I/O bit in a single bank) to change the data direction of Arduino pin 13 to output: `sbi _SFR_IO_ADDR(DDRB), PIN5`.
	
	`_SFR_IO_ADDR()` converts a memory address of an I/O pin to it's appropriate I/O address, so we are setting the the data direction (`DDRx`  of `DDRB`) of the fifth pin (`PIN5`) on bank B (`xxxB` of `DDRB`) to output (`1`).
3. Now set the correct bit of `PORTB` to `1` to turn on the pin. Remember to use `_SFR_IO_ADDR()`.

	What happens when you upload this program to your Arduino? The LED built into Arduino pin 13 should flicker on and off while the program starts, but should then settle into a specific state.
4. Now add a `cbi` instruction (Clear Bit of I/O register) to turn *off* the LED directly after you turn it on. Rerun the program. What happens to the LED? How long does it take for the change to occur? How does that compare to the 16MHz clock rate of the Arduino?

## Check out!

1. Commit your code and get checked out.
