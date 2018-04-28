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

import org.jfugue.rhythm.Rhythm;

public class TrackTable implements PatternProducer
{
    private int length;
    private double cellDuration;
    private List<List<PatternProducer>> tracks; 
    private List<PatternProducer> trackSettings;
    
    public TrackTable(int length, double cellDuration) {
        this.length = length;
        this.cellDuration = cellDuration;
        tracks = new ArrayList<List<PatternProducer>>(TrackTable.NUM_TRACKS);
        trackSettings = new ArrayList<PatternProducer>(TrackTable.NUM_TRACKS);
        
        for (int i=0; i < TrackTable.NUM_TRACKS; i++) {
            trackSettings.add(new Pattern(""));
            List<PatternProducer> list = new ArrayList<PatternProducer>(length);
            for (int u=0; u < length; u++) {
                list.add(new Pattern("R/"+cellDuration));
            }
            tracks.add(list);
        }
    }
    
    public List<PatternProducer> getTrack(int track) {
        return tracks.get(track);
    }
    
    public TrackTable put(int track, int position, PatternProducer patternProducer) {
        List<PatternProducer> trackList = this.tracks.get(track);
        if (trackList == null) {
            trackList = new ArrayList<PatternProducer>(getLength());
            this.tracks.add(track, trackList);
        }
        trackList.add(position, patternProducer.getPattern());
        return this;
    }

    public TrackTable put(int track, int start, PatternProducer... patternProducers) {
        int counter = 0;
        for (PatternProducer producer : patternProducers) {
            this.put(track, start+counter, producer);
            counter++;
        }
        return this;
    }

    /** Puts the given pattern in the track table at every 'nth' position */
    public TrackTable putAtIntervals(int track, int nth, PatternProducer patternProducer) {
        for (int position = 0; position < this.length; position += nth) {
            this.put(track, position, patternProducer);
        }
        return this;
    }

    /** Puts the given pattern in the track table at every 'nth' position, starting with position 'first' and ending with 'end' */
    public TrackTable putAtIntervals(int track, int first, int nth, int end, PatternProducer patternProducer) {
        for (int position = first; position < Math.min(this.length, end); position += nth) {
            this.put(track, position, patternProducer);
        }
        return this;
    }

    /**
     * As part of JFugue's fluent API, this method returns the instance of this class. 
     * @param track
     * @param start
     * @param end
     * @param patternProducer
     * @return The instance of this class
     */
    public TrackTable put(int track, int start, int end, PatternProducer patternProducer) {
        for (int i=start; i <= end; i++) {
            put(track, i, patternProducer);
        }
        return this;
    }
    
    /**
     * Lets you specify which cells in the TrackTable should be populated with the given PatternProducer by using a String 
     * in which a period means "not in this cell" and any other character means "in this cell".
     * Example: put(1, pattern, "...XXXX..XX....XXXX..XX....");
     * 
     * @param track
     * @param periodMeansNoOtherMeansYes
     * @param patternProducer
     * @return
     */
    public TrackTable put(int track, String periodMeansNo_DashMeansExtend_OtherMeansYes, PatternProducer patternProducer) {
    	for (int i=0; i < periodMeansNo_DashMeansExtend_OtherMeansYes.length(); i++) {
    		if (periodMeansNo_DashMeansExtend_OtherMeansYes.charAt(i) == '.') {
    		    // No op
    		}
    		else if (periodMeansNo_DashMeansExtend_OtherMeansYes.charAt(i) == '-') {
    		    put(track, i, new Pattern(""));
    		} else {
    			put(track, i, patternProducer);
    		}
    	}
    	return this;
    }
    
    public TrackTable put(Rhythm rhythm) {
        for (int i=0; i < rhythm.getLength(); i++) {
            put(9, i, rhythm.getPatternAt(i));
        }
        return this;
    }
    
    public PatternProducer get(int track, int position) {
        return tracks.get(track).get(position);
    }
    
    public TrackTable clear(int track, int position) {
        put(track, position, new Pattern(""));
        return this;
    }
    
    public TrackTable reset(int track, int position) {
        put(track, position, new Pattern("R/"+cellDuration));
        return this;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public TrackTable setTrackSettings(int track, PatternProducer p) {
        this.trackSettings.add(track, p);
        return this;
    }
    
    public TrackTable setTrackSettings(int track, String s) {
        this.trackSettings.add(track, new Pattern(s));
        return this;
    }
    
    public PatternProducer getTrackSettings(int track) {
        return this.trackSettings.get(track);
    }
    
    public Pattern getPatternAt(int column) {
        Pattern columnPattern = new Pattern();
        for (List<PatternProducer> track : tracks) {
            PatternProducer p = track.get(column);
            columnPattern.add(new Pattern(p).setVoice(tracks.indexOf(track)));
        }
        return columnPattern;
    }
    
    @Override
    public Pattern getPattern() {
        Pattern pattern = new Pattern();
        int trackCounter = 0;
        
        // First, add the track settings
        for (PatternProducer trackSetting : trackSettings) {
            if (!trackSetting.toString().equals("")) {
                pattern.add(new Pattern(trackSetting).setVoice(trackCounter));
            }
            trackCounter++;
        }
        
        // Next, for each track, add it to the pattern
        for (List<PatternProducer> track : tracks) {
            for (PatternProducer p : track) {
                pattern.add(new Pattern(p).setVoice(tracks.indexOf(track)));
            }
        }
        return pattern;
    }

    @Override
    public String toString() {
        return getPattern().toString();
    }
    
    public static final int NUM_TRACKS = 16;
    public static final int RHYTHM_TRACK = 9;
}
