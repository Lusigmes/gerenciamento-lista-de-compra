package br.dsp.projeto.entity.enums;

public enum Categoria {
    ROUPAS("Roupas"),
    ELETRONICOS("Eletrônicos"),
    LIVROS("Livros"),
    ALIMENTOS("Alimentos"),
    JOGOS("Jogos"),
    LIMPEZA("Limpeza"),
    BRINQUEDOS("Brinquedos"),
    MATERIAL_ESCRITORIO("Material de Escritório"),
    BELEZA("Beleza"),
    BEBIDAS("Bebidas"),
    MOVEIS("Móveis"),
    ESPORTES("Esportes"),
    OUTROS("Outros");

    private final String label;

    Categoria(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
