package com.dgrissom.djbukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part {
    private final Instrument instrument;
    private final List<Note> notes;

    public Part(Instrument instrument, List<Note> notes) {
        this.instrument = instrument;
        this.notes = Collections.unmodifiableList(notes);
    }

    public Instrument getInstrument() {
        return this.instrument;
    }
    public List<Note> getNotes() {
        return this.notes;
    }

    public String serialize() {
        String s = this.instrument.name();
        for (Note note : this.notes)
            s += ";" + note.serialize();
        return s;
    }
    public static Part deserialize(String s) {
        String[] split = s.split(";");
        Instrument instrument = Instrument.valueOf(split[0].toUpperCase().replace(' ', '_'));
        List<Note> notes = new ArrayList<>();
        for (int i = 1; i < split.length; i++)
            notes.add(Note.deserialize(split[i]));
        return new Part(instrument, notes);
    }
}
