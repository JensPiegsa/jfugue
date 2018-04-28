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

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class KeyTest 
{
	@Before
	public void setUp() { }
	
    @Test
    public void testCreateMajorKeyByString() {
        Key key = new Key("Amaj");
        assertTrue(key.getKeySignature().equalsIgnoreCase("AMAJ"));
        assertTrue(key.getRoot().getOriginalString().equalsIgnoreCase("A"));
        assertTrue(key.getScale().equals(Scale.MAJOR));
    }

    @Test
    public void testCreateMajorKeyByStringWithoutChord() {
        Key key = new Key("A");
        assertTrue(key.getKeySignature().equalsIgnoreCase("AMAJ"));
        assertTrue(key.getRoot().getOriginalString().equalsIgnoreCase("A"));
        assertTrue(key.getScale().equals(Scale.MAJOR));
    }

    @Test
    public void testCreateMinorKeyByString() {
        Key key = new Key("Amin");
        assertTrue(key.getKeySignature().equalsIgnoreCase("Amin"));
        assertTrue(key.getRoot().getOriginalString().equalsIgnoreCase("A"));
        assertTrue(key.getScale().equals(Scale.MINOR));
    }
    
    @Test
    public void testCreateKeyByChord() {
        Key key = new Key(new Chord("Amaj"));
        assertTrue(key.getKeySignature().equalsIgnoreCase("AMAJ"));
        assertTrue(key.getRoot().getOriginalString().equalsIgnoreCase("A"));
        assertTrue(key.getScale().equals(Scale.MAJOR));
    }

    @Test
    public void testCreateKeyBySignature1() {
        Key key = new Key("K####");
        assertTrue(key.getKeySignature().equalsIgnoreCase("EMAJ"));
        assertTrue(key.getRoot().getOriginalString().equalsIgnoreCase("E"));
        assertTrue(key.getScale().equals(Scale.MAJOR));
    }

    @Test
    public void testCreateKeyBySignature2() {
        Key key = new Key("Kbbb");
        assertTrue(key.getKeySignature().equalsIgnoreCase("EbMAJ"));
        assertTrue(key.getRoot().getOriginalString().equalsIgnoreCase("Eb"));
        assertTrue(key.getScale().equals(Scale.MAJOR));
    }

}
