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

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import static org.antlr.v4.runtime.CharStreams.fromFileName;


public class ClassyCompiler
{
	//Declares our variables and objects. The Userchoice hashmap will carry over the arguments obtained from the user to the Mylistener
	public static HashMap<Integer, String> Userchoice = new HashMap<Integer, String>();
	public static Mylistener Mylistener = new Mylistener();
	public static HashMap<Integer, String> parsed = Mylistener.sender();
	public static Integer Part = 0;
	public static String Decision;
	public static String convert1;
	public static String convert2;
	public static String message;
	public static String Outfile;
	public static void main(String[] args) throws Exception
				{
		
				//Adds the arguments to the Userchoice hashmap.
				Userchoice.put( 0 , args[0]);
				Userchoice.put( 1 , args[1]);
				Userchoice.put( 2 , args[2]);

                	String line = null;
			//Checks the filename argument to make sure it is not null before proceeding 
			if (args[1] != null)
                	{
			
			//Creates a File object containing the filepath to our input file.
			String FilePath = args[1];
                	File FileName = new File(FilePath);
				
			//Outfile represents the output file name
			Outfile = args[2];
			
			//Loading the file into the character stream PROGRAM and then creating a lexer to read that character stream
			CharStream PROGRAM = fromFileName(FilePath);  //load the file	
			knightcodeLexer lexer = new knightcodeLexer(PROGRAM);
				
			//Cleans out any ErrorListener's and then adds a new one to check for errors.
			lexer.removeErrorListeners();
			lexer.addErrorListener(new Errorlistener());
			
			//Divides the character stream up into tokens which are then given to the parser.
			CommonTokenStream tokens = new CommonTokenStream(lexer); //Scan stream for tokens
			knightcodeParser parser = new knightcodeParser(tokens);  //Parse the tokens
			
			//Cleans out any Error Listener's and then adds a new one to check for errors.
			parser.removeErrorListeners();
			parser.addErrorListener(new Errorlistener());
				
			ParseTree tree = parser.file();//Parse the content and get the tree
			
			//Creates new Mylistener
			Mylistener listener = new Mylistener();

			//Uses the Mylistener to walk the tree and translate the source file into java. 
			ParseTreeWalker walker = new ParseTreeWalker();
			walker.walk(listener,tree);
		
		//Checks to make sure that the Output file does not exist before trying to create and write to it.
		if (!Outfile.equals(""))
		{
		
		File newfile = new File (Outfile); // Creates File object with the output file name
					
					//Prints out line to user to indicate that it is creating the file
					System.out.println("Creating " + newfile + "...");
					
					//Creates the file
					newfile.createNewFile();
			
					//Declares an output stream called writing which will be used to write the translated program to the output file
					FileOutputStream writing = new FileOutputStream(newfile);
					
					//Loops through the parsed lines of the input file and writes them to the output file.
					for (int writer = 0; writer < parsed.size(); writer = writer + 1)
					{
						convert1 = (String) parsed.get(writer);
						writing.write(convert1.getBytes());
						writing.write(System.getProperty("line.separator").getBytes());
					}
					//Clears and closes the output file stream.
					writing.flush();
					writing.close();
		}
		}
		//Sends error if the input filename was incorrect.
		else 
		{
		System.out.println("You were supposed to enter: java, ClassyCompiler, source, the file you want to read, and the file you want to create.");
		}
	}
	//Method to send the hashmap of arguments to the Mylistener
	public HashMap<Integer,String> sender()
        {
        return Userchoice;
        }	
}
