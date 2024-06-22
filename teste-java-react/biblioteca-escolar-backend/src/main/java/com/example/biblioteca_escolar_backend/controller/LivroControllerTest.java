package com.exemplo.bibliotecaescolarbackend.controller;

import com.exemplo.bibliotecaescolarbackend.model.Livro;
import com.exemplo.bibliotecaescolarbackend.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @BeforeEach
    public void setup() {
        livroRepository.deleteAll();
    }

    @Test
    public void testCriarLivro() throws Exception {
        String novoLivroJson = "{\"titulo\":\"Livro Teste\",\"autor\":\"Autor Teste\",\"anoPublicacao\":2021,\"genero\":\"Ficcao\"}";

        mockMvc.perform(post("/api/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novoLivroJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Livro Teste"))
                .andExpect(jsonPath("$.autor").value("Autor Teste"))
                .andExpect(jsonPath("$.anoPublicacao").value(2021))
                .andExpect(jsonPath("$.genero").value("Ficcao"));
    }

    @Test
    public void testListarLivros() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");
        livro.setAutor("Autor Teste");
        livro.setAnoPublicacao(2021);
        livro.setGenero("Ficcao");
        livroRepository.save(livro);

        mockMvc.perform(get("/api/livros"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("Livro Teste"));
    }
}
