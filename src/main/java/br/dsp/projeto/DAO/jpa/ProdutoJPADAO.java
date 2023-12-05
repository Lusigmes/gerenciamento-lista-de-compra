package br.dsp.projeto.DAO.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import br.dsp.projeto.DAO.ProdutoDAO;
import br.dsp.projeto.entity.Produto;
import br.dsp.projeto.entity.enums.Categoria;

@Repository
@Primary
public interface ProdutoJPADAO extends JpaRepository<Produto, String>, ProdutoDAO {

    // Consulta JPQL para listar produtos por ordem alfabética de nome
    @Query("SELECT p FROM Produto p ORDER BY lower(p.nome)")
    List<Produto> findAllOrderByNomeAsc(Sort sort);

    // Consulta baseada no nome do método para buscar todos os produtos de uma
    // determinada categoria
    List<Produto> findAllByCategoria(Categoria categoria);

    // Consulta baseada no nome do método para contar a quantidade de produtos de
    // uma determinada categoria
    long countByCategoria(Categoria categoria);

    // Consulta SQL nativa para contar o número total de produtos
    @Query(value = "SELECT COUNT(*) FROM produtos", nativeQuery = true)
    long count();

    // Consulta baseada no nome do método para obter produtos por nome contendo uma
    // determinada
    // substring
    List<Produto> findAllByNomeContainingIgnoreCase(String substring);

    // Consulta nomeada JPQL para obter produto por código
    @Query(name = "Produto.produtoPorCodigo")
    Produto findPorCodigo(String codigo);
}
