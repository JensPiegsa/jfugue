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

package org.jfugue.theory;

import org.jfugue.pattern.ReplacementFormatUtil;
import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.PatternProducer;
import org.jfugue.provider.KeyProviderFactory;
import org.jfugue.provider.NoteProviderFactory;
import org.staccato.StaccatoUtil;

public class ChordProgression implements PatternProducer {
    private String[] progressionElements;
    private Chord[] knownChords = null;
    private Key key;
    private String allSequence;
    private String eachSequence;

    // Private constructor for .fromChords static methods
    private ChordProgression() { }
    
    /** 
     * Creates a chord progression given a Progression String, like "I vi ii V" - case is important! 
     * Chords can be separated with spaces ("I vi ii V") or dashes ("I-vi-ii-V"). */
	public ChordProgression(String progression) {
		createProgression(progression.split("[- ]")); // Split on either spaces or dashes // TODO Make ChordProgression parse on [- +]
	}
	
	/** Creates a chord progression given an array of Progression Strings, like { "I", "vi", "ii", "V" } - case is important! */
	public ChordProgression(String[] progressionElements) {
		createProgression(progressionElements);
	}
	
	private void createProgression(String[] progressionElements) {
		this.progressionElements = progressionElements;
		this.key = Key.DEFAULT_KEY;
	}

	public static ChordProgression fromChords(String knownChords) {
		String[] knownChordStrings = knownChords.split(" +");
		ChordProgression cp = new ChordProgression();
		cp.knownChords = new Chord[knownChordStrings.length];
		for (int i=0; i < knownChordStrings.length; i++) {
			cp.knownChords[i] = new Chord(knownChordStrings[i]); 
		}
		return cp;
	}
	
	public static ChordProgression fromChords(Chord... chords) {
		ChordProgression cp = new ChordProgression();
		cp.knownChords = chords;
		return cp;
	}
	
	/** The key usually identifies the tonic note and/or chord [Wikipedia] */
	public ChordProgression setKey(String key) {
	    return setKey(KeyProviderFactory.getKeyProvider().createKey(key));
	}
	
	public ChordProgression setKey(Key key) {
	    this.key = key;
	    return this;
	}
	
	@Override
	public Pattern getPattern() {
	    Pattern pattern = new Pattern();
	    for (Chord chord : getChords()) {
	        pattern.add(chord);
	    }

	    if (allSequence != null) {
	    	pattern = ReplacementFormatUtil.replaceDollarsWithCandidates(allSequence, getChords(), new Pattern(getChords()));
	    }
	    
	    if (eachSequence != null) {
	    	Pattern p2 = new Pattern();
	    	for (String chordString : pattern.toString().split(" ")) { // TODO Should be " +"
	    		Chord chord = new Chord(chordString);
	    		p2.add(ReplacementFormatUtil.replaceDollarsWithCandidates(eachSequence, chord.getNotes(), chord));
	    	}
	    	pattern = p2;
	    }
	    
	    return pattern;
	}
	
