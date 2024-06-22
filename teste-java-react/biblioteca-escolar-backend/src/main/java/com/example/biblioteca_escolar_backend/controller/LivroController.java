package com.exemplo.bibliotecaescolarbackend.controller;

import com.exemplo.bibliotecaescolarbackend.model.Livro;
import com.exemplo.bibliotecaescolarbackend.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    @Autowired
    private LivroRepository livroRepository;

    @PostMapping
    public Livro criarLivro(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }

    @GetMapping
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    @GetMapping("/search")
    public List<Livro> buscarLivros(@RequestParam(required = false) String titulo,
                                    @RequestParam(required = false) String autor,
                                    @RequestParam(required = false) String genero) {
        if (titulo != null) {
            return livroRepository.findByTituloContaining(titulo);
        } else if (autor != null) {
            return livroRepository.findByAutorContaining(autor);
        } else if (genero != null) {
            return livroRepository.findByGeneroContaining(genero);
        } else {
            return listarLivros();
        }
    }
}
