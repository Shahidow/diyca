package com.example.diyca.domain.auth.recovery

import com.example.diyca.domain.auth.recovery.models.ResetPasswordData
import com.example.diyca.domain.auth.recovery.models.VerifyResetCodeData
import com.example.diyca.util.Resource

interface RecoveryInteractor {
    suspend fun requestPasswordReset(email: String): Resource<Unit>
    suspend fun verifyResetCode(email: String, code: String): Resource<VerifyResetCodeData>
    suspend fun resetPassword(resetPasswordData: ResetPasswordData): Resource<Unit>
}