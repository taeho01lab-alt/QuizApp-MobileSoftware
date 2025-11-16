package com.example.mobilesoftwareproject.ui.theme

import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.mobilesoftwareproject.data.QuizData.questions
import com.example.mobilesoftwareproject.model.Question
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.LaunchedEffect
import com.example.mobilesoftwareproject.data.QuizData
import com.example.mobilesoftwareproject.data.WrongAnswerStore
import com.example.mobilesoftwareproject.model.WrongAnswer

@Composable
//한 화면에 퀴즈 한 문제씩
fun QuizScreen(
    question: List<Question>, // 카테고리 내 문제를 가져옴
    onQuizFinished: (score:Int, total: Int)-> Unit){ // 문제가 다 끝난뒤, 점수와 총점수를 얻기위함

    LaunchedEffect(Unit) { WrongAnswerStore.clear() } // 컴포저블함수가 처음 활성화 될때 오답 목록 초기화
    if(question.isEmpty()) { // 퀴즈가 할당되지 않았으면
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("문제가 없습니다.")
        }
        return
    }
    //remember을 하는 이유 : 오답을 위함
    //현재 문제 인덱스 상태 저장
    var currentIndex by remember { mutableStateOf(0) }
    //유저가 현재 선택한 보기 인덱스 저장
    var selectedOptionIndex by remember {mutableStateOf<Int?>(null)}
    //현재까지 맞춘 개수(누적)
    var score by remember {mutableStateOf(0)}
    //퀴즈가 끝났는지 여부 - 마지막 문제인지 알기위함
    var isFinished by remember {mutableStateOf(false)}
    //현재 문제 객체 추출
    val currentQuestion = question[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally // 가로방향 기본 정렬 - 가운데
    ){
        Text( // 상단에 문제 진행 현황 텍스트 블록
            text = "문제 ${currentIndex + 1} / ${questions.size/3}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(), // 가로 전체 채우기
            textAlign = TextAlign.Start // 텍스트 왼쪽 정렬
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text( // 문제 내용 보여주는 텍스트 블록
            text = currentQuestion.text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(24.dp))

        //보기 4개를 카드블록으로 표현
        currentQuestion.options.forEachIndexed { index,optionText ->
             val isSelected = selectedOptionIndex == index // 선택된 보기와 그렇지 않은 보기를 구분하기위함

            Card( // 각 보기의 카드블럭
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp) // 양쪽 세로 공백
                    .clickable{ // 클릭 시
                        selectedOptionIndex = index // 보기가 선택됨
                    },
                colors = CardDefaults.cardColors( // 카드 색
                    containerColor = // 선택 유무에 따른 카드색 구분
                        if(isSelected)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                )
            ){
                Box( // 카드 안쪽 블록
                    modifier = Modifier
                        .padding(12.dp),//카드 안쪽 여백
                    contentAlignment = Alignment.CenterStart
                ){
                    Text( // 보기 내용 텍스트블록
                        text = optionText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row( // 다음/제출 버튼
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End // 오른쪽 정렬
        ){
            val isLastQuestion = currentIndex == question.lastIndex
            Button(
                    // 2. 바닥에서 32dp만큼 위로 올리고, 오른쪽에서 32dp만큼 왼쪽으로 이동
                onClick = {
                    val selectedIndex = selectedOptionIndex ?: return@Button
                    //보기 선택 전이면 버튼 작동 안함

                    if(selectedIndex == currentQuestion.answerIndex){ // 맞으면 점수 + 1
                        score++
                    }else{
                        val wrongItem = WrongAnswer(
                            question = currentQuestion,
                            myAnswerIndex =selectedIndex ,
                            correctIndex = currentQuestion.answerIndex
                        )
                        WrongAnswerStore.addWrongAnswer(wrongItem)
                    }
                    if(isLastQuestion){
                        onQuizFinished(score,question.size)
                    }else{
                        currentIndex++
                        selectedOptionIndex = null
                    }
                },
                enabled = selectedOptionIndex != null && !isFinished
            ){
                Text(
                    text = when{
                        isFinished -> "퀴즈 완료"
                        isLastQuestion -> "제출"
                        else -> "다음 문제"
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun QuizScreenPreview(){
    val sampleQuestions = QuizData.getQuestions("movie")
    MobileSoftWareProjectTheme{
        QuizScreen(
            question = sampleQuestions,
            onQuizFinished = {_,_ ->}
        )
    }
}