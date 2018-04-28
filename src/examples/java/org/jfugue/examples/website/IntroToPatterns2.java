package org.jfugue.examples.website;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class IntroToPatterns2 {
  public static void main(String[] args) {
    Pattern p1 = new Pattern("Eq Ch. | Eq Ch. | Dq Eq Dq Cq").setVoice(0).setInstrument("Piano");
    Pattern p2 = new Pattern("Rw     | Rw     | GmajQQQ  CmajQ").setVoice(1).setInstrument("Flute");
    Player player = new Player();
    player.play(p1, p2);
  }
}