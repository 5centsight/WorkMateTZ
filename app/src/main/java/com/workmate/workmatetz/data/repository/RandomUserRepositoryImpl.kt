package com.workmate.workmatetz.data.repository

import com.workmate.workmatetz.data.datasource.LocalDataSource
import com.workmate.workmatetz.data.datasource.RemoteDataSource
import com.workmate.workmatetz.data.dto.PersonResponse
import com.workmate.workmatetz.data.model.UserEntity
import com.workmate.workmatetz.domain.entity.User
import com.workmate.workmatetz.domain.repository.RandomUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RandomUserRepository {

    override suspend fun getRandomPerson(gender: String, nat: String): Result<User> {
        return try {
            val response = remoteDataSource.getRandomPerson(gender, nat)
            val user = response.toDomain(gender, nat)
            localDataSource.saveUser(user.toEntity(gender, nat))
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAllUsers(): Flow<List<User>> {
        return localDataSource.getAllUsers().map { users ->
            users.map { it.toDomain() }
        }
    }

    override suspend fun deleteUser(seed: String) {
        localDataSource.deleteUser(seed)
    }

    override suspend fun getExistedPerson(seed: String): Result<User> {
        return try {
            val localUser = localDataSource.getUserBySeed(seed)
            if (localUser != null) {
                return Result.success(localUser.toDomain())
            }
            val response = remoteDataSource.getExistedPerson(seed)
            val user = response.toDomain()
            localDataSource.saveUser(user.toEntity())
            Result.success(user)
        } catch (e: UnknownHostException) {
            val localUser = localDataSource.getUserBySeed(seed)
            if (localUser != null) {
                Result.success(localUser.toDomain())
            } else {
                Result.failure(e)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun PersonResponse.toDomain(gender: String? = null, nationality: String? = null): User {
        val result = this.results.first()
        return User(
            seed = this.info.seed,
            firstName = result.name.first,
            lastName = result.name.last,
            phone = result.phone,
            country = result.location.country,
            email = result.email,
            gender = gender ?: result.gender,
            nationality = nationality ?: result.nat,
            date = result.dob.date.take(10),
            age = result.dob.age,
            position = "${result.location.country}, ${result.location.state}, ${result.location.city}, ${result.location.street.name} ${result.location.street.number}",
            imageUrl = result.picture.large
        )
    }

    private fun User.toEntity(gender: String? = null, nationality: String? = null): UserEntity {
        return UserEntity(
            seed = seed,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            country = country,
            email = email,
            gender = gender ?: this.gender ?: "",
            nationality = nationality ?: this.nationality ?: "",
            date = date,
            age = age,
            position = position,
            imageUrl = imageUrl
        )
    }

    private fun UserEntity.toDomain(): User {
        return User(
            seed = seed,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            country = country,
            email = email,
            gender = gender,
            nationality = nationality,
            date = date,
            age = age,
            position = position,
            imageUrl = imageUrl
        )
    }
}