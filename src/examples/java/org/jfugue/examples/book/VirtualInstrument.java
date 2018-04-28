package org.jfugue.examples.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfugue.realtime.RealtimePlayer;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Intervals;
import org.jfugue.theory.Note;

public class VirtualInstrument extends JPanel implements MouseListener {
    private List<InstrumentZone> instrumentZones;
    private RealtimePlayer player;
    private Note[] notes;
    
    public VirtualInstrument() throws MidiUnavailableException {
        super();
        this.instrumentZones = new ArrayList<InstrumentZone>();
        this.addMouseListener(this);
        this.player = new RealtimePlayer();
        this.player.play("I[Crystal]");
        this.notes = new Chord(new Note("C"), new Intervals("1 3 #4 5 7")).getNotes();
        createInstrumentZones();
    }
    
    private void createInstrumentZones() {
        Random rnd = new Random();
        for (int i=0; i < VirtualInstrument.NUM_ZONES; i++) {
            int x = rnd.nextInt(VirtualInstrument.WIDTH - VirtualInstrument.MAX_RECT_WIDTH);
            int y = rnd.nextInt(VirtualInstrument.HEIGHT - VirtualInstrument.MAX_RECT_HEIGHT);
            int w = VirtualInstrument.MIN_RECT_WIDTH + rnd.nextInt(VirtualInstrument.MAX_RECT_WIDTH - VirtualInstrument.MIN_RECT_WIDTH);
            int h = VirtualInstrument.MIN_RECT_HEIGHT + rnd.nextInt(VirtualInstrument.MAX_RECT_HEIGHT - VirtualInstrument.MIN_RECT_HEIGHT);
            Color color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 0.8f);
            Note note = new Note(notes[rnd.nextInt(notes.length)].toString() + (2 + (rnd.nextInt(4))));
            instrumentZones.add(new InstrumentZone(x, y, w, h, color, player, note));
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(new GradientPaint(new Point2D.Double(VirtualInstrument.WIDTH / 2.0, 0.0), Color.LIGHT_GRAY, new Point2D.Double(VirtualInstrument.WIDTH / 2.0, VirtualInstrument.HEIGHT), Color.BLACK));
        g2.fillRect(0, 0, VirtualInstrument.WIDTH, VirtualInstrument.HEIGHT);
        for (InstrumentZone izone : instrumentZones) {
            izone.paint(g2);
        }
    }


    @Override
    public void mousePressed(MouseEvent event) {
        for (InstrumentZone izone : instrumentZones) {
            izone.mousePressed(event.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        for (InstrumentZone izone : instrumentZones) {
            izone.mouseReleased(event.getPoint());
        }
    }
    
    @Override public void mouseClicked(MouseEvent e) { }

    @Override public void mouseEntered(MouseEvent e) { }

    @Override public void mouseExited(MouseEvent e) { }
    
    public static void main(String[] args) throws MidiUnavailableException {
        JFrame frame = new JFrame("JFugue - Virtual Instrument Demo");
        frame.setSize(VirtualInstrument.WIDTH, VirtualInstrument.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new VirtualInstrument(), BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private static int WIDTH = 800;
    private static int HEIGHT = 600;
    private static int NUM_ZONES = 100;
    private static int MIN_RECT_WIDTH = 50;
    private static int MAX_RECT_WIDTH = 200;
    private static int MIN_RECT_HEIGHT = 50;
    private static int MAX_RECT_HEIGHT = 200;
}

class InstrumentZone {
    public int x, y, width, height;
    public Paint paint;
    public RealtimePlayer realtimePlayer;
    public Note note;
    
    public InstrumentZone(int x, int y, int width, int height, Paint paint, RealtimePlayer player, Note note) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.paint = paint;
        this.realtimePlayer = player;
        this.note = note;
    }
    
    public void paint(Graphics2D g2) {
        g2.setPaint(this.paint);
        g2.fillRect(x, y, width, height);
    }
    
    public void mousePressed(Point2D point) {
        if (((point.getX() >= x) && (point.getX() <= x + width)) && ((point.getY() >= y) && (point.getY() <= y + height))) {
            realtimePlayer.startNote(note);
            System.out.println("Play "+note);
        }
    }

    public void mouseReleased(Point2D point) {
        if (((point.getX() >= x) && (point.getX() <= x + width)) && ((point.getY() >= y) && (point.getY() <= y + height))) {
            realtimePlayer.stopNote(note);
            System.out.println("Stop "+note);
        }
    }
}
