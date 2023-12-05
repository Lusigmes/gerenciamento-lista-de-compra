package br.dsp.projeto.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.dsp.projeto.entity.enums.BooleanEnum;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@NamedQueries({
        @NamedQuery(name = "ItemLista.itemListaPorNomeEListaId", query = "SELECT i FROM ItemLista i WHERE i.produto.nome like :nome AND i.listaDeCompras.id = :listaId"),
})

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itens_lista")
@Document(collection = "itenslista")
public class ItemLista {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "O produto é obrigatório")
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private int quantidade;

    private String descricao;

    private float precoUnitario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BooleanEnum obtido;

    @ManyToOne
    @JoinColumn(name = "lista_de_compras_id", nullable = false)
    @DBRef
    private ListaDeCompras listaDeCompras;

    public float getValorTotal() {
        return quantidade * precoUnitario;
    }

    @Override
    public String toString() {
        return "ItemLista{" +
                "id=" + id +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", descricao=" + descricao +
                ", preco unitario=" + precoUnitario +
                ", obtido=" + obtido.getLabel() +
                ", valor total=" + this.getValorTotal() +
                '}';
    }
}
