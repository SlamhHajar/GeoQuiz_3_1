package com.example.geoquiz_3_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var  trueButton: Button
    private lateinit var falseButton:Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton:ImageButton
    private lateinit var questionTextView: TextView
    private var questionBank = listOf(
            Question(R.string.question_text, true,false),
            Question(R.string.question_text1, false,false),
            Question(R.string.question_text2, false,false),
            Question(R.string.question_text3, false,false),
            Question(R.string.question_text4, true,false),
    )
    private var currentIndex = 0
    private var questAnswered=0
    private var trueAnswer=0

    /////////////////////////////////////////////UpdateQuestion///////////////////////////////////////////////////
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        if(questionBank[currentIndex].wasAnswered==false) {
            enable(true)
        }else{ enable(false)}
    }
    //////////////////////////////Check the Answer////////////////////////////////////////////////////////////
    private fun checkAnswer(userAnswer:Boolean){
        var correctAnswer=questionBank[currentIndex].answer
        ++questAnswered
        var messageResId= if(userAnswer==correctAnswer){
            ++trueAnswer
            R.string.correct_toast
        }
        else{R.string.incorrect_toast}
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }

    ////////////////////////Enable Button ///////////////////////////////////////////
    private fun enable(enable_Button:Boolean){
        falseButton.isEnabled=enable_Button
        trueButton.isEnabled=enable_Button
    }
    //////////////////////////////////////////Summation of Score/////////////////////
    private fun sumScore(){
        var total=questionBank.size
        var score=trueAnswer*100/total

        if(questAnswered==total){
            var msg="You Answered the all question  "
            var scores="Your Score is $score / 100"
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
            Toast.makeText(this,scores,Toast.LENGTH_LONG).show()
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
////////////////////////////////////////////////Initial of Variable//////////////////////////////////////
        trueButton=findViewById(R.id.true_button)
        falseButton=findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
/////////////////////////////////////////////SetON click Listener/////////////////////////////////
        trueButton.setOnClickListener{
            checkAnswer(true)
            questionBank[currentIndex].wasAnswered=true
            enable(false)
            sumScore()
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
            questionBank[currentIndex].wasAnswered=true
            enable(false)
            sumScore()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex +1) % questionBank.size
            updateQuestion()
        }

        previousButton.setOnClickListener {
            if(currentIndex==0 || currentIndex<0) currentIndex=questionBank.size
            currentIndex = (currentIndex -1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }
}
