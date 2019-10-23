/**
* This is the compiler for the KnightCode programming language.
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
import java.io.FileInputStream;
import java.io.InputStream;

public class KCCompiler {

    public static void main(String[] args) throws Exception {
    
        String inputFile = null; 
        if ( args.length>0 ) 
		    inputFile = args[0];
          
        InputStream is = System.in;
        if ( inputFile!=null ) 
		    is = new FileInputStream(inputFile);
        
        ANTLRInputStream input = new ANTLRInputStream(is); 
        tinybasicLexer lexer = new tinybasicLexer(input); 
        CommonTokenStream tokens = new CommonTokenStream(lexer); 
        tinybasicParser parser = new tinybasicParser(tokens); 
        
	    parser.removeErrorListeners();

	    parser.addErrorListener(new ErrorListener());

	    ParseTree tree = parser.program(); // start at the program label

	    ParseTreeWalker walker = new ParseTreeWalker();
		
	    MyListener l = new MyListener();
	    walker.walk(l, tree);

	}//end main
  
}//end class
