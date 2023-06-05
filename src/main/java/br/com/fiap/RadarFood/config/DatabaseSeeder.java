package br.com.fiap.RadarFood.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.RadarFood.models.Usuario;
import br.com.fiap.RadarFood.models.Endereco;
import br.com.fiap.RadarFood.models.TipoUsuario;
import br.com.fiap.RadarFood.repository.EnderecoRepository;
import br.com.fiap.RadarFood.repository.UsuarioRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner{

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;


    @Override
    public void run(String... args) throws Exception {
        
        Usuario usuario = Usuario.builder()
        .nome("Gustavo")
        .email("gubalero@hotmail.com")
        .senha(encoder.encode("12345"))
        .telefone("(11) 99504-9078")
        .tipoUsuario(TipoUsuario.R)
        .ativo(true)
        .foto("")
        .build();

        usuarioRepository.save(usuario);

        Endereco endereco1 = Endereco.builder()
        .logradouro("Rua Evaldo Calabrez 1")
        .bairro("Vila Princesa Isabel")
        .numero("1804")
        .cidade("São Paulo")
        .uf("SP")
        .cep("08410-070")
        .usuario(usuario)
        .ativo(true)
        .latitude(-23.5665)
        .longitude(-46.4515)
        .build();

        Endereco endereco2 = Endereco.builder()
        .logradouro("Rua Evaldo Calabrez 2")
        .bairro("Vila Princesa Isabel")
        .numero("1804")
        .cidade("São Paulo")
        .uf("SP")
        .cep("08410-070")
        .usuario(usuario)
        .ativo(true)
        .latitude(-23.5665)
        .longitude(-46.4515)
        .build();

        Endereco endereco3 = Endereco.builder()
        .logradouro("Rua Evaldo Calabrez 3")
        .bairro("Vila Princesa Isabel")
        .numero("1804")
        .cidade("São Paulo")
        .uf("SP")
        .cep("08410-070")
        .usuario(usuario)
        .ativo(true)
        .latitude(-23.5665)
        .longitude(-46.4515)
        .build();

        Endereco endereco4 = Endereco.builder()
        .logradouro("Rua Evaldo Calabrez 4")
        .bairro("Vila Princesa Isabel")
        .numero("1804")
        .cidade("São Paulo")
        .uf("SP")
        .cep("08410-070")
        .usuario(usuario)
        .ativo(true)
        .latitude(-23.5665)
        .longitude(-46.4515)
        .build();

        Endereco endereco5 = Endereco.builder()
        .logradouro("Rua Evaldo Calabrez 5")
        .bairro("Vila Princesa Isabel")
        .numero("1804")
        .cidade("São Paulo")
        .uf("SP")
        .cep("08410-070")
        .usuario(usuario)
        .ativo(true)
        .latitude(-23.5665)
        .longitude(-46.4515)
        .build();

        Endereco endereco6 = Endereco.builder()
        .logradouro("Rua Evaldo Calabrez 6")
        .bairro("Vila Princesa Isabel")
        .numero("1804")
        .cidade("São Paulo")
        .uf("SP")
        .cep("08410-070")
        .usuario(usuario)
        .ativo(true)
        .latitude(-23.5665)
        .longitude(-46.4515)
        .build();

        enderecoRepository.save(endereco1);
        enderecoRepository.save(endereco2);
        enderecoRepository.save(endereco3);
        enderecoRepository.save(endereco4);
        enderecoRepository.save(endereco5);
        enderecoRepository.save(endereco6);
    }
    
}
