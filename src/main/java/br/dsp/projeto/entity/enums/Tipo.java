package br.dsp.projeto.entity.enums;

public enum Tipo {
    SUPERMERCADO("Supermercado"),
    ESCRITORIO("Escritório"),
    MATERIAIS_CONSTRUCAO("Materiais de Construção"),
    DECORACAO("Decoração"),
    BELEZA_CUIDADOS("Beleza e Cuidados"),
    MATERIAIS_ESPORTIVO("Materiais Esportivos"),
    OUTROS("Outros");

    private final String label;

    Tipo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}