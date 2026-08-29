package com.luis.faculquiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luis.faculquiz.data.RepositorioQuestoes
import com.luis.faculquiz.model.Dificuldade
import com.luis.faculquiz.model.Questao

@Composable
fun TelaCriarQuestao(
    onVoltar: () -> Unit,
    onSalvo: () -> Unit
) {
    val listaDisciplinasDisponiveis = RepositorioQuestoes.disciplinas()
    var indiceDisciplinaSelecionada by remember { mutableStateOf(0) }
    val disciplina = listaDisciplinasDisponiveis.getOrNull(indiceDisciplinaSelecionada) ?: "Geral"
    
    var assunto by remember { mutableStateOf("") }
    var pergunta by remember { mutableStateOf("") }
    var altA by remember { mutableStateOf("") }
    var altB by remember { mutableStateOf("") }
    var altC by remember { mutableStateOf("") }
    var altD by remember { mutableStateOf("") }
    var respostaCorretaIndice by remember { mutableStateOf(0) }
    var dificuldade by remember { mutableStateOf(Dificuldade.FACIL) }
    var explicacao by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Nova Questão",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CampoLabel("Disciplina")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF101010), shape = RoundedCornerShape(8.dp))
                .padding(14.dp)
                .clickable {
                    if (listaDisciplinasDisponiveis.isNotEmpty()) {
                        indiceDisciplinaSelecionada = (indiceDisciplinaSelecionada + 1) % listaDisciplinasDisponiveis.size
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = disciplina, color = Color.White)
            Text(text = "▼ Alternar", color = Color(0xFF40C4FF), fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        CampoLabel("Assunto")
        FormTextField(value = assunto, onValueChange = { assunto = it }, placeholder = "Ex: Derivadas")

        Spacer(modifier = Modifier.height(12.dp))

        CampoLabel("Pergunta")
        FormTextField(value = pergunta, onValueChange = { pergunta = it }, placeholder = "Digite o enunciado da questão...")

        Spacer(modifier = Modifier.height(12.dp))

        CampoLabel("Alternativa A")
        FormTextField(value = altA, onValueChange = { altA = it }, placeholder = "Texto da alternativa A")

        Spacer(modifier = Modifier.height(8.dp))

        CampoLabel("Alternativa B")
        FormTextField(value = altB, onValueChange = { altB = it }, placeholder = "Texto da alternativa B")

        Spacer(modifier = Modifier.height(8.dp))

        CampoLabel("Alternativa C")
        FormTextField(value = altC, onValueChange = { altC = it }, placeholder = "Texto da alternativa C")

        Spacer(modifier = Modifier.height(8.dp))

        CampoLabel("Alternativa D")
        FormTextField(value = altD, onValueChange = { altD = it }, placeholder = "Texto da alternativa D")

        Spacer(modifier = Modifier.height(12.dp))

        CampoLabel("Resposta Correta")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val alternativasLetras = listOf("A", "B", "C", "D")
            alternativasLetras.forEachIndexed { index, letra ->
                val selecionado = respostaCorretaIndice == index
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                        .background(
                            if (selecionado) Color(0xFF00E676) else Color(0xFF101010),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { respostaCorretaIndice = index },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = letra,
                        color = if (selecionado) Color.Black else Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        CampoLabel("Dificuldade")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Dificuldade.values().forEach { d ->
                val selecionado = dificuldade == d
                val corBotao = when (d) {
                    Dificuldade.FACIL -> Color(0xFF00E676)
                    Dificuldade.MEDIO -> Color(0xFFFFD740)
                    Dificuldade.DIFICIL -> Color(0xFFFF5252)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                        .background(
                            if (selecionado) corBotao else Color(0xFF101010),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { dificuldade = d },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = d.name,
                        color = if (selecionado) Color.Black else Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        CampoLabel("Explicação (Opcional)")
        FormTextField(value = explicacao, onValueChange = { explicacao = it }, placeholder = "Por que essa resposta é a certa?")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (pergunta.isNotBlank() && altA.isNotBlank() && altB.isNotBlank()) {
                    val novaQuestao = Questao(
                        id = RepositorioQuestoes.todas().size + 1,
                        disciplina = disciplina,
                        assunto = assunto.ifBlank { "Geral" },
                        pergunta = pergunta,
                        alternativas = listOf(altA, altB, altC, altD),
                        correta = respostaCorretaIndice,
                        explicacao = explicacao,
                        dificuldade = dificuldade
                    )
                    RepositorioQuestoes.adicionar(novaQuestao)
                    onSalvo()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676))
        ) {
            Text("SALVAR QUESTÃO", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onVoltar,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C1C1C))
        ) {
            Text("CANCELAR", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CampoLabel(texto: String) {
    Text(
        text = texto,
        color = Color(0xFFAAAAAA),
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color(0xFF666666), fontSize = 14.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF151515), shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF101010),
            unfocusedContainerColor = Color(0xFF101010),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
