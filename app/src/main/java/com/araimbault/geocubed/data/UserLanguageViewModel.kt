package com.araimbault.geocubed.data

import androidx.lifecycle.ViewModel
import com.araimbault.geocubed.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserLanguageState(
    var languageId: Int = 0,
    var language: String = "en"
)

val availableLanguages = listOf("en", "fr")

class UserLanguageViewModel : ViewModel() {

    private val _languageState = MutableStateFlow(UserLanguageState())
    val languageState: StateFlow<UserLanguageState> = _languageState.asStateFlow()

    fun changeLanguage() {
        val newLanguageId = (_languageState.value.languageId + 1) % availableLanguages.size
        _languageState.update { currentState ->
            currentState.copy(
                languageId = newLanguageId,
                language = availableLanguages[newLanguageId]
            )
        }
        println("Language selected : ${_languageState.value.languageId} => ${_languageState.value.language}")
    }
}
