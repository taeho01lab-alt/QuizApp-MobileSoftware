package com.example.mobilesoftwareproject.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilesoftwareproject.R
import com.example.mobilesoftwareproject.data.QuizData
import com.example.mobilesoftwareproject.model.Question
import com.example.mobilesoftwareproject.model.WrongAnswer

@Composable
fun WrongNoteScreen(
    wrongAnswer: List<WrongAnswer>,
    nicknames: List<String>,
    selectedName: String?,
    onSelectedName: (String?) -> Unit,
    onDeleteWrongAnswer: (WrongAnswer) -> Unit,
    onBack: () -> Unit // 뒤로가기
) {
    // 전체 배경
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    0f to Color(0xff3550dc),
                    1f to Color(0xff27e9f7),
                    start = Offset(-134.5f, -288.26f),
                    end = Offset(645.5f, 1410.17f)
                )
            )
    ) {
        // 상단 헤더
        Row(
            modifier = Modifier
                .padding(top = 60.dp, start = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_43),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onBack) // 뒤로가기 기능 연결
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "오답 노트",
                color = Color.White,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
        }

        // 하얀색 메인 컨테이너
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .requiredHeight(760.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 상단 손잡이 모양 바
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(width = 48.dp, height = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        brush = Brush.linearGradient(
                            0f to Color(0xff3550dc),
                            1f to Color(0xff27e9f7)
                        )
                    )
            )

            // 왼쪽 닉네임 목록 + 오른쪽 오답 목록
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                // 왼쪽 : 닉네임 목록
                Column(
                    modifier = Modifier
                        .weight(0.35f)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "닉네임 목록",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                width = 1.dp,
                                color = AppColors.color_1,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(color = AppColors.color_white)
                            .clickable { onSelectedName(null) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "전체 보기",
                            color = AppColors.color_1,
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // 실제 닉네임 리스트
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
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
                                            AppColors.color_d4
                                        }
                                )
                            ) {
                                Text(
                                    text = name,
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // 오른쪽 오답 목록
                Column(
                    modifier = Modifier
                        .weight(0.65f)
                        .fillMaxHeight()
                ) {
                    val title = when {
                        selectedName == null -> "전체 오답 목록"
                        else -> "[$selectedName]님의 오답 목록"
                    }
                    Text(
                        text = title,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // 오답이 없을 때와 있을 때의 리스트
                    if (wrongAnswer.isEmpty()) {
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
                                WrongAnswerCard(
                                    item = item,
                                    onDelete = { onDeleteWrongAnswer(item) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WrongAnswerCard(
    item: WrongAnswer,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "문제: ${item.question.text}",
                style = TextStyle(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "내 답: ${item.question.options[item.myAnswerIndex]}",
                style = TextStyle(fontSize = 14.sp))
            Text(text = "정답: ${item.question.options[item.correctIndex]}",
                style = TextStyle(fontSize = 14.sp))

            item.userName?.let { name ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "닉네임: $name",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, AppColors.color_1),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    "이 오답 삭제",
                    color = AppColors.color_1,
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun WrongNoteScreenPreview() {
    val sampleQuestion: Question = QuizData.getQuestions("common_sense").first()
    val sampleQuestion2: Question = QuizData.getQuestions("history").last()

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