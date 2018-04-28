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

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import org.jfugue.player.Player;
import org.staccato.StaccatoParserListener;

public class MidiParserTest 
{
	public static void main(String[] args) {
		try {
			StaccatoParserListener listener = new StaccatoParserListener();
//			DiagnosticParserListener listener = new DiagnosticParserListener();
			MidiParser midiParser = new MidiParser();
			midiParser.addParserListener(listener);
//			Sequence sequence = MidiSystem.getSequence(new File("beatit.mid"));
						
//            Sequence sequence = MidiSystem.getSequence(new File("Y:\\MIDI\\Keep_Their_Heads_Ringing.mid")); // Has multi-word marker
          Sequence sequence = MidiSystem.getSequence(new File("Y:\\MIDI\\ThePowerOfGoodbye.mid")); // Has Sysex's
//			Sequence sequence = MidiSystem.getSequence(new File("C:\\My Media\\MIDI\\Rappers_Delight.mid")); // No lyrics
//			Sequence sequence = MidiSystem.getSequence(new File("y:\\callresp.mid"));
//			Sequence sequence = MidiSystem.getSequence(new File("Y:\\spring.mid"));
//			Sequence sequence = MidiSystem.getSequence(new File("Y:\\test.mid"));
			midiParser.parse(sequence);
			System.out.println(listener.getPattern());

			new Player().play(listener.getPattern());
			
//			Sequence s2 = new Player().getSequence(listener.getPattern());
//			MidiFileManager.save(s2, new File("beatit-from-jf.mid")); 
//			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
