package com.guardians.profiles;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.guardians.model.Acesso;
import com.guardians.model.Configuracao;
import com.guardians.model.Endereco;
import com.guardians.model.Grupo;
import com.guardians.model.GruposPermitidos;
import com.guardians.model.Usuario;
import com.guardians.repositories.AcessoRepository;
import com.guardians.repositories.ConfiguracaoRepository;
import com.guardians.repositories.EnderecoRepository;
import com.guardians.repositories.GrupoRepository;
import com.guardians.repositories.UsuarioRepository;

@Configuration
@Profile("test")
public class TestProfile implements CommandLineRunner{
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ConfiguracaoRepository configuracaoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
//	@Autowired
//	private NotificacaoRepository notificacaoRepository;
	
	@Override
	@Transactional(readOnly = false)
	public void run(String... args){
				
		Usuario usuario1 = new Usuario("Leonardo", "Lacerda", 1998, "leon.lacerda2015@gmail.com");
		usuario1.getTelefones().addAll(Arrays.asList("1155266481"));
		
		Usuario usuario2 = new Usuario("Rafael", "Almeida", 1999, "rafael.almeida@gmail.com");
		usuario2.getTelefones().addAll(Arrays.asList("1195324804"));
		
		Usuario usuario3 = new Usuario("Igor", "Lopes", 2000, "igor.lopes@gmail.com");
		usuario3.getTelefones().addAll(Arrays.asList("1552649812"));
		
		usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2, usuario3));
		
		usuario1.setGuardiao(usuario3);
		usuario2.setGuardiao(usuario1);
		usuario3.setGuardiao(usuario2);
		
		Acesso acesso1 = new Acesso("leon.lacerda", encoder.encode("senha123"), "dica1", "dica2");
		Acesso acesso2 = new Acesso("rafailds", encoder.encode("senha123"), "dica1", "dica2");
		Acesso acesso3 = new Acesso("deusDoRocket", encoder.encode("senha123"), "dica1", "dica2");
	
		acesso1.addProfile(com.guardians.model.enums.Profile.ADMIN);
			
		acesso1.setUsuario(usuario1);
		acesso2.setUsuario(usuario2);
		acesso3.setUsuario(usuario3);
		
		Endereco endereco1 = new Endereco("04857135", "São Paulo", "São Paulo", "Jardim Marilda", "Estrada do porto", "56");
		Endereco endereco2 = new Endereco("05748123", "São Paulo", "São Paulo", "Grajau", "Mirna", "338-B");
		endereco2.setComplemento("apto-43");
		Endereco endereco3 = new Endereco("04268365", "São Paulo", "São Paulo", "Nakanura", "Rua de trás", "14");
	
		endereco1.setUsuario(usuario1);
		endereco2.setUsuario(usuario2);
		endereco3.setUsuario(usuario3);
		
		Configuracao configuracao1 = new Configuracao("Eu to caindooo", true, true, true, true, true);
		Configuracao configuracao2 = new Configuracao("Preciso de ajuda", true, true, true, true, true);
		Configuracao configuracao3 = new Configuracao("HIHIHIHIHI", false, false, false, false, true);
		
		configuracao1.setUsuario(usuario1);
		configuracao2.setUsuario(usuario2);
		configuracao3.setUsuario(usuario3);
		
		Grupo grupo1 = new Grupo("Rushadores");//
		Grupo grupo2 = new Grupo("PC");
		
		grupo1.getUsuarios().addAll(Arrays.asList(usuario1, usuario2, usuario3));
		grupo2.getUsuarios().addAll(Arrays.asList(usuario2, usuario3));
		
		usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2, usuario3));
		acessoRepository.saveAll(Arrays.asList(acesso1, acesso2, acesso3));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));
		grupoRepository.saveAll(Arrays.asList(grupo1, grupo2));

		//Os usuarios precisam estar com os id preenchidos
		GruposPermitidos grupoNotificacao21 = new GruposPermitidos(configuracao2, grupo1);
		GruposPermitidos grupoNotificacao22 = new GruposPermitidos(configuracao2, grupo2);
		GruposPermitidos grupoNotificacao32 = new GruposPermitidos(configuracao3, grupo2);
		
		configuracao2.getGrupoNotificacoes().addAll(Arrays.asList(grupoNotificacao21, grupoNotificacao22));
		configuracao3.getGrupoNotificacoes().addAll(Arrays.asList(grupoNotificacao32));
		
		configuracaoRepository.saveAll(Arrays.asList(configuracao1, configuracao2, configuracao3));
		grupoRepository.saveAll(Arrays.asList(grupo1, grupo2));
		
	}

}
