package com.exemplo.bibliotecaescolarbackend.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livro livro;

    private String aluno;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;
}
