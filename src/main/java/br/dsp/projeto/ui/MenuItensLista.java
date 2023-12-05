package br.dsp.projeto.ui;

import br.dsp.projeto.entity.ItemLista;
import br.dsp.projeto.entity.ListaDeCompras;
import br.dsp.projeto.entity.Produto;
import br.dsp.projeto.entity.enums.BooleanEnum;
import br.dsp.projeto.DAO.ItemListaDAO;
import br.dsp.projeto.DAO.ListaDeComprasDAO;
import br.dsp.projeto.DAO.ProdutoDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;

@Component
@Slf4j
public class MenuItensLista {

    @Autowired
    private ItemListaDAO baseItensLista;

    @Autowired
    private ProdutoDAO baseProdutos;

    @Autowired
    private ListaDeComprasDAO baseListasDeCompras;

    public void obterItemLista(ItemLista itemLista, ListaDeCompras lista) {
        List<Produto> produtos = baseProdutos.findAll();
        Produto produtoSelecionado = (Produto) JOptionPane.showInputDialog(null,
                "Selecione o Produto", "Produtos Disponíveis",
                JOptionPane.QUESTION_MESSAGE, null, produtos.toArray(), itemLista.getProduto());

        if (produtoSelecionado != null) {
            itemLista.setProduto(produtoSelecionado);
            itemLista.setListaDeCompras(lista);
            itemLista.setQuantidade(Integer.parseInt(JOptionPane.showInputDialog("Quantidade")));
            itemLista.setDescricao(JOptionPane.showInputDialog("Descrição"));
            itemLista.setPrecoUnitario(Float.parseFloat(JOptionPane.showInputDialog("Preço Unitário")));
            itemLista.setObtido(BooleanEnum.FALSE);
            lista.adicionarItemALista(itemLista);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado. O item de lista não será adicionado.");
        }
    }

    public void editarItemLista(ItemLista itemLista, ListaDeCompras lista) {
        List<Produto> produtos = baseProdutos.findAll();
        Produto produtoSelecionado = (Produto) JOptionPane.showInputDialog(null,
                "Selecione o Produto", "Produtos Disponíveis",
                JOptionPane.QUESTION_MESSAGE, null, produtos.toArray(), itemLista.getProduto());

        if (produtoSelecionado != null) {
            itemLista.setProduto(produtoSelecionado);
            itemLista.setQuantidade(
                    Integer.parseInt(JOptionPane.showInputDialog("Quantidade", itemLista.getQuantidade())));
            itemLista.setDescricao(JOptionPane.showInputDialog("Descrição", itemLista.getDescricao()));
            itemLista.setPrecoUnitario(
                    Float.parseFloat(JOptionPane.showInputDialog("Preço Unitário", itemLista.getPrecoUnitario())));
            BooleanEnum obtido = (BooleanEnum) JOptionPane.showInputDialog(
                    null,
                    "O item foi obtido?",
                    "Obtido",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    BooleanEnum.values(),
                    itemLista.getObtido().getValue() ? BooleanEnum.TRUE : BooleanEnum.FALSE);

            itemLista.setObtido(obtido);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado. O item de lista não será adicionado.");
        }
        lista.atualizarItemNaLista(itemLista);
    }

