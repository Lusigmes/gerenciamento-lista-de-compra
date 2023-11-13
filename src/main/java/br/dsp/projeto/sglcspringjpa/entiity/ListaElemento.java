package br.dsp.projeto.sglcspringjpa.entiity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listas_elementos")
public class ListaElemento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@NotNull?
    @ManyToOne
    @NonNull
    private Elemento elemento;
    
    @JsonManagedReference
    @NonNull
    @ManyToOne
    private Compra compra;
    
    //@Size(min = 0, max = 100, message = "Escolha entre 0 a 100 unidades.")
    //@Column(nullable = false)
    private float quantidade;
    
    @Column(nullable = false)
    private float valorElemento;

    public float getValorTotal(){
        return this.quantidade * this.valorElemento;
    }
    @Override
    public String toString() {
        return "Produto #" + id + "\n" +
               "Nome: " + elemento.getNome() + "\n" +
               "Descrição: " + elemento.getDescricao() + "\n" +
               "Quantidade: " + quantidade + "\n" +
               "Valor por unidade: " + valorElemento + "\n" ;// +
            //    "Valor Total: " + getValorTotal() + "\n";
    }
}