package com.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	@ManyToOne(fetch = FetchType.EAGER)
	private Autor autor;

	@ElementCollection
	private List<String> linguagens = new ArrayList<>();

	private int downloads;

	public Livro() {
	}

	public Livro(LivroDados livroDados) {
		this.titulo = livroDados.titulo();
		this.linguagens = livroDados.linguagens() != null ? new ArrayList<>(livroDados.linguagens())
				: new ArrayList<>();
		this.downloads = livroDados.downloads();

		if (livroDados.autor() != null && !livroDados.autor().isEmpty()) {
			this.autor = new Autor(livroDados.autor().get(0));
		} else {
			this.autor = null; // Pode ser tratado depois conforme regra de negócio
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public List<String> getLinguagens() {
		return linguagens;
	}

	public void setLinguagens(List<String> linguagens) {
		this.linguagens = linguagens != null ? new ArrayList<>(linguagens) : new ArrayList<>();
	}

	public int getDownloads() {
		return downloads;
	}

	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

	@Override
	public String toString() {
		return "\n---------------------" + "\nTítulo: " + titulo + "\nAutor: "
				+ (autor != null ? autor.getNome() : "Desconhecido") + "\nLinguagens: " + linguagens + "\nDownloads: "
				+ downloads + "\n---------------------";
	}
}
