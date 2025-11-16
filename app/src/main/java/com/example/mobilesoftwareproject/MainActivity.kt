package com.example.mobilesoftwareproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobilesoftwareproject.data.QuizData
import com.example.mobilesoftwareproject.data.RankingStore
import com.example.mobilesoftwareproject.data.WrongAnswerStore
import com.example.mobilesoftwareproject.model.Ranking
import com.example.mobilesoftwareproject.navigation.Screen
import com.example.mobilesoftwareproject.ui.theme.CategoryScreen
import com.example.mobilesoftwareproject.ui.theme.MobileSoftWareProjectTheme
import com.example.mobilesoftwareproject.ui.theme.QuizScreen
import com.example.mobilesoftwareproject.ui.theme.RankingScreen
import com.example.mobilesoftwareproject.ui.theme.ResultScreen
import com.example.mobilesoftwareproject.ui.theme.WrongNoteScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        //RankingStore.clearAll(context)
        enableEdgeToEdge()
        setContent {
            MobileSoftWareProjectTheme {
                QuizNavHost()
            }
        }
    }
}

@Composable
fun QuizNavHost() {
    val navController = rememberNavController() // 화면 전환 담당
    NavHost( // 어떤 route에 어떤 화면을 연결할 것인가
        navController = navController,
        startDestination = Screen.Category.route // 메인화면을 카테고리 화면으로
    ) {
        //카테고리 선택화면에서 ->
        composable(route = Screen.Category.route) {
            CategoryScreen(
                onStartQuiz = { categoryId ->
                    //카테고리 선택 후 퀴즈시작 버튼 누르면 호출
                    navController.navigate(
                        Screen.Quiz.createRoute(categoryId)
                    )
                },
                onShowRanking = { // 랭킹 버튼 누르면 랭킹 화면 나오도록
                    categoryId->
                    navController.navigate(
                        Screen.Ranking.createRoute(categoryId)
                    )
                }
            )
        }
        // 랭킹 화면에서 ->
        composable(
            route = Screen.Ranking.route,
            arguments = listOf(
                navArgument("categoryId") {type = NavType.StringType}
            )
        ){  backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            val context = LocalContext.current

            val rankings = remember{ // 저장된 랭킹 불러오기
                RankingStore.loadRankingBYCategory(context,categoryId)
            }
            RankingScreen(
                ranking = rankings,
                onBack = {navController.popBackStack()}
            )
        }
        //오답노트 화면에서 ->
        composable(
            route = Screen.WrongNote.route
        ){
            val wrongAnswers = WrongAnswerStore.getWrongAnswer() // 오답 저장소에서 오답 리스트 가져옴
            WrongNoteScreen(
                wrongAnswer = wrongAnswers,
                onBack = {navController.popBackStack()} // 이전 화면으로 돌아가기
            )
        }

        composable( // 결과 화면에서 ->
            route = Screen.Result.route,
            arguments = listOf( // 해당 경로에서 받을 인자들의 타입 알려줌
                navArgument(("categoryId")) {type = NavType.StringType},
                navArgument("score") {type = NavType.IntType},
                navArgument("total") {type = NavType.IntType}
            )
        ){//인자 추출 및 사용
            backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId") ?:""
                val score = backStackEntry.arguments?.getInt("score") ?:0
                val total = backStackEntry.arguments?.getInt("total") ?:0
                val context = LocalContext.current
            ResultScreen(
                score = score,
                total = total,
                onShowWrongNote = {
                    navController.navigate(route = Screen.WrongNote.route)
                },
                onGoHome = {
                    navController.popBackStack( // 네비게이션 벡스택에서
                        route = Screen.Category.route, // 카테고리 화면으로
                        inclusive = false // 카테고리만 남기고 그 위 화면들은 제거
                    )
                },
                onSaveRanking = { name ->
                    val item = Ranking(
                        name = name,
                        score = score,
                        total = total,
                        categoryId = categoryId
                    )
                    RankingStore.addRanking(context, item)
                }
            )
        }

        composable( // 퀴즈화면에서 ->
            route = Screen.Quiz.route,   // "quiz/{categoryId}"
            arguments = listOf(
                // {categoryId} 자리에 들어갈 값의 타입을 정의
                navArgument("categoryId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            // 실제 전달된 categoryId 값을 꺼냄
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""

            // 선택한 카테고리에 해당하는 문제 리스트를 QuizData에서 가져오기
            val questionsForCategory = QuizData.getQuestions(categoryId)
            // QuizScreen 호출하면서 문제 리스트 전달
            QuizScreen(
                question = questionsForCategory,
                onQuizFinished = { score, total -> // 퀴즈가 끝났을 때 실행
                    navController.navigate(
                        Screen.Result.createRoute(categoryId,score, total)
                    )
                }
            )
        }
    }
}