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

package org.jfugue.musicxml;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.jfugue.integration.MusicXmlParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.staccato.StaccatoParserListener;

public class MusicXmlParserTest 
{
	@Before
	public void setUp() { }
	
    @Ignore @Test
    public void testHelloWorldMusicXml() {
    	try {
    		MusicXmlParser musicXmlParser = new MusicXmlParser();
    		StaccatoParserListener spl = new StaccatoParserListener();
    		musicXmlParser.addParserListener(spl);
    		musicXmlParser.parse(new File("src/test/resources/HelloWorldMusicXml.xml"));
    		assertTrue(spl.getPattern().toString().equals("V0 KEY:Cmaj C4w |"));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
