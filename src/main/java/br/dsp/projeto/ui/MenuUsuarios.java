package br.dsp.projeto.ui;

import br.dsp.projeto.DAO.UsuarioDAO;
import br.dsp.projeto.entity.Usuario;
import br.dsp.projeto.entity.enums.Sexo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.data.domain.Sort;

@Component
@Slf4j
public class MenuUsuarios {

	@Autowired
	private UsuarioDAO baseUsuarios;

	public void obterUsuario(Usuario usuario) {
		String nomeCompleto = JOptionPane.showInputDialog("Nome Completo", usuario.getNomeCompleto());
		String nomeUsuario = JOptionPane.showInputDialog("Nome de Usuário", usuario.getNomeUsuario());
		String email = JOptionPane.showInputDialog("Email", usuario.getEmail());
		Sexo sexo = (Sexo) JOptionPane.showInputDialog(null, "Selecione o Sexo", "Sexo",
				JOptionPane.QUESTION_MESSAGE, null, Sexo.values(), usuario.getSexo());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dataNascimento = (usuario.getDataNascimento() != null)
				? LocalDate.parse(JOptionPane.showInputDialog("Data de Nascimento (dd/MM/aaaa)",
						usuario.getDataNascimento().format(formatter)), formatter)
				: LocalDate.parse(JOptionPane.showInputDialog("Data de Nascimento (dd/MM/aaaa)"), formatter);

		usuario.setNomeCompleto(nomeCompleto);
		usuario.setNomeUsuario(nomeUsuario);
		usuario.setEmail(email);
		usuario.setSexo(sexo);
		usuario.setDataNascimento(dataNascimento);
	}

	public void listaUsuarios(List<Usuario> usuarios) {
		StringBuilder listagem = new StringBuilder();
		for (Usuario usuario : usuarios) {
			listagem.append(usuario.toStringCompleto()).append("\n");
		}
		JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum usuário encontrado" : listagem);
	}

	public void listaUsuario(Usuario usuario) {
		JOptionPane.showMessageDialog(null, usuario == null ? "Nenhum usuário encontrado" : usuario.toStringCompleto());
	}

	public void menu() {
		int opcao = 0;
		do {
			try {
				// Consulta SQL para contar o número total de usuários
				long quantidadeUsuarios = baseUsuarios.count();

				// Construir o menu com a quantidade de usuários
				StringBuilder menu = new StringBuilder("Menu Usuários\n")
						.append("\nQuantidade de Usuários: ").append(quantidadeUsuarios).append("\n")
						.append("\n1 - Inserir\n")
						.append("2 - Atualizar por Nome de Usuário\n")
						.append("3 - Remover por Nome de Usuário\n")
						.append("4 - Exibir por Nome de Usuário\n")
						.append("5 - Exibir por Id\n")
						.append("6 - Exibir todos\n")
						.append("7 - Exibir todos por nome\n")
						.append("8 - Exibir usuários por datas de nascimento\n")
						.append("9 - Exibir usuários por dominio de email\n")
						.append("10 - Exibir todos ordenados pelo nome em ordem crescente\n")
						.append("11 - Exibir usuários de um determinado sexo\n")
						.append("12 - Menu anterior\n");

				opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

				switch (opcao) {
					case 1: // Inserir
						Usuario novoUsuario = new Usuario();
						obterUsuario(novoUsuario);
						baseUsuarios.save(novoUsuario);
						break;
					case 2: // Atualizar por Nome de Usuário
						String nomeUsuario = JOptionPane
								.showInputDialog("Digite o nome do usuário a ser atualizado");
						Usuario usuarioParaAtualizar = baseUsuarios.findByNomeUsuario(nomeUsuario);
						if (usuarioParaAtualizar != null) {
							obterUsuario(usuarioParaAtualizar);
							baseUsuarios.save(usuarioParaAtualizar);
						} else {
							JOptionPane.showMessageDialog(null,
									"Não foi possível atualizar, pois o usuário não foi encontrado.");
						}
						break;
					case 3: // Remover por Nome de Usuário
						String nomeUsuarioRemover = JOptionPane
								.showInputDialog("Digite o nome do usuário a ser removido");
						Usuario usuarioParaRemover = baseUsuarios.findByNomeUsuario(nomeUsuarioRemover);
						if (usuarioParaRemover != null) {
							baseUsuarios.deleteById(usuarioParaRemover.getId());
						} else {
							JOptionPane.showMessageDialog(null,
									"Não foi possível remover, pois o usuário não foi encontrado.");
						}
						break;
					case 4: // Exibir por Nome de Usuário
						String nomeUsuarioExibir = JOptionPane
								.showInputDialog("Digite o Nome de Usuário do usuário a ser exibido");
						Usuario usuarioExibir = baseUsuarios.findByNomeUsuario(nomeUsuarioExibir);
						listaUsuario(usuarioExibir);
						break;
					case 5: // Exibir por Id
						String idExibir = JOptionPane.showInputDialog("Id do usuário a ser exibido");
						Usuario usuarioPorId = baseUsuarios.findById(idExibir).orElse(null);
						listaUsuario(usuarioPorId);
						break;
					case 6: // Exibir todos
						listaUsuarios(baseUsuarios.findAll());
						break;
					case 7: // Exibir todos que contêm determinado nome
						String nomeContendo = JOptionPane.showInputDialog("Nome a ser pesquisado");
						listaUsuarios(baseUsuarios.findAllByNomeCompletoContainingIgnoreCase(nomeContendo));
						break;
					case 8: // Exibir usuários pela data de nascimento entre datas
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						LocalDate dataMin = LocalDate
								.parse(JOptionPane.showInputDialog("Data de Nascimento Inicial (dd/MM/aaaa)"),
										formatter);
						LocalDate dataMax = LocalDate
								.parse(JOptionPane.showInputDialog("Data de Nascimento Final (dd/MM/aaaa)"), formatter);
						listaUsuarios(baseUsuarios.findByDataNascimentoBetween(dataMin, dataMax));
						break;
					case 9: // Exibir todos por determinado dominio de email
						String dominio = JOptionPane.showInputDialog("Dominio do e-mail a ser pesquisado");
						listaUsuarios(baseUsuarios.findByEmailIgnoreCase(dominio));
						break;
					case 10: // Exibir todos ordenados pelo nome de usuário em ordem ascendente
						Sort order = Sort.by(Sort.Order.asc("nomeCompleto"));
						listaUsuarios(baseUsuarios.findAllIgnoreCase(order));
						break;
					case 11: // Exibir usuários de um determinado sexo
						Sexo sexo = (Sexo) JOptionPane.showInputDialog(null, "Selecione o Sexo", "Sexo",
								JOptionPane.QUESTION_MESSAGE, null, Sexo.values(), null);
						listaUsuarios(baseUsuarios.findBySexo(sexo));
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
}
