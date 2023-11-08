package br.dsp.projeto.sglcspringjpa.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dsp.projeto.sglcspringjpa.dao.PessoaDAO;
import br.dsp.projeto.sglcspringjpa.entiity.Pessoa;
import br.dsp.projeto.sglcspringjpa.entiity.enums.Sexo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MenuPessoa {
    	@Autowired
	private PessoaDAO baseClientes;

    private Sexo obterSexo(Pessoa cl) {
        Sexo sexo = Sexo.OUTROS; // Valor padrão

        String sexoInput = JOptionPane.showInputDialog("Sexo (M/F)", cl.getSexo().toString());

        if (sexoInput != null && !sexoInput.isEmpty()) {
            if (sexoInput.equalsIgnoreCase("F")) {
                sexo = Sexo.F;
            }else if (sexoInput.equalsIgnoreCase("M")) {
                sexo = Sexo.M;
            }/* else
                sexo = Sexo.OUTROS; */
        }

        return sexo;
    }

	public void obterCliente(Pessoa cl) {
		String nome = JOptionPane.showInputDialog("Nome", cl.getNome());
		String cpf = JOptionPane.showInputDialog("CPF", cl.getCpf());
        String data_nascimentoInput = JOptionPane.showInputDialog("Data de Nascimento (dd/MM/yyyy)", new SimpleDateFormat("dd/MM/yyyy").format(cl.getDataNascimento()));
        Sexo sexo = obterSexo(cl);

		cl.setNome(nome);
		cl.setCpf(cpf);
        try {
            Date data_nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(data_nascimentoInput);
            cl.setDataNascimento(data_nascimento);
        } catch (Exception e) {
            log.error("Erro: {}", e.getMessage(), e);
        }
        cl.setSexo(sexo);
	}

	public void listaClientes(List<Pessoa> clientes) {
		StringBuilder listagem = new StringBuilder();
		for(Pessoa cl : clientes) {
			listagem.append(cl.toString()).append("\n");
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
			.append("8 - Menu anterior");
		char opcao = '0';
		do {
			try {
				Pessoa cll;
			//	String cpf;
				opcao = JOptionPane.showInputDialog(menu).charAt(0);
				switch (opcao) {
					case '1':     // Inserir
						cll = new Pessoa();
						obterCliente(cll);
						baseClientes.save(cll);
						break;
					/* case '2':     // Atualizar por CPF
						cpf = JOptionPane.showInputDialog("Digite o CPF do cliente a ser alterado");
						cl = baseClientes.findFirstByCpf(cpf);
						if (cl != null) {
							obterCliente(cl);
							baseClientes.save(cl);
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível atualizar, pois o cliente não foi encontrado.");
						}
						break;
					case '3':     // Remover por CPF
						cpf = JOptionPane.showInputDialog("CPF");
						cl = baseClientes.findFirstByCpf(cpf);
						if (cl != null) {
							baseClientes.deleteById(cl.getId());
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o cliente não foi encontrado.");
						}
						break;
					case '4':     // Exibir por CPF
						cpf = JOptionPane.showInputDialog("CPF");
						// Temos 3 opções de implementação que fazem o mesmo:
						//cl = baseClientes.findFirstByCpf(cpf);
						//cl = baseClientes.buscaPrimeiroPorCpf(cpf);
						cl = baseClientes.buscaPorCpfViaConsultaNomeada(cpf);
						listaCliente(cl);
						break;
					case '5':     // Exibir por id
						int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
						cl = baseClientes.findById(id).orElse(null);
						listaCliente(cl);
						break;
					case '6':     // Exibir todos
						listaClientes(baseClientes.findAll());
						break;
					case '7':     // Exibir todos que contem determinado nome
						String nome = JOptionPane.showInputDialog("Nome");
						// Temos 2 opções de implementação que fazem o mesmo:
						// listaClientes(baseClientes.findByNomeStartingWith(nome));
						listaClientes(baseClientes.buscaPorNomeContendoString(nome));
						break; */
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
