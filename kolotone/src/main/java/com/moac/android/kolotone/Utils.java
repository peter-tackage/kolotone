package com.moac.android.kolotone;

public class Utils {

    private final static String[] noteString = new String[]{ "C ", "C#", "D ", "D#", "E ", "F ", "F#", "G ", "G#", "A ", "A#", "B " };

    public static String midiNoteToName(int initialNote) {
        int octave = (initialNote / 12) - 1;
        int noteIndex = (initialNote % 12);
        String note = noteString[noteIndex] + octave;
        return note;
    }

}
