package br.dsp.projeto.ui;

import br.dsp.projeto.entity.Produto;
import br.dsp.projeto.entity.enums.Categoria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;
import br.dsp.projeto.DAO.ProdutoDAO;

@Component
@Slf4j
public class MenuProdutos {

    @Autowired
    private ProdutoDAO baseProdutos;

    public void obterProduto(Produto prod) {
        String nome = JOptionPane.showInputDialog("Nome", prod.getNome());
        String codigo = JOptionPane.showInputDialog("Código", prod.getCodigo());
        Categoria categoria = (Categoria) JOptionPane.showInputDialog(null, "Selecione a Categoria", "Categoria",
                JOptionPane.QUESTION_MESSAGE, null, Categoria.values(), prod.getCategoria());

        prod.setNome(nome);
        prod.setCodigo(codigo);
        prod.setCategoria(categoria);
    }

    public void listaProdutos(List<Produto> produtos) {
        StringBuilder listagem = new StringBuilder();
        for (Produto prod : produtos) {
            listagem.append(prod).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum produto encontrado" : listagem);
    }

    public void listaProduto(Produto prod) {
        JOptionPane.showMessageDialog(null, prod == null ? "Nenhum produto encontrado" : prod);
    }

    public void menu() {
        int opcao = 0;
        do {
            try {
                Produto prod;
                String codigo;
                StringBuilder menu = new StringBuilder("Menu Produtos\n")
                        .append("\nQuantidade de Produtos: ").append(baseProdutos.count()).append("\n")
                        .append("\n1 - Inserir\n")
                        .append("2 - Atualizar por código\n")
                        .append("3 - Remover por código\n")
                        .append("4 - Exibir por id\n")
                        .append("5 - Exibir por código\n")
                        .append("6 - Exibir todos\n")
                        .append("7 - Exibir todos por nome\n")
                        .append("8 - Exibir todos por categoria\n")
                        .append("9 - Exibir todos ordenados por nome\n")
                        .append("10 - Menu anterior");

                opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcao) {
                    case 1: // Inserir
                        prod = new Produto();
                        obterProduto(prod);
                        baseProdutos.save(prod);
                        break;

                    case 2: // Atualizar por código
                        codigo = JOptionPane.showInputDialog("Digite o código do produto a ser atualizado");
                        prod = baseProdutos.findPorCodigo(codigo);
                        if (prod != null) {
                            obterProduto(prod);
                            baseProdutos.save(prod);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não foi possível atualizar, pois o produto não foi encontrado.");
                        }
                        break;

                    case 3: // Remover por código
                        codigo = JOptionPane.showInputDialog("Digite o código do produto a ser removido");
                        prod = baseProdutos.findPorCodigo(codigo);
                        if (prod != null) {
                            baseProdutos.deleteById(prod.getId());
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não foi possível remover, pois o produto não foi encontrado.");
                        }
                        break;

                    case 4: // Exibir por id
                        String idExibir = JOptionPane.showInputDialog("Id do produto a ser exibido");
                        prod = baseProdutos.findById(idExibir).orElse(null);
                        listaProduto(prod);
                        break;

                    case 5: // Exibir por código
                        codigo = JOptionPane.showInputDialog("Digite o código do produto a ser exibido");
                        prod = baseProdutos.findPorCodigo(codigo);
                        listaProduto(prod);
                        break;

                    case 6: // Exibir todos
                        listaProdutos(baseProdutos.findAll());
                        break;

                    case 7: // Exibir todos que contêm determinado nome
                        String nomeContendo = JOptionPane.showInputDialog("Nome a ser pesquisado");
                        listaProdutos(baseProdutos.findAllByNomeContainingIgnoreCase(nomeContendo));
                        break;

                    case 8: // Exibir todos por categoria
                        Categoria categoria = (Categoria) JOptionPane.showInputDialog(null, "Selecione a Categoria",
                                "Categoria",
                                JOptionPane.QUESTION_MESSAGE, null, Categoria.values(), null);
                        if (categoria != null) {
                            List<Produto> produtosPorCategoria = baseProdutos.findAllByCategoria(categoria);
                            long quantidadeProdutos = baseProdutos.countByCategoria(categoria);
                            listaProdutos(produtosPorCategoria);
                            JOptionPane.showMessageDialog(null,
                                    "A categoria " + categoria.name() + " possui " + quantidadeProdutos + " produtos.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Categoria inválida");
                        }
                        break;

                    case 9: // Exibir todos ordenados por nome
                        Sort order = Sort.by(Sort.Order.asc("nome"));
                        listaProdutos(baseProdutos.findAllOrderByNomeAsc(order));
                        break;

                    case 10: // Menu anterior
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        } while (opcao != 10);
    }
}
