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
import org.jfugue.theory.Note;
import org.staccato.MicrotonePreprocessor;

public class MicrotoneManualTest 
{
	public static void main(String[] args) {
		Player player = new Player();

		Pattern normalScale = new Pattern("(A4 A#4 B4 C5 C#5 D5 D#5 E5 F5 F#5 G5 G#5 A5 A#5 B5 C6)s");
		Pattern microtoneScale = new Pattern();
		for (double freq = Note.getFrequencyForNote("A4"); freq < Note.getFrequencyForNote("C6"); freq += 10.5) {
			microtoneScale.add("m"+freq+"s");
			System.out.println(freq);
		}

		ManualTestPrint.step("First, playing normal scale: "+normalScale);
		player.play(normalScale);

		player.play("Rw5");
		
		ManualTestPrint.step("Second, playing microtone scale. You should hear middle ranges in these notes: "+microtoneScale);
		player.play(microtoneScale);

		ManualTestPrint.step("The expanded version of the microtone string is: "+MicrotonePreprocessor.getInstance().preprocess(microtoneScale.toString(), null));

		player.play("Rw5");

		Pattern pitchWheelDemo = new Pattern();
		ManualTestPrint.step("Now, let's do a pitch wheel demo. Same note (Middle-C, note 60), played with 163 different pitch wheel settings, each 100 cents apart.");
		for (int i=0; i < 16383; i+= 100) {
			pitchWheelDemo.add(":PW("+i+") 60t");
		}
		ManualTestPrint.step("This should sound like it gradually ramps up.");
		player.play(pitchWheelDemo);
		
		player.play("Rw5");
		
		ManualTestPrint.step("Now playing four notes. The first two should sound different from each other, and the second two should sound the same as the first two.");
		String microtone = "m346.0w m356.5w";
		player.play(microtone);
		player.play(MicrotonePreprocessor.getInstance().preprocess(microtone, null));
	}
}
