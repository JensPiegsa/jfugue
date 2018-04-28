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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import org.junit.Ignore;
import org.junit.Test;

public class MidiToolsTest {
	@Test
	public void testTrue() {
		assertTrue(true);
	}
	
	@Ignore 
	public void testSortMessagesByTick() {
//		Pattern pattern = new Pattern("C D E F G A B");
//		Sequence sequence = new Player().getSequence(pattern);

		Sequence sequence = null;
		try {
			sequence = MidiSystem.getSequence(new File("midi2wav-test.mid"));
		} catch (Exception e) {
			e.printStackTrace();
		}


		Map<Long, List<MidiMessage>> sortedMessages = MidiTools.sortMessagesByTick(sequence);
        long largestTick = MidiTools.getLargestKey(sortedMessages);
        for (long tick = 0; tick <= largestTick; tick++) {
            if (sortedMessages.containsKey(tick)) {
    			System.out.println(tick+" --> ");
				for (MidiMessage msg : sortedMessages.get(tick)) {
					System.out.print("    "+msg.getStatus() + " ");
					for (byte b : msg.getMessage()) {
						System.out.print(b+" ");
					}
					System.out.println();
				}
			}
		}
//    	assertTrue(r2.getPattern().toString().equals("V9 L0 Ri Ri Ri Ri Ri Ri Ri Ri [BASS_DRUM]i [BASS_DRUM]i [BASS_DRUM]i [BASS_DRUM]i Ri Ri Ri Ri"));
	}
}

 
