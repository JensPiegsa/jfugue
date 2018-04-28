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

package org.staccato.manualtest;

import org.jfugue.manualtest.ManualTestPrint;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class ChordInversionTest {
	public static void main(String[] args) {
		ManualTestPrint.expectedResult("The C Major note should play through its three inversions:");
		ManualTestPrint.expectedResult("- One chord without an inversion.");
		ManualTestPrint.expectedResult("- Two identical-sounding chords for the first inversion.");
		ManualTestPrint.expectedResult("- Two identical-sounding chords for the second inversion.");
		
		Pattern pattern = new Pattern(
				"#no_inversion      C4majw                  | " +
				"#first_inversion   C4maj^w     C4maj^Ew    | " +
				"#second_inversion  C4maj^^w    C4maj^Gw      ");
		Player player = new Player();
		player.play(pattern);
	}

}
