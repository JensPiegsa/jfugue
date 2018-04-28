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

package org.jfugue.rhythm;

import org.jfugue.manualtest.ManualTestPrint;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class RhythmTimingTest {
	public static void main(String[] args) {
		ManualTestPrint.expectedResult("This beats in this rhythm should not sound choppy or out-of-synch.");
		Rhythm rhythm = new Rhythm();
		rhythm.addLayer("O..oO...O..oOO..");
		rhythm.addLayer("..S...S...S...S.");
		rhythm.addLayer("````````````````");
		rhythm.addLayer("...............+");
		Pattern pattern = rhythm.getPattern();
		pattern.repeat(4);
		Player player = new Player();
		player.play(pattern);
	}
}

