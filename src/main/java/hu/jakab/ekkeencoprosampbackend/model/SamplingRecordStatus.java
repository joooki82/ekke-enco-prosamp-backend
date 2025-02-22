package hu.jakab.ekkeencoprosampbackend.model;

public enum SamplingRecordStatus {
    ACTIVE("Aktív"),
    LOST("Elveszett"),
    BROKEN("Sérült"),
    INVALID("Érvénytelen");

    private final String magyarMegnevezes;

    SamplingRecordStatus(String magyarMegnevezes) {
        this.magyarMegnevezes = magyarMegnevezes;
    }

    public String getMagyarMegnevezes() {
        return magyarMegnevezes;
    }
}
