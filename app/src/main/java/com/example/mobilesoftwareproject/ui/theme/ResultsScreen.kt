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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilesoftwareproject.R
import com.example.mobilesoftwareproject.data.QuizData

@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    categoryId: String,
    onGoHome: () -> Unit,
    onShowWrongNote: () -> Unit = {},
    onSaveRanking: (name: String) -> Unit = {}
) {
    val wrongCount = total - score
    var name by remember { mutableStateOf("") }
    var saveCompleted by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val categoryName = QuizData.getCategoryTitleById(categoryId)
    val questionCount = QuizData.getQuestions(categoryId).size

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
        Column(modifier = Modifier.fillMaxSize()) {
            // 상단 결과 화면 헤더
            Row(
                modifier = Modifier.padding(top = 60.dp, start = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_43),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onGoHome() }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "결과 화면",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            // 퀴즈 카테고리 정보
            Column(modifier = Modifier.padding(start = 24.dp, top = 36.dp, bottom = 16.dp)) {
                Text(
                    text = categoryName, // 위에서 가져온 이름으로 표시
                    color = Color.White,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
                )
                Text(
                    text = "$questionCount 문제", // 위에서 가져온 문제 수로 표시
                    color = Color.White,
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            // 하단 흰색 컨테이너
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(AppColors.color_white)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 상단 손잡이 모양 바
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 24.dp)
                        .size(width = 48.dp, height = 4.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(
                            brush = Brush.linearGradient(
                                0f to Color(0xff3550dc),
                                1f to Color(0xff27e9f7)
                            )
                        )
                )

                // 최종 점수 섹션
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AppColors.color_black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_44),
                            contentDescription = "Score Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text(
                            text = "$score / $total",
                            color = AppColors.color_black,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        )
                        Text(
                            text = "틀린 문제 수: $wrongCount",
                            color = Color(0xff999999),
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))

                // 닉네임 입력 섹션
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "랭킹에 저장할 닉네임을 입력하세요.",
                        color = AppColors.color_black,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    )
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("예: 홍길동") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = AppColors.color_1,
                            unfocusedIndicatorColor = Color.Gray
                        )
                    )
                    if (saveCompleted) {
                        Text(
                            text = "저장이 완료되었습니다.",
                            color = AppColors.color_1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // 버튼 영역
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ResultButton(
                        text = "점수 저장",
                        onClick = {
                            val finalName = if (name.isBlank()) "익명" else name
                            onSaveRanking(finalName)
                            saveCompleted = true
                            keyboardController?.hide()
                        }
                    )
                    ResultButton(
                        text = "오답 노트",
                        onClick = onShowWrongNote
                    )
                    ResultButton(
                        text = "메인 화면",
                        onClick = onGoHome,
                        isPrimary = true
                    )
                }

                Spacer(modifier = Modifier.height(34.dp))
            }
        }
    }
}

// ResultButton 함수
@Composable
fun ResultButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean = false
) {
    val textColor = if (isPrimary) Color.White else AppColors.color_1

    val buttonModifier = if (isPrimary) {
        Modifier.background(
            brush = Brush.linearGradient(
                0f to Color(0xff3550dc),
                1f to Color(0xff27e9f7)
            )
        )
    } else {
        Modifier.border(
            border = BorderStroke(1.dp, AppColors.color_1),
            shape = RoundedCornerShape(5.dp)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(5.dp))
            .then(buttonModifier)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    MobileSoftWareProjectTheme {
        ResultScreen(
            score = 3,
            total = 5,
            categoryId = "movie", // <<< Preview를 위해 실제 categoryId 값 전달
            onGoHome = {},
            onShowWrongNote = {},
            onSaveRanking = { _ -> }
        )
    }
}
