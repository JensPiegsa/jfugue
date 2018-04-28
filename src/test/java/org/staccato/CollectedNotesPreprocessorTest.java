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

import org.junit.Before;
import org.junit.Test;

public class CollectedNotesPreprocessorTest {
    CollectedNotesPreprocessor pe;
    StaccatoParserContext context;

    @Before
    public void setup() {
        pe = new CollectedNotesPreprocessor();
        context = new StaccatoParserContext(null);
    }
    
    @Test
    public void testNonCollectedNotes() {
        assertTrue(pe.preprocess("C E Gq", context).equalsIgnoreCase("C E Gq"));
    }
    
    
    @Test
    public void testCollectedNotesWithStringDuration() {
        assertTrue(pe.preprocess("(C E G)q", context).equalsIgnoreCase("Cq Eq Gq"));
    }
    
    @Test
    public void testCollectedNotesWithDecimalDuration() {
        assertTrue(pe.preprocess("(C E G)/0.25", context).equalsIgnoreCase("C/0.25 E/0.25 G/0.25"));
    }
      
    @Test
    public void testCollectedNotesWithPlusses() {
        assertTrue(pe.preprocess("(C+E+G)q", context).equalsIgnoreCase("Cq+Eq+Gq"));
    }
        
    @Test
    public void testCollectedNotesWithOtherNotes() {
        assertTrue(pe.preprocess("Ah Bh (C E G)q", context).equalsIgnoreCase("Ah Bh Cq Eq Gq"));
    }
        
    @Test
    public void testCollectedNotesWithPlusAndSpace() {
        assertTrue(pe.preprocess("Zi (C+E G)q Fw", context).equalsIgnoreCase("Zi Cq+Eq Gq Fw"));
    }
        
    @Test
    public void testMultipleCollectedNotesInSameString() {
        assertTrue(pe.preprocess("Zi (1+2 3)q Fw (4+5 6)q Zo", context).equalsIgnoreCase("Zi 1q+2q 3q Fw 4q+5q 6q Zo"));
    }
        
    @Test
    public void testMultipleCollectedNotesInSameStringWithDecimalDuration() {
        assertTrue(pe.preprocess("Zi (1+2 3)/4.0 Fw (4+5 6)/0.5 Zo", context).equalsIgnoreCase("Zi 1/4.0+2/4.0 3/4.0 Fw 4/0.5+5/0.5 6/0.5 Zo"));
    }

    @Test
    public void testNothingAfterParens() {
        assertTrue(pe.preprocess("Zi (1+2 3) Fw (4+5 6) Zo", context).equalsIgnoreCase("Zi (1+2 3) Fw (4+5 6) Zo"));
    }
}
