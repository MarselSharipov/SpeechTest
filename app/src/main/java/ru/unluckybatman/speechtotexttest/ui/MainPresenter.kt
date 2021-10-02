package ru.unluckybatman.speechtotexttest.ui

import moxy.MvpPresenter
import ru.unluckybatman.speechtotexttest.utils.Utils

class MainPresenter(private val utils: Utils): MvpPresenter<IMainActivity>() {

    private var wordsList = arrayListOf<String>()

    private var currentPosition = -1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        currentPosition = 0
        getWordList()
    }

    private fun getWordList() {
        val themeList = utils.getThemeList()

        themeList.forEach { theme ->
            theme.tasks.forEach { task ->
                task.cards.forEach { card ->
                    wordsList.add(card.text)
                }
            }
        }
        showWord()
    }

    fun processListenButton() {
        viewState.startListening()
    }

    fun processNextButton() {
        currentPosition++
        showWord()
    }

    fun processPrevButton() {
        currentPosition--
        showWord()
    }

    private fun showWord() {
        if (currentPosition > -1 && currentPosition < wordsList.size) {
            viewState.showWord(wordsList[currentPosition])
        } else {
            when {
                currentPosition < 0 -> {
                    currentPosition = 0
                }

                currentPosition >= wordsList.size -> {
                    currentPosition = wordsList.size - 1
                }
            }

            if (!wordsList.isNullOrEmpty()) {
                viewState.showWord(wordsList[currentPosition])
            }
        }
    }

}