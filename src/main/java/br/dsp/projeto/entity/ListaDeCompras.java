package br.dsp.projeto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

import br.dsp.projeto.entity.enums.Tipo;

@NamedQueries({
    @NamedQuery(name = "ListaDeCompras.listasDeComprasPorNome", 
    query = "SELECT l FROM ListaDeCompras l LEFT JOIN FETCH l.itens WHERE l.nome like LOWER(CONCAT('%', :nome, '%'))")
})

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "listas_de_compras")
@Document(collection = "listascompras")
public class ListaDeCompras {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O nome da lista é obrigatório")
    @Size(min = 3, max = 200, message = "O nome da lista deve ter entre 3 e 200 caracteres")
    private String nome;

    @NotNull(message = "A data de criação é obrigatória")
    private LocalDateTime dataCriacao;

    @NotNull(message = "O tipo da lista é obrigatório")
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "listaDeCompras", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemLista> itens;

    public float getValorTotal() {
        float valorTotal = 0;
        if (this.itens != null) {
            for (ItemLista item : this.itens) {
                valorTotal += item.getValorTotal();
            }
        }
        return valorTotal;
    }

    public void adicionarItemALista(ItemLista itemLista) {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }

        itemLista.setListaDeCompras(this);

        this.itens.add(itemLista);
    }

    public void atualizarItemNaLista(ItemLista itemAtualizado) {
        if (this.itens != null && !this.itens.isEmpty()) {
            // Procura o item na lista pelo ID
            ItemLista itemParaAtualizar = this.itens.stream()
                    .filter(item -> item.getId().equals(itemAtualizado.getId()))
                    .findFirst()
                    .orElse(null);

            if (itemParaAtualizar != null) {
                itemParaAtualizar.setProduto(itemAtualizado.getProduto());
                itemParaAtualizar.setQuantidade(itemAtualizado.getQuantidade());
                itemParaAtualizar.setDescricao(itemAtualizado.getDescricao());
                itemParaAtualizar.setPrecoUnitario(itemAtualizado.getPrecoUnitario());
                itemParaAtualizar.setObtido(itemAtualizado.getObtido());
            }
        }
    }

    public void removerItemDaLista(String itemId) {
        if (this.itens != null && !this.itens.isEmpty()) {
            this.itens.removeIf(item -> item.getId().equals(itemId));
        }
    }

    public String toStringReduzido() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListaDeCompras [id=").append(id).append(", nome=").append(nome)
                .append(", dataCriacao=").append(dataCriacao.format(formatter)).append(", tipo=").append(tipo)
                .append(", valorTotal=").append(getValorTotal()).append("]");
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListaDeCompras [id=").append(id).append(", nome=").append(nome)
                .append(", dataCriacao=").append(dataCriacao.format(formatter)).append(", tipo=")
                .append(tipo.getLabel())
                .append(", usuario=").append(usuario)
                .append(", valorTotal=").append(getValorTotal()).append("]");
        return builder.toString();
    }

}
