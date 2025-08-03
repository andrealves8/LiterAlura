package com.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorDados implements IConversorDados {

	private final ObjectMapper objectMapper;

	public ConversorDados() {
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public <T> T obterDados(String json, Class<T> anyClass) {
		try {
			return objectMapper.readValue(json, anyClass);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Erro, não é possível processar JSON.", e);
		}
	}
}
