package br.dsp.projeto.sglcspringjpa.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

import br.dsp.projeto.sglcspringjpa.dao.CompraDAO;
import br.dsp.projeto.sglcspringjpa.dao.PessoaDAO;
import br.dsp.projeto.sglcspringjpa.entiity.Compra;
import br.dsp.projeto.sglcspringjpa.entiity.Pessoa;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MenuCompra {

	@Autowired
	CompraDAO baseCompras;
	
	@Autowired 
	PessoaDAO basePessoas;


    public void obterCompra(Compra compra) {
		List<Pessoa> clientes = basePessoas.findAll();
		Pessoa cliente = (Pessoa)JOptionPane.showInputDialog(null, "Selecione um cliente", 
		"Clientes", JOptionPane.PLAIN_MESSAGE, null, clientes.toArray(), compra.getCliente());
		compra.setCliente(cliente);
		if (compra.getDataCompra() == null)
		compra.setDataCompra(LocalDateTime.now());
		baseCompras.save(compra);
	//	menuItensCompras.menu(compra);
	}

	public void lisaCompras(List<Compra> compras) {
		StringBuilder listagem = new StringBuilder();
		for(Compra compr : compras) {
			listagem.append(compr);
			try {
				float valorTotal = compr.getValorTotal();
				listagem.append(" Valor total: ");
				listagem.append(valorTotal);
			} catch (LazyInitializationException e) {
				log.debug(e.getMessage());
			}
			listagem.append("\n");
		}
		JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhuma compra encontrada" : listagem);
	}

	public void listaCompra(Compra compra) {
		JOptionPane.showMessageDialog(null, compra == null ? "Nenhuma compra encontrado" : compra.toString());
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
				Compra compra;
				opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
				switch (opcao) {
			/* 		case 1:     // Inserir
						cliente = new Pessoa();
						obterCliente(cliente);
						baseCompras.save(cliente);
						break;
					case 2:     // Atualizar por CPF
						cpf = JOptionPane.showInputDialog("Digite o CPF do cliente a ser alterado");
						cliente = baseCompras.findPessoaByCpf(cpf);
						if (cliente != null) {
							obterCliente(cliente);
							baseCompras.save(cliente);
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível atualizar, pois o cliente não foi encontrado.");
						}
						break;
					case 3:     // Remover por CPF
						cpf = JOptionPane.showInputDialog("CPF");
						cliente = baseCompras.findPessoaByCpf(cpf);
						if (cliente != null) {
							baseCompras.deleteById(cliente.getId());
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o cliente não foi encontrado.");
						}
						break;
					case 4:     // Exibir por CPF
						cpf = JOptionPane.showInputDialog("CPF");
						
						cliente = baseCompras.findPessoaPorCpfNomeado(cpf);
						listaCliente(cliente);
						break;
					case 5:     // Exibir por id
						int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
						cliente = baseCompras.findById(id).orElse(null);
						listaCliente(cliente);
						break;
					case 6:     // Exibir todos ordenadao por id
						listaClientes(baseCompras.findAllOrdenado());
						break;
					case 7:     // Exibir todos que contem um caractere
						String nome = JOptionPane.showInputDialog("Nome");
						listaClientes(baseCompras.findPessoaPorNomeEspecifico(nome));
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
