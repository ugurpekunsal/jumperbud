package com.jumperbud.models

import com.google.firebase.database.Exclude
import java.sql.Time
import java.time.LocalDateTime
import java.util.*

data class TrainingSession(
    @get:Exclude
    var id: String? = null,
    var fullName: String? = null,
    var trainerName: String? = null,
    var date: String? = null,
    var time: String? = null
) {

}