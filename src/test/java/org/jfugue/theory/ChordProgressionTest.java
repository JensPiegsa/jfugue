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

public class ChordProgressionTest 
{
	@Before
	public void setUp() { }
	
    @Test
    public void testCreateChordProgressionWithNoKey() {
        ChordProgression cp = new ChordProgression("IV V I");
        Pattern pattern = cp.getPattern();
        assertTrue(pattern.toString().equalsIgnoreCase("F4MAJ G4MAJ C4MAJ"));
    }

    @Test
    public void testCreateChordProgressionWithSpaces() {
        ChordProgression cp = new ChordProgression("iv v i");
        Pattern pattern = cp.setKey(Key.DEFAULT_KEY).getPattern();
        assertTrue(pattern.toString().equalsIgnoreCase("F4MIN G4MIN C4MIN"));
    }
        
    @Test
    public void testCreateChordProgressionWithDashes() {
        ChordProgression cp = new ChordProgression("I-vi7-ii-V7"); // This is a turnaround
        Pattern pattern = cp.setKey(new Key("Amajw")).getPattern();
        assertTrue(pattern.toString().equalsIgnoreCase("A4MAJw F#5MIN7w B4MINw E5MAJ7w")); 
    }
    
    @Test
    public void testGetChords() {
        ChordProgression cp = new ChordProgression("I-vi7-ii-V7"); // This is a turnaround
        Chord[] chords = cp.setKey(new Key("Amaj")).getChords();
        Chord[] checklist = new Chord[] { new Chord("A4maj"), new Chord("F#5min7"), new Chord("B4min"), new Chord("E5maj7") };
        for (int i=0; i < chords.length; i++) {
        	assertTrue(chords[i].equals(checklist[i]));
        }
    }
    
    @Test
    public void testEachChordAs() {
        ChordProgression cp = new ChordProgression("iv v i").eachChordAs("$0q $1q $2q");
        assertTrue(cp.getPattern().toString().equals("F4q G#4q C5q G4q Bb4q D5q C4q Eb4q G4q"));

        cp = new ChordProgression("I IV V").eachChordAs("$0q $1h $2w");
        assertTrue(cp.getPattern().toString().equals("C4q E4h G4w F4q A4h C5w G4q B4h D5w"));
    }

    @Test
    public void testEachChordAsWithUnderscore() {
        ChordProgression cp = new ChordProgression("I IV V").eachChordAs("$!q $0q $1h $2w");
        assertTrue(cp.getPattern().toString().equals("C4MAJq C4q E4h G4w F4MAJq F4q A4h C5w G4MAJq G4q B4h D5w"));
    }
    
    @Test
    public void testAllChordsAs() {
        ChordProgression cp = new ChordProgression("iv v i").allChordsAs("$0q $1q $2q");
        assertTrue(cp.getPattern().toString().equals("F4MINq G4MINq C4MINq"));

        cp = new ChordProgression("I IV V").allChordsAs("$0q $1h $2w");
        assertTrue(cp.getPattern().toString().equals("C4MAJq F4MAJh G4MAJw"));
    }

    @Test
    public void testAllChordsAsWithBang() {
        ChordProgression cp = new ChordProgression("I IV V").allChordsAs("$!i $0q $1h $2w");
        assertTrue(cp.getPattern().toString().equals("C4MAJi F4MAJi G4MAJi C4MAJq F4MAJh G4MAJw"));

        cp = new ChordProgression("I IV V").allChordsAs("$0q $1h $2w $!i");
        assertTrue(cp.getPattern().toString().equals("C4MAJq F4MAJh G4MAJw C4MAJi F4MAJi G4MAJi"));
    }

    @Test
    public void testAllChordsAsWithInversions() {
        ChordProgression cp = new ChordProgression("iv v i").allChordsAs("$0q $0^q $0^^q $1q $1^q $1^^q $2q $2^q $2^^q");
        assertTrue(cp.getPattern().toString().equals("F4MINq F4MIN^q F4MIN^^q G4MINq G4MIN^q G4MIN^^q C4MINq C4MIN^q C4MIN^^q"));
    }

    @Test
    public void testEachChordAsWithInversions() {
        ChordProgression cp = new ChordProgression("iv v i").eachChordAs("$!q $!^q $!^^q");
        assertTrue(cp.getPattern().toString().equals("F4MINq F4MIN^q F4MIN^^q G4MINq G4MIN^q G4MIN^^q C4MINq C4MIN^q C4MIN^^q"));
    }

    @Test
    public void testAllChordsAsWithBangAndInversions() {
        ChordProgression cp = new ChordProgression("iv v i").allChordsAs("$!q $!^q $!^^q");
        assertTrue(cp.getPattern().toString().equals("F4MINq G4MINq C4MINq F4MIN^q G4MIN^q C4MIN^q F4MIN^^q G4MIN^^q C4MIN^^q"));
    }

    @Test
    public void testAllChordsAsAndEachChordAs() {
        ChordProgression cp = new ChordProgression("I IV V").allChordsAs("$2 $1 $0").eachChordAs("$2 $1 $0");
        assertTrue(cp.getPattern().toString().equals("D5 B4 G4 C5 A4 F4 G4 E4 C4"));
    }
    
    @Test
    public void testChordProgressionWithInversions() {
    	ChordProgression cp = new ChordProgression("I II^ III^^ IV^^^ v^^^ vi^^ vii^");
    	assertTrue(cp.getPattern().toString().equals("C4MAJ D4MAJ^ E4MAJ^^ F4MAJ^^^ G4MIN^^^ A4MIN^^ B4MIN^"));
    }

}
