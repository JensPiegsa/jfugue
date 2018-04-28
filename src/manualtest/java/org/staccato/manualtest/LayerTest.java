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
import org.jfugue.player.Player;

/**
 * This test ensures that the Voice element is being parsed and interpreted consistently.
 */
public class LayerTest {
	public static void main(String[] args) {
		ManualTestPrint.expectedResult("You should hear the same sequence of three chords, played twice.");
		ManualTestPrint.expectedResult("The first group of three chords should sound identical to the second group of three chords.");
		
	    Player player = new Player();
        player.play("V1 L0 C L1 E L2 G L0 D L1 F L2 A L0 E L1 G L2 B " +
                "#(That previous half should sound the same as this upcoming half) " +
                "V1 L0 C D E L1 E F G L2 G A B"); 
	}
}
