package com.example.mobilesoftwareproject.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//점수 결과 화면
@Composable
fun ResultScreen(score:Int,
                 total:Int,
                 onGoHome:()-> Unit,
                 onShowWrongNote:()->Unit = {},
                 onSaveRanking:(name: String)->Unit = {}
                 ){
    val WrongCount = total - score
    var name by remember{ mutableStateOf("") }
    var saveCompleted by remember {mutableStateOf(false)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text( // 결과 화면 제목 텍스트 블록
            text = "결과 화면",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.padding(24.dp))

        Text( // 최종 점수가 나오는 텍스트 블록
            text = "최종 점수: $score / $total",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = "맞춘 문제 수: $score 개\n틀린 문제 수: $WrongCount 개",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(24.dp))

        Text(
            text = "랭킹에 저장할 닉네임을 입력하세요",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField( // 입력을 받을수있는 텍스트 블록
            value = name,
            onValueChange = {name = it},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {Text("예: 홍길동")}
        )
        Column(
            modifier = Modifier.fillMaxWidth() // 영역 가로 전체 사용
        ){
            OutlinedButton(
                onClick = {onShowWrongNote()}, // 버튼 클릭시 오답노트 콜백
                modifier = Modifier
                    .fillMaxWidth() // 버튼이 가로 전체를 차지하도록 함
            ) {
                Text(text = "오답노트")
            }
            Spacer(modifier = Modifier.padding(5.dp))
            OutlinedButton(
                onClick = {
                    val finalName = if(name.isBlank()) "익명" else name
                    onSaveRanking(finalName)
                    saveCompleted = true
                          },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "점수 저장")
            }
            if(saveCompleted){
                Text(
                    text = "저장 완료",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Button(
                onClick = {onGoHome()},
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(text = "메인화면")
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun ResultsScreenPreview(){
    MobileSoftWareProjectTheme {
        ResultScreen(
            score = 3,
            total = 5,
            onGoHome = {},
            onShowWrongNote = {},
            onSaveRanking = {_ -> }
        )
    }
}