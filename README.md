# group_3_pp2 README 

### Summary:
The program was created by Max Bickley, Jacob Redmon, Chad Critchelow, and Ben Torrance.

This project is a simple Compiler where the ANT file takes a .g4 file, and constructs both parsers and lexers for the java compiler file. Furthermore, the ANT file is capable of building and compiling all the necessary files and deleting all files except for the java files that pertain to the compiler itself, ClassyCompiler, Mylistener, and Errorlistener. For the compiler itself, it reads three arguments; source, the name of the file to read, and the name of the file to write, runs through the lexers, the parser, and Mylistener, creates a new file, and writes the results derived from the Mylistener in the new file. However, in the event that an error occurs, the program will print the row and column where the error occured and halt the program's execution.

This project does not currently support bytecode but it will be expanded to support it within the near future.

### How To Compile the Compiler with ANT:
code examples of how to compile the compiler with ANT;

     This script will enable you to convert a program from KnightCode to Java Source Code.
     build-grammar - ant will parse the grammar files.
     clean-grammar - ant will clean and delete all relevant files.
     compile-grammar - ant will compile the java files.
     compile-knightcode - ant will compile the java files but will not compile the parser files.
     run-compiler - ant will attempt to run the compiler. Used for basic test runs.

Assuming that you imported build.xml, knightcode.g4, ClassyCompiler.java, Mylistener.java, and Errorlistener.java, everything can be run by following the commands listed above. However, there are five things of note;
#1 build-grammar runs clean-grammar
#2 compile-grammar, compile-knightcode, and run-compiler require build-grammar to be run prior to their use.
#3 compile-grammar runs compile-knightcode automatically.
#4 run-compiler runs compile-grammar automatically.
#5 run-compiler only tests the first program. Other programs must be manually entered.

### How To Run the Compiler to Compile a .kc File:
Once you have run the compile-grammar function, all you need to do is type "java ClassyCompiler source" before typing the name of the program to run and the name of the program to create. Keep in mind however, that it is crucial that you specify the file types after entering each file name or else the program will not run correctly. Here is an example of a successful program;

      java ClassyCompiler source Program1.kc Copy.java
      
Unfortunately, the compiler does not currently support byte code. So, the new file will need to be compiled independently.

      javac Copy.java
      
Once complete, the new program will be ready to run.

### Project Log:

Date | Team Member(s) | Hours | Summary of work session

October 3rd, 2019 | Max Bickley, Jacob Redmon, Chad Critchelow | Thirty minutes |  We created the grammar file, we created the Github account, and we established the contributors in the Github account.

October 4th, 2019 | Chad Critchelow | Thirty minutes | Copied over the four example KnightCode programs from the PDF to the server and pushed them to github. All KnightCode programs end with the ".kc" extension.

October 7th, 2019 | Max Bickley | Thirty minutes| Resolved the issue with ESC that prevented the grammar file from running.

October 8th, 2019 | Max Bickley | Two hours | Worked on the the ant file.

October 9th, 2019 | Max Bickley | Two hours |Completed the ant file.

October 10th, 2019 | Jacob Redmon | Five minutes | Added the proper build directories to knightcode.

October 13th, 2019 | Max Bickley | 2.5 hours | Worked towards setting up the framework for Mylistener.

October 13th, 2019 | Max Bickley | 2 hours | Successfully determined how to utilize parse trees and wrote each @Override response currently listed in Mylistener.

October 14th, 2019 | Max Bickley | 3 hours | Worked towards resolving errors regarding the ELSE statement being parsed before statements that were intended to be contained within the ELSE.

October 15th, 2019 | Max Bickley | 3 hours | Resolved an error where the Mylistener cannot determine whether or not to read input as string or integers. Resolved the error where all ELSE statements would be parsed before statements that were intended to be contained within the ELSE.

October 16th, 2019 | Max Bickley | 15 minutes | Modified the ant file and submitted it to Github.

October 20th, 2019 | Max Bickley | 3 hours | Finished the Mylistener, uploaded the Mylistener to Github, added terminators as a token in knightcode.g4, corrected the error where the ADD token had a ',' instead of a '+', and modified build.xml so that the compiler could store three arguments instead of only two.

October 21st, 2019 | Max Bickley | 8 minutes | Corrected a minor error in knightcode.g4 regarding the IF and ELSE statements where ENDIF recurred. Made a minor modification to Mylistener to remove an unnecessary method override.

October 22nd, 2019 | Max Bickley | 25 minutes | Replaced the method of determining where to parse read input as an Int or String with a much more robust method in Mylistener.

October 22nd, 2019 | Jacob Redmon | 1 hour | Researched methods of error handling and decided on a method to use in the project.

October 23rd, 2019 | Jacob Redmon | 1.5 hours | Cleaned and added the compiler, Mylistener and ErrorListener, updated README with proper formatting and documentation.

October 23rd, 2019 | Jacob Redmon | 1.5 hours | Created 4 knightcode files, made Errorlistener simpler

October 23rd, 2019 | Max Bickley & Ben Torrance | 4 hours | Created the ClassyCompiler.java file with comments and tested the compiler on the 8 knightcode programs. All test files should now translate and compile.

October 24th, 2019 | Max Bickley | 1 hour | Fixed a bunch of minor errors and improved the README file.

October 24th, 2019 | Chad Critchelow and Jacob Redmon | 1 hour | Reviewed the code, tested the programs on the server, and spell-checked the README.

October 26th, 2019 | Max Bickley | 10 minutes | Altered the initial condition in ClassyCompiler to distinguish source from byte code, resolved the issue with nested if statements and loops, and enabled greater than or equal, less than or equal, and not equal statements.
