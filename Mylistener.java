/**
* This is the Mylistener for the compiler for the KnightCode programming language.
* @author Max Bickley
* @author Chad Critchelow
* @author Jacob Redmon
* @author Ben Torrance
* @version 1.0
* Programming Project Two
* CS322 - Compiler Construction
* Fall 2019
**/

// Imports all of the necessary tools and functions for the program.
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

// Opens the class and extends it to knightcodeBaseListener
public class Mylistener extends knightcodeBaseListener {
	
	public static HashMap<Integer, String> parsed = new HashMap<Integer, String>();
	public static HashMap<String, String> variable1 = new HashMap<String, String>();
	public static HashMap<String, String> variable2 = new HashMap<String, String>();
	ClassyCompiler Classy = new ClassyCompiler(); //Allows the user's chosen file name to be imported.
	HashMap<Integer, String> Userchoice = Classy.sender(); // Actually imports the file name that the user chose.	
	public static String conversion = "";
	public static String placer = "";
	public static String catcher;
	public static String word1 = "";
	public static String word2 = "";
	public static String word3 = "";
	public static int place = 0;
	public static int numholder = 1;
	
	//Overrides a method. This remains consistent whenever this code occurs.
	@Override public void enterEveryRule(ParserRuleContext ctx) {
		placer = ctx.getText(); //Grabs the text from the parser and sorts it into given @Overrides below.
		placer = placer.replace(";",""); //Removes ; so that they can be added back properly later. It also prevents the Mylistener from having to check every time for it.
	}// end enterEveryRule method
	
