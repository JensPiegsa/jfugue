package org.jfugue.pattern;

import org.jfugue.theory.Note;
import org.staccato.AtomSubparser;
import org.staccato.IVLSubparser;
import org.staccato.StaccatoParser;
import org.staccato.StaccatoParserContext;

/**
 * An Atom represents a single entity of a Voice+Layer+Instrument+Note
 * and is useful especially when using the Realtime Player, so all of
 * the information about a specific note is conveyed at the same time.
 * Pattern now has an atomize() method that will turn the Pattern into
 * a collection of atoms.
 */
public class Atom implements PatternProducer 
{
	private byte voice;
	private byte layer;
	private byte instrument;
	private Note note;
	private String contents;
	
	public Atom(byte voice, byte layer, byte instrument, String note) {
		this(voice, layer, instrument, new Note(note));
	}
	
	public Atom(byte voice, byte layer, byte instrument, Note note) {
		createAtom(voice, layer, instrument, note);
	}

	public Atom(String voice, String layer, String instrument, String note) {
		this(voice, layer, instrument, new Note(note));
	}

	public Atom(String voice, String layer, String instrument, Note note) {
		StaccatoParserContext context = new StaccatoParserContext((StaccatoParser)null);
		IVLSubparser.populateContext(context);
		
		// Need to use toUpperCase() and create a context... this is sounding very parser-ish 
		// and should ideally happen elsewhere!
		createAtom(IVLSubparser.getInstance().getValue(voice.toUpperCase(), context), 
		     IVLSubparser.getInstance().getValue(layer.toUpperCase(), context), 
		     IVLSubparser.getInstance().getValue(instrument.toUpperCase(), context), 
		     new Note(note));
	}
	
	private void createAtom(byte voice, byte layer, byte instrument, Note note) {
		this.voice = voice;
		this.layer = layer;
		this.instrument = instrument;
		this.note = note;
				
		StringBuilder buddy = new StringBuilder();
		buddy.append(AtomSubparser.ATOM);
		buddy.append(IVLSubparser.VOICE);
		buddy.append(voice);
		buddy.append(AtomSubparser.QUARK_SEPARATOR);
		buddy.append(IVLSubparser.LAYER);
		buddy.append(layer);
		buddy.append(AtomSubparser.QUARK_SEPARATOR);
		buddy.append(IVLSubparser.INSTRUMENT);
		buddy.append(instrument);
		buddy.append(AtomSubparser.QUARK_SEPARATOR);
		buddy.append(note);
		this.contents = buddy.toString();
	}
    
	public byte getVoice() {
		return this.voice;
	}
	
	public byte getLayer() {
		return this.layer;
	}
	
	public byte getInstrument() {
		return this.instrument;
	}
	
	public Note getNote() {
		return this.note;
	}
	
    @Override
    public String toString() {
        return this.contents;
    }
    
    @Override
    public Pattern getPattern() {
        return new Pattern(contents);
    }
}
