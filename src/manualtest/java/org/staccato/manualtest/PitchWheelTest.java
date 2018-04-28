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

package org.staccato.manualtest;

import org.jfugue.manualtest.ManualTestPrint;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

/**
 *  This test ensures that the Pitch Wheel is working as expected
 */
public class PitchWheelTest {
	public static void main(String[] args) {
		ManualTestPrint.expectedResult("The following notes should all sound different");
	    Player player = new Player();
	    Pattern pattern = new Pattern("V0 60w");

	    for (int i=0; i < 16384; i+=150) {
  	      pattern.add(":PW("+i+") 60t");
	    }
	    
	    for (int i=0; i < 127; i+=10) {
		    for (int u=0; u < 127; u+=10) {
//	    	  pattern.add(":CON(101,0) :CON(100,0) :CON(6,"+i+") :CON(38,"+u+") 60t"); // See Table 3a here: http://www.midi.org/techspecs/midimessages.php
//	    	  pattern.add(":CON(101,0) :CON(100,0) :CON(96,"+i+") :CON(97,"+u+") 60t"); // See Table 3a here: http://www.midi.org/techspecs/midimessages.php
	        } 
		}
		ManualTestPrint.step("Here is the pattern: "+pattern);
	    player.play(pattern); 
	}
}
