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

import javax.sound.midi.MidiEvent;

/**
 * Provides implementors with an opportunity to know when MidiEvents have been parsed
 * by the MidiParser, and lets implementors deal with MidiEvents that the MidiParser
 * does not handle.
 * 
 * For a list of MidiEvents that the MidiParser will handle, @see MidiParser
 *
 */
public interface AuxilliaryMidiParser 
{
	public void parseHandledMidiEvent(MidiEvent event, MidiParser parser);
	public void parseUnhandledMidiEvent(MidiEvent event, MidiParser parser);
}
