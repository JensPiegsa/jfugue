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

package org.jfugue.examples.website;

import org.jfugue.player.Player;

public class HelloWorld4 {
	public static void main(String[] args) {
		Player player = new Player();
		player.play("T[Moderato]  V1 I[Piano] (E+A)q  (E+A)q  (E+A)q  Fi. C5s | (E+A)q  Fi. C5s (E+A)h  " + 
                    "             V2 I[Piano] (A3+C)q (A3+C)q (A3+C)q F3minq  | (A3+C)q F3minq  (A3+C)h ");
	}
}
