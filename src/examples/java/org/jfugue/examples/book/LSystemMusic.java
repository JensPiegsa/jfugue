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

package org.jfugue.examples.book;

import java.util.HashMap;
import java.util.Map;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.ReplacementMapPreprocessor;

public class LSystemMusic {
	public static void main(String[] args) {
		
		// Specify the transformation rules for this Lindenmayer system
		Map<String, String> rules = new HashMap<String, String>() {{
	        put("Cmajw", "Cmajw Fmajw");
	        put("Fmajw", "Rw Bbmajw");
	        put("Bbmajw", "Rw Fmajw");
	        put("C5q", "C5q G5q E6q C6q");
	        put("E6q", "G6q D6q F6i C6i D6q");
	        put("G6i+D6i", "Rq Rq G6i+D6i G6i+D6i Rq");
	        put("axiom", "axiom V0 I[Flute] Rq C5q V1 I[Tubular_Bells] Rq Rq Rq G6i+D6i V2 I[Piano] Cmajw E6q V3 I[Warm] E6q G6i+D6i V4 I[Voice] C5q E6q");
		}};
		
		// Set up the ReplacementMapPreprocessor to iterate 3 times 
		// and not require brackets around replacements
		ReplacementMapPreprocessor rmp = ReplacementMapPreprocessor.getInstance();
		rmp.setReplacementMap(rules);
		rmp.setIterations(3);
		rmp.setRequireAngleBrackets(false);
		
		// Create a Pattern that contains the L-System axiom
		Pattern axiom = new Pattern("T120 " + "V0 I[Flute] Rq C5q "
                + "V1 I[Tubular_Bells] Rq Rq Rq G6i+D6i "
                + "V2 I[Piano] Cmajw E6q " 
                + "V3 I[Warm] E6q G6i+D6i "
                + "V4 I[Voice] C5q E6q");

//		Pattern axiom = new Pattern("T120 axiom");

		Player player = new Player();
		System.out.println(rmp.preprocess(axiom.toString(), null));
		player.play(rmp.preprocess(axiom.toString(), null));
	}
}
