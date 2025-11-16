package com.example.mobilesoftwareproject.navigation
// 화면 간의 이동을 관리해줌
// 여러 화면을 모아두는 클래스
sealed class Screen(val route: String){
    object Category : Screen("category") // 카테고리 화면의 고유 경로 부여
    //퀴즈화면
    object Quiz : Screen("quiz/{categoryId}"){ // 퀴즈 화면의 고유 경로 부여
        //실제 이동할 때 쓸 route 문자열을 만들어 주는 함수
        fun createRoute(categoryId: String) = "quiz/$categoryId" // 카테고리 경로를 받기 위함
    }
    //결과화면 , 점수와 총문제 수를 보냄
    object Result : Screen("result/{categoryId}/{score}/{total}"){
        fun createRoute(categoryId: String,score:Int,total:Int) = "result/$categoryId/$score/$total" // 경로를 받기 위함
    } // 결과 화면의 고유 경로 부여

    //카테고리별 랭킹 화면
    object Ranking : Screen(route = "ranking/{categoryId}"){
        fun createRoute(categoryId: String) = "ranking/$categoryId"
    }
    //오답화면
    object WrongNote : Screen(route = "wrongNote")
}