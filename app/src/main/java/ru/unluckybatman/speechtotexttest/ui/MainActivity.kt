package ru.unluckybatman.speechtotexttest.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import github.com.vikramezhil.dks.speech.Dks
import github.com.vikramezhil.dks.speech.DksListener
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.get
import ru.unluckybatman.speechtotexttest.databinding.ActivityMainBinding
import java.util.*

class MainActivity: MvpAppCompatActivity(), IMainActivity {

    private lateinit var binding: ActivityMainBinding

    private val presenter by moxyPresenter { get<MainPresenter>() }

    private var dks: Dks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listenButton.setOnClickListener { presenter.processListenButton() }
        binding.nextButton.setOnClickListener { presenter.processNextButton() }
        binding.previousButton.setOnClickListener { presenter.processPrevButton() }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        }

        dks = Dks(application, dksListener)
        dks?.currentSpeechLanguage = "ru-RU"
    }

    override fun startListening() {
        dks?.startSpeechRecognition()
    }

    override fun showWord(text: String?, currentPosition: String) {
        binding.targetTextView.text = text
        binding.currentPositionTextView.text = currentPosition
    }

    private fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun requestPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) -> {
        }
        else -> {
            requestPermissionLauncher.launch(
                Manifest.permission.RECORD_AUDIO
            )
        }
    }

    private val dksListener = object: DksListener {
        override fun onDksLiveSpeechResult(liveSpeechResult: String) {
            Log.d("TaskActivity", "onDksLiveSpeechResult: $liveSpeechResult")
        }

        override fun onDksFinalSpeechResult(speechResult: String) {
            Log.d("TaskActivity", "onDksFinalSpeechResult: $speechResult")
            binding.resultTextView.text = speechResult

            if (speechResult == null) {
                toast("Null - не распозналось!")
            }
        }

        override fun onDksLiveSpeechFrequency(frequency: Float) {}

        override fun onDksLanguagesAvailable(defaultLanguage: String?, supportedLanguages: ArrayList<String>?) {
        }

        override fun onDksSpeechError(errMsg: String) {
            Log.d("TaskActivity", "onDksSpeechError: $errMsg ")
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            println("GRANTED")
        } else {
            println("NOT GRANTED")
        }
    }

}