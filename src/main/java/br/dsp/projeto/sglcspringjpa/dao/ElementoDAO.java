package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dsp.projeto.sglcspringjpa.entiity.Elemento;
import java.util.List;


public interface ElementoDAO extends JpaRepository<Elemento, Integer> {
    
    public Elemento findElementoById(int id);

    public List<Elemento> findElementosById(int id);
}
