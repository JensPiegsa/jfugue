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

import javax.sound.midi.Sequence;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.junit.Ignore;

@Ignore
public class CreateSpringMid 
{
	public static void main(String[] args) {
	    // From 2008 JavaOne examples 
        Pattern repeated = new Pattern();
        repeated.add("V0 Cq+Eq Cq+Eq Cq+Eq Di Ci | Dh.+Gh.");
        repeated.add("V1 C4h          C4h          | C4h");

        Pattern miniRepeated = new Pattern("V0 Gi Fi V1 C4h");
        Pattern pattern = new Pattern();
        pattern.add("V0 Cq V1 Rq");
        pattern.add(repeated);
        pattern.add(miniRepeated);
        pattern.add(repeated);
        pattern.add(miniRepeated);
        pattern.add("V0 Cq+Eq Fi Gi Dq+Fq Cq+Eq | Dh  V1 C4h C4h G4h.");
        
        Pattern ultPattern = new Pattern("T140 V0 I0 V1 I0 "+pattern);

		Player player = new Player();
		Sequence sequence = player.getSequence(ultPattern);
		System.out.println("Resolution is "+sequence.getResolution());
        try {
			MidiFileManager.save(sequence, new File("Y:\\spring.mid"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
