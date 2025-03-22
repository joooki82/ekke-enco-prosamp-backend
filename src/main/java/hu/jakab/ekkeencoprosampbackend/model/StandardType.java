package hu.jakab.ekkeencoprosampbackend.model;

public enum StandardType {
    SAMPLING("Mintavétel"),
    ANALYSES("Vizsgálat");

    private final String magyarName;

    StandardType(String magyarName) {
        this.magyarName = magyarName;
    }

    public String getMagyarName() {
        return magyarName;
    }
}
