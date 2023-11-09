package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import br.dsp.projeto.sglcspringjpa.entiity.Pessoa;

public interface PessoaDAO extends JpaRepository<Pessoa, Integer>{
    
    @Query("select p from Pessoa p where p.cpf = :cpf")
    public Pessoa findPessoaByCpf(String cpf);

    @Query(name="pessoaPorCpf")
    public Pessoa findPessoaPorCpfNomeado(String cpf);

    @Query("select p from Pessoa p where p.nome ilike %:nome%")
    public List<Pessoa> findPessoaPorNomeEspecifico(String nome);
}
