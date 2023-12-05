package br.dsp.projeto.DAO;

import java.util.List;

import br.dsp.projeto.entity.ListaDeCompras;
import br.dsp.projeto.entity.ItemLista;
import br.dsp.projeto.entity.Usuario;
import br.dsp.projeto.entity.enums.Tipo;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ListaDeComprasDAO {
    ListaDeCompras save(ListaDeCompras entity);

    void deleteById(String id);

    Optional<ListaDeCompras> findById(String id);

    List<ListaDeCompras> findAll();

    List<ListaDeCompras> findByNomeContainingIgnoreCase(String nome);

    List<ListaDeCompras> findByDataCriacaoAfterOrderByDataCriacaoDesc(LocalDateTime data);

    List<ListaDeCompras> findByTipo(Tipo tipo);

    List<ListaDeCompras> findByOrderByDataCriacaoDesc();

    List<ItemLista> findByIdItens(String id);

    Usuario findUsuarioById(String id);

    long count();

    ListaDeCompras findListaDeComprasById(String id);

    ListaDeCompras findListaDeComprasWithUsuarioById(String id);

}
