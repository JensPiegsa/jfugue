/*
 * JFugue, an Application Programming Interface (API) for Music Programming
 * http://www.jfugue.org
 *
 * Copyright (C) 2003-2014 David Koelle
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jfugue.devices;

import java.io.File;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import org.jfugue.devtools.MidiDevicePrompt;

public class MusicReceiverDemo {
    public static void main(String[] args) {
        try {
          MidiDevice device = MidiDevicePrompt.askForMidiDevice();
          MusicReceiver r = new MusicReceiver(device);

          Sequence sequence = MidiSystem.getSequence(new File("C:\\My Media\\MIDI\\Keep_Their_Heads_Ringing.mid")); 
          r.sendSequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
