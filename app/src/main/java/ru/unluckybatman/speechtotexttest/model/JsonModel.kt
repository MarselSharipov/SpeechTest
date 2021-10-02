package ru.unluckybatman.speechtotexttest

data class Theme(var themeId: Long,
                 var name: String,
                 var tasks: List<Task>,
                 var test: Test,
                 var percentage: Int,
                 var score: Int,
                 var animate: Boolean)

data class Task(var taskId: Long,
                var name: String,
                var engine: String,
                var cards: ArrayList<Card>,
                var done: Boolean,
                var animate: Boolean)

data class Card(var cardId: Long,
                var type: Int,
                var text: String,
                var image: String?,
                var lottie: String?)

data class Test(var timer: Long,
                var engine: String,
                var text: String,
                var textList: List<String> = arrayListOf(),
                var done: Boolean,
                var animate: Boolean)