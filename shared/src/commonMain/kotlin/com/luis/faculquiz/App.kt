package com.luis.faculquiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val FundoPreto = Color(0xFF080808)
private val CartaoEscuro = Color(0xFF111111)
private val BotaoEscuro = Color(0xFF181818)
private val TextoBranco = Color(0xFFFFFFFF)
private val TextoSecundario = Color(0xFFBDBDBD)
private val Verde = Color(0xFF00E676)
private val Vermelho = Color(0xFFFF5252)

data class Questao(
    val pergunta: String,
    val alternativas: List<String>,
    val correta: Int
)

private val questoes = listOf(
    Questao(
        pergunta = "Qual é a capital do Brasil?",
        alternativas = listOf(
            "São Paulo",
            "Rio de Janeiro",
            "Brasília",
            "Belo Horizonte"
        ),
        correta = 2
    ),
    Questao(
        pergunta = "Quanto é 2 + 2?",
        alternativas = listOf(
            "3",
            "4",
            "5",
            "6"
        ),
        correta = 1
    ),
    Questao(
        pergunta = "Qual planeta é conhecido como Planeta Vermelho?",
        alternativas = listOf(
            "Vênus",
            "Júpiter",
            "Marte",
            "Saturno"
        ),
        correta = 2
    ),
    Questao(
        pergunta = "Qual linguagem é usada neste projeto?",
        alternativas = listOf(
            "Kotlin",
            "Python",
            "C",
            "JavaScript"
        ),
        correta = 0
    ),
    Questao(
        pergunta = "Quantos lados tem um triângulo?",
        alternativas = listOf(
            "2",
            "3",
            "4",
            "5"
        ),
        correta = 1
    )
)

fun nomeDoCombo(combo: Int): String {
    return when {
        combo >= 5 -> "🔥 LEGENDARY"
        combo == 4 -> "🔥 AMAZING"
        combo == 3 -> "🔥 AWESOME"
        combo == 2 -> "🔥 GREAT"
        combo == 1 -> "🔥 GOOD"
        else -> "COMBO x0"
    }
}

fun xpDoAcerto(combo: Int): Int {
    return when {
        combo >= 5 -> 30
        combo == 4 -> 25
        combo == 3 -> 20
        combo == 2 -> 15
        else -> 10
    }
}

@Composable
fun App() {

    var tela by remember { mutableStateOf("home") }

    var xp by remember { mutableStateOf(0) }
    var combo by remember { mutableStateOf(0) }

    var questaoAtual by remember { mutableStateOf(0) }
    var acertos by remember { mutableStateOf(0) }
    var maiorCombo by remember { mutableStateOf(0) }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = FundoPreto
        ) {

            when (tela) {

                "home" -> {

                    Home(
                        xp = xp,
                        combo = combo,
                        iniciarQuiz = {

                            questaoAtual = 0
                            acertos = 0
                            combo = 0
                            maiorCombo = 0

                            tela = "quiz"
                        }
                    )
                }

                "quiz" -> {

                    TelaQuiz(
                        questao = questoes[questaoAtual],
                        numero = questaoAtual + 1,
                        total = questoes.size,
                        combo = combo,
                        xp = xp,

                        responder = { alternativa ->

                            val correta =
                                alternativa == questoes[questaoAtual].correta

                            if (correta) {

                                combo++

                                acertos++

                                if (combo > maiorCombo) {
                                    maiorCombo = combo
                                }

                                val xpGanho = xpDoAcerto(combo)

                                xp += xpGanho

                            } else {

                                combo = 0
                            }
                        },

                        proxima = {

                            if (questaoAtual < questoes.lastIndex) {

                                questaoAtual++

                            } else {

                                tela = "resultado"
                            }
                        }
                    )
                }

                "resultado" -> {

                    TelaResultado(
                        acertos = acertos,
                        total = questoes.size,
                        xp = xp,
                        maiorCombo = maiorCombo,

                        voltar = {
                            tela = "home"
                        },

                        jogarNovamente = {

                            questaoAtual = 0
                            acertos = 0
                            combo = 0
                            maiorCombo = 0

                            tela = "quiz"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Home(
    xp: Int,
    combo: Int,
    iniciarQuiz: () -> Unit
) {

    val nivel = (xp / 100) + 1
    val xpNivel = xp % 100

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoPreto)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "FACULQUIZ",
            color = TextoBranco,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "⭐ Nível $nivel",
            color = TextoBranco
        )

        Text(
            text = "XP $xpNivel / 100",
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(32.dp))

        BotaoMenu(
            texto = "🎯 Começar Quiz",
            onClick = iniciarQuiz
        )

        Spacer(modifier = Modifier.height(12.dp))

        BotaoMenu(
            texto = "📚 Disciplinas",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(12.dp))

        BotaoMenu(
            texto = "📊 Desempenho",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(12.dp))

        BotaoMenu(
            texto = "🎭 Quiz Temático",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "🔥 Combo: $combo",
            color = TextoBranco
        )
    }
}

@Composable
fun BotaoMenu(
    texto: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BotaoEscuro,
            contentColor = TextoBranco
        )
    ) {

        Text(texto)
    }
}

