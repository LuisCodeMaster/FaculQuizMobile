package com.luis.faculquiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// ============================================================
// CORES
// ============================================================

private val FundoPreto = Color(0xFF050505)
private val FundoCard = Color(0xFF101010)
private val FundoCard2 = Color(0xFF151515)
private val Botao = Color(0xFF1C1C1C)

private val Branco = Color(0xFFFFFFFF)
private val Cinza = Color(0xFFAAAAAA)
private val CinzaEscuro = Color(0xFF666666)

private val Verde = Color(0xFF00E676)
private val Vermelho = Color(0xFFFF5252)
private val Amarelo = Color(0xFFFFD740)
private val Azul = Color(0xFF40C4FF)
private val Roxo = Color(0xFFB388FF)


// ============================================================
// MODELOS
// ============================================================

data class Questao(
    val pergunta: String,
    val alternativas: List<String>,
    val correta: Int,
    val dificuldade: String
)

data class Disciplina(
    val nome: String,
    val icone: String,
    val nivel: Int,
    val progresso: Int
)


// ============================================================
// DADOS
// ============================================================

private val disciplinas = listOf(

    Disciplina(
        nome = "Cálculo",
        icone = "📐",
        nivel = 1,
        progresso = 35
    ),

    Disciplina(
        nome = "Programação",
        icone = "💻",
        nivel = 2,
        progresso = 60
    ),

    Disciplina(
        nome = "Física",
        icone = "⚛️",
        nivel = 1,
        progresso = 20
    ),

    Disciplina(
        nome = "Álgebra",
        icone = "📊",
        nivel = 1,
        progresso = 45
    )
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
        correta = 2,
        dificuldade = "Fácil"
    ),

    Questao(
        pergunta = "Quanto é 2 + 2?",
        alternativas = listOf(
            "3",
            "4",
            "5",
            "6"
        ),
        correta = 1,
        dificuldade = "Fácil"
    ),

    Questao(
        pergunta = "Qual planeta é conhecido como Planeta Vermelho?",
        alternativas = listOf(
            "Vênus",
            "Júpiter",
            "Marte",
            "Saturno"
        ),
        correta = 2,
        dificuldade = "Fácil"
    ),

    Questao(
        pergunta = "Qual linguagem é usada neste projeto?",
        alternativas = listOf(
            "Kotlin",
            "Python",
            "C",
            "JavaScript"
        ),
        correta = 0,
        dificuldade = "Médio"
    ),

    Questao(
        pergunta = "Quantos lados tem um triângulo?",
        alternativas = listOf(
            "2",
            "3",
            "4",
            "5"
        ),
        correta = 1,
        dificuldade = "Fácil"
    )
)


// ============================================================
// XP
// ============================================================

fun xpDoAcerto(
    combo: Int,
    dificuldade: String
): Int {

    val base = when (dificuldade) {

        "Médio" -> 20
        "Difícil" -> 35
        "Insano" -> 50

        else -> 10
    }

    val bonus = when {

        combo >= 5 -> 20
        combo >= 4 -> 15
        combo >= 3 -> 10
        combo >= 2 -> 5

        else -> 0
    }

    return base + bonus
}


// ============================================================
// NOME DO COMBO
// ============================================================

fun nomeDoCombo(combo: Int): String {

    return when {

        combo >= 5 -> "LEGENDARY"
        combo == 4 -> "AMAZING"
        combo == 3 -> "AWESOME"
        combo == 2 -> "GREAT"
        combo == 1 -> "GOOD"

        else -> "COMBO x0"
    }
}


// ============================================================
// APP PRINCIPAL
// ============================================================

@Composable
fun App() {

    var tela by remember {
        mutableStateOf("home")
    }

    var disciplinaSelecionada by remember {
        mutableStateOf<Disciplina?>(null)
    }

    var xp by remember {
        mutableStateOf(0)
    }

    var combo by remember {
        mutableStateOf(0)
    }

    var maiorCombo by remember {
        mutableStateOf(0)
    }

    var questaoAtual by remember {
        mutableStateOf(0)
    }

    var acertos by remember {
        mutableStateOf(0)
    }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = FundoPreto
        ) {

            when (tela) {

                "home" -> {

                    TelaHome(

                        xp = xp,

                        combo = combo,

                        irDisciplinas = {
                            tela = "disciplinas"
                        }
                    )
                }

                "disciplinas" -> {

                    TelaDisciplinas(

                        selecionar = { disciplina ->

                            disciplinaSelecionada = disciplina

                            questaoAtual = 0
                            acertos = 0
                            combo = 0
                            maiorCombo = 0

                            tela = "quiz"
                        },

                        voltar = {
                            tela = "home"
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

                                xp += xpDoAcerto(
                                    combo,
                                    questoes[questaoAtual].dificuldade
                                )

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

                        jogarNovamente = {

                            questaoAtual = 0
                            acertos = 0
                            combo = 0
                            maiorCombo = 0

                            tela = "quiz"
                        },

                        voltar = {
                            tela = "home"
                        }
                    )
                }
            }
        }
    }
}


