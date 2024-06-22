package com.exemplo.bibliotecaescolarbackend.repository;

import com.exemplo.bibliotecaescolarbackend.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContaining(String titulo);
    List<Livro> findByAutorContaining(String autor);
    List<Livro> findByGeneroContaining(String genero);
}
