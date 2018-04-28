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

package org.jfugue.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

import org.jfugue.midi.MidiDictionary;
import org.jfugue.midi.MidiParser;

public class InstrumentTool extends ParserListenerAdapter 
{
	private List<String> instrumentNames;
	
	public InstrumentTool() {
		super();
		instrumentNames = new ArrayList<String>();
	}
	
	@Override
	public void onInstrumentParsed(byte instrument) {
		String instrumentName = MidiDictionary.INSTRUMENT_BYTE_TO_STRING.get(instrument);
		if (!instrumentNames.contains(instrumentName)) {
			instrumentNames.add(instrumentName);
		}
		
	}
	
	public List<String> getInstrumentNames() {
		return this.instrumentNames;
	}
	
	public static void main(String[] args) {
		MidiParser midiParser = new MidiParser();
		InstrumentTool instrumentTool = new InstrumentTool();
		midiParser.addParserListener(instrumentTool);
		try {
			midiParser.parse(MidiSystem.getSequence(new File("test.mid")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		List<String> instrumentNames = instrumentTool.getInstrumentNames();
		for (String name : instrumentNames) {
			System.out.println(name);
		}
	}
}
