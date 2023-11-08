package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dsp.projeto.sglcspringjpa.entiity.Pessoa;

public interface PessoaDAO extends JpaRepository<Pessoa, Integer>{
    
}
