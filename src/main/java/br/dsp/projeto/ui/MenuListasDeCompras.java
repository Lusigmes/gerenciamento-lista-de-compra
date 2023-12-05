package br.dsp.projeto.ui;

import br.dsp.projeto.entity.ItemLista;
import br.dsp.projeto.entity.ListaDeCompras;
import br.dsp.projeto.entity.Usuario;
import br.dsp.projeto.entity.enums.Tipo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import br.dsp.projeto.DAO.UsuarioDAO;
import br.dsp.projeto.DAO.ListaDeComprasDAO;

@Component
@Slf4j
public class MenuListasDeCompras {

    @Autowired
    private ListaDeComprasDAO baseListasDeCompras;

    @Autowired
    private UsuarioDAO baseUsuarios;

    @Autowired
    private MenuItensLista menuItensLista;

    public void obterListaDeCompras(ListaDeCompras lista) {
        String nome = JOptionPane.showInputDialog("Nome da Lista de Compras", null);
        LocalDateTime dataCriacao = LocalDateTime.now(); // A data de criação será sempre a data atual
        Tipo tipo = (Tipo) JOptionPane.showInputDialog(null, "Selecione o Tipo", "Tipo",
                JOptionPane.QUESTION_MESSAGE, null, Tipo.values(), null);

        List<Usuario> usuariosDisponiveis = baseUsuarios.findAll();
        Usuario usuarioEscolhido = (Usuario) JOptionPane.showInputDialog(null, "Selecione o Usuário", "Usuário",
                JOptionPane.QUESTION_MESSAGE, null, usuariosDisponiveis.toArray(), null);

        lista.setNome(nome);
        lista.setDataCriacao(dataCriacao);
        lista.setTipo(tipo);
        lista.setUsuario(usuarioEscolhido);

        baseListasDeCompras.save(lista);
        menuItensLista.menu(lista);
    }

    public void atualizarListaDeCompras(ListaDeCompras lista) {
        String nome = JOptionPane.showInputDialog("Nome da Lista de Compras", lista.getNome());
        Tipo tipo = (Tipo) JOptionPane.showInputDialog(null, "Selecione o Tipo", "Tipo",
                JOptionPane.QUESTION_MESSAGE, null, Tipo.values(), lista.getTipo());

        List<Usuario> usuariosDisponiveis = baseUsuarios.findAll(); // Substitua por seu método real
        Usuario usuarioEscolhido = (Usuario) JOptionPane.showInputDialog(null, "Selecione o Usuário", "Usuário",
                JOptionPane.QUESTION_MESSAGE, null, usuariosDisponiveis.toArray(), lista.getUsuario());

        lista.setNome(nome);
        lista.setTipo(tipo);
        lista.setUsuario(usuarioEscolhido);
        baseListasDeCompras.save(lista);
        menuItensLista.menu(lista);
    }

