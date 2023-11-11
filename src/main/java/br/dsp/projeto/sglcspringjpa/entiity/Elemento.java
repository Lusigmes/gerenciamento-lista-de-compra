package br.dsp.projeto.sglcspringjpa.entiity;


import br.dsp.projeto.sglcspringjpa.entiity.enums.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NamedQueries({
		@NamedQuery(name = "findElementoByCategoria", query = "select e from Elemento e where e.categoria = upper(:categoria)")
})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "elementos")//itens
public class Elemento { 
//item para elemento ? por causa do tipo Item,  Item item ;
   

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    
    @NotNull(message = "Nome obrigatorio")
    @Column(nullable = false)
    private String nome;
    
    @NotBlank(message = "Código obrigatório")
    @Column(unique = true, nullable = false)
    @Size(min = 10, max=10, message = "O código deve conter 10 dígitos")
    private String codigo;
    
    @NotBlank
    @Size(min = 0, max=255, message = "Escreva alguma descrição")
    private String descricao;
    
    @NotNull(message = "Valor obrigatorio")
    @Column(nullable = false)
    private float valor;

    @NotNull
    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

}
