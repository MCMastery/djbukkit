package com.dgrissom.djbukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class SongParser {
    private final File file;

    public SongParser(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public Song parse() throws IOException {
        List<String> lines = Files.readAllLines(this.file.toPath());

        try {
            Song song = new Song();
            song.setName(lines.get(0));
            song.setBeatsPerMinute(Double.parseDouble(lines.get(1)));
            for (int line = 2; line < lines.size(); line++)
                song.addPart(Part.deserialize(lines.get(line)));
            return song;
        } catch (Exception e) {
            DJBukkit.getInstance().getLogger().severe("Corrupt song file (" + this.file.getName() + ")!");
            return null;
        }
    }
}
