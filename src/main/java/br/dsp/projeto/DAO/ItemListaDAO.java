package br.dsp.projeto.DAO;

import br.dsp.projeto.entity.ItemLista;
import br.dsp.projeto.entity.enums.BooleanEnum;

import java.util.List;
import java.util.Optional;

public interface ItemListaDAO {
  ItemLista save(ItemLista entity);

  void deleteById(String id);

  Optional<ItemLista> findById(String id);

  List<ItemLista> findAll();

  List<ItemLista> findByNomeAndListaId(String nome, String listaId);

  ItemLista findByIdAndListaDeComprasId(String itemId, String listaId);

  List<ItemLista> findByListaDeComprasId(String listaId);

  long countByListaId(String listaId);

  List<ItemLista> findAllByListaDeComprasIdOrderByQuantidadeDesc(String listaId);

  List<ItemLista> findAllByListaIdAndPrecoBetween(String listaId, float minPreco, float maxPreco);

  List<ItemLista> findByListaIdAndDescricaoContainingIgnoreCase(String listaId, String descricao);

  List<ItemLista> findAllObtidosByListaId(String listaId);

  List<ItemLista> findAllNaoObtidosByListaId(String listaId);
}