	//This method loads the imports, file name, and opening bracket into parsed.
	@Override public void enterFile(knightcodeParser.FileContext ctx) {
		//In general, parsed.put stores parsed code into a hashmap that will be sent back to the compiler.
		parsed.put(place, "import javax.swing.*;"); 
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
			parsed.put(place, "{"); //Adds the opening brackets.
			place = place + 1;
        }// end while
    }// end enterFile override
	
	//Declares integers and String.
	@Override public void enterDeclare(knightcodeParser.DeclareContext ctx) { 
		Pattern pattern2 = Pattern.compile("INTEGER[A-Z]?[a-z]*[0-9]*(:=[0-9])?"); //matches integers found. Note that they can be given values here.
		Matcher match2 = pattern2.matcher(placer);
		
		while (match2.find())
        {
			conversion = match2.group();
			conversion = conversion.replace("INTEGER", "");
			Pattern pattern9 = Pattern.compile("[A-Z]?[a-z]*[0-9]*"); //matches integers found. Note that they can be given values here.
			Matcher match9 = pattern9.matcher(conversion);
			
			while (match9.find())
			{
				word3 = match9.group();
				variable1.put(word3, " = Integer.parseInt(");
				variable2.put(word3, "2);");
			}// end inner while
			
			conversion = conversion.replace(":=", " = ");
			conversion = "public static int " + conversion + ";";
			parsed.put(place, conversion);
			place = place + 1;
		}// end while
		
		Pattern pattern3 = Pattern.compile("STRING[A-Z]?[a-z]*[0-9]*(:=\\S+)?"); //matches String found. Note that they can be given values here if they are in brackets.
		Matcher match3 = pattern3.matcher(placer);
		
		while (match3.find())
        {
			conversion = match3.group();
			conversion = conversion.replace("STRING", "");
			Pattern pattern10 = Pattern.compile("[A-Z]?[a-z]*[0-9]*"); //matches integers found. Note that they can be given values here.
			Matcher match10 = pattern10.matcher(conversion);
			
			while (match10.find())
			{
				word3 = match10.group();
				variable1.put(word3, " = ");
				variable2.put(word3, "2;");
			}// end inner while

			conversion = conversion.replace(":=", " = ");
			conversion = "public static String " + conversion + ";";
			parsed.put(place, conversion);
			place = place + 1;
		}// end while
	}// end enterDeclare override
	
	//Handles the public static void main aspect of the code as well as the open bracket that follows. 
	//There is no condition because this always triggers, it triggers in the same place, and it triggers exactly once.
	@Override public void enterBody(knightcodeParser.BodyContext ctx) { 
		parsed.put(place, "public static void main(String[] args)");
		place = place + 1;
		parsed.put(place, "{");
		place = place + 1;
	}// end enterBody override
	
	//Handles the opening of each loop including the opening bracket at the end.
	@Override public void enterLoop(knightcodeParser.LoopContext ctx) { 
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
			}// end while
	}//end enterLoop override
	
	//Handles the IF THEN statement. Due to sorting difficulties, it was separated from the ELSE method.
	@Override public void enterDecision(knightcodeParser.DecisionContext ctx) { 
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
		}// end while
	}// end enterDecision override
	
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
			}// end while
	}// end enterOther override
	
	//Handles any situation where a program needs to read user input.
	@Override public void enterRead(knightcodeParser.ReadContext ctx) { 
		Pattern pattern6 = Pattern.compile("READ\\S?[A-Z]?[a-z]+\\S?");
        Matcher match6 = pattern6.matcher(placer);
		
		while (match6.find())
		{
			conversion = match6.group();
			conversion = conversion.replace("READ", "");
			parsed.put(place, "Scanner scan" + numholder + " = new Scanner(System.in);"); 
			place = place + 1;
			parsed.put(place, "String " + conversion + "2 = scan" + numholder + ".nextLine();"); 
	
			word1 = (String) variable1.get(conversion);
			word2 = (String) variable2.get(conversion);

			place = place + 1;
			numholder = numholder + 1;
			//Scans the input prompt for key phrases that indicate that the user is going to input a number. If any phrases match, the input will be an integer.
			parsed.put(place, "" + conversion + "" + word1 + "" + conversion + "" + word2 + ""); 
			place = place + 1;
		}// end while
	}// end enterRead override
	
	//Stores the System.out.Println function for later use.
	//Note, that this function always uses System.out.println since there is no way of distinguishing it from System.out.print in knightcode.
	@Override public void enterPrint(knightcodeParser.PrintContext ctx) { 
   		Pattern pattern5 = Pattern.compile("PRINT\\S{0,2}(([a-z]||[0-9])+\\s?)+\\S?\\s*\\S?");
        Matcher match5 = pattern5.matcher(placer);
		
		while (match5.find()) //Stores the input into the variable word in order to perform the test within enterRead above.
        {
			conversion = match5.group();
			conversion = conversion.replace("PRINT", "");
			conversion = "System.out.println(" + conversion + ");";
			parsed.put(place, conversion);
			place = place + 1;
		}// end while
	}// end enterPrint override
	
	//Assigns values to variables.
	@Override public void enterSetvar(knightcodeParser.SetvarContext ctx) {
	Pattern pattern4 = Pattern.compile("SET\\S?[a-z]*[0-9]*:=(\\W.+\\W)||((\\+||\\-||\\/||\\*)(\\S?[a-z0-9]+))*"); //This pattern supports basic arithmetic within the assignment.
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
			}// end while
	}// end enterSetvar
	
	//Closes loops once the program reaches ENDWHILE.
	//Lacks a condition because it is a closing method.
	@Override public void exitLoop(knightcodeParser.LoopContext ctx) { 
		parsed.put(place, "}"); 
		place = place + 1;
	}// end exitLoop override
	
	//Closes the IF statement once the program reaches ENDIF.
	//ENDIF is capable of closing both IF and ELSE.
	//Lacks a condition because it is a closing method.
	@Override public void exitDecision(knightcodeParser.DecisionContext ctx) { 
		parsed.put(place, "}"); 
		place = place + 1;
	}// end exitDecision override
	
	//Closes loops once the program reaches the end of the body.
	//Lacks a condition because it is a closing method.
	@Override public void exitBody(knightcodeParser.BodyContext ctx) {
		parsed.put(place, "}");
		place = place + 1;
	}// end exitBody override
	
	//Closes the class once the file is finished.
	//Lacks a condition because it is a closing method.
	@Override public void exitFile(knightcodeParser.FileContext ctx) { 
 		parsed.put(place, "}");
	}// end exitFile override
	
	//Returns the parsed HashMap to any file that calls sender() so that the parsed code can be utilized by other programs.
	public static HashMap<Integer,String> sender(){
        return parsed;
    }// end sender
	
}//end Mylistener
