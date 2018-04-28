package org.jfugue.examples.book;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

import org.jfugue.midi.MidiDictionary;
import org.jfugue.midi.MidiParser;
import org.jfugue.parser.ParserListenerAdapter;

public class InstrumentToolExample {
    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        MidiParser midiParser = new MidiParser();
        InstrumentTool instrumentTool = new InstrumentTool();
        midiParser.addParserListener(instrumentTool);
        midiParser.parse(MidiSystem.getSequence(new File("filename")));
        List<String> instrumentNames = instrumentTool.getInstrumentNames();
        for (String name : instrumentNames) {
            System.out.println(name);
        }
    }
}

class InstrumentTool extends ParserListenerAdapter 
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
}
