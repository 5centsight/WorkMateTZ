package com.workmate.workmatetz.domain.usecases

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import javax.inject.Inject

class CopyUserToClipboardUseCase @Inject constructor(
    private val context: Context,
    private val getUserBySeedUseCase: GetUserBySeedUseCase,
    private val formatUserInfoUseCase: FormatUserInfoUseCase
) {
    suspend operator fun invoke(seed: String): Result<Unit> {
        return try {
            getUserBySeedUseCase(seed).onSuccess { user ->
                val clipboard = context.getSystemService(ClipboardManager::class.java)
                formatUserInfoUseCase(user).let {
                    clipboard?.setPrimaryClip(
                        ClipData.newPlainText("User Info", it)
                    )
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}