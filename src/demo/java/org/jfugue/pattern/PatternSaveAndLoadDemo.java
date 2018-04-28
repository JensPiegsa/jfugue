package org.jfugue.pattern;

import java.io.File;
import java.io.IOException;

public class PatternSaveAndLoadDemo {
    public static void main(String[] args) throws IOException {
        Pattern pattern = new Pattern("T[Adagio] V0 I[Piano] Cmajh Emajh Gmajh V1 I[Flute] Cq Eq Gq Gq Cq Gq");
        pattern.save(new File("pattern-output-demo.jfugue"), new String[] { "This is a comment!", "So is this!" });
        
        Pattern pattern2 = Pattern.load(new File("pattern-output-demo.jfugue"));
        System.out.println(pattern2);
    }
}
