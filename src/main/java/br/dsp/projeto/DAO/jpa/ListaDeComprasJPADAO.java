package br.dsp.projeto.DAO.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dsp.projeto.DAO.ListaDeComprasDAO;
import br.dsp.projeto.entity.ItemLista;
import br.dsp.projeto.entity.ListaDeCompras;
import br.dsp.projeto.entity.Usuario;
import br.dsp.projeto.entity.enums.Tipo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface ListaDeComprasJPADAO extends JpaRepository<ListaDeCompras, String>, ListaDeComprasDAO {

    // Consulta baseada no nome do método para buscar listas de compras por nome
    // @Query("SELECT l FROM ListaDeCompras l LEFT JOIN FETCH l.itens WHERE l.nome
    // like LOWER(CONCAT('%', :nome, '%'))")
    @Query(name = "ListaDeCompras.listasDeComprasPorNome")
    List<ListaDeCompras> findByNomeContainingIgnoreCase(String nome);

    // Consulta JPQL para buscar listas de compras criadas após uma determinada
    // data
    @Query("SELECT l FROM ListaDeCompras l WHERE l.dataCriacao > :data")
    List<ListaDeCompras> findByDataCriacaoAfterDesc(LocalDateTime data);

    @Query("SELECT DISTINCT l FROM ListaDeCompras l LEFT JOIN FETCH l.itens ORDER BY l.dataCriacao DESC")
    List<ListaDeCompras> findByOrderByDataCriacaoDesc();

    @Query("SELECT l FROM ListaDeCompras l LEFT JOIN FETCH l.itens WHERE l.tipo = :tipo")
    List<ListaDeCompras> findByTipo(@Param("tipo") Tipo tipo);

    @Query("SELECT DISTINCT l FROM ListaDeCompras l LEFT JOIN FETCH l.itens WHERE l.dataCriacao > :dataCriacao ORDER BY l.dataCriacao DESC")
    List<ListaDeCompras> findByDataCriacaoAfterOrderByDataCriacaoDesc(@Param("dataCriacao") LocalDateTime dataCriacao);

    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    Usuario findUsuarioById(String id);

    @Query("SELECT l FROM ListaDeCompras l LEFT JOIN FETCH l.usuario WHERE l.id = :id")
    ListaDeCompras findListaDeComprasWithUsuarioById(String id);

    // Consulta SQL nativa para contar o número total de listas de compras
    @Query(value = "SELECT COUNT(*) FROM listas_de_compras", nativeQuery = true)
    long count();

    @Query("select l from ListaDeCompras l left join fetch l.itens")
    List<ListaDeCompras> findAll();

    @Query("SELECT i FROM ItemLista i LEFT JOIN FETCH i.listaDeCompras l WHERE l.id = :id")
    List<ItemLista> findByIdItens(@Param("id") String id);

    @Query("SELECT l FROM ListaDeCompras l LEFT JOIN FETCH l.itens WHERE l.id = :id")
    ListaDeCompras findListaDeComprasById(@Param("id") String id);

}
