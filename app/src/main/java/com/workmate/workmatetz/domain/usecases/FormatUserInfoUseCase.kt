package com.workmate.workmatetz.domain.usecases

import com.workmate.workmatetz.domain.entity.User
import javax.inject.Inject

class FormatUserInfoUseCase @Inject constructor() {
    operator fun invoke(user: User): String {
        return """
            User Information
            Name: ${user.firstName} ${user.lastName}
            Email: ${user.email}
            Phone: ${user.phone}
            Location: ${user.country}
            Gender: ${user.gender}
            Date of Birth: ${user.date}
        """.trimIndent()
    }
}