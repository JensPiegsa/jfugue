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

import org.jfugue.pattern.Token.TokenType;

/**
 * Parses Instrument, Voice, and Layer tokens. Each has values that are parsed as bytes. 
 * 
 * @author David Koelle (dkoelle@gmail.com)
 */
public class AtomSubparser implements Subparser 
{
	public static final char ATOM = '&';
	public static final String QUARK_SEPARATOR = ",";
	
	private static AtomSubparser instance;
	
	public static AtomSubparser getInstance() {
		if (instance == null) {
			instance = new AtomSubparser();
		}
		return instance;
	}
	
	@Override
	public boolean matches(String music) {
		return (music.charAt(0) == ATOM); 
	}

    @Override
    public TokenType getTokenType(String tokenString) {
        if (tokenString.charAt(0) == ATOM) {
            return TokenType.ATOM;
        }
        
        return TokenType.UNKNOWN_TOKEN;
    }
    
	@Override
	public int parse(String music, StaccatoParserContext context) {
		if (matches(music)) {
			int posNextSpace = StaccatoUtil.findNextOrEnd(music, ' ', 0);
			music = music.substring(1, posNextSpace); // Remove the initial character
			String[] quarks = music.split(QUARK_SEPARATOR);
			for (String quark : quarks) {
				if (IVLSubparser.getInstance().matches(quark)) {
					IVLSubparser.getInstance().parse(quark, context);
				}
				else if (NoteSubparser.getInstance().matches(quark)) {
					NoteSubparser.getInstance().parse(quark, context);
				}
			}
			
			return posNextSpace + 1;
		}
		return 0;
	}
}
