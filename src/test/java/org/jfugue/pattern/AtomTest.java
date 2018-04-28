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

import org.jfugue.theory.Note;
import org.junit.Test;

public class AtomTest {
    @Test
    public void testAtom() {
    	Atom atom = new Atom((byte)1, (byte)3, (byte)4, "C");
    	assertTrue(atom.toString().equals("&V1,L3,I4,C"));
    }

    @Test
    public void testAtomMemberVariables() {
    	Atom atom = new Atom((byte)1, (byte)3, (byte)4, "C");
    	assertTrue(atom.getVoice() == 1);
    	assertTrue(atom.getLayer() == 3);
    	assertTrue(atom.getInstrument() == 4);
    	assertTrue(atom.getNote().equals(new Note("C")));
    }

    @Test
    public void testAtomWithContext() {
    	Atom atom = new Atom("V1", "L2", "I[Piano]", new Note("C"));
    	assertTrue(atom.toString().equals("&V1,L2,I0,C"));
    }

    @Test
    public void testAtomizePattern() {
    	Pattern pattern = new Pattern("C").setVoice(1).setInstrument(2);
    	pattern.atomize();
    	assertTrue(pattern.toString().equals("&V1,L0,I2,C"));
    }

    @Test
    public void testAtomizeCrazierPattern() {
    	// This pattern has:
    	// - Unstated layers and instruments that should resolve to 0
    	// - Layers and instruments stated at the end of one voice that shouldn't affect other voices
    	// - Layers and instruments that should be recalled when parsed later in the line
    	Pattern pattern = new Pattern("V1 A V2 B I55 L6 V1 I7 C V3 L7 E I8 D L5 C I4 A V2 G"); 
    	pattern.atomize();
    	assertTrue(pattern.toString().equals("&V1,L0,I0,A &V2,L0,I0,B &V1,L0,I7,C &V3,L7,I0,E &V3,L7,I8,D &V3,L5,I8,C &V3,L5,I4,A &V2,L6,I55,G"));
    }

}
