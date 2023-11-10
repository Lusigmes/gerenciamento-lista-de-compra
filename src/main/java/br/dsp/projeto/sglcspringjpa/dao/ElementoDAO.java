package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dsp.projeto.sglcspringjpa.entiity.Elemento;
import java.util.List;


public interface ElementoDAO extends JpaRepository<Elemento, Integer> {
    
    @Query("select e from Elemento e where e.nome iLike %:nome%")
    public List<Elemento> findElementosByNome(String nome);
}