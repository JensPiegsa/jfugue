package org.jfugue.examples.website;

import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

public class AdvancedChordProgressions {
  public static void main(String[] args) {
    ChordProgression cp = new ChordProgression("I IV V");

    Player player = new Player();
//    player.play(cp.eachChordAs("$0q $1q $2q Rq"));
    
//    player.play(cp.allChordsAs("$0q $0q $0q $0q $1q $1q $2q $0q"));

    player.play(cp.allChordsAs("$0 $0 $0 $0 $1 $1 $2 $0").eachChordAs("V0 $0s $1s $2s Rs V1 $_q"));

  }
}