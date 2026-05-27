package com.example.diyca.domain.phrasebooks.impl

import com.example.diyca.data.repository.dictionaries.DictionaryDataBaseRepository
import com.example.diyca.domain.phrasebooks.PhrasebookInteractor
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow

class PhrasebookInteractorImpl(
    private val dictionaryDataBaseRepository: DictionaryDataBaseRepository
) : PhrasebookInteractor {
    override suspend fun getPhrasebooks(): Flow<List<Phrasebook>> {
        return dictionaryDataBaseRepository.getPhrasebooks()
    }

    override suspend fun setData() {
        val list = listOf(
            Phrasebook(
                id = 1,
                title = "Приветствия",
                image = ""
            ),
            Phrasebook(
                id = 2,
                title = "Соболезнования",
                image = ""
            ),
            Phrasebook(
                id = 3,
                title = "Прощание",
                image = ""
            ),
            Phrasebook(
                id = 4,
                title = "В кафе",
                image = ""
            ),
            Phrasebook(
                id = 5,
                title = "На улице",
                image = ""
            ),
            Phrasebook(
                id = 6,
                title = "На крыше",
                image = ""
            ),
            Phrasebook(
                id = 7,
                title = "Под диваном",
                image = ""
            ),
            Phrasebook(
                id = 8,
                title = "На телефоне",
                image = ""
            ),
        )
        list.forEach { item ->
            dictionaryDataBaseRepository.insertPhrasebook(item)
        }
    }
}