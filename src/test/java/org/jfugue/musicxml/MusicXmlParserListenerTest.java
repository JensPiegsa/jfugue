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

package org.jfugue.musicxml;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO: Write MusicXmlParserListenerTest

public class MusicXmlParserListenerTest {
	
    @Test
    public void testMusicXmlParserListenerTest() {
    	assertTrue(true);
    }

//
//	
//	/**
//	 ** Used for diagnostic purposes. main() makes calls to test the
//	 * Pattern-to-MusicXML renderer.
//	 ** 
//	 * @param args
//	 *            not used
//	 **/
//	public static void main(String[] args) {
//		// FrereJacquesRound();
//		// Entertainer();
//		metronome(120);
//	}
//
//	@SuppressWarnings("unused")
//	private static void FrereJacquesRound() {
//		File fileXML = new File(
//				"C:\\Documents and Settings\\Philip Sobolik\\My Documents\\"
//						+ "Visual Studio 2005\\WebSites\\NYSSMA3\\"
//						+ "FrereJacquesRound.xml");
//		try {
//			FileOutputStream fosXML = new FileOutputStream(fileXML, false);
//
//			// set up the source MusicXML file (parser)
//			MusicXmlParserListener MusicXmlOut = new MusicXmlParserListener();
//			// MusicXmlParser.setTracing(Parser.TRACING_ON);
//
//			// set up the target MusicString (renderer)
//			MusicStringParser MusicStringIn = new MusicStringParser();
//
//			// attach the render to the parser
//			MusicStringIn.addParserListener(MusicXmlOut);
//
//			// "Frere Jacques"
//			Pattern pattern1 = new Pattern("C5q D5q E5q C5q |");
//
//			// "Dormez-vous?"
//			Pattern pattern2 = new Pattern("E5q F5q G5h |");
//
//			// "Sonnez les matines"
//			Pattern pattern3 = new Pattern("G5i A5i G5i F5i E5q C5q |");
//
//			// "Ding ding dong"
//			Pattern pattern4 = new Pattern("C5q G4q C5h |");
//
//			// Put it all together
//			Pattern song = new Pattern();
//			song.add(pattern1, 2); // Adds 'pattern1' to 'song' twice
//			song.add(pattern2, 2); // Adds 'pattern2' to 'song' twice
//			song.add(pattern3, 2); // Adds 'pattern3' to 'song' twice
//			song.add(pattern4, 2); // Adds 'pattern4' to 'song' twice
//			// MusicStringIn.parse(new Pattern("T160 I[Cello] "+
//			// "G3q G3q G3q Eb3q Bb3i G3q Eb3q Bb3i G3h"));
//			// MusicStringIn.parse(song);
//
//			// "Frere Jacques" as a round
//			Pattern doubleMeasureRest = new Pattern("Rw | Rw |");
//
//			// Create the first voice
//			Pattern round1 = new Pattern("V0");
//			round1.add(song);
//			round1.add(doubleMeasureRest, 2);
//
//			// Create the second voice
//			Pattern round2 = new Pattern("V1");
//			round2.add(doubleMeasureRest);
//			round2.add(song);
//			round2.add(doubleMeasureRest);
//
//			// Create the third voice
//			Pattern round3 = new Pattern("V2");
//			round3.add(doubleMeasureRest, 2);
//			round3.add(song);
//
//			// Put the voices together
//			PatternInterface roundSong = new Pattern();
//			roundSong.add(round1);
//			roundSong.add(round2);
//			roundSong.add(round3);
//
//			Player player = new Player();
//			player.play(roundSong);
//			System.out.println(roundSong.toString());
//
//			// start the parser
//			MusicStringIn.parse(roundSong);
//
//			// write the MusicXML file unformatted
//			/*
//			 * String p = MusicXmlOut.getMusicXMLString();
//			 * fosXML.write(p.getBytes());
//			 * 
//			 * // display the MusicXML file as an unformatted string
//			 * System.out.println(p); System.out.print('\n');
//			 */
//			// write the MusicXML file formatted
//			Serializer ser = new Serializer(fosXML, "UTF-8");
//			ser.setIndent(4);
//			ser.write(MusicXmlOut.getMusicXMLDoc());
//
//			fosXML.flush();
//			fosXML.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unused")
//	private static void Entertainer() {
//		File fileSrc = new File("F:\\WIN\\JFugue\\org\\jfugue\\extras\\"
//				+ "entertainer.jfugue");
//		File fileXML = new File(
//				"C:\\Documents and Settings\\Philip Sobolik\\My Documents\\"
//						+ "Visual Studio 2005\\WebSites\\NYSSMA3\\"
//						+ "Entertainer.xml");
//		try {
//			FileOutputStream fosXML = new FileOutputStream(fileXML, false);
//
//			// set up the source MusicXML file (parser)
//			MusicXmlParserListener MusicXmlOut = new MusicXmlParserListener();
//			// MusicXmlParser.setTracing(Parser.TRACING_ON);
//
//			// set up the target MusicString (renderer)
//			MusicStringParser MusicStringIn = new MusicStringParser();
//
//			// attach the render to the parser
//			MusicStringIn.addParserListener(MusicXmlOut);
//
//			// read the song from the file
//			BufferedReader brSrc = new BufferedReader(new FileReader(fileSrc));
//			LineNumberReader lnrSrc = new LineNumberReader(brSrc);
//
//			PatternInterface song = new Pattern();
//			for (String s = lnrSrc.readLine(); s != null; s = lnrSrc.readLine()) {
//				if (s.length() > 0)
//					if (s.charAt(0) != '#')
//						song.add(s);
//			}
//			lnrSrc.close();
//
//			// play the song
//			// Player player = new Player();
//			// player.play(song);
//			System.out.println(song.toString());
//
//			// start the parser
//			MusicStringIn.parse(song);
//
//			// write the MusicXML file unformatted
//			/*
//			 * String p = MusicXmlOut.getMusicXMLString();
//			 * fosXML.write(p.getBytes());
//			 * 
//			 * // display the MusicXML file as an unformatted string
//			 * System.out.println(p); System.out.print('\n');
//			 */
//			// write the MusicXML file formatted
//			Serializer ser = new Serializer(fosXML, "UTF-8");
//			ser.setIndent(4);
//			ser.write(MusicXmlOut.getMusicXMLDoc());
//
//			fosXML.flush();
//			fosXML.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static void testMusicXmlParser() { // File fileXML = new
//		// File("C:\\Documents and Settings\\Philip Sobolik\\My Documents\\"
//		// +
//		// "Visual Studio 2005\\WebSites\\NYSSMA3\\"
//		// + "NYSSMA-Flute-2.xml");
//		File fileXML = new File("/users/epsobolik/documents/binchois.xml");
//		// File fileXML = new
//		// File("/users/epsobolik/documents/SchbAvMaSample.xml");
//		try {
//			FileInputStream fisXML = new FileInputStream(fileXML);
//
//			// test the XML file by displaying the first 1024 characters
//			FileChannel fc = fisXML.getChannel();
//			ByteBuffer buf = ByteBuffer.allocate((int) fc.size());
//			fc.read(buf);
//			buf.flip();
//			// while(buf.hasRemaining())
//			// System.out.print((char)buf.get());
//			// fisXML.close();
//			// System.out.print('\n');
//
//			// set up the source MusicXML file (parser)
//			MusicXmlParser MusicXMLIn = new MusicXmlParser();
//			// MusicXmlParser.setTracing(Parser.TRACING_ON);
//
//			// set up the target MusicString (renderer)
//			MusicStringRenderer MusicStringOut = new MusicStringRenderer();
//
//			// attach the render to the parser
//			MusicXMLIn.addParserListener(MusicStringOut);
//
//			// start the parser
//			MusicXMLIn.parse(fileXML);
//
//			// display the MusicString
//			PatternInterface p = MusicStringOut.getPattern();
//			p.insert("T60");
//
//			System.out.println(p.toString());
//			System.out.print('\n');
//
//			// File fileMS = new
//			// File("/users/epsobolik/documents/SchbAvMaSample.jFugue");
//			// FileOutputStream fosMS = new FileOutputStream(fileMS);
//
//			// String ps = p.toString();
//			// for (int c = 0; c < ps.length(); ++c)
//			// fosMS.write(ps.charAt(c));
//			// fosMS.close();
//
//			// play the pattern
//			Player player = new Player();
//			player.play(p);
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static void metronome(int bpm) {
//		PatternInterface p = new Pattern("T"
//				+ Integer.toString((60 * 240) / bpm));
//		p.add("A4q", bpm); // should play for 1 minute
//		Player pl = new Player();
//		pl.play(p);
//	}

}
