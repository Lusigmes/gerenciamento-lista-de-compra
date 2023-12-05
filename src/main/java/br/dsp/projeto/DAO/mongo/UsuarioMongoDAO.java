package br.dsp.projeto.DAO.mongo;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.dsp.projeto.DAO.UsuarioDAO;
import br.dsp.projeto.entity.Usuario;
import br.dsp.projeto.entity.enums.Sexo;

import java.time.LocalDate;
import java.util.List;

@Repository
@Primary
public interface UsuarioMongoDAO extends MongoRepository<Usuario, String>, UsuarioDAO {

    // Consulta baseada no nome do método para encontrar o usuário com determinado
    // nome de usuário
    Usuario findByNomeUsuario(String nomeUsuario);

    // Consulta baseada no nome do método para buscar usuários por parte do nome
    @Query("{ 'nomeCompleto' : { $regex: ?0, $options: 'i' } }")
    List<Usuario> findAllByNomeCompletoContainingIgnoreCase(String nome);

    // Consulta MongoDB para buscar usuários pela data de nascimento entre datas
    List<Usuario> findByDataNascimentoBetween(LocalDate dataMin, LocalDate dataMax);

    // Consulta MongoDB para buscar usuários pelo domínio do email
    @Query("{ 'email' : { $regex: '@?0', $options: 'i' } }")
    List<Usuario> findByEmailIgnoreCase(String email);

    @Query(value = "{}", collation = "{ 'locale' : 'pt' }")
    List<Usuario> findAllIgnoreCase(Sort sort);

    // Contagem total de usuários (sem necessidade de consulta nativa)
    long count();

    // Consulta baseada no nome do método para encontrar usuários de um determinado
    // sexo
    List<Usuario> findBySexo(Sexo sexo);

}
