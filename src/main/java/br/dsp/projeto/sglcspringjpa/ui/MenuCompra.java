package br.dsp.projeto.sglcspringjpa.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import br.dsp.projeto.sglcspringjpa.entiity.Pessoa;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MenuCompra {
    	public void obterCliente(Pessoa cliente) {
		String nome = JOptionPane.showInputDialog("Nome", cliente.getNome());
		String cpf = JOptionPane.showInputDialog("CPF", cliente.getCpf());
		String email = JOptionPane.showInputDialog("E-mail", cliente.getEmail());
		String dataInput = JOptionPane.showInputDialog("Data de Nascimento(dd/MM/yyyy)", cliente.getDataNascimento());
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date dataNascimento = format.parse(dataInput);
	
			cliente.setNome(nome);
			cliente.setCpf(cpf);
			cliente.setEmail(email);
			cliente.setDataNascimento(dataNascimento);
		} catch (ParseException e) {
			log.error("Errro: {}", e.getMessage(), e);
			JOptionPane.showMessageDialog(null, "Formato de data inválido. Use dd/MM/yyyy");
		}
	}

	public void listaClientes(List<Pessoa> clientes) {
		StringBuilder listagem = new StringBuilder();
		for(Pessoa cliente : clientes) {
			listagem.append(cliente.toString()).append("\n");
		}
		JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum cliente encontrado" : listagem);
	}

	public void listaCliente(Pessoa cl) {
		JOptionPane.showMessageDialog(null, cl == null ? "Nenhum cliente encontrado" : cl.toString());
	}

	public void menu() {
		StringBuilder menu = new StringBuilder("Menu Clientes\n")
			.append("1 - Inserir\n")
			.append("2 - Atualizar por CPF\n")
			.append("3 - Remover por CPF\n")
			.append("4 - Exibir por CPF\n")
			.append("5 - Exibir por id\n")
			.append("6 - Exibir todos\n")
			.append("7 - Exibir todos que contém determinado nome\n")
			.append("8 - \n")
			.append("9 - n")
			.append("10 - \n")
			.append("11 - Menu anterior");
		int opcao = 0;
		do {
			try {
				Pessoa cliente;
				String cpf;
				opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
				switch (opcao) {
			/* 		case 1:     // Inserir
						cliente = new Pessoa();
						obterCliente(cliente);
						baseClientes.save(cliente);
						break;
					case 2:     // Atualizar por CPF
						cpf = JOptionPane.showInputDialog("Digite o CPF do cliente a ser alterado");
						cliente = baseClientes.findPessoaByCpf(cpf);
						if (cliente != null) {
							obterCliente(cliente);
							baseClientes.save(cliente);
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível atualizar, pois o cliente não foi encontrado.");
						}
						break;
					case 3:     // Remover por CPF
						cpf = JOptionPane.showInputDialog("CPF");
						cliente = baseClientes.findPessoaByCpf(cpf);
						if (cliente != null) {
							baseClientes.deleteById(cliente.getId());
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o cliente não foi encontrado.");
						}
						break;
					case 4:     // Exibir por CPF
						cpf = JOptionPane.showInputDialog("CPF");
						
						cliente = baseClientes.findPessoaPorCpfNomeado(cpf);
						listaCliente(cliente);
						break;
					case 5:     // Exibir por id
						int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
						cliente = baseClientes.findById(id).orElse(null);
						listaCliente(cliente);
						break;
					case 6:     // Exibir todos ordenadao por id
						listaClientes(baseClientes.findAllOrdenado());
						break;
					case 7:     // Exibir todos que contem um caractere
						String nome = JOptionPane.showInputDialog("Nome");
						listaClientes(baseClientes.findPessoaPorNomeEspecifico(nome));
						break;// 
					case 8:     // Sair
						break;
					
					case 9:     // Sair
						break;
					
					case 10:     // Sair
						break;
					
					case 11:     // Sair
						break;
					
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break; */
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}

		} while(opcao != 11);
	}
}
