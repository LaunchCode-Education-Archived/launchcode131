---
title: Welcome to CSE131!
week: 0
assigned: 2016-01-27
due: 2016-02-03
---

* This will be a TOC
{:toc}

#Pre-Lab 0 - CSE131 &amp; Version Control

In CSE131 this semester, we will be using <a href="https://git-scm.com/" target="new">Git</a> as our version control system.

Version control systems (VCS) are great for when you want to record who did what for which file or set of files, and it is especially useful when multiple people collaborate on projects, as you will be in this course.

<iframe src="https://player.vimeo.com/video/41027679" width="480" height="270" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>

Before working on any project, you must *check out your repository*. The instructions for this will be later on in this writeup.

The general workflow of Git, following the repository being checked out, is as follows. Don't worry if you don't understand some of the terms yet, they'll make sense as we go through this studio and the coming lab:

> 1. Pull from the master/origin
> 2. Do work on one/some of your files
> 3. Commit and/or push your work back to the origin
> 4. Repeat steps 1 through 3

These steps will have to be repeated and followed for every assignment you work on throughout the duration of this course.

Whenever you sit down to work on an assignment, you **must always** pull from origin/master before working. If you don't, it is possible that you'll cause a conflict.

###Which computer should you use for this lab session?

* For most of this course's assignments, you will probably want to do the work on your
own computer.  

* There should be time at the end of this lab session for you to install the course software
on your computer.

* If you don't have your computer with you, or the software is not yet installed, then please use a
lab computer in this room.

##Getting Started with Java in eclipse with Subversion

Professional software developers use an <a href="http://en.wikipedia.org/wiki/Integrated_development_environment">Integrated Development Environment (IDE)</a> that includes a text editor (for
creating source code), a <a href="http://en.wikipedia.org/wiki/Compiler">compiler</a> (for translating source code into
executable code), and a <a href="http://en.wikipedia.org/wiki/Debugger">debugger</a> (for finding errors).  In this
course,
we'll be using <a href="http://www.eclipse.org/">eclipse</a>, a popular IDE that supports (among many other
things) programming in
Java.  

Professional software developers also use a 
<a href="http://en.wikipedia.org/wiki/Software_versioning">versioning repository</a> to
store their code, so that they can share their code easily and keep track
of the changes they make.  We will be using the
<a href="http://subversion.apache.org/">subversion</a> revision control
system.  It will allow you to submit your code for grading and to 
share it with TAs who can help you with problems you might encounter.

> **Attention wireless users in CEC labs!!**
>
> You must be on a *non-guest* network to access your repository.
> If your wireless network name has the word **guest** in it somewhere,
> switch to one that does not.

This part of the lab explains how to get started using eclipse with
subversion.

<aside class="sidenote">
If you have not yet installed eclipse, follow
<a href="../../Videos/Instructions/installeclipse.mp4">these instructions</a> to install it on your computer.
</aside>

1. *Start eclipse:*
	* If you are working on your own computer, launch eclipse.  You can use the default location for your workspace.

	* On CEC computers, click on the Start button and look under "All Programs", "Programming"
to start up **eclipse&nbsp;Luna**.  
	
	The first time eclipse starts, it may be slower than usual.
	You will be asked where you want your workspace to be.

	> We recommend using the **H** drive, and then creating a folder there called **workspace** in your **documents** folder.

	After eclipse starts up, click the 'X' on the "Welcome" tab at the top to close the welcome screen.

2. *Identifying your repository:*

	Open the 
	<a href="../../Videos/Instructions/usingsubversive.mp4">subversive tutorial</a> 
	in another window, and follow
	the steps given there to specify a subversion repository.  


	> When you
	get to the point of entering the full and complete location
	of the repository, copy and paste the line below as the repository's URL:
	>
	> **<FONT SIZE="-1"><FONT COLOR="blue">YYYY</FONT></FONT>/cse131_fl15/java**
	>
	> After pasting:
	>
	> Change <FONT COLOR="blue">YYYY</FONT> to your WUSTL key, 
	*using lower case letters only*.  Include all punctuation that
	is normally part of your WUSTL key.
	>
	> If you would normally type in the WUSTL key shown on the left, you need
	to use the version shown in blue on the right to access your repository:
	> Examples:
	>
	> * RonKCytron &rarr; <FONT COLOR="blue">ronkcytron</FONT>
	> * ima.StudeNt &rarr; <FONT COLOR="blue">ima.student</FONT>
	> * Queen.Mary.4 &rarr; <FONT COLOR="blue">queen.mary.4</FONT>

	Although only you can access your repository, you should generally keep your
	WUSTL key a secret.

