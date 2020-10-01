package com.example.geoquiz_3_1

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.random.Random
private const val TAG = "QuizViewModel"
class QuizViewModel: ViewModel() {
// override fun onCleared() {
//        super.onCleared()
//           }
    var currentIndex = 0
    var isCheater = false
var numCheat=0
 //  var questionShow = listOf()
 var questionEasy = listOf(
        Question(R.string.question_text1_easy, false,false,1),
        Question(R.string.question_text2_easy, false,false,1),
        Question(R.string.question_text3_easy, false,false,1),
        Question(R.string.question_text4_easy, false,false,1),
       Question(R.string.question_text5_easy, true,false,1),
       Question(R.string.question_text6_easy, false,false,1),


       )
    var questionMeduim = listOf(
        Question(R.string.question_text8_mud, false,false,2),
        Question(R.string.question_text9_mud, true,false,2),
        Question(R.string.question_text7_mud, true,false,2),
        Question(R.string.question_text10_mud, false,false,2),
        Question(R.string.question_text11_mud, true,false,2),
        Question(R.string.question_text12_mud, true,false,2)
    )
    var questionDeffcult = listOf(
        Question(R.string.question_text13_diffuc, true,false,3),
        Question(R.string.question_text14_diffuc, false,false,3),
        Question(R.string.question_text15_diffuc, false,false,3),
        Question(R.string.question_text16_diffuc, false,false,3),
        Question(R.string.question_text17_diffuc, true,false,3),
        Question(R.string.question_text18_diffuc, false,false,3)
    )

     var questionBank = arrayListOf<Question>()
fun randomQuest(){
  questionBank.clear();
    val randomValues = List(6){(0..5).shuffled().last()}

    for (i in 0..5) {

        Log.d(TAG,"in for")
        when (i) {
        in 0..1 -> {
            for (l in 0..1) {
           //if(questionShow[i].typeofLevel==1)
                questionBank.add(questionEasy[randomValues[i]])
            Log.d(TAG,"in if 1")
        }}
        in 2..3 -> {
              // for (l in 0..1) {
              // if(questionShow[i].typeofLevel==2)
                    questionBank.add(questionMeduim[randomValues[i]])
                Log.d(TAG,"in if 2")
            }
        //}
            in 4..5 -> {
                //for (l in 0..18) {
            //  if(questionShow[i].typeofLevel==3)
                    questionBank.add(questionDeffcult[randomValues[i]])
                Log.d(TAG,"in if 3")
            }
    }}
}



    val sizeOfQuestBank: Int
        get() = questionBank.size

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val level: Int
        get() = questionBank[currentIndex].typeofLevel

    var currentWasAnswer:  Boolean?=null
        get() = questionBank[currentIndex].wasAnswered


    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev() {
        if (currentIndex == 0 || currentIndex < 0) currentIndex = questionBank.size
        currentIndex = (currentIndex - 1) % questionBank.size
    }

}