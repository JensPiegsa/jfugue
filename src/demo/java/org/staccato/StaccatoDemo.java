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

import org.jfugue.demo.DemoPrint;
import org.jfugue.player.Player;

public class StaccatoDemo {
	public static void main(String[] args) {
		Player player = new Player();
		
		// This is one of the simplest Staccato strings that you can play.
		DemoPrint.start("A simple Staccato string.");
		player.play("C D E F G A B");
		
		// This Staccato string is more typical of the kind of music you might 
		// expect to see (and write). There are changes in voice (or channels)
		// and two different instruments in play. There is also a rest in V1. 
		DemoPrint.step("A typical Staccato string with voices, instruments, and durations.");
		player.play("V0 I[Piano] Cq Cq Cq Dq Eq V1 I[Flute] Rq Eq Gq Fq Gq");
		
		// This string is more complicated: Each C note has an octave provided, 
		// and there are a variety of durations, from whole down to thirty-second,
		// followed by a quarter and a dotted quarter, followed by a combined 
		// whole+half duration, then a numeric duration, then a large combined duration. 
		DemoPrint.step("A more complicated Staccato string.");
		player.play("C0w C1h C2q C3i C4s C5t Rq C6q. C7wh C8/1.5 C9qqqqiiiss");
	}
}
