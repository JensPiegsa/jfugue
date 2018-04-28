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

package org.jfugue.integration;

import org.jfugue.pattern.Pattern;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.staccato.StaccatoParser;

/**
 * @author Hans Beemsterboer
 */
public class LilyPondParserListenerTest {
	private StaccatoParser parser = null;
	private LilyPondParserListener listener = null;
	private Pattern pattern = null;

	private void doTest(String jfugueStr, String lilyStr) {
		pattern = new Pattern(jfugueStr);
		parser.parse(pattern);
		Assert.assertEquals("\\new Staff { "+lilyStr+" }", listener.getLyString());
	}
	
	private void doTestMultipleStaves(String jfugueStr, String lilyStr) {
		pattern = new Pattern(jfugueStr);
		parser.parse(pattern);
		Assert.assertEquals(lilyStr, listener.getLyString());
	}	

	@Before
	public void setUp() throws Exception {
		parser = new StaccatoParser();
		listener = new LilyPondParserListener();
		// parser.setTracing(MusicStringParser.TRACING_ON);
		parser.addParserListener(listener);
		pattern = new Pattern();
	}

	@Test	
	public void note() {
		doTest("A4q", "a'4");
	}

	@Test
	public void notes() {
		doTest("A4q B4w C4i", "a'4 b1 c,8");
	}

	@Test	
	public void notes2() {
		doTest("A4q E4q", "a'4 e4");
	}	
	
	@Test	
	public void notes3() {
		doTest("G4i B4i", "g'8 b8");
	}	
	
	@Test	
	public void notes4() {
		doTest("G3i C4i E4i C4i G3i", "g8 c8 e8 c8 g8");
	}

	@Test	
	public void sharpFlatNotes() {
		doTest("A#4 Bb4", "ais'4 bes4");
	}

	@Test
	public void restNotes() {
		doTest("Rq Ri Rw", "r4 r8 r1");
	}

	@Test	
	public void noteWithDot() {
		doTest("Rq. B4h.", "r4. b'2.");
	}

	@Test	
	public void chord() {
		doTest("C4maj", "<c e g>4");
	}
	
	@Test	
	public void parallelNotesFis() {
		doTest("B3q+C#5q", "<b cis'>4");
	}	

	@Test	
	public void parallelNotes3() {
		doTest("A4q+B4q+C4q", "<a' b c,>4");
	}
	
	@Test	
	public void parallelNotes4() {
		doTest("C4h+C5q_B4i_E4i_G4i_B4i", "<< { c2 } \\\\ { c'4 b8 e,8 g8 b8 } >>");
	}
	
	@Test	
	public void parallelNotes5() {
		doTest("Dq+Fq+Aq+Bbq+F#q", "<d' f a bes fis>4");
	}

	@Test	
	public void parallelNotes2() {
		doTest("A4q+B4q", "<a' b>4");
	}

	@Test	
	public void polyphoneNotes() {
		doTest("A4w+B4q_C4q_D4h", "<< { a'1 } \\\\ { b4 c,4 d2 } >>");
	}

	@Test	
	public void chordDot() {
		doTest("C4majq.", "<c e g>4.");
	}

	@Test	
	public void chordOctave() {
		doTest("E4minq G4i B4i", "<e g b>4 g8 b8");
	}
	
	@Test	
	public void chordOctave2() {
		doTest("E4 G4i B4i", "e4 g8 b8");
	}

	@Test	
	public void chordOctave3() {
		doTest("C3MAJq F3MAJq G3MAJq C3MAJq", "<c, e g>4 <f a c>4 <g b d>4 <c, e g>4");
	}
	
	@Test	
	public void octave3() {
		doTest("C3q F3q G3q C3q", "c,4 f4 g4 c,4");
	}	

	@Test	
	public void octaveUpDown() {
		doTest("D4w+G5q_F#5q_E5i_A4i_D5q",
				"<< { d1 } \\\\ { g'4 fis4 e8 a,8 d4 } >>");
	}

	@Test	
	public void octaveWithRest() {
		doTest("Rq E4i E4q E4q Ri Rq D4i D4q C4q Ri",
				"r4 e8 e4 e4 r8 r4 d8 d4 c4 r8");
	}

	@Test	
	public void octaveFirstNoteUp() {
		doTest("B5i ", "b''8");
	}

	@Test	
	public void octaveFirstNoteUpSecondNoteDown() {
		doTest("B5i D4i ", "b''8 d,,8");
	}

