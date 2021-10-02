package ru.unluckybatman.speechtotexttest.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import net.gotev.speech.Speech
import net.gotev.speech.SpeechDelegate
import org.koin.android.ext.android.get
import ru.unluckybatman.speechtotexttest.databinding.ActivityMainBinding

class MainActivity: MvpAppCompatActivity(), IMainActivity, SpeechDelegate {

    private val tag = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private val presenter by moxyPresenter { get<MainPresenter>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Speech.init(this, packageName)
//        Speech.getInstance().setPreferOffline(true)
//        Speech.getInstance().setStopListeningAfterInactivity(10000000000)


        binding.listenButton.setOnClickListener { presenter.processListenButton() }
        binding.nextButton.setOnClickListener { presenter.processNextButton() }
        binding.previousButton.setOnClickListener { presenter.processPrevButton() }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            requestPermissions()
        }
    }

    override fun onDestroy() {
        Speech.getInstance().shutdown()
        super.onDestroy()
    }

    override fun onStartOfSpeech() {
        Log.d(tag, "onStartOfSpeech: ")
    }

    override fun onSpeechRmsChanged(value: Float) {
        Log.d(tag, "onSpeechRmsChanged: ")
    }

    override fun onSpeechPartialResults(results: MutableList<String>?) {
        Log.d(tag, "onSpeechPartialResults: ")
    }

    override fun onSpeechResult(result: String?) {
        binding.resultTextView.text = result

        if (result == null) {
            toast("Null - не распозналось!")
        }

    }

    override fun startListening() {
        Speech.getInstance().startListening(this@MainActivity)
    }

    override fun showWord(text: String?) {
        binding.targetTextView.text = text
    }

    private fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun requestPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        this.let {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO
            )
        } -> {
        }
        else -> {
            requestPermissionLauncher.launch(
                Manifest.permission.RECORD_AUDIO
            )
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
//            presenter.startRecognition()
            println("GRANTED")
        } else {
            println("NOT GRANTED")
        }
    }

}