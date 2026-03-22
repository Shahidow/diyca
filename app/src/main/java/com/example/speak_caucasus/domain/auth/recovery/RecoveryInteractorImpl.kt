package com.example.speak_caucasus.domain.auth.recovery

import com.example.speak_caucasus.data.repository.auth.AuthRepository
import com.example.speak_caucasus.domain.auth.recovery.models.ResetPasswordData
import com.example.speak_caucasus.domain.auth.recovery.models.VerifyResetCodeData
import com.example.speak_caucasus.util.Resource
import kotlinx.coroutines.flow.first

class RecoveryInteractorImpl(private val authRepository: AuthRepository): RecoveryInteractor {
    override suspend fun requestPasswordReset(email: String): Resource<Unit> {
        return authRepository.passwordReset(email).first()
    }

    override suspend fun verifyResetCode(
        email: String,
        code: String
    ): Resource<VerifyResetCodeData> {
        return authRepository.verifyResetCode(email, code).first()
    }

    override suspend fun resetPassword(resetPasswordData: ResetPasswordData): Resource<Unit> {
        return authRepository.resetPassword(resetPasswordData).first()
    }
}