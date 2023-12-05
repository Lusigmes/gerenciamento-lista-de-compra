package br.dsp.projeto.DAO;

import java.util.List;

import br.dsp.projeto.entity.Produto;

import br.dsp.projeto.entity.enums.Categoria;
import java.util.Optional;

import org.springframework.data.domain.Sort;

public interface ProdutoDAO {
    Produto save(Produto entity);

    void deleteById(String id);

    Optional<Produto> findById(String id);

    List<Produto> findAll();

    List<Produto> findAllOrderByNomeAsc(Sort sort);

    List<Produto> findAllByCategoria(Categoria categoria);

    long countByCategoria(Categoria categoria);

    long count();

    List<Produto> findAllByNomeContainingIgnoreCase(String substring);

    Produto findPorCodigo(String codigo);
}
