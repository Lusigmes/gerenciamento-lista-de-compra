package br.dsp.projeto.DAO.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dsp.projeto.entity.enums.Sexo;
import br.dsp.projeto.DAO.UsuarioDAO;
import br.dsp.projeto.entity.Usuario;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface UsuarioJPADAO extends JpaRepository<Usuario, String>, UsuarioDAO {

    // Consulta nomeada para encontrar o usuario com determinado nome de usuário
    @Query(name = "Usuario.usuarioPorNomeUsuario")
    public Usuario findByNomeUsuario(String nomeUsuario);

    // Consulta baseada no nome do método para buscar usuários por parte do nome
    List<Usuario> findAllByNomeCompletoContainingIgnoreCase(String nome);

    // Consulta JPQL para buscar usuários pela data de nascimento entre datas
    @Query("SELECT u FROM Usuario u WHERE u.dataNascimento BETWEEN :dataMin AND :dataMax")
    List<Usuario> findByDataNascimentoBetween(LocalDate dataMin,
            LocalDate dataMax);

    // consulta jpql para buscar email por dominio
    @Query("select u from Usuario u where u.email iLike CONCAT('%', '@', :email, '%')")
    public List<Usuario> findByEmailIgnoreCase(String email);

    // Consulta SQL para contar o número total de usuários
    @Query(value = "select count(*) from usuarios", nativeQuery = true)
    long count();

    // Consulta JPQL para buscar usuários ordenados pelo nome
    // de usuário em ordem
    // ascendente
    @Query("SELECT u FROM Usuario u ORDER BY lower(u.nomeCompleto) ASC")
    List<Usuario> findAllIgnoreCase(Sort sort);

    // Consulta baseada no nome do método para encontrar usuários de um determinado
    // sexo
    List<Usuario> findBySexo(Sexo sexo);
}
