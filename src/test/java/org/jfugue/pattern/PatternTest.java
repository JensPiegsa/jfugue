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

package org.jfugue.pattern;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PatternTest {
    @Test
    public void testSetTempo1() {
    	Pattern pattern = new Pattern("A B C"); 
    	pattern.setTempo("Adagio");
    	assertTrue(pattern.toString().equals("T60 A B C"));
    }

    @Test
    public void testSetTempo2() {
    	Pattern pattern = new Pattern("A B C"); 
    	pattern.setTempo(60);
    	assertTrue(pattern.toString().equals("T60 A B C"));
    }

    @Test(expected=RuntimeException.class)
    public void testSetTempo3() {
    	// "Unknown" is not the name of a tempo, so this should throw a RuntimeException
    	Pattern pattern = new Pattern("A B C"); 
    	pattern.setTempo("Unknown");
    }

    @Test
    public void testSetInstrument1() {
    	Pattern pattern = new Pattern("A B C"); 
    	pattern.setInstrument("FlUtE");
    	assertTrue(pattern.toString().equals("I[Flute] A B C"));
    }

    @Test
    public void testSetInstrument2() {
    	Pattern pattern = new Pattern("A B C"); 
    	pattern.setInstrument(60);
    	assertTrue(pattern.toString().equals("I[French_Horn] A B C"));
    }

    @Test(expected=RuntimeException.class)
    public void testSetInstrument3() {
    	// "Unknown" is not the name of an instrument, so this should throw a RuntimeException
    	Pattern pattern = new Pattern("A B C"); 
    	pattern.setInstrument("Unknown");
    }

    @Test
    public void testSetVoice() {
    	Pattern pattern = new Pattern("A B C"); 
    	pattern.setVoice(1);
    	assertTrue(pattern.toString().equals("V1 A B C"));
    }

    @Test
    public void testSetTempoInstrumentVoice() {
    	// Expect the correct ordering of T, V, and I in the resulting pattern
    	Pattern pattern = new Pattern("A B C").setTempo("Allegro").setInstrument(0).setVoice(8);
    	assertTrue(pattern.toString().equals("T120 V8 I[Piano] A B C"));
    }

    @Test
    public void testAddToNoteElements1() {
    	// There is one decorator that should be applied to each of the 3 notes
    	Pattern pattern = new Pattern("T60 A | B V1 C").addToEachNoteToken("q");
    	assertTrue(pattern.toString().equals("T60 Aq | Bq V1 Cq"));
    }

    @Test
    public void testAddToNoteElements2() {
    	// There are fewer decorators than notes in the pattern
    	Pattern pattern = new Pattern("T60 A | B V1 C").addToEachNoteToken("q i");
    	assertTrue(pattern.toString().equals("T60 Aq | Bi V1 Cq"));
    }

    @Test
    public void testAddToNoteElements3() {
    	// There are an equal number of decorators and notes in the pattern
    	Pattern pattern = new Pattern("T60 A | B V1 C").addToEachNoteToken("q i s");
    	assertTrue(pattern.toString().equals("T60 Aq | Bi V1 Cs"));
    }

    @Test
    public void testAddToNoteElements4() {
    	// There are more decorators than notes in the pattern
    	Pattern pattern = new Pattern("T60 A | B V1 C").addToEachNoteToken("q i s w");
    	assertTrue(pattern.toString().equals("T60 Aq | Bi V1 Cs"));
    }

    @Test
    public void testAddToNoteElements5() {
    	// Note elements in this pattern are percussion instruments
    	Pattern pattern = new Pattern("[BASS_DRUM] [HI_HAT] [OPEN_CUICA]").addToEachNoteToken("q i s w").setVoice(9);
    	assertTrue(pattern.toString().equals("V9 [BASS_DRUM]q [HI_HAT]i [OPEN_CUICA]s"));
    }

    @Test
    public void testAddToNoteElements6() {
    	// There are no note elements in this pattern
    	Pattern pattern = new Pattern("T120 V9 I[Piano]").addToEachNoteToken("q i s w");
    	assertTrue(pattern.toString().equals("T120 V9 I[PIANO]"));
    }

    @Test
    public void testRepeat() {
    	Pattern pattern = new Pattern("A B").repeat(3);
    	assertTrue(pattern.toString().equals("A B A B A B"));
    }

    @Test
    public void testRepeatWithTempoVoiceInstrument1() {
    	// Try declaring the repeat at the end of the calls to Pattern
    	Pattern pattern = new Pattern("A B").setTempo(20).setVoice(1).repeat(3);
    	assertTrue(pattern.toString().equals("T20 V1 A B A B A B"));
    }

    @Test
    public void testRepeatWithTempoVoiceInstrument2() {
    	// Try declaring the repeat towards the beginning of the calls to Pattern
    	Pattern pattern = new Pattern("A B").repeat(3).setTempo(20).setVoice(1);
    	assertTrue(pattern.toString().equals("T20 V1 A B A B A B"));
    }
    
    @Test
    public void testClear() {
    	Pattern pattern = new Pattern("A B").clear().add("C D");
    	assertTrue(pattern.toString().equals("C D"));
    }
    
    @Test
    public void testPrependMultiple() {
        Pattern pattern = new Pattern("D D");
        pattern.prepend(new Pattern[] { new Pattern("A A"), new Pattern("B B"), new Pattern("C C") } );
        assertTrue(pattern.toString().equals("A A B B C C D D"));
    }
}
