package ru.unluckybatman.speechtotexttest.ui

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface IMainActivity: MvpView {

    @OneExecution
    fun startListening()

    fun showWord(text: String?)

}