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

	@Autowired
	MenuListaElemento menuListaElemento;


    public void obterCompra(Compra compra) {
		List<Pessoa> clientes = basePessoas.findAll();
		Pessoa cliente = (Pessoa)JOptionPane.showInputDialog(null, "Selecione um cliente", 
		"Clientes", JOptionPane.PLAIN_MESSAGE, null, clientes.toArray(), compra.getCliente());
		compra.setCliente(cliente);
		if (compra.getDataCompra() == null)
			compra.setDataCompra(LocalDateTime.now());
		baseCompras.save(compra);
		menuListaElemento.menu(compra);
	}

	public void listaCompras(List<Compra> compras) {
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
		StringBuilder menu = new StringBuilder("Menu de Compras\n")
			.append("1 - Inserir\n")
			.append("2 - Atualizar por id\n")
			.append("3 - Remover por id\n")
			.append("4 - Exibir por id\n")
			.append("5 - Exibir todos\n")
			.append("6 - Exibir compras com valor total maior ou igual a um determinado valor\n")
			.append("7 - Menu anterior");
		int opcao = 0;
		do {
			try {
				Compra compra;
				int id;
				opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
				switch (opcao) {
					case 1:     // Inserir
						compra = new Compra();
						obterCompra(compra);
						break;
					case 2:     // Atualizar por id
						id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id da compra a ser alterada"));
						compra = baseCompras.findById(id).orElse(null);
						if (compra != null) {
							obterCompra(compra);
						} else {
							JOptionPane.showMessageDialog(null, "Não foi encontrada compra com o id " + id);
						}
						break;
					case 3:     // Remover por id
						id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id da compra a ser removida"));
						compra = baseCompras.findById(id).orElse(null);
						if (compra != null) {
							baseCompras.deleteById(compra.getId());
						} else {
							JOptionPane.showMessageDialog(null, "Não foi encontrada compra com o id " + id);
						}
						break;
					case 4:     // Exibir por id
						id = Integer.parseInt(JOptionPane.showInputDialog("Digite o id da compra a ser exibida"));
						compra = baseCompras.findCompras(id);
						
						if (compra != null) {
							listaCompra(compra);
						} else {
							JOptionPane.showMessageDialog(null, "Não foi encontrada compra com o id " + id);
						}
						break;
					case 5:     // Exibir todos
						listaCompras(baseCompras.findAllSorted());
						break;
					case 6:     // Exibir todos
						// float valor = Float.valueOf(JOptionPane.showInputDialog("Digite o valor"));
						// listaCompras(baseCompras.findComprasComValorTotalMaiorOuIgualA(valor));
						break;
					case 7:     // Menu anterior
						break;
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}

		} while(opcao != 7);
	
	}
}
