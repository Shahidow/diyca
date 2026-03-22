package com.example.speak_caucasus.domain.auth.recovery

import com.example.speak_caucasus.domain.auth.recovery.models.ResetPasswordData
import com.example.speak_caucasus.domain.auth.recovery.models.VerifyResetCodeData
import com.example.speak_caucasus.util.Resource

interface RecoveryInteractor {
    suspend fun requestPasswordReset(email: String): Resource<Unit>
    suspend fun verifyResetCode(email: String, code: String): Resource<VerifyResetCodeData>
    suspend fun resetPassword(resetPasswordData: ResetPasswordData): Resource<Unit>
}