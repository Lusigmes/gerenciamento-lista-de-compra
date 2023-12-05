package br.dsp.projeto.entity.enums;

public enum Sexo {
    M("Masculino"),
    F("Feminino"),
    OUTROS("Outros");

    private final String label;

    Sexo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
