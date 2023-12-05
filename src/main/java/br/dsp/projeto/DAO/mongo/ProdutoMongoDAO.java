package br.dsp.projeto.DAO.mongo;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

import br.dsp.projeto.DAO.ProdutoDAO;
import br.dsp.projeto.entity.Produto;
import br.dsp.projeto.entity.enums.Categoria;

import java.util.List;

@Repository
@Primary
public interface ProdutoMongoDAO extends MongoRepository<Produto, String>, ProdutoDAO {

   // Consulta baseada no nome do método para buscar todos os produtos de uma
   // determinada categoria
   List<Produto> findAllByCategoria(Categoria categoria);

   // Consulta baseada no nome do método para contar a quantidade de produtos de
   // uma determinada categoria
   long countByCategoria(Categoria categoria);

   // Consulta baseada no nome do método para obter produtos por nome contendo uma
   // determinada substring (ignorando maiúsculas/minúsculas)
   List<Produto> findAllByNomeContainingIgnoreCase(String substring);

   // Consulta nomeada JPQL para obter produto por código
   @Query("{ 'codigo' : ?0 }")
   Produto findPorCodigo(String codigo);

   // Consulta MongoDB para listar produtos por ordem alfabética de nome
   @Query(value = "{}", collation = "{ 'locale' : 'pt' }")
   List<Produto> findAllOrderByNomeAsc(Sort sort);

   // Contagem total de produtos (sem necessidade de consulta nativa)
   long count();
}
