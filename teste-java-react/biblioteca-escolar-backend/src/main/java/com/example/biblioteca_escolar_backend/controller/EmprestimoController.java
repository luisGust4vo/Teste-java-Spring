package com.exemplo.bibliotecaescolarbackend.controller;

import com.exemplo.bibliotecaescolarbackend.model.Emprestimo;
import com.exemplo.bibliotecaescolarbackend.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @PostMapping
    public Emprestimo criarEmprestimo(@RequestBody Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    @PostMapping("/devolucao/{id}")
    public ResponseEntity<?> devolverLivro(@PathVariable Long id) {
        Optional<Emprestimo> optionalEmprestimo = emprestimoRepository.findById(id);
        if (!optionalEmprestimo.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Emprestimo emprestimo = optionalEmprestimo.get();
        emprestimo.setDevolvido(true);
        emprestimoRepository.save(emprestimo);
        return ResponseEntity.ok().build();
    }
}
