package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Record para representar a estrutura principal que contém uma lista de livros.
 * Ignora propriedades desconhecidas no JSON para evitar erros na
 * desserialização.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(@JsonAlias("resultados") List<LivroDados> resultados) {
}
