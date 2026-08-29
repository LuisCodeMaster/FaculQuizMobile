package com.luis.faculquiz.data

import com.luis.faculquiz.model.Questao

object RepositorioQuestoes {

    private val listaDisciplinas = mutableListOf(
        "Cálculo 1",
        "Algoritmos",
        "Álgebra Linear"
    )

    fun todas(): List<Questao> {
        return BancoQuestoes.questoes
    }

    fun porDisciplina(disciplina: String): List<Questao> {
        return BancoQuestoes.questoes.filter {
            it.disciplina == disciplina
        }
    }

    fun disciplinas(): List<String> {
        return listaDisciplinas
    }

    fun adicionarDisciplina(nome: String) {
        val nomeTratado = nome.trim()
        if (nomeTratado.isNotBlank() && !listaDisciplinas.contains(nomeTratado)) {
            listaDisciplinas.add(nomeTratado)
        }
    }

    // NOVA FUNÇÃO: Remove a disciplina da lista dinâmica
    fun removerDisciplina(nome: String) {
        listaDisciplinas.remove(nome)
    }

    fun adicionar(questao: Questao) {
        BancoQuestoes.questoes.add(questao)
        adicionarDisciplina(questao.disciplina)
    }

    fun buscar(id: Int): Questao? {
        return BancoQuestoes.questoes.find {
            it.id == id
        }
    }
}
