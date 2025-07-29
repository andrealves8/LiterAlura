package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO imutável para transferência de dados do Autor. Ignora propriedades
 * desconhecidas no JSON para maior robustez.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDados(@JsonAlias("nome") String nome,

		@JsonAlias("nascimentoAno") String nascimentoAno,

		@JsonAlias("morteAno") String morteAno) {
}
