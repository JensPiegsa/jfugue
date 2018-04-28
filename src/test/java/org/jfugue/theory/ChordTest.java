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

package org.jfugue.theory;

import static org.junit.Assert.assertTrue;

import org.jfugue.pattern.Pattern;
import org.junit.Before;
import org.junit.Test;

public class ChordTest 
{
	@Before
	public void setUp() { }
	
    @Test
    public void testCreateChordByString() {
        Chord chord = new Chord("Cmaj");
        Pattern pattern = chord.getPattern();
        assertTrue(pattern.toString().equalsIgnoreCase("CMAJ"));
    }

    @Test
    public void testCreateChordByNumberedRoot() {
        Chord chord = new Chord("60maj");
        Pattern pattern = chord.getPattern();
        assertTrue(pattern.toString().equalsIgnoreCase("CMAJ"));
    }

    @Test
    public void testCreateChordByIntervals() {
        Chord chord = new Chord(new Note("D5h"), new Intervals("1 3 5"));
        Pattern pattern = chord.getPattern();
        assertTrue(pattern.toString().equalsIgnoreCase("D5MAJh"));
    }
    
    @Test
    public void testChordInversionByNumber() {
    	Chord chord = new Chord("C4maj");
    	chord.setInversion(1);
    	Note[] notes = chord.getNotes();
    	assertTrue(notes[0].getValue() == 52);
    	assertTrue(notes[1].getValue() == 55);
    	assertTrue(notes[2].getValue() == 60);

    	chord.setInversion(2);
    	notes = chord.getNotes();
    	assertTrue(notes[0].getValue() == 55);
    	assertTrue(notes[1].getValue() == 60);
    	assertTrue(notes[2].getValue() == 64);
    }

    @Test
    public void testChordInversionByBass() {
    	Chord chord = new Chord("C4maj");
    	chord.setBassNote("E");
    	Note[] notes = chord.getNotes();
    	assertTrue(notes[0].getValue() == 52);
    	assertTrue(notes[1].getValue() == 55);
    	assertTrue(notes[2].getValue() == 60);

    	chord.setBassNote("G");
    	notes = chord.getNotes();
    	assertTrue(notes[0].getValue() == 55);
    	assertTrue(notes[1].getValue() == 60);
    	assertTrue(notes[2].getValue() == 64);
    }

    @Test
    public void testCreateChordWithNotes_StringConstructor() {
        Chord chord = Chord.fromNotes("C E G");
        assertTrue(chord.equals(new Chord("Cmaj")));
    }
    
    @Test
    public void testCreateChordWithNotes_StringArrayConstructor() {
        Chord chord = Chord.fromNotes(new String[] { "Bb", "Db", "F" });
        assertTrue(chord.equals(new Chord("Bbmin^")));
    }

    @Test
    public void testCreateChordWithNotes_NoteArrayConstructor() {
        Chord chord = Chord.fromNotes(new Note[] { new Note("D"), new Note("F#"), new Note("A") });
        assertTrue(chord.equals(new Chord("Dmaj")));
    }
    
    @Test
    public void testGetChordType() {
        Chord chord = new Chord("C5sus4");
        assertTrue(chord.getChordType().equalsIgnoreCase("sus4"));
    }
    
    @Test
    public void testCreateChordWithNotesInWrongOrder_ThreeNoteChord() {
        Chord chord = Chord.fromNotes("E G C");
        assertTrue(chord.equals(new Chord("Cmaj")));
        assertTrue(chord.getInversion() == 0);
    }

    @Test
    public void testCreateChordWithNotesInvertedNotes_ThreeNoteChord() {
        Chord chord = Chord.fromNotes("E4 G4 C5");
        assertTrue(chord.equals(new Chord("C5maj^")));
        assertTrue(chord.getInversion() == 1);
    }

    @Test
    public void testCreateChordWithInvertedNotes_FourNoteChord_FirstInversion() {
        Chord chord = Chord.fromNotes("E4 G4 B4 C5");
        assertTrue(chord.equals(new Chord("C5maj7^")));
        assertTrue(chord.getInversion() == 1);
    }

    @Test
    public void testCreateChordWithInvertedNotes_FourNoteChord_SecondInversion() {
        Chord chord = Chord.fromNotes("G4 C5 E5 B5");
        assertTrue(chord.equals(new Chord("Cmaj7^^")));
        assertTrue(chord.getInversion() == 2);
    }

    @Test
    public void testCreateChordWithInvertedNotes_FourNoteChord_ThirdInversion_AskBassNote() {
        Chord chord = Chord.fromNotes("B4 C5 E5 G5");
        assertTrue(chord.equals(new Chord("C5maj7^^^")));
        assertTrue(chord.getInversion() == 3);
        assertTrue(chord.getBassNote().equals(new Note("B").setOctaveExplicitlySet(true)));
    }

    @Test
    public void testGetBassNoteWithoutOctave() {
        Chord chord1 = new Chord("Cmaj^");
        assertTrue(chord1.getBassNote().equals(new Note("E")));
    }

    @Test
    public void testGetBassNoteWithOctave() {
        Chord chord2 = new Chord("C3maj^");
        assertTrue(chord2.getBassNote().equals(new Note("E").setOctaveExplicitlySet(true)));
    }

    @Test
    public void testCreateChordWithNotesInDifferentOctaves() {
        Chord chord = Chord.fromNotes("C3 E5 G7");
        assertTrue(chord.equals(new Chord("Cmaj")));
    }

    @Test
    public void testCreateChordWithManySimilarNotes() {
        Chord chord = Chord.fromNotes("F3 F4 F5 A6 A5 C4 C3");
        assertTrue(chord.equals(new Chord("Fmaj^^")));
    }

    @Test
    public void testChords() {
        Chord chord1 = Chord.fromNotes("C4 G4 E5");
        assertTrue(chord1.equals(new Chord("Cmaj")));
        
        Chord chord2 = Chord.fromNotes("G4 E5 C6");
        assertTrue(chord2.equals(new Chord("Cmaj^^")));
        
        Chord chord3 = Chord.fromNotes("C4 G4 B4 E5");
        assertTrue(chord3.equals(new Chord("Cmaj7")));
    }

    @Test
    public void testAddNewChord() {
        Chord.chordMap.put("POW", new Intervals("1 5"));
        Chord chord = new Chord("Cpow");
        Note[] notes = chord.getNotes();
        assertTrue(notes[0].getValue() == 48);
        assertTrue(notes[1].getValue() == 55);
    }
    
    @Test
    public void testGetPatternWithoutRoot() {
        assertTrue(new Chord("Cmaj").getPatternWithNotesExceptRoot().toString().equals("E+G"));
    }

    @Test
    public void testGetPatternWithoutRootFirstInversion() {
    	assertTrue(new Chord("Dmin^").getPatternWithNotesExceptRoot().toString().equals("F+A"));
    }

    @Test
    public void testGetPatternWithoutRootSecondInversion() {
        assertTrue(new Chord("Emaj^^").getPatternWithNotesExceptRoot().toString().equals("B+G#"));
    }

    @Test
    public void testGetPatternWithoutBass() {
        assertTrue(new Chord("Cmaj").getPatternWithNotesExceptBass().toString().equals("E+G"));
    }

    @Test
    public void testGetPatternWithoutBassFirstInversion() {
        assertTrue(new Chord("Dmin^").getPatternWithNotesExceptBass().toString().equals("A+D"));
    }
    
    @Test
    public void testGetPatternWithoutBassSecondInversion() {
        assertTrue(new Chord("Emaj^^").getPatternWithNotesExceptBass().toString().equals("E+G#"));
    }

}
