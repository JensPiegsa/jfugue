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

import java.util.HashMap;
import java.util.Map;

import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.PatternProducer;
import org.jfugue.pattern.ReplacementFormatUtil;
import org.jfugue.theory.Chord;

public class BrokenChordPreprocessor implements Preprocessor
{
	private static BrokenChordPreprocessor instance;
	
	public static BrokenChordPreprocessor getInstance() {
		if (instance == null) {
			instance = new BrokenChordPreprocessor();
		}
		return instance;
	}

	@Override
	public String preprocess(String incomingString, StaccatoParserContext context) {
		StringBuilder retVal = new StringBuilder();
		
		String[] splitsville = incomingString.split(" ");
		for (String s : splitsville) {
		    int posColon = 0;
		    if (((posColon = s.indexOf(':')) != -1) && (posColon > 0)) {
		        // Get the chord
		        String candidateChord = s.substring(0, posColon);
	
		        // We don't want to think we have a chord when we really have a key signature or time signature, or 
		        // we have a tuplet (indicated by the asterisk)
		        if (Chord.isValidChord(candidateChord)) {
			        Chord chord = new Chord(candidateChord);
		
			        // Get the replacement description
			        int posColon2 = StaccatoUtil.findNextOrEnd(s, ':', posColon+1);
			        String replacementDescription = s.substring(posColon+1, posColon2);
			        String dynamics = (posColon2 == s.length()) ? "" : s.substring(posColon2+1, s.length());
		
			        // If the replacement description starts with a bracket, look up the value in the context's dictionary
			        if (replacementDescription.charAt(0) == '[') {
			            String replacementLookup = replacementDescription.substring(1, replacementDescription.length()-1);
			            replacementDescription = (String)context.getDictionary().get(replacementLookup);
			        }
		
			        Map<String, PatternProducer> specialReplacers = new HashMap<String, PatternProducer>();
			        specialReplacers.put("ROOT", chord.getRoot());
			        specialReplacers.put("BASS", chord.getBassNote());
			        specialReplacers.put("NOTROOT", wrapInParens(chord.getPatternWithNotesExceptRoot()));
			        specialReplacers.put("NOTBASS", wrapInParens(chord.getPatternWithNotesExceptBass()));
			        
			        Pattern result = ReplacementFormatUtil.replaceDollarsWithCandidates(replacementDescription, chord.getNotes(), wrapInParens(chord.getPatternWithNotes()), specialReplacers, ",", " ", dynamics);
			        retVal.append(result.toString());
		        } else {
		        	retVal.append(s);
		        }
		    } else {
		    	retVal.append(s);
		    }
		    retVal.append(" ");
		}
		return retVal.toString().trim();
	}
	
	private Pattern wrapInParens(Pattern s) {
		return new Pattern("(" + s.toString() + ")");
	}
}
