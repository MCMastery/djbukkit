package com.dgrissom.djbukkit;

import org.bukkit.Bukkit;
import org.bukkit.block.NoteBlock;

public class Player {
    private Player() {}

    public static void playSong(Song song, NoteBlock block) {
        if (song.getParts().size() == 0)
            return;
        for (Part part : song.getParts())
            playPart(block, song, part);
    }

    private static void playPartRecursive(NoteBlock block, Song song, Part part, int index) {
        Note note = part.getNotes().get(index);
        playSound(block, part.getInstrument(), note);

        if (index + 1 < part.getNotes().size()) {
            double duration = note.getDuration() / song.getBeatsPerMinute() * 60; // note duration in seconds
            long tickDuration = Math.round(duration * 20);
            Bukkit.getScheduler().scheduleSyncDelayedTask(DJBukkit.getInstance(), () -> playPartRecursive(block, song, part, index + 1), tickDuration);
        }
    }

    public static void playPart(NoteBlock block, Song song, Part part) {
        if (part.getNotes().size() == 0)
            return;
        playPartRecursive(block, song, part, 0);
    }

    public static void playSound(NoteBlock block, Instrument instrument, Note note) {
        if (note instanceof Rest)
            return;
        instrument.play(block, note);
    }
}
