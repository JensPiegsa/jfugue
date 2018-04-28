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
public class CreateChordMid 
{
	public static Pattern createChordMid() {
		return new Pattern("V1 C5majQ");
	}

	public static void main(String[] args) {
		player.play(createChordMid());
//	    save(CreateChordMid.createSoloTest(), "chord.mid");
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
