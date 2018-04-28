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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jfugue.parser.ParserException;
import org.jfugue.testtools.parser.JFugueTestHelper;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;
import org.junit.Test;

public class NoteSubparserTest extends JFugueTestHelper 
{
    @Test
    public void testSimpleNotesMatch() {
        NoteSubparser parser = NoteSubparser.getInstance();
        // Ensure that simple notes parse
        assertTrue(parser.matches("A"));
        assertTrue(parser.matches("R"));
        assertFalse(parser.matches("S"));
        
        // Ensure that flats and sharps parse
        assertTrue(parser.matches("Cb"));
        assertTrue(parser.matches("B#"));
        assertFalse(parser.matches("bC")); // The matcher should require uppercase letters
        assertTrue(parser.matches("A%")); // The matcher should just look at the first letter to accept the string 

        // Ensure that double-flats and double-sharps parse
        assertTrue(parser.matches("Ebb"));
        assertTrue(parser.matches("A##"));
        assertTrue(parser.matches("A#b")); // Should this be valid?
        assertFalse(parser.matches("I&&"));
        
        // Ensure that octaves parse
        assertTrue(parser.matches("Eb5"));
    }

    @Test
    public void testSimpleNotes() {
        assertTrue(compare("C", NOTE_PARSED, new Note(60).setOctaveExplicitlySet(false)));
        assertTrue(compare("C5q", NOTE_PARSED, new Note(60).setOctaveExplicitlySet(true).setDuration(0.25d)));
        assertTrue(compare("AWHa90", NOTE_PARSED, new Note(69, 1.5d).setOnVelocity((byte)90)));
    }
    
    @Test
    public void testNumericNoteByItself() {
    	assertTrue(compare("69", NOTE_PARSED, new Note(69)));
    }

    @Test
    public void testNumericNoteWithDuration() {
    	assertTrue(compare("60q", NOTE_PARSED, new Note(60).setDuration(0.25d)));
    }

    @Test
    public void testLookupNotes() {
    	assertTrue(compare("[BASS_DRUM]q", NOTE_PARSED, new Note(36).setDuration(0.25d)));
    }

    @Test
    public void testLookupNotesInHarmony() {
    	assertTrue(compare("[CLOSED_HI_HAT]q+[BASS_DRUM]q", NOTE_PARSED, new Note(36).setDuration(0.25d).setHarmonicNote(true).setFirstNote(false)));
    }

    @Test
    public void testDefaultOctave() {
    	assertTrue(compare("C", NOTE_PARSED, new Note(60)));
    }

    @Test
    public void testLowestNote() {
    	assertTrue(compare("C0", NOTE_PARSED, new Note(0).setOctaveExplicitlySet(true)));
    }

    @Test
    public void testHighestNote() {
    	assertTrue(compare("G10", NOTE_PARSED, new Note(127).setOctaveExplicitlySet(true)));
    }

    @Test(expected=ParserException.class)
    public void testIllegalOctave() {
    	compare("A10", NOTE_PARSED, new Note(127));
    }

    @Test
    public void testTriplets() {
    	assertTrue(compare("Cq*5:4", NOTE_PARSED, new Note(60).setDuration(0.2d)));
    	assertTrue(compare("D/0.25*4:5", NOTE_PARSED, new Note(62).setDuration(0.3125d)));
    	assertTrue(compare("C#h*2:4", NOTE_PARSED, new Note(61).setDuration(1.0d)));
    }

    @Test
    public void testTripletWithParens() {
        assertTrue(compare("(E G C)q*5:4", NOTE_PARSED, new Note(60).setDuration(0.2d)));
    }

    @Test
    public void testTripletWithDefaultDuration() {
        assertTrue(compare("C*5:4", NOTE_PARSED, new Note(60).setDuration(0.2d)));
    }

    @Test
    public void testTripletWithDefaultTuplet() {
        assertTrue(compare("C*", NOTE_PARSED, new Note(60).setDuration(0.16666666666666666d)));
        assertTrue(compare("Cq*3:2", NOTE_PARSED, new Note(60).setDuration(0.16666666666666666d)));
    }

    @Test
    public void testTiesWithLetterDuration() {
    	assertTrue(compare("Cw-", NOTE_PARSED, new Note(60).setDuration(1.0d).setStartOfTie(true).setEndOfTie(false)));
    	assertTrue(compare("C-w", NOTE_PARSED, new Note(60).setDuration(1.0d).setStartOfTie(false).setEndOfTie(true)));
    	assertTrue(compare("C-w-", NOTE_PARSED, new Note(60).setDuration(1.0d).setStartOfTie(true).setEndOfTie(true)));
    }
    
