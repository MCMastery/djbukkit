package com.dgrissom.djbukkit;

public class Note {
    public enum Accidental {
        FLAT, NATURAL, SHARP
    }
    public enum Tone {
        A, B, C, D, E, F, G;

        public org.bukkit.Note.Tone toBukkitTone() {
            return org.bukkit.Note.Tone.valueOf(name());
        }
    }

    private final Tone tone;
    private final Accidental accidental;
    private final int octave;
    private final double duration; // in beats

    public Note(Tone tone, Accidental accidental, int octave, double duration) {
        this.tone = tone;
        this.accidental = accidental;
        this.octave = octave;
        this.duration = duration;
    }

    public Tone getTone() {
        return this.tone;
    }
    public Accidental getAccidental() {
        return this.accidental;
    }
    public int getOctave() {
        return this.octave;
    }
    public double getDuration() {
        return this.duration;
    }

    // NOTE_NAME:ACCIDENTAL:OCTAVE:DURATION unless it is REST:DURATION
    public String serialize() {
        return this.tone + ":" + this.accidental + ":" + this.octave + ":" + this.duration;
    }
    public static Note deserialize(String s) {
        String[] noteSplit = s.split(":");

        if (noteSplit[0].equalsIgnoreCase("REST")) {
            double duration = Double.parseDouble(noteSplit[1]);
            return new Rest(duration);
        }

        Tone tone = Tone.valueOf(noteSplit[0].toUpperCase());
        Accidental accidental = Accidental.valueOf(noteSplit[1].toUpperCase());
        int octave = Integer.parseInt(noteSplit[2]);
        double duration = Double.parseDouble(noteSplit[3]);
        return new Note(tone, accidental, octave, duration);
    }

    public org.bukkit.Note getBukkitNote() {
        switch (this.accidental) {
            default:
                return org.bukkit.Note.natural(this.octave, this.tone.toBukkitTone());
            case SHARP:
                return org.bukkit.Note.sharp(this.octave, this.tone.toBukkitTone());
            case FLAT:
                return org.bukkit.Note.flat(this.octave, this.tone.toBukkitTone());
        }
    }
}
