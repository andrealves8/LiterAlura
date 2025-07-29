package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * DTO imutável que representa os dados de um livro, usados na desserialização
 * JSON. Ignora propriedades desconhecidas para maior tolerância a mudanças no
 * JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDados(@JsonAlias("Titulo") String titulo,

		@JsonAlias("autores") List<AutorDados> autor,

		@JsonAlias("linguagens") List<String> linguagens,

		@JsonAlias("downloads_contador") int downloads) {
}
