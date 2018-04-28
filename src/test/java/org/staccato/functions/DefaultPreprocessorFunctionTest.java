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

package org.staccato.functions;

import static org.junit.Assert.assertTrue;

import org.jfugue.pattern.Pattern;
import org.jfugue.testtools.parser.JFugueTestHelper;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.staccato.DefaultNoteSettingsManager;
import org.staccato.StaccatoParser;

public class DefaultPreprocessorFunctionTest extends JFugueTestHelper {

	@Test
    public void testDefaultOctave() {
    	assertTrue(compare(":DEFAULT(OCTAVE=6) C", NOTE_PARSED, new Note(72)));
    }

	@Test
    public void testDefaultOctaveAndAttack() {
    	assertTrue(compare(":DEFAULT(OCTAVE=6,ATTACK=60) C", NOTE_PARSED, new Note(72).setOnVelocity((byte)60)));
    }

	@Test
    public void testDefaultAttackAndDecay() {
    	assertTrue(compare(":DEFAULT(ATTACK=60,DECAY=80) C", NOTE_PARSED, new Note(60).setOnVelocity((byte)60).setOffVelocity((byte)80)));
    }

	@Test
    public void testDefaultDuration() {
    	assertTrue(compare(":DEFAULT(DURATION=0.5) C", NOTE_PARSED, new Note(60).setImplicitDurationForTestingOnly(0.5)));
    }

	@Test
    public void testDefaultsWithLowercase() {
    	assertTrue(compare(":default(duration=0.5) c", NOTE_PARSED, new Note(60).setImplicitDurationForTestingOnly(0.5)));
    }

	@Ignore @Test
    public void testDefaultBassOctave() {
		// This test is not passing because the rootNote of the parsed chord is not equal 
		// to the rootNote of the expected Chord because their originalStrings are slightly different ("C" vs. "C1")		
    	assertTrue(compare(":DEFAULT(BASS_OCTAVE=1) Cmaj", CHORD_PARSED, new Chord("CMAJ"))); 
    }
	
	@Test
	public void testCleanParse() {
		StaccatoParser parser = new StaccatoParser();
		assertTrue(parser.preprocess(new Pattern("C :DEFAULT(DURATION=0.25) D")).equals("C  D"));
		// I'm not sure if leaving an extra space behind is really a clean parse, but the Staccato Parser skips over spaces, so it doesn't really matter.
	}

	@After
	public void tearDown() {
		parser.preprocess(":DEFAULT(Duration="+DefaultNoteSettingsManager.DEFAULT_DEFAULT_DURATION+
				",Octave="+DefaultNoteSettingsManager.DEFAULT_DEFAULT_OCTAVE+
				",Bass_Octave="+DefaultNoteSettingsManager.DEFAULT_DEFAULT_BASS_OCTAVE+
				",Attack="+DefaultNoteSettingsManager.DEFAULT_DEFAULT_ON_VELOCITY+
				",Decay="+DefaultNoteSettingsManager.DEFAULT_DEFAULT_OFF_VELOCITY+
				")");
	}
}
