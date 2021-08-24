package ru.unluckybatman.speechtotexttest

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import net.gotev.speech.Speech
import net.gotev.speech.SpeechDelegate

class MainActivity: AppCompatActivity(), SpeechDelegate {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Speech.init(this, packageName)
//        Speech.getInstance().setPreferOffline(true)
//        Speech.getInstance().setStopListeningAfterInactivity(10000000000)

        val button : Button = findViewById(R.id.button)
        button.setOnClickListener {
            Speech.getInstance().startListening(this@MainActivity)
        }
    }

    override fun onDestroy() {
        Speech.getInstance().shutdown()
        super.onDestroy()
    }

    private fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStartOfSpeech() {
//        TODO("Not yet implemented")
        Log.d(TAG, "onStartOfSpeech: ")
        toast("onStartOfSpeech")
    }

    override fun onSpeechRmsChanged(value: Float) {
//        TODO("Not yet implemented")
//        Log.d(TAG, "onSpeechRmsChanged: ")
//        toast("onSpeechRmsChanged")
    }

    override fun onSpeechPartialResults(results: MutableList<String>?) {
//        TODO("Not yet implemented")
        Log.d(TAG, "onSpeechPartialResults: ")
        toast("onSpeechPartialResults")
    }

    override fun onSpeechResult(result: String?) {
//        TODO("Not yet implemented")
        Log.d(TAG, "onSpeechResult: $result")
        toast(result ?: "null")
    }

}