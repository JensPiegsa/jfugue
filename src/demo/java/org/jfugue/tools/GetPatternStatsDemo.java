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

package org.jfugue.tools;

import org.jfugue.demo.DemoPrint;
import org.jfugue.pattern.Pattern;


/**
 * The pattern analyzer class can be used in style and composer analysis, music string prediction,
 * and music comparisons.
 * 
 * @author GTMEHRER
 */
public class GetPatternStatsDemo {
    public static void main(String args[]){
        // Use GetPatternStats to get stats for a single pattern or midi file
        GetPatternStats pa = new GetPatternStats();
        pa.parsePattern(new Pattern("Aq Cmajq D#i Ei Fq"), Boolean.TRUE);
        
        // Data can be retrieved through the various getStats methods
        GetPatternStats.Stats pitchStatistics = pa.getPitchStats();
        DemoPrint.step("Pitch average: " + pitchStatistics.getAverage()); //Average is always (mean - min)
        
        // To see all stats we can call toString()
        DemoPrint.step("All stats: "+pa.toString());
        
        // We can also add data to an existing set by setting the "clear" parameter to false
        pa.parsePattern(new Pattern("Bq Cq Di Ei Fw"), Boolean.FALSE);
        // To get the updated stats we must call the getStats methods again
        DemoPrint.step("Pitch average after adding data to the parsed pattern: " + pa.getPitchStats().getAverage());
        // The new stats will now include all patterns parsed since the class was 
        // instantiated or since the clear flag was set to true
           
        // The class also includes a comparison method that accepts two patterns and returns the average difference
        double difference = pa.comparePatterns(new Pattern("Aq Bq Cq"), new Pattern("Cq Bq Aq"));
        DemoPrint.step("Difference between two patterns: " + difference);
        
        // This method will clear all previous data and set the approximate difference for each of the stats
        GetPatternStats.Stats intervalStats = pa.getIntervalStats();
        DemoPrint.step("Interval average difference:  " + intervalStats.getAverage());
    }
}
