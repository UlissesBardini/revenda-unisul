package br.unisul.revendaunisul;

import java.awt.EventQueue;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.unisul.revendaunisul.entity.Veiculo;
import br.unisul.revendaunisul.service.VeiculoService;

@SpringBootApplication
public class InitApp {

	@Autowired
	private TelaLogin telaLogin;
	
	@Autowired
	private ColaboradorService colaboradorService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(InitApp.class);
		builder.headless(false);
		builder.run(args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ac) {
		return args -> {
			try {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {

							//telaLogin.setVisible(true);
							
							Usuario usuario1 = new Usuario();
							usuario1 = usuarioService.buscarPor(1);
							System.err.println(usuario1);
							
							
							Colaborador colaborador1 = new Colaborador();
							colaborador1.setNomeCompleto("Mateus Colaborador");
							colaborador1.setCpf("123.123.123-12");
							colaborador1.setUsuario(usuario1);
							colaborador1.setDataDeNascimento(LocalDate.of(2000, 06, 10));
							colaborador1.setDataDeCadastro(LocalDate.now());
							colaborador1.setTelefone("55048996977503");
							
							//colaboradorService.inserir(colaborador1);
							
							List<Colaborador> listaEncontrada = colaboradorService.listarPor(colaborador1.getNomeCompleto());
							System.err.println(listaEncontrada);
							

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		};
	}

}