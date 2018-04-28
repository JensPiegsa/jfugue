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

import org.jfugue.testtools.parser.JFugueTestHelper;
import org.junit.Test;

public class UppercasePreprocessorTest extends JFugueTestHelper {
    @Test
    public void testPreprocess() {
    	UppercasePreprocessor up = new UppercasePreprocessor();
		String output = up.preprocess("a b c :test(1, 2, 3) #tag #(tag tag) aminwha90 'lyric '(lyric lyric)", (StaccatoParserContext)null);
		assertTrue(output.equals("A B C :TEST(1, 2, 3) #tag #(tag tag) AMINWHA90 'lyric '(lyric lyric)"));
    }
}

