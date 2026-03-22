package com.example.speak_caucasus.domain.auth.registration.impl

import com.example.speak_caucasus.data.repository.auth.AuthRepository
import com.example.speak_caucasus.domain.auth.models.RegistrationData
import com.example.speak_caucasus.domain.auth.registration.RegistrationInteractor
import com.example.speak_caucasus.util.Resource
import kotlinx.coroutines.flow.first

class RegistrationInteractorImpl(private val authRepository: AuthRepository) :
    RegistrationInteractor {
    override suspend fun registration(registrationData: RegistrationData): Resource<Unit> {
        return authRepository.registration(registrationData).first()
    }
}