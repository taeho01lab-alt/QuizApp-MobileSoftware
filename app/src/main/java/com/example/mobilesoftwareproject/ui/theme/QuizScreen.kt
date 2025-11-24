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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilesoftwareproject.R
import com.example.mobilesoftwareproject.data.QuizData
import com.example.mobilesoftwareproject.data.WrongAnswerStore
import com.example.mobilesoftwareproject.model.Question
import com.example.mobilesoftwareproject.model.WrongAnswer

// 메인 퀴즈 화면 Composable
@Composable
fun QuizScreen(
    categoryId: String,
    onGoToMain: () -> Unit,
    onQuizFinished: (score: Int, total: Int) -> Unit
) {
    val questions = remember { QuizData.getQuestions(categoryId) }

    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("이 카테고리에는 문제가 없습니다.")
        }
        return
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    // 각 문제에 대해 사용자가 선택한 답을 저장하는 Map
    val userAnswers = remember { mutableStateMapOf<Int, Int?>() }
    val currentQuestion = questions[currentIndex]
    val categoryTitle = QuizData.getCategoryTitleById(categoryId)

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
        Header(
            categoryTitle = categoryTitle,
            onBackClick = onGoToMain
        )

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
                    .padding(top = 16.dp, bottom = 24.dp)
                    .size(width = 48.dp, height = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        brush = Brush.linearGradient(
                            0f to Color(0xff3550dc),
                            1f to Color(0xff27e9f7)
                        )
                    )
            )

            // 문제 진행도 표시 바
            ProgressIndicator(
                total = questions.size,
                current = currentIndex + 1
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 문제 텍스트
            Text(
                text = currentQuestion.text,
                color = AppColors.color_black,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            // 선택지 목록
            currentQuestion.options.forEachIndexed { index, optionText ->
                OptionItem(
                    optionChar = ('A' + index).toString(),
                    optionText = optionText,
                    isSelected = userAnswers[currentIndex] == index,
                    onClick = { userAnswers[currentIndex] = index }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // 하단 네비게이션 버튼
            BottomNavigation(
                isFirst = currentIndex == 0,
                isLast = currentIndex == questions.lastIndex,
                onPrev = { if (currentIndex > 0) currentIndex-- },
                onNext = { if (currentIndex < questions.lastIndex) currentIndex++ },
                onSubmit = {
                    var score = 0
                    questions.forEachIndexed { index, question ->
                        val userAnswerIndex = userAnswers[index]
                        if (userAnswerIndex == question.answerIndex) {
                            score++
                        } else if (userAnswerIndex != null) {
                            // 오답인 경우 기록
                            WrongAnswerStore.addWrongAnswer(
                                WrongAnswer(
                                    question = question,
                                    myAnswerIndex = userAnswerIndex,
                                    correctIndex = question.answerIndex,
                                    categoryId = categoryId
                                )
                            )
                        }
                    }
                    onQuizFinished(score, questions.size)
                }
            )
            Spacer(modifier = Modifier.height(34.dp))
        }
    }
}

// 상단 헤더 Composable
@Composable
private fun Header(categoryTitle: String, onBackClick: () -> Unit) {
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
                .clickable(onClick = onBackClick)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = categoryTitle,
            color = Color.White,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
private fun ProgressIndicator(total: Int, current: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        // 실제 문제 수 만큼만 동그라미를 그림
        repeat(total) { index ->
            val number = index + 1
            val isActive = number == current

            // 원과 밑줄을 묶는 Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp) // 원과 밑줄 사이의 간격
            ) {
                // 숫자 원
                val circleBrush = if (isActive) {
                    Brush.linearGradient(
                        0f to Color(0xff3550dc),
                        1f to Color(0xff27e9f7)
                    )
                } else {
                    SolidColor(AppColors.color_d4)
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(brush = circleBrush),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toString(),
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    )
                }

                // 개별 밑줄
                val underlineBrush = if (isActive) {
                    Brush.linearGradient(
                        0f to Color(0xff3550dc),
                        1f to Color(0xff27e9f7)
                    )
                } else {
                    // 비활성 상태에서는 회색 밑줄
                    SolidColor(AppColors.color_d4)
                }

                Box(
                    modifier = Modifier
                        .size(width = 46.dp, height = 2.dp)
                        .background(brush = underlineBrush)
                )
            }
        }
    }
}



// 선택지 아이템 Composable
@Composable
private fun OptionItem(
    optionChar: String,
    optionText: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundBrush = if (isSelected) {
        Brush.linearGradient(
            0f to Color(0xff3550dc),
            1f to Color(0xff27e9f7)
        )
    } else {
        SolidColor(AppColors.color_d4)
    }
    val textColor = if (isSelected) AppColors.color_1 else AppColors.color_black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(brush = backgroundBrush),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = optionChar,
                color = Color.White,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = optionText,
            color = textColor,
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

// 하단 네비게이션 Composable
@Composable
private fun BottomNavigation(
    isFirst: Boolean,
    isLast: Boolean,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onSubmit: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 이전 버튼
        NavArrowButton(
            iconId = R.drawable.vector,
            onClick = onPrev,
            enabled = !isFirst,
            rotation = -180f
        )

        // 제출 버튼 (마지막 문제에서만 보임)
        if (isLast) {
            SubmitButton(onClick = onSubmit)
        }

        // 다음 버튼
        NavArrowButton(
            iconId = R.drawable.vector,
            onClick = onNext,
            enabled = !isLast
        )
    }
}

// 이전/다음 화살표 버튼 Composable
@Composable
private fun NavArrowButton(
    iconId: Int,
    onClick: () -> Unit,
    enabled: Boolean,
    rotation: Float = 0f
) {
    val backgroundBrush = if (enabled) {
        Brush.linearGradient(
            0f to Color(0xff3550dc),
            1f to Color(0xff27e9f7)
        )
    } else {
        SolidColor(AppColors.color_d4)
    }

    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(brush = backgroundBrush)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "Arrow",
            modifier = Modifier.rotate(rotation)
        )
    }
}

// 제출 버튼 Composable
@Composable
private fun SubmitButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .requiredWidth(195.dp)
            .requiredHeight(50.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(
                border = BorderStroke(1.dp, AppColors.color_1),
                shape = RoundedCornerShape(5.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "제출",
            color = AppColors.color_1,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
        )
    }
}

// 디자인 프리뷰
@Preview(showBackground = true)
@Composable
private fun QuizScreenPreview() {
    MobileSoftWareProjectTheme {
        QuizScreen(
            categoryId = "movie",
            onGoToMain = {},
            onQuizFinished = { _, _ -> }
        )
    }
}

