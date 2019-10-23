import org.antlr.v4.runtime.*;
//import org.antlr.v4.runtime.misc.Nullable;

import java.util.*;

public class ErrorListener extends BaseErrorListener{

	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e){

		List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
		Collections.reverse(stack);
		System.err.println("rule stack "+stack);
		System.err.println("line "+line+":"+charPositionInLine+" at" + offendingSymbol + ": "+msg);
		//System.err.println(msg);
		//System.err.println("SON - YOU DONE MESSED UP!");
		System.exit(1);
	}


}
