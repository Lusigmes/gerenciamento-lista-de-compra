package br.dsp.projeto.DAO.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import br.dsp.projeto.DAO.ItemListaDAO;
import br.dsp.projeto.entity.ItemLista;
import br.dsp.projeto.entity.enums.BooleanEnum;

@Repository
@Primary
public interface ItemListaJPADAO extends JpaRepository<ItemLista, String>, ItemListaDAO {

        // Consulta nomeada para buscar itens de lista por nome e ID da lista
        @Query(name = "ItemLista.itemListaPorNomeEListaId")
        List<ItemLista> findByNomeAndListaId(String nome, String listaId);

        @Query("SELECT i FROM ItemLista i WHERE i.id = :itemId AND i.listaDeCompras.id = :listaId")
        ItemLista findByIdAndListaDeComprasId(String itemId, String listaId);

        List<ItemLista> findByListaDeComprasId(String listaId);

        // Consulta SQL nativa para contar o número total de itens de lista
        @Query(value = "SELECT COUNT(*) FROM itens_lista WHERE lista_de_compras_id =:listaId", nativeQuery = true)
        long countByListaId(String listaId);

        List<ItemLista> findAllByListaDeComprasIdOrderByQuantidadeDesc(String listaId);

        // Consulta JPQL para listar itens dentro de uma faixa de preço para uma lista
        // específica
        @Query("SELECT i FROM ItemLista i WHERE i.listaDeCompras.id = :listaId AND i.precoUnitario BETWEEN :minPreco AND :maxPreco")
        List<ItemLista> findAllByListaIdAndPrecoBetween(String listaId,
                        float minPreco, float maxPreco);

        @Query("SELECT i FROM ItemLista i WHERE i.listaDeCompras.id = :listaId AND LOWER(i.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))")
        List<ItemLista> findByListaIdAndDescricaoContainingIgnoreCase(
                        String listaId, String descricao);

        @Query("SELECT i FROM ItemLista i WHERE i.listaDeCompras.id = :listaId AND i.obtido='TRUE'")
        List<ItemLista> findAllObtidosByListaId(@Param("listaId") String listaId);

        // Consulta JPQL para listar itens não obtidos para uma lista específica
        @Query("SELECT i FROM ItemLista i WHERE i.listaDeCompras.id = :listaId AND i.obtido='FALSE'")
        List<ItemLista> findAllNaoObtidosByListaId(@Param("listaId") String listaId);
}
