package org.jfugue.midi;

public class MidiRoundTripTest {
	public static void main(String[] args) {
		MidiRoundTripRunner.run("TIME:4/4 KEY:C#min T90 I[Flute] :PW(8000) Cq Rw Cq"); 
	}
}
