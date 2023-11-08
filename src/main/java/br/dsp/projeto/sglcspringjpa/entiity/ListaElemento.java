package br.dsp.projeto.sglcspringjpa.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Elemento elemento;
    
    @ManyToOne
    private Compra compra;
    
    @Size(min = 0, max = 100, message = "Escolha entre 0 a 100 unidades.")
    @Column(nullable = false)
    private float quantidade;
    
    @Column(nullable = false)
    private float valorElemento;

    public float getValorTotal(){
        return this.quantidade * this.valorElemento;
    }
}