    public void listaItensLista(List<ItemLista> itensLista) {
        StringBuilder listagem = new StringBuilder();
        for (ItemLista itemLista : itensLista) {
            listagem.append(itemLista).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum item de lista encontrado" : listagem);
    }

    public void listaItemLista(ItemLista itemLista) {
        JOptionPane.showMessageDialog(null, itemLista == null ? "Nenhum item de lista encontrado" : itemLista);
    }

    public void menu(ListaDeCompras lista) {
        int opcao = 0;
        do {
            try {
                StringBuilder menu = new StringBuilder("Menu Itens de Lista\n")
                        .append("\nLista: ").append(lista.toStringReduzido()).append("\n")
                        .append("\nQuantidade de Itens na Lista: ").append(baseItensLista.countByListaId(lista.getId()))
                        .append("\n")
                        .append("\n1 - Inserir item\n")
                        .append("2 - Atualizar item por id\n")
                        .append("3 - Remover item por id\n")
                        .append("4 - Exibir item por id\n")
                        .append("5 - Exibir todos os itens\n")
                        .append("6 - Exibir itens obtidos\n")
                        .append("7 - Exibir itens não obtidos\n")
                        .append("8 - Exibir itens por descrição\n")
                        .append("9 - Exibir itens em ordem de quantidade decrescente\n")
                        .append("10 - Exibir itens em uma faixa de preço\n")
                        .append("11 - Menu anterior");

                opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcao) {
                    case 1: // Inserir
                        ItemLista novoItemLista = new ItemLista();
                        obterItemLista(novoItemLista, lista);
                        baseItensLista.save(novoItemLista);
                        baseListasDeCompras.save(lista);
                        break;
                    case 2: // Atualizar por Id
                        String idAtualizar = JOptionPane.showInputDialog("Id do item de lista a ser atualizado");
                        ItemLista itemListaParaAtualizar = baseItensLista.findByIdAndListaDeComprasId(idAtualizar,
                                lista.getId());
                        if (itemListaParaAtualizar != null) {
                            editarItemLista(itemListaParaAtualizar, lista);
                            baseItensLista.save(itemListaParaAtualizar);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não foi possível atualizar, pois o item de lista não foi encontrado.");
                        }
                        break;
                    case 3: // Remover por Id
                        String idRemover = JOptionPane.showInputDialog("Digite o id do item da lista a ser removido");
                        ItemLista item = baseItensLista.findByIdAndListaDeComprasId(idRemover, lista.getId());
                        if (item != null) {
                            lista.removerItemDaLista(idRemover);
                            baseListasDeCompras.save(lista);
                            baseItensLista.deleteById(item.getId());
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não foi possível remover, pois o item de lista não foi encontrado.");
                        }
                        break;
                    case 4: // Exibir por Id
                        String idExibir = JOptionPane.showInputDialog("Id do item de lista a ser exibido");
                        ItemLista itemListaExibir = baseItensLista.findByIdAndListaDeComprasId(idExibir, lista.getId());
                        listaItemLista(itemListaExibir);
                        break;
                    case 5: // Exibir todos
                        listaItensLista(baseItensLista.findByListaDeComprasId(lista.getId()));
                        break;
                    case 6: // Exibir itens obtidos por Lista
                        listaItensLista(baseItensLista.findAllObtidosByListaId(lista.getId()));
                        break;
                    case 7: // Exibir itens não obtidos por Lista
                        listaItensLista(baseItensLista.findAllNaoObtidosByListaId(lista.getId()));
                        break;
                    case 8: // Exibir itens por descrição contendo
                        String descricaoContendo = JOptionPane.showInputDialog("Descrição a ser pesquisada");
                        listaItensLista(baseItensLista.findByListaIdAndDescricaoContainingIgnoreCase(lista.getId(),
                                descricaoContendo));
                        break;
                    case 9: // Exibir em ordem de quantidade
                        listaItensLista(baseItensLista.findAllByListaDeComprasIdOrderByQuantidadeDesc(lista.getId()));
                        break;
                    case 10: // Exibir em uma faixa de preço
                        float minPreco = Float.parseFloat(JOptionPane.showInputDialog("Preço Mínimo"));
                        float maxPreco = Float.parseFloat(JOptionPane.showInputDialog("Preço Máximo"));
                        listaItensLista(
                                baseItensLista.findAllByListaIdAndPrecoBetween(lista.getId(), minPreco, maxPreco));
                        break;
                    case 11: // Menu anterior
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }

        } while (opcao != 11);
    }
}
