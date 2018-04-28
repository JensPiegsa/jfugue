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

import static org.junit.Assert.assertTrue;

import org.jfugue.parser.Parser;
import org.jfugue.testtools.parser.JFugueTestHelper;
import org.junit.Before;
import org.junit.Test;

public class LyricMarkerSubparserTest extends JFugueTestHelper {
    LyricMarkerSubparser parser;
    StaccatoParserContext context;

    @Before
    public void setUp() {
        parser = LyricMarkerSubparser.getInstance();
        context = new StaccatoParserContext((Parser)null);
    }
    
    @Test
    public void testLyricWithNoParentheses() {
        assertTrue(compare("'lyric", LYRIC_PARSED, "lyric"));
    }

    @Test
    public void testLyricWithParentheses() {
        assertTrue(compare("'(three word lyric)", LYRIC_PARSED, "three word lyric"));
    }

    @Test
    public void testMarkerWithNoParentheses() {
        assertTrue(compare("#marker", MARKER_PARSED, "marker"));
    }

    @Test
    public void testMarkerWithParentheses() {
        assertTrue(compare("#(three word marker)", MARKER_PARSED, "three word marker"));
    }

}
