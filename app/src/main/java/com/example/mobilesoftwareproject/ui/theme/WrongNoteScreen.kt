package com.example.mobilesoftwareproject.ui.theme

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.mobilesoftwareproject.data.QuizData
import com.example.mobilesoftwareproject.model.Question
import com.example.mobilesoftwareproject.model.WrongAnswer
import com.example.mobilesoftwareproject.navigation.Screen
import kotlin.math.sin

@Composable
fun WrongNoteScreen(
    wrongAnswer: List<WrongAnswer>,
    onBack:() -> Unit // 뒤로 버튼
){
    Column(
        modifier = Modifier
            .fillMaxSize() // 전체화면 사용
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // 가로방향 가운데 정렬
    ){
        Text(
            text = "오답 노트",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.padding(16.dp))
        if(wrongAnswer.isEmpty()){ // 오답이 아예 없을 경우
            Text(
                text = "틀린 문제가 없습니다.",
                style = MaterialTheme.typography.bodyLarge, // 본문 큰 스타일
                modifier = Modifier.fillMaxWidth(),// 가로 전체 사용
                textAlign = TextAlign.Center
            )
        }else{ // 오답이 있을 경우, 스크롤 가능한 리스트 표시
            LazyColumn(
                modifier = Modifier
                    .weight(1f)// 남은 공간을 여기에 모두 할당하도록함
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)//항목 사이의 간격을 지정 ( 세로간격 ?)
            ){
                items(wrongAnswer){item -> // 각 오답문제를 하나씩 호출
                    wrongAnswerCard(item = item)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // 리스트와 버튼 사이의 간격

        Button(
            onClick = {onBack()},
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "결과 화면으로"
            )
        }
    }
}

@Composable
fun wrongAnswerCard(item : WrongAnswer){
    val question = item.question
    val myIndex = item.myAnswerIndex
    val correctIndex = item.correctIndex

    val mytext = question.options.get(myIndex)
    val correcttext = question.options.get(correctIndex)

    Card(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.padding(12.dp) // 카드 안쪽 여백 간격
        ){
            Text(
                text = question.text, // 문제 내용
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "내가 고른 답 : $mytext",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "실제 정답 : $correcttext",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun WrongNoteScreenPreview(){
    val sampleQuestion: Question = QuizData.questions.first()

    val sampleWrong = listOf(
        WrongAnswer(
            question = sampleQuestion,
            myAnswerIndex = 1,
            correctIndex = sampleQuestion.answerIndex
        )
    )
    MobileSoftWareProjectTheme {
        WrongNoteScreen(
            wrongAnswer = sampleWrong,
            onBack = {}
        )
    }
}