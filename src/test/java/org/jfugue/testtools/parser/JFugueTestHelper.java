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

package org.jfugue.testtools.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jfugue.parser.ParserListener;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;
import org.junit.Ignore;
import org.staccato.StaccatoParser;

@Ignore
public class JFugueTestHelper {
	protected StaccatoParser parser;
	protected ResultParserListener resultListener;
	
	public JFugueTestHelper() {
		parser = new StaccatoParser();
		resultListener = new ResultParserListener();
		parser.addParserListener(resultListener);
	}
	
	protected boolean compare(String stringToParse, String eventName) {
		parser.parse(stringToParse);
		ArgEvent event = (ArgEvent)resultListener.getPenultimateEvent();
		return event.getEventName().equals(eventName);
	}
	
	protected boolean compare(String stringToParse, String eventName, byte value) {
		parser.parse(stringToParse);
		OneByteArgEvent event = (OneByteArgEvent)resultListener.getPenultimateEvent();
		return (event.getEventName().equals(eventName) && (event.getValue() == value));
	}
	
	protected boolean compare(String stringToParse, String eventName, int value) {
		parser.parse(stringToParse);
		OneIntArgEvent event = (OneIntArgEvent)resultListener.getPenultimateEvent();
		return (event.getEventName().equals(eventName) && (event.getValue() == value));
	}
	
	protected boolean compare(String stringToParse, String eventName, long value) {
		parser.parse(stringToParse);
		OneLongArgEvent event = (OneLongArgEvent)resultListener.getPenultimateEvent();
		return (event.getEventName().equals(eventName) && (event.getValue() == value));
	}
	
    protected boolean compare(String stringToParse, String eventName, double value) {
        parser.parse(stringToParse);
        OneDoubleArgEvent event = (OneDoubleArgEvent)resultListener.getPenultimateEvent();
        return (event.getEventName().equals(eventName) && (event.getValue() == value));
    }
	
	protected boolean compare(String stringToParse, String eventName, byte value1, byte value2) {
		parser.parse(stringToParse);
		TwoByteArgEvent event = (TwoByteArgEvent)resultListener.getPenultimateEvent();
		return (event.getEventName().equals(eventName) && (event.getValue1() == value1) && (event.getValue2() == value2));
	}
	
	protected boolean compare(String stringToParse, String eventName, String value) {
		parser.parse(stringToParse);
		OneStringArgEvent event = (OneStringArgEvent)resultListener.getPenultimateEvent();
		return (event.getEventName().equals(eventName) && (event.getValue().equals(value)));
	}
	
	protected boolean compare(String stringToParse, String eventName, String id, Object object) {
		parser.parse(stringToParse);
		UserEventArgEvent event = (UserEventArgEvent)resultListener.getPenultimateEvent();
		return (event.getEventName().equals(eventName) && (event.getId().equals(id)) && (event.getObject().equals(object)));
	}
	
	protected boolean compare(String stringToParse, String eventName, byte[] value) {
		parser.parse(stringToParse);
		ByteArrayArgEvent event = (ByteArrayArgEvent)resultListener.getPenultimateEvent();
		return (event.getEventName().equals(eventName) && (Arrays.equals(event.getValue(), value)));
	}
	
	protected boolean compare(String stringToParse, String eventName, Note note) {
		parser.parse(stringToParse);
		NoteArgEvent event = (NoteArgEvent)resultListener.getPenultimateEvent();
		// Only want this diagnostic stuff if you're testing whether event.getNote() equals Note (e.g., using assertTrue)
		if (event.getEventName().equals(eventName) && !(event.getNote().equals(note))) {
			System.out.println("Diagnostic info for " + stringToParse + ":");
			System.out.println("Expected " + note.toDebugString());
			System.out.println("Got      " + event.getNote().toDebugString());
			System.out.println();
		}
		return (event.getEventName().equals(eventName) && (event.getNote().equals(note)));
	}
	
	protected boolean compare(String stringToParse, String eventName, Chord chord) {
		parser.parse(stringToParse);
		ChordArgEvent event = (ChordArgEvent)resultListener.getPenultimateEvent();
		// Only want this diagnostic stuff if you're testing whether event.getNote() equals Note (e.g., using assertTrue)
		if (event.getEventName().equals(eventName) && !(event.getChord().equals(chord))) {
			System.out.println("Diagnostic info for " + stringToParse + ":");
			System.out.println("Expected:");
			System.out.println(chord.toDebugString());
			System.out.println("Got:");
			System.out.println(event.getChord().toDebugString());
			System.out.println();
		}
		return (event.getEventName().equals(eventName) && event.getChord().equals(chord));
	}
	
	class ResultParserListener implements ParserListener
	{
		private List<ArgEvent> events;

	    public ResultParserListener() {
	        this.events = new ArrayList<ArgEvent>();
	    }
	    
	    /**
	     * Returns the second-to-last event - because the last event is
	     * presumed to be AFTER_PARSING_FINISHED
	     */
	    public ArgEvent getPenultimateEvent() {
	    	return events.get(events.size()-2);
	    }
	    
	    public List<ArgEvent> getEvents() {
	        return this.events;
	    }
	    
	    @Override
	    public void beforeParsingStarts() {
	        this.events.add(new ArgEvent(BEFORE_PARSING_STARTS));
	    }

	    @Override
	    public void afterParsingFinished() {
	        this.events.add(new ArgEvent(AFTER_PARSING_FINISHED));
	    }

	    @Override
	    public void onTrackChanged(byte track) {
	        this.events.add(new OneByteArgEvent(TRACK_CHANGED, track));
	    }