@Composable
fun TelaQuiz(
    questao: Questao,
    numero: Int,
    total: Int,
    combo: Int,
    xp: Int,
    responder: (Int) -> Unit,
    proxima: () -> Unit
) {

    var respondida by remember(questao) {
        mutableStateOf(false)
    }

    var acertou by remember(questao) {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoPreto)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "QUESTÃO $numero/$total",
            color = TextoSecundario,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "XP: $xp",
            color = TextoBranco
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = nomeDoCombo(combo),
            color = TextoBranco,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "COMBO x$combo",
            color = TextoBranco
        )

        Spacer(modifier = Modifier.height(8.dp))

        val progressoCombo = combo.coerceAtMost(5) / 5f

        LinearProgressIndicator(
            progress = { progressoCombo },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = questao.pergunta,
            color = TextoBranco,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        questao.alternativas.forEachIndexed { index, alternativa ->

            Button(
                onClick = {

                    if (!respondida) {

                        acertou =
                            index == questao.correta

                        responder(index)

                        respondida = true
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .height(52.dp),

                shape = RoundedCornerShape(12.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = BotaoEscuro,
                    contentColor = TextoBranco
                )
            ) {

                Text(
                    "${('A'.code + index).toChar()}) $alternativa"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (respondida) {

            if (acertou) {

                Text(
                    text = "✓ ACERTOU!",
                    color = Verde,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "+${xpDoAcerto(combo)} XP",
                    color = Verde
                )

                Text(
                    text = nomeDoCombo(combo),
                    color = TextoBranco
                )

            } else {

                Text(
                    text = "✗ ERROU!",
                    color = Vermelho,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "🔥 Combo quebrado!",
                    color = TextoBranco
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = proxima,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CartaoEscuro,
                    contentColor = TextoBranco
                )
            ) {

                Text(
                    if (numero == total) {
                        "Ver resultado"
                    } else {
                        "Próxima questão"
                    }
                )
            }
        }
    }
}

@Composable
fun TelaResultado(
    acertos: Int,
    total: Int,
    xp: Int,
    maiorCombo: Int,
    voltar: () -> Unit,
    jogarNovamente: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoPreto)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🏆 QUIZ CONCLUÍDO",
            color = TextoBranco,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ACERTOS",
            color = TextoSecundario
        )

        Text(
            text = "$acertos / $total",
            color = TextoBranco,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "XP",
            color = TextoSecundario
        )

        Text(
            text = "$xp XP",
            color = Verde,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "🔥 MAIOR COMBO",
            color = TextoSecundario
        )

        Text(
            text = "x$maiorCombo",
            color = TextoBranco,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        BotaoMenu(
            texto = "🔄 Jogar novamente",
            onClick = jogarNovamente
        )

        Spacer(modifier = Modifier.height(12.dp))

        BotaoMenu(
            texto = "⌂ Voltar para Home",
            onClick = voltar
        )
    }
}