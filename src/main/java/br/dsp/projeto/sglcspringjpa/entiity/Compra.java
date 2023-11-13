package br.dsp.projeto.sglcspringjpa.entiity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.format.DateTimeFormatter;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Pessoa cliente;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Column(nullable = false)
    @NotNull(message ="Insira uma data válida")
    private LocalDateTime dataCompra;

    @JsonBackReference
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)/* fetch = FetchType.LAZY */
    private List<ListaElemento> elementos;

    @Transactional
    public float getValorTotal() {
      float total = 0;
      for (ListaElemento item: this.elementos) {
        total += item.getValorTotal();
      }
      return total;
	  } 

    @Override
    public String toString() {
        return "Compra #" + id + "\n" +
               "Cliente: " + cliente.getNome() + "\n" +
               "Data da Compra: " + dataCompra.format(formatter) + "\n" +
                elementos + "\n"; //+
              // "Valor Total: " + getValorTotal() + "\n";
    }
    
}
