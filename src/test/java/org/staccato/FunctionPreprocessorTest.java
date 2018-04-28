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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.staccato.functions.FunctionManager;
import org.staccato.functions.TrillFunction;

public class FunctionPreprocessorTest {
    @Test
    public void testTrillFunctionPreprocessor() {
        FunctionPreprocessor preprocessor = FunctionPreprocessor.getInstance();
        StaccatoParserContext context = new StaccatoParserContext(null);
        FunctionManager.getInstance().addPreprocessorFunction(TrillFunction.getInstance());
        
        assertTrue(preprocessor.preprocess("a b c :TRILL(Cq) e r", context).equals("a b c C5t D5t C5t D5t C5t D5t C5t D5t e r"));
        assertTrue(preprocessor.preprocess("a b c :TRILL(Ch Eh) e r", context).equals("a b c C5t D5t C5t D5t C5t D5t C5t D5t C5t D5t C5t D5t C5t D5t C5t D5t E5t F#5t E5t F#5t E5t F#5t E5t F#5t E5t F#5t E5t F#5t E5t F#5t E5t F#5t e r"));
        assertTrue(preprocessor.preprocess(":pw(4000) a", context).equals(":pw(4000) a")); 
    }
}
