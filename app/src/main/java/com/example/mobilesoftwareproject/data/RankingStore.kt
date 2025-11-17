package com.example.mobilesoftwareproject.data

import android.content.Context
import com.example.mobilesoftwareproject.model.Ranking

object RankingStore{
    private const val FILE_NAME = "quiz_rank" //shared Pref파일 이름
    private const val KEY_NAME = "key_rank" //파일 접근하기 위한 키
    //context : 앱 환경 정보
    fun loadRanking(context : Context):List<Ranking>{ //랭킹 가져오기 , quiz_rank란 파일에 key_rank라는 키가 생성됨.
        val prefs = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE) //quiz_rank란 이름의 SharedPreferences파일을 가져옴 , 없으면 새로 생성
        // "name|score|total\nname2|score2|total2\n..."
        val stored = prefs.getString(KEY_NAME,"") ?:"" //key_rank라는 키에 저장된 문자열을 가져옴, 없으면 기본값으로 ""할당

        if(stored.isBlank()){return emptyList()} //문자열이 비어있으면 빈 리스트 반환
        return stored //저장된 문자열들을 한 줄로 나누기위함 -
            .lines() // \n 으로 split
            .filter{it.isNotBlank()} // 빈 문자열 베제
            .mapNotNull { line -> // 각 줄을 Ranking으로 바꿈
                val parts = line.split("|")
                if(parts.size != 5) return@mapNotNull null //저장된 데이텨 형식이 잘못됬으면 데이터 무시 후 null 반환

                val name = parts[0]
                val score = parts[1].toInt()
                val total = parts[2].toInt()
                val categoryID = parts[3]
                val date = parts[4]

                Ranking(name, score, total,categoryID, date) //랭킹 하나의 객체 생성
            }
            .sortedByDescending {it.score} //점수 기준 높은순 정렬
    }
    //특정 카테고리의 랭킹만 읽기
    fun loadRankingBYCategory(context: Context, categoryId : String): List<Ranking>{
        return loadRanking(context)
            .filter {it.categoryId == categoryId}
    }

    fun addRanking(context:Context, Rank: Ranking){ //랭킹 갱신
        val current = loadRanking(context).toMutableList() //가변성 부여
        //같은 기록의 중복 방지
        val already = current.any{
            it.name == Rank.name && it.score == Rank.score && it.total == Rank.total && it.categoryId == Rank.categoryId && it.date == Rank.date
        }
        if(!already){ //중복된 기록이 없을 때만 추가
            current.add(Rank)
        }
        val sorted10 = current
            .sortedByDescending { it.score } //재정렬
            .take(10) //상위 10개만 나오도록

        //다시 문자열로 변환해서 Shared_pref에 저장되도록함
        val toStore: String = sorted10.joinToString(separator = "\n") { r ->
            "${r.name}|${r.score}|${r.total}|${r.categoryId}|${r.date}" // 각 속성을 분리하기 용이하도록 | 로 구분
        }
        //객체 가져오기 - 위의 문자열을 넣기 위함
        val prefs = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
        prefs.edit() //수정 객체 꺼내기
            .putString(KEY_NAME,toStore) // 문자열 저장
            .apply() //변경 내용 적용 ( 비동기 )
    }
    fun clearAll(context: Context) {
        val prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_NAME).apply()
    }
}
