package br.dsp.projeto.sglcspringjpa.ui;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dsp.projeto.sglcspringjpa.dao.ElementoDAO;
import br.dsp.projeto.sglcspringjpa.entiity.Elemento;
import br.dsp.projeto.sglcspringjpa.entiity.Pessoa;
import br.dsp.projeto.sglcspringjpa.entiity.enums.Sexo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MenuElemento {
    @Autowired
    private ElementoDAO baseItems;
    

    public void obterItem(Elemento Item) {
		String nome = JOptionPane.showInputDialog("Nome", Item.getNome());
	
	}

	public void listaItems(List<Elemento> items) {
		StringBuilder listagem = new StringBuilder();
		for(Elemento item : items) {
			listagem.append(item.toString()).append("\n");
		}
		JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum Item encontrado" : listagem);
	}

	public void listaItem(Elemento item) {
		JOptionPane.showMessageDialog(null, item == null ? "Nenhum Item encontrado" : item.toString());
	}

	public void menu() {
		StringBuilder menu = new StringBuilder("Menu Items\n")
			.append("1 - Inserir\n")
			.append("2 - Atualizar por CPF\n")
			.append("3 - Remover por CPF\n")
			.append("4 - Exibir por CPF\n")
			.append("5 - Exibir por id\n")
			.append("6 - Exibir todos\n")
			.append("7 - Exibir todos que contém determinado nome\n")
			.append("8 -Exibir todos que contem determinado nome em sua descrição\n")
			.append("9 - n")
			.append("10 - \n")
			.append("11 - Menu anterior");
		int opcao = 0;
		do {
			try {
				Elemento item;
				String codigo;
                ;
				opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
				switch (opcao) {
					case 1:     // Inserir
						item = new Elemento();
						obterItem(item);
						baseItems.save(item);
						break;
					case 2:     // Atualizar por CPF
						codigo = JOptionPane.showInputDialog("Digite o CPF do Item a ser alterado");
						//item = baseItems.findPessoaByCpf(codigo);
						// if (item != null) {
						// 	obterItem(item);
						// 	baseItems.save(item);
						// } else {
						// 	JOptionPane.showMessageDialog(null, "Não foi possível atualizar, pois o Item não foi encontrado.");
						// }
						break;
					case 3:     // Remover por CPF
						codigo = JOptionPane.showInputDialog("CPF");
						//item = baseItems.findPessoaByCpf(codigo);
						// if (item != null) {
						// 	baseItems.deleteById(item.getId());
						// } else {
						// 	JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o Item não foi encontrado.");
						// }
						break;
					case 4:     // Exibir por CPF
						codigo = JOptionPane.showInputDialog("CPF");
						
						//item = baseItems.findPessoaPorCpfNomeado(codigo);
						//listaItem(item);
						break;
					case 5:     // Exibir por id
						int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
						item = baseItems.findById(id).orElse(null);
						listaItem(item);
						break;
					case 6:     // Exibir todos
						listaItems(baseItems.findAll());
						break;
					case '7':     // Exibir todos que contem um caractere
						String nome = JOptionPane.showInputDialog("Nome");
						listaItems(baseItems.findElementosByNome(nome));
						break;// */
					case '8':     // Sair
						break;
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}

		} while(opcao != '8');
	}
}
