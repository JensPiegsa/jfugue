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

package org.jfugue.pattern;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.parser.ParserListenerAdapter;

public class InstrumentListener extends ParserListenerAdapter {
	private List<Byte> instruments = new ArrayList<Byte>();
	
	@Override
	public void onInstrumentParsed(byte instrument) {
		instruments.add(instrument);
	}
	
	public List<Byte> getInstruments() {
		return this.instruments;
	}
}
