package org.jfugue.examples.website;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

import org.jfugue.midi.MidiDictionary;
import org.jfugue.midi.MidiParser;
import org.jfugue.parser.ChainingParserListenerAdapter;
import org.jfugue.parser.ParserListenerAdapter;
import org.jfugue.theory.Note;
import org.staccato.StaccatoParserListener;

public class ChainingParserListenerDemo {
    public static void main(String[] args) throws InvalidMidiDataException, IOException {
        MidiParser parser = new MidiParser();
        InstrumentChangingParserListener instrumentChanger = new InstrumentChangingParserListener();
        StaccatoParserListener staccatoListener = new StaccatoParserListener();
        instrumentChanger.addParserListener(staccatoListener);
        parser.addParserListener(instrumentChanger);
        parser.parse(MidiSystem.getSequence(new File("Y:\\MIDI\\The_Way_I_Am.mid")));
        System.out.println("Changed "+instrumentChanger.counter+" Piano's to Guitar! "+ staccatoListener.getPattern().toString());
    }
}

class InstrumentChangingParserListener extends ChainingParserListenerAdapter {
	int counter = 0;
    @Override 
    public void onInstrumentParsed(byte instrument) {
    	if (instrument == MidiDictionary.INSTRUMENT_STRING_TO_BYTE.get("PIANO")) {
    		instrument = MidiDictionary.INSTRUMENT_STRING_TO_BYTE.get("GUITAR");
    		counter++;
    	}
    	super.onInstrumentParsed(instrument);
    }
}

