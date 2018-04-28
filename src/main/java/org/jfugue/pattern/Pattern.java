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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jfugue.midi.MidiDefaults;
import org.jfugue.midi.MidiDictionary;
import org.jfugue.parser.ParserListener;
import org.jfugue.pattern.Token.TokenType;
import org.staccato.IVLSubparser;
import org.staccato.StaccatoParser;
import org.staccato.StaccatoParserPatternHelper;
import org.staccato.TempoSubparser;

public class Pattern implements PatternProducer, TokenProducer
{
	protected StringBuilder patternSB;
	private int explicitVoice = UNDECLARED_EXPLICIT;
	private int explicitLayer = UNDECLARED_EXPLICIT;
	private int explicitInstrument = UNDECLARED_EXPLICIT;
	private int explicitTempo = UNDECLARED_EXPLICIT;
	
	public Pattern() { 
	    patternSB = new StringBuilder();
	}
	    
	public Pattern(String string) {
		this();
	    patternSB.append(string);
	}
	
	public Pattern(String... strings) {
		this();
	    for (String string : strings) {
	    	patternSB.append(string);
	    	patternSB.append(" ");
	    }
	}
	
    public Pattern(PatternProducer... producers) {
        this();
        this.add(producers);
    }
    
    public Pattern add(PatternProducer... producers) {
        for (PatternProducer producer : producers) {
            this.add(producer.getPattern().toString());
        }
        return this;
    }
    
    public Pattern add(String string) {
        if (patternSB.length() > 0) {
        	patternSB.append(" ");
        }
        patternSB.append(string);
        return this;
    }

    public Pattern add(PatternProducer producer, int repetitions) {
        for (int i=0; i < repetitions; i++) {
            this.add(producer);
        }
        return this;
    }

    public Pattern add(String string, int repetitions) {
        for (int i=0; i < repetitions; i++) {
            this.add(string);
        }
        return this;
    }

    /**
     * Prepends each producer in the order it is passed in, 
     * so if you pass in "F F", "G G", and "E E", and the current
     * pattern is "A A", you will get "F F G G E E A A".
     */
    public Pattern prepend(PatternProducer... producers) {
        StringBuilder temp = new StringBuilder();
        for (PatternProducer producer : producers) {
            temp.append(producer.getPattern().toString());
            temp.append(" ");
        }
        this.prepend(temp.toString().trim());
        return this;
    }
    
    /** 
     * Inserts the given string to the beginning of this pattern.
     * If there is content in this pattern already, this method will
     * insert a space between the given string and this pattern so
     * the tokens remain separate.
     */
    public Pattern prepend(String string) {
    	if (patternSB.length() > 0) {
    		patternSB.insert(0, " ");
    	}
    	patternSB.insert(0, string);
    	return this;
    }
    
    public Pattern clear() {
    	patternSB.delete(0, patternSB.length());
    	return this;
    }
    
    public Pattern repeat(int n) {
    	Pattern p2 = new Pattern();
    	for (int i=0; i < n; i++) {
    		p2.add(this.patternSB.toString());
    	}
    	this.patternSB = p2.patternSB;
    	return this;
    }
        
    /**
     * Turns the given pattern into a pattern of Voice-Instrument-Note atoms
     * 
     * @return this pattern for method chaining
     */
    public Pattern atomize() {
    	String currentVoice;
    	String[] currentLayer = new String[MidiDefaults.TRACKS];      // Most recent layer for each voice
    	String[] currentInstrument = new String[MidiDefaults.TRACKS]; // Most recent instrument for each voice
    	List<Token> tokens = this.getTokens();

    	// Set current values
    	currentVoice = "" + IVLSubparser.VOICE + valueOrZero(this.explicitVoice);
    	currentLayer[valueOrZero(this.explicitVoice)] = "" + IVLSubparser.LAYER + valueOrZero(this.explicitLayer);
    	currentInstrument[valueOrZero(this.explicitVoice)] = "" + IVLSubparser.INSTRUMENT + valueOrZero(this.explicitInstrument);
    	
    	// Clear the current contents of pattern (except for Tempo)
    	this.patternSB.delete(0, this.patternSB.length()); 
    	this.explicitVoice = UNDECLARED_EXPLICIT;
    	this.explicitLayer = UNDECLARED_EXPLICIT;
    	this.explicitInstrument = UNDECLARED_EXPLICIT;
    	
    	int voiceCounter = 0;
    	for (Token token : tokens) {
    		String s = token.getPattern().toString();
    		switch (token.getType()) {
    		case VOICE: 
    			currentVoice = s; 
    		    voiceCounter = IVLSubparser.getInstance().getValue(currentVoice, null); 
    		    if (currentLayer[voiceCounter] == null) currentLayer[voiceCounter] = IVLSubparser.LAYER + "0"; 
    		    if (currentInstrument[voiceCounter] == null) currentInstrument[voiceCounter] = IVLSubparser.INSTRUMENT + "0"; 
    		    break;
    		case LAYER: currentLayer[voiceCounter] = s; break;
    		case INSTRUMENT: currentInstrument[voiceCounter] = s; break;
    		case NOTE: this.add(new Atom(currentVoice, currentLayer[voiceCounter], currentInstrument[voiceCounter], s)); break;
    		default: this.add(s); break;
    		} 
    	}
    	
    	return this;
    }

