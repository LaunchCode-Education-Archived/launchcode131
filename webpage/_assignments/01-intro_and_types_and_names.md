* In this lab you must create and code a simple program that prints
nutritional information about food.  You have done similar activities
in studio.
  * Review or reference the <a href="studio.html">studio</a> exercises
  as necessary.
* Code is distributed to you throughout the semester, so you may
  need to *update* your repository to see the files for this lab:
  * In the package explorer, right- (control-) clock on your project
  name, choose **Team&hellip;Update**, and supply your credentials.
  * In the **labs** source folder you should see a **lab1**
* Find and open the **lab1** package in the **labs** source folder.

##Nutrition Analyzer
In this lab you develop a simple tool that prints nutrition information about food. This lab is based on what you learned in studio. By completing this lab you demonstrate that you can:

* Create a Java class on your own
* Arrange for the class to take inputs of interest
* Compute output values of interest
* Produce meaningful output based on your computations


##Procedure
1. Create a **Nutrition** Java class in the **lab1** package of the **labs** source folder.
  * The **lab1** class is currently empty, but don't let that bother you.  Right (control) click on **lab1** and create a new class called **Nutrition**.
2. Arrange for your program to accept the following inputs:
  > name
  >  * The name of this food, provided as a **String**
  > carbs
  >  * The number of grams of carbohydrates in this food
  > fat
  >  * The number of grams of fat in this food
  > protein
  >  * The number of grams of protein in this food
  > statedCals
  >  * The number of calories stated on this food's label
  >
  > For example, the values for a Snicker bar are:
  > | Name          | Snickers      |
  > | ------------- |-------------  |
  > | carbs         | 34.5          |
  > | fat           | 13.6          |
  > | protein       | 4.3           |
  > | statedCals    | 271           |

  > At this point, you should be thinking about the data types you would use to represent each of the above
  items.  We tell you that the **name** is a **String**, but what about the other inputs?
  >
  > The above example is provided to inspire your thinking.
  >
  > To accept the inputs, use the same code we used in studio.  Just > after the **main** method declaration,
  > you should have the line:
  >
  > **ArgsProcessor ap = new ArgsProcessor(args);**
  >
  > This will likely show an error because eclipse doesn't know about **ArgsProcessor**.  Do you remember how to
  > fix this?  Ask if you need help.
  >
  > After that, you should initialize your inputs using code modeled after the code you read and wrote in <ahref="studio.html">studio</a>.
  >
  > Eclipse will help you out here:  If you have typed **ap.** and pause, eclipse will show you the possible
  > ways of continuing.  For example, **nextInt(String)** is one way to keep going.  That call will prompt
  > the user for an **int** displaying the provided **String** as the prompting message.

3. Arrange for your program to produce output such as the following:
