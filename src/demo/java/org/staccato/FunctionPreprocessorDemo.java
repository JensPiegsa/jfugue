/*
 * JFugue, an Application Programming Interface (API) for Music Programming
 * http://www.jfugue.org
 *
 * Copyright (C) 2003-2014 David Koelle
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.staccato;

import org.staccato.functions.FunctionManager;
import org.staccato.functions.TrillFunction;

public class FunctionPreprocessorDemo 
{
	public static void main(String[] args) {
		// Each function that is added to the ParserContext is essentially a singleton, since it keeps no state.
		FunctionManager.getInstance().addPreprocessorFunction(TrillFunction.getInstance());
	    
	    // There are two rounds of Staccato parsing. The first round, "preprocessing," expands
	    // special functions and other capabilities to their Staccato representations.
	    // The FunctionPreprocessor expands preprocessor functions. Note that Staccato has
	    // preprocessor functions, which are resolved before the Staccato string is parsed,
	    // and subparser functions, which are resolved while the Staccato string is being parsed.
	    // This is demonstrating only the preprocessor.
		FunctionPreprocessor preprocessor = FunctionPreprocessor.getInstance();
		
		// Now, let's see what happens when we preprocess a string that contains a function!
		
		// The next line should print "a b c C4t D4t C4t D4t C4t D4t C4t D4t e r"
		System.out.println(preprocessor.preprocess("a b c :TRILL(Cq) e r", (StaccatoParserContext)null));
		
		// The next line should print "a b c C4t D4t C4t D4t C4t D4t C4t D4t C4t D4t C4t D4t C4t D4t C4t D4t E4t F#4t E4t F#4t E4t F#4t E4t F#4t E4t F#4t E4t F#4t E4t F#4t E4t F#4t e r"
		System.out.println(preprocessor.preprocess("a b c :TRILL(Ch Eh) e r", (StaccatoParserContext)null));
		
		// The next line should print ":pw(4000) a" since "pw" has not been added as a function to the context
		System.out.println(preprocessor.preprocess(":pw(4000) a", (StaccatoParserContext)null)); 
	}
}
