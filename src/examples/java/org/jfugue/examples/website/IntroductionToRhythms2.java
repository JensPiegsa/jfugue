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

package org.jfugue.examples.website;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.ChordProgression;

public class IntroductionToRhythms2 {
	public static void main(String[] args) {
		// Create a rhythm
		Rhythm rhythm = new Rhythm();
        rhythm.addLayer("O..oO...O..oOO..");
        rhythm.addLayer("..S...S...S...S.");
        rhythm.addLayer("````````````````");
        rhythm.addLayer("...............+");

		// Create a chord progression, give it a key (with duration), and make sure it always plays in Voice 1
        Pattern chords = new ChordProgression("I IV V I").setKey("AbMINww").getPattern().setVoice(1);
        
        // Combine the chords and the rhythm
		Pattern combinedPattern = new Pattern(chords, rhythm.getPattern().repeat(4));

		// Play the *combined* pattern twice (that will be 8 total occurrences the rhythm)
		Player player = new Player();
        player.play(combinedPattern.repeat(2)); 
	}
}
