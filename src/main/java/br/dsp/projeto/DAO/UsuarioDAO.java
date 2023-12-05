package br.dsp.projeto.DAO;

import br.dsp.projeto.entity.Usuario;
import br.dsp.projeto.entity.enums.Sexo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

public interface UsuarioDAO {
    Usuario save(Usuario entity);

    void deleteById(String id);

    Optional<Usuario> findById(String id);

    List<Usuario> findAll();

    List<Usuario> findAllIgnoreCase(Sort sort);

    Usuario findByNomeUsuario(String nomeUsuario);

    List<Usuario> findAllByNomeCompletoContainingIgnoreCase(String nome);

    List<Usuario> findByDataNascimentoBetween(LocalDate dataMin, LocalDate dataMax);

    List<Usuario> findByEmailIgnoreCase(String email);

    long count();

    List<Usuario> findBySexo(Sexo sexo);

}
