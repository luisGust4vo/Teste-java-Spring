package com.exemplo.bibliotecaescolarbackend.controller;

import com.exemplo.bibliotecaescolarbackend.model.Emprestimo;
import com.exemplo.bibliotecaescolarbackend.model.Livro;
import com.exemplo.bibliotecaescolarbackend.repository.EmprestimoRepository;
import com.exemplo.bibliotecaescolarbackend.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmprestimoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @BeforeEach
    public void setup() {
        emprestimoRepository.deleteAll();
        livroRepository.deleteAll();
    }

    @Test
    public void testCriarEmprestimo() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");
        livro.setAutor("Autor Teste");
        livro.setAnoPublicacao(2021);
        livro.setGenero("Ficcao");
        livro = livroRepository.save(livro);

        String novoEmprestimoJson = String.format(
                "{\"livro\":{\"id\":%d},\"aluno\":\"Aluno Teste\",\"dataEmprestimo\":\"%s\",\"dataDevolucao\":\"%s\",\"devolvido\":false}",
                livro.getId(), LocalDate.now().toString(), LocalDate.now().plusDays(14).toString());

        mockMvc.perform(post("/api/emprestimos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novoEmprestimoJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.livro.id").value(livro.getId()))
                .andExpect(jsonPath("$.aluno").value("Aluno Teste"))
                .andExpect(jsonPath("$.devolvido").value(false));
    }

    @Test
    public void testDevolverLivro() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");
        livro.setAutor("Autor Teste");
        livro.setAnoPublicacao(2021);
        livro.setGenero("Ficcao");
        livro = livroRepository.save(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setAluno("Aluno Teste");
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucao(LocalDate.now().plusDays(14));
        emprestimo.setDevolvido(false);
        emprestimo = emprestimoRepository.save(emprestimo);

        mockMvc.perform(post("/api/emprestimos/devolucao/" + emprestimo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Emprestimo emprestimoDevolvido = emprestimoRepository.findById(emprestimo.getId()).orElseThrow();
        assert emprestimoDevolvido.isDevolvido();
    }
}
