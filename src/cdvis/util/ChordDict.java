package cdvis.util;

import java.util.Map;

public class ChordDict {
    public static final Map<String, String> chord3 = Map.ofEntries(
            Map.entry("43", "M"),
            Map.entry("34", "m"),
            Map.entry("25", "sus2"),

            Map.entry("33", "°"),
            Map.entry("44", "+"),

            Map.entry("74", "Δ7"),
            Map.entry("47", "Δ7"),

            Map.entry("73", "7"),
            Map.entry("37", "7")
    );
    public static final Map<String, String> chord7 = Map.ofEntries(
            Map.entry("434" , "Δ7"),
            Map.entry("524" , "Δ7sus4"),
            Map.entry("254" , "Δ9"),
            Map.entry("227" , "Δ9"),
            Map.entry("223" , "Δ9"),

            Map.entry("443", "Δ+"),

            Map.entry("433" , "7"),
            Map.entry("523" , "7sus4"),
            Map.entry("253" , "9"),
            Map.entry("226" , "9"),

            Map.entry("133" , "7b9"),
            Map.entry("136" , "7b9"),
            Map.entry("163" , "7b9"),

            Map.entry("343" , "m7"),
            Map.entry("217" , "m9"),
            Map.entry("214" , "m9"),

            Map.entry("333" , "°7"),
            Map.entry("334" , "ø7"),
            Map.entry("344" , "mM7")
    );
    public static final Map<String, String> chord9 = Map.ofEntries(
            Map.entry("2234" , "Δ9"),

            Map.entry("2233" , "9"),
            Map.entry("1333" , "7b9"),

            Map.entry("2143" , "m9"),
            Map.entry("2144" , "mM9"),

            Map.entry("2232" , "6/9")
    );

}
