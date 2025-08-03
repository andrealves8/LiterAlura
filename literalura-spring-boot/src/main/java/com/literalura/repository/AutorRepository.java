package com.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.literalura.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Optional<Autor> buscarNome(@Param("nome") String nome);

    @Query("SELECT a FROM Autor a WHERE :ano BETWEEN CAST(a.nascimentoAno AS integer) AND CAST(a.morteAno AS integer)")
    List<Autor> buscarAutoresVivos(@Param("ano") int ano);

}
