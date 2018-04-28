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

import org.jfugue.midi.MidiDictionary;
import org.jfugue.pattern.Token.TokenType;

/**
 * Parses Instrument, Voice, and Layer tokens. Each has values that are parsed as bytes. 
 * 
 * @author David Koelle (dkoelle@gmail.com)
 */
public class IVLSubparser implements Subparser 
{
	public static final char INSTRUMENT = 'I';
	public static final char LAYER = 'L';
	public static final char VOICE = 'V';
	
	private static IVLSubparser instance;
	
	public static IVLSubparser getInstance() {
		if (instance == null) {
			instance = new IVLSubparser();
		}
		return instance;
	}
	
	@Override
	public boolean matches(String music) {
		return ((music.charAt(0) == VOICE) || 
				(music.charAt(0) == INSTRUMENT) ||
				(music.charAt(0) == LAYER));
	}

    @Override
    public TokenType getTokenType(String tokenString) {
        if (tokenString.charAt(0) == VOICE) {
            return TokenType.VOICE;
        }
        if (tokenString.charAt(0) == INSTRUMENT) {
            return TokenType.INSTRUMENT;
        }
        if (tokenString.charAt(0) == LAYER) {
            return TokenType.LAYER;
        }
        
        return TokenType.UNKNOWN_TOKEN;
    }
    
	@Override
	public int parse(String music, StaccatoParserContext context) {
		if (matches(music)) {
			int posNextSpace = StaccatoUtil.findNextOrEnd(music, ' ', 0);
			byte value = -1;
			if (posNextSpace > 1) {
				value = getValue(music.substring(0, posNextSpace), context);
//				String instrumentId = music.substring(1, posNextSpace);
//				if (instrumentId.matches("\\d+")) {
//					value = Byte.parseByte(instrumentId);
//				} else {
//					if (instrumentId.charAt(0) == '[') {
//						instrumentId = instrumentId.substring(1, instrumentId.length()-1);
//					}
//					value = (Byte)context.getDictionary().get(instrumentId);
//				}
			}
			switch (music.charAt(0)) {
				case INSTRUMENT: context.getParser().fireInstrumentParsed(value); break;
				case LAYER : context.getParser().fireLayerChanged(value); break;
				case VOICE : context.getParser().fireTrackChanged(value); break;
				default : break;
			}
			return posNextSpace + 1;
		}
		return 0;
	}
	
	/** Given a string like "V0" or "I[Piano]", this method will return the value of the token */
	public byte getValue(String ivl, StaccatoParserContext context) {
		String instrumentId = ivl.substring(1, ivl.length());
		if (instrumentId.matches("\\d+")) {
			return Byte.parseByte(instrumentId);
		} else {
			if (instrumentId.charAt(0) == '[') {
				instrumentId = instrumentId.substring(1, instrumentId.length()-1);
			}
			return (Byte)context.getDictionary().get(instrumentId);
		}
	}
	
    public static void populateContext(StaccatoParserContext context) {
        // Voices
        context.getDictionary().put("PERCUSSION", (byte)9);
        
        // Instruments
        context.getDictionary().putAll(MidiDictionary.INSTRUMENT_STRING_TO_BYTE);
    }
}