    /**
     * If the provided value is equal to UNDECLARED_EXPLICIT, return 0. 
     * Otherwise, return the value.
     */
    private int valueOrZero(int value) {
    	return (value == UNDECLARED_EXPLICIT ? 0 : value);
    }
   
    @Override
    public Pattern getPattern() {
	    return this;
	}
	
	@Override
	public List<Token> getTokens() {
	    StaccatoParserPatternHelper spph = new StaccatoParserPatternHelper();
	    return spph.getTokens(this.getPattern());
	}
	
	public String toString() {
		StringBuilder b2 = new StringBuilder();

		// Add the explicit tempo, if one has been provided
		if (explicitTempo != UNDECLARED_EXPLICIT) {
			b2.append(TempoSubparser.TEMPO);
			b2.append(explicitTempo);
			b2.append(" ");
		}

		// Add the explicit voice, if one has been provided
		if (explicitVoice != UNDECLARED_EXPLICIT) {
			b2.append(IVLSubparser.VOICE);
			b2.append(explicitVoice);
			b2.append(" ");
		}

		// Add the explicit layer, if one has been provided
		if (explicitLayer != UNDECLARED_EXPLICIT) {
			b2.append(IVLSubparser.LAYER);
			b2.append(explicitVoice);
			b2.append(" ");
		}

		// Add the explicit voice, if one has been provided
		if (explicitInstrument != UNDECLARED_EXPLICIT) {
			b2.append(IVLSubparser.INSTRUMENT);
			b2.append("[");
			b2.append(MidiDictionary.INSTRUMENT_BYTE_TO_STRING.get((byte)explicitInstrument));
			b2.append("] ");
		}
		
		// Now add the actual contents of the pattern!
		b2.append(patternSB);
		
		return b2.toString();
	}
	
	/*
	 * Explicit setters for tempo, voice, and instrument
	 */
	
	/**
	 * Provides a way to explicitly set the tempo on a Pattern directly
	 * through the pattern rather than by adding text to the contents
	 * of the Pattern.
	 * 
	 * When Pattern.toString() is called, the a tempo will be prepended 
	 * to the beginning of the pattern in the form of "Tx", where x is the
	 * tempo number.
     *
	 * @return this pattern 
	 */
	public Pattern setTempo(int explicitTempo) {
		this.explicitTempo = explicitTempo;
		return this;
	}
	
	/**
	 * Provides a way to explicitly set the tempo on a Pattern directly
	 * through the pattern rather than by adding text to the contents
	 * of the Pattern.
	 * 
	 * When Pattern.toString() is called, the a tempo will be prepended 
	 * to the beginning of the pattern in the form of "Tx", where x is the
	 * tempo number (even though this method takes a string as a parameter)
     *
	 * @return this pattern 
	 */
	public Pattern setTempo(String tempo) {
		if (!MidiDictionary.TEMPO_STRING_TO_INT.containsKey(tempo.toUpperCase())) {
			throw new RuntimeException("The tempo '"+tempo+"' is not recognized");
		}
		return setTempo(MidiDictionary.TEMPO_STRING_TO_INT.get(tempo.toUpperCase()));
	}
	
	/**
	 * Provides a way to explicitly set the voice on a Pattern directly
	 * through the pattern rather than by adding text to the contents
	 * of the Pattern.
	 * 
	 * When Pattern.toString() is called, the a voice will be prepended 
	 * to the beginning of the pattern after any explicit tempo and before any
	 * explicit layer in the form of "Vx", where x is the voice number
     *
	 * @return this pattern 
	 */
	public Pattern setVoice(int voice) {
		this.explicitVoice = voice;
		return this;
	}

	/**
	 * Provides a way to explicitly set the layer on a Pattern directly
	 * through the pattern rather than by adding text to the contents
	 * of the Pattern.
	 * 
	 * When Pattern.toString() is called, the a layer will be prepended 
	 * to the beginning of the pattern after any explicit voice and before any
	 * explicit instrument in the form of "Lx", where x is the voice number
     *
	 * @return this pattern 
	 */
	public Pattern setLayer(int layer) {
		this.explicitLayer = layer;
		return this;
	}

