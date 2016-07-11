package com.dgrissom.djbukkit;

import java.util.HashSet;
import java.util.Set;

public class Song {
    private String name;
    private Set<Part> parts;
    private double bpm;

    public Song() {
        this.name = "Song";
        this.parts = new HashSet<>();
        this.bpm = 120;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Part> getParts() {
        return this.parts;
    }
    public void addPart(Part part) {
        this.parts.add(part);
    }
    public double getBeatsPerMinute() {
        return this.bpm;
    }
    public void setBeatsPerMinute(double bpm) {
        this.bpm = bpm;
    }
}
