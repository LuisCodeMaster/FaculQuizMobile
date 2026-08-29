package com.luis.faculquiz.model

data class Questao(
    val id: Int,
    val disciplina: String,
    val assunto: String,
    val pergunta: String,
    val alternativas: List<String>,
    val correta: Int,
    val explicacao: String,
    val dificuldade: Dificuldade
)

enum class Dificuldade {
    FACIL,
    MEDIO,
    DIFICIL
}
