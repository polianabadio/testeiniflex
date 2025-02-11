package com.iniflex.industria.domain.repository;

import com.iniflex.industria.domain.model.Funcionario;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByNome(String nome);

    @Modifying
    @Transactional
    @Query("DELETE FROM Funcionario f WHERE f.nome = :nome")
    void deleteByNome(String nome);
}
