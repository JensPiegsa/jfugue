package org.jfugue.examples.book;

import java.util.Random;

import javax.sound.midi.MidiUnavailableException;

import org.jfugue.devices.MusicTransmitterToParserListener;
import org.jfugue.devtools.MidiDevicePrompt;
import org.jfugue.parser.ParserListenerAdapter;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

public class ChordGame {
    public static void main(String[] args) throws MidiUnavailableException {
        MusicTransmitterToParserListener transmitter = new MusicTransmitterToParserListener(MidiDevicePrompt.askForMidiDevice());
        ChordGameParserListener listener = new ChordGameParserListener();
        transmitter.addParserListener(listener);
        
        String[] rootNames = Note.NOTE_NAMES_COMMON;
        String[] chordNames = Chord.getChordNames();

        int correctGuesses = 0;
        int totalGuesses = 10;
        Random rnd = new Random();
        
        for (int i=0; i < totalGuesses; i++) {
            Chord chord = new Chord(rootNames[rnd.nextInt(rootNames.length)] + chordNames[rnd.nextInt(chordNames.length)]);
            System.out.println("Play " + chord.toHumanReadableString());
            ChordGameParserListener.GameState gameState = listener.waitForChord(chord);
            if (gameState == ChordGameParserListener.GameState.CORRECT) {
                System.out.println("CORRECT! \n\n");
                correctGuesses++;
            } else {
                System.out.println("Incorrect. \n\n");
            }
        }
        
        System.out.println("You guessed " + correctGuesses + " chords correctly out of " + totalGuesses + " challenges!");
        transmitter.close();
    }
}

class ChordGameParserListener extends ParserListenerAdapter {
    private Note[] expectedNotes;
    private boolean[] notesMatched;
    private GameState gameState;
    
    public ChordGameParserListener() {
        expectedNotes = new Note[] { };
        notesMatched = new boolean[] { };
        gameState = GameState.GUESSING;
    }
    
    public GameState waitForChord(Chord chord) {
        this.expectedNotes = chord.getNotes();
        this.notesMatched = new boolean[this.expectedNotes.length];
        this.gameState = GameState.GUESSING;
        
        for (Note note : expectedNotes) {
            System.out.println(note);
        }
        
        // Just spin until the state changes to either CORRECT or WRONG
        while (gameState == GameState.GUESSING) {
            try { Thread.sleep(50); } catch (InterruptedException e) { }
        }
        
        return gameState;
    }
    
    @Override
    public void onNoteParsed(Note note) {
        // If the note played matches one of the notes expected, yay!
        boolean matchFound = false;
        for (int i=0; i < expectedNotes.length; i++) {
            if (expectedNotes[i].getPositionInOctave() == note.getPositionInOctave()) {
                notesMatched[i] = true;
            }
        }
        
        // If the player played an unexpected note, that's wrong!
        if (!matchFound) {
            this.gameState = GameState.WRONG;
        }
        
        // If we haven't yet matched all chord notes, exit
        for (int i=0; i < notesMatched.length; i++) {
            if (!notesMatched[i]) return;
        }
                
        // If we've gotten this far, all of the notes have matched!
        this.gameState = GameState.CORRECT;
    }
    
    public enum GameState { GUESSING, CORRECT, WRONG };
}
