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

package org.jfugue.pattern;

import static org.junit.Assert.assertTrue;

import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.junit.Test;

public class TrackTableTest {
    @Test
    public void testSimpleTrackTable() {
        TrackTable t = new TrackTable(10, 1.0d);
        t.put(0, 0, new Pattern("A B C D"));
    	assertTrue(t.toString().equals("V0 A B C D V0 R/1.0 V0 R/1.0 V0 R/1.0 V0 R/1.0 V0 R/1.0 V0 R/1.0 V0 R/1.0 V0 R/1.0 V0 R/1.0 V0 R/1.0 V1 R/1.0 V1 R/1.0 V1 R/1.0 V1 R/1.0 V1 R/1.0 V1 R/1.0 V1 R/1.0 V1 R/1.0 V1 R/1.0 V1 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0"));
    }

    @Test
    public void testCoolPlacement() {
        TrackTable t = new TrackTable(5, 1.0d);
        t.put(0, "X-.X-", new Pattern("Cmajh Emajh Gmajh Emajh"));
        t.put(1, ".X..X", new Pattern("Gq Cq Eq Gq"));
        assertTrue(t.toString().equals("V0 Cmajh Emajh Gmajh Emajh V0  V0 R/1.0 V0 Cmajh Emajh Gmajh Emajh V0  V0 R/1.0 V0 R/1.0 V0 R/1.0 V0 R/1.0 V1 R/1.0 V1 Gq Cq Eq Gq V1 R/1.0 V1 R/1.0 V1 Gq Cq Eq Gq V1 R/1.0 V1 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V2 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V3 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V4 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V5 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V6 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V7 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V8 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V9 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V10 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V11 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V12 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V13 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V14 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0 V15 R/1.0"));
    }

    @Test
    public void testTrackSettingsAndFluent() {
        TrackTable t = new TrackTable(5, 1.0d)
                .put(0, "X-.X-", new Pattern("Cmajh Emajh Gmajh Emajh"))
                .put(1, ".X..X", new Pattern("Gq Cq Eq Gq"))
                .setTrackSettings(0, "I[Flute]")
                .setTrackSettings(1, "I[Piano]");
        assertTrue(t.toString().startsWith("V0 I[Flute] V1 I[Piano]"));
    }

    @Test
    public void testGetPatternAt() {
        TrackTable t = new TrackTable(5, 1.0d)
                .put(0, "X-.X-", new Pattern("Cmajh Emajh Gmajh Emajh"))
                .put(1, ".X..X", new Pattern("Gq Cq Eq Gq"))
                .setTrackSettings(0, "I[Flute]")
                .setTrackSettings(1, "I[Piano]");
        assertTrue(t.getPatternAt(1).toString().equals("V0  V1 Gq Cq Eq Gq V2 R/1.0 V3 R/1.0 V4 R/1.0 V5 R/1.0 V6 R/1.0 V7 R/1.0 V8 R/1.0 V9 R/1.0 V10 R/1.0 V11 R/1.0 V12 R/1.0 V13 R/1.0 V14 R/1.0 V15 R/1.0"));
    }

    @Test
    public void testPutRhythmInTrack() {
        // Rhythm from RhythmTest.testAltsInMultipleLayers()
        Rhythm rhythm = new Rhythm(); 
        rhythm.addLayer("So").addLayer("xx").setLength(10);
        rhythm.addRecurringAltLayer(0, 0, 7, 2, "..");
        rhythm.addRangedAltLayer(1, 1, 5, "XX");
        rhythm.addOneTimeAltLayer(1, 4, "**");
        assertTrue(rhythm.getRhythm()[0].equals("..So..So..So..SoSoSo"));
        assertTrue(rhythm.getRhythm()[1].equals("xxXXXXXX**XXxxxxxxxx"));
        
        // Now put it in a TrackTable!
        TrackTable t = new TrackTable(10, 1.0d);
        t.put(rhythm);
        assertTrue(t.get(9, 3).toString().equals("V9 L0 [ACOUSTIC_SNARE]i Rs [BASS_DRUM]s L1 [HAND_CLAP]i [HAND_CLAP]i"));
        assertTrue(t.get(9, 9).toString().equals("V9 L0 [ACOUSTIC_SNARE]i Rs [BASS_DRUM]s L1 Rs [HAND_CLAP]s Rs [HAND_CLAP]s"));
    }

}
