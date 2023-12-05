package br.dsp.projeto.entity;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.mongodb.core.mapping.Document;

import br.dsp.projeto.entity.enums.Categoria;
import jakarta.validation.constraints.*;

@NamedQueries({
        @NamedQuery(name = "Produto.produtoPorCodigo", query = "SELECT p FROM Produto p WHERE p.codigo = :codigo")
})

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")
@Document(collection = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O código é obrigatório")
    @Size(min = 3, max = 10, message = "O código deve ter entre 3 e 10 caracteres")
    @Column(unique = true)
    private String codigo;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 200 caracteres")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Override
    public String toString() {
        return "Produto [id=" + id + ", codigo=" + codigo + ", nome=" + this.nome +
                ", categoria=" + categoria.getLabel() + "]";
    }
}
