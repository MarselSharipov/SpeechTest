package ru.unluckybatman.speechtotexttest.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.unluckybatman.speechtotexttest.ui.MainPresenter
import ru.unluckybatman.speechtotexttest.utils.Utils

val appModule = module {

    single { Utils(androidContext()) }

    factory { MainPresenter(get()) }

}