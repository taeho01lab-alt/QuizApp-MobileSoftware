package com.example.mobilesoftwareproject.ui.theme

import android.graphics.Paint
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesoftwareproject.data.QuizData
import com.example.mobilesoftwareproject.model.Question
import com.example.mobilesoftwareproject.model.WrongAnswer
import com.example.mobilesoftwareproject.navigation.Screen
import kotlinx.coroutines.selects.select
import kotlin.math.sin

@Composable
fun WrongNoteScreen(
    wrongAnswer: List<WrongAnswer>,
    nicknames: List<String>,
    selectedName: String?,
    onSelectedName: (String?) -> Unit,
    onDeleteWrongAnswer: (WrongAnswer) -> Unit,
    onBack: () -> Unit // 뒤로 버튼
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // 전체화면 사용
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // 가로방향 가운데 정렬
    ) {
        Text(
            text = "오답 노트",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 왼쪽 닉네임 목록 + 오른쪽 오답 목록
        Row(
            modifier = Modifier
                .weight(1f) // 아래 버튼 제외 모든 공간 사용
                .fillMaxWidth()
        ) {
            // ===== 왼쪽 : 닉네임 목록 =====
            Column(
                modifier = Modifier
                    .weight(0.35f) // 전체폭의 35% 정도 사용
                    .fillMaxHeight() // 세로 전체 사용
            ) {
                Text(
                    text = "닉네임 목록",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { onSelectedName(null) },  // 전체 보기 (닉네임 필터 해제)
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "전체 보기")
                }
                Spacer(modifier = Modifier.height(8.dp))

                // 실제 닉네임 리스트
                LazyColumn(
                    modifier = Modifier.fillMaxSize(), // 영역 다 사용
                    verticalArrangement = Arrangement.spacedBy(4.dp) // 항목 사이 간격
                ) {
                    items(nicknames) { name ->
                        val isSelected = (name == selectedName)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectedName(name) },
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                            )
                        ) {
                            Text(
                                text = name,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            //오른쪽 오답 목록
            Column(
                modifier = Modifier
                    .weight(0.65f)       // 나머지 65% 사용
                    .fillMaxHeight()
            ) {
                val title = when {
                    selectedName == null -> "전체 오답 목록"
                    else -> "[$selectedName]님의 오답 목록"
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (wrongAnswer.isEmpty()) {
                    // 현재 필터에 해당하는 오답이 없을 때
                    Text(
                        text = "오답이 없습니다.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(wrongAnswer) { item ->
                            wrongAnswerCard(
                                item = item,
                                onDelete = { onDeleteWrongAnswer(item) } // 삭제 버튼 누르면 콜백
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 아래쪽 뒤로가기 버튼
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "결과 화면으로")
        }
    }
}

@Composable
fun wrongAnswerCard(
    item: WrongAnswer,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "문제: ${item.question.text}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "내 답: ${item.question.options[item.myAnswerIndex]}")
            Text(text = "정답: ${item.question.options[item.correctIndex]}")

            item.userName?.let { name ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "닉네임: $name",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDelete,                     //삭제 콜백 실행
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("이 오답 삭제")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun WrongNoteScreenPreview() {
    // QuizData 에 최소 2문제 있다고 가정
    val sampleQuestion: Question = QuizData.questions.first()
    val sampleQuestion2: Question = QuizData.questions.last()

    val sampleWrong = listOf(
        WrongAnswer(
            question = sampleQuestion,
            myAnswerIndex = 1,
            correctIndex = sampleQuestion.answerIndex,
            categoryId = sampleQuestion.categoryId,
            userName = "익명1"
        ),
        WrongAnswer(
            question = sampleQuestion2,
            myAnswerIndex = 2,
            correctIndex = sampleQuestion2.answerIndex,
            categoryId = sampleQuestion2.categoryId,
            userName = "익명2"
        )
    )
    val sampleNicknames = listOf("익명1", "익명2")

    MobileSoftWareProjectTheme {
        WrongNoteScreen(
            wrongAnswer = sampleWrong,
            nicknames = sampleNicknames,
            selectedName = "익명1",
            onSelectedName = {},
            onDeleteWrongAnswer = {},
            onBack = {}
        )
    }
}
