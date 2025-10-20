package com.tomus.labels;

public enum Position {
    GOLEIRO("Goleiro"),
    ZAGUEIRO("Zagueiro"),
    LATERAL("Lateral"),
    VOLANTE("Volante"),
    MEIO_CAMPO("Meio-Campo"),
    ATACANTE("Atacante");

    private final String label;

    Position(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
