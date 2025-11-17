package com.example.mobilesoftwareproject.ui.theme

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesoftwareproject.model.Ranking

@Composable
fun RankingScreen( //랭킹 화면
    ranking:List<Ranking>,
    onBack: () -> Unit //뒤로가기 버튼을 눌렀을 때 실행되는 콜백 함수
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), //Edge와의 간격을 생성
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "랭킹 리스트",
            style= MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.padding(16.dp))

        if(ranking.isEmpty()){
            Text(
                text = "유저 기록이 존재하지 않습니다.",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }else{
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                //Lazy컴포저블 내부에서 리스트의 항목을 화면에 표시하기위해 반복적으로 UI항목 생성 ( ranking을 순회 )
                itemsIndexed(ranking){ //리스트의 내용을 순회하기 위함( 여러 인덱스 가능 )
                    index,item ->
                    RankingRow(
                        rank = index +1,
                        item = item
                    )
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.Center)
                .padding()
        ){
            Text(
                text = "뒤로가기"
            )
        }
    }
}
//랭킹 하나 표시
@Composable
fun RankingRow(rank: Int, item: Ranking){
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "${rank}위",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(end = 20.dp)
            )
            Row(
                modifier =Modifier.weight(1f)
            ){
                Text(
                    text = item.name,
                    modifier = Modifier.padding(end = 20.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "점수 : ${item.score} / ${item.total}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "[날짜 : ${item.date}]",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun RankingScreenPreview(){
    val sampledata = listOf(
        Ranking("사용자1",5,6, categoryId = "movie",date = "2025-11-16"),
        Ranking("사용자2",3,6, categoryId = "movie",date = "2025-11-15"),
        Ranking("사용자3",4,6, categoryId = "movie", date = "2025-11-14"),
    )
    MobileSoftWareProjectTheme{
        RankingScreen(
            ranking =sampledata,
            onBack = {}
        )
    }
}
