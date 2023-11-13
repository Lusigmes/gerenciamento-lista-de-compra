package br.dsp.projeto.sglcspringjpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.dsp.projeto.sglcspringjpa.entiity.ListaElemento;

@Repository
public interface ListaElementoDAO extends JpaRepository<ListaElemento, Integer>{
    
    @Query("select le from ListaElemento le where le.compra.id = :idCompra order by le.id asc")
    public List<ListaElemento> findByCompraId(int idCompra);

}

/*Obter o produto por id.
Obter o produto por código.
Obter os produtos por descrição. Parte da string de consulta (substring) é usada para obtenção dos produtos.
Dado um preço, obter os produtos com valores menores ou iguais a esse preço.
Dadas uma data inicial e uma data final, obter os produtos cuja data da última entrada está entre essas datas. */

//select * from mensagem where data_entrada between :dataEntrada and :dataSaida order by id asc