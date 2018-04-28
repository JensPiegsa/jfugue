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

package org.staccato.manualtest;

import org.jfugue.parser.ParserException;
import org.jfugue.parser.ParserListener;
import org.jfugue.parser.ParserListenerAdapter;
import org.staccato.StaccatoParser;

public class StaccatoEfficiencyTest {
	public static void main(String[] args) {
		StaccatoParser parser = new StaccatoParser();
		ParserListener l = new ParserListenerAdapter() {
			@Override public void onBarLineParsed(long time) {
				System.out.println("Bar line: "+time);
			}
			@Override public void onInstrumentParsed(byte instrument) {
				System.out.println("Instrument: "+instrument);
			}
		};

		parser.addParserListener(l);
		
		double t3 = 0d;
		for (int i=0; i < 1000; i++ ) {
	  		try {
				long t1 = System.currentTimeMillis();
				parser.parse("I9 | I10 |23 I20");
				long t2 = System.currentTimeMillis();
				System.out.println(t2 - t1);
				t3 += (t2-t1);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		System.out.println(t3 / 1000d);
	}
}
