package com.dgrissom.djbukkit;

import org.bukkit.block.NoteBlock;

public enum Instrument {
    PIANO {
        @Override
        public void play(NoteBlock block, Note note) {
            block.play(org.bukkit.Instrument.PIANO, note.getBukkitNote());
        }
    },
    BASS_GUITAR {
        @Override
        public void play(NoteBlock block, Note note) {
            block.play(org.bukkit.Instrument.BASS_GUITAR, note.getBukkitNote());
        }
    },
    SNARE_DRUM {
        @Override
        public void play(NoteBlock block, Note note) {
            block.play(org.bukkit.Instrument.SNARE_DRUM, note.getBukkitNote());
        }
    },
    BASS_DRUM {
        @Override
        public void play(NoteBlock block, Note note) {
            block.play(org.bukkit.Instrument.BASS_DRUM, note.getBukkitNote());
        }
    };

    public abstract void play(NoteBlock block, Note note);
}
