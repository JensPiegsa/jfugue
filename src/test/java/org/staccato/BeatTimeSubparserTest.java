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

public class BeatTimeSubparserTest extends JFugueTestHelper {
    BeatTimeSubparser parser;
    StaccatoParserContext context;

    @Before
    public void setUp() {
        parser = BeatTimeSubparser.getInstance();
        context = new StaccatoParserContext((Parser)null);
    }
    
    @Test
    public void testTrackBeatTimeRequested() {
        assertTrue(compare("@200", TRACK_BEAT_TIME_REQUESTED, 200.0D));
    }

    @Test
    public void testTrackBeatTimeBookmarked() {
        assertTrue(compare("#mark", MARKER_PARSED, "mark")); 
        // Should also fire TRACK_BEAT_TIME_BOOKMARK_REQUESTED, which is what we really care about in
        // this test, but that even happens before the penultimate event that compare() returns
    }

    @Test
    public void testTrackBeatTimeBookmarkRequested() {
        assertTrue(compare("@#mark", TRACK_BEAT_TIME_BOOKMARK_REQUESTED, "mark"));
    }
}
