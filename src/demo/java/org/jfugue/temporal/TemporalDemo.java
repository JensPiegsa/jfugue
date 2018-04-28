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

package org.jfugue.temporal;

import org.jfugue.demo.DemoPrint;
import org.jfugue.devtools.DiagnosticParserListener;
import org.jfugue.player.Player;
import org.staccato.StaccatoParser;

/**
 *  This demo illustrates how to use the Temporal classes to get MIDI events in time-based order,
 *  and how to use time-ordered MIDI at the same time as calling Player's delayPlay to create a stream
 *  of messages that is separate from the stream of data that is being converted to music.
 * 
 *  This is the re-incarnation of the Anticipator functionality from JFugue 4.1. The Anticipator
 *  let you know about musical events before they happened by firing time-ordered events from a separate
 *  thread than the thread that was playing the music.
 */
public class TemporalDemo {
	private static final String MUSIC = "C D E F G A B";  // Feel free to put your own music here to experiment!
	private static final long TEMPORAL_DELAY = 500;       // Feel free to put your own delay here to experiment!
	
	public static void main(String[] args) {
		// In this first block of code, we use the TemporalPLP class 
		// as a ParserListener. The StaccatoParser will parse the 
		// music, and the TemporalPLP class will listen and construct
		// a real-time representation of the musical events.
		DemoPrint.start("Using StaccatoParser to parse music. Temporal class is listening for events from the StaccatoParser and building its model of the musical events.");
		StaccatoParser parser = new StaccatoParser();
		TemporalPLP plp = new TemporalPLP();
		parser.addParserListener(plp);
		parser.parse(MUSIC);

		// In this second block of code, we use the TemporalPLP class 
		// as a "parser" - it will send the events that it collected
		// previously, and the DiagnosticParserListener will show those
		// events as they happen.
		DemoPrint.step("Now playing Temporal's recorded events, and printing them in real-time with the DiagnosticParserListener.");
		DiagnosticParserListener dpl = new DiagnosticParserListener();
		plp.addParserListener(dpl);
		plp.parse();
		
		// You might imagine creating new types of ParserListeners, like an 
		// AnimationParserListener, that depend on knowing about the musical events
		// before they happen. For example, perhaps your animation is of a robot
		// playing a drum or strumming a guitar. Before the note makes a sound, the
		// animation needs to get its virtual hands in the right place, so you might
		// want a notice 500ms earlier that a musical event is about to happen. To do this,
		// delayPlay() creates a new thread that first waits the specified amount of time
		// before playing. If you do this, make sure to call delayPlay() 
		// before plp.parse().
		DemoPrint.step("Playing Temporal's recorded events again, using Player.delayPlay() to play the music by "+TEMPORAL_DELAY+"ms.");
		new Player().delayPlay(TEMPORAL_DELAY, MUSIC);
		plp.parse();
	}
}
