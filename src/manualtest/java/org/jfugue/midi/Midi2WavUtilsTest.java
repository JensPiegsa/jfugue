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


package org.jfugue.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.jfugue.audio.Midi2WavUtils;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class Midi2WavUtilsTest {
	public static void main(String[] args) {
		File file = new File("midi2wav-test.mid");
		
		Pattern pattern = new Pattern("C D E F G A B");
		Player player = new Player();
		player.play(pattern);
		
		try {
			Midi2WavUtils.createWavFile(pattern, file);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Now load it back
		try {
			Pattern loadedPattern = MidiFileManager.loadPatternFromMidi(file);
			System.out.println(loadedPattern);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		
	}
}
