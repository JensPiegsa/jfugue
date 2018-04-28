package org.jfugue.realtime;

import javax.sound.midi.MidiUnavailableException;

import org.jfugue.theory.Note;

public class RealtimeInterpolatorDemo {
    public static void main(String[] args) throws MidiUnavailableException {
        RealtimePlayer player = new RealtimePlayer();
        RealtimeInterpolator ri = new RealtimeInterpolator() {
            int pitchBend;
            
            @Override
            public void update(RealtimePlayer realtimePlayer, long elapsedTime, double percentComplete) {
                realtimePlayer.setPitchBend(pitchBend++);
            }
        };
        player.startInterpolator(ri, 3000);
        Note note = new Note("C");
        player.changeInstrument("Flute");
        player.startNote(note);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) { }
        player.stopNote(note);
        player.close();
    }
}
