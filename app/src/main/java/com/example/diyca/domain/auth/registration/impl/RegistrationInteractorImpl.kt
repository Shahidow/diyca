package com.example.diyca.domain.auth.registration.impl

import com.example.diyca.data.repository.auth.AuthRepository
import com.example.diyca.domain.auth.models.RegistrationData
import com.example.diyca.domain.auth.registration.RegistrationInteractor
import com.example.diyca.util.Resource

class RegistrationInteractorImpl(private val authRepository: AuthRepository) :
    RegistrationInteractor {
    override suspend fun registration(registrationData: RegistrationData): Resource<Unit> {
        return authRepository.registration(registrationData)
    }
}