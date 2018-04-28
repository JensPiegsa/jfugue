package org.jfugue.midi;

import javax.sound.midi.Sequence;

import org.jfugue.pattern.Pattern;
import org.staccato.StaccatoParser;
import org.staccato.StaccatoParserListener;

public class MidiRoundTripRunner {
	public static void run(String patternString) {
		// First, convert Staccto to MIDI
		Pattern pattern = new Pattern(patternString); 
		MidiParserListener midiParserListener = new MidiParserListener();
		StaccatoParser staccatoParser = new StaccatoParser();
		staccatoParser.addParserListener(midiParserListener);
		staccatoParser.parse(pattern);
		Sequence sequence = midiParserListener.getSequence();
		
		// Next, convert MIDI to Staccato
		StaccatoParserListener staccatoParserListener = new StaccatoParserListener();
		MidiParser midiParser = new MidiParser();
		midiParser.addParserListener(staccatoParserListener);
		midiParser.parse(sequence);
		Pattern reconstitutedPattern = staccatoParserListener.getPattern();
		
		System.out.println("Original Pattern:      " + pattern.toString());
		System.out.println("Reconstituted Pattern: " + reconstitutedPattern.toString());
	}
}
