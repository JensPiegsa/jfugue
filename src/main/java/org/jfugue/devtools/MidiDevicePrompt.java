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

package org.jfugue.devtools;

import java.util.Scanner;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class MidiDevicePrompt {
    public static MidiDevice askForMidiDevice() {
        try {
            System.out.println("Available MIDI Devices:");
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            for (int i=0; i < infos.length; i++) {
                System.out.println("  "+i+". "+infos[i].getName()+" - "+infos[i].getDescription());
            }
            System.out.print("Enter the number of the device you want to use: ");
            Scanner scanner = new Scanner(System.in);
            int a = scanner.nextInt();
            scanner.close();
            
            return MidiSystem.getMidiDevice(infos[a]);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }
}
