package com.example.speak_caucasus.domain.dictionaries.dictionary.impl

import android.annotation.SuppressLint
import com.example.speak_caucasus.data.repository.dictionaries.DictionaryRepository
import com.example.speak_caucasus.data.repository.favorites.FavoritesRepository
import com.example.speak_caucasus.domain.dictionaries.dictionary.DictionaryInteractor
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.flow.Flow

class DictionaryInteractorImpl(
    private val favoritesRepository: FavoritesRepository,
    private val dictionaryRepository: DictionaryRepository
) :
    DictionaryInteractor {
    override suspend fun getDictionary(dictionaryType: DictionaryType): Flow<List<DictionaryItem>> {
        return dictionaryRepository.getDictionary(dictionaryType)
    }

    override suspend fun updateFavoriteItem(dictionaryItem: DictionaryItem) {
        favoritesRepository.updateFavoriteItem(dictionaryItem)
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun setDic(){
        val dicList = listOf(
            DictionaryItem.Expression(
                id = 1,
                original = "Expression lkjdsgflksgcbedoi uegrofiengrconfin groeigfbufgbu geiucgebiuvfgiu egifuegbv iufgneiubgfiu cegbgfieungci",
                translation = "Expression lkjdsgflksgcbedoi uegrofiengrconfin groeigfbufgbu geiucgebiuvfgiu egifuegbv iufgneiubgfiu cegbgfieungci",
                isFavorite = false,
                audio = null
            ),
            DictionaryItem.Proverb(
                id = 3,
                original = "Proverb lkjdsgflksgcbedoi uegrofiengrconfin groeigfbufgbu geiucgebiuvfgiu egifuegbv iufgneiubgfiu cegbgfieungci",
                translation = "Proverb lkjdsgflksgcbedoi uegrofiengrconfin groeigfbufgbu geiucgebiuvfgiu egifuegbv iufgneiubgfiu cegbgfieungci",
                isFavorite = false,
                audio = ""
            ),
            DictionaryItem.Word(
                id = 1,
                original = "Word первое",
                translation = "Word lkjdsgflksgcbedoi uegrofiengrconfin groeigfbufgbu geiucgebiuvfgiu egifuegbv iufgneiubgfiu cegbgfieungci",
                isFavorite = false,
                audio = ""
            ),
            DictionaryItem.Word(
                id = 2,
                original = "Word второе",
                translation = "Word",
                isFavorite = false,
                audio = ""
            ),
            DictionaryItem.Word(
                id = 3,
                original = "Третье слово",
                translation = "Word",
                isFavorite = false,
                audio = ""
            ),
            DictionaryItem.Word(
                id = 4,
                original = "Word четверное",
                translation = "Word lkjdsgflksgcbedoi uegrofiengrconfin groeigfbufgbu geiucgebiuvfgiu egifuegbv iufgneiubgfiu cegbgfieungci",
                isFavorite = false,
                audio = ""
            ),
            DictionaryItem.Word(
                id = 5,
                original = "Word пятое",
                translation = "Word",
                isFavorite = false,
                audio = ""
            ),
            DictionaryItem.Word(
                id = 6,
                original = "Шестое слово",
                translation = "Word",
                isFavorite = false,
                audio = ""
            ),
        )
            dicList.forEach { item->
                dictionaryRepository.insertDictionaryItem(item)
            }

    }
}