	/**
	 * Returns a list of chords represented by this chord progression.
	 */
	public Chord[] getChords() {
		if (knownChords != null) {
			return knownChords;
		}
		Chord[] chords = new Chord[progressionElements.length];
	    Pattern scalePattern = key.getScale().getIntervals().setRoot(key.getRoot()).getPattern();
	    String[] scaleNotes = scalePattern.toString().split(" ");
	    int counter = 0;
	    for (String progressionElement : progressionElements) {
	        Note rootNote = NoteProviderFactory.getNoteProvider().createNote(scaleNotes[romanNumeralToIndex(progressionElement)]);
	        rootNote.useSameDurationAs(key.getRoot());
	        Intervals intervals = Chord.MAJOR_INTERVALS;
	        if ((progressionElement.charAt(0) == 'i') || (progressionElement.charAt(0) == 'v')) {
	        	// Checking to see if the progression element is lowercase 
	            intervals = Chord.MINOR_INTERVALS;
	        }
	        if ((progressionElement.toLowerCase().indexOf("o") > 0) || (progressionElement.toLowerCase().indexOf("d") > 0)) {
	        	// Checking to see if the progression element is diminished
	            intervals = Chord.DIMINISHED_INTERVALS; 
	        }
	        if (progressionElement.endsWith("7")) {
	            if (intervals.equals(Chord.MAJOR_INTERVALS)) {
	                intervals = Chord.MAJOR_SEVENTH_INTERVALS;
	            } else if (intervals.equals(Chord.MINOR_INTERVALS)) {
                    intervals = Chord.MINOR_SEVENTH_INTERVALS;
	            } else if (intervals.equals(Chord.DIMINISHED_INTERVALS)) {
                    intervals = Chord.DIMINISHED_SEVENTH_INTERVALS;
	            }
	        }
	        if (progressionElement.endsWith("7%6")) {
	            if (intervals.equals(Chord.MAJOR_INTERVALS)) {
	                intervals = Chord.MAJOR_SEVENTH_SIXTH_INTERVALS;
	            } else if (intervals.equals(Chord.MINOR_INTERVALS)) {
                    intervals = Chord.MINOR_SEVENTH_SIXTH_INTERVALS;
	            }
	        }
	        
	        // Check for inversions
	        int inversions = countInversions(progressionElement);

	        chords[counter] = new Chord(rootNote, intervals);
	        if (inversions > 0) { chords[counter].setInversion(inversions); }
	        counter++;
	    }
	    return chords;
	}
	
	/** 
	 * Only converts Roman numerals I through VII, because that's all we need in music theory... 
	 * VIII would be the octave and equal I!
	 */
	private int romanNumeralToIndex(String romanNumeral) {
		String s = romanNumeral.toLowerCase();
		if (s.startsWith("vii")) { return 6; } 
		else if (s.startsWith("vi")) { return 5; }
		else if (s.startsWith("v")) { return 4; }
		else if (s.startsWith("iv")) { return 3; }
		else if (s.startsWith("iii")) { return 2; }
		else if (s.startsWith("ii")) { return 1; }
		else if (s.startsWith("i")) { return 0; }
		else { return 0; }
	}
	
	private int countInversions(String s) {
		int counter = 0;
		for (int i=0; i < s.length(); i++) {
			if (s.charAt(i) == '^') { counter++; }
		}
		return counter;
	}
	
	public String toString() {
		return getPattern().toString();
	}
	
	public String[] toStringArray() {
		return getPattern().toString().split(" ");
	}
	
	/**
	 * Requires passing a string that has dollar signs followed by an index, in which case each dollar+index will be replaced
	 * by the indexed note of the chord for each chord in the progression. For example, given a ChordProgression of "I IV V"
	 * and a string of "$0q $1h $2w", will return "Cq E4h G4w Fq A4h C5w Gq B4h D5w". Using the underscore character instead
	 * of an index will result in the chord itself added to the string. The final result will be returned from the getPattern()
	 * method.
	 */
	public ChordProgression eachChordAs(String sequence) {
		this.eachSequence = sequence;
		return this;
	}

	/**
	 * Requires passing a string that has dollar signs followed by an index, in which case each dollar+index will be replaced
	 * by the indexed chord of the chord progression. For example, given a ChordProgression of "I IV V"
	 * and a string of "$0q $1h $2w", will return "C4MAJq F4MAJh G4MAJw". Using the underscore character instead of an
	 * index will result in the pattern of the ChordProgression itself added to the string. The final result will be returned
	 * from the getPattern() method.
	 */
	public ChordProgression allChordsAs(String sequence) {
		this.allSequence = sequence;
		return this;
	}
	
	public ChordProgression distribute(String distribute) {
		for (int i=0; i < progressionElements.length; i++) {
			progressionElements[i] = progressionElements[i] + distribute;
		}
		return this;
	}
}

