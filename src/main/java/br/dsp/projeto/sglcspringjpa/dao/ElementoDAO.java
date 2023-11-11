package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dsp.projeto.sglcspringjpa.entiity.Elemento;
import java.util.List;


public interface ElementoDAO extends JpaRepository<Elemento, Integer> {
    
    @Query("select e from Elemento e where e.nome iLike %:nome%")
    public List<Elemento> findElementosByNome(String nome);
    
    @Query("select e from Elemento e where e.descricao iLike %:descricao%")
    public List<Elemento> findElementosByDescricao(String descricao);
    
    @Query("select e from Elemento e where e.valor <= :valor")
    public List<Elemento> findElementosByPreco(float valor);

    @Query(name = "findElementoByCategoria")
    public List<Elemento> findElementosByCategoriaNamed(String categoria);





}

/*Obter o produto por id.
Obter o produto por código.
Obter os produtos por descrição. Parte da string de consulta (substring) é usada para obtenção dos produtos.
Dado um preço, obter os produtos com valores menores ou iguais a esse preço.
Dadas uma data inicial e uma data final, obter os produtos cuja data da última entrada está entre essas datas. */