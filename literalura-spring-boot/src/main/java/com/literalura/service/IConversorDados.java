package com.literalura.service;

/**
 * Interface para conversão de dados JSON em objetos Java.
 */
public interface IConversorDados {

	/**
	 * Converte uma string JSON em um objeto da classe especificada.
	 * 
	 * @param <T>      Tipo do objeto de retorno.
	 * @param json     String JSON a ser convertida.
	 * @param anyClass Classe do objeto para o qual o JSON será convertido.
	 * @return Objeto da classe especificada contendo os dados do JSON.
	 * @throws RuntimeException se o JSON não puder ser processado.
	 */
	<T> T obterDados(String json, Class<T> anyClass);
}
