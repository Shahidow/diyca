package com.example.diyca.domain.learning.lesson.impl

import com.example.diyca.domain.learning.lesson.LessonInteractor
import com.example.diyca.domain.learning.models.Lesson

class LessonInteractorImpl: LessonInteractor {
    override fun getLesson(): Lesson {
        return Lesson(
            id = 1,
            title = "",
            lessonsAmount = 3,
            newWordsAmount = 87,
            pic = "",
            text = "Можно поздравить вас: из 35 согласных фонем вы уже знаете 29. \n" +
                    "Теперь о некоторых\n" +
                    "специфических согласных фонемах, передаваемых через сочетание букв. Их называют смычно-гортанными или надгортанными,\n" +
                    " потому что при их произношении образуются две смычки в полости горла и в полости рта. ",
            lessonsList = emptyList()
        )
    }
}