	/**
	 * Provides a way to explicitly set the instrument on a Pattern directly
	 * through the pattern rather than by adding text to the contents
	 * of the Pattern.
	 * 
	 * When Pattern.toString() is called, the a instrument will be prepended 
	 * to the beginning of the pattern after any explicit voice in the form of 
	 * "I[instrument-name]" (even though this method takes an integer as a parameter)
     *
	 * @return this pattern 
	 */
	public Pattern setInstrument(int instrument) {
		this.explicitInstrument = instrument;
		return this;
	}
	
	/**
	 * Provides a way to explicitly set the instrument on a Pattern directly
	 * through the pattern rather than by adding text to the contents
	 * of the Pattern.
	 * 
	 * When Pattern.toString() is called, the a instrument will be prepended 
	 * to the beginning of the pattern after any explicit voice in the form of 
	 * "I[instrument-name]"
     *
	 * @return this pattern 
	 */
	public Pattern setInstrument(String instrument) {
		if (!MidiDictionary.INSTRUMENT_STRING_TO_BYTE.containsKey(instrument.toUpperCase())) {
			throw new RuntimeException("The instrument '"+instrument+"' is not recognized");
		}
		return setInstrument(MidiDictionary.INSTRUMENT_STRING_TO_BYTE.get(instrument.toUpperCase()));
	}
	
	/*
	 * Decorate each note
	 */

	/**
	 * Expects a parameter of "note decorators" - i.e., things that are added to 
	 * the end of a note, such as duration or attack/decay settings; splits the given 
	 * parameter on spaces and applies each decorator to each note as it is encountered
	 * in the current pattern. 
	 * 
	 * If there is one decorator in the parameter, this method will apply that same
	 * decorator to all note in the pattern.
	 * 
	 * If there are more notes than decorators, a counter resets to 0 and the decorators
	 * starting from the first are applied to the future notes.
	 * 
	 * Examples:
	 * 
	 * new Pattern("A B C").addToEachNoteToken("q")       --> "Aq Bq Cq"
	 * new Pattern("A B C").addToEachNoteToken("q i")     --> "Aq Bi Cq" (rolls back to q for third note)
	 * new Pattern("A B C").addToEachNoteToken("q i s")   --> "Aq Bi Cs"
	 * new Pattern("A B C").addToEachNoteToken("q i s w") --> "Aq Bi Cs" (same as "q i s")
	 * 
	 * @return this pattern
	 */
	public Pattern addToEachNoteToken(String decoratorString) {
        int currentDecorator = 0;
        String[] decorators = decoratorString.split(" ");

	    StringBuilder b2 = new StringBuilder();

	    List<Token> tokens = this.getTokens();
	    for (Token token : tokens) {
	        if (token.getType() == TokenType.NOTE) {
	            b2.append(token);
	            b2.append(decorators[currentDecorator++ % decorators.length]);
	        } else {
	            b2.append(token);
	        }
	        b2.append(" ");
	    }
	    
		this.patternSB = new StringBuilder(b2.toString().trim());
		return this;
	}
	
	public Pattern save(File file) throws IOException {
	    return save(file, (String)null);
	}
	
	public Pattern save(File file, String... comments) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        if (comments.length > 0) {
            writer.write("# \n");
        }
        for (String comment : comments) {
            writer.write("# ");
            writer.write(comment);
            writer.write("\n");
        }
        if (comments.length > 0) {
            writer.write("# \n\n");
        }
        writer.write(this.toString());
        writer.close();
        return this;
	}
	
	public static Pattern load(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		Pattern pattern = new Pattern();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    if (!line.startsWith("#")) {
		        pattern.add(line);
		    }
		}
		reader.close();
		return pattern;
	}
	
	/** 
	 * Parse this pattern and have the given ParserListener listen to it.
	 * The user will need to call a function on the listener to get the result.
	 * This method returns its own class, the contents of which ARE NOT modified by this call.
	 */
	public Pattern transform(ParserListener listener) {
	    return parseAndListen(listener);
	}
	
    /** 
     * Parse this pattern and have the given ParserListener listen to it.
     * The user will need to call a function on the listener to get the result.
     * This method returns its own class, the contents of which ARE NOT modified by this call.
     */
	public Pattern measure(ParserListener listener) {
	    return parseAndListen(listener);
	}
	
    /** 
     * Parse this pattern and have the given ParserListener listen to it.
     * The user will need to call a function on the listener to get the result.
     * This method returns its own class, the contents of which ARE NOT modified by this call.
     */
	private Pattern parseAndListen(ParserListener listener) {
	    StaccatoParser parser = new StaccatoParser();
	    parser.addParserListener(listener);
	    parser.parse(getPattern());
	    return this;
	}
	
	private static final int UNDECLARED_EXPLICIT = -1;
}
