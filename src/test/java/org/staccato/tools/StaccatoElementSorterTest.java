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

package org.staccato.tools;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class StaccatoElementSorterTest 
{
	@Before
	public void setUp() { } 
	
    @Test
    public void testSortTwoElements() {
    	String s = "V0 Cw V1 Ew";
    	Map<Double, List<ElementWithTrack>> map = StaccatoElementSorter.sortElements(s);
        assertTrue(map.get(0D).get(0).getElement().equals("Cw"));
        assertTrue(map.get(0D).get(1).getElement().equals("Ew"));
    }

}
