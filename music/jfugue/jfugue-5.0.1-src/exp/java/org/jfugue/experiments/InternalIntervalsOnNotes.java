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

package org.jfugue.experiments;

import org.jfugue.player.Player;

public class InternalIntervalsOnNotes {
	public static void main(String[] args) {
		Player player = new Player();
//		player.play("Cx Cx Cx C4'5q C C'6bq Cx Cx Cx");
		player.play("C C C C'1# C'2 C'2# C'3 C'4 C'4# C'5 C'6b C'6 C'6# C'7 C'7# C'8 C6 C6");
	}
}
