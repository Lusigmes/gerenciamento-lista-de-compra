package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import br.dsp.projeto.sglcspringjpa.entiity.Compra;

@Repository
public interface CompraDAO extends JpaRepository<Compra, Integer> {
    
    @Query("SELECT c FROM Compra c " + //
         "left join fetch c.elementos " + //
            "where c.id = :id")
    public Compra findCompras(int id);


    @Query("select c from Compra c order by c.id asc")
    public List<Compra> findAllSorted();
}

/*Obter o produto por id.
Obter o produto por código.
Obter os produtos por descrição. Parte da string de consulta (substring) é usada para obtenção dos produtos.
Dado um preço, obter os produtos com valores menores ou iguais a esse preço.
Dadas uma data inicial e uma data final, obter os produtos cuja data da última entrada está entre essas datas. */