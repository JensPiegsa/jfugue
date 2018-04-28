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

import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

public class ChordDemo {
	public static void main(String[] args) {
		Player player = new Player();
		
		/** This example plays four chords */
		player.play("C3MAJq F3MAJq G3MAJq C3MAJq");

		/** 
		 * This example creates a Chord Progression, then
		 * uses the Replacement syntax to play the chords in
		 * the progression according to certain patterns
		 */
		ChordProgression cp = new ChordProgression("I IV V I");
		player.play(cp.eachChordAs("$0q. $1i. $2q. $2q. $1i."));
		player.play(cp.allChordsAs("$q $q R $i"));
		
		/**
		 * This example plays a broken chord
		 */
		player.play("Cmaj:$0h+$1h,$2q,$2q");
		
		/**
		 * This example shows that you can set the key of a chord progression
		 * using the same syntax as a chord
		 */
		player.play(cp.setKey("BbMAJ"));
	}
}
