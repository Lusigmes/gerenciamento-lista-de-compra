package br.dsp.projeto.sglcspringjpa.ui;

import java.text.ParseException;
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

    private Sexo obterSexo(Pessoa cliente) {
        Sexo sexo = Sexo.OUTROS; 

        String sexoInput = JOptionPane.showInputDialog("Sexo (M/F)", cliente.getSexo());

        if (sexoInput != null && !sexoInput.isEmpty()) {
            if (sexoInput.equalsIgnoreCase("F")) {
                sexo = Sexo.F;
            }else if (sexoInput.equalsIgnoreCase("M")) {
                sexo = Sexo.M;
            }
        }

        return sexo;
    }

	public void obterCliente(Pessoa cliente) {
		String nome = JOptionPane.showInputDialog("Nome", cliente.getNome());
		String cpf = JOptionPane.showInputDialog("CPF", cliente.getCpf());
		String email = JOptionPane.showInputDialog("E-mail", cliente.getEmail());
        Sexo sexo = obterSexo(cliente);
		
		cliente.setNome(nome);
		cliente.setCpf(cpf);
		cliente.setEmail(email);
		cliente.setSexo(sexo);
		
      /*   tratamento de data defasado
	  String data_nascimentoInput = JOptionPane.showInputDialog("Data de Nascimento (dd/MM/yyyy)", new SimpleDateFormat("dd/MM/yyyy").format(cl.getDataNascimento()));
		if (data_nascimentoInput != null && !data_nascimentoInput.isEmpty()) {
			try {
				Date data_nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(data_nascimentoInput);
				cl.setDataNascimento(data_nascimento);
			} catch (ParseException e) {
				log.error("Erro: {}", e.getMessage(), e);
			}
		} */
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
			.append("8 - Menu anterior");
		char opcao = '0';
		do {
			try {
				Pessoa cliente;
				String cpf;
				opcao = JOptionPane.showInputDialog(menu).charAt(0);
				switch (opcao) {
					case '1':     // Inserir
						cliente = new Pessoa();
						obterCliente(cliente);
						baseClientes.save(cliente);
						break;
					case '2':     // Atualizar por CPF
						cpf = JOptionPane.showInputDialog("Digite o CPF do cliente a ser alterado");
						cliente = baseClientes.findPessoaByCpf(cpf);
						if (cliente != null) {
							obterCliente(cliente);
							baseClientes.save(cliente);
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível atualizar, pois o cliente não foi encontrado.");
						}
						break;
					case '3':     // Remover por CPF
						cpf = JOptionPane.showInputDialog("CPF");
						cliente = baseClientes.findPessoaByCpf(cpf);
						if (cliente != null) {
							baseClientes.deleteById(cliente.getId());
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o cliente não foi encontrado.");
						}
						break;
					case '4':     // Exibir por CPF
						cpf = JOptionPane.showInputDialog("CPF");
						
						cliente = baseClientes.findPessoaPorCpfNomeado(cpf);
						listaCliente(cliente);
						break;
					case '5':     // Exibir por id
						int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
						cliente = baseClientes.findById(id).orElse(null);
						listaCliente(cliente);
						break;
					case '6':     // Exibir todos
						listaClientes(baseClientes.findAll());
						break;
					case '7':     // Exibir todos que contem um caractere
						String nome = JOptionPane.showInputDialog("Nome");
						listaClientes(baseClientes.findPessoaPorNomeEspecifico(nome));
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
