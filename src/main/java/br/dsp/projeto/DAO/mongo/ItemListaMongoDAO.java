package br.dsp.projeto.DAO.mongo;

import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.dsp.projeto.DAO.ItemListaDAO;
import br.dsp.projeto.entity.ItemLista;

import java.util.List;

@Repository
@Primary
public interface ItemListaMongoDAO extends MongoRepository<ItemLista, String>, ItemListaDAO {

    @Query("{'nome': ?0, 'listaId': ?1 }")
    List<ItemLista> findByNomeAndListaId(String nome, String listaId);

    @Query("{ '_id': ?0, 'listaDeCompras.id': ?1 }")
    ItemLista findByIdAndListaDeComprasId(String itemId, String listaId);

    List<ItemLista> findByListaDeComprasId(String listaId);

    @Query(value = "{ 'listaDeCompras.id': ?0 }", count = true)
    long countByListaId(String listaId);

    List<ItemLista> findAllByListaDeComprasIdOrderByQuantidadeDesc(String listaId);

    @Query("{ 'listaDeCompras.id': ?0, 'precoUnitario': { $gte: ?1, $lte: ?2 } }")
    List<ItemLista> findAllByListaIdAndPrecoBetween(String listaId, float minPreco, float maxPreco);

    @Query("{ 'listaDeCompras.id': ?0, 'descricao': { $regex: ?1, $options: 'i' } }")
    List<ItemLista> findByListaIdAndDescricaoContainingIgnoreCase(String listaId, String descricao);

    @Query("{ 'listaDeCompras.id': ?0, 'obtido': 'TRUE' }")
    List<ItemLista> findAllObtidosByListaId(String listaId);

    @Query("{ 'listaDeCompras.id': ?0, 'obtido': 'FALSE' }")
    List<ItemLista> findAllNaoObtidosByListaId(String listaId);
}
