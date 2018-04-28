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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ReplacementMapPreprocessorTest {
    @Test
    public void testReplacementMapPreprocessor() {
        Map<String, String> replacementMap = new HashMap<String, String>();
        replacementMap.put("R1", "m440.0");
        replacementMap.put("S", "m770.0");
        
    	ReplacementMapPreprocessor preprocessor = ReplacementMapPreprocessor.getInstance();
        preprocessor.setReplacementMap(replacementMap);
 
        assertTrue(preprocessor.preprocess("<R1> C <S>", (StaccatoParserContext)null).equals("m440.0 C m770.0"));
    }

    @Test 
    public void testThreeIterations() {
        Map<String, String> replacementMap = new HashMap<String, String>();
        replacementMap.put("A", "<B> 1");
        replacementMap.put("B", "<A> 2");
        
    	ReplacementMapPreprocessor preprocessor = ReplacementMapPreprocessor.getInstance();
        preprocessor.setReplacementMap(replacementMap);
        preprocessor.setIterations(3);
        
        assertTrue(preprocessor.preprocess("<A> A <B>", (StaccatoParserContext)null).equals("<B> 1 2 1 A <A> 2 1 2"));
    }

}
