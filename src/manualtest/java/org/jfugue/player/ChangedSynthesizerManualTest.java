package org.jfugue.player;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class ChangedSynthesizerManualTest {
    public static void main(String[] args) throws MidiUnavailableException, InterruptedException, InvalidMidiDataException, IOException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        Soundbank soundbank = MidiSystem.getSoundbank(new File("C:\\My Projects\\JFugue\\Soundfonts\\SONiVOX_GS250.SF2"));
        synth.loadInstrument(soundbank.getInstruments()[5]);

        SynthesizerManager.getInstance().setSynthesizer(synth);
        Player player = new Player();
        player.play("I5 Cw");

//        MidiChannel[] mc = synth.getChannels();
//        mc[5].programChange(5);
//        mc[5].noteOn(60, 100);
//        Thread.sleep(2000);
//        mc[5].noteOff(60);
    }
}
