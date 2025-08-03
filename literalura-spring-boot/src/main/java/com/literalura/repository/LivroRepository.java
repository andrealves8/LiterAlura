package com.literalura.repository;

import com.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Interface que representa o repositório de livros, extendendo JpaRepository para
// herdar métodos padrão de persistência (CRUD)
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    /**
     * Verifica se existe algum livro no banco cujo título contenha o texto passado.
     * Usa query JPQL para contar livros que satisfaçam a condição.
     * 
     * @param title Texto para buscar dentro do título do livro (parâmetro incorreto: "title" em vez de "titulo")
     * @return Boolean indicando se existe pelo menos um livro correspondente.
     */
    @Query("SELECT COUNT(b) > 0 FROM Livro b WHERE b.titulo LIKE %:titulo%")
    Boolean verificarExistenciaDeLivroNoBD(@Param("title") String title);

    /**
     * Busca livros cujo idioma (armazenado na lista 'linguagens') contenha o valor informado.
     * Usa query nativa SQL, pois o campo linguagens é uma lista e usa o operador ANY do SQL.
     * 
     * @param language Código do idioma (ex: "en", "pt", etc) para filtrar os livros.
     * @return Lista de livros que possuem o idioma especificado.
     */
    @Query(value = "SELECT * FROM livros WHERE :linguagem = ANY (livros.linguagens)", nativeQuery = true)
    List<Livro> buscarLivrosPorIdioma(@Param("language") String language);

    /**
     * Busca os livros ordenados pelo número de downloads, do maior para o menor.
     * Intenção de retornar somente os 10 primeiros, porém o JPQL não suporta LIMIT.
     * A query atual com 'LIMIT 10' vai gerar erro em JPQL.
     * 
     * @return Lista dos livros mais baixados (deve ser limitado no código que chama esse método).
     */
    @Query("SELECT b FROM Livro b ORDER BY b.downloads DESC LIMIT 10")
    List<Livro> buscarTop10();
}
