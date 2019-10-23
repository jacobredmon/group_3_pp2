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

public class ClassyCompiler{

        public static String Userchoice;
        public static Mylistener Mylistener = new Mylistener();
        public static HashMap<Integer, String> parsed = Mylistener.sender();
        public static Integer Part = 0;
        public static String Decision;
        public static String convert1;
        public static String convert2;
        public static String message;
        public static String Outfile;

        public static void main(String[] args) throws Exception{
                Userchoice = args[2];
                String line = null;
                if (args[0] == "source"){
                        //Takes the input filename from the arguments array and creates a File object using that name as the path.
                        String FilePath = args[1];
                        File FileName = new File(FilePath);

                        Outfile = args[2];

                        CharStream PROGRAM = fromFileName(FilePath);  //load the file and enters the character stream into the lexer.
                        knightcodeLexer lexer = new knightcodeLexer(PROGRAM);

                        lexer.removeErrorListeners();//Cleans up any error listeners and then creates a new error listener for the lexer
                        lexer.addErrorListener(new Errorlistener());

                        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
                        knightcodeParser parser = new knightcodeParser(tokens);  //parse the tokens

                        parser.removeErrorListeners();//Cleans up any remaining error listeners and then creates a fresh error listener for the parser
                        parser.addErrorListener(new Errorlistener());

                        ParseTree tree = parser.file();//parse the content and get the tree

                        Mylistener listener = new Mylistener();

                        ParseTreeWalker walker = new ParseTreeWalker();
                        walker.walk(listener,tree);

                        if (!Outfile.equals("")){
                                File newfile = new File (Outfile);
                                System.out.println("Creating " + newfile + "...");
                                newfile.createNewFile();
                                FileOutputStream writing = new FileOutputStream(newfile);

                                for (int writer = 0; writer < parsed.size(); writer = writer + 1){
                                        convert1 = (String) parsed.get(writer);
                                        writing.write(convert1.getBytes());
                                        writing.write(System.getProperty("line.separator").getBytes());
                                }
                                writing.flush();
                                writing.close();
                        }
                }
                if (args[0] == "bytecode"){
                        System.out.println("bytecode output has not yet been implemented");
                }
                else {
                        System.out.println("You were supposed to enter: java, ClassyCompiler, source, the file you want to read, and the file you want to create.");
                }
        }
        public string sender(){
                return Userchoice;
        }
}
