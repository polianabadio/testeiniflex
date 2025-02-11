package com.iniflex.industria.domain.repository;

import com.iniflex.industria.domain.model.Funcionario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByNome(String nome);

    void deleteByNome(String nome);
}
