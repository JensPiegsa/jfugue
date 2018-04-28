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

import java.util.HashMap;
import java.util.Map;

import org.jfugue.pattern.Pattern;
import org.jfugue.testtools.parser.JFugueTestHelper;
import org.jfugue.theory.Note;
import org.junit.Test;

public class InstructionPreprocessorTest extends JFugueTestHelper {
    @Test
    public void testSimpleInstruction() {
        InstructionPreprocessor ip = InstructionPreprocessor.getInstance();
        ip.addInstruction("letter", "c");
        assertTrue(compare("{letter}", NOTE_PARSED, new Note("C")));
    }

    @Test
    public void testSwitchInstruction() {
        InstructionPreprocessor ip = InstructionPreprocessor.getInstance();
        ip.addInstruction("turn", new Instruction.Switch("$", "c", "d"));
        assertTrue(compare("{turn off}", NOTE_PARSED, new Note("C")));
        assertTrue(compare("{turn on}", NOTE_PARSED, new Note("D")));
    }

    @Test
    public void testChoiceInstruction() {
        InstructionPreprocessor ip = InstructionPreprocessor.getInstance();
        ip.addInstruction("chorus", new Instruction.Choice("c", "d", "e"));
        assertTrue(compare("{chorus 0}", NOTE_PARSED, new Note("C")));
        assertTrue(compare("{chorus 1}", NOTE_PARSED, new Note("D")));
        assertTrue(compare("{chorus 2}", NOTE_PARSED, new Note("E")));
    }
    
    @Test
    public void testLastIsValueInstruction() {
        InstructionPreprocessor ip = InstructionPreprocessor.getInstance();
        ip.addInstruction("volume", new Instruction.LastIsValue(":CON(7,$)"));
        assertTrue(compare("{volume should now be set to the glorious value of 2}", CONTROLLER_EVENT_PARSED, (byte)7, (byte)2));
    }
    
    @Test 
    public void testLastIsValueToSplitInstruction() {
        InstructionPreprocessor ip = InstructionPreprocessor.getInstance();
        Instruction.Splitter splitter = new Instruction.Splitter() {
        	public Map<String, String> splitInstructionParameter(String value) {
        		Map<String, String> retVal = new HashMap<String, String>();
        		retVal.put("$1", ""+Integer.parseInt(value) / 128);
        		retVal.put("$2", ""+Integer.parseInt(value) % 128);
        		return retVal;
        	}
        };
        ip.addInstruction("set total volume", new Instruction.LastIsValueToSplit(":CON(7,$1) :CON(39,$2)", splitter));
        assertTrue(compare("{set total volume to 1600}", CONTROLLER_EVENT_PARSED, (byte)39, (byte)64));
    }

    @Test
    public void testPatternAsInstruction() {
    	Pattern pattern = new Pattern("C");
        InstructionPreprocessor ip = InstructionPreprocessor.getInstance();
        ip.addInstruction("pattern",  pattern);
    	assertTrue(compare("{pattern}", NOTE_PARSED, new Note("C"))); 
    }

    @Test
    public void testUnknownInstruction() {
    	assertTrue(compare("{unknown instruction}", BEFORE_PARSING_STARTS)); // Remember that compare is looking for the penultimate event - the one *before* AFTER_PARSING_FINISHES
    }
    
    @Test
    public void testInstructionsBySize() {
        InstructionPreprocessor ip = InstructionPreprocessor.getInstance();
        ip.addInstruction("life", new Instruction.LastIsValue(":CON(6,$)"));
        ip.addInstruction("life is a lemon and i want my money back", new Instruction.LastIsValue(":CON(7,$)"));
        ip.addInstruction("life is a lemon", new Instruction.LastIsValue(":CON(8,$)"));
        assertTrue(compare("{life is a lemon and i want my money back because meat loaf said so 42}", CONTROLLER_EVENT_PARSED, (byte)7, (byte)42));
    }
}


