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
import static org.junit.Assert.fail;

import org.jfugue.parser.Parser;
import org.junit.Before;
import org.junit.Test;

public class MicrotonePreprocessorTest {
    MicrotonePreprocessor preprocessor;
    StaccatoParserContext context;

    @Before
    public void setUp() {
        preprocessor = MicrotonePreprocessor.getInstance();
        context = new StaccatoParserContext((Parser)null);
    }

    @Test
    public void testMicrotoneAdjustment() {
    	System.out.println(MicrotonePreprocessor.convertFrequencyToStaccato(155.56, "s"));
    	assertTrue(MicrotonePreprocessor.convertFrequencyToStaccato(440.0, "s").equals("57s"));
    	assertTrue(MicrotonePreprocessor.convertFrequencyToStaccato(155.56, "q").equals("39q"));
    }
    
    @Test
    public void testDoesNotBreakRegularStringsWithM() {
        assertTrue(preprocessor.preprocess("TIME:44/2 KEY:C", context).equals("TIME:44/2 KEY:C"));
    }

    @Test
    public void testNoGivenDuration() {
        assertTrue(preprocessor.preprocess("a m512.3 e", context).equals("a :PitchWheel(13384) 59/0.25 :PitchWheel(8192) e"));
    }
    
    @Test
    public void testGivenLetterDuration() {
        assertTrue(preprocessor.preprocess("a m512.3h e", context).equals("a :PitchWheel(13384) 59h :PitchWheel(8192) e"));
    }

    @Test
    public void testGivenNumericDuration() {
        assertTrue(preprocessor.preprocess("a m512.3/0.5 e", context).equals("a :PitchWheel(13384) 59/0.5 :PitchWheel(8192) e"));
    }

    @Test
    public void testNoDecimalInFrequency() {
        assertTrue(preprocessor.preprocess("a m500 e", context).equals("a :PitchWheel(9937) 59/0.25 :PitchWheel(8192) e"));
    }

    @Test
    public void testMicrotoneParsedWhenFirst() {
    	System.out.println(preprocessor.preprocess("m500 e", context));
        assertTrue(preprocessor.preprocess("m500 e", context).equals(":PitchWheel(9937) 59/0.25 :PitchWheel(8192) e"));
    }

    @Test
    public void testMicrotoneParsedWhenLast() {
        assertTrue(preprocessor.preprocess("a m500", context).equals("a :PitchWheel(9937) 59/0.25 :PitchWheel(8192)"));
    }

    @Test
    public void testMicrotoneParsedByItself() {
        assertTrue(preprocessor.preprocess("m500", context).equals(":PitchWheel(9937) 59/0.25 :PitchWheel(8192)"));
    }

    @Test
    public void testTwoMicrotonesParse() {
        assertTrue(preprocessor.preprocess("a m512.3 e m500 a", context).equals("a :PitchWheel(13384) 59/0.25 :PitchWheel(8192) e :PitchWheel(9937) 59/0.25 :PitchWheel(8192) a"));
    }

    @Test
    public void testCarnaticValues() {
    	assertTrue(preprocessor.preprocess("m261.6256q m290.6951q", context).equals(":PitchWheel(8192) 48q :PitchWheel(8192) :PitchWheel(14942) 49q :PitchWheel(8192)"));
    }

    @Test (expected = IllegalStateException.class)
    public void testBadDefinition() {
        fail(preprocessor.preprocess("a ma e", context));
    }

}
