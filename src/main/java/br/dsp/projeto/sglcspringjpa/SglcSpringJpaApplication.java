package br.dsp.projeto.sglcspringjpa;



import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.dsp.projeto.sglcspringjpa.ui.MenuPessoa;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication/* (scanBasePackages = "br.dsp.projeto.sglcspringjpa") */
/* @EntityScan("br.dsp.projeto.sglcspringjpa.entity")
@EnableJpaRepositories("br.dsp.projeto.sglcspringjpa.dao") */
public class SglcSpringJpaApplication implements CommandLineRunner {

    @Autowired
    private MenuPessoa menuPessoa;

  public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SglcSpringJpaApplication.class);
		builder.headless(false).run(args);
	}


        public void run(String... args) throws Exception {
            StringBuilder menu = new StringBuilder("Menu Principal\n")
			.append("1 - Clientes\n")
			.append("2 - Produtos\n")
			.append("3 - Compras\n")
			.append("4 - Sair");
		char opcao = '0';
		do {
			try {
				opcao = JOptionPane.showInputDialog(menu).charAt(0);
				switch (opcao) {
					case '1':     // Clientes
						menuPessoa.menu();
                        log.info("SUCESSO OK");
						break;
					/* case '2':     // Produtos
						menuProdutos.menu();
						break;
					case '3':     // Compras
						menuCompras.menu();
						break; */
					case '4':     // Sair
						break;
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break;
					}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}

		} while(opcao != '4');
        }
}


