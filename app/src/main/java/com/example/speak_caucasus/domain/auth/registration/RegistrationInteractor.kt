package com.example.speak_caucasus.domain.auth.registration

import com.example.speak_caucasus.domain.auth.models.RegistrationData
import com.example.speak_caucasus.util.Resource

interface RegistrationInteractor {
    suspend fun registration(registrationData: RegistrationData): Resource<Unit>
}