	    @Override
	    public void onLayerChanged(byte layer) {
	        this.events.add(new OneByteArgEvent(LAYER_CHANGED, layer));
	    }

	    @Override
	    public void onInstrumentParsed(byte instrument) {
	        this.events.add(new OneByteArgEvent(INSTRUMENT_PARSED, instrument));
	    }

	    @Override
	    public void onTempoChanged(int tempoBPM) {
	        this.events.add(new OneIntArgEvent(TEMPO_CHANGED, tempoBPM));
	    }

	    @Override
	    public void onKeySignatureParsed(byte key, byte scale) {
	        this.events.add(new TwoByteArgEvent(KEY_SIGNATURE_PARSED, key, scale));
	    }

	    @Override
	    public void onTimeSignatureParsed(byte numerator, byte powerOfTwo) {
	        this.events.add(new TwoByteArgEvent(TIME_SIGNATURE_PARSED, numerator, powerOfTwo));
	    }

	    @Override
	    public void onBarLineParsed(long time) {
	        this.events.add(new OneLongArgEvent(BAR_LINE_PARSED, time));
	    }

	    @Override
	    public void onTrackBeatTimeBookmarked(String timeBookmarkId) {
	        this.events.add(new OneStringArgEvent(TRACK_BEAT_TIME_BOOKMARKED, timeBookmarkId));
	    }

	    @Override
	    public void onTrackBeatTimeBookmarkRequested(String timeBookmarkId) {
	        this.events.add(new OneStringArgEvent(TRACK_BEAT_TIME_BOOKMARK_REQUESTED, timeBookmarkId));
	    }

	    @Override
	    public void onTrackBeatTimeRequested(double time) {
	        this.events.add(new OneDoubleArgEvent(TRACK_BEAT_TIME_REQUESTED, time));
	    }

	    @Override
	    public void onPitchWheelParsed(byte lsb, byte msb) {
	        this.events.add(new TwoByteArgEvent(PITCH_WHEEL_PARSED, lsb, msb));
	    }

	    @Override
	    public void onChannelPressureParsed(byte pressure) {
	        this.events.add(new OneByteArgEvent(CHANNEL_PRESSURE_PARSED, pressure));
	    }

	    @Override
	    public void onPolyphonicPressureParsed(byte key, byte pressure) {
	        this.events.add(new TwoByteArgEvent(POLYPHONIC_PRESSURE_PARSED, key, pressure));
	    }

	    @Override
	    public void onSystemExclusiveParsed(byte... bytes) {
	        this.events.add(new ByteArrayArgEvent(SYSEX_PARSED, bytes));
	    }

	    @Override
	    public void onControllerEventParsed(byte controller, byte value) {
	        this.events.add(new TwoByteArgEvent(CONTROLLER_EVENT_PARSED, controller, value));
	    }

	    @Override
	    public void onLyricParsed(String lyric) {
	        this.events.add(new OneStringArgEvent(LYRIC_PARSED, lyric));
	    }

	    @Override
	    public void onMarkerParsed(String marker) {
	        this.events.add(new OneStringArgEvent(MARKER_PARSED, marker));
	    }

	    @Override
	    public void onFunctionParsed(String id, Object message) {
	        this.events.add(new UserEventArgEvent(USER_EVENT_PARSED, id, message));
	    }

        @Override
        public void onNotePressed(Note note) {
            this.events.add(new NoteArgEvent(NOTE_PARSED, note));
        }
        
        @Override
        public void onNoteReleased(Note note) {
            this.events.add(new NoteArgEvent(NOTE_PARSED, note));
        }
        
	    @Override
	    public void onNoteParsed(Note note) {
	        this.events.add(new NoteArgEvent(NOTE_PARSED, note));
	    }
	    
	    @Override
	    public void onChordParsed(Chord chord) {
	    	this.events.add(new ChordArgEvent(CHORD_PARSED, chord));
	    }
	}
	
    public static final String BEFORE_PARSING_STARTS = "beforeParsingStarts";
	public static final String AFTER_PARSING_FINISHED = "afterParsingFinished";
	public static final String TRACK_CHANGED = "trackChanged";
	public static final String LAYER_CHANGED = "layerChanged";
	public static final String INSTRUMENT_PARSED = "instrumentParsed";
	public static final String TEMPO_CHANGED = "tempoChanged";
	public static final String BAR_LINE_PARSED = "barLineParsed";
	public static final String KEY_SIGNATURE_PARSED = "keySignatureParsed";
	public static final String TIME_SIGNATURE_PARSED = "timeSignatureParsed";
	public static final String TRACK_BEAT_TIME_REQUESTED = "trackBeatTimeRequested";
	public static final String PITCH_WHEEL_PARSED = "pitchWheelParsed";
	public static final String POLYPHONIC_PRESSURE_PARSED = "polyphonicPressureParsed";
	public static final String CONTROLLER_EVENT_PARSED = "controllerEventParsed";
	public static final String CHANNEL_PRESSURE_PARSED = "channelPressureParsed";
	public static final String LYRIC_PARSED = "lyricParsed";
	public static final String MARKER_PARSED = "markerParsed";
	public static final String NOTE_PARSED = "noteParsed";
	public static final String CHORD_PARSED = "chordParsed";
	public static final String TRACK_BEAT_TIME_BOOKMARK_REQUESTED = "trackBeatTimeBookmarkRequested";
	public static final String TRACK_BEAT_TIME_BOOKMARKED = "trackBeatTimeBookmarked";
	public static final String SYSEX_PARSED = "sysexParsed";
	public static final String USER_EVENT_PARSED = "userEventParsed";
}
