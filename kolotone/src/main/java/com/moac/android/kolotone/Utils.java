package com.moac.android.kolotone;

import android.content.Context;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

import java.io.*;

public class Utils {
    public static String objectToString(Serializable object) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(out).writeObject(object);
            byte[] data = out.toByteArray();
            out.close();

            out = new ByteArrayOutputStream();
            Base64OutputStream b64 = new Base64OutputStream(out, Base64.DEFAULT);
            b64.write(data);
            b64.close();
            out.close();

            return new String(out.toByteArray());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object stringToObject(String encodedObject) {
        try {
            return new ObjectInputStream(new Base64InputStream(new ByteArrayInputStream(encodedObject.getBytes()), Base64.DEFAULT)).readObject();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static String[] noteString = new String[]{ "C ", "C#", "D ", "D#", "E ", "F ", "F#", "G ", "G#", "A ", "A#", "B " };

    public static String midiNoteToName(int initialNote) {
        int octave = (initialNote / 12) - 1;
        int noteIndex = (initialNote % 12);
        String note = noteString[noteIndex] + octave;
        return note;
    }

    public static String frmRes(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String getStringResource(Context context, String thingie) {
        try {
            String[] split = thingie.split("/");
            String pack = split[0].replace("@", "");
            String name = split[1];
            int id = context.getResources().getIdentifier(name, pack, context.getPackageName());
            return context.getResources().getString(id);
        } catch(Exception e) {
            return thingie;
        }
    }
}
