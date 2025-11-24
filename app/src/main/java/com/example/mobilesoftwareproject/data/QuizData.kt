package com.example.mobilesoftwareproject.data

import com.example.mobilesoftwareproject.model.Question
//퀴즈 데이터
object QuizData {// (id, 이름) 구조로 카테고리 관리
val categories = listOf(
        "movie" to "영화",
        "music" to "음악",
        "game" to "게임"
    )
    fun getCategoryTitleById(id: String): String {
        // categories 리스트에서 id가 일치하는 항목(it.first)을 찾고,
        // 그 항목의 두 번째 값(it.second, 즉 이름)을 반환합니다.
        // 만약 해당하는 id가 없으면 "알 수 없는 카테고리"를 반환합니다.
        return categories.find { it.first == id }?.second ?: "알 수 없는 카테고리"
    }
    // 실제 문제들
    val questions: List<Question> = listOf(
        //------------------영화--------- - 명대사 맞히기 퀴즈
        Question(
            id = 1,
            categoryId = "movie",
            text = "\"I'll be back.\" 이 명대사가 나오는 영화는?",
            options = listOf("터미네이터", "겨울왕국", "주토피아", "매트릭스"),
            answerIndex = 0

        ),
        Question(
            id = 2,
            categoryId = "movie",
            text = "\"Why so serious?\" 이 명대사가 나오는 영화는?",
            options = listOf("배트맨","조커","수어사이드 스쿼드","슈퍼맨"),
            answerIndex = 1
        ),
        Question(
            id = 3,
            categoryId = "movie",
            text = "\"비겁한 변명입니다.\" 이 명대사가 나오는 영화는?",
            options = listOf("실미도", "바람", "태극기 휘날리며", "친구"),
            answerIndex = 0

        ),
        Question(
            id = 4,
            categoryId = "movie",
            text = "\"나 이대나온 여자야\" 이 명대사가 나오는 영화는?",
            options = listOf("도둑들","범죄와의 전쟁","극한직업","타짜"),
            answerIndex = 3
        ),
        Question(
            id = 5,
            categoryId = "movie",
            text = "\"나 전당포한다. 금이빨은 받아.\" 이 명대사가 나오는 영화는?",
            options = listOf("끝까지 간다","존 윅","아저씨","악마를 보았다"),
            answerIndex = 2
        ),
        //-------------------음악-------- - 명곡 가사 맞히기
        Question(
            id = 6,
            categoryId = "music",
            text = "\"눈물이 차올라서 고갤 들어\" 이 가사가 있는 노래는?",
            options = listOf("태연 - 만약에","이하이 - 한숨","에일리 - 보여줄게","아이유 - 좋은 날"),
            answerIndex = 3
        ),
        Question(
            id = 7,
            categoryId = "music",
            text = "\"Gonna be, gonna be golden\". 이 가사가 있는 노래는?",
            options = listOf("Saja Boys - Soda Pop","Huntr/x - Golden","Coldplay - Viva La Vida","BTS - Dynamite"),
            answerIndex = 1

        ),
        Question(
            id = 8,
            categoryId = "music",
            text = "\"Cause I-I-I'm in the stars tonight\". 이 가사가 있는 노래는?",
            options = listOf("BTS - Dynamite", "NewJeans - Hype Boy", "IVE - Love Dive", "AESPA - Next Level"),
            answerIndex = 0
        ),
        Question(
            id = 9,
            categoryId = "music",
            text = "\"I’m on the next level\". 이 가사가 있는 노래는?",
            options = listOf("ITZY - Wannabe", "IVE - Eleven", "NewJeans - ETA","aespa - Next Level"),
            answerIndex = 3
        ),
        Question(
            id = 10,
            categoryId = "music",
            text = "\"나를 삼킨 appetite\". 이 가사가 있는 노래는?",
            options = listOf("IVE - I AM", "LE SSERAFIM - UNFORGIVEN", "AESPA - Black Mamba", "NewJeans - OMG"),
            answerIndex = 2
        ),
        //--------------------게임-------- -롤 포지션 맞추기
        Question(
            id = 11,
            categoryId = "game",
            text = "다음 중 페이커(Faker)의 현재 소속 팀은?",
            options = listOf("T1", "GEN", "KT", "DK"),
            answerIndex = 0
        ),
        Question(
            id = 12,
            categoryId = "game",
            text = "다음 중 데프트(Deft)의 2023 시즌 소속 팀은?",
            options = listOf("HLE", "DK", "KT", "GEN"),
            answerIndex = 2
        ),
        Question(
            id = 13,
            categoryId = "game",
            text = "다음 중 쇼메이커(ShowMaker)의 소속 팀은?",
            options = listOf("GEN", "T1", "DK", "KT"),
            answerIndex = 2
        ),
        Question(
            id = 14,
            categoryId = "game",
            text = "다음 중 쵸비(Chovy)의 소속 팀은?",
            options = listOf("GEN", "T1", "KT", "DRX"),
            answerIndex = 0
        ),
        Question(
            id = 15,
            categoryId = "game",
            text = "다음 중 제우스(Zeus)의 소속 팀은?",
            options = listOf("T1", "HLE", "GEN", "KT"),
            answerIndex = 1
        )
    )
    // 문제 호출 시 특정 카테고리의 문제들만 가져오기 위함
    fun getQuestions(categoryId:String):List<Question>{
        return questions.filter {it.categoryId == categoryId}
    }
}
