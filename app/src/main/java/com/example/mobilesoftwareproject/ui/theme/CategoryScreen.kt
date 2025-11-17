package com.example.mobilesoftwareproject.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesoftwareproject.data.QuizData
import kotlinx.coroutines.selects.select

//카테고리 선택 화면 - 메인 화면
@Composable
fun CategoryScreen(onStartQuiz:(String)->Unit = {}, // 퀴즈 시작 버튼이 눌렸을 때 신호 전달
                   onShowRanking: (String)-> Unit = {}, // 랭킹 버튼이 눌렸을 때 신호 전달
                   onShowWrongNote: (String) -> Unit = {}) // 오답노트 버튼이 눌렸을 때 신호 전달
{
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    //사용자가 어떤 카테고리를 눌렀는지 기억하기 위함
    //Compose가 값을 기억하고, 값이 바뀌면 화면을 다시 그려줌
    //처음엔 아무것도 선택되지않은 상태라 null해야함
    Column(
        modifier = Modifier
            .fillMaxSize() //이 Column이 화면 크기 전체를 차지
            .padding(24.dp), //가장자리에서 24만큼의 여백을 줌
        horizontalAlignment = Alignment.CenterHorizontally //자식들을 가로방향 중앙 정렬
    )
    {
        Text( //앱 상단 제목 텍스트 블럭
            text = "퀴즈 카테고리",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp)) //제목과 아래 내용사이에 여백 생성
        Row( // 가로 방향으로 자식을 나란히 배치
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth(), // 가로폭 전체 사용
            horizontalArrangement = Arrangement.spacedBy(8.dp)//자식들 간 사이를 균일하게 배치
        ) {
            QuizData.categories.forEach { (id, title) -> //각 카테고리id마다 카드를 생성하기 위해 반복문 사용
                Card(
                    modifier = Modifier
                        .size(width = 110.dp, height = 120.dp)//카드 크기 조절
                        .padding(vertical = 8.dp) //위아래 여백생성
                        //.padding(20.dp)
                        .clickable {
                            //카드가 눌렸을 때 실행됨
                            selectedCategoryId = id //핻동 저장
                        }
                ) {
                    Box( // 카테고리 카드 안의 박스블록
                        modifier = Modifier
                            .padding(35.dp)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(24.dp))

        //유저가 선택한 카테고리
        val selectedName = QuizData.categories
            //리스트의 key가 선택된 id와 일치한 항목을 찾으면 value부분을 꺼냄,없으면 null반환
            .find {it.first == selectedCategoryId}?.second

        if(selectedName != null){ //카테고리가 존재하면 텍스트 블록 생성
            Text( //선택한 카테고리를 나타내주는 텍스트 블록
                text = "선택된 카테고리: $selectedName",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(300.dp))

        //퀴즈 시작 버튼 & 랭킹 버튼
        Box(modifier = Modifier.fillMaxSize()) { //남은 영역 전체를 사용
            Button(
                modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 64.dp,end=32.dp),
                onClick = { //카테고리가 선택되있으면, 그 id를 콜백함수의 인자로 넘겨버림 - Navhost에 연결
                    selectedCategoryId?.let { categoryId -> onStartQuiz(categoryId) }
                },
                enabled = selectedCategoryId != null //카테고리 선택했을때만 활성화 되도록함
            ) {
                Text(text = "퀴즈 시작")
            }
            OutlinedButton(
                modifier =  Modifier.align(Alignment.BottomStart).padding(bottom = 64.dp, start = 32.dp),
                onClick = {
                    selectedCategoryId?.let {id->onShowRanking(id)}
                },
                enabled = selectedCategoryId != null
            ){
                Text(text = "랭킹 보기")
            }
            OutlinedButton(
                modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 128.dp,start = 32.dp),
                onClick = {
                    selectedCategoryId?.let {id->onShowWrongNote(id)}
                },
                enabled = selectedCategoryId != null
            ) {
                Text(text = "오답 노트")
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun CategoryScreenPreview(){
    MobileSoftWareProjectTheme {
        CategoryScreen()
    }
}