package com.workmate.workmatetz.domain.usecases

import android.content.Context
import android.content.Intent
import jakarta.inject.Inject

class ShareUserUseCase @Inject constructor(
    private val context: Context,
    private val getUserBySeedUseCase: GetUserBySeedUseCase,
    private val formatUserInfoUseCase: FormatUserInfoUseCase
) {
    suspend operator fun invoke(seed: String): Result<Unit> {
        return try {
            getUserBySeedUseCase(seed).onSuccess { user ->
                val userdata = formatUserInfoUseCase(user)
                userdata.let {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, it)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    val chooserIntent = Intent.createChooser(
                        shareIntent,
                        "Share User"
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(chooserIntent)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}