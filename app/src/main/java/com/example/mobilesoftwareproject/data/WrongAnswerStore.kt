package com.example.mobilesoftwareproject.data

import com.example.mobilesoftwareproject.model.WrongAnswer
//오답이 저장될 곳
object WrongAnswerStore{
    private val wrongAnswers = mutableListOf<WrongAnswer>() // 오답 목록 저장
    //오답 추가
    fun addWrongAnswer(item: WrongAnswer){
        wrongAnswers.add(item)
    }
    //오답 반환
    fun getWrongAnswer():List<WrongAnswer>{
        return wrongAnswers.toList()
    }

    //오답목록 초기화
    fun clear(){
        wrongAnswers.clear()
    }
}