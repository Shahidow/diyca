package com.example.diyca.util

object Validator {

    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
    private val NAME_REGEX = "^[\\p{L}\\d\\s'._-]{2,25}$".toRegex()

    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.matches(NAME_REGEX)
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && email.matches(EMAIL_REGEX)
    }

    fun isValidPassword(password: String): Boolean {
        // Минимум 6 символов
        val hasMinLength = password.length >= 6
        // Хотя бы одна буква
        val hasLetter = password.any { it.isLetter() }
        // Хотя бы одна цифра
        val hasDigit = password.any { it.isDigit() }
        // Хотя бы один спецсимвол
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasMinLength && hasLetter && hasDigit && hasSpecialChar
    }
}