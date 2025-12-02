package com.workmate.workmatetz.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PersonResponse(
    val results: List<PersonResultsDto>,
    val info: PersonInfoDto
)

@Serializable
data class PersonResultsDto(
    val gender: String,
    val name: PersonNameDto,
    val location: PersonLocationDto,
    val email: String,
    val dob: PersonDobDto,
    val phone: String,
    val picture: PersonPictureDto,
    val nat: String
)

@Serializable
data class PersonNameDto(
    val title: String,
    val first: String,
    val last: String
)

@Serializable
data class PersonDobDto(
    val date: String,
    val age: String
)

@Serializable
data class PersonPictureDto(
    val large: String,
    val medium: String,
    val thumbnail: String
)

@Serializable
data class PersonInfoDto(
    val seed: String
)

@Serializable
data class PersonLocationDto(
    val street: LocationStreetDto,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: CoordsDto
)

@Serializable
data class LocationStreetDto(
    val number: Int,
    val name: String
)

@Serializable
data class CoordsDto(
    val latitude: String,
    val longitude: String
)