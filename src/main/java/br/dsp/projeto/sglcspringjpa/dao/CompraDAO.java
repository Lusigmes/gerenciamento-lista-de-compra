package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dsp.projeto.sglcspringjpa.entiity.Compra;

public interface CompraDAO extends JpaRepository<Compra, Integer> {
    
}
