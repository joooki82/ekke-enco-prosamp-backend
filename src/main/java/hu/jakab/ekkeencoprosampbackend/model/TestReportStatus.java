package hu.jakab.ekkeencoprosampbackend.model;

public enum TestReportStatus {
    DRAFT("Piszkozat"),
    FINALIZED("Véglegesítve"),
    APPROVED("Jóváhagyva"),
    REJECTED("Elutasítva");

    private final String magyarMegnevezes;

    TestReportStatus(String magyarMegnevezes) {
        this.magyarMegnevezes = magyarMegnevezes;
    }

    public String getMagyarMegnevezes() {
        return magyarMegnevezes;
    }
}
