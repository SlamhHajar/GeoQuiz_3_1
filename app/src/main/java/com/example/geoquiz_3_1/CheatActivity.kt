package com.example.geoquiz_3_1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

private const val EXTRA_ANSWER_IS_TRUE =
    "com.example.geoquiz_3_1.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz_3_1.answer_shown"
private lateinit var answerTextView: TextView
private lateinit var showAnswerButton: Button
private lateinit var sdkApi: TextView
private var answerIsTrue = false
var numCheater=0


class CheatActivity : AppCompatActivity() {
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)

            }
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
            putExtra("chanceOfCheat", numCheater)
        }
        setResult(Activity.RESULT_OK, data)
    }

 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        sdkApi=findViewById(R.id.Sdk_ApI)
        /////////////////////////////////////////////////////////////////////////////////////////
      /**************************versionSdk****************/
        var versionSdk="API Level"+ Build.VERSION.SDK_INT.toString()
        sdkApi.setText(versionSdk)


        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            numCheater++
            quizViewModel.numCheat== numCheater
            setAnswerShownResult(true)

        }
    }
}