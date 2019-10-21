//Imports all of the necessary tools and functions for the program.
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

//Opens the class and extends it to knightcodeBaseListener
public class Mylistener extends knightcodeBaseListener
 {	
	public static HashMap<Integer, String> parsed = new HashMap<Integer, String>();
	ClassyCompiler Classy = new ClassyCompiler(); //Allows the user's chosen file name to be imported.
	HashMap<Integer, String> Userchoice = Classy.sender(); // Actually imports the file name that the user chose.
	public static String conversion = "";
	public static String placer = "";
	public static String catcher;
	public static String word = "";
	public static int place = 0;
	public static int numholder = 1;
	@Override public void enterEveryRule(ParserRuleContext ctx) {//Overrides a method. This remains consistent whenever this code occurs.
	placer = ctx.getText(); //Grabs the text from the parser and sorts it into given @Overrides below.
	placer = placer.replace(";",""); //Removes ; so that they can be added back properly later. It also prevents the Mylistener from having to check every time for it.
	}
	@Override public void enterFile(knightcodeParser.FileContext ctx) {//This method loads the imports, file name, and opening bracket into parsed.
	parsed.put(place, "import javax.swing.*;"); //In general, parsed.put stores parsed code into a hashmap that will be sent back to the compiler.
	//Code sent back to the compiler will be used to write in the new file.
	place = place + 1; //Every place = place + 1 incremements the parsed HashMap and incdiccates a new line of code.
	parsed.put(place, "import java.io.*;");
	place = place + 1;
	parsed.put(place, "import java.util.*;");
	place = place + 1;
	//In general, pattern matching sequences match the code taken from the parse tree against predetermined text and initiate actions while the conditions are fulfilled.
	Pattern pattern1 = Pattern.compile("PROGRAM\\S+(DECLARE||BEGIN)"); //The pattern to match.
 	Matcher match1 = pattern1.matcher(placer);
	while (match1.find())
                        {
			//The body of the while loop. Bodies, in general, replace unwanted code with the desired code.
			//This section manages the file name the opening brackets.
			catcher = (String) Userchoice.get(2);
			catcher = catcher.replace(".java", ""); //Removes .java from the text inside the file
			parsed.put(place, "public class " + catcher + "");
			place = place + 1;

			parsed.put(place, "{");//Adds the opening brackets.
			place = place + 1;
                        }
        }
	@Override public void enterDeclare(knightcodeParser.DeclareContext ctx) { //Declares integers and String.
	Pattern pattern2 = Pattern.compile("INTEGER[A-Z]?[a-z]*[0-9]*(:=[0-9])?"); //matches integers found. Note that they can be given values here.
	Matcher match2 = pattern2.matcher(placer);
	while (match2.find())
                        {
			conversion = match2.group();
			conversion = conversion.replace("INTEGER", "");
			conversion = conversion.replace(":=", " = ");
			conversion = "public static int " + conversion + ";";
			parsed.put(place, conversion);
			place = place + 1;
			}
	Pattern pattern3 = Pattern.compile("STRING[A-Z]?[a-z]*[0-9]*(:=\\S+)?"); //matches String found. Note that they can be given values here if they are in brackets.
	Matcher match3 = pattern3.matcher(placer);
	while (match3.find())
                        {
			conversion = match3.group();
			conversion = conversion.replace("STRING", "");
			conversion = "public static String " + conversion + ";";
			parsed.put(place, conversion);
			place = place + 1;
			}
	}
	@Override public void enterBody(knightcodeParser.BodyContext ctx) { //Handles the public static void main aspect of the code as well as the open bracket that follows. 
	//There is no condition because this always triggers, it triggers in the same place, and it triggers exactly once.
	parsed.put(place, "public static void main(String[] args)");
	place = place + 1;
	parsed.put(place, "{");
	place = place + 1;
	}
	@Override public void enterLoop(knightcodeParser.LoopContext ctx) { //Handles the opening of each loop including the opening bracket at the end.
	Pattern pattern7 = Pattern.compile("WHILE\\S+(>||<||=||<>||>=||<=)\\S+DO");
	Matcher match7 = pattern7.matcher(placer);
	while (match7.find())
                        {
			conversion = match7.group();
			conversion = conversion.replace("WHILE", "while (");
			//A simple means of closing the condition.
			conversion = conversion.replace("DO", ")");
			//Replaces all the comparisons with those in the target code.
			conversion = conversion.replace(">", " > ");
			conversion = conversion.replace("<", " < ");
			conversion = conversion.replace("=", " = ");
			conversion = conversion.replace("<>", " != ");
			conversion = conversion.replace(">=", " >= ");
			conversion = conversion.replace("<=", " <= ");
			parsed.put(place, conversion); 
			place = place + 1;
			parsed.put(place, "{"); 
			place = place + 1;
			}
	}
	@Override public void enterDecision(knightcodeParser.DecisionContext ctx) { //Handles the IF THEN statement. Due to sorting difficulties, it was separated from the ELSE method.
	Pattern pattern8 = Pattern.compile("IF[A-Z]?([a-z]||[0-9])+(>||<||=||<>||>=||<=)\\S+THEN");
	Matcher match8 = pattern8.matcher(placer);
	while (match8.find())
                        {
			conversion = match8.group();
			//Replaces unwanted code with wanted code and loads it into parsed.
			conversion = conversion.replace("IF", "if (");
			conversion = conversion.replace("THEN", ")");
			conversion = conversion.replace(">", " > ");
			conversion = conversion.replace("<", " < ");
			conversion = conversion.replace("=", " = ");
			conversion = conversion.replace("<>", " != ");
			conversion = conversion.replace(">=", " >= ");
			conversion = conversion.replace("<=", " <= ");
			parsed.put(place, conversion); 
			place = place + 1;
			parsed.put(place, "{"); 
			place = place + 1;
			}
	}
	@Override public void enterOther(knightcodeParser.OtherContext ctx) { //Manages the ELSE in any IF statements that have an ELSE.
	Pattern pattern11 = Pattern.compile("ELSE(IF[A-Z]?([a-z]||[0-9])+(>||<||=||<>||>=||<=)\\S+THEN)?");
	Matcher match11 = pattern11.matcher(placer);	
	while (match11.find())
                        {
			//Closes the previous IF.
			parsed.put(place, "}"); 
			place = place + 1;
			//Inserts and else.
			parsed.put(place, "else"); 
			place = place + 1;
			//Opens the else statement.
			parsed.put(place, "{"); 
			place = place + 1;
			}
	}
	@Override public void enterRead(knightcodeParser.ReadContext ctx) { //Handles any situation where a program needs to read user input.
	Pattern pattern6 = Pattern.compile("(PRINT.*)?READ\\S?[A-Z]?[a-z]+\\S?");
        Matcher match6 = pattern6.matcher(placer);
	while (match6.find())
			{
			conversion = match6.group();
			conversion = conversion.replace("READ", "");
			parsed.put(place, "Scanner scan" + numholder + " = new Scanner(System.in);"); 
			place = place + 1;
			parsed.put(place, "String " + conversion + "2 = scan" + numholder + ".nextLine();"); 
			place = place + 1;
			numholder = numholder + 1;
			//Scans the input prompt for key phrases that indicate that the user is going to input a number. If any phrases match, the input will be an integer.
			if (word.contains("number:")||word.contains("repetitions:")||word.contains("How many")||word.contains("How much")||word.contains("count")||word.contains("amount"))
			{
			parsed.put(place, "" + conversion + " = Integer.parseInt(" + conversion + "2);"); 
			place = place + 1;
			}
			//If there is no input prompt or if none of the above phrases are found, the input will be registered as String.
			else
			{
			parsed.put(place, "" + conversion + " = " + conversion + "2;"); 
			place = place + 1;
			}
			}
	}
	@Override public void enterPrint(knightcodeParser.PrintContext ctx) { //Stores the System.out.Println function for later use.
   	Pattern pattern5 = Pattern.compile("PRINT\\S{0,2}(([a-z]||[0-9])+\\s?)+\\S?\\s*\\S?");
        Matcher match5 = pattern5.matcher(placer);
	while (match5.find())
                        {
			conversion = match5.group();
			conversion = conversion.replace("PRINT", "");
			//Note, that this function always uses System.out.println since there is no way of distinguishing it from System.out.print in knightcode.
			conversion = "System.out.println(" + conversion + ");";
			parsed.put(place, conversion);
			place = place + 1;
			//Stores the input into the variable word in order to perform the test within enterRead above.
			word = match5.group();
			}
	}
	@Override public void enterSetvar(knightcodeParser.SetvarContext ctx) { //Assigns values to variables.
	Pattern pattern4 = Pattern.compile("SET\\S?[a-z]*[0-9]*:=((\\+||\\-||\\/||\\*)\\S?[a-z0-9]+)*"); //This pattern supports basic arithmetic within the assignment.
        Matcher match4 = pattern4.matcher(placer);
	while (match4.find())
                        {
			conversion = match4.group();
			//Replaces the unwanted text with the target text.
			conversion = conversion.replace("SET", "");
			conversion = conversion.replace(":=", " = ");
			conversion = conversion.replace("+", " + ");
			conversion = conversion.replace("-", " - ");
			conversion = conversion.replace("*", " * ");
			conversion = conversion.replace("/", " / ");
			conversion = "" + conversion + ";";
			parsed.put(place, conversion); 
			place = place + 1;
			}
	}
	@Override public void exitLoop(knightcodeParser.LoopContext ctx) { 
	//Closes loops once the program reaches ENDWHILE.
	//Lacks a condition because it is a closing method.
	parsed.put(place, "}"); 
	place = place + 1;
	}
	@Override public void exitDecision(knightcodeParser.DecisionContext ctx) { 
	//Closes the IF statement once the program reaches ENDIF.
	//ENDIF is capable of closing both IF and ELSE.
	//Lacks a condition because it is a closing method.
	parsed.put(place, "}"); 
	place = place + 1;
	}
	@Override public void exitBody(knightcodeParser.BodyContext ctx) {
	//Closes loops once the program reaches the end of the body.
	//Lacks a condition because it is a closing method.
	parsed.put(place, "}");
	place = place + 1;
	}
	@Override public void exitFile(knightcodeParser.FileContext ctx) { 
	//Closes the class once the file is finished.
	//Lacks a condition because it is a closing method.
 	parsed.put(place, "}");
	}
	//Returns the parsed HashMap to any file that calls sender() so that the parsed code can be utilized by other programs.
	public static HashMap<Integer,String> sender()
        {
        return parsed;
        }
    }
