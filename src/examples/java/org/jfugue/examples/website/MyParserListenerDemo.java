package org.jfugue.examples.website;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

import org.jfugue.midi.MidiParser;
import org.jfugue.parser.ParserListenerAdapter;
import org.jfugue.theory.Note;

public class MyParserListenerDemo {

    public static void main(String[] args) throws InvalidMidiDataException, IOException {
        MidiParser parser = new MidiParser();
        MyParserListener listener = new MyParserListener();
        parser.addParserListener(listener);
        parser.parse(MidiSystem.getSequence(new File("C:\\My Media\\MIDI\\The_Way_I_Am.mid")));
        System.out.println("There are "+listener.counter+" 'C' notes in this music.");
    }
}

class MyParserListener extends ParserListenerAdapter {
    public int counter;
    
    @Override 
    public void onNoteParsed(Note note) {
        if (note.getPositionInOctave() == 0) {
            counter++;
        }
    }
}
