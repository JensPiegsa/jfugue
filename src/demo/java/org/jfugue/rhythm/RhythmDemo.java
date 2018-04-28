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

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class RhythmDemo {
	public static void main(String[] args) {
		
		// In this first block of code, we create a single-layer Rhythm
		// which will be played with a default kit. The o and O 
		// characters represent specific drum hits in the kit, and the
	    // period is a rest.
		Rhythm rhythm = new Rhythm("...o...o...o...O");
		new Player().play(rhythm);
		
		// In this next example, we create a more complex rhythm, 
		// again using the default kit. And, we use Pattern to show
		// that a Rhythm returns a Pattern, and the pattern can be
		// repeated multiple times.
		rhythm = new Rhythm();
		rhythm.addLayer("...o...o...o...O");
		rhythm.addLayer(".......*.....*..");
		rhythm.addLayer(".x...xx..x...xx.");
		Pattern pattern = rhythm.getPattern().repeat(4);
		System.out.println(pattern);
		new Player().play(pattern);
		
		// Here's another example.
		rhythm = new Rhythm();
		rhythm.addLayer("O.OO...O.OO....O");
        rhythm.addLayer("....o.......o...");
        rhythm.addLayer("^.`.^.`.^.`.^.`.");
        new Player().play(rhythm.getPattern().repeat(4));
        
		// In this example, we provide a rhythm with a time signature,
		// then use replaceBar to change one section of the rhythm.
        rhythm = new Rhythm("...o...o...o...O");
        pattern = rhythm.getPattern().repeat(4);
        rhythm.addOneTimeAltLayer(0, 3, "^^^^");
		pattern.add(rhythm);
		new Player().play(pattern);
	}

}
