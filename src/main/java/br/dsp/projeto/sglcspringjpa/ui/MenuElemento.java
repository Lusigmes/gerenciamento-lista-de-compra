package br.dsp.projeto.sglcspringjpa.ui;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dsp.projeto.sglcspringjpa.dao.ElementoDAO;
import br.dsp.projeto.sglcspringjpa.entiity.Elemento;
import br.dsp.projeto.sglcspringjpa.entiity.Pessoa;
import br.dsp.projeto.sglcspringjpa.entiity.enums.Categoria;
import br.dsp.projeto.sglcspringjpa.entiity.enums.Sexo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MenuElemento {
    @Autowired
    private ElementoDAO baseItems;
    
	
    private Categoria obterCategoria(Elemento elemento) {
        Categoria categoria = Categoria.OUTROS; 

        String categoriaInput = JOptionPane.showInputDialog("CATEGORIAS .: \r\n" +
				"	 Roupas\r\n" + //
        		"    Eletronicos\r\n" + //
        		"    Livros\r\n" + //
        		"    Alimentos\r\n" + //
        		"    Jogos\r\n" + //
        		"    Brinquedos\r\n" + //
        		"    Beleza\r\n" + //
        		"    Moveis\r\n" + //
        		"    Esportes\r\n" +
        		"    Outros\r\n",
		elemento.getCategoria());

        if (categoriaInput != null && !categoriaInput.isEmpty()) {
            if (categoriaInput.equalsIgnoreCase("alimentos")) {
                categoria = Categoria.ALIMENTOS;
            }else if (categoriaInput.equalsIgnoreCase("beleza")) {
                categoria = Categoria.BELEZA;
            }else if (categoriaInput.equalsIgnoreCase("eletronicos")) {
                categoria = Categoria.ELETRONICOS;
            }else if (categoriaInput.equalsIgnoreCase("livros")) {
                categoria = Categoria.LIVROS;
            }else if (categoriaInput.equalsIgnoreCase("jogos")) {
                categoria = Categoria.JOGOS;
            }else if (categoriaInput.equalsIgnoreCase("brinquedos")) {
                categoria = Categoria.BRINQUEDOS;
            }else if (categoriaInput.equalsIgnoreCase("moveis")) {
                categoria = Categoria.MOVEIS;
            }else if (categoriaInput.equalsIgnoreCase("esportes")) {
                categoria = Categoria.ESPORTES;
            }else if (categoriaInput.equalsIgnoreCase("roupas")) {
                categoria = Categoria.ROUPAS;
            }
        }
        return categoria;
    }
        
    public void obterItem(Elemento elemento) {
		String nome = JOptionPane.showInputDialog("Nome", elemento.getNome());
		String codigo = JOptionPane.showInputDialog("Código", elemento.getCodigo());
		String descricao = JOptionPane.showInputDialog("Descrição", elemento.getDescricao());
		float valor = Float.parseFloat(JOptionPane.showInputDialog("Valor", elemento.getValor()));
		Categoria categoria = obterCategoria(elemento);
		elemento.setNome(nome);
		elemento.setCodigo(codigo);
		elemento.setDescricao(descricao);
		elemento.setValor(valor);
		elemento.setCategoria(categoria);

	}

	public void listaItems(List<Elemento> elemento) {
		StringBuilder listagem = new StringBuilder();
		for(Elemento item : elemento) {
			listagem.append(item.toString()).append("\n");
		}
		JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum Item encontrado" : listagem);
	}

	public void listaItem(Elemento elemento) {
		JOptionPane.showMessageDialog(null, elemento == null ? "Nenhum Item encontrado" : elemento.toString());
	}

	public void menu() {
		StringBuilder menu = new StringBuilder("Menu Items\n")
			.append("1 - Inserir\n")
			.append("2 - Atualizar por Codigo\n")
			.append("3 - Remover por Codigo\n")
			.append("4 - Exibir por Codigo\n")
			.append("5 - Exibir por id\n")
			.append("6 - Exibir todos\n")
			.append("7 - Exibir todos que contém determinado nome\n")
			.append("8 - Exibir todos que contém determinado nome em sua descrição\n")
			.append("9 - Exibir todos de uma determinada Categoria : Roupas / Eletronicos / Livros / Alimentos / Jogos / Brinquedos / Beleza / Moveis / Esportes\n")
			.append("10 - Exibir todos que possuem um valor abaixo do valor consultado\n")
			.append("11 - Exibir todos que possuem um valor acima do valor consultado\n")
			.append("12 - Exibir todos que contem determinado valor no seu código \n")
			.append("13 - Menu anterior");
		int opcao = 0;
		do {
			try {
				Elemento item;
				String codigo, nome, descricao, categoria;
				float valor;
                ;
				opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
				switch (opcao) {
					case 1:     // Inserir
						item = new Elemento();
						obterItem(item);
						baseItems.save(item);
						break;
					case 2:     // Atualizar por Codigo
						codigo = JOptionPane.showInputDialog("Digite o Código do Item a ser alterado");
						item = baseItems.findElementoByCodigo(codigo);
						if (item != null) {
							obterItem(item);
							baseItems.save(item);
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível atualizar, pois o Item não foi encontrado.");
						}
						break;
					case 3:     // Remover por Codigo
						codigo = JOptionPane.showInputDialog("Código");
						item = baseItems.findElementoByCodigo(codigo);
						if (item != null) {
							baseItems.deleteById(item.getId());
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o Item não foi encontrado.");
						}
						break;
					case 4:     // Exibir por Codigo
						codigo = JOptionPane.showInputDialog("Código");
						
						item = baseItems.findElementoByCodigo(codigo);
						listaItem(item);
						break;
					case 5:     // Exibir por id
						int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
						item = baseItems.findById(id).orElse(null);
						listaItem(item);
						break;
					case 6:     // Exibir todos
						listaItems(baseItems.findAllOrdenado());
						break;
					case 7:     // Exibir todos que contem um caractere
						 nome = JOptionPane.showInputDialog("Nome");
						listaItems(baseItems.findElementosByNome(nome));
						break;// */
					case 8:     // Exibir todos que contem um caractere especifico na descrição
						 descricao = JOptionPane.showInputDialog("Descrição");
						listaItems(baseItems.findElementosByDescricao(descricao));
						break;
					case 9:     // Exibir todos de determinada categoria (named query )
						 categoria = JOptionPane.showInputDialog("Categoria:  Roupas / Eletronicos / Livros / Alimentos / Jogos / Brinquedos / Beleza / Moveis / Esportes");
						listaItems(baseItems.findElementosByCategoriaNamed(categoria.toUpperCase()));
						break;
					case 10:     // Exibir todos que possuem um valor abaixo do valor consultado
						 valor = Float.parseFloat(JOptionPane.showInputDialog("Valor"));
						listaItems(baseItems.findElementosLessByValor(valor));
						break;
					case 11:// Exibir todos que possuem um valor acima do valor consultado
						 valor = Float.parseFloat(JOptionPane.showInputDialog("Valor"));
						listaItems(baseItems.findElementosMoreByValor(valor));
						break;
					case 12:     // Exibir todos que contem determinado valor no seu códiigo identificador
						codigo = JOptionPane.showInputDialog("Código parcial");
						listaItems(baseItems.findElementosByCodigo(codigo));
						break;
					case 13:     // Sair
						break;
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}

		} while(opcao != 13);
	}
}
