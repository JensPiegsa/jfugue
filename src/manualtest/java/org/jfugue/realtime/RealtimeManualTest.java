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

package org.jfugue.realtime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfugue.pattern.Pattern;
import org.jfugue.theory.Note;

public class RealtimeManualTest {
    private org.jfugue.realtime.RealtimePlayer player;
    private JPanel panel;
    private JLabel volumeLabel = new JLabel();
    private byte volume = 60;
    private Pattern[] pattern = new Pattern[] { new Pattern("Ch Dh Eh"), new Pattern("Cq Dq Eq"), new Pattern("I[Piano] Cq I[Flute] Cq I[Piano]"), new Pattern("CmajW"), new Pattern("V0 Ch V1 Eh V2 Gh") };

    public RealtimeManualTest() {
        try {
            player = new org.jfugue.realtime.RealtimePlayer();
            playVolume();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        init();
    }
    
    private void init() {
        JPanel keyPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        keyPanel.add(createTonePanel("C"));
        keyPanel.add(createTonePanel("D"));
        keyPanel.add(createTonePanel("E"));
        keyPanel.add(createTonePanel("F"));
        keyPanel.add(createTonePanel("G"));
        keyPanel.add(createTonePanel("A"));
        keyPanel.add(createTonePanel("B"));
        
        JPanel controlPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        JButton volDown = new JButton("Decrease volume");
        volDown.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (volume > 10) volume -= 10;
        		playVolume();
        	}
        });
        controlPanel.add(volDown);

        controlPanel.add(volumeLabel);
        
        JButton volUp = new JButton("Increase volume");
        volUp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (volume < 117) volume += 10;
        		playVolume();
        	}
        });
        controlPanel.add(volUp);
        
        JPanel patternPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        for (int i=0; i < 5; i++) {
        	final int a = i;
        	JButton button = new JButton(pattern[a].toString());
            button.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		playPattern(pattern[a]);
            	}
            });
            patternPanel.add(button);
        }

        panel = new JPanel(new BorderLayout());
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(keyPanel, BorderLayout.CENTER);
        panel.add(patternPanel, BorderLayout.SOUTH);
    }

    private void playVolume() {
    	player.play(":CON(7, " + volume + ")");
        volumeLabel.setText("Volume: "+volume);
    }

    private void playPattern(Pattern pattern) {
    	player.play(pattern);
    }
    
    private JPanel createTonePanel(final String tone) {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel toneLabel = new JLabel("<HTML><FONT SIZE=+3>"+tone+"</FONT></HTML>");
        toneLabel.setOpaque(false);
        panel.add(toneLabel, BorderLayout.CENTER);
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { 
                if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                    play();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) { 
                release();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                play();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                release();
            }

            private void play() {
                panel.setBackground(Color.GREEN);
//              player.play(tone+"s-"); // Sixteenth note, and the start of a musical tie - it'll stop when the end of the tie is played
                player.startNote(new Note(tone));
            }
            
            private void release() {
                panel.setBackground(Color.WHITE);
//                player.play(tone+"-s"); // End of the tie
                player.stopNote(new Note(tone));
            }
        });
        
        return panel;
    }
    
    public JPanel getPanel() { 
        return this.panel;
    }
    
    public static void run() {
        RealtimeManualTest test = new RealtimeManualTest();

        JFrame frame = new JFrame("JFugue Real Time Manual Test");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(test.getPanel(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        RealtimeManualTest.run();
    }
}
