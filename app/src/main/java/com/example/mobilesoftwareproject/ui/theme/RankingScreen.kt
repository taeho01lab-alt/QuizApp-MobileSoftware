package com.example.mobilesoftwareproject.ui.theme

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.mobilesoftwareproject.model.Ranking

@Composable
fun RankingScreen( //랭킹 화면
    ranking: List<Ranking>,
    onBack: () -> Unit //뒤로가기 버튼을 눌렀을 때 실행되는 콜백 함수
) {
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
                .padding(top = 60.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
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
                text = "랭킹 리스트",
                color = Color.White,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
        }

        // 하얀색 메인 컨테이너
        Column(
            modifier = Modifier
                .padding(top = 104.dp) // 상단 헤더 아래에 위치
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White),
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

            // 랭킹 리스트
            if (ranking.isEmpty()) {
                Text(
                    text = "유저 기록이 존재하지 않습니다.",
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                    modifier = Modifier.padding(top = 32.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(ranking) { index, item ->
                        RankingRow(
                            rank = index + 1,
                            item = item
                        )
                    }
                }
            }
        }
    }
}

//랭킹 하나 표시
@Composable
fun RankingRow(rank: Int, item: Ranking) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(92.dp)
    ) {
        // 흰색 배경 카드
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = AppColors.color_white)
                .border(
                    width = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(20.dp)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 랭크 숫자 원
            Box(
                modifier = Modifier
                    .size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(shape = CircleShape)
                        .border(
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                AppColors.color_Neutral_Grey_4
                            ),
                            shape = CircleShape
                        )
                )
                Text(
                    text = "$rank",
                    color = AppColors.color_Neutral_Grey_2,
                    textAlign = TextAlign.Center,
                    style = AppTypes.type_Body_XSmall_Medium,
                )
            }

            Spacer(modifier = Modifier.size(12.dp))


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        color = AppColors.color_Neutral_Black,
                        style = AppTypes.type_Body_Normal_Medium
                    )
                    Spacer(modifier = Modifier.size(8.dp)) // 이름과 날짜 사이 간격
                    Text(
                        text = "[${item.date}]",
                        color = AppColors.color_Neutral_Grey_2,
                        style = AppTypes.type_Body_Small_Regular,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "점수: ${item.score}/${item.total}",
                    color = AppColors.color_Neutral_Grey_2,
                    style = AppTypes.type_Body_Small_Regular,
                )
            }
        }

        // 1, 2, 3위 왕관 아이콘
        when (rank) {
            1 -> Property1Gold(modifier = Modifier.align(Alignment.CenterEnd))
            2 -> Property1Silver(modifier = Modifier.align(Alignment.CenterEnd))
            3 -> Property1Bronze(modifier = Modifier.align(Alignment.CenterEnd))
        }
    }
}

@Composable
fun Property1Gold(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(end = 16.dp)
            .size(40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color(0xffffd54b))
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = "Gold Crown",
            tint= Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        )
    }
}

@Composable
fun Property1Silver(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(end = 16.dp)
            .size(40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color(0xffe1e1e2))
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = "Silver Crown",
            tint = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        )
    }
}

@Composable
fun Property1Bronze(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(end = 16.dp)
            .size(40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color(0xfff6b191))
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = "Bronze Crown",
            tint = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RankingScreenPreview() {
    val sampledata = listOf(
        Ranking("사용자1", 5, 5, categoryId = "movie", date = "2025-11-16"),
        Ranking("사용자2", 4, 5, categoryId = "movie", date = "2025-11-15"),
        Ranking("사용자3", 3, 5, categoryId = "movie", date = "2025-11-14"),
        Ranking("사용자4", 2, 5, categoryId = "movie", date = "2025-11-14"),
        Ranking("사용자5", 2, 5, categoryId = "movie", date = "2025-11-16"),
    )
    MobileSoftWareProjectTheme {
        RankingScreen(
            ranking = sampledata,
            onBack = {}
        )
    }
}
