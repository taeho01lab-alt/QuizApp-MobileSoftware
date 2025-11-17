package com.example.mobilesoftwareproject.data

import com.example.mobilesoftwareproject.model.WrongAnswer
import kotlin.collections.sorted

//오답이 저장될 곳
object WrongAnswerStore {
    //오답들을 저장
    private val wrongAnswers = mutableListOf<WrongAnswer>() //오답 목록 저장
    //오답 추가
    fun addWrongAnswer(item: WrongAnswer) {
        //같은 오답이 있는지 확인
        val already = wrongAnswers.any {
            it.myAnswerIndex == item.myAnswerIndex &&
                    it.categoryId == item.categoryId &&
                    it.question.text == item.question.text
        }

        //중복된 오답이 아니면 리스트에 추가
        if (!already) {
            wrongAnswers.add(item)
        }
    }
    //닉네임 일괄 설정
    //오답이 생길 때는 userName이 비어 있는 상태라
    //결과 화면에서 닉네임을 입력하면 그떄 이름을 추가
    fun setUserNameForAll(name: String) {
        wrongAnswers.forEach { wrong ->
            if (wrong.userName.isNullOrBlank()) { // 아직 이름이 없는 오답만
                wrong.userName = name
            }
        }
    }
    //특정 카테고리의 닉네임 목록 가져오기
    fun getNicknamesByCategory(categoryId: String): List<String> {
        return wrongAnswers
            .filter { it.categoryId == categoryId && !it.userName.isNullOrBlank() } //카테고리 일치 + 닉네임 존재
            .map { it.userName!! }//String 으로 변환
            .distinct()
            .sorted()
    }
    //카테고리 + 닉네임 필터로 오답 가져오기
    fun getWrongAnswers(
        categoryId: String? = null,
        userName: String? = null
    ): List<WrongAnswer> {
        return wrongAnswers.filter { item ->
            val categoryMatch = (categoryId == null || item.categoryId == categoryId)
            val nameMatch = (userName.isNullOrBlank() || item.userName == userName)
            categoryMatch && nameMatch
        }
    }
    //특정 오답 하나 삭제
    fun removeWrongAnswer(target: WrongAnswer) {
        wrongAnswers.remove(target)
    }
    //전체 오답 목록 초기화
    fun clear() {
        wrongAnswers.clear()
    }
}