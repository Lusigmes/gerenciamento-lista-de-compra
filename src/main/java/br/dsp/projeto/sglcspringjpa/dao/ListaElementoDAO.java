package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dsp.projeto.sglcspringjpa.entiity.ListaElemento;

public interface ListaElementoDAO extends JpaRepository<ListaElemento, Integer>{
    
}
