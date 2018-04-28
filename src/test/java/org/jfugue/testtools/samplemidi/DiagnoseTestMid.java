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

import javax.sound.midi.Sequence;

import org.jfugue.devtools.DiagnosticParserListener;
import org.jfugue.midi.MidiParser;
import org.jfugue.pattern.PatternProducer;
import org.jfugue.player.Player;
import org.junit.Ignore;

@Ignore
public class DiagnoseTestMid 
{
	public static void main(String[] args) {
		DiagnoseTestMid test = new DiagnoseTestMid();
		test.diagnose(CreateTestMid.createSoloTest());
		test.diagnose(CreateTestMid.createDuetTest());
		test.diagnose(CreateTestMid.createCallResponseTest());
	}
	
	private Player player;
	private DiagnosticParserListener listener;
	private MidiParser parser;
	
	public DiagnoseTestMid() {
		player = new Player();
		listener = new DiagnosticParserListener();
		parser = new MidiParser();
		parser.addParserListener(listener);
	}
	
	public void diagnose(PatternProducer patternProducer)
	{
		Sequence sequence = player.getSequence(patternProducer);
		parser.parse(sequence);
	}
}
