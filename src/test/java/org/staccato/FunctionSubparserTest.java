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

package org.staccato;

import static org.junit.Assert.assertTrue;

import org.jfugue.testtools.parser.JFugueTestHelper;
import org.junit.Test;

public class FunctionSubparserTest extends JFugueTestHelper {
    @Test
    public void testPitchWheelSubparser() {
        assertTrue(compare(":PW(7, 120)", PITCH_WHEEL_PARSED, (byte)7, (byte)120));
        assertTrue(compare(":PITCHWHEEL(8, 125)", PITCH_WHEEL_PARSED, (byte)8, (byte)125));
        assertTrue(compare(":PITCHBEND(16008)", PITCH_WHEEL_PARSED, (byte)8, (byte)125));
        assertTrue(compare(":pb(0, 64)", PITCH_WHEEL_PARSED, (byte)0, (byte)64));
        assertTrue(compare(":PitchWheel(8192)", PITCH_WHEEL_PARSED, (byte)0, (byte)64));
    }
    
    @Test
    public void testChannelPressureSubparser() {
        assertTrue(compare(":CP(7)", CHANNEL_PRESSURE_PARSED, (byte)7));
        assertTrue(compare(":CHANNELPRESSURE(8)", CHANNEL_PRESSURE_PARSED, (byte)8));
    }

    @Test
    public void testPolyPressureSubparser() {
        assertTrue(compare(":PP(7, 120)", POLYPHONIC_PRESSURE_PARSED, (byte)7, (byte)120));
        assertTrue(compare(":POLYPRESSURE(8, 125)", POLYPHONIC_PRESSURE_PARSED, (byte)8, (byte)125));
        assertTrue(compare(":POLYPHONICPRESSURE(8,125)", POLYPHONIC_PRESSURE_PARSED, (byte)8, (byte)125));
        assertTrue(compare(":PolyphonicPressure(8, 125)", POLYPHONIC_PRESSURE_PARSED, (byte)8, (byte)125));
    }

    @Test
    public void testControllerFunctionSubparser() {
        assertTrue(compare(":CON(7, 120)", CONTROLLER_EVENT_PARSED, (byte)7, (byte)120));
        assertTrue(compare(":CONTROLLER(8, 125)", CONTROLLER_EVENT_PARSED, (byte)8, (byte)125));
    }

    @Test
    public void testSysExFunctionSubparser() {
    	assertTrue(compare(":SYSEX(7, 120)", SYSEX_PARSED, new byte[] { (byte)7, (byte)120 }));
        assertTrue(compare(":SystemExclusive(8,125,70,40)", SYSEX_PARSED, new byte[] { (byte)8, (byte)125, (byte)70, (byte)40 }));
    }
}
