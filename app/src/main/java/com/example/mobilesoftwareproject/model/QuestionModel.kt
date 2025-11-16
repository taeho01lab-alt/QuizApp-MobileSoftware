package com.example.mobilesoftwareproject.model

//퀴즈 구조
data class Question(
    val id: Int, // 문제 고유 ID
    val categoryId: String, // 카테고리 ID
    val text:String, // 문제 내용
    val options: List<String>, // 보기 ( 4지선다 )
    val answerIndex: Int // 정답 인덱스
)
//랭킹 구조
data class Ranking(
    val name:String, // 유저 이름
    val score:Int, // 맞춘 문제 수
    val total:Int, // 총 점수
    val categoryId: String // 어떤 카테고리의 랭킹인가
)
//오답 노트 구조
data class WrongAnswer(
    val question: Question, // 틀렸던 문제
    val myAnswerIndex:Int, // 선택 했던 보기 인덱스
    val correctIndex: Int // 정답 보기 인덱스
)