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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BrokenChordPreprocessorTest {
    BrokenChordPreprocessor bc;
    StaccatoParserContext context;

    @Before
    public void setup() {
        bc = new BrokenChordPreprocessor();
        context = new StaccatoParserContext(null);
    }

    @Test
    public void testOneColonNoOctave() {
        assertTrue(bc.preprocess("Cmaj7:$0", context).equalsIgnoreCase("C"));
    }

    @Test
    public void testOneColonYesOctave() {
        assertTrue(bc.preprocess("C5maj7:$0", context).equalsIgnoreCase("C5"));
    }

    @Test
    public void testFullChord() {
        assertTrue(bc.preprocess("Fmaj:$0,$1,$2", context).equalsIgnoreCase("F A C"));
    }

    @Test
    public void testFullChordWithDurations() {
        assertTrue(bc.preprocess("Dmin:$0q,$1i,$2w", context).equalsIgnoreCase("Dq Fi Aw"));
    }

    @Test
    public void testFullChordWithDynamics() {
        assertTrue(bc.preprocess("Fmaj:$0q,$1w,$2h:a40d90", context).equalsIgnoreCase("Fqa40d90 Awa40d90 Cha40d90"));
        assertTrue(bc.preprocess("Fmaj:$0,$1,$2:q", context).equalsIgnoreCase("Fq Aq Cq"));
    }

    @Test
    public void testUnderscoreAndPlus() {
        assertTrue(bc.preprocess("G6maj:$0q+$1i_$2i", context).equalsIgnoreCase("G6q+B6i_D7i"));
        assertTrue(bc.preprocess("G6maj:$0q+Ri_$2i", context).equalsIgnoreCase("G6q+Ri_D7i"));
    }

    @Test
    public void testUnderscoreWithDynamics() {
        assertTrue(bc.preprocess("Fmaj:$0+$1,$2:q", context).equalsIgnoreCase("Fq+Aq Cq"));
    }

    @Test
    public void testInversion() {
        assertTrue(bc.preprocess("Amin^^:$0q,$1i,$2h", context).equalsIgnoreCase("Eq Ai Ch"));
    }

    @Test
    public void testCombinedDurationsWithDot() {
        assertTrue(bc.preprocess("Amin^^:$0q,$1i,$2h:.", context).equalsIgnoreCase("Eq. Ai. Ch."));
    }

    @Test
    public void testShouldNotBeParsed() {
        assertTrue(bc.preprocess(":CON(57,1)", context).equalsIgnoreCase(":CON(57,1)"));
    }

    @Test
    public void testLotsOfRests() {
        assertTrue(bc.preprocess("Cmaj:Rq,Rq,$1q,Rq,Rq", context).equalsIgnoreCase("Rq Rq Eq Rq Rq"));
    }

    @Test
    public void testRoot() {
        assertTrue(bc.preprocess("Cmaj:$ROOTq,Rq,$ROOTq,Rq", context).equalsIgnoreCase("Cq Rq Cq Rq"));
    }

    @Test
    public void testBassWithFirstInversion() {
        assertTrue(bc.preprocess("Cmaj^:$BASSq,$0q,$ROOTq,Rq", context).equalsIgnoreCase("Eq Eq Cq Rq"));
    }

    @Test
    public void testBassWithSecondInversion() {
        assertTrue(bc.preprocess("Cmaj^^:$BASSq,$0q,$ROOTq,Rq", context).equalsIgnoreCase("Gq Gq Cq Rq"));
    }

    @Test
    public void testAll() {
        assertTrue(bc.preprocess("Cmaj:$!q", context).equalsIgnoreCase("(C+E+G)q"));
    }

    @Test
    public void testExceptRoot() {
        assertTrue(bc.preprocess("Cmaj:$NOTROOTq Rq", context).equalsIgnoreCase("(E+G)q Rq"));
    }

    @Test
    public void testExceptRootWithSecondInversion() {
        assertTrue(bc.preprocess("Cmaj^^:$NOTROOTq Rq", context).equalsIgnoreCase("(G+E)q Rq"));
    }

    @Test
    public void testExceptBass() {
        assertTrue(bc.preprocess("Cmaj:$NOTBASSq", context).equalsIgnoreCase("(E+G)q"));
    }

    @Test
    public void testExceptBassWithSecondInversion() {
        assertTrue(bc.preprocess("Cmaj^^:$NOTBASSq", context).equalsIgnoreCase("(C+E)q"));
    }

    @Test
    public void testDictionaryLookup() {
        context.getDictionary().put("MARCH", "$0q,$1q+$2q,$1q+$2q,Rq");
        assertTrue(bc.preprocess("Cmaj:[MARCH]", context).equalsIgnoreCase("Cq Eq+Gq Eq+Gq Rq"));
        assertTrue(bc.preprocess("Cmaj:[MARCH]:a20", context).equalsIgnoreCase("Cqa20 Eqa20+Gqa20 Eqa20+Gqa20 Rqa20"));
    }

    @Test
    public void testIntentionallyBadChord() {
        assertFalse(bc.preprocess("FOO:$0", context).equalsIgnoreCase("G6q+B6i_D7i"));
    }
}
