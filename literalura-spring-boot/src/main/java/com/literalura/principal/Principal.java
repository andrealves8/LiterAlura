package com.literalura.principal;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.literalura.model.Autor;
import com.literalura.model.AutorDados;
import com.literalura.model.Livro;
import com.literalura.model.LivroDados;
import com.literalura.model.Dados;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LivroRepository;
import com.literalura.service.ConsultorApi;
import com.literalura.service.ConversorDados;

import jakarta.transaction.Transactional;

@Service
public class Principal {
	private final Scanner leitor = new Scanner(System.in);
	private final ConsultorApi consultorApi = new ConsultorApi();
	private final ConversorDados conversorDados = new ConversorDados();
	private final AutorRepository autorRepository;
	private final LivroRepository livroRepository;

	public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
		this.livroRepository = livroRepository;
		this.autorRepository = autorRepository;
	}

	public void inicio() {
		int opcao = -1;

		while (opcao != 0) {
			String menu = """
					\n
					======================================
					            LiterAlura
					======================================
					\n
					--- Escolha uma opção ---

					1 - Buscar livro por título
					2 - Listar livros cadastrados
					3 - Listar autores cadastrados
					4 - Listar autores vivos em determinado ano
					5 - Listar livros por idioma
					6 - Listar os 10 livros mais baixados
					7 - Mostrar estatísticas do banco de dados de livros
					0 - Sair
					""";

			System.out.println(menu);

			if (leitor.hasNextInt()) {
				opcao = leitor.nextInt();
				leitor.nextLine();

				switch (opcao) {
				case 1 -> buscarLivroPorTitulo();
				case 2 -> listarLivrosRegistrados();
				case 3 -> listarAutoresRegistrados();
				case 4 -> listarAutoresVivos();
				case 5 -> listarLivrosPorIdioma();
				case 6 -> listarTop10();
				case 7 -> mostrarEstatisticasDoBanco();
				case 0 -> System.out.println("\nVocê saiu do aplicativo...");
				default -> System.out.println("\nOpção Inválida!");
				}

			} else {
				System.out.println("\nEntrada Inválida!");
				leitor.next();
			}
		}
	}

	@Transactional
	private void buscarLivroPorTitulo() {
		final String BASE_URL = "https://gutendex.com/books/?search=";
		System.out.println("\nInforme o nome do livro que você quer procurar: ");
		String titulo = leitor.nextLine().trim();

		if (!titulo.isBlank() && !isANumber(titulo)) {
			var json = consultorApi.obterDados(BASE_URL + titulo.replace(" ", "%20"));
			var dados = conversorDados.obterDados(json, Dados.class);

			Optional<LivroDados> buscarLivro = dados.resultados().stream()
					.filter(b -> b.titulo().toLowerCase().contains(titulo.toLowerCase())).findFirst();

			if (buscarLivro.isPresent()) {
				LivroDados livroDados = buscarLivro.get();

				if (!verificarExistenciaDoLivro(livroDados)) {
					Livro livro = new Livro(livroDados);
					AutorDados autorDados = livroDados.autor().get(0);
					Optional<Autor> optionalAutor = autorRepository.buscarNome(autorDados.nome());

					if (optionalAutor.isPresent()) {
						Autor autorExistente = optionalAutor.get();
						livro.setAutor(autorExistente);
						autorExistente.getLivros().add(livro);
						autorRepository.save(autorExistente);
					} else {
						Autor autor = new Autor(autorDados);
						livro.setAutor(autor);
						autor.getLivros().add(livro);
						autorRepository.save(autor);
					}

					livroRepository.save(livro);
					System.out.println("\nLivro adicionado no banco de dados.");
				} else {
					System.out.println("\nLivro já existente no banco de dados.");
				}

			} else {
				System.out.println("\nLivro não encontrado.");
			}

		} else {
			System.out.println("\nEntrada inválida.");
		}
	}

	private void listarLivrosRegistrados() {
		List<Livro> livros = livroRepository.findAll();

		if (!livros.isEmpty()) {
			System.out.println("\n----- Livros cadastrados -----");
			livros.forEach(System.out::println);
		} else {
			System.out.println("\nNão há nada aqui.");
		}
	}

	private void listarAutoresRegistrados() {
		List<Autor> autores = autorRepository.findAll();

		if (!autores.isEmpty()) {
			System.out.println("\n----- Autores cadastrados -----");
			autores.forEach(System.out::println);
		} else {
			System.out.println("\nNão há nada aqui.");
		}
	}

	private boolean verificarExistenciaDoLivro(LivroDados livroDados) {
		// Verifica existência pelo título diretamente, sem criar objeto Livro
		// desnecessário
		return livroRepository.verificarExistenciaDeLivroNoBD(livroDados.titulo());
	}

	private void listarAutoresVivos() {
		System.out.println("\nDigite o ano que você deseja consultar: ");

		if (leitor.hasNextInt()) {
			int ano = leitor.nextInt();
			leitor.nextLine();

			List<Autor> autores = autorRepository.buscarAutoresVivos(ano);

			if (!autores.isEmpty()) {
				System.out.println("\n----- Autores vivos em " + ano + " -----");
				autores.forEach(System.out::println);
			} else {
				System.out.println("\nNenhum resultado, digite outro ano.");
			}
		} else {
			System.out.println("\nEntrada inválida!");
			leitor.next();
		}
	}

	private void listarLivrosPorIdioma() {
		System.out.println("\nSelecione o idioma que deseja consultar");
		System.out.println("""
				1 - Inglês
				2 - Francês
				3 - Alemão
				4 - Português
				5 - Espanhol
				""");

		if (leitor.hasNextInt()) {
			int opcao = leitor.nextInt();
			leitor.nextLine();

			String linguagem = switch (opcao) {
			case 1 -> "en";
			case 2 -> "fr";
			case 3 -> "de";
			case 4 -> "pt";
			case 5 -> "es";
			default -> {
				System.out.println("\nEntrada Inválida!");
				yield "";
			}
			};

			if (!linguagem.isEmpty()) {
				List<Livro> livros = livroRepository.buscarLivrosPorIdioma(linguagem);

				if (!livros.isEmpty()) {
					System.out.println("\n----- Livros registrados -----");
					livros.forEach(System.out::println);
				} else {
					System.out.println("\nNenhum resultado, selecione outro idioma.");
				}
			}

		} else {
			System.out.println("\nEntrada Inválida!");
			leitor.next();
		}
	}

	private boolean isANumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void listarTop10() {
		List<Livro> livros = livroRepository.buscarTop10();

		if (!livros.isEmpty()) {
			System.out.println("\n----- Top 10 livros mais baixados -----");
			livros.forEach(livro -> System.out.println(livro.getTitulo()));
		} else {
			System.out.println("\nNenhum conteúdo disponível até o momento.");
		}
	}

	private void mostrarEstatisticasDoBanco() {
		List<Livro> livros = livroRepository.findAll();

		if (!livros.isEmpty()) {
			IntSummaryStatistics stats = livros.stream().filter(l -> l.getDownloads() > 0)
					.collect(Collectors.summarizingInt(Livro::getDownloads));

			System.out.println("\n----- Estatísticas do banco de dados -----");
			System.out.printf("Média de downloads: %.2f%n", stats.getAverage());
			System.out.println("Número máximo de downloads: " + stats.getMax());
			System.out.println("Número mínimo de downloads: " + stats.getMin());
			System.out.println("Livro(s) registrado(s): " + stats.getCount());
		} else {
			System.out.println("\nNenhum conteúdo disponível até o momento.");
		}
	}
}
