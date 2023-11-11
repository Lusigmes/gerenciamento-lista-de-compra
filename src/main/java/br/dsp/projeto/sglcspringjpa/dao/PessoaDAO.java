package br.dsp.projeto.sglcspringjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import br.dsp.projeto.sglcspringjpa.entiity.Pessoa;

public interface PessoaDAO extends JpaRepository<Pessoa, Integer>{
    
    @Query("select p from Pessoa p where p.cpf = :cpf")
    public Pessoa findPessoaByCpf(String cpf);
    
    @Query(name="pessoaPorCpf")
    public Pessoa findPessoaPorCpfNomeado(String cpf);

    @Query("select p from Pessoa p order by p.id asc")
    public List<Pessoa> findAllOrdenado();

    @Query("select p from Pessoa p where p.nome ilike %:nome%")
    public List<Pessoa> findPessoaPorCaractereNome(String nome);

    // consulta baseadas no nome do método
    public List<Pessoa> findByNomeContainingIgnoreCase(String str);

    @Query("select p from Pessoa p where p.sexo = upper(:sexo)")
    public List<Pessoa> findBySexo(String sexo);
                                        //p.data_nascimento
    @Query("select p from Pessoa p where p.dataNascimento between :dataMin and :dataMax")
    public List<Pessoa> findByBirthBetweenDatas(Date dataMin, Date dataMax);

    @Query("select p from Pessoa p where p.email iLike CONCAT('%', '@', :email, '%')")
    public List<Pessoa> findByEmailIgnoreCase(String email);

}

/*Obter o produto por id.
Obter o produto por código.
Obter os produtos por descrição. Parte da string de consulta (substring) é usada para obtenção dos produtos.
Dado um preço, obter os produtos com valores menores ou iguais a esse preço.
Dadas uma data inicial e uma data final, obter os produtos cuja data da última entrada está entre essas datas. */