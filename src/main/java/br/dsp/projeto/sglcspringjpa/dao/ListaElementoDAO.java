package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dsp.projeto.sglcspringjpa.entiity.ListaElemento;

public interface ListaElementoDAO extends JpaRepository<ListaElemento, Integer>{
    
}

/*Obter o produto por id.
Obter o produto por código.
Obter os produtos por descrição. Parte da string de consulta (substring) é usada para obtenção dos produtos.
Dado um preço, obter os produtos com valores menores ou iguais a esse preço.
Dadas uma data inicial e uma data final, obter os produtos cuja data da última entrada está entre essas datas. */

//select * from mensagem where data_entrada between :dataEntrada and :dataSaida order by id asc