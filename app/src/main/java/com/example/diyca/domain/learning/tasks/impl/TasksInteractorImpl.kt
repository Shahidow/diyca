package com.example.diyca.domain.learning.tasks.impl

import com.example.diyca.domain.learning.models.task_type.BuildSentenceTask
import com.example.diyca.domain.learning.models.task_type.BuildWordTask
import com.example.diyca.domain.learning.models.task_type.ChooseTranslationTask
import com.example.diyca.domain.learning.models.task_type.Task
import com.example.diyca.domain.learning.tasks.TasksInteractor

class TasksInteractorImpl: TasksInteractor {
    override fun getTasksList(): List<Task> {
        return listOf(
            BuildSentenceTask(
                id = "1",
                sentence = "Мое любимое блюдо это шашлык",
                correctTranslation = "Мое любимое блюдо это шашлык",
                words = listOf("Мое","любимое","блюдо","это","шашлык","лосось","кукуруза","хлеб","она","кинза","деревянная нога")
            ),
            BuildWordTask(
                id = "2",
                word = "Шашлык",
                correctTranslation = "Шашлык",
                letters = listOf("Къ","А","Ш","Л","К","К1","П1","Р","О","П","С","Т","И","Кх","Яь","З","1","Е")
            ),
            ChooseTranslationTask(
                id = "3",
                word = "шашлык и деревянная нога",
                options = listOf("шашлык","дерево","нога","шашлык и деревянная нога"),
                correctTranslation = "шашлык и деревянная нога"
            )
        )
    }

}