package com.example.mobilesoftwareproject.ui.theme

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilesoftwareproject.data.QuizData

@Composable
fun CategoryScreen(
    onStartQuiz: (String) -> Unit,
    onShowRanking: (String) -> Unit,
    onShowWrongNote: (String) -> Unit
) {
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }

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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 상단 "퀴즈 카테고리" 텍스트
            Text(
                text = "퀴즈 카테고리",
                color = Color.White,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 114.dp, start = 24.dp, bottom = 80.dp)
            )

            // 하단 흰색 컨테이너
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight() // 남은 공간을 모두 차지
                    .clip(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(AppColors.color_white)
                    .padding(horizontal = 24.dp), // 좌우 여백
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 상단 손잡이 모양 바
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 24.dp)
                        .width(48.dp)
                        .height(4.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(
                            brush = Brush.linearGradient(
                                0f to Color(0xff3550dc),
                                1f to Color(0xff27e9f7),
                                start = Offset(-17.22f, -1.42f),
                                end = Offset(82.62f, 6.95f)
                            )
                        )
                )

                // "Quiz" 텍스트와 하단 바
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Quiz",
                            color = AppColors.color_1,
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Divider(
                            modifier = Modifier
                                .padding(top = 2.dp, start = 5.dp)
                                .width(20.dp),
                            thickness = 2.dp,
                            color = AppColors.color_1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 카테고리 목록
                LazyColumn(
                    modifier = Modifier.weight(1f), // 버튼을 제외한 남은 공간을 모두 차지
                    verticalArrangement = Arrangement.spacedBy(16.dp) // 아이템 사이의 간격
                ) {
                    items(QuizData.categories) { category ->
                        val (categoryId, categoryName) = category
                        val isSelected = selectedCategoryId == categoryId
                        val questionCount = QuizData.questions.count { it.categoryId == categoryId }

                        CategoryCard(
                            name = categoryName,
                            questionCount = questionCount,
                            isSelected = isSelected,
                            onClick = { selectedCategoryId = categoryId }
                        )
                    }
                }

                // 하단 버튼 영역
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BottomButton(
                        text = "오답 노트",
                        onClick = { selectedCategoryId?.let { onShowWrongNote(it) } },
                        enabled = selectedCategoryId != null,
                        modifier = Modifier.weight(1f)
                    )
                    BottomButton(
                        text = "랭킹 보기",
                        onClick = { selectedCategoryId?.let { onShowRanking(it) } },
                        enabled = selectedCategoryId != null,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 퀴즈 시작 버튼
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(shape = RoundedCornerShape(5.dp))
                        .background(
                            brush = if (selectedCategoryId != null) {
                                Brush.linearGradient(
                                    0f to Color(0xff3550dc),
                                    1f to Color(0xff27e9f7)
                                )
                            } else {
                                SolidColor(Color.Gray)
                            }
                        )
                        .clickable(enabled = selectedCategoryId != null) {
                            selectedCategoryId?.let { onStartQuiz(it) }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "퀴즈 시작",
                        color = AppColors.color_white,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Spacer(modifier = Modifier.height(34.dp)) // 하단 여백
            }
        }
    }
}

// 재사용을 위해 카테고리 카드를 별도의 Composable로 분리
@Composable
fun CategoryCard(
    name: String,
    questionCount: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(5.dp)) // shadow를 먼저 적용
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = AppColors.color_white)
            .border(
                border = if (isSelected) BorderStroke(2.dp, AppColors.color_1) else BorderStroke(
                    0.dp,
                    Color.Transparent
                ),
                shape = RoundedCornerShape(5.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                color = AppColors.color_1,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$questionCount 문제",
                color = Color(0xff999999),
                style = TextStyle(fontSize = 14.sp),
            )
        }
    }
}

// 재사용을 위해 하단 버튼을 별도의 Composable로 분리
@Composable
fun BottomButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = if (enabled) Color(0xff333333) else Color.Gray)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = AppColors.color_white,
            style = TextStyle(fontSize = 12.sp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    MobileSoftWareProjectTheme {
        CategoryScreen(
            onStartQuiz = {},
            onShowRanking = {},
            onShowWrongNote = {}
        )
    }
}