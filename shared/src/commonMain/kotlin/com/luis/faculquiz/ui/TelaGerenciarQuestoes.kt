package com.luis.faculquiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
fun TelaGerenciarQuestoes(
    onVoltar: () -> Unit,
    onCriarNovaQuestao: () -> Unit,
    onGerenciarDisciplinas: () -> Unit
) {
    val questoes = remember { mutableStateListOf<Questao>().apply { addAll(RepositorioQuestoes.todas()) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(16.dp)
    ) {
        Text(
            text = "Gerenciador de Questões",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onGerenciarDisciplinas,
            modifier = Modifier.fillMaxWidth().height(45.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF151515))
        ) {
            Text("📚 GERENCIAR DISCIPLINAS", color = Color(0xFF40C4FF), fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onCriarNovaQuestao,
            modifier = Modifier.fillMaxWidth().height(45.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C1C1C))
        ) {
            Text("+ CRIAR NOVA QUESTÃO", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            if (questoes.isEmpty()) {
                Text("Nenhuma questão cadastrada.", color = Color(0xFF666666), modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(questoes) { questao ->
                        ItemQuestaoSimples(
                            questao = questao,
                            onExcluir = {
                                RepositorioQuestoes.remover(questao.id)
                                questoes.remove(questao)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = onVoltar,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF151515))
        ) {
            Text("VOLTAR PARA HOME", color = Color(0xFFAAAAAA), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ItemQuestaoSimples(questao: Questao, onExcluir: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF101010), shape = RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF151515), shape = RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = questao.disciplina.uppercase(),
                color = Color(0xFF40C4FF),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = questao.dificuldade.name,
                color = when (questao.dificuldade) {
                    Dificuldade.FACIL -> Color(0xFF00E676)
                    Dificuldade.MEDIO -> Color(0xFFFFD740)
                    Dificuldade.DIFICIL -> Color(0xFFFF5252)
                },
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = questao.pergunta, color = Color.White, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onExcluir,
            modifier = Modifier.fillMaxWidth().height(40.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A1010)
            )
        ) {
            Text(
                "EXCLUIR QUESTÃO",
                color = Color(0xFFFF5252),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}
