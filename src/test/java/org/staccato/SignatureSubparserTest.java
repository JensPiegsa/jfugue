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

import org.jfugue.testtools.parser.JFugueTestHelper;
import org.jfugue.theory.Note;
import org.junit.Test;

public class SignatureSubparserTest extends JFugueTestHelper {
    @Test
    public void testMajorKeySignature() {
    	assertTrue(compare("KEY:Gmaj", KEY_SIGNATURE_PARSED, (byte)7, (byte)1));
    }
    
    @Test
    public void testMinorKeySignature() {
    	assertTrue(compare("KEY:Abmin", KEY_SIGNATURE_PARSED, (byte)8, (byte)-1));
    }
    
    @Test
    public void testKeySignatureChangesSubsequentNote() {
        assertTrue(compare("KEY:Gmaj F5q", NOTE_PARSED, new Note(66, 0.25f).setOctaveExplicitlySet(true)));
    }

    @Test
    public void testKeySignatureWithSharpsChangesSubsequentNote1() {
        assertTrue(compare("KEY:K#### D5q", NOTE_PARSED, new Note(63, 0.25f).setOctaveExplicitlySet(true)));
    }
    
    @Test
    public void testKeySignatureWithSharpsChangesSubsequentNote2() {
        assertTrue(compare("KEY:K# F5q", NOTE_PARSED, new Note(66, 0.25f).setOctaveExplicitlySet(true)));
    }

    @Test
    public void testKeySignatureWithFlatsChangesSubsequentNote1() {
        assertTrue(compare("KEY:Kb B5q", NOTE_PARSED, new Note(70, 0.25f).setOctaveExplicitlySet(true)));
    }
    
    @Test
    public void testKeySignatureWithFlatsChangesSubsequentNote2() {
        assertTrue(compare("KEY:Kbbbb D5q", NOTE_PARSED, new Note(61, 0.25f).setOctaveExplicitlySet(true)));
    }

    @Test
    public void testTimeSignature() {
    	assertTrue(compare("TIME:6/8", TIME_SIGNATURE_PARSED, (byte)6, (byte)8));
    }

}
