package com.iniflex.industria.presentation.controller;

import com.iniflex.industria.application.service.FuncionarioService;
import com.iniflex.industria.domain.model.Funcionario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping
    public List<Funcionario> listarTodos() {
        return funcionarioService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Funcionario> criar(@RequestBody Funcionario funcionario) {
        return ResponseEntity.ok(funcionarioService.salvar(funcionario));
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> remover(@PathVariable String nome) {
        funcionarioService.removerPorNome(nome);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/aumentar-salario/{percentual}")
    public ResponseEntity<Void> aumentarSalario(@PathVariable BigDecimal percentual) {
        funcionarioService.aumentarSalario(percentual);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/agrupados")
    public ResponseEntity<Map<String, List<Funcionario>>> agruparPorFuncao() {
        return ResponseEntity.ok(funcionarioService.agruparPorFuncao());
    }

    @GetMapping("/aniversariantes/{meses}")
    public ResponseEntity<List<Funcionario>> aniversariantes(@PathVariable int[] meses) {
        return ResponseEntity.ok(funcionarioService.listarAniversariantes(meses));
    }

    @GetMapping("/mais-velho")
    public ResponseEntity<Funcionario> funcionarioMaisVelho() {
        return ResponseEntity.ok(funcionarioService.getFuncionarioMaisVelho());
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<Funcionario>> listarOrdenados() {
        return ResponseEntity.ok(funcionarioService.listarOrdenadosPorNome());
    }

    @GetMapping("/total-salarios")
    public ResponseEntity<BigDecimal> totalSalarios() {
        return ResponseEntity.ok(funcionarioService.calcularTotalSalarios());
    }

    @GetMapping("/salarios-minimos/{salarioMinimo}")
    public ResponseEntity<Map<String, Double>> salariosMinimos(@PathVariable double salarioMinimo) {
        return ResponseEntity.ok(funcionarioService.calcularSalariosMinimos(salarioMinimo));
    }
}
