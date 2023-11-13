package br.dsp.projeto.sglcspringjpa.ui;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dsp.projeto.sglcspringjpa.dao.ElementoDAO;
import br.dsp.projeto.sglcspringjpa.dao.ListaElementoDAO;
import br.dsp.projeto.sglcspringjpa.entiity.Compra;
import br.dsp.projeto.sglcspringjpa.entiity.Elemento;
import br.dsp.projeto.sglcspringjpa.entiity.ListaElemento;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MenuListaElemento {

	@Autowired
	ListaElementoDAO baseListaElemento;

	@Autowired
	ElementoDAO baseElemento;

	private Compra compra;
//id elemento compra qnt valorElemento

	public void obterListaElemento(ListaElemento elementos) {
		List<Elemento> produtos = baseElemento.findAllOrdenado();
		Elemento elemento = (Elemento)JOptionPane.showInputDialog(null, "Selecione um produto", 
			"Produtos", JOptionPane.PLAIN_MESSAGE, null, produtos.toArray(), elementos.getElemento());
		//String nome = JOptionPane.showInputDialog("Nome", compr.getNome());
		elementos.setElemento(elemento);
		
		float qtd = Float.valueOf(JOptionPane.showInputDialog("Quantidade", elementos.getQuantidade()));
		elementos.setQuantidade(qtd);
		
		float valorUnitario = elementos.getValorElemento();
		if (valorUnitario == 0) // se o item não tem valor unitário, usar o valor atual do produto
			valorUnitario = elemento.getValor();
		valorUnitario = Float.valueOf(JOptionPane.showInputDialog("Valor do produto", valorUnitario));
		elementos.setValorElemento(valorUnitario);
	}


	public static void listaItemCompra(ListaElemento listaElemento) {
		JOptionPane.showMessageDialog(null, listaElemento == null ? "Nenhuma compra encontrada" : listaElemento);
	}

	public StringBuilder getStringListaElemento() {
		List<ListaElemento> itens = baseListaElemento.findByCompraId(this.compra.getId());
		StringBuilder str = new StringBuilder();
		for(ListaElemento item : itens) {
			str.append(item).append("\n");
		}
		return str.length() == 0 ? new StringBuilder("Nenhum item de compra na compra (id=").append(compra.getId()).append(")\n") : str;
	}

	public void menu(Compra compra) {
		this.compra = compra;
		int opcao = 0;
		do {
			try {
				StringBuilder menu = new StringBuilder("Menu itens de compra (id=").append(compra.getId()).append(")\n")
					.append(getStringListaElemento())
					.append("1 - Inserir\n")
					.append("2 - Atualizar por id\n")
					.append("3 - Remover por id\n")
					.append("4 - Exibir por id\n")
					.append("5 - Exibir todos\n")
					.append("6 - Menu anterior");
				ListaElemento listaElemento;
				Integer id;
				opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
				switch (opcao) {
					case 1:     // Inserir
						listaElemento = new ListaElemento();
						obterListaElemento(listaElemento);
						listaElemento.setCompra(compra);
						baseListaElemento.save(listaElemento);
						break;
					case 2:     // Atualizar por id
						id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id do item de compra a ser alterado"));
						listaElemento = baseListaElemento.findById(id).orElse(null);
						if (listaElemento != null) {
							if (listaElemento.getCompra().getId() != compra.getId()) {
								JOptionPane.showMessageDialog(null, "O item de compra " + listaElemento.getId() + " não pertence à compra " + compra.getId());
							} else {
								obterListaElemento(listaElemento);
								baseListaElemento.save(listaElemento);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Não foi encontrado item de compra com o id " + id);
						}
						break;
					case 3:     // Remover por id
						id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id da compra a ser removida"));
						listaElemento = baseListaElemento.findById(id).orElse(null);
						if (listaElemento != null) {
							baseListaElemento.deleteById(listaElemento.getId());
						} else {
							JOptionPane.showMessageDialog(null, "Não foi encontrado item de compra com o id " + id);
						}
						break;
					case 4:     // Exibir por id
						id = Integer.parseInt(JOptionPane.showInputDialog("Digite o id da compra a ser exibida"));
						listaElemento = baseListaElemento.findById(id).orElse(null);
						if (listaElemento != null) {
							listaItemCompra(listaElemento);
						} else {
							JOptionPane.showMessageDialog(null, "Não foi encontrado item de compra com o id " + id);
						}
						break;
					case 5:     // Exibir todos
							JOptionPane.showMessageDialog(null, getStringListaElemento());
						break;
					case 6:     // Sair
						break;
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}

		} while(opcao !=  6);
	}
}