3. OK, click Finish to get the repository location validated.

4. When you are prompted for a username and password, supply your

	* WUSTL key username (again, all letters must be lower-case)
	* WUSTL key password


	> Your WUSTL key username and password are what you use to log
	> into WebSTAC for registration.  These credentials may be different than those
	> required to log into a lab computer.
	> 
	> *Be careful!* If you provide a bad password repeatedly,
	> your account will get locked out after a small number of such attempts. 
	> So be sure to follow
	> these instructions carefully and ask if things are not working.


5. 
	Continue
	following the rest of the instructions in the
	<a href="../../Videos/Instructions/usingsubversive.mp4">subversive tutorial</a> 
	so that you are back in the Java view and have the project available for
	your use.  

	> Those instructions next ask you to Check Out a copy of the
	> repository location so you can do the work for this lab.
	> Be *sure* to right click on the whole project (your name and ID number)
	> to do the checkout.  Do NOT click on things below that like **labs**.

6. *Look at your project:*
	If you are back in the Java view, then
	you should see a project in the Package Explorer at the left side of the eclipse window. The name of the project is the hyphenation of your last name and
	your student ID number.

7. *Look at the source code:*

	<aside class="sidenote">
	Throughout the semester,
	this is where you should look for the files you need to complete your assignments.

	Supporting software is placed in other source folders, such as **labsupport**.
	You are welcome to look at any code, but to keep the number of files managable, we'll place lab code in your
	**labs** source folder in your repository. 
	</aside>

	1. In the Package Explorer, expand the project by clicking on the '+' sign 
	or little triangle to the left of the project name.
	You will see that it contains an entry for **labs**, which is
	a folder that contains packages of code.

	2. Expand the **labs** folder (click on its '+' sign or triangle).
	3. Each lab you complete is given to you in a separate <I>package</I>.
	For this lab, expand the **lab0** package.
	4. Double-click the file named **RobotController.java** to open it.
	Look at the source code in the middle window. 
	You'll see instance variables, methods, and a main method.  

	> If what you see makes sense to you already, you may not need to take this class.

	For now, you don't need to understand what you see.  We just want the computer to run this program.

8. *Run the program:* Right click on **RobotController.java** and select
	*Run as -> Java application*. In the bottom right corner, you will see a * GO * button.  
	Click the *Go *.  The program should create a robot, and you should see the robot start to move.

9. 
	*Modifying programs:*
	In general, before you can execute a Java program, it must first be compiled (translated)
	into *byte code* that can be executed by the Java interpreter. A program
	that performs this translation is called a <I>compiler</I>. 
	Eclipse checks your files to see if they will compile, and it marks
	errors so you can correct them.
	If you position the
	mouse cursor over an error icon or a red underline,
	a description of the error will be displayed.

	Try typing some random text into the **RobotController.java** file to introduce an error.  You
	should see an error icon appear at the left.  Note that sometimes the
	error message appears on the line *after* the error.  At the bottom of
	the window, you'll see **console** and **problems**.  The console window shows any printed output of the program.  If you click on **problems** you'll see a list of the errors, and clicking on an item in the list will take you to the location of that error in the Java file so you can correct it.

	Undo your typing, or close (without saving) and re-open the file.
