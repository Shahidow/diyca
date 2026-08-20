package com.example.diyca.domain.auth.recovery

import com.example.diyca.data.repository.auth.AuthRepository
import com.example.diyca.domain.auth.recovery.models.ResetPasswordData
import com.example.diyca.domain.auth.recovery.models.VerifyResetCodeData
import com.example.diyca.util.Resource

class RecoveryInteractorImpl(private val authRepository: AuthRepository): RecoveryInteractor {
    override suspend fun requestPasswordReset(email: String): Resource<Unit> {
        return authRepository.passwordReset(email)
    }

    override suspend fun verifyResetCode(
        email: String,
        code: String
    ): Resource<VerifyResetCodeData> {
        return authRepository.verifyResetCode(email, code)
    }

    override suspend fun resetPassword(resetPasswordData: ResetPasswordData): Resource<Unit> {
        return authRepository.resetPassword(resetPasswordData)
    }
}