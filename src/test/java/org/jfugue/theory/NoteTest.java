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

import org.junit.Before;
import org.junit.Test;

public class NoteTest 
{
	@Before
	public void setUp() { }
	
    @Test
    public void testToneString() {
        assertTrue(Note.getToneString((byte)57).equalsIgnoreCase("A4"));
    }

    @Test
    public void testDurationString() {
        assertTrue(Note.getDurationString(0.25d).equalsIgnoreCase("q"));
        assertTrue(Note.getDurationString(0.75d).equalsIgnoreCase("h."));
        assertTrue(Note.getDurationString(1.125d).equalsIgnoreCase("wi"));
    }
    
    @Test
    public void testPercussionString() {
        assertTrue(Note.getPercussionString((byte)56).equalsIgnoreCase("[COWBELL]"));
    }
    
    @Test
    public void testDurationForBeat() {
    	assertTrue(Note.getDurationStringForBeat(2).equalsIgnoreCase("h"));
    	assertTrue(Note.getDurationStringForBeat(4).equalsIgnoreCase("q"));
    	assertTrue(Note.getDurationStringForBeat(8).equalsIgnoreCase("i"));
    	assertTrue(Note.getDurationStringForBeat(16).equalsIgnoreCase("s"));
    	assertTrue(Note.getDurationStringForBeat(10).equalsIgnoreCase("/0.1"));
    	assertTrue(Note.getDurationStringForBeat(6).equalsIgnoreCase("/0.16666666666666666"));
    }
    
    @Test
    public void testGetFrequency() {
    	assertTrue(Note.getFrequencyForNote("A5") == 440.0);
    	assertTrue(Note.getFrequencyForNote("A") == 440.0);
    	assertTrue(Note.getFrequencyForNote(69) == 440.0);
    }
    
    @Test
    public void testIsSameNote() {
    	assertTrue(Note.isSameNote("G#", "Ab"));
    	assertTrue(Note.isSameNote("BB", "a#"));
    	assertTrue(Note.isSameNote("C", "C"));
    }
    
    @Test
    public void testRestValue() {
        assertTrue(new Note("R").getValue() == 0);
    }
    
    @Test
    public void testRestOctave() {
        assertTrue(new Note("R").getOctave() == 0);
    }
    
    @Test
    public void testRestToneString() {
        assertTrue(new Note("R").getToneString().equals("R"));
    }
    
    @Test
    public void testRestFrequency() {
        assertTrue(Note.getFrequencyForNote("R") == 0.0D);
    }

    @Test
    public void testPositionInOctave() {
    	assertTrue(new Note("C").getPositionInOctave() == 0);
    	assertTrue(new Note("Bb5").getPositionInOctave() == 10);
    	assertTrue(new Note("F#2").getPositionInOctave() == 6);
    }
}
