package com.exemplo.bibliotecaescolarbackend.repository;

import com.exemplo.bibliotecaescolarbackend.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
