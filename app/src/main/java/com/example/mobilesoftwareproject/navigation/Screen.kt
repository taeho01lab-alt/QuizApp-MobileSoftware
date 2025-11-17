package com.example.mobilesoftwareproject.navigation
// 화면 간의 이동을 관리해줌
// 여러 화면을 모아두는 클래스
sealed class Screen(val route: String){
    object Category : Screen("category")//카테고리 화면 자체 고유 경로 부여
    object Quiz : Screen("quiz/{categoryId}"){ //퀴즈 화면 자체 고유 경로 부여
        //실제 이동 시 사용할 route 문자열
        fun createRoute(categoryId: String) = "quiz/$categoryId" // 카테고리 경로를 받기 위함 - 보통 이전 화면에서 기억해야하는 값들을 적음
    }
    object Result : Screen("result/{categoryId}/{score}/{total}"){
        fun createRoute(categoryId: String,score:Int,total:Int) = "result/$categoryId/$score/$total" // 경로를 받기 위함 - 퀴즈화면에서 계산된, 점수와 총문제 수, 카테고리id를 기억하기위함
    } // 결과 화면의 고유 경로 부여

    //카테고리별 랭킹 화면
    object Ranking : Screen(route = "ranking/{categoryId}"){
        fun createRoute(categoryId: String) = "ranking/$categoryId"
    }
    //카테고리별 오답화면
    object WrongNote : Screen(route = "wrongNote/{categoryId}"){
        fun createRoute(categoryId: String) = "wrongNote/$categoryId"
    }
}