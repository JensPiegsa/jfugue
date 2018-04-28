package org.jfugue.examples.website;

import org.jfugue.player.Player;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Note;

public class IntroToChordProgressions1 {
  public static void main(String[] args) {
    ChordProgression cp = new ChordProgression("I IV V");
    Chord[] chords = cp.setKey("C").getChords();
    for (Chord chord : chords) {
      System.out.print("Chord "+chord+" has these notes: ");
      Note[] notes = chord.getNotes();
      for (Note note : notes) {
        System.out.print(note+" ");
      }
      System.out.println();
    }

    Player player = new Player();
    player.play(cp);
  }
}
