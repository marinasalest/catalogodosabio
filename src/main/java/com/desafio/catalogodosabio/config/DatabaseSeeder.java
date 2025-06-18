package com.desafio.catalogodosabio.config;

import com.desafio.catalogodosabio.model.*;
import com.desafio.catalogodosabio.repository.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final TipoRepository tipoRepository;
    private final GeneroRepository generoRepository;
    private final IdiomaRepository idiomaRepository;
    private final EditoraRepository editoraRepository;
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public DatabaseSeeder(
            TipoRepository tipoRepository,
            GeneroRepository generoRepository,
            IdiomaRepository idiomaRepository,
            EditoraRepository editoraRepository,
            AutorRepository autorRepository,
            LivroRepository livroRepository
    ) {
        this.tipoRepository = tipoRepository;
        this.generoRepository = generoRepository;
        this.idiomaRepository = idiomaRepository;
        this.editoraRepository = editoraRepository;
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    public void run(String... args) {
        if (livroRepository.count() > 0) {
            System.out.println("Banco já populado, seed ignorado.");
            return;
        }

        Faker faker = new Faker(new Locale("pt-BR"));

        List<Tipo> tipos = Arrays.asList(
                new Tipo("Físico"),
                new Tipo("Físico Especial"),
                new Tipo("Online")
        );
        tipoRepository.saveAll(tipos);

        List<String> generosNomes = Arrays.asList(
                "Romance", "Ficção Científica", "Fantasia", "Terror", "Suspense", "Drama", "Histórico", "Biografia",
                "Autoajuda", "Economia", "Tecnologia", "Psicologia", "Religião", "Acadêmico/Didático", "Infantil",
                "Adolescente", "HQs e Mangás", "Poesia", "Gastronomia/Culinária", "Artes/Fotografia/Design",
                "Viagem/Turismo", "Esporte", "Saúde e Bem Estar", "Policial", "Erótico", "Humor"
        );
        List<Genero> generos = new ArrayList<>();
        for (String nome : generosNomes) {
            generos.add(new Genero(nome));
        }
        generoRepository.saveAll(generos);

        List<String> idiomasNomes = Arrays.asList(
                "Português Brasil", "Português Portugal", "Inglês", "Espanhol", "Francês", "Alemão", "Italiano",
                "Japonês", "Chinês (Mandarim)", "Coreano", "Russo", "Árabe"
        );
        List<Idioma> idiomas = new ArrayList<>();
        for (String nome : idiomasNomes) {
            idiomas.add(new Idioma(nome));
        }
        idiomaRepository.saveAll(idiomas);

        List<Editora> editoras = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            editoras.add(new Editora(
                    faker.book().publisher(),
                    faker.number().digits(14),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone()
            ));
        }
        editoraRepository.saveAll(editoras);

        List<Autor> autores = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            autores.add(new Autor(
                    faker.book().author(),
                    faker.country().name(),
                    faker.number().numberBetween(1, 50)
            ));
        }
        autorRepository.saveAll(autores);

        List<Livro> livros = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1500; i++) {
            Tipo tipo = tipos.get(random.nextInt(tipos.size()));
            Genero genero = generos.get(random.nextInt(generos.size()));
            Idioma idioma = idiomas.get(random.nextInt(idiomas.size()));
            Editora editora = editoras.get(random.nextInt(editoras.size()));
            Autor autor = autores.get(random.nextInt(autores.size()));

            Integer qtd = tipo.getNome().equalsIgnoreCase("Online") ? null : faker.number().numberBetween(1, 100);

            String nomeArquivo = faker.file().fileName(null, null, "jpg", null)
                .replace("\\", "_").replace("/", "_");
            String capaUrl = "https://s3.amazonaws.com/seu-bucket/capas/" + nomeArquivo;

            Livro livro = new Livro();
            livro.setTitulo(faker.book().title());
            livro.setAutor(autor);
            livro.setCapaUrl(capaUrl);
            livro.setAno(faker.number().numberBetween(1950, 2024));
            livro.setSinopse(faker.lorem().paragraph());
            livro.setGenero(genero);
            livro.setEditora(editora);
            livro.setTipo(tipo);
            livro.setIdioma(idioma);
            livro.setPreco(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 300)));
            livro.setQtd(qtd);

            livros.add(livro);
        }
        livroRepository.saveAll(livros);

        System.out.println("Banco populado com sucesso!");
    }
}