// ============================================================
// HOME
// ============================================================

@Composable
fun TelaHome(
    xp: Int,
    combo: Int,
    irDisciplinas: () -> Unit
) {

    val nivel = (xp / 100) + 1

    val xpAtual = xp % 100

    val progresso = xpAtual / 100f

    LazyColumn(

        modifier = Modifier
            .fillMaxSize()
            .background(FundoPreto)
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Text(
                text = "FACULQUIZ",
                color = Branco,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Black
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "APRENDA • JOGUE • EVOLUA",
                color = Cinza,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )


            // CARD DO JOGADOR

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        FundoCard,
                        RoundedCornerShape(20.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFF242424),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {

                Column {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "SEU NÍVEL",
                                color = Cinza,
                                style = MaterialTheme.typography.labelMedium
                            )

                            Text(
                                text = "⭐ Nível $nivel",
                                color = Branco,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {

                            Text(
                                text = "$xpAtual / 100 XP",
                                color = Verde,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    LinearProgressIndicator(

                        progress = {
                            progresso
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),

                        color = Verde,
                        trackColor = Color(0xFF242424)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )


            // COMBO

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        FundoCard,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(18.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "🔥",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Column {

                        Text(
                            text = "COMBO ATUAL",
                            color = Cinza,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = "x$combo",
                            color = Branco,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // BOTÃO PRINCIPAL

            Button(

                onClick = irDisciplinas,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Branco,
                    contentColor = Color.Black
                )
            ) {

                Text(
                    text = "🎯  JOGAR AGORA",
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // OUTROS BOTÕES

            BotaoSecundario(
                texto = "📚  Disciplinas",
                onClick = irDisciplinas
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            BotaoSecundario(
                texto = "📊  Meu desempenho",
                onClick = {}
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            BotaoSecundario(
                texto = "🏆  Conquistas",
                onClick = {}
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Text(
                text = "Continue evoluindo.",
                color = CinzaEscuro,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


// ============================================================
// BOTÃO SECUNDÁRIO
// ============================================================

@Composable
fun BotaoSecundario(
    texto: String,
    onClick: () -> Unit
) {

    Button(

        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),

        shape = RoundedCornerShape(15.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Botao,
            contentColor = Branco
        )
    ) {

        Text(
            text = texto,
            fontWeight = FontWeight.SemiBold
        )
    }
}


// ============================================================
// DISCIPLINAS
// ============================================================

@Composable
fun TelaDisciplinas(
    selecionar: (Disciplina) -> Unit,
    voltar: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(FundoPreto)
            .padding(20.dp)
    ) {

        Text(
            text = "DISCIPLINAS",
            color = Branco,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Escolha onde você quer evoluir.",
            color = Cinza
        )

        Spacer(
            modifier = Modifier.height(22.dp)
        )

        LazyColumn {

            items(disciplinas) { disciplina ->

                CardDisciplina(
                    disciplina = disciplina,
                    selecionar = {
                        selecionar(disciplina)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            item {

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                BotaoSecundario(
                    texto = "← Voltar",
                    onClick = voltar
                )
            }
        }
    }
}


// ============================================================
// CARD DISCIPLINA
// ============================================================

@Composable
fun CardDisciplina(
    disciplina: Disciplina,
    selecionar: () -> Unit
) {

    Button(

        onClick = selecionar,

        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp),

        shape = RoundedCornerShape(18.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = FundoCard,
            contentColor = Branco
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = disciplina.icone,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = disciplina.nome,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Nível ${disciplina.nivel}",
                    color = Cinza,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                LinearProgressIndicator(

                    progress = {
                        disciplina.progresso / 100f
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),

                    color = Azul,
                    trackColor = Color(0xFF292929)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "${disciplina.progresso}%",
                color = Azul,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ============================================================
// QUIZ
// ============================================================

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

    val progresso = numero / total.toFloat()

    LazyColumn(

        modifier = Modifier
            .fillMaxSize()
            .background(FundoPreto)
            .padding(20.dp)
    ) {

        item {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "QUESTÃO $numero/$total",
                    color = Cinza,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "$xp XP",
                    color = Verde,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            LinearProgressIndicator(

                progress = {
                    progresso
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),

                color = Azul,
                trackColor = Color(0xFF242424)
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // COMBO

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        FundoCard,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "🔥",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Column {

                        Text(
                            text = nomeDoCombo(combo),
                            color = Branco,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Combo x$combo",
                            color = Cinza
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // DIFICULDADE

            Text(
                text = "DIFICULDADE • ${questao.dificuldade.uppercase()}",
                color = when (questao.dificuldade) {
                    "Médio" -> Amarelo
                    "Difícil" -> Vermelho
                    "Insano" -> Roxo
                    else -> Verde
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // PERGUNTA

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        FundoCard,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(22.dp)
            ) {

                Text(
                    text = questao.pergunta,
                    color = Branco,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )
        }


        items(
            questao.alternativas.indices.toList()
        ) { index ->

            val alternativa =
                questao.alternativas[index]

            val letra =
                ('A'.code + index).toChar()

            val cor = when {

                !respondida ->
                    Botao

                index == questao.correta ->
                    Verde

                else ->
                    Botao
            }

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
                    .height(58.dp),

                shape = RoundedCornerShape(15.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = cor,
                    contentColor = if (
                        respondida &&
                        index == questao.correta
                    ) {
                        Color.Black
                    } else {
                        Branco
                    }
                )
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "$letra",
                        fontWeight = FontWeight.Black
                    )

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Text(
                        text = alternativa
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }


        item {

            if (respondida) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (acertou) {
                                Color(0xFF082B1A)
                            } else {
                                Color(0xFF2A0B0B)
                            },
                            RoundedCornerShape(18.dp)
                        )
                        .padding(18.dp)
                ) {

                    Column {

                        Text(
                            text = if (acertou) {
                                "✓ ACERTOU!"
                            } else {
                                "✗ ERROU!"
                            },
                            color = if (acertou) {
                                Verde
                            } else {
                                Vermelho
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = if (acertou) {
                                "+XP recebido • Combo aumentado!"
                            } else {
                                "Seu combo foi quebrado."
                            },
                            color = Branco
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Button(

                    onClick = proxima,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(15.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Branco,
                        contentColor = Color.Black
                    )
                ) {

                    Text(
                        text = if (numero == total) {
                            "🏆 VER RESULTADO"
                        } else {
                            "→ PRÓXIMA QUESTÃO"
                        },
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}


// ============================================================
// RESULTADO
// ============================================================

@Composable
fun TelaResultado(
    acertos: Int,
    total: Int,
    xp: Int,
    maiorCombo: Int,
    jogarNovamente: () -> Unit,
    voltar: () -> Unit
) {

    val porcentagem =
        (acertos * 100) / total

    val rank = when {

        porcentagem >= 90 -> "S"
        porcentagem >= 80 -> "A"
        porcentagem >= 70 -> "B"
        porcentagem >= 60 -> "C"
        else -> "D"
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
            text = "QUIZ CONCLUÍDO",
            color = Cinza,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "🏆",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "RANK $rank",
            color = when (rank) {
                "S" -> Amarelo
                "A" -> Verde
                "B" -> Azul
                else -> Branco
            },
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Black
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "$porcentagem% DE ACERTO",
            color = Branco,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // ESTATÍSTICAS

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Estatistica(
                titulo = "ACERTOS",
                valor = "$acertos/$total",
                modifier = Modifier.weight(1f)
            )

            Estatistica(
                titulo = "XP",
                valor = "$xp",
                modifier = Modifier.weight(1f)
            )

            Estatistica(
                titulo = "COMBO",
                valor = "x$maiorCombo",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Button(

            onClick = jogarNovamente,

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),

            shape = RoundedCornerShape(16.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Branco,
                contentColor = Color.Black
            )
        ) {

            Text(
                text = "🔄 JOGAR NOVAMENTE",
                fontWeight = FontWeight.Black
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        BotaoSecundario(
            texto = "⌂ VOLTAR PARA HOME",
            onClick = voltar
        )
    }
}


// ============================================================
// ESTATÍSTICA
// ============================================================

@Composable
fun Estatistica(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {

    Box(

        modifier = modifier
            .height(95.dp)
            .background(
                FundoCard,
                RoundedCornerShape(16.dp)
            )
            .padding(12.dp),

        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = titulo,
                color = Cinza,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = valor,
                color = Branco,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )
        }
    }
}