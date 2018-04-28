package org.jfugue.examples.website;

import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;

public class AdvancedRhythms {
    public static void main(String[] args) {
        Rhythm rhythm = new Rhythm().addLayer("O..oO...O..oOO..")
                .addLayer("..S...S...S...S.")
                .addLayer("````````````````")
                .addLayer("...............+")
                .addOneTimeAltLayer(3, 3, "...+...+...+...+")
                .setLength(4);
        new Player().play(rhythm.getPattern().repeat(2));
    }
}
