package org.staccato.maps;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.ReplacementMapPreprocessor;

public class SolfegeReplacementMapDemo {
	public static void main(String[] args) {
		Player player = new Player();
		ReplacementMapPreprocessor rmp = ReplacementMapPreprocessor.getInstance();
		rmp.setReplacementMap(new SolfegeReplacementMap()).setRequireAngleBrackets(false);
		Pattern pattern = new Pattern("do re mi fa so la ti do");
		System.out.println(rmp.preprocess(pattern.toString().toUpperCase(), null));
		
		rmp.setRequireAngleBrackets(true);
		player.play(new Pattern("<Do>q <Re>q <Mi>h | <Mi>q <Fa>q <So>h | <So>q <Fa>q <Mi>h | <Mi>q <Re>q <Do>h")); 
		
	}
}
