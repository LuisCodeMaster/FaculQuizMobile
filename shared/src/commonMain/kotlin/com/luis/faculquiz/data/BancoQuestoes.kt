package com.luis.faculquiz.data

import com.luis.faculquiz.model.Dificuldade
import com.luis.faculquiz.model.Questao

object BancoQuestoes {

    val questoes = mutableListOf(

        Questao(
            id = 1,
            disciplina = "Cálculo 1",
            assunto = "Limites",
            pergunta = "Calcule o limite quando x tende a 2 de (x² - 4)/(x - 2).",
            alternativas = listOf(
                "2",
                "4",
                "6",
                "8"
            ),
            correta = 1,
            explicacao = "Fatorando x² - 4 = (x - 2)(x + 2), obtemos x + 2. Portanto, o limite é 4.",
            dificuldade = Dificuldade.MEDIO
        ),

        Questao(
            id = 2,
            disciplina = "Cálculo 1",
            assunto = "Derivadas",
            pergunta = "Qual é a derivada de f(x) = x³ - 4x² + 2x?",
            alternativas = listOf(
                "3x² - 8x + 2",
                "3x² - 4x + 2",
                "x² - 8x + 2",
                "3x³ - 8x² + 2"
            ),
            correta = 0,
            explicacao = "Aplicando a regra da potência: f'(x) = 3x² - 8x + 2.",
            dificuldade = Dificuldade.MEDIO
        ),

        Questao(
            id = 3,
            disciplina = "Cálculo 1",
            assunto = "Integrais",
            pergunta = "Qual é uma primitiva de f(x) = 3x² + 2x?",
            alternativas = listOf(
                "x³ + x² + C",
                "3x³ + 2x² + C",
                "x³ + 2x + C",
                "6x + 2 + C"
            ),
            correta = 0,
            explicacao = "Integrando termo a termo: ∫3x² dx = x³ e ∫2x dx = x².",
            dificuldade = Dificuldade.MEDIO
        ),

        Questao(
            id = 4,
            disciplina = "Algoritmos",
            assunto = "Programação",
            pergunta = "Qual estrutura é normalmente utilizada para repetir um bloco enquanto uma condição for verdadeira?",
            alternativas = listOf(
                "if",
                "while",
                "switch",
                "return"
            ),
            correta = 1,
            explicacao = "A estrutura while executa repetidamente um bloco enquanto sua condição for verdadeira.",
            dificuldade = Dificuldade.FACIL
        ),

        Questao(
            id = 5,
            disciplina = "Álgebra Linear",
            assunto = "Matrizes",
            pergunta = "Qual é a dimensão de uma matriz com 3 linhas e 2 colunas?",
            alternativas = listOf(
                "2 × 3",
                "3 × 2",
                "3 × 3",
                "2 × 2"
            ),
            correta = 1,
            explicacao = "A dimensão é indicada por linhas × colunas. Portanto, 3 × 2.",
            dificuldade = Dificuldade.FACIL
        )
    )
}
