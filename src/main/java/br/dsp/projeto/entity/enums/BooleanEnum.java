package br.dsp.projeto.entity.enums;

public enum BooleanEnum {
    TRUE(true, "Sim"),
    FALSE(false, "Não");

    private final boolean value;
    private final String label;

    BooleanEnum(boolean value, String label) {
        this.value = value;
        this.label = label;
    }

    public boolean getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
