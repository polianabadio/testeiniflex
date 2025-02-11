package com.iniflex.industria.application.service;

import com.iniflex.industria.domain.model.Funcionario;
import com.iniflex.industria.domain.repository.FuncionarioRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FuncionarioServiceTest {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    @BeforeEach
    void setUp() {
        
        List<Funcionario> funcionarios = new ArrayList<>(List.of(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario( "Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario( "Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario( "Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario( "Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario( "Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario( "Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario( "Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario( "Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarioRepository.saveAll(funcionarios);
    }


    @Test
    void deveListarTodosOsFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarTodos();
        System.out.println("\n--- Lista de Funcionários ---");
        funcionarios.forEach(f -> System.out.println(f));
        assertThat(funcionarios).hasSize(10);
    }

    @Test
    void deveRemoverFuncionarioPorNome() {
        funcionarioService.removerPorNome("João");
        List<Funcionario> funcionarios = funcionarioService.listarTodos();
        System.out.println("\n--- Lista após remover João ---");
        funcionarios.forEach(f -> System.out.println(f));
        assertThat(funcionarios).hasSize(9);
        assertThat(funcionarios).noneMatch(f -> f.getNome().equals("João"));
    }

    @Test
    void deveFormatarDataESalarioCorretamente() {
        List<Funcionario> funcionarios = funcionarioService.listarTodos();
        NumberFormat formatoBrasileiro = NumberFormat.getInstance(new Locale("pt", "BR"));

        System.out.println("\n--- Funcionários com Formatação Correta ---");
        funcionarios.forEach(f -> {
            String dataFormatada = f.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String salarioFormatado = formatoBrasileiro.format(f.getSalario());
            System.out.println(f.getNome() + " - " + dataFormatada + " - R$ " + salarioFormatado);
        });

        assertThat(funcionarios.get(0).getDataNascimento().getYear()).isEqualTo(2000);
    }

    @Test
    void deveAumentarSalarioEm10Porcento() {
        funcionarioService.aumentarSalario(BigDecimal.TEN);
        List<Funcionario> funcionarios = funcionarioService.listarTodos();

        System.out.println("\n--- Lista após aumento de 10% ---");
        funcionarios.forEach(f -> System.out.println(f));

        assertThat(funcionarios)
                .allSatisfy(f -> assertThat(f.getSalario()).isGreaterThan(new BigDecimal("1212.00")));
    }

    @Test
    void deveAgruparFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> agrupados = funcionarioService.agruparPorFuncao();

        System.out.println("\n--- Funcionários Agrupados por Função ---");
        agrupados.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println("  - " + f.getNome()));
        });

        assertThat(agrupados).containsKeys("Operador", "Gerente", "Coordenador", "Diretor", "Recepcionista", "Eletricista", "Contador");
    }

    @Test
    void deveListarFuncionariosAniversariantesEmOutubroEDezembro() {
        List<Funcionario> aniversariantes = funcionarioService.listarAniversariantes(10, 12);

        System.out.println("\n--- Funcionários que fazem Aniversário em Outubro e Dezembro ---");
        aniversariantes.forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento()));

        assertThat(aniversariantes).extracting(Funcionario::getNome)
                .containsExactlyInAnyOrder("Maria", "Miguel");
    }

    @Test
    void deveRetornarFuncionarioMaisVelho() {
        Funcionario maisVelho = funcionarioService.getFuncionarioMaisVelho();
        long idade = ChronoUnit.YEARS.between(maisVelho.getDataNascimento(), LocalDate.now());

        System.out.println("\n--- Funcionário Mais Velho ---");
        System.out.println(maisVelho.getNome() + " - " + idade + " anos");

        assertThat(maisVelho.getNome()).isEqualTo("Caio");
    }

    @Test
    void deveOrdenarFuncionariosPorNome() {
        List<Funcionario> ordenados = funcionarioService.listarOrdenadosPorNome();

        System.out.println("\n--- Funcionários em Ordem Alfabética ---");
        ordenados.forEach(f -> System.out.println(f.getNome()));

        assertThat(ordenados).extracting(Funcionario::getNome)
                .containsExactly("Alice", "Arthur", "Caio", "Heitor", "Helena", "Heloísa", "João", "Laura", "Maria", "Miguel");
    }

    @Test
    void deveCalcularTotalDosSalarios() {
        BigDecimal totalSalarios = funcionarioService.calcularTotalSalarios();

        System.out.println("\n--- Total dos Salários ---");
        System.out.println("R$ " + NumberFormat.getInstance(new Locale("pt", "BR")).format(totalSalarios));

        assertThat(totalSalarios).isGreaterThan(new BigDecimal("40000"));
    }

    @Test
    void deveCalcularSalariosMinimosPorFuncionario() {
        Map<String, Double> salariosMinimos = funcionarioService.calcularSalariosMinimos(SALARIO_MINIMO.doubleValue());

        System.out.println("\n--- Salários em relação ao Mínimo ---");
        salariosMinimos.forEach((nome, qtdSalarios) ->
                System.out.println(nome + " recebe " + String.format("%.2f", qtdSalarios) + " salários mínimos"));

        assertThat(salariosMinimos.get("Maria")).isGreaterThan(1);
        assertThat(salariosMinimos.get("Miguel")).isGreaterThan(10);
    }
}
