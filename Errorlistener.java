/**
* This is the Error listener for the compiler for theKnightCode programming language.
* @author Max Bickley
* @author Chad Critchelow
* @author Jacob Redmon
* @author Ben Torrance
* @version 1.0
* Programming Project Two
* CS322 - Compiler Construction
* Fall 2019
**/

import org.antlr.v4.runtime.*;
//import org.antlr.v4.runtime.misc.Nullable;

import java.util.*;

public class Errorlistener extends BaseErrorListener{

	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e){

		System.out.println("An error was encountered at line " + line + ", col " + charPositionInLine);
		System.exit(1);
		
	}// end method
	
}// end Error Listener
