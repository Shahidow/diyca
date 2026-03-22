package com.example.diyca.domain.auth.registration

import com.example.diyca.domain.auth.models.RegistrationData
import com.example.diyca.util.Resource

interface RegistrationInteractor {
    suspend fun registration(registrationData: RegistrationData): Resource<Unit>
}