    @Test
    public void testTiesWithNumericDuration() {
    	assertTrue(compare("C/1.0-", NOTE_PARSED, new Note(60).setDuration(1.0f).setStartOfTie(true).setEndOfTie(false)));
    	assertTrue(compare("C/-1.0", NOTE_PARSED, new Note(60).setDuration(1.0f).setStartOfTie(false).setEndOfTie(true)));
    	assertTrue(compare("C/-1.0-", NOTE_PARSED, new Note(60).setDuration(1.0f).setStartOfTie(true).setEndOfTie(true)));
    }

    @Test
    public void testQuantityDuration() {
    	assertTrue(compare("Cw9", NOTE_PARSED, new Note(60).setDuration(9.0f)));
    }
    
    @Test
    public void testDyanmicsUsingLetters() {
        assertTrue(compare("Ca20", NOTE_PARSED, new Note(60).setOctaveExplicitlySet(false).setOnVelocity((byte)20)));
        assertTrue(compare("D4d20", NOTE_PARSED, new Note(50).setOctaveExplicitlySet(true).setOffVelocity((byte)20)));
        assertTrue(compare("Bb3a10d20", NOTE_PARSED, new Note(46).setOctaveExplicitlySet(true).setOnVelocity((byte)10).setOffVelocity((byte)20)));
    }

    @Test
    public void testDyanmicsWithDurations() {
        assertTrue(compare("Ch.a20", NOTE_PARSED, new Note(60).setOctaveExplicitlySet(false).setDuration(0.75d).setOnVelocity((byte)20)));
        assertTrue(compare("c2w2a2", NOTE_PARSED, new Note(24).setOctaveExplicitlySet(true).setDuration(2.0d).setOnVelocity((byte)2)));
        assertTrue(compare("D4/0.25d20", NOTE_PARSED, new Note(50).setOctaveExplicitlySet(true).setDuration(0.25d).setOffVelocity((byte)20)));
        assertTrue(compare("Bb3/-1.0-a10d20", NOTE_PARSED, new Note(46).setOctaveExplicitlySet(true).setDuration(1.0d).setStartOfTie(true).setEndOfTie(true).setOnVelocity((byte)10).setOffVelocity((byte)20)));
    }

    @Test
    public void testSimpleChord1() {
        assertTrue(compare("C4maj", CHORD_PARSED, new Chord(new Note(48).setOctaveExplicitlySet(true), Chord.getIntervals("MAJ"))));
    }
    
    @Test
    public void testSimpleChord2() {
        assertTrue(compare("AMIN/1.5", CHORD_PARSED, new Chord(new Note(57).setDuration(1.5d), Chord.getIntervals("MIN"))));
    }

    @Test
    public void testDefaultChordOctave() {
        assertTrue(compare("Cmaj", CHORD_PARSED, new Chord(new Note(48), Chord.getIntervals("MAJ"))));
    }

    @Test 
    public void testChordInversions() {
    	assertTrue(compare("C4maj", CHORD_PARSED, new Chord(new Note(48).setOctaveExplicitlySet(true), Chord.getIntervals("MAJ"))));
    }

    @Test
    public void testCombinedLetterNotes() {
        assertTrue(compare("E4q+C4q", NOTE_PARSED, new Note(48, 0.25d).setOctaveExplicitlySet(true).setFirstNote(false).setHarmonicNote(true)));
    }
    
    @Test
    public void testCombinedNumericNotes() {
        assertTrue(compare("65/0.25+60/0.25", NOTE_PARSED, new Note(60, 0.25d).setFirstNote(false).setHarmonicNote(true)));
    }

    @Test
    public void testInternalInterval() {
    	assertTrue(compare("C'6q", NOTE_PARSED, new Note(69, 0.25d)));
    }

    @Test
    public void testInternalIntervalWithFlat() {
    	assertTrue(compare("C'b3q", NOTE_PARSED, new Note("Ebq")));
    }

    @Test
    public void testInternalIntervalWithSharp() {
    	assertTrue(compare("C'#6q", NOTE_PARSED, new Note(70, 0.25d)));
    }

    @Test
    public void testInternalIntervalWithDoubleFlat() {
    	assertTrue(compare("C'6bbq", NOTE_PARSED, new Note(67, 0.25d)));
    }

    @Test
    public void testInternalIntervalWithDoubleSharp() {
    	assertTrue(compare("C'6##q", NOTE_PARSED, new Note(71, 0.25d)));
    }
}
