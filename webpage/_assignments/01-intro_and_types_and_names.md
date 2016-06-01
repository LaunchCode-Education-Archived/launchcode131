</hr>
* In this lab you must create and code a simple program that prints
nutritional information about food.  You have done similar activities
in studio.
  * Review or reference the [studio] (studio.html "studio 01") exercises
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

  | Variable        | Value                                              |
  | --------------- | -------------------------------------------------- |
  | `name`          | The name of this food, provided as a `String`      |
  | `carbs`         | The number of grams of carbohydrates in this food  |
  | `fat`           | The number of grams of fat in this food            |
  | `protein`       | The number of grams of protein in this food        |
  | `statedCals`    | The number of calories stated on this food's label |

  For example, the values for a Snicker bar are:

  | Variable        | Value         |
  | --------------- | ------------- |
  | `name`          | Snickers      |
  | `carbs`         | 34.5          |
  | `fat`           | 13.6          |
  | `protein`       | 4.3           |
  | `statedCals`    | 271           |

  At this point, you should be thinking about the data types you would use to represent each of the above
  items.  We tell you that the **name** is a `String`, but what about the other inputs?
  The above example is provided to inspire your thinking.
  To accept the inputs, use the same code we used in studio.  
  Just after the `main` method declaration, you should have the line:

  `ArgsProcessor ap = new ArgsProcessor(args);`

  This will likely show an error because eclipse doesn't know about `ArgsProcessor`.  Do you remember how to fix this?  Ask if you need help.

  After that, you should initialize your inputs using code modeled after the code you read and wrote in [studio] (studio.html "studio 01").

  Eclipse will help you out here:  If you have typed `ap.` and pause, eclipse will show you the possible
  ways of continuing.  For example, `nextInt(String)` is one way to keep going.  That call will prompt the user for an `int` displaying the provided `String` as the prompting message.

3. Arrange for your program to produce output such as the following:

  `Snickers has
    34.5 grams of carbohydrates = 138.0 Calories
    13.6 grams of fat = 122.4 Calories
    4.3 grams of protein = 17.2 Calories

    This food is said to have 271 (available) Calories.
    With 6.6 unavailable Calories, this food has 1.65 grams of fiber

    Approximately
      50.9% of your food is carbohydrates
      45.2% of your food is fat
      6.3% of your food is protein

    This food is acceptable for a low-carb diet?  false
    This food is acceptable for a low-fat diet?  false
    By coin flip, you should eat this food?   true`

    **Notes:**
    * There are 4 Calories in a gram of carbohydrates or protein.
    * There are 9 Calories in a gram of fat.
    * The stated caloric content of some foods may be less than the value you obtain by adding the calories due to carbohydrates, fat, and protein. This is because some of the carbohydrates may be dietary fiber, and as such they are unavailable for your body to use as energy.

        > In the above example, based on carbohydrates, fat, and protein, the food contains 277.6 Calories.
        > However, the label claims a modest 271 Calories. Thus, 6.6 Calories are unavailable and are   attributed to dietary fiber.
        >
        > From this we can compute *6.6 / 4 = 1.65 grams* of the stated carbohydrates are fiber.

    * The percentages shown in the output above feature at most a single digit of precision after the decimal point.

        > You must arrange for your percentages to exactly this precise.
        > This will take some thought, but it can be done using what is taught in the book: `int` operations, `double` operations, casting, and `Math.round(…)`.
        > You are **not** allowed to use `printf` or other functions you may find that would accomplish this task. You have to figure out how to do it using what you have learned in this module.

    * The following are taken from this **online resource**:
      * A food is a *low-carb* food if no more than 25% of its calories come from carbohydrates.
      * A food is a *low-fat* food if no more than 15% of its calories come from fat.

        > For the output lines whose conclusions are `true` or `false`, define an appropriately named `boolean` variable in your program and set it equal to the expression that determines its value.
        > When you print the value it will print as `true` or `false`.

    * To accomplish the coin flip, recall the random-number generator as described in the text. Define a `boolean heads` and assign its value by comparing a random number with `0.5`.

    * Your strings can include the character sequences `\t` and `\n`, which have the effect of inserting a tab and a newline, respectively. A *tab* will cause the output to shift right to a predetermined position. A *newline* will end the current line of output and begin a new line.

4. To demo your lab, find some food labels (for real or on the web) and run your program with the appropriate values for those foods.
</hr>

###What went wrong? (not required, for your amusement only)
I know of a person who took Chemistry and learned that a calorie of energy is required to raise the temperature of a gram of water 1 degree Centigrade.

This gave him the idea that he could lose a lot of weight by drinking ice water, as follows. His body would be called upon to raise the temperature of that cold water to 98.6 degrees Fahrenheit. So if the water starts at 40 degrees Fahrenheit, then his body must raise each gram of that water about almost 60 degrees Fahrenheit, or about 33 degrees Centigrade.

A gram of water is a mL in volume. Thus, each liter of water should cause the body to expend 33,000 Calories. There are about 3,500 Calories in a pound of fat, so drinking the liter of water should cause about 10 pounds of fat to burn.

This person tried this but did not notice much fat loss at all. Can you explain the epic fail here?
</hr>

##Submitting your work (read carefully)

* You must commit all of your work to your repository. It's best to do this from the top-most level of your repository, which bears your name and student ID.
* You must demo the commited work to a TA. Make sure the TA knows that your demo is for credit at this point.
* Follow the directions below to have your demo for this work recorded.