	@Test	
	public void singlePattern() {
		doTest("F4w+E5q_A4i_D5i_E5q_G5q",
				"<< { f1 } \\\\ { e'4 a,8 d8 e4 g4 } >>");
	}

	@Test	
	public void twoPatterns() {
		doTest("F4w+E5q_A4i_D5i_E5q_G5q  F4w+E5q_A4i_D5i_E5q_D5q",
				"<< { f1 } \\\\ { e'4 a,8 d8 e4 g4 } >><< { f,1 } \\\\ { e'4 a,8 d8 e4 d4 } >>");
	}
	
	@Test	
	public void twoPatterns2() {
		doTest("C4h+G4q_E4q_G4q_E4i  B4w+Rq._E4q_G4q_E4q",
				"<< { c2 } \\\\ { g'4 e4 g4 e8 } >><< { b'1 } \\\\ { r4. e,4 g4 e4 } >>");
	}	

	@Test	
	public void octaveThirdNoteDown() {
		doTest("B4i E4i A4i B4i E4i A4i B4q C5i E4i A4i C5i E4i A4i C5q",
				"b'8 e,8 a8 b8 e,8 a8 b4 c8 e,8 a8 c8 e,8 a8 c4");
	}

	@Test
	public void increasingOctaves() {
	    doTest("V1 I0 C2 C3 C4 C5 C6 C7 C8 Rh", "\\set Staff.instrumentName = \"Piano\" c,,4 c'4 c'4 c'4 c'4 c'4 c'4 r2");
	}
	
	@Test	
	public void octaveUp() {
		doTest("B4i E4i A4i B4i E4i A4i B4q C5i E4i A4i C5i E4i A4i C5q",
				"b'8 e,8 a8 b8 e,8 a8 b4 c8 e,8 a8 c8 e,8 a8 c4");
	}
	
	@Test	
	public void octaveUp2() {
		doTest("B4i D4i A4i", "b'8 d,8 a'8");
	}
	
	@Test	
	public void octaveDown2() {
		doTest("B4i E4i A4i B4i", "b'8 e,8 a8 b8");
	}
	
	@Test	
	public void twoParallelPlusOneNote() {
		doTest("C4h+G4q  E4q+B4q A4q", "<c g'>4 <e b'>4 a4");
	}
	
	@Test
	public void oneNotePlusParallelNotes() {
		doTest("A4q C4h+G4q", "a'4 <c, g'>4");
	}
	
	@Test	
	public void parallelDurationChange() {
		doTest("E4q+A4q E4i+A4i", "<e a>4 <e a>8");
	}
	
	@Test	
	public void parallelNoteChange() {
		doTest("E4q+C5q Ri E4q+B4q", "<e c'>4 r8 <e, b'>4");
	}
	
	@Test
	public void parallelOctaveChange() {
		doTest("B4i+D#5i+F#5i Ri D4q E4q", "<b' dis fis>8 r8 d,4 e4");
	}
	
	@Test	
	public void threeStaves() {
		doTestMultipleStaves("V0 I16 A4q V1 I20 B4q V2 I24 C4q", "\\new Staff { \\set Staff.instrumentName = \"Drawbar_Organ\" a'4 }\n\\new Staff { \\set Staff.instrumentName = \"Reed_Organ\" b4 }\n\\new Staff { \\set Staff.instrumentName = \"Guitar\" c,4 }");
	}
	
	@Test	
	public void twoStavesParallel() {
		doTestMultipleStaves("V0 I16 A4q V1 I20 B4q+A4q V2 I24 C4q", "\\new Staff { \\set Staff.instrumentName = \"Drawbar_Organ\" a'4 }\n\\new Staff { \\set Staff.instrumentName = \"Reed_Organ\" <b a>4 }\n\\new Staff { \\set Staff.instrumentName = \"Guitar\" c,4 }");
	}	
	
	@Test
	public void applause() {
		doTest("B6s Ri B6s Ri B6s Ri B6www","b'''16 r8 b16 r8 b16 r8 b\\breve.");
	}
	
	@Test
	public void tie() {
		doTest("Rh G4h F#4q E4q D#4h- D#4-h Rh", "r2 g'2 fis4 e4 dis2 ~ dis2 r2");
	}
	
	@Test
	public void tie2() {
		doTest("D#4h- D#4-h", "dis2 ~ dis2");
	}	

	@Test
	public void noteWithLength3() {
		doTest("I[ROCK_ORGAN] B4www", "\\set Staff.instrumentName = \"Rock_Organ\" b'\\breve.");
	}
}
