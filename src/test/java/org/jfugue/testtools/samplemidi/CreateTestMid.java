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

package org.jfugue.testtools.samplemidi;

import java.io.File;
import java.io.IOException;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.PatternProducer;
import org.jfugue.player.Player;
import org.junit.Ignore;

@Ignore
public class CreateTestMid 
{
	public static Pattern createSoloTest() {
		return new Pattern("V1 I0 C5w D5h E5q F5i G5s A5t B5x Rw E4hq D4q. C4ww");
	}

	public static Pattern createDuetTest() {
		return new Pattern("V1 I0 C5w D5h E5q F5i G5s A5t B5x V2 I42 B6w A6h G6q F6i E6s D6t C6x");
	}

	public static Pattern createCallResponseTest() {
		return new Pattern("V1 I0 A5q F5q A5q D5q | Rw | D5q A5q F5q A5q | V2 I23 Rw | D4q F4q D4q A4q | Rh D4q F4q");
	}

	public static void main(String[] args) {
		player.play(createSoloTest());
	    save(CreateTestMid.createSoloTest(), "solotest.mid");

		player.play(createDuetTest());
	    save(CreateTestMid.createDuetTest(), "duettest.mid");

		player.play(createCallResponseTest());
	    save(CreateTestMid.createCallResponseTest(), "callresp.mid");
	}
	
	private static Player player;
	
	static {
		player = new Player();
	}
	
	private static void save(PatternProducer patternProducer, String filename)
	{
        try {
			MidiFileManager.save(player.getSequence(patternProducer), new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
