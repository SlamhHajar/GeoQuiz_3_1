package com.example.geoquiz_3_1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private lateinit var  trueButton: Button
    private lateinit var falseButton:Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton:ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var levelText: TextView
    private lateinit var totalOfScores: TextView
    private lateinit var cheatButton: Button
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)    }

 private var questAnswered=0
    private var trueAnswer=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
////////////////////////////////////////////////Initial of Variable//////////////////////////////////////
        trueButton=findViewById(R.id.true_button)
        falseButton=findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        totalOfScores = findViewById(R.id.score_text_view)
        cheatButton = findViewById(R.id.cheat_button)
        levelText = findViewById(R.id.level)
        quizViewModel.randomQuest()
        updateQuestion()
/////////////////////////////////////////////SetON click Listener/////////////////////////////////
        trueButton.setOnClickListener{
            checkAnswer(true)
          quizViewModel.currentWasAnswer=true
            enable(false)
            sumScore()
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
            quizViewModel.currentWasAnswer=true
            enable(false)
            sumScore()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            enable(false)
            updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        previousButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }
        cheatButton.setOnClickListener {            // Start CheatActivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        updateQuestion()
    }
    override fun onActivityResult(requestCode: Int,resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false        }    }
    /////////////////////////////////////////////UpdateQuestion///////////////////////////////////////////////////
    private fun updateQuestion() {
        val questionTextResId =quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        if(quizViewModel.questionBank[quizViewModel.currentIndex].wasAnswered==false) {
            enable(true)
        }else{ enable(false)}

    }
    //////////////////////////////Check the Answer////////////////////////////////////////////////////////////
    private fun checkAnswer(userAnswer:Boolean){
        var correctAnswer=quizViewModel.currentQuestionAnswer
        questAnswered++
        val messageResId = when{
            quizViewModel.isCheater   -> {
                isCheatting()
                R.string.judgment_toast
            }
            userAnswer==correctAnswer -> {
                when(quizViewModel.currentIndex){
                    in 0..1->{
                     trueAnswer= trueAnswer+2}
                    in 2..3->{
                        trueAnswer= trueAnswer+4}
                    in 4..5->{
                       trueAnswer= trueAnswer+6}
                }
                R.string.correct_toast
            }
            else ->   R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    ////////////////////////Enable Button ///////////////////////////////////////////
    private fun enable(enable_Button:Boolean){
        falseButton.isEnabled=enable_Button
        trueButton.isEnabled=enable_Button
        cheatButton.isEnabled=enable_Button
    }
    //////////////////////////////////////////Summation of Score/////////////////////
    private fun sumScore() {
        var total = quizViewModel.sizeOfQuestBank
        var score = trueAnswer
        totalOfScores.setText("${trueAnswer}")
       levelText.setText("${quizViewModel.level}")
        if (questAnswered == total) {
            var msg = "You Answered the all question  "
            var scores = "Your Score is $score /24"
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            Toast.makeText(this, scores, Toast.LENGTH_LONG).show()
            /////////////////If you want to try again//////////////////////////////////////
//        trueAnswer=0
//        questAnswered=0
//        var trying="try answer the  Question agian "
//        Toast.makeText(this,trying,Toast.LENGTH_LONG).show()
//        for(i in questionBank){
//            i.wasAnswered=false
//        }
        }
    }


    fun isCheatting() {
        var msg = "You are cheat your score is decrement"
        var tost = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        if (quizViewModel.isCheater == true) {
            trueAnswer =trueAnswer + 0
            tost
        }}
//        }else if(quizViewModel.isCheater==true&&typeQest==4){
//            trueAnswer = trueAnswer - 4
//            tost
//        }else{trueAnswer = trueAnswer - 6
//            tost}


}
