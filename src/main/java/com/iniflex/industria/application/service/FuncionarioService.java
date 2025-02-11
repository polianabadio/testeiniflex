package com.iniflex.industria.application.service;

import com.iniflex.industria.domain.model.Funcionario;
import com.iniflex.industria.domain.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario salvar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public void removerPorNome(String nome) {
        funcionarioRepository.deleteByNome(nome);
    }

    public void aumentarSalario(BigDecimal percentual) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(BigDecimal.ONE.add(percentual.divide(BigDecimal.valueOf(100))))));
        funcionarioRepository.saveAll(funcionarios);
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return funcionarioRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public List<Funcionario> listarAniversariantes(int... meses) {
        return funcionarioRepository.findAll()
                .stream()
                .filter(f -> Arrays.stream(meses).anyMatch(m -> f.getDataNascimento().getMonthValue() == m))
                .toList();
    }

    public Funcionario getFuncionarioMaisVelho() {
        return funcionarioRepository.findAll()
                .stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow();
    }

    public List<Funcionario> listarOrdenadosPorNome() {
    Collator collator = Collator.getInstance(new Locale("pt", "BR"));
    collator.setStrength(Collator.PRIMARY);

    return funcionarioRepository.findAll()
        .stream()
        .sorted((f1, f2) -> collator.compare(f1.getNome(), f2.getNome()))
        .toList();
}
    

    public BigDecimal calcularTotalSalarios() {
        return funcionarioRepository.findAll()
                .stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, Double> calcularSalariosMinimos(double salarioMinimo) {
        return funcionarioRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Funcionario::getNome,
                        f -> f.getSalario().doubleValue() / salarioMinimo
                ));
    }
}