10. 
	*Make the robot do your bidding:*
	So your real assignment here is
	to provide some instructions so that the robot navigates from 
	from CEC to your instructor's office.  

	1. 
		Open up the **RobotInstructions** file, which is also in your **lab0** package.
		This is where the commands to move the robot are written. 

		> You now have two files open in eclipse:
		> 
		> * **RobotController.java** is the <i>main</i> file. You always run the application using the instructions given
		> above for this file.
		> * **RobotInstructions.java** is the file that contains code directing the robot to move.   This is the file
		> you will modify to change the robot's behavior.

	2. Modify some of the robot instructions, and re-run the program, to gain an understanding of how the
	instructions in this file affect the robot's behavior.

		> Really?  We're asking you to program even though you probably don't much about Java yet?
		> 
		> Well, yes.  
		> 
		> Even after you have learned much about Java, programming, and computer science, you may come across some
		> code that you want to modify to change its behavior.  This takes some courage, but see if you can figure out what
		> the robot's instructions do, based on observing the robot and reading the code.  Make some changes to the code to test
		> your hypotheses.  Soon you will have the robot doing your bidding.

	3. 
	Once you have the hang of it, try to have the robot make its way from CEC to the instructor's office.

		> *Tip:* Since RobotController.java was the program most recently run,
		> you can run it again simply by clicking the green arrow on the toolbar at the top.  

		Each time it runs, you should see the results of your most recent modifications.


		> If you pause with the mouse over a location on the map, the x,y
		> coordinate of your mouse location will be shown on the screen.

	4. 
		After 20 minutes of successful robot navigation,
		*don't spend much more time* trying to get the robot to go exactly the right way.
		Get comfortable with controlling it for a bit, and then you can have it plow
		through the walls, juggernaut style.   That's a bit easier and a lot more fun.

		Also, feel free to walk the route yourself, and stop by the the instructor's office to say hello sometime.
		Visitors are usually treated to soda and candy.

11. *Commit your work:*

Now that you have modified a file, take a look at the Package Explorer window and you
should see some **&gt;** signs next to the files that have been changed.
The **&gt;** sign warns you that you have modified files that have not been
committed back to the repository.  The files 
won't be visible by TAs or the instructor until you *commit* them.

To commit them, locate the name of your project (your name and ID) in the package explorer window of eclipse.
Right (control) click on that, go down to *Team...* and select *Commit*.  You should see activity
reflective of elipse sending your modified code back to the repository.

> *Tip:*  It is good practice to commit your code often.  You can always recover any previously committed version
> of your software.  Committing your code allows you to work on the code from a different computer, and ensures that
> you do not lose your work should your computer fail.






You now know the basic steps to create projects, as well as open, edit, compile, and run Java programs in eclipse.  You should also have a basic understanding of sequential execution and how to call methods on ojects.
*Please ask if you have questions about any of this.*





## Anatomy of a Java File, Overview of the Course

OK so you can program a robot now to go where you want it to go.  How does this fit into the rest of the course?
Based on the instructor's presentation, you will next complete a simple online activity (in pairs if you like), with the
goal of understanding the anatomy of a Java file.

The course will then proceed from the most simple aspects of programming in Java to the more complex parts.


> * The online activity can be found <a href="Lab/Description/ClickQuiz.shtml">here</a>.
> * Try it (in pairs if you like) until you get it completely right.  
> * Be prepared to identify the anatomy of a Java file
> when the TA checks you off.


##Course Information

Take some time to peruse the web site. Are you clear about
course policies concerning grading and attendance?

* How do studios count toward your grade?
* How do labs count toward your grade?
* What happens if you cannot turn in a lab on time?
* How many extension points do you need to earn an A?
* What are the possible consequences of inappropriate collaboration?



##Submitting your work


* Make sure your work is committed before asking a TA to sign you off
* The green block you see below appears at the end of any studio or lab assignment for which you need a TA to
clear you.  
* Please type in the requested information before you flag down a TA.

The information logged by this activity will result in a blackboard grade being posted for your assignment,
but that grade may not show up for a week or so.

You should have already done this, but just in case,
be sure you have committed your work back to the repository as follows:

> 1. Right click on the project (your name-ID) in the Package Explorer
> 2. Drag down to *Team*....*Commit....*
> 3. You can fill in some comment there &mdash;  be creative.
> 4. Click OK to cause your changes to be saved in the repository.