    public void listaListasDeCompras(List<ListaDeCompras> listas) {
        StringBuilder listagem = new StringBuilder();
        try {
            for (ListaDeCompras lista : listas) {
                listagem.append(lista.toStringReduzido()).append("\n");
            }
        } catch (LazyInitializationException e) {
            log.debug(e.getMessage());
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhuma lista de compras encontrada" : listagem);
    }

    public void listaListaDeCompras(ListaDeCompras lista) {
        JOptionPane.showMessageDialog(null,
                lista == null ? "Nenhuma lista de compras encontrada" : lista.toStringReduzido());
    }

    public void menu() {
        int opcao = 0;
        do {
            try {
                StringBuilder menu = new StringBuilder("Menu Listas de Compras\n")
                        .append("\nQuantidade de Listas de Compras: ").append(baseListasDeCompras.count()).append("\n")
                        .append("\n1 - Inserir\n")
                        .append("2 - Atualizar por Id\n")
                        .append("3 - Remover por Id\n")
                        .append("4 - Exibir por Id\n")
                        .append("5 - Exibir todos\n")
                        .append("6 - Exibir todos que contêm determinado nome\n")
                        .append("7 - Exibir listas de compras criadas após uma determinada data\n")
                        .append("8 - Exibir listas de compras por tipo\n")
                        .append("9 - Exibir listas de compras ordenadas por data de criação em ordem descendente\n")
                        .append("10 - Exibir itens de uma lista por id\n")
                        .append("11 - Exibir usuario de uma lista por id\n")
                        .append("12 - Menu anterior");

                opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcao) {
                    case 1: // Inserir
                        ListaDeCompras novaLista = new ListaDeCompras();
                        obterListaDeCompras(novaLista);
                        break;
                    case 2: // Atualizar por Id
                        String idListaAtualizar = JOptionPane
                                .showInputDialog("Digite o id da lista a ser atualizada");
                        ListaDeCompras listaParaAtualizar = baseListasDeCompras
                                .findListaDeComprasById(idListaAtualizar);
                        if (listaParaAtualizar != null) {
                            atualizarListaDeCompras(listaParaAtualizar);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não foi possível atualizar, pois a lista de compras não foi encontrada.");
                        }
                        break;
                    case 3: // Remover por Id
                        String idListaRemover = JOptionPane.showInputDialog("Digite o ID da lista a ser removida");
                        ListaDeCompras listaParaRemover = baseListasDeCompras
                                .findListaDeComprasById(idListaRemover);
                        if (listaParaRemover != null) {
                            baseListasDeCompras.deleteById(listaParaRemover.getId());
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não foi possível remover, pois a lista de compras não foi encontrada.");
                        }
                        break;

                    case 4: // Exibir por Id
                        String idExibir = JOptionPane.showInputDialog("Id da lista de compras a ser exibida");
                        ListaDeCompras listaPorId = baseListasDeCompras.findListaDeComprasById(idExibir);
                        listaListaDeCompras(listaPorId);
                        break;
                    case 5: // Exibir todos
                        listaListasDeCompras(baseListasDeCompras.findAll());
                        break;
                    case 6: // Exibir todos que contêm determinado nome
                        String nomeContendo = JOptionPane.showInputDialog("Nome da lista a ser pesquisada");
                        listaListasDeCompras(baseListasDeCompras.findByNomeContainingIgnoreCase(nomeContendo));
                        break;
                    case 7: // Exibir listas de compras criadas após uma determinada data
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                        LocalDateTime dataMin = LocalDateTime
                                .parse(JOptionPane.showInputDialog("Data de criação mínima (dd-MM-aaaa)") + " 00:00:00",
                                        formatter);

                        listaListasDeCompras(baseListasDeCompras.findByDataCriacaoAfterOrderByDataCriacaoDesc(dataMin));
                        break;
                    case 8: // Exibir listas de compras por tipo
                        Tipo tipo = (Tipo) JOptionPane.showInputDialog(null, "Selecione o Tipo", "Tipo",
                                JOptionPane.QUESTION_MESSAGE, null, Tipo.values(), null);
                        listaListasDeCompras(baseListasDeCompras.findByTipo(tipo));
                        break;
                    case 9: // Exibir listas de compras ordenadas por data de criação em ordem descendente
                        listaListasDeCompras(baseListasDeCompras.findByOrderByDataCriacaoDesc());
                        break;
                    case 10: // Exibir itens de uma lista
                        String listaIdItens = JOptionPane.showInputDialog("Id da lista para obter itens");
                        listaItensDeUmaLista(listaIdItens);
                        break;
                    case 11:
                        String listaId = JOptionPane.showInputDialog("Id da lista para obter usuário");

                        ListaDeCompras lista = baseListasDeCompras.findListaDeComprasWithUsuarioById(listaId);

                        if (lista != null && lista.getUsuario() != null) {
                            Usuario usuario = lista.getUsuario();
                            JOptionPane.showMessageDialog(null, usuario);
                        } else {
                            JOptionPane.showMessageDialog(null, "Id não encontrado");
                        }
                        break;

                    case 12: // Menu anterior
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }

        } while (opcao != 12);
    }

    private void listaItensDeUmaLista(String listaId) {
        List<ItemLista> itens = baseListasDeCompras.findByIdItens(listaId);
        ListaDeCompras lista = baseListasDeCompras.findListaDeComprasById(listaId);
        StringBuilder itensListagem = new StringBuilder(
                "Itens da Lista de Compras ( " + lista.toStringReduzido() + ")\n");
        for (ItemLista item : itens) {
            itensListagem.append(item).append("\n");
        }
        JOptionPane.showMessageDialog(null,
                itensListagem.length() == 0 ? "Nenhum item encontrado para esta lista" : itensListagem);
    }
}
