package com.luis.faculquiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luis.faculquiz.data.RepositorioQuestoes

@Composable
fun TelaGerenciarDisciplinas(
    onVoltar: () -> Unit
) {
    var novoNomeDisciplina by remember { mutableStateOf("") }
    
    // Lista observável que reage em tempo real a adições e remoções na tela
    val listaDisciplinas = remember { mutableStateListOf<String>().apply { addAll(RepositorioQuestoes.disciplinas()) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505)) // Fundo Preto AMOLED
            .padding(16.dp)
    ) {
        Text(
            text = "Gerenciar Disciplinas",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CampoLabel("Nome da Nova Disciplina")
        FormTextField(
            value = novoNomeDisciplina,
            onValueChange = { novoNomeDisciplina = it },
            placeholder = "Ex: Física Quântica, Estrutura de Dados..."
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val tratada = novoNomeDisciplina.trim()
                if (tratada.isNotBlank() && !listaDisciplinas.contains(tratada)) {
                    RepositorioQuestoes.adicionarDisciplina(tratada)
                    listaDisciplinas.add(tratada)
                    novoNomeDisciplina = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C1C1C))
        ) {
            Text("+ ADICIONAR DISCIPLINA", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        CampoLabel("Disciplinas Cadastradas")

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaDisciplinas) { disciplina ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF101010), shape = RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFF151515), shape = RoundedCornerShape(10.dp))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "📚",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Text(
                            text = disciplina,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Botão Vermelho de Deletar / Excluir
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFFF5252).copy(alpha = 0.15f), shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFFF5252).copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp))
                            .clickable {
                                RepositorioQuestoes.removerDisciplina(disciplina)
                                listaDisciplinas.remove(disciplina) // Remove da tela instantaneamente
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✕",
                            color = Color(0xFFFF5252), // Vermelho para Erros/Exclusões
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onVoltar,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF151515))
        ) {
            Text("VOLTAR", color = Color(0xFFAAAAAA), fontWeight = FontWeight.Bold)
        }
    }
}
