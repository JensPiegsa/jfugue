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
 
package org.jfugue.midi;

import java.util.HashMap;
import java.util.Map;

import org.jfugue.realtime.RealtimeMidiParserListener;

/**
 * This class is useful for any applications that plan on keeping track of
 * musical events by beat time. It automatically provides beat time, manages
 * the current track, and maintains beat time bookmarks. And, this class is
 * agnostic to units of time. It is used within JFugue for both track beats
 * (as in MidiParserListener) and milliseconds (as in RealtimeMidiParserListener) 
 * 
 * @see MidiParserListener
 * @see RealtimeMidiParserListener
 * 
 * @author David Koelle
 */
public class TrackTimeManager
{
    private double[][] beatTime;
    private byte currentTrackNumber;
    private byte lastCreatedTrackNumber;
    private byte[] currentLayerNumber;
    private double initialNoteBeatTimeForHarmonicNotes;  
    private Map<String, Double> bookmarkedTrackTimeMap;

    public TrackTimeManager() { 
    	beatTime = new double[MidiDefaults.TRACKS][MidiDefaults.LAYERS];
    	currentTrackNumber = 0;
    	lastCreatedTrackNumber = 0;
    	currentLayerNumber = new byte[MidiDefaults.TRACKS];
        initialNoteBeatTimeForHarmonicNotes = 0.0d;  
    	bookmarkedTrackTimeMap = new HashMap<String, Double>();
    }
    
    /**
     * Sets the current track, or channel, to which new events will be added.
     * @param trackNumber the track to select
     */
    public void setCurrentTrack(byte trackNumber) {
        if (trackNumber > this.lastCreatedTrackNumber) {
            for (int i = this.lastCreatedTrackNumber+1; i < trackNumber; i++) {
                createTrack((byte)i);
            }
            this.lastCreatedTrackNumber = trackNumber;
        }
        this.currentTrackNumber = trackNumber;
    }
    
    public byte getCurrentTrackNumber() {
    	return this.currentTrackNumber;
    }
    
    protected byte getLastCreatedTrackNumber() { 
    	return this.lastCreatedTrackNumber;
    }

    protected void createTrack(byte track) {
        for (byte layer = 0; layer < MidiDefaults.LAYERS; layer++) {
            beatTime[track][layer] = 0;
        }
        currentLayerNumber[track] = 0;
    }        

    /**
     * Sets the current layer within the track to which new events will be added.
     * @param layerNumber the layer to select
     */
    public void setCurrentLayerNumber(byte layerNumber) {
        currentLayerNumber[currentTrackNumber] = layerNumber;
    }

    protected byte getCurrentLayerNumber() { 
    	return this.currentLayerNumber[getCurrentTrackNumber()];
    }
    
    public void setInitialNoteBeatTimeForHarmonicNotes(double initialNoteBeatTimeForHarmonicNotes) {
    	this.initialNoteBeatTimeForHarmonicNotes = initialNoteBeatTimeForHarmonicNotes;
    }
    
    public double getInitialNoteBeatTimeForHarmonicNotes() {
    	return this.initialNoteBeatTimeForHarmonicNotes;
    }
    
    /**
     * Advances the timer for the current track by the specified duration,
     * which is specified in Pulses Per Quarter (PPQ)
     * @param duration the duration to increase the track timer
     */
    public void advanceTrackBeatTime(double advanceTime) {
        beatTime[currentTrackNumber][currentLayerNumber[currentTrackNumber]] += advanceTime;
    }

    /**
     * Sets the timer for the current track by the given time,
     * which is specified in Pulses Per Quarter (PPQ)
     * @param newTickTime the time at which to set the track timer
     */
    public void setTrackBeatTime(double newTime) {
        beatTime[currentTrackNumber][currentLayerNumber[currentTrackNumber]] = newTime;
    }

    /**
     * Sets the timer for all tracks to the given time,
     * which is specified in Pulses Per Quarter (PPQ)
     * @param newTickTime the time at which to set the track timer
     */
    public void setAllTrackBeatTime(double newTime) {
        for (int track=0; track < MidiDefaults.TRACKS; track++) {
        	for (int layer=0; layer < MidiDefaults.LAYERS; layer++) {
        		if (beatTime[track][layer] < newTime) {
        			beatTime[track][layer] = newTime;
        		};
        	}
        }
    }

    /**
     * Returns the timer for the current track and current layer.
     * @return the timer value for the current track, specified in Pulses Per Quarter (PPQ)
     */
    public double getTrackBeatTime() {
        return beatTime[getCurrentTrackNumber()][getCurrentLayerNumber()];
    }

    public void addTrackTickTimeBookmark(String timeBookmarkID) {
        bookmarkedTrackTimeMap.put(timeBookmarkID, getTrackBeatTime());
    }

    public double getTrackBeatTimeBookmark(String timeBookmarkID) {
    	return bookmarkedTrackTimeMap.get(timeBookmarkID);
    }

    /**
     * Returns the latest track time across all layers in the given track
     * @param trackNumber
     */
    public double getLatestTrackBeatTime(byte trackNumber) {
    	double latestTime = 0.0D;
    	for (byte i=0; i < MidiDefaults.LAYERS; i++) {
    		if (beatTime[trackNumber][i] > latestTime) {
    			latestTime = beatTime[trackNumber][i];
    		}
    	}
    	return latestTime;
    }
}