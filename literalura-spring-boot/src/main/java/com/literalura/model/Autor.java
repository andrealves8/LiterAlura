package com.literalura.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String nascimentoAno;
	private String morteAno;

	@OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
	private List<Livro> livros = new ArrayList<>();

	public Autor() {
	}

	public Autor(AutorDados autorDados) {
		this.nome = autorDados.nome();
		this.nascimentoAno = autorDados.nascimentoAno();
		this.morteAno = autorDados.morteAno();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNascimentoAno() {
		return nascimentoAno;
	}

	public void setNascimentoAno(String nascimentoAno) {
		this.nascimentoAno = nascimentoAno;
	}

	public String getMorteAno() {
		return morteAno;
	}

	public void setMorteAno(String morteAno) {
		this.morteAno = morteAno;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	/**
	 * Atualiza a lista de livros do autor, garantindo que cada livro referencie
	 * este autor.
	 */
	public void setLivros(List<Livro> livros) {
		this.livros.clear();
		if (livros != null) {
			livros.forEach(livro -> {
				livro.setAutor(this);
				this.livros.add(livro);
			});
		}
	}

	@Override
	public String toString() {
		List<String> titulos = livros.stream().filter(Objects::nonNull).map(Livro::getTitulo).toList();

		return "\n---------------------" + "\nNome: " + nome + "\nAno de nascimento: " + nascimentoAno
				+ "\nAno de morte: " + morteAno + "\nLivros: " + titulos + "\n---------------------";
	}
}
