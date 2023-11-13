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
		String dataInput = JOptionPane.showInputDialog("Data de Nascimento(dd/MM/yyyy)", cliente.getDataNascimento());
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date dataNascimento = format.parse(dataInput);
	
			cliente.setNome(nome);
			cliente.setCpf(cpf);
			cliente.setEmail(email);
			cliente.setSexo(sexo);
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
			.append("5 - Exibir por Id\n")
			.append("6 - Exibir todos\n")
			.append("7 - Exibir todos que contém determinado nome\n")
			.append("8 - Exibir todos de determinado sexo(M / F / Outros)\n")
			.append("9 - Exibir todos que fazem aniversário entre dada uma data inicial e uma data final\n")
			.append("10 - Exibir todos que possuem email de determinado dominio (Gmail / Outlook / Hotmail / ...)\n")
			.append("11 - Menu anterior");
		int opcao = 0;
		do {
			try {
				Pessoa cliente;
				String cpf;
				opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
				switch (opcao) {
					case 1:     // Inserir
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
					case 4:     // Exibir por CPF (NAMED QUERY)
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
					case 7:     // Exibir todos que contem um caractere em seu nome
						String nome = JOptionPane.showInputDialog("Nome");
						listaClientes(baseClientes.findPessoaPorCaractereNome(nome));
						 //  todos que contem determinado caractere no nome, consulta baseadas no nome do método
						//listaClientes(baseClientes.findByNomeContainingIgnoreCase(nome)); 
						break;
					case 8:     //Exibir todos de determinado sexo
						String sexo = JOptionPane.showInputDialog("Sexo: M / F / Outros");
						listaClientes(baseClientes.findBySexo(sexo));
						break;
					
					case 9:     // Exibir todos que nasceram entre uma data inicial e uma data final
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						try {
							Date dataInicial = dateFormat.parse(JOptionPane.showInputDialog("Data inicial (dd/MM/yyyy)"));
							Date dataFinal = dateFormat.parse(JOptionPane.showInputDialog("Data limite (dd/MM/yyyy)"));

							List<Pessoa> nascidosEntreDatas = baseClientes.findByBirthBetweenDatas(dataInicial, dataFinal);

							if (nascidosEntreDatas.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Nenhum cliente encontrado entre as datas informadas.");
							} else {
								listaClientes(nascidosEntreDatas);
							}
							} catch (ParseException e) {
								JOptionPane.showMessageDialog(null, "Formato de data inválido. Use o formato dd/MM/yyyy.");
							}

						break;
					case 10:     // Exibir todos que possuem email de determinado dominio (Gmail, Outlook, Hotmail, ...)
						String dominio = JOptionPane.showInputDialog("Dominio do E-mail (Gmail, Outlook, Yahoo, ...)");
						listaClientes(baseClientes.findByEmailIgnoreCase(dominio));
						break;
							
					case 11:     // Sair
						break;
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}

		} while(opcao != 11);
	}
}
